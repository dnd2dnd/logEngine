package com.example.logengine.utils.search;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

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

}