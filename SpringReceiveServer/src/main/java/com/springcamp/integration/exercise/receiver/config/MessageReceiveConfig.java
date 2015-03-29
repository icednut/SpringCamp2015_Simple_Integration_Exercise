package com.springcamp.integration.exercise.receiver.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;

import javax.jms.ConnectionFactory;

/**
 * @author wglee21g@gmail.com
 */
@Configuration
public class MessageReceiveConfig {
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		connectionFactory.setBrokerURL("tcp://localhost:61616");
		cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);
		cachingConnectionFactory.setSessionCacheSize(10);
		cachingConnectionFactory.setCacheProducers(false);
		return cachingConnectionFactory;
	}

	@Bean
	public ActiveMQQueue requestQueue() {
		ActiveMQQueue queue = new ActiveMQQueue();

		queue.setPhysicalName("request.queue");
		return queue;
	}

	@Bean
	public MessageChannel jmsInChannel() {
//		return new QueueChannel(10);
		return new DirectChannel();
	}

	@Bean
	public JmsMessageDrivenEndpoint jmsIn() {
		AbstractMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
		ChannelPublishingJmsMessageListener listener = new ChannelPublishingJmsMessageListener();
		JmsMessageDrivenEndpoint jmsIn = new JmsMessageDrivenEndpoint(listenerContainer, listener);

		listenerContainer.setDestination(requestQueue());
		listenerContainer.setConnectionFactory(connectionFactory());
		listener.setRequestChannel(jmsInChannel());
		return jmsIn;
	}

	@Bean
	EventDrivenConsumer jmsInboundMessageAdaptor(MessageHandler messageStdoutPrinter) {
		EventDrivenConsumer consumer = new EventDrivenConsumer((SubscribableChannel) jmsInChannel(), messageStdoutPrinter);
		return consumer;
	}

//	@Bean
//	public EventDrivenConsumer jmsOutboundMessageAdapter() {
//		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
//		jmsTemplate.setDefaultDestination(requestQueue());
//		MessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
//		EventDrivenConsumer consumer = new EventDrivenConsumer(jmsOutChannel(), messageHandler);
//		return consumer;
//	}
//
//	@Bean
//	public SubscribableChannel jmsOutChannel() {
//		return new DirectChannel();
//	}
}
