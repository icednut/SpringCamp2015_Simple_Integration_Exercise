package com.springcamp.integration.exercise.receive.config;

import com.springcamp.integration.exercise.receiver.config.MessageReceiveConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author wangeun.lee@sk.com
 */
public class MessageReceiveTest {
	@Test
//	@Ignore
	public void testGetMessage_pointToPoint() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MessageReceiveConfig.class);
		QueueChannel jmsInChannel = applicationContext.getBean("jmsInChannel", QueueChannel.class);
		Message<String> message = (Message<String>) jmsInChannel.receive(60000);
		String payload = message.getPayload();

		System.out.println("******************** " + payload);
		applicationContext.close();
	}

	@Test
	@Ignore
	public void testRecevieMessage_pubSub() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MessageReceiveConfig.class);

		DirectChannel jmsInChannel = applicationContext.getBean("jmsInChannel", DirectChannel.class);
		jmsInChannel.subscribe(new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				String payload = (String) message.getPayload();
				System.out.println(payload);
			}
		});

		applicationContext.close();
	}
}
