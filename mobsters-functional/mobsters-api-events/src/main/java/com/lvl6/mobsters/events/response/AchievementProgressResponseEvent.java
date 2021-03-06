package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventAchievementProto.AchievementProgressResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class AchievementProgressResponseEvent extends NormalResponseEvent {

  private final AchievementProgressResponseProto achievementProgressResponseProto;
  
  public AchievementProgressResponseEvent(String playerId, int tag, AchievementProgressResponseProto.Builder protoBuilder){
    super(playerId, EventProtocolResponse.S_ACHIEVEMENT_PROGRESS_EVENT, tag);
    achievementProgressResponseProto = protoBuilder.build();
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = achievementProgressResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }
}
