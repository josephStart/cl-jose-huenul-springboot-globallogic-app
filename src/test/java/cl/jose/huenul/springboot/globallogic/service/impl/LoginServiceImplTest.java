package cl.jose.huenul.springboot.globallogic.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;

import cl.jose.huenul.springboot.globallogic.entity.PhoneEntity;
import cl.jose.huenul.springboot.globallogic.entity.UserEntity;
import cl.jose.huenul.springboot.globallogic.entity.dao.UserDao;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLogin;
import cl.jose.huenul.springboot.globallogic.service.JwtService;

@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginServiceImplTest {

	@InjectMocks
	private LoginServiceImpl loginServiceImpl;

	@Mock
	private UserDao userDao;

	@Mock
	private JwtService jwtService;

	private LocalDateTime actualDate;
	private UserEntity userEntity;
	private PhoneEntity phoneEntity;

	@BeforeEach
	public void setUp() {
		actualDate = LocalDateTime.now();
		phoneEntity = PhoneEntity.builder().cityCode(1).countryCode("testCountryCode").id(1L).number(123456789L)
				.build();
		userEntity = UserEntity.builder().id(UUID.randomUUID()).name("Test").email("test@test.test")
				.password("testPassword").creationDate(actualDate).updatedDate(actualDate).lastLoginDate(actualDate)
				.isActive(Boolean.TRUE).phones(List.of(phoneEntity)).build();

	}

	@Test
	public void login_success_test() {
		when(userDao.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
		UserEntity user = UserEntity.builder().email("test@test.com").build();
		TestSecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(user, null, List.of()));

		ResponseUserLogin responseUserLogin = loginServiceImpl.login().getBody();
		assertEquals(responseUserLogin.getData().getName(), "Test");

	}

	@Test
	public void login_nocontent_test() {
		UserEntity user = UserEntity.builder().email("test@test.com").build();
		TestSecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(user, null, List.of()));

		ResponseEntity<ResponseUserLogin> responseUserLogin = loginServiceImpl.login();
		assertEquals(HttpStatus.NO_CONTENT, responseUserLogin.getStatusCode());

	}

	@Test
	public void login_no_phones_test() {
		when(userDao.findByEmail(anyString())).thenReturn(Optional.of(UserEntity.builder().id(UUID.randomUUID())
				.name("Test").email("test@test.test").password("testPassword").creationDate(actualDate)
				.updatedDate(actualDate).lastLoginDate(actualDate).isActive(Boolean.TRUE).build()));
		UserEntity user = UserEntity.builder().email("test@test.com").build();
		TestSecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(user, null, List.of()));

		ResponseUserLogin responseUserLogin = loginServiceImpl.login().getBody();
		assertEquals(responseUserLogin.getData().getName(), "Test");

	}

}
