package com.example.logengine.service;

import static com.example.logengine.utils.search.SearchConstants.*;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.entity.FilterInfo;
import com.example.logengine.message.slack.SlackService;
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
	private ObjectMapper mapper = new ObjectMapper();

	private final SearchService searchService;
	private final SlackService slackService;
	private final RedisCacheService redisCacheService;


	public void doFindKafka(String message) throws JsonProcessingException {
		CollectorDto collectorDto = mapper.readValue(message, CollectorDto.class);
		String msg = collectorDto.getLog();
		String filename = collectorDto.getProduct();
		if(msg == null) {
			return;
		}

		// System.out.println(collectorDto);

		if(filename!= null && findStr(msg, filename)) {
			slackService.sendMessage(msg);
		}
	}

	public void doFind(CollectorDto collectorDto) {
		String msg = collectorDto.getLog();
		String filename = collectorDto.getProduct();
		if(msg == null) {
			return;
		}

		System.out.println(msg);

		if(filename!= null && findStr(msg, filename)) {
			slackService.sendMessage(msg);
		}
	}

	public boolean findStr(String msg, String filename) {
		searchService.setAlgorithm(algorithm);
		System.out.println("시작 !!!");
		List<FilterInfo> list = redisCacheService.getFilterInfoCache(filename);
		for (FilterInfo filterInfo : list) {
			String s = filterInfo.getMsg();
		    List<String> findStr = stringSep(s);

		    if (findStr.size() == 1) {
		        // 하나의 문자열만 찾으면 됨.
		        if (searchService.find(msg, s)) {
		            return true;
		        }
		    } else if (findStr.size() > 1){
		        // 두개의 and 조건을 만족해야 함.
				for (String fs : findStr) {
					if(!searchService.find(msg, fs)) {
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
