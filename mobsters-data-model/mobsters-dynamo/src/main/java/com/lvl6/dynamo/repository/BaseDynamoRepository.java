package com.lvl6.dynamo.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;

public class BaseDynamoRepository<T> {
	
	
	private static final Logger log = LoggerFactory.getLogger(BaseDynamoRepository.class);
	
	protected boolean isActive = false;
	
	@Autowired
	protected AmazonDynamoDBClient client;
	
	@Autowired
	protected DynamoProvisioning provisioning = new DynamoProvisioning();
	
	protected DynamoDBMapper mapper;
	
	protected Class<T> clss;
	
	
	public BaseDynamoRepository(Class<T> clss) {
		super();
		this.clss = clss;
	}

	
	public void save(T obj) {
		mapper.save(obj);
	}
	
	public T load(String id) {
		return mapper.load(clss, id);
	}
	
	
	
	protected void createTable() {
		try {
		log.info("Creating Dynamo table {}", clss.getSimpleName());
		ArrayList<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("id").withAttributeType("S"));
		        
		ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
		ks.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH));
		  
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
		    .withReadCapacityUnits(provisioning.getReads())
		    .withWriteCapacityUnits(provisioning.getWrites());
		        
		CreateTableRequest request = new CreateTableRequest()
		    .withTableName(clss.getSimpleName())
		    .withAttributeDefinitions(attributeDefinitions)
		    .withKeySchema(ks)
		    .withProvisionedThroughput(provisionedThroughput)
		    .withGlobalSecondaryIndexes(getGlobalIndexes())
		    .withLocalSecondaryIndexes(getLocalIndexes());
		    
		CreateTableResult result = client.createTable(request);
		}catch(Throwable e) {
			log.error("Error creating Dynamo table {}", clss.getSimpleName(), e);
			throw e;
		}
	}
	
	protected void updateTable() {
		try {
	        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
	        .withReadCapacityUnits(provisioning.getReads())
	        .withWriteCapacityUnits(provisioning.getWrites());

	        UpdateTableRequest updateTableRequest = new UpdateTableRequest()
	            .withTableName(clss.getSimpleName())
	            .withProvisionedThroughput(provisionedThroughput);
	        
	        UpdateTableResult result = client.updateTable(updateTableRequest);
			
		}catch(Throwable e) {
			log.error("Error creating Dynamo table {}", clss.getSimpleName(), e);
			throw e;
		}
	}
	
	public void checkTable() {
		if(!isActive){
			return;
		}
		try {
		DescribeTableResult result = client.describeTable(clss.getSimpleName());
		if(result != null && result.getTable().getCreationDateTime() != null) {
			log.info("Dynamo table {} status: {}", clss.getSimpleName(), result.getTable().getTableStatus());
			ProvisionedThroughputDescription prov = result.getTable().getProvisionedThroughput();
			if(prov.getReadCapacityUnits().equals(provisioning.getReads())&&prov.getWriteCapacityUnits().equals(provisioning.getWrites())) {
				log.info("Dynamo table {}", clss.getSimpleName());
			}else {
				updateTable();
			}
		}else {
			createTable();
		}
		}catch(ResourceNotFoundException re) {
			createTable();
		}catch(Throwable e) {
			log.error("Error checking Dynamo table {}", clss.getSimpleName(), e);
			throw e;
		}
	}
	
	
	public List<GlobalSecondaryIndex> getGlobalIndexes(){
		return new ArrayList<>();
	}
	
	public List<LocalSecondaryIndex> getLocalIndexes(){
		return new ArrayList<>();
	}
	
	public AmazonDynamoDBClient getClient() {
		return client;
	}

	public void setClient(AmazonDynamoDBClient client) {
		this.client = client;
		mapper = new DynamoDBMapper(client);
	}

	public DynamoProvisioning getProvisioning() {
		return provisioning;
	}

	public void setProvisioning(DynamoProvisioning provisioning) {
		this.provisioning = provisioning;
	}

}
