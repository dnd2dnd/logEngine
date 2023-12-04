package com.example.logengine.service;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.dto.LogResponse;
import com.example.logengine.entity.LogData;
import com.example.logengine.repository.logdata.LogDataRepository;
import com.example.logengine.utils.CommonResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogDataService {
	private final LogDataRepository logDataRepository;

	public void saveLog(CollectorDto collectorDto) {
		String timestamp = collectorDto.getTimestamp().replace(".", "").substring(0, 13);
		LogData logData = LogData.builder()
			.timestamp(new Timestamp(Long.parseLong(timestamp)))
			.filename(collectorDto.getProduct())
			.filepath(collectorDto.getFilename())
			.msg(collectorDto.getLog())
			.build();
		logDataRepository.save(logData);
	}

	public ResponseEntity<CommonResponse.ListResponse<LogResponse>> getLogData(String filename, String startDate,
		String endDate) {
		return CommonResponse.ListResponse.toListResponse("로그 조회 성공", HttpStatus.OK.value(),
			logDataRepository.getLogData(filename, startDate, endDate));
	}
}
