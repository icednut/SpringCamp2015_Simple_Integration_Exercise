package com.springcamp.integration.exercise.receiver.message.handler;

import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author wangeun.lee@sk.com
 */
public class MessageStdoutPrinter {
	public void handleMessage(String message) throws MessagingException {
		System.out.println("*** Payload Received! " + message);
	}
}
