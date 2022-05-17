package com.pension.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pension.entity.LoginUserDetail;
import com.pension.exception.NotValidUserException;
import com.pension.repository.UserDetailRepository;
import com.pension.util.JwtUtil;

@Service
public class UserDetailService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailRepository repository;

	public boolean verifyUser(LoginUserDetail user) {
		String name = user.getUserName();
		String password = user.getPassword();
		LoginUserDetail dbUser = repository.findByUserName(name);
		return dbUser != null && dbUser.getPassword().equals(password) ? true : false;
	}

	public boolean saveUser(LoginUserDetail user) {
		String name = user.getUserName();
		LoginUserDetail dbUser = repository.findByUserName(name);
		LoginUserDetail saved = null;
		if(dbUser == null) { 
			saved = repository.save(user);
		};
		return saved != null ? true : false;
	}

	public ResponseEntity<Map<String, String>> loginDetail(LoginUserDetail user) {
		String token = "";
		if (this.verifyUser(user)) {
			token = jwtUtil.generateToken(user.getUserName());
		} else {
			throw new NotValidUserException("In valid Credentials");
		}
		return new ResponseEntity<>(new HashMap<>(Map.of("token", token)), HttpStatus.OK);
	}

}
