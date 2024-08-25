package cl.jose.huenul.springboot.globallogic.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cl.jose.huenul.springboot.globallogic.entity.PhoneEntity;
import cl.jose.huenul.springboot.globallogic.entity.UserEntity;
import cl.jose.huenul.springboot.globallogic.entity.dao.PhoneDao;
import cl.jose.huenul.springboot.globallogic.entity.dao.UserDao;
import cl.jose.huenul.springboot.globallogic.pojo.RequestUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreateDataPojo;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserCreatePojo;
import cl.jose.huenul.springboot.globallogic.service.JwtService;
import cl.jose.huenul.springboot.globallogic.service.UserService;
import cl.jose.huenul.springboot.globallogic.util.DateUtil;
import cl.jose.huenul.springboot.globallogic.util.EncryptUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PhoneDao phoneDao;
	private final DateUtil dateUtil;
	private final EncryptUtil encryptUtil;
	private final JwtService jwtService;

	@Override
	public ResponseEntity<ResponseUserCreatePojo> signUp(RequestUserCreatePojo request) {

		ResponseEntity<ResponseUserCreatePojo> response = null;

		boolean existEmail = userDao.existsByEmail(request.getEmail());

		if (existEmail) {
			response = ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			response = ResponseEntity.ok(fillResponseUserCreatePojo(createUserPhoneData(request)));
		}
		return response;
	}

	private ResponseUserCreatePojo fillResponseUserCreatePojo(UserEntity user) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("user_name", user.getName());

		return ResponseUserCreatePojo.builder()
				.data(ResponseUserCreateDataPojo.builder().id(user.getId().toString())
						.created(dtf.format(user.getCreationDate())).lastLogin(dtf.format(user.getLastLoginDate()))
						.token(jwtService.generateToken(user.getEmail(), claimsMap)).isActive(user.isActive()).build())
				.build();
	}

	private UserEntity createUserPhoneData(RequestUserCreatePojo request) {

		List<PhoneEntity> phonesList = null;
		if (!CollectionUtils.isEmpty(request.getPhones())) {

			phonesList = request
					.getPhones().stream().map(phone -> PhoneEntity.builder().number(phone.getNumber())
							.cityCode(phone.getCityCode()).countryCode(phone.getCountryCode()).build())
					.collect(Collectors.toList());

			phoneDao.saveAll(phonesList);
		}

		LocalDateTime actualDate = dateUtil.getActualDate();
		UserEntity userEntity = userDao.save(UserEntity.builder().name(request.getName()).email(request.getEmail())
				.password(encryptUtil.encryptPassword(request.getPassword())).isActive(Boolean.TRUE)
				.creationDate(actualDate).lastLoginDate(actualDate).updatedDate(actualDate).phones(phonesList).build());
		return userEntity;

	}

}