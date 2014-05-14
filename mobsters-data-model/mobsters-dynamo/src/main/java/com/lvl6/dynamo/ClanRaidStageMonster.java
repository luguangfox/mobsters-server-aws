package com.lvl6.dynamo;
//this class is analogous to task_stage_monster
//multiple monsters can point to stage (all must spawn)

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="ClanRaidStageMonster")
public class ClanRaidStageMonster {



	private String id;
	private Long version;

	
	private int clanRaidStageId;
	private int monsterId;
	private int monsterHp; //instead of specifying monster level info, specify hp here
	private int minDmg;
	private int maxDmg;
	private int monsterNum;	
	public ClanRaidStageMonster(){}
	public ClanRaidStageMonster(int id, int clanRaidStageId, int monsterId,
			int monsterHp, int minDmg, int maxDmg, int monsterNum) {
		super();
		this.clanRaidStageId = clanRaidStageId;
		this.monsterId = monsterId;
		this.monsterHp = monsterHp;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.monsterNum = monsterNum;
	}




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


	public int getClanRaidStageId() {		return clanRaidStageId;
	}

	public void setClanRaidStageId(int clanRaidStageId) {
		this.clanRaidStageId = clanRaidStageId;
	}

	public int getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}

	public int getMonsterHp() {
		return monsterHp;
	}

	public void setMonsterHp(int monsterHp) {
		this.monsterHp = monsterHp;
	}

	public int getMinDmg() {
		return minDmg;
	}

	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	public int getMaxDmg() {
		return maxDmg;
	}

	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public int getMonsterNum() {
		return monsterNum;
	}

	public void setMonsterNum(int monsterNum) {
		this.monsterNum = monsterNum;
	}

	@Override
	public String toString() {
		return "ClanRaidStageMonster [id=" + id + ", clanRaidStageId="
				+ clanRaidStageId + ", monsterId=" + monsterId + ", monsterHp="
				+ monsterHp + ", minDmg=" + minDmg + ", maxDmg=" + maxDmg
				+ ", monsterNum=" + monsterNum + "]";
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
		ClanRaidStageMonster other = (ClanRaidStageMonster) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}