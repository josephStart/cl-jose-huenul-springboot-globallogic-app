package cl.jose.huenul.springboot.globallogic.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
public class DateUtilTest {

	@InjectMocks
	private DateUtil dateUtil;

	@Test
	public void getActualDate_test() {
		assertNotNull(dateUtil.getActualDate());
	}

	@Test
	public void getZonedActualDate_test() {
		assertNotNull(dateUtil.getZonedActualDate());
	}

}
