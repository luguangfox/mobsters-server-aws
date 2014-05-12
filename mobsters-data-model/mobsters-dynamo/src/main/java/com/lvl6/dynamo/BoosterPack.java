package com.lvl6.dynamo;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="BoosterPack")
public class BoosterPack {



	private String id;
	
	private String name;
	private int gemPrice;
	private String listBackgroundImgName;
	private String listDescription;
	private String navBarImgName;
	private String navTitleImgName;
	private String machineImgName;  
	public BoosterPack(){}
	public BoosterPack(int id, String name, int gemPrice,
			String listBackgroundImgName, String listDescription,
			String navBarImgName, String navTitleImgName, String machineImgName) {
		super();
		this.name = name;
		this.gemPrice = gemPrice;
		this.listBackgroundImgName = listBackgroundImgName;
		this.listDescription = listDescription;
		this.navBarImgName = navBarImgName;
		this.navTitleImgName = navTitleImgName;
		this.machineImgName = machineImgName;
	}




	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	public String getName() {		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGemPrice() {
		return gemPrice;
	}

	public void setGemPrice(int gemPrice) {
		this.gemPrice = gemPrice;
	}

	public String getListBackgroundImgName() {
		return listBackgroundImgName;
	}

	public void setListBackgroundImgName(String listBackgroundImgName) {
		this.listBackgroundImgName = listBackgroundImgName;
	}

	public String getListDescription() {
		return listDescription;
	}

	public void setListDescription(String listDescription) {
		this.listDescription = listDescription;
	}

	public String getNavBarImgName() {
		return navBarImgName;
	}

	public void setNavBarImgName(String navBarImgName) {
		this.navBarImgName = navBarImgName;
	}

	public String getNavTitleImgName() {
		return navTitleImgName;
	}

	public void setNavTitleImgName(String navTitleImgName) {
		this.navTitleImgName = navTitleImgName;
	}

	public String getMachineImgName() {
		return machineImgName;
	}

	public void setMachineImgName(String machineImgName) {
		this.machineImgName = machineImgName;
	}

	@Override
	public String toString() {
		return "BoosterPack [id=" + id + ", name=" + name + ", gemPrice="
				+ gemPrice + ", listBackgroundImgName=" + listBackgroundImgName
				+ ", listDescription=" + listDescription + ", navBarImgName="
				+ navBarImgName + ", navTitleImgName=" + navTitleImgName
				+ ", machineImgName=" + machineImgName + "]";
	}
	
}
