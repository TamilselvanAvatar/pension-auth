package com.pension.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pension.model.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotInLengthException.class)
	protected ResponseEntity<ExceptionModel> NotInLengthException(NotInLengthException nile) {
		String message = nile.getMessage();
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(message, "Aadhaar Number should be 12 digit Number", date, true);
		return ResponseEntity.badRequest().body(exp);
	}

	@ExceptionHandler(DataNotFoundException.class)
	protected ResponseEntity<ExceptionModel> DataNotFoundException(DataNotFoundException dnfe) {
		String message = dnfe.getMessage();
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(message, "Cross Check the Aadhaar number once again", date, true);
		return ResponseEntity.badRequest().body(exp);
	}

	@ExceptionHandler(JwtTokenExpiredException.class)
	protected ResponseEntity<ExceptionModel> handleJwtTokenExpiredException(JwtTokenExpiredException jtee) {
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(jtee.getMessage(), "Provide new Token", date, true);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exp);
	}

	@ExceptionHandler(JwtTokenEmptyException.class)
	protected ResponseEntity<ExceptionModel> handleJwtTokenEmptyException(JwtTokenEmptyException jtmee) {
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(jtmee.getMessage(), "Authorization value should not empty", date, true);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exp);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ExceptionModel> handleAllException(Exception e) {
		String date = new Date().toString();
		String message = e.toString() + "\n" + e.getMessage();
		ExceptionModel exp = new ExceptionModel(message, e.getLocalizedMessage(), date, true);
		return ResponseEntity.badRequest().body(exp);
	}

}
