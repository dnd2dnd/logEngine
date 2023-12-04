package com.example.logengine.utils.search;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.logengine.dto.LogFilterInfoDto;
import com.example.logengine.entity.LogFilterInfo;
import com.example.logengine.repository.LogFilterInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheService {
	private final LogFilterInfoRepository logFilterInfoRepository;

	@Cacheable(cacheNames = "getFilterInfoCache", key = "#filename")
	public List<LogFilterInfo> getFilterInfoCache(String filename) {
		return logFilterInfoRepository.findByFileName(filename);
	}

	@CacheEvict(cacheNames = "getFilterInfoCache", key = "#request.filename")
	public void addLogFilterInfo(LogFilterInfoDto request) {
		LogFilterInfo logFilterInfo = LogFilterInfo.builder()
			.fileName(request.getFilename())
			.msg(request.getMsg())
			.build();
		logFilterInfoRepository.save(logFilterInfo);
	}
}