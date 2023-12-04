package com.example.logengine.repository.logdata;

import java.util.List;

import com.example.logengine.dto.LogResponse;

public interface LogDataRepositoryCustom {
	List<LogResponse> getLogData(String filename, String strStamp, String endStamp);
}
