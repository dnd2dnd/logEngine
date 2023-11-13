package com.example.logengine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.service.LogFilterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogInputController {
	private final LogFilterService logFilterService;

	@PostMapping("/collector/logs")
	public void logCollector(@RequestBody List<CollectorDto> data) {
		data.forEach(logFilterService::doFind);
	}

	@PostMapping("/collector/heartbeat")
	public void logCollector() {
		log.info("heartbeat !!");
	}

}
