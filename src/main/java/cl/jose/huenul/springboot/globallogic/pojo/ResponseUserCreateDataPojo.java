package cl.jose.huenul.springboot.globallogic.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ResponseUserCreateDataPojo {

	private String id;
	private String created;
	private String lastLogin;
	private String token;
	@JsonProperty("isActive")
	private boolean isActive;

}
