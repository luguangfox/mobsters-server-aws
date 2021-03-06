package com.lvl6.eventhandlers;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.socket.WebSocketSession;

import com.lvl6.mobsters.controllers.ControllerManager;
import com.lvl6.mobsters.events.RequestEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolRequest;
import com.lvl6.mobsters.server.ApplicationMode;
import com.lvl6.mobsters.server.EventController;
import com.lvl6.utils.Attachment;

public abstract class AbstractGameEventHandler implements MessageHandler {

	protected static Logger log = LoggerFactory.getLogger(AbstractGameEventHandler.class);

	@Autowired
	protected ApplicationMode applicationMode;

	@Autowired
	protected ControllerManager controllerManager;
	

	@Override
	public void handleMessage(Message<?> msg) throws MessagingException {
		log.trace("Received message: ");
		for (String key : msg.getHeaders().keySet()) {
			log.debug(key + ": " + msg.getHeaders().get(key));
		}
		// log.info("Payload: "+msg.getPayload());
		//handleEvent((byte[]) msg.getPayload());
	}

	public void handleEvent(ByteBuffer bytes, WebSocketSession session) {
		Attachment attachment = new Attachment();
		byte[] payload = (byte[]) bytes.asReadOnlyBuffer().array();
		attachment.readBuff = bytes;
		while (attachment.eventReady()) {
			RequestEvent event = getEvent(attachment);
			log.trace("Recieved event from client: " + event.getPlayerId());
			delegateEvent(payload, event, attachment.eventType, session);
			attachment.reset();

		}
	}

	/**
	 * read an event from the attachment's payload
	 */
	protected RequestEvent getEvent(Attachment attachment) {
		RequestEvent event = null;
		ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOf(attachment.payload, attachment.payloadSize));

		// get the controller and tell it to instantiate an event for us
		EventController ec = getControllerManager().getEventControllerByEventType(attachment.eventType);

		if (ec == null) {
			return null;
		}
		event = ec.createRequestEvent();
		event.setTag(attachment.tag);

		// read the event from the payload
		event.read(bb);
		return event;
	}

	protected abstract void delegateEvent(byte[] bytes, RequestEvent event, EventProtocolRequest eventType, WebSocketSession session);
	
	

	public ApplicationMode getApplicationMode() {
		return applicationMode;
	}

	public void setApplicationMode(ApplicationMode applicationMode) {
		this.applicationMode = applicationMode;
	}

	public ControllerManager getControllerManager() {
		return controllerManager;
	}

	public void setControllerManager(ControllerManager controllerManager) {
		this.controllerManager = controllerManager;
	}

	

}