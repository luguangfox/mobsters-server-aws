package com.lvl6.mobsters.dynamo;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="ClanEventPersistentForClan")
public class ClanEventPersistentForClan {



	private String id;
	private Long version;

	
	private int clanId;
	private int clanEventPersistentId; //not really needed, but oh well
	private int crId; //clan raid id
	private int crsId; //clan raid stage id
	private Date stageStartTime; // refers to time clan started a daily event
	private int crsmId; //clan raid stage monster id
	private Date stageMonsterStartTime; //differentiate attacks across different stage monsters  
	public ClanEventPersistentForClan(){}
	public ClanEventPersistentForClan(int clanId, int clanEventPersistentId,
			int crId, int crsId, Date stageStartTime, int crsmId,
			Date stageMonsterStartTime) {
		super();
		this.clanId = clanId;
		this.clanEventPersistentId = clanEventPersistentId;
		this.crId = crId;
		this.crsId = crsId;
		this.stageStartTime = stageStartTime;
		this.crsmId = crsmId;
		this.stageMonsterStartTime = stageMonsterStartTime;
	}


	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


	public int getClanId() {		return clanId;
	}

	public void setClanId(int clanId) {
		this.clanId = clanId;
	}

	public int getClanEventPersistentId() {
		return clanEventPersistentId;
	}

	public void setClanEventPersistentId(int clanEventPersistentId) {
		this.clanEventPersistentId = clanEventPersistentId;
	}

	public int getCrId() {
		return crId;
	}

	public void setCrId(int crId) {
		this.crId = crId;
	}

	public int getCrsId() {
		return crsId;
	}

	public void setCrsId(int crsId) {
		this.crsId = crsId;
	}

	public Date getStageStartTime() {
		return stageStartTime;
	}

	public void setStageStartTime(Date stageStartTime) {
		this.stageStartTime = stageStartTime;
	}

	public int getCrsmId() {
		return crsmId;
	}

	public void setCrsmId(int crsmId) {
		this.crsmId = crsmId;
	}

	public Date getStageMonsterStartTime() {
		return stageMonsterStartTime;
	}

	public void setStageMonsterStartTime(Date stageMonsterStartTime) {
		this.stageMonsterStartTime = stageMonsterStartTime;
	}

	@Override
	public String toString() {
		return "ClanEventPersistentForClan [clanId=" + clanId
				+ ", clanEventPersistentId=" + clanEventPersistentId + ", crId=" + crId
				+ ", crsId=" + crsId + ", stageStartTime=" + stageStartTime
				+ ", crsmId=" + crsmId + ", stageMonsterStartTime="
				+ stageMonsterStartTime + "]";
	}

/*	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClanEventPersistentForClan other = (ClanEventPersistentForClan) obj;
		if (clanEventPersistentId != other.clanEventPersistentId)
			return false;
		if (clanId != other.clanId)
			return false;
		if (crId != other.crId)
			return false;
		if (crsId != other.crsId)
			return false;
		if (crsmId != other.crsmId)
			return false;
		if (stageMonsterStartTime == null) {
			if (other.stageMonsterStartTime != null)
				return false;
		} else if (!stageMonsterStartTime.equals(other.stageMonsterStartTime))
			return false;
		if (stageStartTime == null) {
			if (other.stageStartTime != null)
				return false;
		} else if (!stageStartTime.equals(other.stageStartTime))
			return false;
		return true;
	}*/
	
 	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClanEventPersistentForClan other = (ClanEventPersistentForClan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}