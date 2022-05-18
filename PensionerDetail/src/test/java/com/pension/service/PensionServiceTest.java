package com.pension.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pension.entity.BankDetail;
import com.pension.entity.DetailsOfPensioner;
import com.pension.exception.AadhaarNotFoundException;
import com.pension.repository.PensionerDetailRepository;
import com.pension.util.TypeOfBank;

@SpringBootTest
class PensionServiceTest {

	@Autowired
	private PensionService service;
	
	@MockBean
	private PensionerDetailRepository detailRepository;
	
	@BeforeEach
	void setup() {
		DetailsOfPensioner pensioner = DetailsOfPensioner.builder()
											.aadhaar(987654321098l)
											.allowances(2000)
											.dob(new Date())
											.name("ABC")
											.PAN("BAJPC4350M")
											.salaryEarned(15000)
											.bankDetail(
													BankDetail.builder()
													.accountNumber(67890543215l)
													.bankType(TypeOfBank.PUBLIC)
													.name("SBI").build())
											.build();
		Mockito.when(detailRepository.findByAadhaar(987654321098l)).thenReturn(pensioner);
	}
	
	@Test
	@DisplayName("CASE 1 :Get Pensioner Detail by using Aadhaar")
	void providePensionerDetailBasedOnAadhaarTest1() {
		long aadhaar = 987654321098l;
		DetailsOfPensioner pensioner = service.fetchDetails(aadhaar);
		assertThat(aadhaar).isEqualTo(pensioner.getAadhaar());
	}
	
	@Test
	@DisplayName("CASE 2 :Get Pensioner Detail by using Aadhaar")
	void providePensionerDetailBasedOnAadhaarTest2() {
		assertThatExceptionOfType(AadhaarNotFoundException.class).isThrownBy(()->{
			long aadhaar = 987654321099l;
			service.fetchDetails(aadhaar);			
		});
	}
	
}
