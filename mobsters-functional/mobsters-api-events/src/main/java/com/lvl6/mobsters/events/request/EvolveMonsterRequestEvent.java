package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventMonsterProto.EvolveMonsterRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class EvolveMonsterRequestEvent extends RequestEvent {

	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
  private EvolveMonsterRequestProto evolveMonsterRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      evolveMonsterRequestProto = EvolveMonsterRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = evolveMonsterRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("evolve monster request exception", e);
    }
  }

  public EvolveMonsterRequestProto getEvolveMonsterRequestProto() {
    return evolveMonsterRequestProto;
  }
}
