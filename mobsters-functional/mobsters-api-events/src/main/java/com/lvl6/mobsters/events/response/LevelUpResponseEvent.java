package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventUserProto.LevelUpResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class LevelUpResponseEvent extends NormalResponseEvent {

  private LevelUpResponseProto levelUpResponseProto;
  
  public LevelUpResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_LEVEL_UP_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = levelUpResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setLevelUpResponseProto(LevelUpResponseProto levelUpResponseProto) {
    this.levelUpResponseProto = levelUpResponseProto;
  }

}
