package com.pension.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pension.entity.DetailsOfPensioner;

@Repository
@Transactional
public interface PensionerDetailRepository extends JpaRepository<DetailsOfPensioner, Long> {
	public DetailsOfPensioner findByAadhaar(long aadhaar);

	public int deleteByAadhaar(long aadhaar);

	public DetailsOfPensioner findByPAN(String pan);

	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO PENSIONER_DETAIL "
			+ "(NAME, DATE_OF_BIRTH , AADHAAR , PAN , SALARY , ALLOWANCES , PENSION_CLASSIFICATION, BANK_DETAIL_BANK_USER_ID) "
			+ "VALUES (?1,?2,?3,?4,?5,?6,?7,?8)")
	public void savePensionerDetail(String name, Date dob, long aadhaar, String pAN, double salaryEarned,
			int allowances, String typeofPension, long bankUserId);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE PENSIONER_DETAIL SET "
			+ "NAME =?1, DATE_OF_BIRTH =?2, PAN =?3, SALARY =?4, ALLOWANCES =?5, PENSION_CLASSIFICATION =?6, BANK_DETAIL_BANK_USER_ID =?7 "
			+ "WHERE AADHAAR =?8")
	public void updatePensionerDetail(String name, Date dob, String pAN, double salaryEarned, int allowances,
			String typeofPension, long bankUserId, long aadhaar);

}
