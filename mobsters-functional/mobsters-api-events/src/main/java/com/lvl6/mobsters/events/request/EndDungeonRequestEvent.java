package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventDungeonProto.EndDungeonRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class EndDungeonRequestEvent extends RequestEvent {

	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
  private EndDungeonRequestProto endDungeonRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      endDungeonRequestProto = EndDungeonRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = endDungeonRequestProto.getSender().getMinUserProto().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("end dungeon request exception", e);
    }
  }

  public EndDungeonRequestProto getEndDungeonRequestProto() {
    return endDungeonRequestProto;
  }
}
