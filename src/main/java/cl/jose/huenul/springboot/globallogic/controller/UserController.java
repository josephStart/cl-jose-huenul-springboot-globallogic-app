package cl.jose.huenul.springboot.globallogic.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.jose.huenul.springboot.globallogic.pojo.RequestUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/sign-up")
	public ResponseEntity<ResponseUserCreatePojo> signUp(@Valid @RequestBody RequestUserCreatePojo request) {
		return userService.signUp(request);
	}

}