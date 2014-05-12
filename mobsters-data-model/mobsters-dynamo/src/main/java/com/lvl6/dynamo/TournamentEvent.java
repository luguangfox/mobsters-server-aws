package com.lvl6.dynamo;

import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="TournamentEvent")
public class TournamentEvent {



	private String id;	
	private Date startDate;
	private Date endDate;
	private String eventName;
	private boolean rewardsGivenOut;  
	public TournamentEvent(){}
  public TournamentEvent(int id, Date startDate, Date endDate, String eventName, boolean rewardsGivenOut) {
    super();
    this.startDate = startDate;
    this.endDate = endDate;
    this.eventName = eventName;
    this.rewardsGivenOut = rewardsGivenOut;
  }


	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


  public Date getStartDate() {    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getEventName() {
    return eventName;
  }
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public boolean isRewardsGivenOut() {
    return rewardsGivenOut;
  }

  public void setRewardsGivenOut(boolean rewardsGivenOut) {
    this.rewardsGivenOut = rewardsGivenOut;
  }

	@Override
	public String toString() {
		return "TournamentEvent [id=" + id + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", eventName=" + eventName
				+ ", rewardsGivenOut=" + rewardsGivenOut + "]";
	}

}
