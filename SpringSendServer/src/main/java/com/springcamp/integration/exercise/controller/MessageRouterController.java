package com.springcamp.integration.exercise.controller;

import com.springcamp.integration.exercise.service.JmsSender;
import com.springcamp.integration.exercise.service.NodejsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wglee21g@gmail.com
 */
@RestController
public class MessageRouterController {
	@Autowired
	private JmsSender jmsSender;

	@Autowired
	private NodejsSender nodejsSender;

	@RequestMapping(value = "/send/jms", method = {RequestMethod.GET})
	public String jmsMessageSend(String messageContent) {
		jmsSender.sendMessage(messageContent);

		return "[JMS Message] " + messageContent + " Send OK";
	}

	@RequestMapping(value = "/send/nodejs", method = {RequestMethod.GET})
	public String nodejsMessage(String messageContent) {
		nodejsSender.sendMessage(messageContent);

		return "[NodeJS Message] " + messageContent + " Send OK";
	}
}
