package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventMiniJobProto.RedeemMiniJobResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class RedeemMiniJobResponseEvent extends NormalResponseEvent {

  private RedeemMiniJobResponseProto redeemMiniJobResponseProto;
  
  public RedeemMiniJobResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_REDEEM_MINI_JOB_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = redeemMiniJobResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setRedeemMiniJobResponseProto(RedeemMiniJobResponseProto redeemMiniJobResponseProto) {
    this.redeemMiniJobResponseProto = redeemMiniJobResponseProto;
  }

}
