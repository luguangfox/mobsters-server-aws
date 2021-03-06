package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventMonsterProto.UpdateMonsterHealthResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class UpdateMonsterHealthResponseEvent extends NormalResponseEvent {

  private UpdateMonsterHealthResponseProto updateMonsterHealthResponseProto;
  
  public UpdateMonsterHealthResponseEvent(final String playerId, final int tag, UpdateMonsterHealthResponseProto.Builder protoBuilder){
    super(playerId, EventProtocolResponse.S_UPDATE_MONSTER_HEALTH_EVENT, tag);
    updateMonsterHealthResponseProto = protoBuilder.build();
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = updateMonsterHealthResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setUpdateMonsterHealthResponseProto(UpdateMonsterHealthResponseProto updateMonsterHealthResponseProto) {
    this.updateMonsterHealthResponseProto = updateMonsterHealthResponseProto;
  }

  public UpdateMonsterHealthResponseProto getUpdateMonsterHealthResponseProto() {   //because APNS required
    return updateMonsterHealthResponseProto;
  }
  
}
