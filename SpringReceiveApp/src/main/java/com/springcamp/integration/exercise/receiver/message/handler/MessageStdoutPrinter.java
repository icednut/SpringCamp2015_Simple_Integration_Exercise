package com.springcamp.integration.exercise.receiver.message.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * @author wangeun.lee@sk.com
 */
@Component
public class MessageStdoutPrinter implements MessageHandler {
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		if (message == null) {
			return;
		}

		String payload = (String) message.getPayload();
		System.out.println("*** Payload Received! " + payload);
	}
}
