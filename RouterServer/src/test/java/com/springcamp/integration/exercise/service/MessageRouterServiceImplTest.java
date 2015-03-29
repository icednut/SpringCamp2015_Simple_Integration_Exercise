package com.springcamp.integration.exercise.service;

import com.springcamp.integration.exercise.config.MessageSendConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * @author wangeun.lee@sk.com
 */
public class MessageRouterServiceImplTest {
	@Test
	public void testSendJmsMessageSend() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MessageSendConfig.class);
//		MessageRouterService service = applicationContext.getBean("messageRouterServiceImpl", MessageRouterServiceImpl.class);
		DirectChannel jmsChannel = applicationContext.getBean("jmsChannel", DirectChannel.class);

		Message<String> message = MessageBuilder.withPayload("Hello JMS").build();
		jmsChannel.send(message);

		applicationContext.close();
	}

//	@Test
//	public void testSendNodejsMessage() throws Exception {
//		String message = "Hello Nodejs";
//		service.sendAtNodejs(message);
//	}
}