package com.example.logengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.logengine.entity.LogData;

@Repository
public interface LogDataRepository extends JpaRepository<LogData, Long> {
}
