package cl.jose.huenul.springboot.globallogic.pojo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExceptionPojo {

	private LocalDateTime timestamp;
	private int code;
	private List<String> details;

}
