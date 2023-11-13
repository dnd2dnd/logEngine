package com.example.logengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.logengine.entity.FilterInfo;

@Repository
public interface FilterInfoRepository extends JpaRepository<FilterInfo, Long> {
	List<FilterInfo> findByFileName(String fileName);
}
