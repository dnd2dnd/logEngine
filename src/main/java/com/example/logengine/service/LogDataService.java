package com.example.logengine.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.entity.LogData;
import com.example.logengine.repository.LogDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogDataService {
	private final LogDataRepository logDataRepository;

	public void saveLog(CollectorDto collectorDto) {
		LogData logData = LogData.builder()
			.timestamp(new Timestamp(collectorDto.getTimestamp()))
			.filename(collectorDto.getProduct())
			.filepath(collectorDto.getFilename())
			.msg(collectorDto.getLog())
			.build();
		logDataRepository.save(logData);
	}

}
