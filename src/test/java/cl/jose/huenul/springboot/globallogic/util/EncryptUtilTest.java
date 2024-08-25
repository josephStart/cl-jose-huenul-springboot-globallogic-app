package cl.jose.huenul.springboot.globallogic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

@MockitoSettings(strictness = Strictness.LENIENT)
public class EncryptUtilTest {

	@InjectMocks
	private EncryptUtil encryptUtil;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(encryptUtil, "cryptoSecretKey", "holi123456789123");
	}

	@Test
	public void encryptPassword_test() throws Exception {
		assertEquals("test", encryptUtil.decrypt(encryptUtil.encryptPassword("test")));

	}

}
