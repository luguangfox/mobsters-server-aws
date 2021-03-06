package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventClanProto.LeaveClanRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class LeaveClanRequestEvent extends RequestEvent {

	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
  private LeaveClanRequestProto leaveClanRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      leaveClanRequestProto = LeaveClanRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = leaveClanRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("leave clan request exception", e);
    }
  }

  public LeaveClanRequestProto getLeaveClanRequestProto() {
    return leaveClanRequestProto;
  }
}
