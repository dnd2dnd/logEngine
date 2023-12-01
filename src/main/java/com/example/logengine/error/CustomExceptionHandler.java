package com.example.logengine.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.logengine.utils.CommonResponse;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<CommonResponse.ErrorResponse> handleCustomException(CustomException exception) {
		return CommonResponse.toErrorResponse(exception.getErrorCode());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors()
			.forEach(c -> errors.put(((FieldError)c).getField(), c.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
}
