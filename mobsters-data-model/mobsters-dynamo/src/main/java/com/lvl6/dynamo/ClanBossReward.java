package com.lvl6.dynamo;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="ClanBossReward")
public class ClanBossReward {



	private String id;
	

	private int clanBossId;
	private int equipId;  
	public ClanBossReward(){}
  public ClanBossReward(int id, int clanBossId, int equipId) {
    super();
    this.clanBossId = clanBossId;
    this.equipId = equipId;
  }




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


  public int getClanBossId() {    return clanBossId;
  }

  public void setClanBossId(int clanBossId) {
    this.clanBossId = clanBossId;
  }

  public int getEquipId() {
    return equipId;
  }

  public void setEquipId(int equipId) {
    this.equipId = equipId;
  }

  @Override
  public String toString() {
    return "ClanBossReward [id=" + id + ", clanBossId=" + clanBossId
        + ", equipId=" + equipId + "]";
  }
  
}
