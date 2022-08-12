package com.pension.exception;

import java.util.Date;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pension.model.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotInLengthException.class)
	protected ResponseEntity<ExceptionModel> notInLengthException(NotInLengthException nile) {
		String message = nile.getMessage();
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(message, "Aadhaar Number should be 12 digit Number", date, true);
		return ResponseEntity.badRequest().body(exp);
	}

	@ExceptionHandler(DataNotFoundException.class)
	protected ResponseEntity<ExceptionModel> dataNotFoundException(DataNotFoundException dnfe) {
		String message = dnfe.getMessage();
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(message, "Cross Check the Aadhaar number once again", date, true);
		return ResponseEntity.badRequest().body(exp);
	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<ExceptionModel> handleJwtTokenEmptyException(AuthenticationException ae) {
		String date = new Date().toString();
		ExceptionModel exp = new ExceptionModel(ae.getMessage(), "Provide Token", date, true);
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
