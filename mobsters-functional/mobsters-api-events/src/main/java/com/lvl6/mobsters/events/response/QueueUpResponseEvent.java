package com.lvl6.mobsters.events.response;

import java.nio.ByteBuffer;

import com.google.protobuf.ByteString;
import com.lvl6.mobsters.eventproto.EventPvpProto.QueueUpResponseProto;
import com.lvl6.mobsters.events.NormalResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

public class QueueUpResponseEvent extends NormalResponseEvent {

  private QueueUpResponseProto QueueUpResponseProto;
  
  public QueueUpResponseEvent(String playerId){
    super(playerId);
    eventType = EventProtocolResponse.S_QUEUE_UP_EVENT;
  }
  
  @Override
  public int write(ByteBuffer bb) {
    ByteString b = QueueUpResponseProto.toByteString();
    b.copyTo(bb);
    return b.size();
  }

  public void setQueueUpResponseProto(QueueUpResponseProto QueueUpResponseProto) {
    this.QueueUpResponseProto = QueueUpResponseProto;
  }

  public QueueUpResponseProto getQueueUpResponseProto() {   //because APNS required
    return QueueUpResponseProto;
  }
  
}
