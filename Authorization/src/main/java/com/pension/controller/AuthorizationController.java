package com.pension.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pension.entity.LoginUserDetail;
import com.pension.service.UserDetailService;

@RestController
@CrossOrigin(value = { "http://localhost:4200" }, methods = { RequestMethod.POST })
public class AuthorizationController {
	
	private Logger log = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	private UserDetailService service;

	@GetMapping("/test")
	public String awsHealthCall(){
		return "API POSTMAN TEST ONLY : It is working in ECS Sevice Container - CI/CD \nTask Revision Created";
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody() Map<String,String> user) {
		log.info("User Recieved , Login service is initiated ...");
		LoginUserDetail usr = new LoginUserDetail(user.get("userName") ,user.get("password"));
		return service.loginDetail(usr);
	}

	@PostMapping(value = "/register" , produces="application/json" , consumes = "application/json")
	public ResponseEntity<Map<String,String>> register(@RequestBody Map<String,String> user) {
		log.info("User Recieved , Register service is initiated ...");
		LoginUserDetail usr = new LoginUserDetail(user.get("userName") ,user.get("password"));
		return service.saveUser(usr) ?
				new ResponseEntity<>(Map.of("message","Register"),HttpStatus.OK)
				: new ResponseEntity<>(Map.of("message","Not Register"),HttpStatus.OK);
	}

}

