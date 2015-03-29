package com.springcamp.integration.exercise.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;

/**
 * @author wglee21g@gmail.com
 */
@Configuration
public class MessageSendConfig {
	@Value("#{message.queue.url}")
	private String messageQueueUrl;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public BrokerService brokerService() throws Exception {
		BrokerService brokerService = new BrokerService();

		brokerService.setBrokerName("localhost");
		brokerService.setUseJmx(false);
		brokerService.setUseShutdownHook(false);
		brokerService.addConnector(messageQueueUrl);
		brokerService.setPersistent(false);
		return brokerService;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		connectionFactory.setBrokerURL(messageQueueUrl);
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
	public DirectChannel jmsChannel() {
		return new DirectChannel();
	}

	@Bean
	public EventDrivenConsumer jmsOutboundMessageAdapter() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setDefaultDestination(requestQueue());
		MessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
		EventDrivenConsumer consumer = new EventDrivenConsumer(jmsChannel(), messageHandler);
		return consumer;
	}

	@Bean
	public MessageChannel nodejsChannel() {
		return MessageChannels.direct("nodejsChannel").get();
	}
}
