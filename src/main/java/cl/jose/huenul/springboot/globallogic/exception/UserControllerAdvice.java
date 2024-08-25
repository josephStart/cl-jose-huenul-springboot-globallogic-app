package cl.jose.huenul.springboot.globallogic.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cl.jose.huenul.springboot.globallogic.pojo.ExceptionPojo;
import cl.jose.huenul.springboot.globallogic.util.DateUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

	private final DateUtil dateUtil;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return getResponseEntityExceptionPojo(HttpStatus.BAD_REQUEST,
				ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler(value = JwtException.class)
	protected ResponseEntity<Object> handleJwtException(JwtException ex, WebRequest request) {
		return getResponseEntityExceptionPojo(HttpStatus.UNAUTHORIZED, Arrays.asList(ex.getMessage()));
	}

	@ExceptionHandler(value = UsernameNotFoundException.class)
	protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		return getResponseEntityExceptionPojo(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(ex.getMessage()));
	}

	private ResponseEntity<Object> getResponseEntityExceptionPojo(HttpStatus status, List<String> messages) {
		return ResponseEntity.status(status).body(ExceptionPojo.builder().timestamp(dateUtil.getActualDate())
				.code(status.value()).details(messages).build());
	}

}
