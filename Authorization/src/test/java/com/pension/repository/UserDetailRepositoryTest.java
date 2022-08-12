package com.pension.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.pension.entity.LoginUserDetail;

@DataJpaTest
class UserDetailRepositoryTest {

	@Autowired
	private UserDetailRepository repository;

	@Autowired
	private TestEntityManager em;

	@BeforeEach
	void setUp() {
		LoginUserDetail user = LoginUserDetail.builder().userName("abcd").password("abcd").build();
		em.persist(user);
	}

	@Test
	@DisplayName("CASE 1 :Find Exist user in Database")
	void findByUserNameTest1() {
		LoginUserDetail user = repository.findByUserName("abcd");
		assertThat(user.getUserName()).isEqualTo("abcd");
	}

	@Test
	@DisplayName("CASE 2 :Find Not Exist user in Database")
	void findByUserNameTest2() {
		LoginUserDetail user = repository.findByUserName("abc");
		assertThat(user).isNull();
	}

}
