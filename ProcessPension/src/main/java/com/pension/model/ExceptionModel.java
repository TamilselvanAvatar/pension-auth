package com.pension.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionModel {
	private String message;
	private String solution;
	private String timeStamp;
	private boolean error;
}
