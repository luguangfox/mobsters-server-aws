package com.lvl6.mobsters.dynamo.repository;
import org.springframework.stereotype.Component;

import com.lvl6.mobsters.dynamo.PvpBattleHistory;
@Component public class PvpBattleHistoryRepository extends BaseDynamoRepository<PvpBattleHistory>{
	public PvpBattleHistoryRepository(){
		super(PvpBattleHistory.class);
	}

}