package com.lvl6.dynamo;
import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="ObstacleForUser")
public class ObstacleForUser {



	private String id;
	private Long version;

	

	private int userId;
	private int obstacleId;
	private int xcoord;
	private int ycoord;
	private Date removalTime;
	private String orientation;	
	public ObstacleForUser() {
		super();
	}

	public ObstacleForUser(int id, int userId, int obstacleId, int xcoord,
			int ycoord, Date removalTime, String orientation) {
		super();
		this.userId = userId;
		this.obstacleId = obstacleId;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.removalTime = removalTime;
		this.orientation = orientation;
	}




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


	public int getUserId() {		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getObstacleId() {
		return obstacleId;
	}

	public void setObstacleId(int obstacleId) {
		this.obstacleId = obstacleId;
	}

	public int getXcoord() {
		return xcoord;
	}

	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	public int getYcoord() {
		return ycoord;
	}

	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}

	public Date getRemovalTime() {
		return removalTime;
	}

	public void setRemovalTime(Date removalTime) {
		this.removalTime = removalTime;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "ObstacleForUser [id=" + id + ", userId=" + userId
				+ ", obstacleId=" + obstacleId + ", xcoord=" + xcoord
				+ ", ycoord=" + ycoord + ", removalTime=" + removalTime
				+ ", orientation=" + orientation + "]";
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
		ObstacleForUser other = (ObstacleForUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}