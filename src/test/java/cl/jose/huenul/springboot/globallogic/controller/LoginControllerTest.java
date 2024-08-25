package cl.jose.huenul.springboot.globallogic.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLogin;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLoginData;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserPhoneLogin;
import cl.jose.huenul.springboot.globallogic.service.LoginService;

@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginControllerTest {

	@InjectMocks
	private LoginController loginController;

	@Mock
	private LoginService loginService;

	private ResponseUserLogin responseUserLogin;

	@BeforeEach
	public void setUp() {
		responseUserLogin = ResponseUserLogin.builder()
				.data(ResponseUserLoginData.builder().id(UUID.randomUUID().toString()).created("dateFormattedTest")
						.lastLogin("dateFormattedTest").token("tokenTest").isActive(Boolean.TRUE).name("Test")
						.email("test@test.test").password("passwordTest").phones(List.of(ResponseUserPhoneLogin
								.builder().cityCode(1).countryCode("countryCodeTest").number(123456789).build()))
						.build())
				.build();
	}

	@Test
	public void login_controller_test() {
		when(loginService.login()).thenReturn(ResponseEntity.ok(responseUserLogin));

		ResponseEntity<ResponseUserLogin> response = loginController.login();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("test@test.test", response.getBody().getData().getEmail());
	}

}
