package com.lvl6.mobsters.events.request;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lvl6.mobsters.eventproto.EventStructureProto.BeginObstacleRemovalRequestProto;
import com.lvl6.mobsters.events.RequestEvent;

public class BeginObstacleRemovalRequestEvent extends RequestEvent {

	private Logger log = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
  private BeginObstacleRemovalRequestProto beginObstacleRemovalRequestProto;
  
  /**
   * read the event from the given ByteBuffer to populate this event
   */
  public void read(ByteBuffer buff) {
    try {
      beginObstacleRemovalRequestProto = BeginObstacleRemovalRequestProto.parseFrom(ByteString.copyFrom(buff));
      playerId = beginObstacleRemovalRequestProto.getSender().getUserUuid();
    } catch (InvalidProtocolBufferException e) {
      log.error("begin obstacle removal request exception", e);
    }
  }

  public BeginObstacleRemovalRequestProto getBeginObstacleRemovalRequestProto() {
    return beginObstacleRemovalRequestProto;
  }
  //added for testing purposes
  public void setBeginObstacleRemovalRequestProto(
		  BeginObstacleRemovalRequestProto beginObstacleRemovalRequestProto) {
	  this.beginObstacleRemovalRequestProto = beginObstacleRemovalRequestProto;
  }

}
