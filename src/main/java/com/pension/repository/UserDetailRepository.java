package com.pension.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pension.entity.LoginUserDetail;

@Repository	
public interface UserDetailRepository extends JpaRepository<LoginUserDetail, Long> {
	LoginUserDetail findByUserName(String username);
}
