package cl.jose.huenul.springboot.globallogic.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import cl.jose.huenul.springboot.globallogic.pojo.PhonePojo;
import cl.jose.huenul.springboot.globallogic.pojo.RequestUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreateDataPojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.service.UserService;

@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private RequestUserCreatePojo requestUserCreatePojo;
	private PhonePojo phonePojo;
	private ResponseUserCreatePojo responseUserCreatePojo;

	@BeforeEach
	public void setUp() {
		phonePojo = PhonePojo.builder().cityCode(1).countryCode("testCountryCode").number(123456789L).build();
		requestUserCreatePojo = RequestUserCreatePojo.builder().name("Test").email("test@test.test")
				.password("testPassword").phones(List.of(phonePojo)).build();
		responseUserCreatePojo = ResponseUserCreatePojo.builder()
				.data(ResponseUserCreateDataPojo.builder().id(UUID.randomUUID().toString()).created("dateFormattedTest")
						.lastLogin("dateFormattedTest").token("tokenTest").isActive(Boolean.TRUE).build())
				.build();
	}

	@Test
	public void signUp_controller_test() {
		when(userService.signUp(any(RequestUserCreatePojo.class)))
				.thenReturn(ResponseEntity.ok(responseUserCreatePojo));

		ResponseEntity<ResponseUserCreatePojo> response = userController.signUp(requestUserCreatePojo);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("dateFormattedTest", response.getBody().getData().getCreated());
	}

}
