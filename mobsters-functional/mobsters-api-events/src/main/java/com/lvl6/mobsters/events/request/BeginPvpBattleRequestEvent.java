package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventPvpProto.BeginPvpBattleRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class BeginPvpBattleRequestEvent extends RequestEvent {
	
	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());

  private BeginPvpBattleRequestProto beginPvpBattleRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      beginPvpBattleRequestProto = BeginPvpBattleRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = beginPvpBattleRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("update user currency request exception", e);
    }
  }

  public BeginPvpBattleRequestProto getBeginPvpBattleRequestProto() {
    return beginPvpBattleRequestProto;
  }
}
