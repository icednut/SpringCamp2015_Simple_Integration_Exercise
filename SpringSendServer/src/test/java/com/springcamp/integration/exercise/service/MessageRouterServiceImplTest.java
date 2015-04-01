package com.springcamp.integration.exercise.service;

import com.springcamp.integration.exercise.config.MessageSendConfig;
import org.junit.Ignore;
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
	@Ignore
	public void testSendJmsMessageSend() throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MessageSendConfig.class);
		DirectChannel jmsChannel = applicationContext.getBean("jmsOutChannel", DirectChannel.class);

		Message<String> message = MessageBuilder.withPayload("Hello JMS").build();
		jmsChannel.send(message);

		applicationContext.close();
	}
}