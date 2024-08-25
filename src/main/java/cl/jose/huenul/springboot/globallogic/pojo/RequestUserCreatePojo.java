package cl.jose.huenul.springboot.globallogic.pojo;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import cl.jose.huenul.springboot.globallogic.constants.GlobalConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestUserCreatePojo {

	@NotBlank(message = GlobalConstants.MENSAJE_ERROR_BLANK_NAME)
	private String name;

	@NotBlank(message = GlobalConstants.MENSAJE_ERROR_BLANK_EMAIL)
	@Email(message = GlobalConstants.MENSAJE_ERROR_EMAIL)
	private String email;

	@NotBlank(message = GlobalConstants.MENSAJE_ERROR_BLANK_PASSWORD)
	@Pattern(regexp = GlobalConstants.REGEX_PATTERN_PASSWORD, message = GlobalConstants.MENSAJE_ERROR_PASSWORD)
	@Size(min = 8, max = 12, message = GlobalConstants.ERROR_MESSAGE_PASSWORD_LENGTH)
	private String password;
	private List<PhonePojo> phones;

}
