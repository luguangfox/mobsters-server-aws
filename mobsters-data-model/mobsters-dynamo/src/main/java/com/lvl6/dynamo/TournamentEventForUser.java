package com.lvl6.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="TournamentEventForUser")
public class TournamentEventForUser {



	private String id;
	private Long version;
	
	private int tournamentEventId;
	private int userId;
	private int battlesWon;
	private int battlesLost;
	private int battlesFled;  
	public TournamentEventForUser(){}
  public TournamentEventForUser(int tournamentEventId, int userId, int battlesWon,
      int battlesLost, int battlesFled) {
    super();
    this.tournamentEventId = tournamentEventId;
    this.userId = userId;
    this.battlesWon = battlesWon;
    this.battlesLost = battlesLost;
    this.battlesFled = battlesFled;
  }

	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


  public int getTournamentEventId() {    return tournamentEventId;
  }
  public void setTournamentEventId(int tournamentEventId) {
    this.tournamentEventId = tournamentEventId;
  }
  public int getUserId() {
    return userId;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }
  public int getBattlesWon() {
    return battlesWon;
  }
  public void setBattlesWon(int battlesWon) {
    this.battlesWon = battlesWon;
  }
  public int getBattlesLost() {
    return battlesLost;
  }
  public void setBattlesLost(int battlesLost) {
    this.battlesLost = battlesLost;
  }
  public int getBattlesFled() {
    return battlesFled;
  }
  public void setBattlesFled(int battlesFled) {
    this.battlesFled = battlesFled;
  }
  @Override
  public String toString() {
    return "BossEvent [tournamentEventId=" + tournamentEventId + ", userId="
        + userId + ", battlesWon=" + battlesWon + ", battlesLost=" + battlesLost
        + ", battlesFled=" + battlesFled + "]";
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
		TournamentEventForUser other = (TournamentEventForUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}