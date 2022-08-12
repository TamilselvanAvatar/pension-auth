package com.pension.controller;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pension.model.DetailsOfPensioner;
import com.pension.service.PensionDefaultService;

@CrossOrigin(value = { "http://localhost:4200/" }, methods = { RequestMethod.POST })
@RestController
public class ProcessPensionController {

	private Logger log = LoggerFactory.getLogger(ProcessPensionController.class);

	@Autowired
	private PensionDefaultService service;

	@PostMapping(value = "/ProcessPension", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
	public Object processPensionController(@RequestHeader(value = "Authorization") String token,
			@RequestBody Map<String, Long> aadhaarNumber) throws AuthenticationException {

		service.validateAuthorization(token);
		log.info("Authorized Request. Process Pension Service is initiated ...");

		long aadhaar = service.validateAadhaar(aadhaarNumber);

		DetailsOfPensioner pensioner = service.getPensionerFromServiceInstance("pensioner-detail", aadhaar, token);

		log.info("Successfully Pension Amount is Calculated ...");
		return service.calculatedPensionDetails(pensioner);
	}
}
