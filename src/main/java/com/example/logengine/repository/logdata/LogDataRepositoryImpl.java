package com.example.logengine.repository.logdata;

import static com.example.logengine.entity.QLogData.*;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.logengine.dto.LogResponse;
import com.example.logengine.entity.LogData;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class LogDataRepositoryImpl extends QuerydslRepositorySupport implements LogDataRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public LogDataRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		super(LogData.class);
		this.queryFactory = jpaQueryFactory;
	}

	@Override
	public List<LogResponse> getLogData(String filename, String strStamp, String endStamp) {
		return queryFactory.select(
				Projections.bean(LogResponse.class, logData.rawSn, logData.timestamp, logData.filename,
					logData.filepath, logData.msg))
			.from(logData)
			.where(containsName(filename), afterTime(strStamp), beforeTime(endStamp))
			.fetch();
	}

	private BooleanExpression containsName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}
		return logData.filename.contains(name);
	}

	private BooleanExpression afterTime(String timestamp) {
		if (timestamp == null || timestamp.isEmpty()) {
			return null;
		}
		return logData.timestamp.after(Timestamp.valueOf(timestamp));
	}

	private BooleanExpression beforeTime(String timestamp) {
		if (timestamp == null || timestamp.isEmpty()) {
			return null;
		}
		return logData.timestamp.before(Timestamp.valueOf(timestamp));
	}
}
