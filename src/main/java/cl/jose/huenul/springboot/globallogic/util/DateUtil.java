package cl.jose.huenul.springboot.globallogic.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import cl.jose.huenul.springboot.globallogic.constants.GlobalConstants;

@Component
public class DateUtil {

	public LocalDateTime getActualDate() {
		return LocalDateTime.now(ZoneId.of(GlobalConstants.ZONEID));
	}

	public ZonedDateTime getZonedActualDate() {
		return ZonedDateTime.now(ZoneId.of(GlobalConstants.ZONEID));
	}

}
