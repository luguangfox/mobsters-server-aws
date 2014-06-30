package com.lvl6.mobsters.noneventproto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvl6.mobsters.info.Achievement;
import com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.Element;
import com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.Quality;
import com.lvl6.mobsters.noneventproto.ConfigNoneventSharedEnumProto.ResourceType;
import com.lvl6.mobsters.noneventproto.NoneventAchievementProto.AchievementProto;
import com.lvl6.mobsters.noneventproto.NoneventAchievementProto.AchievementProto.AchievementType;

public class NoneventAchievementProtoSerializerImpl implements NoneventAchievementProtoSerializer 
{

	private static Logger log = LoggerFactory.getLogger(new Object() {}.getClass()
		.getEnclosingClass());

	@Override
	public AchievementProto createAchievementProto( Achievement a )
	{
		AchievementProto.Builder ab = AchievementProto.newBuilder();

		ab.setAchievementId(a.getId());

		String str = a.getAchievementName();
		if (null != str) {
			ab.setName(str);
		}

		str = a.getDescription();
		if (null != str) {
			ab.setDescription(str);
		}

		ab.setGemReward(a.getGemReward());
		ab.setLvl(a.getLvl());

		str = a.getAchievementType();
		if (null != str) {
			try {
				AchievementType at = AchievementType.valueOf(str);
				ab.setAchievementType(at);
			} catch(Exception e) {
				log.error("invalid AchievementType. achievement=" + a);
			}
		}

		str = a.getResourceType();
		if (null != str) {
			try {
				ResourceType rt = ResourceType.valueOf(str);
				ab.setResourceType(rt);
			} catch(Exception e) {
				log.error("invalid ResourceType. achievement=" + a);
			}
		}

		str = a.getMonsterElement();
		if (null != str) {
			try {
				Element me = Element.valueOf(str);
				ab.setElement(me);
			} catch(Exception e) {
				log.error("invalid MonsterElement. achievement=" + a);
			}
		}

		str = a.getMonsterQuality();
		if (null != str) {
			try {
				Quality mq = Quality.valueOf(str);
				ab.setQuality(mq);
			} catch(Exception e) {
				log.error("invalid MonsterQuality. achievement=" + a);
			}
		}

		ab.setStaticDataId(a.getStaticDataId());
		ab.setQuantity(a.getQuantity());
		ab.setPriority(a.getPriority());
		ab.setPrerequisiteId(a.getPrerequisiteAchievement().getId());
		ab.setSuccessorId(a.getSuccessorAchievement().getId());

		return ab.build();
	}
}
