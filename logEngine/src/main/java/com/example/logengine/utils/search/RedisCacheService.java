package com.example.logengine.utils.search;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.logengine.entity.FilterInfo;
import com.example.logengine.repository.FilterInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheService {
	private final FilterInfoRepository filterInfoRepository;

	@Cacheable(cacheNames = "getFilterInfoCache", key = "#filename")
	public List<FilterInfo> getFilterInfoCache(String filename) {
		System.out.println("getFilterInfoCache 메소드 호출");
		// System.out.println("Class Name: " + this.getClass().getName());
		return filterInfoRepository.findByFileName(filename);
	}

}