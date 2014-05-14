package com.lvl6.dynamo;
//this class is analogous to a task

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="ClanRaidStage")
public class ClanRaidStage {



	private String id;
	private Long version;

	
	private int clanRaidId;
	private int durationMinutes;
	private int stageNum;
	private String name;	
	//sum of all monster healths for this stage
	//not actually a column in the table

	private int stageHealth;	
	public ClanRaidStage(){}
	public ClanRaidStage(int id, int clanRaidId, int durationMinutes,
			int stageNum, String name) {
		super();
		this.clanRaidId = clanRaidId;
		this.durationMinutes = durationMinutes;
		this.stageNum = stageNum;
		this.name = name;
	}




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


	public int getClanRaidId() {		return clanRaidId;
	}

	public void setClanRaidId(int clanRaidId) {
		this.clanRaidId = clanRaidId;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public int getStageNum() {
		return stageNum;
	}

	public void setStageNum(int stageNum) {
		this.stageNum = stageNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getStageHealth() {
		return stageHealth;
	}

	public void setStageHealth(int stageHealth) {
		this.stageHealth = stageHealth;
	}

	@Override
	public String toString() {
		return "ClanRaidStage [id=" + id + ", clanRaidId=" + clanRaidId
				+ ", durationMinutes=" + durationMinutes + ", stageNum=" + stageNum
				+ ", name=" + name + ", stageHealth=" + stageHealth + "]";
	}

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
		ClanRaidStage other = (ClanRaidStage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}