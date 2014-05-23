package com.lvl6.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.events.NormalResponseEvent;
import com.lvl6.mobsters.eventproto.EventMonsterProto.SubmitMonsterEnhancementResponseProto;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class SubmitMonsterEnhancementResponseEvent extends NormalResponseEvent {

  private SubmitMonsterEnhancementResponseProto submitMonsterEnhanceResponseProto;
  
  public SubmitMonsterEnhancementResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_SUBMIT_MONSTER_ENHANCEMENT_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = submitMonsterEnhanceResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setSubmitMonsterEnhancementResponseProto(SubmitMonsterEnhancementResponseProto submitMonsterEnhanceResponseProto) {
    this.submitMonsterEnhanceResponseProto = submitMonsterEnhanceResponseProto;
  }

}
