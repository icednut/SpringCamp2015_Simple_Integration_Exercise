package com.springcamp.integration.exercise.service;

/**
 * @author wglee21g@gmail.com
 */
public interface MessageRouterService {
	void sendAtNodejs(String messageContent);

	void sendAtJms(String messageContent);
}
