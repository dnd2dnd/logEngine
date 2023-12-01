package com.example.logengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.logengine.entity.LogFilterInfo;

@Repository
public interface LogFilterInfoRepository extends JpaRepository<LogFilterInfo, Integer> {
	List<LogFilterInfo> findByFileName(String fileName);
}
