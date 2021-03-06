package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventStructureProto.MoveOrRotateNormStructureRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class MoveOrRotateNormStructureRequestEvent extends RequestEvent {

	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
  private MoveOrRotateNormStructureRequestProto moveOrRotateNormStructureRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      moveOrRotateNormStructureRequestProto = MoveOrRotateNormStructureRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = moveOrRotateNormStructureRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("move or rotate norm structure request exception", e);
    }
  }

  public MoveOrRotateNormStructureRequestProto getMoveOrRotateNormStructureRequestProto() {
    return moveOrRotateNormStructureRequestProto;
  }
}
