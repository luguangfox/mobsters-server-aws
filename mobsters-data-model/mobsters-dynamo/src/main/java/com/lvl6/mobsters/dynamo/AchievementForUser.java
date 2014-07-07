package com.lvl6.mobsters.dynamo;

// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName = "AchievementForUser")
public class AchievementForUser {

    // private String id;
    private String userId;

    private int achievementId;

    private Long version;

    private int progress;

    private boolean isComplete;

    private Date timeCompleted;

    private boolean isRedeemed;

    private Date timeRedeemed;

    public AchievementForUser() {
        super();
    }

    public AchievementForUser(
        String userId,
        int achievementId,
        int progress,
        boolean isComplete,
        Date timeCompleted,
        boolean isRedeemed,
        Date timeRedeemed )
    {
        super();
        this.userId = userId;
        this.achievementId = achievementId;
        this.progress = progress;
        this.isComplete = isComplete;
        this.timeCompleted = timeCompleted;
        this.isRedeemed = isRedeemed;
        this.timeRedeemed = timeRedeemed;
    }

    /*
     * @DynamoDBHashKey(attributeName = "id")
     * @DynamoDBAutoGeneratedKey public String getId(){return id;} public void setId(String id){this.id =
     * id;}
     */

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId( String userId ) {
        this.userId = userId;
    }

    @DynamoDBRangeKey(attributeName = "achievementId")
    public int getAchievementId() {
    	return achievementId;
    }
    
    public void setAchievementId( int achievementId ) {
    	this.achievementId = achievementId;
    }

    @DynamoDBVersionAttribute
    public Long getVersion() {
        return version;
    }

    public void setVersion( Long version ) {
        this.version = version;
    }

    public void setAchievementId( Integer achievementId ) {
        this.achievementId = achievementId.intValue();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress( int progress ) {
        this.progress = progress;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete( boolean isComplete ) {
        this.isComplete = isComplete;
    }

    public Date getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted( Date timeCompleted ) {
        this.timeCompleted = timeCompleted;
    }

    public boolean isRedeemed() {
        return isRedeemed;
    }

    public Date getTimeRedeemed() {
        return timeRedeemed;
    }

    public void setTimeRedeemed( Date timeRedeemed ) {
        this.timeRedeemed = timeRedeemed;
    }

    public void setRedeemed( boolean isRedeemed ) {
        this.isRedeemed = isRedeemed;
    }

    @Override
    public String toString() {
        return "AchievementForUser [userId=" + userId + ", version=" + version + ", achievementId="
            + achievementId + ", progress=" + progress + ", isComplete=" + isComplete
            + ", timeCompleted=" + timeCompleted + ", isRedeemed=" + isRedeemed + ", timeRedeemed="
            + timeRedeemed + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + achievementId;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AchievementForUser other = (AchievementForUser) obj;
        if (achievementId != other.achievementId)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }


}