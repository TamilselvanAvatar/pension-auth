package com.pension.service;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pension.exception.NotInLengthException;
import com.pension.model.DetailsOfPensioner;
import com.pension.util.JwtToken;

@Service
public class PensionDefaultService {

	private Logger log = LoggerFactory.getLogger(PensionDefaultService.class);

	@Value("${link:http://localhost:8000}")
	private String link;

	@Autowired
	private JwtToken jwt;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service_charge_public:500}")
	// @Value("${ENV_VARIABLE:DEFAULT_VALUE}")
	private double publicServiceCharge; // Public Bank Service Charge

	@Value("${service_charge_private:550}")
	private double privateServiceCharge; // Private Bank Service Charge

	@Value("${percent_self_pension:80}")
	private double selfPensionPercent; // Self Pension Percentage

	@Value("${percent_family_pension:50}")
	private double familyPensionPercent; // Family Pension Percentage

	public Map<String, Object> calculatedPensionDetails(Object obj) {

		Map<String, Object> map = new HashMap<>();

		DetailsOfPensioner pensioner = (DetailsOfPensioner) obj;

		double percent = pensioner.getTypeofPension().equals("SELF") ? selfPensionPercent : familyPensionPercent;

		double pension = (percent * 0.01 * pensioner.getSalaryEarned()) + pensioner.getAllowances();

		double charge = pensioner.getBankDetail().getBankType().equalsIgnoreCase("PUBLIC") ? publicServiceCharge
				: privateServiceCharge;
		
		log.debug("Charge = {} , Pensioner = {}" , charge , pensioner);

		map.put("PensionAmount", pension);
		map.put("BankServiceCharge", charge);

		return map;

	}

	public long validateAadhaar(Map<String, Long> aadhaarNumber) {
		long aadhaar = aadhaarNumber.get("aadhaar");
		if (Long.toString(aadhaar).length() != 12) {
			throw new NotInLengthException("Given Aadhaar Number(" + aadhaar + ") is not Valid");
		}
		log.debug("Validation is done");
		return aadhaar;
	}

	public DetailsOfPensioner getPensionerFromServiceInstance(String service, long aadhaar, String token) {

		final Map<String, Object> params = new HashMap<>();
		params.put("aadhaar", aadhaar);
		params.put("serviceId", service);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", token);

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = link + "/PensionerDetailByAadhaar/{aadhaar}";

		log.info(url);
		
		ResponseEntity<DetailsOfPensioner> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				DetailsOfPensioner.class, params);

		log.debug("Microservice communication is done");

		return response.getBody();
	}

	public void validateAuthorization(String token) throws AuthenticationException {
		jwt.validateToken(token);
	}

}
