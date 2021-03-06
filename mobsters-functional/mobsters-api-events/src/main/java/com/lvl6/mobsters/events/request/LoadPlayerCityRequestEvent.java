package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventCityProto.LoadPlayerCityRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class LoadPlayerCityRequestEvent extends RequestEvent {

	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
  private LoadPlayerCityRequestProto loadPlayerCityRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      loadPlayerCityRequestProto = LoadPlayerCityRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = loadPlayerCityRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("load player city request exception", e);
    }
  }

  public LoadPlayerCityRequestProto getLoadPlayerCityRequestProto() {
    return loadPlayerCityRequestProto;
  }
}
