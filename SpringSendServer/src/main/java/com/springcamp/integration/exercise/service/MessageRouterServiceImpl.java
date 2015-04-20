package com.springcamp.integration.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wglee21g@gmail.com
 */
@Service
public class MessageRouterServiceImpl implements MessageRouterService {

	@Override
	public void sendAtJms(String messageContent) {
//		jmsSender.sendMessage(messageContent);
	}

	@Override
	public void sendAtNodejs(String messageContent) {
//		nodejsSender.sendMessage(messageContent);
	}
}
