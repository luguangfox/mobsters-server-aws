package com.lvl6.dynamo;
import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="UserLockBoxEvent")
public class UserLockBoxEvent {



	private String id;
	private Long version;
	
	private int lockBoxId;
	private int userId;
	private int numLockBoxes;
	private int numTimesCompleted;
	private Date lastPickTime;
	private boolean hasBeenRedeemed;

	public UserLockBoxEvent(){}
  public UserLockBoxEvent(int lockBoxId, int userId, int numLockBoxes,
      int numTimesCompleted, Date lastPickTime, boolean hasBeenRedeemed) {
    super();
    this.lockBoxId = lockBoxId;
    this.userId = userId;
    this.numLockBoxes = numLockBoxes;
    this.numTimesCompleted = numTimesCompleted;
    this.lastPickTime = lastPickTime;
    this.hasBeenRedeemed = hasBeenRedeemed;
  }


	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


  public int getLockBoxId() {    return lockBoxId;
  }

  public void setLockBoxId(int lockBoxId) {
    this.lockBoxId = lockBoxId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getNumLockBoxes() {
    return numLockBoxes;
  }

  public void setNumLockBoxes(int numLockBoxes) {
    this.numLockBoxes = numLockBoxes;
  }

  public int getNumTimesCompleted() {
    return numTimesCompleted;
  }

  public void setNumTimesCompleted(int numTimesCompleted) {
    this.numTimesCompleted = numTimesCompleted;
  }

  public Date getLastPickTime() {
    return lastPickTime;
  }

  public void setLastPickTime(Date lastPickTime) {
    this.lastPickTime = lastPickTime;
  }
  
  public boolean isHasBeenRedeemed() {
    return hasBeenRedeemed;
  }

  public void setHasBeenRedeemed(boolean hasBeenRedeemed) {
    this.hasBeenRedeemed = hasBeenRedeemed;
  }

  @Override
  public String toString() {
    return "UserLockBoxEvent [lockBoxId=" + lockBoxId + ", userId=" + userId
        + ", numLockBoxes=" + numLockBoxes + ", numTimesCompleted="
        + numTimesCompleted + ", lastPickTime=" + lastPickTime
        + ", hasBeenRedeemed=" + hasBeenRedeemed + "]";
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
		UserLockBoxEvent other = (UserLockBoxEvent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}