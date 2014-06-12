package com.lvl6.mobsters.dynamo.repository;
import org.springframework.stereotype.Component;

import com.lvl6.mobsters.dynamo.MonsterHealingForUser;
@Component public class MonsterHealingForUserRepository extends BaseDynamoRepositoryImpl<MonsterHealingForUser>{
	public MonsterHealingForUserRepository(){
		super(MonsterHealingForUser.class);
	}

}