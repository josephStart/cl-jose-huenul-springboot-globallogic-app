package cl.jose.huenul.springboot.globallogic.service;

import org.springframework.http.ResponseEntity;

import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLogin;

public interface LoginService {

	public ResponseEntity<ResponseUserLogin> login();

}
