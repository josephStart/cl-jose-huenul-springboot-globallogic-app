package cl.jose.huenul.springboot.globallogic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLogin;
import cl.jose.huenul.springboot.globallogic.service.LoginService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping("/login")
	public ResponseEntity<ResponseUserLogin> login() {
		return loginService.login();
	}

}
