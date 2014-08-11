package com.lvl6.mobsters.services.task

import com.lvl6.mobsters.common.utils.AbstractAction
import com.lvl6.mobsters.common.utils.AbstractService
import com.lvl6.mobsters.common.utils.ICallableAction
import com.lvl6.mobsters.dynamo.TaskForUserCompleted
import com.lvl6.mobsters.dynamo.TaskForUserOngoing
import com.lvl6.mobsters.dynamo.TaskStageForUser
import com.lvl6.mobsters.dynamo.User
import com.lvl6.mobsters.dynamo.repository.EventPersistentForUserRepository
import com.lvl6.mobsters.dynamo.repository.TaskForUserCompletedRepository
import com.lvl6.mobsters.dynamo.repository.TaskForUserOngoingRepository
import com.lvl6.mobsters.dynamo.repository.TaskStageForUserRepository
import com.lvl6.mobsters.dynamo.repository.UserRepository
import com.lvl6.mobsters.dynamo.setup.DataServiceTxManager
import com.lvl6.mobsters.info.IQuestJob
import com.lvl6.mobsters.info.IQuestJobMonsterItem
import com.lvl6.mobsters.info.ITaskStageMonster
import com.lvl6.mobsters.info.Task
import com.lvl6.mobsters.info.TaskStageMonster
import com.lvl6.mobsters.info.repository.QuestRepository
import com.lvl6.mobsters.info.xtension.ConfigExtensions
import com.lvl6.mobsters.server.ControllerConstants
import com.lvl6.mobsters.services.user.UserExtensionLib
import com.lvl6.mobsters.utility.lambda.Director
import com.lvl6.mobsters.utility.lambda.IntCounter
import com.lvl6.mobsters.utility.probability.ProbabilityExtensionLib
import com.lvl6.mobsters.validation.constraints.ConfigID
import com.lvl6.mobsters.validation.constraints.DynamoID
import java.sql.Timestamp
import java.util.ArrayList
import java.util.Collections
import java.util.Date
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import org.hibernate.validator.constraints.ScriptAssert
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import static com.google.common.base.Preconditions.*
import static com.lvl6.mobsters.services.task.TaskServiceImpl.*

import static extension java.lang.String.format

@Component
class TaskServiceImpl extends AbstractService implements TaskService
{
	private static val Logger LOG = LoggerFactory.getLogger(TaskServiceImpl)

	@Autowired
	private var TaskForUserCompletedRepository taskForUserCompletedRepository

	@Autowired
	private var TaskForUserOngoingRepository taskForUserOngoingRepository

	@Autowired
	private var TaskStageForUserRepository taskStageForUserRepository

	@Autowired
	private var EventPersistentForUserRepository eventPersistentForUserRepository

	@Autowired
	private var UserRepository userRepo

	// @Autowired
	// private var TaskRepository taskRepo
	@Autowired
	private var QuestRepository questRepo

	@Autowired
	package var extension UserExtensionLib userExtensionLib

	@Autowired
	package var extension TaskExtensionLib taskExtensionLib

	@Autowired
	@Qualifier("concurrent")
	package var extension ProbabilityExtensionLib probExtensionLib

	@Autowired
	package var extension ConfigExtensions configExtensionLib

	@Autowired
	package var DataServiceTxManager txManager

	/**************************************************************************/
	/* BEGIN NON-CRUD LOGIC **********************************************/
	/**************************************************************************/
	/**************************************************************************/
	/* BEGIN CRUD LOGIC ***************************************************/
	/* BEGIN READ ONLY LOGIC *********************************************/
	/*************************************************************************/
	override getUserTaskForUserId(String userId)
	{
		val List<TaskForUserOngoing> tfuoList = taskForUserOngoingRepository.
			findByUserId(userId)
		checkArgument(
			tfuoList.nullOrEmpty,
			"No ongoing tasks found for userId=%s; taskList=%s",
			userId,
			tfuoList
		)

		var TaskForUserOngoing retVal
		if (tfuoList.size() > 1)
		{
			LOG.warn(
				"User with userId=%s has multiple ongoing tasks.  Selecting the most recent from taskList=%s",
				userId, tfuoList)
			retVal = tfuoList.reduce [ min, next |
				var TaskForUserOngoing nextMin = next
				if (min.getStartDate() < next.getStartDate())
				{
					nextMin = min
				}
				return nextMin
			]
		}
		else
		{
			retVal = tfuoList.get(0)
		}

		return retVal
	}

	/**************************************************************************/
	override getTaskCompletedForUser(String userId)
	{
		return taskForUserCompletedRepository.findByUserId(userId)
	}

	/**************************************************************************/
	override getTaskStagesForUserWithTaskForUserId(String taskForUserUuid)
	{
		return taskStageForUserRepository.findByTaskForUserId(taskForUserUuid)
	}

	/**************************************************************************/
	override getUserPersistentEventForUserId(String userId)
	{
		return eventPersistentForUserRepository.findByUserId(userId)
	}

	/**************************************************************************/
	/* BEGIN TRANSACTIONAL LOGIC  ***************************************/
	/**************************************************************************/
	override completeTasks(
		String userUuid,
		Director<TaskService.CompleteTasksBuilder> director
	)
	{
		val ArrayList<TaskForUserCompleted> saveList = (new TaskServiceImpl.CompleteTasksBuilderImpl(
			userUuid) => [ listBuilder |
			director.apply(listBuilder)
		]).build()

		var boolean success = false
		val boolean isTxRoot = txManager.requireTransaction()
		try
		{

			// Save whatever we were asked to create to Dynamo.
			taskForUserCompletedRepository.saveEach(saveList)

			// TODO: Isn't there a concept of changing something previous OnGoing to Completed?
			success = true
		}
		finally
		{
			if (success)
			{
				if (isTxRoot)
				{
					txManager.commit()
				}
			}
			else
			{
				txManager.rollback()
			}
		}
	}

	static class CompleteTasksBuilderImpl implements TaskService.CompleteTasksBuilder
	{

		// the end state: objects to be saved to db
		private val String userUuid
		private val ArrayList<TaskForUserCompleted> completedTaskList

		new(String userUuid)
		{
			this.userUuid = userUuid
			completedTaskList = new ArrayList<TaskForUserCompleted>(2)
		}

		override taskId(int taskId)
		{
			throw new UnsupportedOperationException(
				"The workflow for normal task completion is not yet clear enough to determine if this belongs here"
			)
		}

		override taskId(int taskId, Date timeOfEntry)
		{
			completedTaskList.add(
				new TaskForUserCompleted() => [
					it.userId = userUuid
					it.taskId = taskId
					it.timeOfEntry = timeOfEntry
				]
			)

			return this
		}

		def ArrayList<TaskForUserCompleted> build()
		{
			return completedTaskList
		}
	}

	/**************************************************************************/
	override generateUserTaskStages(String userId, Date curTime, int taskId, boolean isEvent,
		int eventId, int gemsSpent, List<Integer> questIds, String elementName,
		boolean forceEnemyElem, boolean alreadyCompletedMiniTutorialTask)
	{
		return new TaskServiceImpl.GenerateUserTaskActionImpl(this, userId, curTime,
			taskId, isEvent, eventId, gemsSpent, questIds, elementName, forceEnemyElem,
			alreadyCompletedMiniTutorialTask)
	}

	// TODO: Is gem spending mandatory when this "is an event"?
	@ScriptAssert.List(@ScriptAssert(lang="javascript", script="if (_this.forceEnemyElem) { return(_this.elementName !== null && _this.elementName !== '');}; return true;", message="elementName must not be blank or null when forceEnemyElem is set true"),
	@ScriptAssert(lang="javascript", script="if (_this.isEvent) { return(_this.eventId > 0); } else { return(_this.gemsSpent == 0); }", message="If this is an event, then eventId must be a positive value."))
	static class GenerateUserTaskActionImpl 
		extends AbstractAction 
		implements ICallableAction<TaskService.GenerateUserTaskListener>
	{
		@DynamoID
		val String userId
	
		// Cannot mark @Past because client clock may run fast.  Create an annotation for
		// tolerated margin for error?
		val Date curTime
	
		@ConfigID
		val int taskId
	
		val boolean isEvent
		val int eventId
	
		@Min(0)
		val int gemsSpent
	
		@Size(min=1)
		val List<Integer> questIds
	
		val String elementName
		val boolean forceEnemyElem
	
		val boolean alreadyCompletedMiniTutorialTask
	
		@NotNull
		val TaskServiceImpl parent
	
		val extension UserExtensionLib userExtensionLib
	
		val extension TaskExtensionLib taskExtensionLib
	
		val extension ProbabilityExtensionLib probExtensionLib
		
		val extension ConfigExtensions configExtensionLib
	
		// Derived state
		//
		var User aUser
		var Task aTask
		var TaskForUserOngoing newUserTask
		var List<TaskForUserOngoing> tasksToDelete
		var boolean mayGenerateMonsterPieces
	
		val HashMap<Integer, List<Integer>> stageNumsToExps = new HashMap<Integer, List<Integer>>()
		val HashMap<Integer, List<Integer>> stageNumsToCash = new HashMap<Integer, List<Integer>>()
		val HashMap<Integer, List<Integer>> stageNumsToOil = new HashMap<Integer, List<Integer>>()
		val HashMap<Integer, List<Boolean>> stageNumsToPuzzlePiecesDropped = new HashMap<Integer, List<Boolean>>()
		val HashMap<Integer, List<TaskStageMonster>> stageNumsToTaskStageMonsters = new HashMap<Integer, List<TaskStageMonster>>()
		val HashMap<Integer, HashMap<Integer, Integer>> stageNumsToTsmIdToItemId = new HashMap<Integer, HashMap<Integer, Integer>>()
	
		val IntCounter expGained = new IntCounter()
		val IntCounter cashGained = new IntCounter()
		val IntCounter oilGained = new IntCounter()
	
		new(TaskServiceImpl parentService, String userId, Date curTime, int taskId, boolean isEvent,
			int eventId, int gemsSpent, List<Integer> questIds, String elementName,
			boolean forceEnemyElem, boolean alreadyCompletedMiniTutorialTask)
		{
			super(parentService)
			this.userId = userId
			this.curTime = curTime
			this.taskId = taskId
			this.isEvent = isEvent
			this.eventId = eventId
			this.gemsSpent = gemsSpent
			this.questIds = questIds
			this.elementName = elementName
			this.forceEnemyElem = forceEnemyElem
			this.alreadyCompletedMiniTutorialTask = alreadyCompletedMiniTutorialTask
	
			this.parent = parentService
			this.userExtensionLib = parentService.userExtensionLib
			this.taskExtensionLib = parentService.taskExtensionLib
			this.probExtensionLib = parentService.probExtensionLib
			this.configExtensionLib = parentService.configExtensionLib
		}
	
		override execute(TaskService.GenerateUserTaskStagesResponseBuilder resultBuilder)
		{
			verifySemantics()
	
			//calculate the SINGLE monster the user fights in each stage
			generateStages(resultBuilder)
	
			evaluateResult(resultBuilder)
		}
	
		def void verifySemantics()
		{
			aUser = parent.userRepo.load(userId)
			checkNotNull(aUser)
	
			newUserTask = new TaskForUserOngoing()
			newUserTask.taskId = taskId
			aTask = newUserTask.task
			checkNotNull(aTask)
	
			tasksToDelete = aUser.getOngoingTaskList(LOG)
			if (tasksToDelete.size > 0)
			{
				LOG.warn(
					"(will continue processing, but) user has existing task.  None should exist when beginning another.  userId=%s, taskId=%d, userTask=%s".
						format(userId, taskId, tasksToDelete.format)
				)
			}
	
			// if event, check if user has enough gems to reset event.  If not event, gemsSpent is zero.
			aUser.checkCanSpendGems(gemsSpent, LOG)
			
			if(taskId != ControllerConstants.MINI_TUTORIAL__GUARANTEED_MONSTER_DROP_TASK_ID) {
				LOG.info(
					"Player is completing a post-mini-tutorial task and so may collect monster pieces.  user=%s, task=%d",
					userId, taskId)
				mayGenerateMonsterPieces = true
			} else {
				if (
					(alreadyCompletedMiniTutorialTask == false) &&
					(aUser.hasCompletedTask(aTask) == false)
				) {
					LOG.info(
						"Player is completing mini-tutorial for first time and so may collect monster pieces.  user=%s, task=%d",
						userId, taskId)
					mayGenerateMonsterPieces = true
				} else {
					LOG.info(
						"Player is repeating mini-tutorial and so may NOT collect monster pieces.  user=%s, task=%d",
						userId, taskId)
					mayGenerateMonsterPieces = false
				}
			}
		}
	
		def void evaluateResult(TaskService.GenerateUserTaskResponseBuilder resultBuilder)
		{
			//DELETE TASK AND STAGES AND RECORD INTO TASK HISTORY
			tasksToDelete.forEach[it.deleteExisting()]
	
			//record into user_task table	  
			val int tsId = TaskStageRetrieveUtils.getFirstTaskStageIdForTaskId(tId)
			val long userTaskId = InsertUtils.get()
				.insertIntoUserTaskReturnId(userId, taskId, expGained, cashGained, oilGained, curTime,
					tsId)
	
			//record into user_task stage table
			recordStages(userTaskId, stageNumsToExps, stageNumsToCash, stageNumsToOil,
				stageNumsToPuzzlePiecesDropped, stageNumsToTaskStageMonsters,
				stageNumsToTsmIdToItemId)
	
			//send stuff back up to caller
			utIdList.add(userTaskId)
			stageNumsToProtos.putAll(stageNumsToProtosTemp)
	
			LOG.info("stageNumsToProtosTemp=" + stageNumsToProtosTemp)
			LOG.info("stageNumsToProtos=" + stageNumsToProtos)
	
			//start the cool down timer if for event
			if (isEvent)
			{
				val int numInserted = InsertUtils.get().
					insertIntoUpdateEventPersistentForUser(uId, eventId, clientTime)
				LOG.info(
					"started cool down timer for (eventId, userId): " + uId + "," + eventId +
						"\t numInserted=" + numInserted)
				previousCurrency.put(MiscMethods.gems, u.getGems())
				if (0 != gemsSpent)
				{
					val int gemChange = -1 * gemsSpent
					updateUser(u, gemChange)
					LOG.info(
						"successfully upgraded user gems to reset event cool down timer? Answer:" +
							success)
					currencyChange.put(MiscMethods.gems, u.getGems())
				}
			}
		}
	
		//stage can have multiple monsters; stage has a drop rate (but is useless for now); 
		//for each stage do the following
		//1) select monster at random
		//1a) determine if monster drops a puzzle piece
		//2) create MonsterProto
		//3) create TaskStageProto with the MonsterProto
		def void generateStages(
			TaskService.GenerateUserTaskStagesResponseBuilder resultBuilder)
		{
			//quest monster items are dropped based on QUEST JOB IDS not quest ids
			val List<IQuestJob> questJobs = 
				parent.questRepo.findAll(questIds)
					.map[it.questJobs].flatten.toList
			val List<TaskStageForUser> userTaskStages = 
				newUserTask.userTaskStages
	
			//for each stage, calculate the monster(s) the user will face and
			//reward(s) that might be given if the user wins
			aTask.taskStages.forEach [ stageMeta |
				val List<ITaskStageMonster> taskStageMonsters = stageMeta.stageMonsters
				
				userTaskStages.add(
					new TaskStageForUser() => [
						it.stageNum = stageMeta.stageNum
						var ITaskStageMonster stageMonster = null
						var IQuestJobMonsterItem droppedItem = null
						
						if (forceEnemyElem) {
							// select the element-matching monster.  This is the ONE monster for
							// this stage.
							stageMonster = 
								taskStageMonsters.selectElementMonster()
						} else {
							//select one monster, at random. This is the ONE monster for this stage
							stageMonster = taskStageMonsters
								.selectWithoutReplacement(1)[it.chanceToAppear]
								.get(0)
						}
						
						if (taskId != ControllerConstants.MINI_TUTORIAL__GUARANTEED_MONSTER_DROP_TASK_ID) {
							droppedItem = stageMonster.monster.rollForDroppedItem(questJobs)
						}
			
						it.taskStageMonsterId = stageMonster.id
						expGained.add(stageMonster.expReward)
						cashGained.add(
							rollValueInRange(stageMonster.minCashDrop, stageMonster.maxCashDrop)
						)
						oilGained.add(
							rollValueInRange(stageMonster.minOilDrop, stageMonster.maxOilDrop)
						)
						
						if (droppedItem != null) {
							it.itemIdDropped = droppedItem.item.id
							it.monsterPieceDropped = false
						} else {
							it.itemIdDropped = -1
					  		it.monsterPieceDropped = mayGenerateMonsterPieces
						}
							

						//create the proto
						//					val TaskStageProto tsp = 
						//						CreateInfoProtoUtils.createTaskStageProto(tsId, ts, spawnedTaskStageMonsters,
						//							puzzlePiecesDropped, individualCash, individualOil, tsmIdToItemId)
						LOG.info("task stage proto=" + tsp);
						LOG.info("after tsp taskStageMonsterIdToItemId=" + tsmIdToItemId)
						LOG.info(
							"exp, cash, oil. exp=" + individualExps + "\t cash=" + individualCash +
								"\t oil=" + individualOil)
						//NOTE, all the sizes are equal:
						//individualSilvers.size() == individualExps.size() == puzzlePiecesDropped.size()
						// == spawnedTaskStageMonsters.size()
						//update the protos to return to parent function
						// stageNumsToProtos.put(stageNum, tsp)
						stageNumsToExps.put(stageMeta.stageNum, individualExps)
						stageNumsToCash.put(stageMeta.stageNum, individualCash)
						stageNumsToOil.put(stageMeta.stageNum, individualOil)
						// stageNumsToPuzzlePieceDropped.put(stageNum, puzzlePiecesDropped)
						stageNumsToTaskStageMonsters.put(stageMeta.stageNum, null/*spawnedTaskStageMonsters*/)
						stageNumsToTsmIdToItemId.put(stageMeta.stageNum, tsmIdToItemId)
					]
				)
			]
	
			// TODO
			// LOG.info("monster(s) spawned=" + spawnedTaskStageMonsters)
			return // stageNumsToProtos
		}
	
		def ITaskStageMonster selectElementMonster(List<ITaskStageMonster> taskStageMonsters)
		{
			return taskStageMonsters.findFirst[it.monster.element == elementName]
		}
	
		/*picking without replacement*/
		/*def ITaskStageMonster selectRandomMonster(List<ITaskStageMonster> taskStageMonsters)
		{
			val int size = taskStageMonsters.size()
		  	//sum up chance to appear, and need to normalize all the probabilities
		  	val float sumOfProbabilities = 
		  		taskStageMonsters.fold(0f) [sum, stgMnstr|
		  			return sum + stgMnstr.chanceToAppear]
		  	val FloatCounter randFloat = new FloatCounter(rand.nextFloat())
		  	taskStageMonsters.findFirst[
		  		var retVal = false
		  		val nextProb = it.chanceToAppear
		  		if (randFloat.isLessThan(nextProb)) {
		  			retVal = true;
		  		} else {
		  			randFloat.subtract(nextProb)
		  		}
		  		
		  		return retVal
		  	]
		}*/
		
		def void writeToUserCurrencyHistory() {
			var HashMap<String, Integer> currencyChange
			var HashMap<String, Integer> previousCurrency
			if (currencyChange.isEmpty()) {
				return
			}
	
			val String reason = "ControllerConstants.UCHRFC__END_PERSISTENT_EVENT_COOLDOWN"
			val String details = "eventId=%d, taskId=%d".format(eventId, taskId)
			val HashMap<String, Integer> currentCurrency = new HashMap<String, Integer>()
			val HashMap<String, String> reasonsForChanges = new HashMap<String, String>()
			val HashMap<String, String> detailsMap = new HashMap<String, String>()
	
			// val String gems = MiscMethods.gems
			currentCurrency.put("gems", aUser.getGems())
			reasonsForChanges.put("gems", reason)
			detailsMap.put("gems", details)
	
		// MiscMethods.writeToUserCurrencyOneUser(userId, curTime, currencyChange, 
		//		  previousCurrency, currentCurrency, reasonsForChanges, detailsMap)
		}
	
		//TODO: MOVE THESE METHODS INTO A UTILS FOR TASK
		def void deleteExisting(TaskForUserOngoing aTaskForUser) {
	
			//  	DeleteUtils.get().deleteTaskForUserOngoingWithTaskForUserId(taskForUserId)
			val String userId = aTaskForUser.getUserId()
			val int taskId = aTaskForUser.getTaskId()
			val int expGained = aTaskForUser.getExpGained()
			val int cashGained = aTaskForUser.getCashGained()
			val int oilGained = aTaskForUser.getOilGained()
			val int numRevives = aTaskForUser.getNumRevives()
			val Date aDate = aTaskForUser.getStartDate(); //shouldn't null
	
			// TODO: Use a TimeUtils method!!!
			var Timestamp startTime = null
			if (null != aDate) {
				startTime = new Timestamp(aDate.getTime())
			}
			val Timestamp endTime = null
			val boolean userWon = false
			val boolean cancelled = true
			val int taskStageId = aTaskForUser.getTaskStageId()
	
			var int num = DeleteUtils.get().
				deleteTaskForUserOngoingWithTaskForUserId(taskForUserId)
			LOG.warn(
				"deleted existing task_for_user. taskForUser=" + aTaskForUser +
					"\t (should be 1) numDeleted=" + num)
	
			//meh, fogedaboudit 
			num = InsertUtils.get().insertIntoTaskHistory(taskForUserId, userId, taskId,
				expGained, cashGained, oilGained, numRevives, startTime, endTime, userWon,
				cancelled, taskStageId)
			LOG.warn(
				"inserted into task_history. taskForUser=" + aTaskForUser +
					"\t (should be 1) numInserted=" + num)
		}
	
		def void deleteExistingTaskStagesForUser(long taskForUserId) {
			val List<TaskStageForUser> taskStages = TaskStageForUserRetrieveUtils.
				getTaskStagesForUserWithTaskForUserId(taskForUserId)
	
			val List<Long> userTaskStageId = new ArrayList<Long>()
			val List<Long> userTaskId = new ArrayList<Long>()
			val List<Integer> stageNum = new ArrayList<Integer>()
			val List<Integer> taskStageMonsterIdList = new ArrayList<Integer>()
			val List<String> monsterTypes = new ArrayList<String>()
			val List<Integer> expGained = new ArrayList<Integer>()
			val List<Integer> cashGained = new ArrayList<Integer>()
			val List<Integer> oilGained = new ArrayList<Integer>()
			val List<Boolean> monsterPieceDropped = new ArrayList<Boolean>()
			val List<Integer> itemIdDropped = new ArrayList<Integer>()
	
			for (var int i = 0; i < taskStages.size(); i++) {
				val TaskStageForUser tsfu = taskStages.get(i)
				userTaskStageId.add(tsfu.getId())
				userTaskId.add(tsfu.getUserTaskId())
				stageNum.add(tsfu.getStageNum())
	
				val int monsterId = tsfu.getTaskStageMonsterId()
				taskStageMonsterIdList.add(monsterId)
				monsterTypes.add(tsfu.getMonsterType())
				expGained.add(tsfu.getExpGained())
				cashGained.add(tsfu.getCashGained())
				oilGained.add(tsfu.getOilGained())
				val boolean dropped = tsfu.isMonsterPieceDropped()
				monsterPieceDropped.add(dropped)
				itemIdDropped.add(tsfu.getItemIdDropped())
	
			}
	
			var int num = DeleteUtils.get().deleteTaskStagesForUserWithIds(userTaskStageId)
			LOG.warn(
				"num task stage history rows deleted: num=" + num + "taskStageForUser=" +
					taskStages)
	
			num = InsertUtils.get().insertIntoTaskStageHistory(userTaskStageId, userTaskId,
				stageNum, taskStageMonsterIdList, monsterTypes, expGained, cashGained,
				oilGained, monsterPieceDropped, itemIdDropped)
			LOG.warn(
				"num task stage history rows inserted: num=" + num + "taskStageForUser=" +
					taskStages)
		}
	
		def boolean updateUser(User u, int gemChange) {
			if (!u.updateRelativeGemsNaive(gemChange)) {
				LOG.error(
					"unexpected error: problem with updating user gems to delete event cool down timer. gemChange=" +
						gemChange + "user=" + u)
				return false
			}
			return true
		}
	
		//for a monster, choose the reward to give (monster puzzle piece)
		def List<Boolean> generatePuzzlePieces(List<TaskStageMonster> taskStageMonsters,
			Map<Integer, Integer> tsmIdToItemId, int userId, int taskId,
			boolean alreadyCompletedMiniTutorialTask) {
			val List<Boolean> piecesDropped = new ArrayList<Boolean>()
	
			//ostensibly and explicitly preserve ordering in monsterIds
			for (TaskStageMonster tsm : taskStageMonsters) {
				val int tsmId = tsm.getId()
	
				//I think tsmIdToItemId will always contain entry for tsmId...
				if (tsmIdToItemId.containsKey(tsmId) && tsmIdToItemId.get(tsmId) > 0) {
					LOG.info(
						"not generating monster piece since monster dropped item(s). tsmId=" +
							tsmId)
					LOG.info("item(s)=" + tsmIdToItemId.get(tsmId) + "\t tsm=" + tsm)
					piecesDropped.add(false)
	
				// continue
				}
			}
	
			return piecesDropped
		}
	
		def void recordStages(long userTaskId, Map<Integer, List<Integer>> stageNumsToExps,
			Map<Integer, List<Integer>> stageNumsToCash,
			Map<Integer, List<Integer>> stageNumsToOil,
			Map<Integer, List<Boolean>> stageNumsToPuzzlePiecesDropped,
			Map<Integer, List<TaskStageMonster>> stageNumsToTaskStageMonsters,
			Map<Integer, Map<Integer, Integer>> stageNumsToTsmIdToItemId) 
		{
			val Set<Integer> stageNums = stageNumsToExps.keySet()
			val List<Integer> stageNumList = new ArrayList<Integer>(stageNums)
			Collections.sort(stageNumList)
			val int size = stageNumList.size()
	
			//	  LOG.info("inserting task_stage_for_user: userTaskId="
			//	  		+ userTaskId + "\t stageNumsToSilvers=" + stageNumsToSilvers
			//	  		+ "\t stageNumsToExps=" + stageNumsToExps
			//	  		+ "\t stageNumsToPuzzlePiecesDropped=" + stageNumsToPuzzlePiecesDropped
			//	  		+ "\t stageNumsToMonsterIds=" + stageNumsToMonsterIds)
			//loop through the individual stages, saving each to the db.
			for (val int i = 0; i < size; i++) {
				val int stageNum = stageNumList.get(i)
				val List<TaskStageMonster> taskStageMonsters = stageNumsToTaskStageMonsters.
					get(stageNum)
				val List<Integer> expsGained = stageNumsToExps.get(stageNum)
				val List<Integer> cashGained = stageNumsToCash.get(stageNum)
				val List<Integer> oilGained = stageNumsToOil.get(stageNum)
				val List<Boolean> monsterPiecesDropped = stageNumsToPuzzlePiecesDropped.
					get(stageNum)
				val Map<Integer, Integer> tsmIdToItemId = stageNumsToTsmIdToItemId.get(
					stageNum)
	
				val int numStageRows = taskStageMonsters.size()
				val List<Long> userTaskIds = Collections.nCopies(numStageRows, userTaskId)
				val List<Integer> repeatedStageNum = Collections.nCopies(numStageRows,
					stageNum)
	
				val List<Integer> tsmIds = new ArrayList<Integer>()
				val List<String> monsterTypes = new ArrayList<String>()
				for (TaskStageMonster tsm : taskStageMonsters) {
					tsmIds.add(tsm.getId())
					monsterTypes.add(tsm.getMonsterType())
				}
	
				val int num = InsertUtils.get().
					insertIntoUserTaskStage(userTaskIds, repeatedStageNum, tsmIds,
						monsterTypes, expsGained, cashGained, oilGained,
						monsterPiecesDropped, tsmIdToItemId)
				LOG.info("for stageNum=%d, inserted %d rows", stageNum, num)
			}
		}
	
		def void setResponseBuilder(Builder resBuilder, List<Long> userTaskIdList,
			Map<Integer, TaskStageProto> stageNumsToProtos) 
		{
			//stuff to send to the client
			val long userTaskId = userTaskIdList.get(0)
			resBuilder.setUserTaskId(userTaskId)
			  
			//to handle the case if there are gaps in stageNums for a task, we
			//order the available stage numbers. Then we give it all to the client
			//sequentially, just because.
			val Set<Integer> stageNums = stageNumsToProtos.keySet()
			val List<Integer> stageNumsOrdered = new ArrayList<Integer>(stageNums)
			Collections.sort(stageNumsOrdered)
			  
			for (val Integer i : stageNumsOrdered) {
				val TaskStageProto tsp = stageNumsToProtos.get(i)
				resBuilder.addTsp(tsp)}
			}
		}
	}
	
    /**************************************************************************/
    
    //for the dependency injection
    def void setUserRepository( UserRepository userRepo )
    {
    	this.userRepo = userRepo
    }
    
    def void setTaskRepository( TaskRepository taskRepo )
    {
    	this.taskRepo = taskRepo
    }
    
    def void setTaskForUserCompletedRepository( TaskForUserCompletedRepositoryImpl taskForUserCompletedRepository )
    {
        this.taskForUserCompletedRepository = taskForUserCompletedRepository
    }

	def void setTaskForUserOngoingRepository(
		TaskForUserOngoingRepository taskForUserOngoingRepository )
	{
		this.taskForUserOngoingRepository = taskForUserOngoingRepository
	}

	def void setTaskStageForUserRepository( 
		TaskStageForUserRepository taskStageForUserRepository
	)
	{
		this.taskStageForUserRepository = taskStageForUserRepository
	}
	
	def void setEventPersistentForUserRepository( 
		EventPersistentForUserRepository eventPersistentForUserRepository
	)
	{
		this.eventPersistentForUserRepository = eventPersistentForUserRepository
	}
	
	def void setDataServiceTxManager( DataServiceTxManager txManager )
	{
		this.txManager = txManager
	}	
}