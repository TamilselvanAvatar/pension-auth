package com.pension.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetail {
	private long id;
	private String name;
	private long accountNumber;
	private String bankType;
}
