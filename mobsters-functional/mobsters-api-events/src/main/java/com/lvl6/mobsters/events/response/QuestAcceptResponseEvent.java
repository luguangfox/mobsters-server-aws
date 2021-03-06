package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventQuestProto.QuestAcceptResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class QuestAcceptResponseEvent extends NormalResponseEvent {

  private QuestAcceptResponseProto questAcceptResponseProto;
  
  public QuestAcceptResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_QUEST_ACCEPT_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = questAcceptResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setQuestAcceptResponseProto(QuestAcceptResponseProto questAcceptResponseProto) {
    this.questAcceptResponseProto = questAcceptResponseProto;
  }

}