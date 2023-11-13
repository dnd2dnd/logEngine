package com.example.logengine.service;

import static com.example.logengine.utils.search.SearchConstants.*;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.logengine.dto.CollectorDto;
import com.example.logengine.entity.FilterInfo;
import com.example.logengine.repository.FilterInfoRepository;
import com.example.logengine.message.slack.SlackService;
import com.example.logengine.utils.search.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogFilterService {
	@Value("${search.algorithm}")
	private String algorithm;
	private final FilterInfoRepository filterInfoRepository;
	private final SearchService searchService;
	private final SlackService slackService;

	public void doFind(CollectorDto collectorDto) {
		String msg = collectorDto.getLog();
		if(msg == null) {
			return;
		}

		System.out.println(msg);

		if(findStr(msg)) {
			slackService.sendMessage(msg);
		}
	}

	private boolean findStr(String msg) {
		searchService.setAlgorithm(algorithm);
		for (FilterInfo filterInfo : filterInfoRepository.findByFileName("server")) {
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
