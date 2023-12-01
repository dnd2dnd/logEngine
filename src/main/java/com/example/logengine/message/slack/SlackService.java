package com.example.logengine.message.slack;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SlackService {
	private final String PREFIX = "#";
	@Value("${slack.token}")
	public String slackToken;
	@Value("${slack.channel}")
	public String slackChannel;

	public void sendMessage(String message) {
		try {
			MethodsClient methodsClient = Slack.getInstance().methods(slackToken);
			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel(PREFIX + slackChannel)
				.text(message)
				.build();
			methodsClient.chatPostMessage(request);
			log.info(PREFIX + slackChannel + "에 " + message + " 메세지 보냄");
		} catch (IOException | SlackApiException e) {
			log.error(e.getMessage());
		}
	}
}
