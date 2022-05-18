package com.pension.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pension.entity.LoginUserDetail;
import com.pension.exception.NotValidUserException;
import com.pension.service.UserDetailService;

@WebMvcTest(value = { AuthorizationController.class })
class AuthorizationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserDetailService service;

	@BeforeEach
	void setUp() {
		LoginUserDetail user = LoginUserDetail.builder().userName("abcd").password("abcd").build();
		Mockito.when(service.loginDetail(user))
				.thenReturn(new ResponseEntity<>(new HashMap<>(Map.of("token", "someToken")), HttpStatus.OK));
	}

	@Test
	void getTokenTest1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{ \"userName\": \"abcd\" , \"password\" : \"abcd\" }"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void getTokenTest2() throws Exception {
		LoginUserDetail user = LoginUserDetail.builder().userName("abc").password("abcd").build();
		Mockito.when(service.loginDetail(user)).thenThrow(new NotValidUserException("In valid Credentials"));
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{ \"userName\": \"abc\" , \"password\" : \"abcd\" }"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void registerUserTest1() throws Exception {
		LoginUserDetail user = LoginUserDetail.builder().userName("asdf").password("aasdf").build();
		Mockito.when(service.saveUser(user)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{ \"userName\": \"asdf\" , \"password\" : \"aasdf\" }"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Register"));

	}
	
	@Test
	void registerUserTest2() throws Exception {
		LoginUserDetail user = LoginUserDetail.builder().userName("asdf").password("aasdf").build();
		Mockito.when(service.saveUser(user)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{ \"userName\": \"asdf\" , \"password\" : \"aasdf\" }"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not Register"));


	}

}
