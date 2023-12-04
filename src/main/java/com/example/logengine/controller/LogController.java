package com.example.logengine.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.logengine.dto.LogFilterInfoDto;
import com.example.logengine.dto.LogResponse;
import com.example.logengine.service.LogDataService;
import com.example.logengine.service.LogFilterService;
import com.example.logengine.utils.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {
	private final LogFilterService logFilterService;
	private final LogDataService logDataService;

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

	@PostMapping("/api/v1/filter")
	public ResponseEntity<CommonResponse> addFilter(@RequestBody LogFilterInfoDto request) {
		return logFilterService.addLogFilterInfo(request);
	}

	@GetMapping("/api/v1/log")
	public ResponseEntity<CommonResponse.ListResponse<LogResponse>> getLogData(
		@RequestParam(name = "filename", required = false) String filename,
		@RequestParam(name = "start_date", required = false) String startDate,
		@RequestParam(name = "end_data", required = false) String endDate) {
		return logDataService.getLogData(filename, startDate, endDate);
	}
}
