package com.example.logengine.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	FORBIDDEN_TOKEN("T001", HttpStatus.FORBIDDEN, "권한이 없습니다"),
	VIDEO_NOT_FOUND("F001", HttpStatus.NOT_FOUND, "해당 파일 정보가 없습니다."),
	USER_NOT_FOUND("U001", HttpStatus.NOT_FOUND, "해당 유저 정보가 없습니다.");
	private final String code;
	private final HttpStatus httpStatus;
	private final String message;
}
