package com.lvl6.mobsters.utils;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvl6.mobsters.events.ResponseEvent;
import com.lvl6.properties.Globals;

/**
 * NIOUtils.java
 *
 * Misc utility functions to simplify dealing w/ NIO channels and buffers.
 */
public class NIOUtils {

  
	private static final Logger log = LoggerFactory.getLogger(NIOUtils.class);
	/** 
	 * first, writes the header, then the 
	 * event into the given ByteBuffer
	 * in preparation for the channel write
	 */
	public static void prepBuffer(ResponseEvent event, ByteBuffer writeBuffer) {
		// write header
		writeBuffer.clear();

		int type = event.getEventType().getNumber();
		//log.info("Setting eventType in writeBuffer to "+type);
		writeBuffer.put((byte) (type & 0xFF));
		writeBuffer.put((byte) ((type & 0xFF00) >> 8));
		writeBuffer.put((byte) ((type & 0xFF0000) >> 16));
		writeBuffer.put((byte) ((type & 0xFF000000) >> 24));
		
		int tag = event.getTag();
		//log.info("Setting tag in writeBuffer to "+tag);
		writeBuffer.put((byte) (tag & 0xFF));
		writeBuffer.put((byte) ((tag & 0xFF00) >> 8));
		writeBuffer.put((byte) ((tag & 0xFF0000) >> 16));
		writeBuffer.put((byte) ((tag & 0xFF000000) >> 24));

		int sizePos = writeBuffer.position();
		//log.info("Setting placeHolder for size in writeBuffer at "+sizePos);
		writeBuffer.putInt(0); // placeholder for payload size
		int size = event.write(writeBuffer);
		//log.info("Prepped buffer size: "+(size+12));
		// insert the payload size in the placeholder spot
		writeBuffer.put(sizePos, (byte) (size & 0xFF));
		writeBuffer.put(sizePos+1, (byte) ((size & 0xFF00) >> 8));
		writeBuffer.put(sizePos+2, (byte) ((size & 0xFF0000) >> 16));
		writeBuffer.put(sizePos+3, (byte) ((size & 0xFF000000) >> 24));

		// prepare for a channel.write
		writeBuffer.flip();
	}


	public static byte[] getByteArray(ResponseEvent event) {
		ByteBuffer writeBuffer = ByteBuffer.allocateDirect(Globals.MAX_EVENT_SIZE);
		NIOUtils.prepBuffer(event, writeBuffer);
		int remaining = writeBuffer.remaining();
		//log.info("Got byte[] of size: {}", remaining);
		byte[] b = new byte[remaining];
		writeBuffer.get(b);
		return b;
	}
	
	/**
	 * write a String to a ByteBuffer, 
	 * prepended with a short integer representing the length of the String
	 */
	public static void putStr(ByteBuffer buff, String str) {
		if (str == null) {
			buff.put((byte)0);
		}
		else {
			buff.put((byte)str.length());
			buff.put(str.getBytes());
		}
	}

	/**
	 * read a String from a ByteBuffer 
	 * that was written w/the putStr method
	 */
	public static String getStr(ByteBuffer buff) {
		byte len = buff.get();
		if (len == 0) {
			return null;
		}
		else {
			byte[] b = new byte[len];
			buff.get(b);
			return new String(b);
		}
	}


}
