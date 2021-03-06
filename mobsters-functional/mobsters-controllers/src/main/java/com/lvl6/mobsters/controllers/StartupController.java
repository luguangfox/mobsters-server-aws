//package com.lvl6.mobsters.controllers;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import com.lvl6.mobsters.utility.common.CollectionUtils;
//import com.lvl6.mobsters.controllers.utils.ConfigurationDataUtil;
//import com.lvl6.mobsters.dynamo.AchievementForUser;
//import com.lvl6.mobsters.dynamo.ClanForUser;
//import com.lvl6.mobsters.dynamo.EventPersistentForUser;
//import com.lvl6.mobsters.dynamo.MiniJobForUser;
//import com.lvl6.mobsters.dynamo.MonsterEnhancingForUser;
//import com.lvl6.mobsters.dynamo.MonsterEvolvingForUser;
//import com.lvl6.mobsters.dynamo.MonsterForUser;
//import com.lvl6.mobsters.dynamo.MonsterHealingForUser;
//import com.lvl6.mobsters.dynamo.QuestForUser;
//import com.lvl6.mobsters.dynamo.QuestJobForUser;
//import com.lvl6.mobsters.dynamo.TaskForUserCompleted;
//import com.lvl6.mobsters.dynamo.TaskForUserOngoing;
//import com.lvl6.mobsters.dynamo.TaskStageForUser;
//import com.lvl6.mobsters.dynamo.User;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupRequestProto;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.Builder;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants.ClanConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants.DownloadableNibConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants.MiniTutorialConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants.MonsterConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants.TaskMapConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupConstants.UserMonsterConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.StartupStatus;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.TutorialConstants;
//import com.lvl6.mobsters.eventproto.EventStartupProto.StartupResponseProto.UpdateStatus;
//import com.lvl6.mobsters.events.EventsToDispatch;
//import com.lvl6.mobsters.events.RequestEvent;
//import com.lvl6.mobsters.events.request.StartupRequestEvent;
//import com.lvl6.mobsters.events.response.StartupResponseEvent;
//import com.lvl6.mobsters.info.Achievement;
//import com.lvl6.mobsters.info.AnimatedSpriteOffset;
//import com.lvl6.mobsters.info.BaseIntPersistentObject;
//import com.lvl6.mobsters.info.BoosterPack;
//import com.lvl6.mobsters.info.EventPersistent;
//import com.lvl6.mobsters.info.ITask;
//import com.lvl6.mobsters.info.Item;
//import com.lvl6.mobsters.info.Monster;
//import com.lvl6.mobsters.info.MonsterBattleDialogue;
//import com.lvl6.mobsters.info.Obstacle;
//import com.lvl6.mobsters.info.PvpLeague;
//import com.lvl6.mobsters.info.StaticUserLevelInfo;
//import com.lvl6.mobsters.info.StructureHospital;
//import com.lvl6.mobsters.info.StructureLab;
//import com.lvl6.mobsters.info.StructureMiniJob;
//import com.lvl6.mobsters.info.StructureResidence;
//import com.lvl6.mobsters.info.StructureResourceGenerator;
//import com.lvl6.mobsters.info.StructureResourceStorage;
//import com.lvl6.mobsters.info.StructureTownHall;
//import com.lvl6.mobsters.info.Task;
//import com.lvl6.mobsters.info.repository.AchievementRepository;
//import com.lvl6.mobsters.info.repository.BoosterPackRepository;
//import com.lvl6.mobsters.info.repository.EventPersistentRepository;
//import com.lvl6.mobsters.info.repository.ItemRepository;
//import com.lvl6.mobsters.info.repository.MonsterBattleDialogueRepository;
//import com.lvl6.mobsters.info.repository.MonsterRepository;
//import com.lvl6.mobsters.info.repository.ObstacleRepository;
//import com.lvl6.mobsters.info.repository.PvpLeagueRepository;
//import com.lvl6.mobsters.info.repository.QuestRepository;
//import com.lvl6.mobsters.info.repository.StaticUserLevelInfoRepository;
//import com.lvl6.mobsters.info.repository.StructureHospitalRepository;
//import com.lvl6.mobsters.info.repository.StructureLabRepository;
//import com.lvl6.mobsters.info.repository.StructureMiniJobRepository;
//import com.lvl6.mobsters.info.repository.StructureResidenceRepository;
//import com.lvl6.mobsters.info.repository.StructureResourceGeneratorRepository;
//import com.lvl6.mobsters.info.repository.StructureResourceStorageRepository;
//import com.lvl6.mobsters.info.repository.StructureTownHallRepository;
//import com.lvl6.mobsters.info.repository.TaskRepository;
//import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolRequest;
//import com.lvl6.mobsters.noneventproto.NoneventInAppPurchaseProto.InAppPurchasePackageProto;
//import com.lvl6.mobsters.noneventproto.NoneventMonsterProto.UserEnhancementItemProto;
//import com.lvl6.mobsters.noneventproto.NoneventQuestProto.FullUserQuestProto;
//import com.lvl6.mobsters.noneventproto.NoneventStaticDataProto.StaticDataProto;
//import com.lvl6.mobsters.noneventproto.NoneventStructureProto.MinimumObstacleProto;
//import com.lvl6.mobsters.noneventproto.NoneventStructureProto.TutorialStructProto;
//import com.lvl6.mobsters.noneventproto.NoneventTaskProto.MinimumUserTaskProto;
//import com.lvl6.mobsters.noneventproto.NoneventTaskProto.TaskStageProto;
//import com.lvl6.mobsters.noneventproto.utils.NoneventAchievementProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventBoosterPackProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventClanProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventEventPersistentProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventMiniJobProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventMonsterProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventPvpProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventQuestProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventStartupProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventStructureProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventTaskProtoSerializer;
//import com.lvl6.mobsters.noneventproto.utils.NoneventUserProtoSerializer;
//import com.lvl6.mobsters.server.ControllerConstants;
//import com.lvl6.mobsters.server.EventController;
//import com.lvl6.mobsters.services.achievement.AchievementService;
//import com.lvl6.mobsters.services.clan.ClanService;
//import com.lvl6.mobsters.utility.common.TimeUtils;
//import com.lvl6.mobsters.services.minijob.MiniJobService;
//import com.lvl6.mobsters.services.monster.MonsterService;
//import com.lvl6.mobsters.services.quest.QuestService;
//import com.lvl6.mobsters.services.task.TaskService;
//import com.lvl6.mobsters.services.user.UserService;
//import com.lvl6.properties.Globals;
//import com.lvl6.properties.IAPValues;
//import com.lvl6.properties.MDCKeys;
//
//@Component
//public class StartupController extends EventController
//{
//	private static Logger LOG = LoggerFactory.getLogger(StartupController.class);
//
//	@Autowired
//	protected Globals globals;
//	
//	@Autowired
//	protected UserService userService;
//	
//	@Autowired
//	protected NoneventUserProtoSerializer noneventUserProtoSerializer;
//
//	@Autowired
//	protected QuestService questService;
//	
//	@Autowired
//	protected QuestRepository questRepository;
//	
//	@Autowired
//	protected NoneventQuestProtoSerializer noneventQuestProtoSerializer;
//	
//	@Autowired
//	protected ClanService clanService;
//	
//	@Autowired
//	protected NoneventClanProtoSerializer noneventClanProtoSerializer;
//	
//	@Autowired
//	protected MonsterService monsterService;
//	
//	@Autowired
//	protected TaskService taskService;
//	
//	@Autowired
//	protected TaskRepository taskRepository;
//	
//	@Autowired
//	protected NoneventTaskProtoSerializer noneventTaskProtoSerializer;
//	
//	@Autowired
//	protected MonsterRepository monsterRepository;
//	
//	@Autowired
//	protected NoneventMonsterProtoSerializer noneventMonsterProtoSerializer;
//	
//	@Autowired
//	protected StaticUserLevelInfoRepository staticUserLevelInfoRepository;
//	
//	@Autowired
//	protected BoosterPackRepository boosterPackRepository;
//	
//	@Autowired
//	protected NoneventBoosterPackProtoSerializer noneventBoosterPackProtoSerializer;
//	
//	@Autowired
//	protected NoneventStructureProtoSerializer noneventStructureProtoSerializer;
//	
//	@Autowired
//	protected StructureResourceGeneratorRepository structureResourceGeneratorRepository;
//	
//	@Autowired
//	protected StructureResourceStorageRepository structureResourceStorageRepository;
//	
//	@Autowired
//	protected StructureHospitalRepository structureHospitalRepository;
//	
//	@Autowired
//	protected StructureResidenceRepository structureResidenceRepository;
//	
//	@Autowired
//	protected StructureTownHallRepository structureTownHallRepository;
//	
//	@Autowired
//	protected StructureLabRepository structureLabRepository;
//	
//	@Autowired
//	protected StructureMiniJobRepository structureMiniJobRepository;
//	
//	@Autowired
//	protected EventPersistentRepository eventPersistentRepository;
//	
//	@Autowired
//	protected NoneventEventPersistentProtoSerializer eventPersistentProtoSerializer;
//	
//	@Autowired
//	protected MonsterBattleDialogueRepository monsterBattleDialogueRepository;
//	
//	@Autowired
//	protected ItemRepository itemRepository;
//	
//	@Autowired
//	protected ObstacleRepository obstacleRepository;
//	
//	@Autowired
//	protected PvpLeagueRepository pvpLeagueRepository;
//	
//	@Autowired
//	protected NoneventPvpProtoSerializer noneventPvpProtoSerializer;
//	
//	@Autowired
//	protected AchievementRepository achievementRepository;
//	
//	@Autowired
//	protected NoneventAchievementProtoSerializer noneventAchievementProtoSerializer;
//	
//	@Autowired
//	protected AchievementService achievementService;
//	
//	@Autowired
//	protected MiniJobService miniJobService;
//	
//	@Autowired
//	protected NoneventMiniJobProtoSerializer noneventMiniJobProtoSerializer;
//	
//	@Autowired
//	protected NoneventStartupProtoSerializer noneventStartupProtoSerializer;
//	
//	// TODO
//	/*
//	 * @Autowired protected EventWriter eventWriter;
//	 */
//
//	public StartupController()
//	{}
//
//	@Override
//	public RequestEvent createRequestEvent()
//	{
//		return new StartupRequestEvent();
//	}
//
//	@Override
//	public EventProtocolRequest getEventType()
//	{
//		return EventProtocolRequest.C_STARTUP_EVENT;
//	}
//
//	@Override
//	protected void processRequestEvent( RequestEvent event, EventsToDispatch eventWriter ) throws Exception
//	{
//		final StartupRequestProto reqProto =
//			((StartupRequestEvent) event).getStartupRequestProto();
//		LOG.info("Prcessing Startup request event");
//
//		final String udid = reqProto.getUdid();
//		String fbId = reqProto.getFbId();
//		boolean freshRestart = reqProto.getIsFreshRestart();
//
//		// the player might be a new player with no user_id yet
//		String userId = null;
//		setMDCProperties(udid, userId); // cassandra version had this
//
//		double tempClientVersionNum = reqProto.getVersionNum() * 10;
//		// TODO: Figure out the right version number to use
//		double tempLatestVersionNum = Globals.VERSION_NUMBER() * 10;
//
//		UpdateStatus updateStatus;
//		// Check version number
//		if ((int) tempClientVersionNum < (int) tempLatestVersionNum
//			&& tempClientVersionNum > 12.5) {
//			updateStatus = UpdateStatus.MAJOR_UPDATE;
//			LOG.info("player has been notified of forced update");
//		} else if (tempClientVersionNum < tempLatestVersionNum) {
//			updateStatus = UpdateStatus.MINOR_UPDATE;
//		} else {
//			updateStatus = UpdateStatus.NO_UPDATE;
//		}
//
//		// prepare to send response back to client
//		StartupResponseProto.Builder resBuilder = StartupResponseProto.newBuilder();
//		resBuilder.setUpdateStatus(updateStatus);
//		resBuilder.setAppStoreURL(Globals.APP_STORE_URL());
//		resBuilder.setReviewPageURL(Globals.REVIEW_PAGE_URL());
//		resBuilder.setReviewPageConfirmationMessage(Globals.REVIEW_PAGE_CONFIRMATION_MESSAGE);
//
//		StartupResponseEvent resEvent = new StartupResponseEvent(udid);
//		resEvent.setTag(event.getTag());
//
//		// Don't fill in other fields if it is a major update
//		StartupStatus startupStatus = StartupStatus.USER_NOT_IN_DB;
//		Date now = TimeUtils.createNow();
//
//		try {
//			if (!UpdateStatus.MAJOR_UPDATE.equals(updateStatus)) {
//				userId = userService.getUserCredentialByFacebookIdOrUdid(fbId, udid);
//				if (StringUtils.hasText(userId)) {
//					startupStatus = StartupStatus.USER_IN_DB;
//					LOG.info("No major update... getting user info");
//					loginExistingUser(resBuilder, userId, now);
//				} else {
//					LOG.info("no user id: tutorial(?) player with udid "
//						+ udid);
//					setAllStaticData(resBuilder, null, false);
//				}
//
//				resBuilder.setStartupStatus(startupStatus);
//				setConstants(resBuilder, startupStatus);
//			}
//			//startup time
//
//		} catch (Exception e) {
//			LOG.error("exception in StartupController processEvent when calling userService", e);
//			resBuilder.setStartupStatus(StartupStatus.USER_NOT_IN_DB);
//			
//		}
//
//		resBuilder.setServerTimeMillis(now.getTime());
//		resEvent.setStartupResponseProto(resBuilder.build());
//
//		// write to client
//		LOG.info("Writing event: "
//			+ resEvent);
//		try {
//			eventWriter.writeEvent(resEvent);
//		} catch (Exception e) {
//			LOG.error("fatal exception in StartupController processRequestEvent", e);
//		}
//
//	}
//
//	// copy pasted from aoc's MiscMethods.java
//	// commenting it out, 1) don't know how to properly get ip, 2) just cause don't know what
//	// these are used for or how they are used
//	public void purgeMDCProperties()
//	{
//		MDC.remove(MDCKeys.UDID);
//		MDC.remove(MDCKeys.PLAYER_ID);
//		// MDC.remove(MDCKeys.IP);
//	}
//
//	public void setMDCProperties( String udid, String playerId )
//	{// , String ip) {
//		purgeMDCProperties();
//		if (udid != null)
//			MDC.put(MDCKeys.UDID, udid);
//		// if (ip != null) MDC.put(MDCKeys.IP, ip);
//		if (playerId != null)
//			MDC.put(MDCKeys.PLAYER_ID.toString(), playerId);
//	}
//	
//	private void loginExistingUser(Builder resBuilder, String userId, Date now) {
//		// TODO: Account for forcelogout
////		StopWatch stopWatch = new StopWatch();
////		stopWatch.start();
//
//		LOG.info("No major update... getting user info");
//		setInProgressAndAvailableQuests(resBuilder, userId);
////		LOG.info("{}ms at setInProgressAndAvailableQuests", stopWatch.getTime());
//		setUserClanInfos(resBuilder, userId);
////		LOG.info("{}ms at setUserClanInfos", stopWatch.getTime());
////		setNotifications(resBuilder, user);
////		LOG.info("{}ms at setNotifications", stopWatch.getTime());
////		setNoticesToPlayers(resBuilder);
////		LOG.info("{}ms at setNoticesToPlayers", stopWatch.getTime());
////		setGroupChatMessages(resBuilder, user);
////		LOG.info("{}ms at groupChatMessages", stopWatch.getTime());
////		setPrivateChatPosts(resBuilder, user, userId);
////		LOG.info("{}ms at privateChatPosts", stopWatch.getTime());
//		setUserMonsterStuff(resBuilder, userId);
////		LOG.info("{}ms at setUserMonsterStuff", stopWatch.getTime());
////		setFacebookAndExtraSlotsStuff(resBuilder, user, userId);
////		LOG.info("{}ms at facebookAndExtraSlotsStuff", stopWatch.getTime());
//		setTaskStuff(resBuilder, userId);
////		LOG.info("{}ms at task stuff", stopWatch.getTime());
//		setAllStaticData(resBuilder, userId, true);
////		LOG.info("{}ms at static data", stopWatch.getTime());
//		setEventStuff(resBuilder, userId);
////		LOG.info("{}ms at eventStuff", stopWatch.getTime());
//		//if server sees that the user is in a pvp battle, decrement user's elo
////		PvpLeagueForUser plfu = pvpBattleStuff(resBuilder, user,
////			userId, freshRestart, now);
////		LOG.info("{}ms at pvpBattleStuff", stopWatch.getTime());
////		pvpBattleHistoryStuff(resBuilder, user, userId);
////		LOG.info("{}ms at pvpBattleHistoryStuff", stopWatch.getTime());
////		setClanRaidStuff(resBuilder, user, userId, now);
////		LOG.info("{}ms at clanRaidStuff", stopWatch.getTime());
//		setAchievementStuff(resBuilder, userId);
////		LOG.info("{}ms at achivementStuff", stopWatch.getTime());
//		setMiniJob(resBuilder, userId);
////		LOG.info("{}ms at miniJobStuff", stopWatch.getTime());
//		
//		//TODO: update the user's last login
//		//send this updated user back to the client
//	}
//	
//	private void setInProgressAndAvailableQuests(Builder resBuilder, String userId) {
//		// get all inProgressAndRedeemedUserQuests which is all userQuests
//		List<QuestForUser> allUserQuests = questService.findByUserId(userId);
//		
//		List<QuestForUser> inProgressQuests = new ArrayList<QuestForUser>();
//		List<Integer> inProgressQuestIds = new ArrayList<Integer>();
//		List<Integer> redeemedQuestIds = new ArrayList<Integer>();
//
//		for (QuestForUser uq : allUserQuests) {
//
//			if (!uq.isRedeemed()) {
//				//unredeemed quest section, could be complete or not
//				inProgressQuests.add(uq);
//				inProgressQuestIds.add(uq.getQuestId());
//			} else {
//				redeemedQuestIds.add(uq.getQuestId());
//			}
//		}
//		
//		// get the QuestJobForUser for ONLY the inProgressQuests
//		Map<Integer, Collection<QuestJobForUser>> questIdToUserQuestJobs = questService
//			.findByUserIdAndQuestIdIn(userId, inProgressQuestIds);
//		
//		Collection<?> configQuests = questRepository.findByIdIn(inProgressQuestIds);
//		
//		Map<Integer, BaseIntPersistentObject> questIdToQuests =
//			ConfigurationDataUtil.mapifyConfigurationData(configQuests);
//
//		//generate the user quests
//		List<FullUserQuestProto> currentUserQuests = noneventQuestProtoSerializer
//			.createFullUserQuestDataLarges(inProgressQuests, questIdToQuests,
//				questIdToUserQuestJobs);
//		resBuilder.addAllUserQuests(currentUserQuests);
//
//		//send the redeemed quest ids
//		resBuilder.addAllRedeemedQuestIds(redeemedQuestIds);
//	}
//	
//	private void setUserClanInfos(Builder resBuilder, String userId) {
//		List<ClanForUser> userClans = clanService.findByUserId(userId);
//		
//		for (ClanForUser cfu : userClans) {
//			resBuilder.addUserClanInfo(
//				noneventClanProtoSerializer.createFullUserClanProtoFromUserClan(cfu));
//		}
//	}
//	
//	/*private void setNoticesToPlayers(Builder resBuilder) {
//		// TODO: Fill in the place holder
//		List<String> notices = null;//StartupStuffRetrieveUtils.getAllActiveAlerts();
//	  	if (null != notices) {
//	  	  for (String notice : notices) {
//	  	    resBuilder.addNoticesToPlayers(notice);
//	  	  }
//	  	}
//	}*/
//	
//	private void setGroupChatMessages(Builder resBuilder, User user) {
//		// TODO: Fill in
//	}
//	
//	private void setUserMonsterStuff(Builder resBuilder, String userId) {
//		List<MonsterForUser> userMonsters= monsterService.getMonstersForUser(userId);
//		
//		if (!CollectionUtils.lacksSubstance(userMonsters))
//		{
//			for (MonsterForUser mfu : userMonsters) {
//				resBuilder.addUsersMonsters(
//					noneventMonsterProtoSerializer.createFullUserMonsterProtoFromUserMonster(mfu));
//			}
//		}
//		
//		//monsters in healing
//		List<MonsterHealingForUser> userMonstersHealing =
//			monsterService.getMonstersInHealingForUser(userId);
//		if (!CollectionUtils.lacksSubstance(userMonstersHealing))
//		{
//			for (MonsterHealingForUser mhfu : userMonstersHealing) {
//				resBuilder.addMonstersHealing(
//					noneventMonsterProtoSerializer.createUserMonsterHealingProto(mhfu));
//			}
//		}
//		
//		//monsters in enhancing
//		List<MonsterEnhancingForUser> userMonstersEnhancing =
//			monsterService.getMonstersInEnhancingForUser(userId);
//		if (!CollectionUtils.lacksSubstance(userMonstersEnhancing))
//		{
//	    	UserEnhancementItemProto baseMonster = null;
//	    	
//	    	List<UserEnhancementItemProto> feeders = new ArrayList<UserEnhancementItemProto>();
//	    	for (MonsterEnhancingForUser mefu : userMonstersEnhancing) {
//	    		UserEnhancementItemProto ueip =
//	    			noneventMonsterProtoSerializer.createUserEnhancementItemProto(mefu);
//	    		
//	    		// TODO: if user has no monsters with null start time
//	    		// (if user has all monsters with a start time), or
//	    		// if user has more than one monster with a null start time
//	    		// store them to history and delete them or something
//	    		
//	    		//search for the monster that is being enhanced, the one with null start time
//	    		Date startTime = mefu.getExpectedStartTime();
//	    		if(null == startTime) {
//	    			//found him
//	    			baseMonster = ueip;
//	    		} else {
//	    			//just a feeder, add him to the list
//	    			feeders.add(ueip);
//	    		}
//	    	}
//			
//	    	resBuilder.setEnhancements(
//	    		noneventMonsterProtoSerializer.createUserEnhancementProto(
//	    			userId,
//	    			baseMonster,
//	    			feeders)
//	    	);
//		}
//		
//		//monsters in evolution
//		List<MonsterEvolvingForUser> userMonstersEvolving =
//			monsterService.getMonstersInEvolution(userId);
//		
//		if (!CollectionUtils.lacksSubstance(userMonstersEvolving))
//		{
//			for (MonsterEvolvingForUser mefu : userMonstersEvolving) {
//				// TODO: NOTE THAT IF MORE THAN ONE EVOLUTION IS ALLLOWED AT A TIME,
//				// THIS METHOD CALL NEEDS TO CHANGE
//				resBuilder.setEvolution(
//					noneventMonsterProtoSerializer.createUserEvolutionProtoFromEvolution(mefu));
//			}
//		}
//	}
//	
//	private void setTaskStuff(Builder resBuilder, String userId) {
//		List<TaskForUserCompleted> tfucList = taskService.getTaskCompletedForUser(userId);
//		for (TaskForUserCompleted tfuc : tfucList) {
//			resBuilder.addCompletedTaskIds(tfuc.getTaskId());
//		}
//		
//		TaskForUserOngoing aTaskForUser = taskService.getUserTaskForUserId(userId);
//		if (null != aTaskForUser) {
//			LOG.warn("user has incompleted task userTask=" + aTaskForUser);
//			setOngoingTask(resBuilder, userId, aTaskForUser);
//		}
//	}
//	
//	private void setOngoingTask(Builder resBuilder, String userId,
//		TaskForUserOngoing aTaskForUser)
//	{
//		try {
//			MinimumUserTaskProto mutp = noneventTaskProtoSerializer
//				.createMinimumUserTaskProto(userId, aTaskForUser);
//			resBuilder.setCurTask(mutp);
//			
//			//create protos for stages
//			List<TaskStageForUser> taskStages = taskService
//				.getTaskStagesForUserWithTaskForUserId(
//					aTaskForUser.getTaskForUserId());
//			
//			//group taskStageForUsers by stage nums because more than one
//			//taskStageForUser with the same stage num means this stage
//			//has more than one monster
//			Map<Integer, List<TaskStageForUser>> stageNumToTsfu =
//				new HashMap<Integer, List<TaskStageForUser>>();
//			for (TaskStageForUser tsfu : taskStages) {
//				int stageNum = tsfu.getStageNum();
//
//				if (!stageNumToTsfu.containsKey(stageNum)) {
//					List<TaskStageForUser> a = new ArrayList<TaskStageForUser>(); 
//					stageNumToTsfu.put(stageNum, a);
//				}
//
//				List<TaskStageForUser> tsfuList = stageNumToTsfu.get(stageNum);
//				tsfuList.add(tsfu);
//			}
//
//			//now that we have grouped all the monsters in their corresponding
//			//task stages, protofy them
//			int taskId = aTaskForUser.getTaskId();
//			for (Integer stageNum : stageNumToTsfu.keySet()) {
//				List<TaskStageForUser> monsters = stageNumToTsfu.get(stageNum);
//
//				TaskStageProto tsp = noneventTaskProtoSerializer.createTaskStageProto(
//					taskId, stageNum, monsters);
//				resBuilder.addCurTaskStages(tsp);
//			}
//				
//		} catch (Exception e) {
//			LOG.error("could not create existing user task, letting it get" +
//		  		" deleted when user starts another task.", e);
//		}
//	}
//	
//	
//	private void setAllStaticData(Builder resBuilder, String userId, boolean userIdSet) {
//		StaticDataProto.Builder sdpb = StaticDataProto.newBuilder();
//		
//		setTasks(sdpb);
//		setMonsters(sdpb);
//		setUserLevelInfo(sdpb);
//		setBoosterPackInfo(sdpb);
//		setStructures(sdpb);
//		setEvents(sdpb);
//		setMonsterDialogue(sdpb);
////	    setClanRaidStuff(sdpb);
//	    setItems(sdpb);
//	    setObstacleStuff(sdpb);
////	    setClanIconStuff(sdpb);
//	    setPvpLeagueStuff(sdpb);
//	    setAchievementStuff(sdpb);
//
//		resBuilder.setStaticDataStuffProto(sdpb.build());
//	}
//	
//	private void setTasks(StaticDataProto.Builder sdpb) {
//		List<Task> tasks = taskRepository.findAll();
//		for (ITask aTask : tasks) {
//			sdpb.addAllTasks(
//				noneventTaskProtoSerializer.createFullTaskProtoFromTask(aTask));
//		}
//	}
//	
//	private void setMonsters(StaticDataProto.Builder sdpb) {
//		List<Monster> monsterList = monsterRepository.findAll();
//		for (Monster monster : monsterList) {
//			noneventMonsterProtoSerializer.createMonsterProto(monster);
//		}
//	}
//	
//	private void setUserLevelInfo(StaticDataProto.Builder sdpb) {
//		List<StaticUserLevelInfo> levelInfoList = staticUserLevelInfoRepository.findAll();
//		for (StaticUserLevelInfo levelInfo : levelInfoList) {
//			sdpb.addSlip(
//				noneventUserProtoSerializer.createStaticUserLevelInfoProto(levelInfo));
//		}
//	}
//	
//	private void setBoosterPackInfo(StaticDataProto.Builder sdpb) {
//		List<BoosterPack> boosterPackList = boosterPackRepository.findAll();
//		for (BoosterPack bp : boosterPackList) {
//			sdpb.addBoosterPacks(
//				noneventBoosterPackProtoSerializer.createBoosterPackProto(bp));
//		}
//	}
//	
//	private void setStructures(StaticDataProto.Builder sdpb) {
//		
//		setGenerators(sdpb);
//		setStorages(sdpb);
//		setHospitals(sdpb);
//	    setResidences(sdpb);
//	    setTownHalls(sdpb);
//	    setLabs(sdpb);
//	    setMiniJobCenters(sdpb);
//	}
//	
//	private void setGenerators(StaticDataProto.Builder sdpb) {
//		List<StructureResourceGenerator> resourceGenerators =
//			structureResourceGeneratorRepository.findAll();
//		for (StructureResourceGenerator srg : resourceGenerators) {
//			sdpb.addAllGenerators(
//				noneventStructureProtoSerializer.createResourceGeneratorProto(srg));
//		}
//	}
//	
//	private void setStorages(StaticDataProto.Builder sdpb) {
//		List<StructureResourceStorage> resourceStorages =
//			structureResourceStorageRepository.findAll();
//		for (StructureResourceStorage srg : resourceStorages) {
//			sdpb.addAllStorages(
//				noneventStructureProtoSerializer.createResourceStorageProto(srg));
//		}
//	}
//
//	private void setHospitals(StaticDataProto.Builder sdpb) {
//		List<StructureHospital> hospitals = structureHospitalRepository.findAll();
//		for (StructureHospital sh : hospitals) {
//			sdpb.addAllHospitals(
//				noneventStructureProtoSerializer.createHospitalProto(sh));
//		}
//	}
//
//	private void setResidences(StaticDataProto.Builder sdpb) {
//		List<StructureResidence> residences = structureResidenceRepository.findAll();
//		for (StructureResidence sr : residences) {
//			sdpb.addAllResidences(
//				noneventStructureProtoSerializer.createResidenceProto(sr));
//		}
//	}
//
//	private void setTownHalls(StaticDataProto.Builder sdpb) {
//		List<StructureTownHall> townHalls = structureTownHallRepository.findAll();
//		for (StructureTownHall sth : townHalls) {
//			sdpb.addAllTownHalls(
//				noneventStructureProtoSerializer.createTownHallProto(sth));
//		}
//	}
//
//	private void setLabs(StaticDataProto.Builder sdpb) {
//		List<StructureLab> labs = structureLabRepository.findAll();
//		for (StructureLab sl : labs) {
//			sdpb.addAllLabs(
//				noneventStructureProtoSerializer.createLabProto(sl));
//		}
//	}
//
//	private void setMiniJobCenters(StaticDataProto.Builder sdpb) {
//		List<StructureMiniJob> miniJobCenters = structureMiniJobRepository.findAll();
//		for (StructureMiniJob smj : miniJobCenters) {
//			sdpb.addAllMiniJobCenters(
//				noneventStructureProtoSerializer.createMiniJobCenterProto(smj));
//		}
//	}
//	
//	private void setEvents(StaticDataProto.Builder sdpb) {
//		List<EventPersistent> persistentEvents = eventPersistentRepository.findAll();
//		for (EventPersistent ep : persistentEvents) {
//			sdpb.addPersistentEvents(
//				eventPersistentProtoSerializer.createPersistentEventProtoFromEvent(ep));
//		}
//		
//	}
//	
//	private void setMonsterDialogue(StaticDataProto.Builder sdpb) {
//		List<MonsterBattleDialogue> dialogues = monsterBattleDialogueRepository.findAll();
//		for (MonsterBattleDialogue mbd : dialogues) {
//			sdpb.addMbds(
//				noneventMonsterProtoSerializer.createMonsterBattleDialogueProto(mbd));
//		}
//	}
//	
//	private void setItems(StaticDataProto.Builder sdpb) {
//		List<Item> items = itemRepository.findAll();
//		
//		for (Item i : items) {
//			sdpb.addItems(
//				noneventQuestProtoSerializer.createItemProtoFromItem(i));
//		}
//	}
//	
//	private void setObstacleStuff(StaticDataProto.Builder sdpb) {
//		List<Obstacle> obstacles = obstacleRepository.findAll();
//		
//		for (Obstacle o : obstacles) {
//			sdpb.addObstacles(
//				noneventStructureProtoSerializer.createObstacleProto(o));
//		}
//	}
//	
//	private void setPvpLeagueStuff(StaticDataProto.Builder sdpb) {
//		List<PvpLeague> pvpLeagues = pvpLeagueRepository.findAll();
//		
//		for (PvpLeague pl : pvpLeagues) {
//			sdpb.addLeagues(
//				noneventPvpProtoSerializer.createPvpLeagueProto(pl));
//		}
//	}
//	
//	private void setAchievementStuff(StaticDataProto.Builder sdpb) {
//		List<Achievement> achievements = achievementRepository.findAll();
//		for (Achievement a : achievements) {
//			sdpb.addAchievements(
//				noneventAchievementProtoSerializer.createAchievementProto(a));
//		}
//	}
//	
//	private void setEventStuff( Builder resBuilder, String userId ) {
//		List<EventPersistentForUser> events = taskService
//			.getUserPersistentEventForUserId(userId);
//		
//		for (EventPersistentForUser epfu : events) {
//			resBuilder.addUserEvents(
//				noneventTaskProtoSerializer.createUserPersistentEventProto(epfu));
//		}
//	}
//	
//	private void setAchievementStuff( Builder resBuilder, String userId) {
//		List<AchievementForUser> userAchievements = achievementService
//			.getAchievementsForUserId(userId);
//		
//		for (AchievementForUser afu : userAchievements) {
//			resBuilder.addUserAchievements(
//				noneventAchievementProtoSerializer.createUserAchievementProto(afu));
//		}
//	}
//	
//	private void setMiniJob( Builder resBuilder, String userId ) {
//		List<MiniJobForUser> userMiniJobs = miniJobService
//			.getMiniJobForUserId(userId);
//		resBuilder.addAllUserMiniJobProtos(
//			noneventMiniJobProtoSerializer
//			.createUserMiniJobProtos(userMiniJobs, null));
//		
//	}
//	
//	
//	private void setConstants( Builder resBuilder,
//		StartupStatus startupStatus )
//	{
//		setStartupConstants();
//		
//		if (StartupStatus.USER_NOT_IN_DB == startupStatus) {
//			setTutorialConstants( resBuilder );
//		}
//	}
//	
//	private void setStartupConstants() {
//		StartupConstants.Builder cb = StartupConstants.newBuilder();
//
//		for (String id : IAPValues.iapPackageNames)
//		{
//			InAppPurchasePackageProto.Builder iapb = InAppPurchasePackageProto.newBuilder();
//			iapb.setImageName(IAPValues.getImageNameForPackageName(id));
//			iapb.setIapPackageId(id);
//
//			int diamondAmt = IAPValues.getDiamondsForPackageName(id);
//			if (diamondAmt > 0) {
//				iapb.setCurrencyAmount(diamondAmt);
//			} else {
//				int coinAmt = IAPValues.getCoinsForPackageName(id);
//				iapb.setCurrencyAmount(coinAmt);
//			}
//			cb.addInAppPurchasePackages(iapb.build());
//		}
//		
//	    cb.setMaxLevelForUser(ControllerConstants.USER__MAX_LEVEL);
//	    cb.setMaxNumOfSingleStruct(ControllerConstants.PURCHASE_NORM_STRUCTURE__MAX_NUM_OF_CERTAIN_STRUCTURE);
//	    
//	    if (ControllerConstants.STARTUP__ANIMATED_SPRITE_OFFSETS != null)
//	    {
//	    	for (int i = 0; i < ControllerConstants.STARTUP__ANIMATED_SPRITE_OFFSETS.length; i++) {
//	    		AnimatedSpriteOffset aso = ControllerConstants.STARTUP__ANIMATED_SPRITE_OFFSETS[i];
//	    		cb.addAnimatedSpriteOffsets(
//	    			noneventStartupProtoSerializer
//	    			.createAnimatedSpriteOffsetProtoFromAnimatedSpriteOffset(aso));
//	    	}
//	    }
//
//	    cb.setMinNameLength(ControllerConstants.USER_CREATE__MIN_NAME_LENGTH);
//	    cb.setMaxNameLength(ControllerConstants.USER_CREATE__MAX_NAME_LENGTH);
//	    cb.setMaxLengthOfChatString(ControllerConstants.SEND_GROUP_CHAT__MAX_LENGTH_OF_CHAT_STRING);
//
//	    ClanConstants.Builder clanConstantsBuilder = ClanConstants.newBuilder();
//	    clanConstantsBuilder.setMaxCharLengthForClanDescription(ControllerConstants.CREATE_CLAN__MAX_CHAR_LENGTH_FOR_CLAN_DESCRIPTION);
//	    clanConstantsBuilder.setMaxCharLengthForClanName(ControllerConstants.CREATE_CLAN__MAX_CHAR_LENGTH_FOR_CLAN_NAME);
//	    clanConstantsBuilder.setCoinPriceToCreateClan(ControllerConstants.CREATE_CLAN__COIN_PRICE_TO_CREATE_CLAN);
//	    clanConstantsBuilder.setMaxCharLengthForClanTag(ControllerConstants.CREATE_CLAN__MAX_CHAR_LENGTH_FOR_CLAN_TAG);
//	    clanConstantsBuilder.setMaxClanSize(ControllerConstants.CLAN__MAX_NUM_MEMBERS);
//	    cb.setClanConstants(clanConstantsBuilder.build());
//
//
//	    DownloadableNibConstants.Builder dncb = DownloadableNibConstants.newBuilder();
////	    dncb.setMapNibName(ControllerConstants.NIB_NAME__TRAVELING_MAP);
////	    dncb.setExpansionNibName(ControllerConstants.NIB_NAME__EXPANSION);
////	    dncb.setGoldShoppeNibName(ControllerConstants.NIB_NAME__GOLD_SHOPPE);
//	    cb.setDownloadableNibConstants(dncb.build());
//	    cb.setLevelToShowRateUsPopup(ControllerConstants.USER__LEVEL_TO_DISPLAY_RATE_US_POPUP);
//	    
//	    UserMonsterConstants.Builder umcb = UserMonsterConstants.newBuilder();
//	    umcb.setMaxNumTeamSlots(ControllerConstants.MONSTER_FOR_USER__MAX_TEAM_SIZE);
//	    umcb.setInitialMaxNumMonsterLimit(ControllerConstants.MONSTER_FOR_USER__INITIAL_MAX_NUM_MONSTER_LIMIT);
//	    cb.setUserMonsterConstants(umcb.build());
//	    
//	    MonsterConstants.Builder mcb = MonsterConstants.newBuilder();
//	    mcb.setCashPerHealthPoint(ControllerConstants.MONSTER__CASH_PER_HEALTH_POINT);
//	    mcb.setSecondsToHealPerHealthPoint(ControllerConstants.MONSTER__SECONDS_TO_HEAL_PER_HEALTH_POINT);
//	    mcb.setElementalStrength(ControllerConstants.MONSTER__ELEMENTAL_STRENGTH);
//	    mcb.setElementalWeakness(ControllerConstants.MONSTER__ELEMENTAL_WEAKNESS);
//	    mcb.setOilPerMonsterLevel(ControllerConstants.MONSTER__OIL_PER_MONSTER_LEVEL);
//	    cb.setMonsterConstants(mcb.build());
//
//	    cb.setMinutesPerGem(ControllerConstants.MINUTES_PER_GEM);
//	    cb.setPvpRequiredMinLvl(ControllerConstants.PVP__REQUIRED_MIN_LEVEL);
//	    cb.setGemsPerResource(ControllerConstants.GEMS_PER_RESOURCE);
//	    cb.setContinueBattleGemCostMultiplier(ControllerConstants.BATTLE__CONTINUE_GEM_COST_MULTIPLIER);
//	    cb.setBattleRunAwayBasePercent(ControllerConstants.BATTLE__RUN_AWAY_BASE_PERCENT);
//	    cb.setBattleRunAwayIncrement(ControllerConstants.BATTLE__RUN_AWAY_INCREMENT);
//
//	    cb.setAddAllFbFriends(globals.isAddAllFbFriends());
//	    
//	    cb.setMiniTuts(
//	    	createMiniTutorialConstantsProto()
//	    );
//
//	    cb.setMaxObstacles(ControllerConstants.OBSTACLE__MAX_OBSTACLES);
//	    cb.setMinutesPerObstacle(ControllerConstants.OBSTACLE__MINUTES_PER_OBSTACLE);
//	    
//	    cb.setTaskMapConstants(createTaskMapConstants());
//	}
//
//	private MiniTutorialConstants createMiniTutorialConstantsProto()
//	{
//		MiniTutorialConstants.Builder mtcb = MiniTutorialConstants.newBuilder();
//		mtcb.setMiniTutorialTaskId(ControllerConstants.MINI_TUTORIAL__GUARANTEED_MONSTER_DROP_TASK_ID);
//		mtcb.setGuideMonsterId(ControllerConstants.TUTORIAL__GUIDE_MONSTER_ID);
//
//		return mtcb.build();
//	}
//	
//	private TaskMapConstants createTaskMapConstants() {
//		TaskMapConstants.Builder mapConstants = TaskMapConstants.newBuilder();
//	    mapConstants.setMapSectionImagePrefix(ControllerConstants.TASK_MAP__SECTION_IMAGE_PREFIX);
//	    mapConstants.setMapNumberOfSections(ControllerConstants.TASK_MAP__NUMBER_OF_SECTIONS);
//	    mapConstants.setMapSectionHeight(ControllerConstants.TASK_MAP__SECTION_HEIGHT);
//	    mapConstants.setMapTotalHeight(ControllerConstants.TASK_MAP__TOTAL_HEIGHT);
//	    mapConstants.setMapTotalWidth(ControllerConstants.TASK_MAP__TOTAL_WIDTH);
//	    
//	    return mapConstants.build();
//	}
//
//	private void setTutorialConstants( Builder resBuilder ) {
//		TutorialConstants.Builder tcb = TutorialConstants.newBuilder();
//
//	    tcb.setStartingMonsterId(ControllerConstants.TUTORIAL__STARTING_MONSTER_ID);
//	    tcb.setGuideMonsterId(ControllerConstants.TUTORIAL__GUIDE_MONSTER_ID);
//	    tcb.setEnemyMonsterId(ControllerConstants.TUTORIAL__ENEMY_MONSTER_ID_ONE);
//	    tcb.setEnemyMonsterIdTwo(ControllerConstants.TUTORIAL__ENEMY_MONSTER_ID_TWO);
//	    tcb.setEnemyBossMonsterId(ControllerConstants.TUTORIAL__ENEMY_BOSS_MONSTER_ID);
//	    tcb.setMarkZMonsterId(ControllerConstants.TUTORIAL__MARK_Z_MONSTER_ID);
//
//	    for (int i = 0; i < ControllerConstants.TUTORIAL__EXISTING_BUILDING_IDS.length; i++)
//	    {
//	    	int structId = ControllerConstants.TUTORIAL__EXISTING_BUILDING_IDS[i];
//	    	float posX = ControllerConstants.TUTORIAL__EXISTING_BUILDING_X_POS[i];
//	    	float posY = ControllerConstants.TUTORIAL__EXISTING_BUILDING_Y_POS[i];
//
//	    	TutorialStructProto tsp = noneventStructureProtoSerializer
//	    		.createTutorialStructProto(structId, posX, posY);
//	    	tcb.addTutorialStructures(tsp);
//	    }
//
//	    List<Integer> structureIdsToBeBuilt = 
//	        Arrays.asList(ControllerConstants.TUTORIAL__STRUCTURE_IDS_TO_BUILD);
//	    tcb.addAllStructureIdsToBeBuillt(structureIdsToBeBuilt);
//
//	    tcb.setCashInit(ControllerConstants.TUTORIAL__INIT_CASH);
//	    tcb.setOilInit(ControllerConstants.TUTORIAL__INIT_OIL);
//	    tcb.setGemsInit(ControllerConstants.TUTORIAL__INIT_GEMS);
//	    
//	    int orientation = 1;
//	    for (int i = 0; i < ControllerConstants.TUTORIAL__INIT_OBSTACLE_ID.length; i++) {
//	    	int obstacleId = ControllerConstants.TUTORIAL__INIT_OBSTACLE_ID[i];
//	    	float posX = ControllerConstants.TUTORIAL__INIT_OBSTACLE_X[i];
//	    	float posY = ControllerConstants.TUTORIAL__INIT_OBSTACLE_Y[i];
//	    	
//	    	MinimumObstacleProto mopb = noneventStructureProtoSerializer
//	    		.createMinimumObstacleProto(
//	    			obstacleId, posX, posY, orientation);
//	    	tcb.addTutorialObstacles(mopb);
////	    	log.info("mopb=" + mopb);
//	    }
//	    
//	    resBuilder.setTutorialConstants(tcb.build());
//	}
//	
//	void setUserService( UserService userService )
//	{
//		this.userService = userService;
//	}
//
//	void setNoneventUserProtoSerializer(
//		NoneventUserProtoSerializer noneventUserProtoSerializer )
//	{
//		this.noneventUserProtoSerializer = noneventUserProtoSerializer;
//	}
//
//	void setQuestService( QuestService questService )
//	{
//		this.questService = questService;
//	}
//
//	void setQuestRepository( QuestRepository questRepository )
//	{
//		this.questRepository = questRepository;
//	}
//
//	void setNoneventQuestProtoSerializer(
//		NoneventQuestProtoSerializer noneventQuestProtoSerializer )
//	{
//		this.noneventQuestProtoSerializer = noneventQuestProtoSerializer;
//	}
//
//	void setClanService( ClanService clanService )
//	{
//		this.clanService = clanService;
//	}
//
//	void setNoneventClanProtoSerializer(
//		NoneventClanProtoSerializer noneventClanProtoSerializer )
//	{
//		this.noneventClanProtoSerializer = noneventClanProtoSerializer;
//	}
//
//	void setMonsterService( MonsterService monsterService )
//	{
//		this.monsterService = monsterService;
//	}
//
//	void setTaskRepository( TaskRepository taskRepository )
//	{
//		this.taskRepository = taskRepository;
//	}
//
//	void setNoneventTaskProtoSerializer(
//		NoneventTaskProtoSerializer noneventTaskProtoSerializer )
//	{
//		this.noneventTaskProtoSerializer = noneventTaskProtoSerializer;
//	}
//
//	void setMonsterRepository( MonsterRepository monsterRepository )
//	{
//		this.monsterRepository = monsterRepository;
//	}
//
//	void setNoneventMonsterProtoSerializer(
//		NoneventMonsterProtoSerializer noneventMonsterProtoSerializer )
//	{
//		this.noneventMonsterProtoSerializer = noneventMonsterProtoSerializer;
//	}
//
//	void setStaticUserLevelInfoRepository(
//		StaticUserLevelInfoRepository staticUserLevelInfoRepository )
//	{
//		this.staticUserLevelInfoRepository = staticUserLevelInfoRepository;
//	}
//
//	void setBoosterPackRepository( BoosterPackRepository boosterPackRepository )
//	{
//		this.boosterPackRepository = boosterPackRepository;
//	}
//
//	void setNoneventBoosterPackProtoSerializer(
//		NoneventBoosterPackProtoSerializer noneventBoosterPackProtoSerializer )
//	{
//		this.noneventBoosterPackProtoSerializer = noneventBoosterPackProtoSerializer;
//	}
//
//	void setNoneventStructureProtoSerializer(
//		NoneventStructureProtoSerializer noneventStructureProtoSerializer )
//	{
//		this.noneventStructureProtoSerializer = noneventStructureProtoSerializer;
//	}
//
//	void setStructureResourceGeneratorRepository(
//		StructureResourceGeneratorRepository structureResourceGeneratorRepository )
//	{
//		this.structureResourceGeneratorRepository = structureResourceGeneratorRepository;
//	}
//
//	void setStructureResourceStorageRepository(
//		StructureResourceStorageRepository structureResourceStorageRepository )
//	{
//		this.structureResourceStorageRepository = structureResourceStorageRepository;
//	}
//
//	void setStructureHospitalRepository(
//		StructureHospitalRepository structureHospitalRepository )
//	{
//		this.structureHospitalRepository = structureHospitalRepository;
//	}
//
//	void setStructureResidenceRepository(
//		StructureResidenceRepository structureResidenceRepository )
//	{
//		this.structureResidenceRepository = structureResidenceRepository;
//	}
//
//	void setStructureTownHallRepository(
//		StructureTownHallRepository structureTownHallRepository )
//	{
//		this.structureTownHallRepository = structureTownHallRepository;
//	}
//
//	void setStructureLabRepository( StructureLabRepository structureLabRepository )
//	{
//		this.structureLabRepository = structureLabRepository;
//	}
//
//	void setStructureMiniJobRepository( StructureMiniJobRepository structureMiniJobRepository )
//	{
//		this.structureMiniJobRepository = structureMiniJobRepository;
//	}
//
//	void setEventPersistentRepository( EventPersistentRepository eventPersistentRepository )
//	{
//		this.eventPersistentRepository = eventPersistentRepository;
//	}
//
//	void setEventPersistentProtoSerializer(
//		NoneventEventPersistentProtoSerializer eventPersistentProtoSerializer )
//	{
//		this.eventPersistentProtoSerializer = eventPersistentProtoSerializer;
//	}
//
//	void setMonsterBattleDialogueRepository(
//		MonsterBattleDialogueRepository monsterBattleDialogueRepository )
//	{
//		this.monsterBattleDialogueRepository = monsterBattleDialogueRepository;
//	}
//
//	void setItemRepository( ItemRepository itemRepository )
//	{
//		this.itemRepository = itemRepository;
//	}
//
//	void setObstacleRepository( ObstacleRepository obstacleRepository )
//	{
//		this.obstacleRepository = obstacleRepository;
//	}
//
//	void setPvpLeagueRepository( PvpLeagueRepository pvpLeagueRepository )
//	{
//		this.pvpLeagueRepository = pvpLeagueRepository;
//	}
//
//	void setNoneventPvpProtoSerializer( NoneventPvpProtoSerializer noneventPvpProtoSerializer )
//	{
//		this.noneventPvpProtoSerializer = noneventPvpProtoSerializer;
//	}
//
//	void setAchievementRepository( AchievementRepository achievementRepository )
//	{
//		this.achievementRepository = achievementRepository;
//	}
//
//	void setNoneventAchievementProtoSerializer(
//		NoneventAchievementProtoSerializer noneventAchievementProtoSerializer )
//	{
//		this.noneventAchievementProtoSerializer = noneventAchievementProtoSerializer;
//	}
//
//	void setTaskService( TaskService taskService )
//	{
//		this.taskService = taskService;
//	}
//
//	void setAchievementService( AchievementService achievementService )
//	{
//		this.achievementService = achievementService;
//	}
//
//	void setMiniJobService( MiniJobService miniJobService )
//	{
//		this.miniJobService = miniJobService;
//	}
//
//	void setNoneventMiniJobProtoSerializer(
//		NoneventMiniJobProtoSerializer noneventMiniJobProtoSerializer )
//	{
//		this.noneventMiniJobProtoSerializer = noneventMiniJobProtoSerializer;
//	}
//
//	void setNoneventStartupProtoSerializer(
//		NoneventStartupProtoSerializer noneventStartupProtoSerializer )
//	{
//		this.noneventStartupProtoSerializer = noneventStartupProtoSerializer;
//	}
//
//}
