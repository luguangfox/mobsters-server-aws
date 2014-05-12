package com.lvl6.dynamo;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="StructureResourceStorage")
public class StructureResourceStorage {



	private String id;/**
	 * 
	 */

	
	//
	private int structId;
	private String resourceTypeStored;
	private int capacity;	
	public StructureResourceStorage(){}
	public StructureResourceStorage(int structId, String resourceTypeStored, int capacity) {
		super();
		this.structId = structId;
		this.resourceTypeStored = resourceTypeStored;
		this.capacity = capacity;
	}


	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	public int getStructId() {		return structId;
	}

	public void setStructId(int structId) {
		this.structId = structId;
	}

	public String getResourceTypeStored() {
		return resourceTypeStored;
	}

	public void setResourceTypeStored(String resourceTypeStored) {
		this.resourceTypeStored = resourceTypeStored;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "ResourceStorage [structId=" + structId + ", resourceTypeStored="
				+ resourceTypeStored + ", capacity=" + capacity + "]";
	}
}
