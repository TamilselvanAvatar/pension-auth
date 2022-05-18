package com.pension.exception;

public class NotInLengthException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NotInLengthException(String message) {
		super(message);
	}

}
