package cl.jose.huenul.springboot.globallogic.service;

import org.springframework.http.ResponseEntity;

import cl.jose.huenul.springboot.globallogic.pojo.RequestUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreatePojo;

public interface UserService {

	public ResponseEntity<ResponseUserCreatePojo> signUp(RequestUserCreatePojo request);

}
