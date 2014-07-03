package com.lvl6.mobsters.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="QuestJobForUser")
public class QuestJobForUser {



//	private String id;
	private String userId;
	private Long version;


	private int questId; //not really necessary but eh
	private int questJobId;
	private boolean isComplete;
	private int progress;	
	public QuestJobForUser() {
		super();
	}
	
	public QuestJobForUser(String userId, int questId, int questJobId,
			boolean isComplete, int progress) {
		super();
		this.userId = userId;
		this.questId = questId;
		this.questJobId = questJobId;
		this.isComplete = isComplete;
		this.progress = progress;
	}

	/*
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}
	*/

	@DynamoDBHashKey(attributeName = "userId")
	public String getUserId() {		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBRangeKey(attributeName = "questJobId")
	public int getQuestJobId() {
		return questJobId;
	}
	
	public void setQuestJobId(int questJobId) {
		this.questJobId = questJobId;
	}
	
	public void setQuestJobId(Integer questJobId) {
		this.questJobId = questJobId.intValue();
	}
	
	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}


	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "QuestJobForUser [userId=" + userId + ", questId=" + questId
				+ ", questJobId=" + questJobId + ", isComplete=" + isComplete
				+ ", progress=" + progress + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime
			* result
			+ questJobId;
		result = prime
			* result
			+ ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestJobForUser other = (QuestJobForUser) obj;
		if (questJobId != other.questJobId)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
}
