package com.example.logengine.service;

import static com.example.logengine.utils.CommonResponse.*;
import static com.example.logengine.utils.search.SearchConstants.*;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.dto.LogFilterInfoDto;
import com.example.logengine.entity.LogFilterInfo;
import com.example.logengine.message.slack.SlackService;
import com.example.logengine.repository.LogFilterInfoRepository;
import com.example.logengine.utils.CommonResponse;
import com.example.logengine.utils.search.RedisCacheService;
import com.example.logengine.utils.search.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogFilterService {
	@Value("${search.algorithm}")
	private String algorithm;
	private final ObjectMapper mapper = new ObjectMapper();
	private final SearchService searchService;
	private final SlackService slackService;
	private final RedisCacheService redisCacheService;
	private final LogDataService logDataService;
	private final LogFilterInfoRepository logFilterInfoRepository;

	public ResponseEntity<CommonResponse> addLogFilterInfo(LogFilterInfoDto request) {
		LogFilterInfo logFilterInfo = LogFilterInfo.builder()
			.fileName(request.getFilename())
			.msg(request.getMsg())
			.build();
		logFilterInfoRepository.save(logFilterInfo);
		return toCommonResponse("로그 필터 추가", HttpStatus.OK.value());
	}

	public void doFindKafka(String message) throws JsonProcessingException {
		CollectorDto collectorDto = mapper.readValue(message, CollectorDto.class);
		String msg = collectorDto.getLog();
		String filename = collectorDto.getProduct();
		if (msg == null) {
			return;
		}

		if (filename != null && findStr(msg, filename)) {
			slackService.sendMessage(msg);
		}
		logDataService.saveLog(collectorDto);
	}

	public boolean findStr(String msg, String filename) {
		searchService.setAlgorithm(algorithm);
		List<LogFilterInfo> list = redisCacheService.getFilterInfoCache(filename);
		for (LogFilterInfo logFilterInfo : list) {
			String s = logFilterInfo.getMsg();
			List<String> findStr = stringSep(s);

			if (findStr.size() == 1) {
				// 하나의 문자열만 찾으면 됨.
				if (searchService.find(msg, s)) {
					return true;
				}
			} else if (findStr.size() > 1) {
				// 두개의 and 조건을 만족해야 함.
				for (String fs : findStr) {
					if (!searchService.find(msg, fs)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private List<String> stringSep(@NotNull String inputStr) {
		return Arrays.asList(inputStr.split(STR_DIV_SEP));
	}
}
