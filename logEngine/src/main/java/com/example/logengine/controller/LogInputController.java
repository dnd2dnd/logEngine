package com.example.logengine.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.entity.FilterInfo;
import com.example.logengine.service.LogFilterService;
import com.example.logengine.utils.search.RedisCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogInputController {
	private final LogFilterService logFilterService;

	@PostMapping("/collector/logs")
	public void logCollector(@RequestBody List<CollectorDto> data) {
		// data.forEach(logFilterService::doFind);
	}

	@PostMapping("/collector/heartbeat")
	public void logCollector() {
		log.info("heartbeat !!");
	}

	@KafkaListener(
		topics = "${message.topic.name}",
		groupId = "${spring.kafka.group-id}",
		concurrency = "10"
	)
	public void consume(String message) throws IOException {
		logFilterService.doFindKafka(message);
	}

}
