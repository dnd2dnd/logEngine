package com.example.logengine.utils;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.logengine.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CommonResponse {
	private String msg;
	private int status;

	public static class ErrorResponse extends CommonResponse {
		private final String code;

		public ErrorResponse(String msg, int status, String code) {
			super(msg, status);
			this.code = code;
		}
	}

	@Getter
	public static class SingleResponse<T> extends CommonResponse {
		private final T data;

		public SingleResponse(String msg, int status, T data) {
			super(msg, status);
			this.data = data;
		}
	}

	@Getter
	public static class ListResponse<T> extends CommonResponse {
		private final List<T> data;

		public ListResponse(String msg, int status, List<T> data) {
			super(msg, status);
			this.data = data;
		}
	}

	public static ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage(), errorCode.getHttpStatus().value(), errorCode.getCode()));
	}

	public static ResponseEntity<CommonResponse> toCommonResponse(String msg, int status) {
		return ResponseEntity.status(status)
			.body(new CommonResponse(msg, status));
	}

	public static <T> ResponseEntity<SingleResponse<T>> toSingleResponse(String msg, int status, T data) {
		return ResponseEntity.status(status)
			.body(new SingleResponse<>(msg, status, data));
	}

	public static <T> ResponseEntity<ListResponse<T>> toListResponse(String msg, int status,
		List<T> data) {
		return ResponseEntity.status(status)
			.body(new ListResponse<>(msg, status, data));
	}

}
