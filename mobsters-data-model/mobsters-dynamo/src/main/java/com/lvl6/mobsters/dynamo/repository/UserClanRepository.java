package com.lvl6.mobsters.dynamo.repository;
import org.springframework.stereotype.Component;

import com.lvl6.mobsters.dynamo.UserClan;
@Component public class UserClanRepository extends BaseDynamoRepositoryImpl<UserClan>{
	public UserClanRepository(){
		super(UserClan.class);
	}

}