package com.lvl6.dynamo;
import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="UserDailyBonusRewardHistory")
public class UserDailyBonusRewardHistory {



	private String id;
	private Long version;

	private int userId;
	private int currencyRewarded;
	private boolean isCoins;
	private int boosterPackIdRewarded;
	private int equipIdRewarded;
	private int nthConsecutiveDay;
	private Date dateAwarded;  
	public UserDailyBonusRewardHistory(){}
  public UserDailyBonusRewardHistory(int id, int userId, int currencyRewarded,
      boolean isCoins, int boosterPackIdRewarded, int equipIdRewarded,
      int nthConsecutiveDay, Date dateAwarded) {
    super();
    this.userId = userId;
    this.currencyRewarded = currencyRewarded;
    this.isCoins = isCoins;
    this.boosterPackIdRewarded = boosterPackIdRewarded;
    this.equipIdRewarded = equipIdRewarded;
    this.nthConsecutiveDay = nthConsecutiveDay;
    this.dateAwarded = dateAwarded;
  }




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


  public int getUserId() {    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getCurrencyRewarded() {
    return currencyRewarded;
  }

  public void setCurrencyRewarded(int currencyRewarded) {
    this.currencyRewarded = currencyRewarded;
  }

  public boolean isCoins() {
    return isCoins;
  }

  public void setCoins(boolean isCoins) {
    this.isCoins = isCoins;
  }

  public int getBoosterPackIdRewarded() {
    return boosterPackIdRewarded;
  }

  public void setBoosterPackIdRewarded(int boosterPackIdRewarded) {
    this.boosterPackIdRewarded = boosterPackIdRewarded;
  }

  public int getEquipIdRewarded() {
    return equipIdRewarded;
  }

  public void setEquipIdRewarded(int equipIdRewarded) {
    this.equipIdRewarded = equipIdRewarded;
  }

  public int getNthConsecutiveDay() {
    return nthConsecutiveDay;
  }

  public void setNthConsecutiveDay(int nthConsecutiveDay) {
    this.nthConsecutiveDay = nthConsecutiveDay;
  }

  public Date getDateAwarded() {
    return dateAwarded;
  }

  public void setDateAwarded(Date dateAwarded) {
    this.dateAwarded = dateAwarded;
  }

  @Override
  public String toString() {
    return "UserDailyBonusRewardHistory [id=" + id + ", userId=" + userId
        + ", currencyRewarded=" + currencyRewarded + ", isCoins=" + isCoins
        + ", boosterPackIdRewarded=" + boosterPackIdRewarded
        + ", equipIdRewarded=" + equipIdRewarded + ", nthConsecutiveDay="
        + nthConsecutiveDay + ", dateAwarded=" + dateAwarded + "]";
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
		UserDailyBonusRewardHistory other = (UserDailyBonusRewardHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}