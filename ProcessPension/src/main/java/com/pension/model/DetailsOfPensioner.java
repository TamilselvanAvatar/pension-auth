package com.pension.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailsOfPensioner {
	private long id;
	private String name;
	private Date dob;
	private long aadhaar;
	private String PAN;
	private double salaryEarned;
	private int allowances;
	private String typeofPension;
	private BankDetail bankDetail;
}
