package cl.jose.huenul.springboot.globallogic.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.jose.huenul.springboot.globallogic.entity.PhoneEntity;
import cl.jose.huenul.springboot.globallogic.entity.UserEntity;
import cl.jose.huenul.springboot.globallogic.entity.dao.PhoneDao;
import cl.jose.huenul.springboot.globallogic.entity.dao.UserDao;
import cl.jose.huenul.springboot.globallogic.pojo.PhonePojo;
import cl.jose.huenul.springboot.globallogic.pojo.RequestUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.service.JwtService;
import cl.jose.huenul.springboot.globallogic.util.DateUtil;
import cl.jose.huenul.springboot.globallogic.util.EncryptUtil;

@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserDao userDao;

	@Mock
	private PhoneDao phoneDao;

	@Mock
	private DateUtil dateUtil;

	@Mock
	private EncryptUtil encryptUtil;

	@Mock
	private JwtService jwtService;

	private RequestUserCreatePojo requestUserCreatePojo;
	private PhonePojo phonePojo;
	private PhoneEntity phoneEntity;
	private UserEntity userEntity;
	private LocalDateTime actualDate;

	@BeforeEach
	public void setUp() {
		actualDate = LocalDateTime.now();
		phonePojo = PhonePojo.builder().cityCode(1).countryCode("testCountryCode").number(123456789L).build();
		phoneEntity = PhoneEntity.builder().cityCode(1).countryCode("testCountryCode").number(123456789L).build();
		userEntity = UserEntity.builder().id(UUID.randomUUID()).name("Test").email("test@test.test")
				.password("testPassword").creationDate(actualDate).updatedDate(actualDate).lastLoginDate(actualDate)
				.isActive(Boolean.TRUE).phones(List.of(phoneEntity)).build();
		requestUserCreatePojo = RequestUserCreatePojo.builder().name("Test").email("test@test.test")
				.password("testPassword").phones(List.of(phonePojo)).build();
	}

	@Test
	public void signUp_success_test() {
		when(userDao.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
		when(phoneDao.saveAll(anyList())).thenReturn(List.of(phoneEntity));
		when(userDao.save(any(UserEntity.class))).thenReturn(userEntity);
		ResponseUserCreatePojo responseUserCreatePojo = userServiceImpl.signUp(requestUserCreatePojo).getBody();

		assertTrue(responseUserCreatePojo.getData().isActive());
	}

	@Test
	public void signUp_empty_phones_test() {
		requestUserCreatePojo = RequestUserCreatePojo.builder().name("Test").email("test@test.test")
				.password("testPassword").build();
		when(userDao.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
		when(userDao.save(any(UserEntity.class))).thenReturn(userEntity);
		ResponseUserCreatePojo responseUserCreatePojo = userServiceImpl.signUp(requestUserCreatePojo).getBody();

		assertTrue(responseUserCreatePojo.getData().isActive());
	}

	@Test
	public void signUp_existsmail_test() {
		when(userDao.existsByEmail(anyString())).thenReturn(Boolean.TRUE);
		ResponseEntity<ResponseUserCreatePojo> response = userServiceImpl.signUp(requestUserCreatePojo);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
