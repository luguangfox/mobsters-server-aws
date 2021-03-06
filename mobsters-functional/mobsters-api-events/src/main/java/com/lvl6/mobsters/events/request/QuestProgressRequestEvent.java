package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventQuestProto.QuestProgressRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class QuestProgressRequestEvent extends RequestEvent {
	
  private static Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());

  private QuestProgressRequestProto questProgressRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      questProgressRequestProto = QuestProgressRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = questProgressRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("quest progress request exception", e);
    }
  }

  public QuestProgressRequestProto getQuestProgressRequestProto() {
    return questProgressRequestProto;
  }
}
