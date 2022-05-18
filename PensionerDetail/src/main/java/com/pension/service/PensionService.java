package com.pension.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pension.entity.BankDetail;
import com.pension.entity.DetailsOfPensioner;
import com.pension.exception.AadhaarNotFoundException;
import com.pension.exception.JwtTokenEmptyException;
import com.pension.exception.JwtTokenExpiredException;
import com.pension.repository.BankDetailRepository;
import com.pension.repository.PensionerDetailRepository;
import com.pension.util.JwtToken;

@Service
public class PensionService {
	@Autowired
	private JwtToken jwt;

	@Autowired
	private PensionerDetailRepository detailRepository;

	@Autowired
	private BankDetailRepository bankRepository;

	public void validateAuthorization(String token) throws JwtTokenExpiredException, JwtTokenEmptyException {
		jwt.validateToken(token);
	}

	public String delete(long aadhaar) {
		return detailRepository.deleteByAadhaar(aadhaar) != 0 ? "Deleted" : null;
	}

	public DetailsOfPensioner fetchDetails(long aadhaar) {
		DetailsOfPensioner pensioner = detailRepository.findByAadhaar(aadhaar);
		if (pensioner == null) {
			throw new AadhaarNotFoundException("Aadhaar Number " + aadhaar + " is not found in Database.");
		}
		return pensioner;
	}

	public String insert(DetailsOfPensioner pensionDetail) {
		BankDetail bank = pensionDetail.getBankDetail();
		long id = 0l;
		if (bankRepository.findByAccountNumber(bank.getAccountNumber()) == null && bank != null) {
			bankRepository.saveBankDetail(bank.getName(), bank.getAccountNumber(), bank.getBankType().ordinal());
			id = bankRepository.findByAccountNumber(bank.getAccountNumber()).getId();
		}
		if (id != 0l) {

			if (detailRepository.findByAadhaar(pensionDetail.getAadhaar()) == null
					|| detailRepository.findByPAN(pensionDetail.getPAN()) == null) {
				detailRepository.savePensionerDetail(pensionDetail.getName(), pensionDetail.getDob(),
						pensionDetail.getAadhaar(), pensionDetail.getPAN(), pensionDetail.getSalaryEarned(),
						pensionDetail.getAllowances(), pensionDetail.getTypeofPension().toString(), id);

			}

			else {
				return null;
			}
		} else {
			return null;
		}

		return "Saved";
	}

	public String update(DetailsOfPensioner pensionDetail) {
		if (detailRepository.findByAadhaar(pensionDetail.getAadhaar()) != null) {
			BankDetail bank = pensionDetail.getBankDetail();
			BankDetail dbBank = bankRepository.findByAccountNumber(bank.getAccountNumber());
			long id = 0l;
			if (dbBank == null) {
				bankRepository.saveBankDetail(bank.getName(), bank.getAccountNumber(), bank.getBankType().ordinal());
				id = bankRepository.findByAccountNumber(bank.getAccountNumber()).getId();
			} else {
				id = dbBank.getId();
			}
			detailRepository.updatePensionerDetail(pensionDetail.getName(), pensionDetail.getDob(),
					pensionDetail.getPAN(), pensionDetail.getSalaryEarned(), pensionDetail.getAllowances(),
					pensionDetail.getTypeofPension().toString(), id, pensionDetail.getAadhaar());
			return "updated";
		}

		return null;
	}

}
