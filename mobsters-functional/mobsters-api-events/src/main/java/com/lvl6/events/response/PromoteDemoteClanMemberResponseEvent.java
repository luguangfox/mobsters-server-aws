package com.lvl6.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.events.NormalResponseEvent;
import com.lvl6.mobsters.eventproto.EventClanProto.PromoteDemoteClanMemberResponseProto;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class PromoteDemoteClanMemberResponseEvent extends NormalResponseEvent {

  private PromoteDemoteClanMemberResponseProto promoteDemoteClanMemberResponseProto;
  
  public PromoteDemoteClanMemberResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_PROMOTE_DEMOTE_CLAN_MEMBER_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = promoteDemoteClanMemberResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setPromoteDemoteClanMemberResponseProto(PromoteDemoteClanMemberResponseProto promoteDemoteClanMemberResponseProto) {
    this.promoteDemoteClanMemberResponseProto = promoteDemoteClanMemberResponseProto;
  }

}
