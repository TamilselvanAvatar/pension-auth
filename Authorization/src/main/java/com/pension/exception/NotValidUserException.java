package com.pension.exception;

public class NotValidUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotValidUserException(String message) {
		super(message);
	}
}
