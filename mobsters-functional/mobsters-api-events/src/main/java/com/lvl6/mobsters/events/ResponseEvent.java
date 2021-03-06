package com.lvl6.mobsters.events;

import java.nio.ByteBuffer;

import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolResponse;

/**
 * GameEventDefault.java
 *
 * A basic GameEvent class, this can be extended for other Events
 * or a completely different class may be used as required by a specific game.
 */
public abstract class ResponseEvent extends GameEvent {
  
	/** event type */
	// TODO Turn final once all assignment is done by constructor
	protected EventProtocolResponse eventType;
	protected int tag;

	// Only required for PreDatabaseResponseEvent!
	protected ResponseEvent() {
		this.eventType = null;
		this.tag = 0;
	}

	// Event type with a default tag
	protected ResponseEvent(final EventProtocolResponse eventType) {
		this.eventType = eventType;
		this.tag = 0;
	}
	   
	// Event type with an explicit tag
	protected ResponseEvent(final EventProtocolResponse eventType, final int tag) {
		this.eventType = eventType;
		this.tag = tag;
	}

	public EventProtocolResponse getEventType() {
	  return eventType;
	}
	  
	public abstract int write (ByteBuffer bb);

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

//	@Override
//	public String toString() {
//		return ReflectionToStringBuilder.toString(this);
//	}
}// GameEvent
