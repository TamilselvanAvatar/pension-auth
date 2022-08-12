package com.pension.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pension.entity.LoginUserDetail;
import com.pension.repository.UserDetailRepository;

@SpringBootTest
class UserDetailServiceTest {

	@Autowired
	private UserDetailService service;

	@MockBean
	private UserDetailRepository repository;

	@BeforeEach
	void setup() {
		LoginUserDetail user1 = LoginUserDetail.builder().userName("abcd").password("abcd").build();

		LoginUserDetail user2 = LoginUserDetail.builder().userName("abce").password("abcd").build();

		Mockito.when(repository.findByUserName("abcd")).thenReturn(user1);
		Mockito.when(repository.save(user2)).thenReturn(user2);
	}

	@Test
	@DisplayName("CASE 1 : Verify the User Exist")
	void verifyUserTest1() {
		LoginUserDetail user = LoginUserDetail.builder().userName("abcd").password("abcd").build();
		boolean result = service.verifyUser(user);
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("CASE 2 : Verify the User Not Exist")
	void verifyUserTest2() {
		LoginUserDetail user = LoginUserDetail.builder().userName("abc").password("abcd").build();
		boolean result = service.verifyUser(user);
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("CASE 1 : Save the User ")
	void saveUserTest1() {
		LoginUserDetail user = LoginUserDetail.builder().userName("abcd").password("abcd").build();
		boolean result = service.saveUser(user);
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("CASE 2 : Save the User")
	void saveUserTest2() {
		LoginUserDetail user = LoginUserDetail.builder().userName("abce").password("abcd").build();
		boolean result = service.saveUser(user);
		assertThat(result).isTrue();
	}

}
