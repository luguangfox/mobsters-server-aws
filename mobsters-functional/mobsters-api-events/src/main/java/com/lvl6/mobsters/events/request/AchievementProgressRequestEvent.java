package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventAchievementProto.AchievementProgressRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class AchievementProgressRequestEvent extends RequestEvent {
	
  private static Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());

  private AchievementProgressRequestProto achievementProgressRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      achievementProgressRequestProto = AchievementProgressRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = achievementProgressRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("AchievementProgress request exception", e);
    }
  }

  public AchievementProgressRequestProto getAchievementProgressRequestProto() {
	  return achievementProgressRequestProto;
  }

  public void setAchievementProgressRequestProto(
		  AchievementProgressRequestProto achievementProgressRequestProto) {
	  this.achievementProgressRequestProto = achievementProgressRequestProto;
  }


}
