package com.lvl6.dynamo;

import java.util.Date;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName="AdminChatPost")
public class AdminChatPost extends PrivateChatPost {	



	private String id;	
	
	public AdminChatPost(int posterId, int recipientId, Date timeOfPost, String content) {
		super(posterId, recipientId, timeOfPost, content);
		//setUsername(username);
	}
	
	public AdminChatPost(){}
	public AdminChatPost(int posterId, int recipientId, Date timeOfPost, String content, String username) {
		super(posterId, recipientId, timeOfPost, content);
		setUsername(username);
	}
	
	protected String username = "";
	


	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId(){return id;}
	public void setId(String id){this.id = id;}


	public String getUsername() {		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	
}
