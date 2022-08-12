package com.pension.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;

import com.pension.model.BankDetail;
import com.pension.model.DetailsOfPensioner;
import com.pension.service.PensionDefaultService;

@WebMvcTest(value = { ProcessPensionController.class })
class PensionDetailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PensionDefaultService service;

	DetailsOfPensioner pensioner;

	@BeforeEach
	void setUp() {
		BankDetail bankDetail = BankDetail.builder().accountNumber(89076543245l).bankType("PUBLIC").name("IOB").build();

		pensioner = DetailsOfPensioner.builder().aadhaar(987654321098l).allowances(2000).dob(Date.valueOf("2022-12-12"))
				.name("ABC").pAN("BAJPC4350N").salaryEarned(15000).bankDetail(bankDetail).build();
	}

	@Test
	void getPensionerByAadhaarTest1() throws Exception {
		Mockito.when(service.validateAadhaar(Map.of("aadhaar", 987654321098l))).thenReturn(987654321098l);
		Mockito.when(service.getPensionerFromServiceInstance("pensioner-detail", 987654321098l, ""))
				.thenReturn(pensioner);
		Mockito.when(service.calculatedPensionDetails(pensioner))
				.thenReturn(new HashMap<>(Map.of("BankServiceCharge", 550.0, "PensionAmount", 14000.0)));
		mockMvc.perform(MockMvcRequestBuilders.post("/ProcessPension").header("Authorization", "")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content("{ \"aadhaar\" : 987654321098 }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.BankServiceCharge").value(550.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.PensionAmount").value(14000.0))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void getPensionerByAadhaarTest2() throws Exception {
		Mockito.when(service.validateAadhaar(Map.of("aadhaar", 987654321099l))).thenReturn(987654321099l);
		Mockito.when(service.getPensionerFromServiceInstance("pensioner-detail", 987654321099l, ""))
				.thenThrow(HttpClientErrorException.class);
		Mockito.when(service.calculatedPensionDetails(null))
				.thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/ProcessPension").header("Authorization", "")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content("{ \"aadhaar\" : 987654321099 }"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

}
