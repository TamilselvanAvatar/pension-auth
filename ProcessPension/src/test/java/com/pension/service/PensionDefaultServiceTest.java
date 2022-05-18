package com.pension.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.sql.Date;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pension.exception.NotInLengthException;
import com.pension.model.BankDetail;
import com.pension.model.DetailsOfPensioner;

@SpringBootTest
class PensionDefaultServiceTest {

	@Autowired
	private PensionDefaultService service;

	DetailsOfPensioner pensioner;

	@BeforeEach
	void setup() {
		pensioner = DetailsOfPensioner.builder()
				.aadhaar(987654321098l).allowances(2000).dob(Date.valueOf("2022-12-12"))
				.name("ABC").PAN("BAJPC4350M").salaryEarned(15000).typeofPension("SELF")
				.bankDetail(BankDetail.builder().accountNumber(67890543215l).bankType("PUBLIC").name("SBI").build())
				.build();
	}

	@Test
	@DisplayName("CASE 1 : Verfying Calculated Pension Amount and Service Charge")
	void calculatedPensionTest1() {
			Map<String,Object> result = service.calculatedPensionDetails(pensioner);
			double objB = 500.0;
			double objP = 14000.0;
			assertThat(objB).isEqualTo(result.get("BankServiceCharge"));
			assertThat(objP).isEqualTo((double)result.get("PensionAmount"));
	}
	
	@Test
	@DisplayName("CASE 2 :Verfying Calculated Pension Amount and Service Charge")
	void calculatedPensionTest2() {
		pensioner.getBankDetail().setBankType("PRIVATE");
		Map<String,Object> result = service.calculatedPensionDetails(pensioner);
		double objB = 550.0;
		double objP = 14000.0;
		assertThat(objB).isEqualTo(result.get("BankServiceCharge"));
		assertThat(objP).isEqualTo((double)result.get("PensionAmount"));
	}
	
	@Test
	@DisplayName("CASE 3 :Verfying Calculated Pension Amount and Service Charge")
	void calculatedPensionTest3() {
		pensioner.setTypeofPension("FAMILY");
		Map<String,Object> result = service.calculatedPensionDetails(pensioner);
		double objB = 500.0;
		double objP = 9500.0;
		assertThat(objB).isEqualTo(result.get("BankServiceCharge"));
		assertThat(objP).isEqualTo((double)result.get("PensionAmount"));
	}
	
	@Test
	@DisplayName("CASE 4 :Verfying Calculated Pension Amount and Service Charge")
	void calculatedPensionTest4() {
		pensioner.getBankDetail().setBankType("PRIVATE");
		pensioner.setTypeofPension("FAMILY");
		Map<String,Object> result = service.calculatedPensionDetails(pensioner);
		double objB = 550.0;
		double objP = 9500.0;
		assertThat(objB).isEqualTo(result.get("BankServiceCharge"));
		assertThat(objP).isEqualTo((double)result.get("PensionAmount"));
	}
	
	@Test
	@DisplayName("CASE 1 :Verifying Aadhaar Number")
	void validateAadhaarTest1() {
		long aadhaar = service.validateAadhaar(Map.of("aadhaar",987654321098l));
		assertThat(987654321098l).isEqualTo(aadhaar);
	}
	
	@Test
	@DisplayName("CASE 2 :Verifying Aadhaar Number")
	void validateAadhaarTest2() {
		assertThatExceptionOfType(NotInLengthException.class).isThrownBy(()->{
			service.validateAadhaar(Map.of("aadhaar",98765432108l));			
		});
	}

}
