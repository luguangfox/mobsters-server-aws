package com.lvl6.dynamo;

import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="Referral")
public class Referral {



	private String id;	
	private int referrerId;
	private int newlyReferredId;
	private Date timeOfReferral;
	private int coinsGivenToReferrer;
	public Referral(){}
	public Referral(int referrerId, int newlyReferredId, Date timeOfReferral,
			int coinsGivenToReferrer) {
		this.referrerId = referrerId;
		this.newlyReferredId = newlyReferredId;
		this.timeOfReferral = timeOfReferral;
		this.coinsGivenToReferrer = coinsGivenToReferrer;
	}


	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	public int getReferrerId() {		return referrerId;
	}

	public int getNewlyReferredId() {
		return newlyReferredId;
	}

	public Date getTimeOfReferral() {
		return timeOfReferral;
	}

	public int getCoinsGivenToReferrer() {
		return coinsGivenToReferrer;
	}

	@Override
	public String toString() {
		return "Referral [referrerId=" + referrerId + ", newlyReferredId="
				+ newlyReferredId + ", timeOfReferral=" + timeOfReferral
				+ ", coinsGivenToReferrer=" + coinsGivenToReferrer + "]";
	}
}
