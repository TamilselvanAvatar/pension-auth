package com.pension.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pension.entity.LoginUserDetail;
import com.pension.service.UserDetailService;

@RestController
@CrossOrigin(value = { "http://localhost:4200" }, methods = { RequestMethod.POST })
public class AuthorizationController {

	@Autowired
	private UserDetailService service;

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginUserDetail user) {
		return service.loginDetail(user);
	}

	@PostMapping(value = "/register" , produces="application/json" , consumes = "application/json")
	public ResponseEntity<?> register(@RequestBody LoginUserDetail user) {
		
		return service.saveUser(user) ?
				new ResponseEntity<Object>(Map.of("message","Register"),HttpStatus.OK)
				: new ResponseEntity<Object>(Map.of("message","Not Register"),HttpStatus.OK);
	}

}
