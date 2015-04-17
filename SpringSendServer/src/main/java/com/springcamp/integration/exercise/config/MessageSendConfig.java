package com.springcamp.integration.exercise.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wglee21g@gmail.com
 */
@Configuration
public class MessageSendConfig {
	@Value("${message.queue.url}")
	private String messageQueueUrl;

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
	public DirectChannel jmsOutChannel() {
		return new DirectChannel();
	}

	@Bean
	public EventDrivenConsumer jmsOutboundMessageAdapter() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setDefaultDestination(requestQueue());
		MessageHandler messageHandler = new JmsSendingMessageHandler(jmsTemplate);
		EventDrivenConsumer consumer = new EventDrivenConsumer(jmsOutChannel(), messageHandler);
		return consumer;
	}

	@Bean
	public DirectChannel nodejsOutChannel() {
		return new DirectChannel();
	}

	@Bean
	public EventDrivenConsumer nodejsOutboundMessageAdapter() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new FormHttpMessageConverter());

		HttpRequestExecutingMessageHandler messageHandler = new HttpRequestExecutingMessageHandler("http://54.69.214.93:1337");
		messageHandler.setHttpMethod(HttpMethod.POST);
		messageHandler.setMessageConverters(messageConverters);
		messageHandler.setOutputChannel(new NullChannel());
		EventDrivenConsumer consumer = new EventDrivenConsumer(nodejsOutChannel(), messageHandler);
		return consumer;
	}
}
