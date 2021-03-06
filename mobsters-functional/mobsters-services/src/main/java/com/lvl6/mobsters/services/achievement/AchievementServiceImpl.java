package com.lvl6.mobsters.services.achievement;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.lvl6.mobsters.dynamo.AchievementForUser;
import com.lvl6.mobsters.dynamo.User;
import com.lvl6.mobsters.dynamo.repository.AchievementForUserRepository;
import com.lvl6.mobsters.dynamo.repository.UserRepository;
import com.lvl6.mobsters.info.Achievement;
import com.lvl6.mobsters.info.repository.AchievementRepository;
import com.lvl6.mobsters.utility.exception.Lvl6MobstersException;
import com.lvl6.mobsters.utility.exception.Lvl6MobstersStatusCode;

@Component
public class AchievementServiceImpl implements AchievementService {
	
    private static Logger LOG = LoggerFactory.getLogger(AchievementServiceImpl.class);

    @Autowired
    private AchievementForUserRepository achievementForUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;
    
	//NON CRUD LOGIC
	
	/**************************************************************************/
	//CRUD LOGIC
    
    // BEGIN READ ONLY LOGIC
    @Override
    public List<AchievementForUser> getAchievementsForUserId( String userId ) {
    	return achievementForUserRepository.findByUserId(userId);
    }
	

	// END READ ONLY LOGIC
    
    /**************************************************************************/

   
    @Override
    public void modifyAchievementsForUser( String userId, ModifyUserAchievementsSpec modifySpec ) {
        // txManager.startTransaction();

        // get whatever we need from the database
        final Map<Integer, AchievementForUser> achievementIdToAfu = modifySpec.getAchievementsForUser();
        
        final Multimap<Integer, UserAchievementFunc> modSpecMultimap = modifySpec.getModSpecMultimap();
        final Set<Integer> achievementIds = achievementIdToAfu.keySet();
        
        List<AchievementForUser> existingUserAchievements =
            achievementForUserRepository.findByUserIdAndAchievementIdIn(userId, achievementIds);
        
        // Mutate the objects
        
        //set the userId on all the achievements to be stored to the db
        for (AchievementForUser afu : achievementIdToAfu.values()) {
            //TODO: Check if the achievementId refers to a valid achievement
            afu.setUserId(userId);
        }
        
        //update the existing ones
        // txManager.startTransaction();
        for (final AchievementForUser nextAchievement : existingUserAchievements) {
            int achievementId = nextAchievement.getAchievementId();
            
            Collection<UserAchievementFunc> achievementOps = modSpecMultimap.get(achievementId);
            for (UserAchievementFunc nextAchievementOp : achievementOps) {
                nextAchievementOp.apply(nextAchievement);
            }

            // the AchievementForUser exists in the db, update it,
            // store to the map that will be persisted to db
            achievementIdToAfu.put(achievementId, nextAchievement);
        }

        achievementForUserRepository.saveEach(achievementIdToAfu.values());
        // Write back to the database, then close the transaction by returning
        // TBD: Need to restore a workable save interface.
        // monsterForUserRepository.save(existingUserAchievements);
        // txManager.endTransaction();
    }

    // motivation for Map of objects and Multimap of functions to apply on objects is
    // because service doesn't know beforehand if objects exist in db or not
    static class ModifyUserAchievementsSpecBuilderImpl implements ModifyUserAchievementsSpecBuilder
    {
        // the end state: objects to be saved to db
        final Map<Integer, AchievementForUser> achievementIdToAfu;
        
        // modification specification map
        final Multimap<Integer, UserAchievementFunc> modSpecMap;

        ModifyUserAchievementsSpecBuilderImpl() {
            this.achievementIdToAfu = new HashMap<Integer, AchievementForUser>();
            this.modSpecMap = ArrayListMultimap.create();
        }

        private AchievementForUser getTarget( int achievementId ) {
            AchievementForUser afu = achievementIdToAfu.get(achievementId);
            if (null == afu) {
                afu = new AchievementForUser();
                achievementIdToAfu.put(achievementId, afu);
            }
            return afu;
        }

        @Override
        public ModifyUserAchievementsSpecBuilder setProgressAbsolute(
            int achievementId,
            int progress )
        {
            AchievementForUser afu = getTarget(achievementId);
            afu.setProgress(progress);
            
            modSpecMap.put(
                achievementId,
                new SetProgressAbsolute(progress)
            );
            return this;
        }

        @Override
        public ModifyUserAchievementsSpecBuilder setIsComplete( int achievementId ) {
            AchievementForUser afu = getTarget(achievementId);
            afu.setComplete(true);
            
            modSpecMap.put(
                achievementId,
                new SetIsComplete()
            );
            return this;
        }

        @Override
        public ModifyUserAchievementsSpecBuilder setTimeComplete(
            int achievementId,
            Date timeCompleted )
        {
            AchievementForUser afu = getTarget(achievementId);
            afu.setTimeCompleted(timeCompleted);
            return this;
        }

        @Override
        public ModifyUserAchievementsSpec build() {
            final ModifyUserAchievementsSpec retVal =
                new ModifyUserAchievementsSpec(achievementIdToAfu, modSpecMap);

            return retVal;
        }
    }
    
    static class SetProgressAbsolute implements UserAchievementFunc {

        final private int progress;
        
        SetProgressAbsolute(int progress) {
            this.progress = progress;
        }
        
        @Override
        public void apply( AchievementForUser t )
        {
            t.setProgress(progress);
        }
        
    }
    
    static class SetIsComplete implements UserAchievementFunc {

        SetIsComplete() {
        }
        
        @Override
        public void apply( AchievementForUser t )
        {
            t.setComplete(true);
        }
        
    }
    
    static class SetTimeComplete implements UserAchievementFunc {

        final private Date timeCompleted;
        
        SetTimeComplete(Date timeCompleted) {
            this.timeCompleted = timeCompleted;
        }
        
        @Override
        public void apply( AchievementForUser t )
        {
            t.setTimeCompleted(timeCompleted);
        }
        
    }

    //for the dependency injection
    public AchievementForUserRepository getAchievementForUserRepository()
    {
        return achievementForUserRepository;
    }

    public void setAchievementForUserRepository(
        AchievementForUserRepository achievementForUserRepository )
    {
        this.achievementForUserRepository = achievementForUserRepository;
    }


	@Override
	public User redeemAchievement( String userId, int achievementId, Date now )
	{
		// TODO: TRANSACTIONIFY
		User u = userRepository.load(userId);
		AchievementForUser afu = achievementForUserRepository
			.findByUserIdAndAchievementId(userId, achievementId);
		
		checkIfUserCanRedeemAchievement(achievementId, u, afu);
		
		Achievement a = achievementRepository.findOne(achievementId);
		int gemReward = Math.max(
			0,
			a.getGemReward()
		);
		// TODO: Write to currency history
		u.setGems(
			u.getGems()
			+ gemReward
		);
		userRepository.save(u);
		
		afu.setRedeemed(true);
		afu.setTimeRedeemed(now);
		achievementForUserRepository.save(afu);
		
		return null;
	}


	private void checkIfUserCanRedeemAchievement(
		int achievementId,
		User u,
		AchievementForUser afu )
	{
		if (null == afu) {
			LOG.error("no achievementForUser. user=" 
				+ u
				+ ", achievementId="
				+ achievementId);
			throw new Lvl6MobstersException(Lvl6MobstersStatusCode.FAIL_OTHER);
		}
		
		if (!afu.isComplete()) {
			LOG.error("user trying to redeem incomplete achievement. user="
				+ u
				+ ", userAchievement="
				+ afu);
			throw new Lvl6MobstersException(Lvl6MobstersStatusCode.FAIL_ACHIEVEMENT_INCOMPLETE);
		}
		
		if (afu.isRedeemed()) {
			LOG.error("user trying to redeem already redeemed achievement. user="
				+ u
				+ ", userAchievement="
				+ afu);
			throw new Lvl6MobstersException(Lvl6MobstersStatusCode.FAIL_ACHIEVEMENT_ALREADY_REDEEMED);
		}
	}

}
