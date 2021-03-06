package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventQuestProto.QuestRedeemResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class QuestRedeemResponseEvent extends NormalResponseEvent {

  private QuestRedeemResponseProto questRedeemResponseProto;
  
  public QuestRedeemResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_QUEST_REDEEM_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = questRedeemResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setQuestRedeemResponseProto(QuestRedeemResponseProto questRedeemResponseProto) {
    this.questRedeemResponseProto = questRedeemResponseProto;
  }

}