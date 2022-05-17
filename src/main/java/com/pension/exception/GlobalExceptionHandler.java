package com.pension.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pension.entity.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotValidUserException.class)
	protected ResponseEntity<ExceptionModel> handleAadhaarNotFoundException(NotValidUserException nvue) {
		String date = new Date().toString();
		String message = nvue.getMessage();
		ExceptionModel exp = new ExceptionModel(message, "Give valid credentials", date, true);
		return ResponseEntity.badRequest().body(exp);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ExceptionModel> handleAllException(Exception e) {
		String date = new Date().toString();
		String message = e.toString() + "\n" + e.getMessage();
		ExceptionModel exp = new ExceptionModel(message, e.getLocalizedMessage(), date, true);
		return ResponseEntity.badRequest().body(exp);
	}
	
}
