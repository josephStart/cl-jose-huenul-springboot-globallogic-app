package cl.jose.huenul.springboot.globallogic.pojo;

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
public class PhonePojo {

	private long number;
	private int cityCode;
	private String countryCode;

}
