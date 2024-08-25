package cl.jose.huenul.springboot.globallogic.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cl.jose.huenul.springboot.globallogic.constants.GlobalConstants;
import cl.jose.huenul.springboot.globallogic.entity.PhoneEntity;
import cl.jose.huenul.springboot.globallogic.entity.UserEntity;
import cl.jose.huenul.springboot.globallogic.entity.dao.UserDao;
import cl.jose.huenul.springboot.globallogic.exception.LoginUserException;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLogin;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserLoginData;
import cl.jose.huenul.springboot.globallogic.pojo.ResponseUserPhoneLogin;
import cl.jose.huenul.springboot.globallogic.service.JwtService;
import cl.jose.huenul.springboot.globallogic.service.LoginService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	private final UserDao userDao;
	private final JwtService jwtService;

	@Override
	public ResponseEntity<ResponseUserLogin> login() {

		ResponseEntity<ResponseUserLogin> response = null;

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserEntity user = (UserEntity) authentication.getPrincipal();

			Optional<UserEntity> userEntityOp = userDao.findByEmail(user.getEmail());

			if (userEntityOp.isPresent()) {

				response = ResponseEntity.ok(fillResponseUserLogin(userEntityOp.get()));
			} else {

				response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

			}
		} catch (Exception ex) {
			LOGGER.error("ERROR", ex);
			throw new LoginUserException(ex);
		}
		return response;
	}

	private ResponseUserLogin fillResponseUserLogin(UserEntity userEntity) {
		return ResponseUserLogin.builder().data(ResponseUserLoginData.builder().id(userEntity.getId().toString())
				.created(formatDate(userEntity.getCreationDate())).lastLogin(formatDate(userEntity.getLastLoginDate()))
				.token(jwtService.generateToken(userEntity.getEmail(), Map.of("user_name", userEntity.getName())))
				.isActive(userEntity.isActive()).name(userEntity.getName()).email(userEntity.getEmail())
				.password(userEntity.getPassword()).phones(fillPhoneList(userEntity.getPhones())).build()).build();
	}

	private List<ResponseUserPhoneLogin> fillPhoneList(List<PhoneEntity> phones) {
		List<ResponseUserPhoneLogin> userPhoneList = null;
		if (!CollectionUtils.isEmpty(phones)) {
			userPhoneList = phones.stream()
					.map(phone -> ResponseUserPhoneLogin.builder().number(phone.getNumber())
							.cityCode(phone.getCityCode()).countryCode(phone.getCountryCode()).build())
					.collect(Collectors.toList());
		}

		return userPhoneList;
	}

	private String formatDate(LocalDateTime creationDate) {
		String formattedDate = creationDate.format(DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT_OUTPUT));
		String month = formattedDate.substring(0, 5);
		formattedDate = formattedDate.replaceAll(month, StringUtils.capitalize(month.replace(".", "")))
				.replaceAll("p. m.", "PM").replaceAll("a. m.", "AM");
		return formattedDate;
	}

}
