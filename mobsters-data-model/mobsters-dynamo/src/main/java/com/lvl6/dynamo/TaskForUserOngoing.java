package com.lvl6.dynamo;

import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="TaskForUserOngoing")
public class TaskForUserOngoing {



	private String id;
	


	private int userId;
	private int taskId;	
	private int expGained;
	private int cashGained;
	private int oilGained;
	private int numRevives;

	private Date startDate;
	private int taskStageId;	
	public TaskForUserOngoing(){}
	public TaskForUserOngoing( int userId, int taskId, int expGained,
			int cashGained, int oilGained, int numRevives, Date startDate,
			int taskStageId) {
		super();
		this.userId = userId;
		this.taskId = taskId;
		this.expGained = expGained;
		this.cashGained = cashGained;
		this.oilGained = oilGained;
		this.numRevives = numRevives;
		this.startDate = startDate;
		this.taskStageId = taskStageId;
	}




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	public int getUserId() {		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getExpGained() {
		return expGained;
	}

	public void setExpGained(int expGained) {
		this.expGained = expGained;
	}

	public int getCashGained() {
		return cashGained;
	}

	public void setCashGained(int cashGained) {
		this.cashGained = cashGained;
	}

	public int getOilGained() {
		return oilGained;
	}

	public void setOilGained(int oilGained) {
		this.oilGained = oilGained;
	}

	public int getNumRevives() {
		return numRevives;
	}

	public void setNumRevives(int numRevives) {
		this.numRevives = numRevives;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getTaskStageId() {
		return taskStageId;
	}

	public void setTaskStageId(int taskStageId) {
		this.taskStageId = taskStageId;
	}

	@Override
	public String toString() {
		return "TaskForUserOngoing [id=" + id + ", userId=" + userId
				+ ", taskId=" + taskId + ", expGained=" + expGained
				+ ", cashGained=" + cashGained + ", oilGained=" + oilGained
				+ ", numRevives=" + numRevives + ", startDate=" + startDate
				+ ", taskStageId=" + taskStageId + "]";
	}

}
