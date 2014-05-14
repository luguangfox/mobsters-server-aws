package com.lvl6.dynamo;
import java.util.Random;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName="TaskStageMonster")
public class TaskStageMonster {



	private String id;
	private Long version;

	

	private int stageId;
	private int monsterId;
	private String monsterType;
	private int expReward;
	private int minCashDrop;
	private int maxCashDrop;
	private int minOilDrop;
	private int maxOilDrop;
	private float puzzlePieceDropRate;
	private int level;
	private float chanceToAppear;  

	private Random rand;
	public TaskStageMonster(){}
	public TaskStageMonster(int id, int stageId, int monsterId, String monsterType,
		int expReward, int minCashDrop, int maxCashDrop, int minOilDrop,
		int maxOilDrop, float puzzlePieceDropRate, int level,
		float chanceToAppear) {
	super();
	this.stageId = stageId;
	this.monsterId = monsterId;
	this.monsterType = monsterType;
	this.expReward = expReward;
	this.minCashDrop = minCashDrop;
	this.maxCashDrop = maxCashDrop;
	this.minOilDrop = minOilDrop;
	this.maxOilDrop = maxOilDrop;
	this.puzzlePieceDropRate = puzzlePieceDropRate;
	this.level = level;
	this.chanceToAppear = chanceToAppear;
	}

//covenience methods--------------------------------------------------------

	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	@DynamoDBVersionAttribute
	public Long getVersion(){return version;}
	public void setVersion(Long version){this.version = version;}


  public Random getRand() {    return rand;
  }

  public void setRand(Random rand) {
    this.rand = rand;
  }
  
  public int getCashDrop() {
    //example goal: [min,max]=[5, 10], transform range to start at 0.
    //[min-min, max-min] = [0,max-min] = [0,10-5] = [0,5]
    //this means there are (10-5)+1 possible numbers
    
    int minMaxDiff = getMaxCashDrop() - getMinCashDrop();
    int randCash = rand.nextInt(minMaxDiff + 1); 

    //number generated in [0, max-min] range, but need to transform
    //back to original range [min, max]. so add min. [0+min, max-min+min]
    return randCash + getMinCashDrop();
  }
  
  public int getOilDrop() {
    int minMaxDiff = getMaxOilDrop() - getMinOilDrop();
    int randOil = rand.nextInt(minMaxDiff + 1); 

    //number generated in [0, max-min] range, but need to transform
    //back to original range [min, max]. so add min. [0+min, max-min+min]
    return randOil + getMinOilDrop();
  }  

  public boolean didPuzzlePieceDrop() {
    float randFloat = getRand().nextFloat();
    
    if (randFloat < getPuzzlePieceDropRate()) {
      return true;
    } else {
      return false;
    }
  }
  //end covenience methods--------------------------------------------------------



  public int getStageId() {
	  return stageId;
  }

  public void setStageId(int stageId) {
	  this.stageId = stageId;
  }

  public int getMonsterId() {
	  return monsterId;
  }

  public void setMonsterId(int monsterId) {
	  this.monsterId = monsterId;
  }

  public String getMonsterType() {
	  return monsterType;
  }

  public void setMonsterType(String monsterType) {
	  this.monsterType = monsterType;
  }

  public int getExpReward() {
	  return expReward;
  }

  public void setExpReward(int expReward) {
	  this.expReward = expReward;
  }

  public int getMinCashDrop() {
	  return minCashDrop;
  }

  public void setMinCashDrop(int minCashDrop) {
	  this.minCashDrop = minCashDrop;
  }

  public int getMaxCashDrop() {
	  return maxCashDrop;
  }

  public void setMaxCashDrop(int maxCashDrop) {
	  this.maxCashDrop = maxCashDrop;
  }

  public int getMinOilDrop() {
	  return minOilDrop;
  }

  public void setMinOilDrop(int minOilDrop) {
	  this.minOilDrop = minOilDrop;
  }

  public int getMaxOilDrop() {
	  return maxOilDrop;
  }

  public void setMaxOilDrop(int maxOilDrop) {
	  this.maxOilDrop = maxOilDrop;
  }

  public float getPuzzlePieceDropRate() {
	  return puzzlePieceDropRate;
  }

  public void setPuzzlePieceDropRate(float puzzlePieceDropRate) {
	  this.puzzlePieceDropRate = puzzlePieceDropRate;
  }

  public int getLevel() {
	  return level;
  }

  public void setLevel(int level) {
	  this.level = level;
  }

  public float getChanceToAppear() {
	  return chanceToAppear;
  }

  public void setChanceToAppear(float chanceToAppear) {
	  this.chanceToAppear = chanceToAppear;
  }

  @Override
  public String toString() {
	  return "TaskStageMonster [id=" + id + ", stageId=" + stageId
			  + ", monsterId=" + monsterId + ", monsterType=" + monsterType
			  + ", expReward=" + expReward + ", minCashDrop=" + minCashDrop
			  + ", maxCashDrop=" + maxCashDrop + ", minOilDrop=" + minOilDrop
			  + ", maxOilDrop=" + maxOilDrop + ", puzzlePieceDropRate="
			  + puzzlePieceDropRate + ", level=" + level + ", chanceToAppear="
			  + chanceToAppear + "]";
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
		TaskStageMonster other = (TaskStageMonster) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}