package cl.jose.huenul.springboot.globallogic.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cl.jose.huenul.springboot.globallogic.constants.GlobalConstants;

@Component
public class EncryptUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);

	@Value("${app.security.crypto.secret.key}")
	private String cryptoSecretKey;

	public String encryptPassword(String password) {
		String encValue = null;
		try {
			encValue = enc(password.getBytes(StandardCharsets.UTF_8));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.error("ERROR", e);
		}
		return encValue;
	}

	private String enc(byte[] value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec keySpec = new SecretKeySpec(cryptoSecretKey.getBytes(), GlobalConstants.AES);
		Cipher cipher = Cipher.getInstance(GlobalConstants.ALGORITHM_AES256);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new GCMParameterSpec(GlobalConstants.GCM_TAG_LENGTH * 8, getIvGcm()));
		byte[] encrypted = cipher.doFinal(value);
		return Base64.getUrlEncoder().encodeToString(encrypted);
	}

	public String decrypt(String encryptedText) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		SecretKeySpec keySpec = new SecretKeySpec(cryptoSecretKey.getBytes(), GlobalConstants.AES);
		Cipher cipher = Cipher.getInstance(GlobalConstants.ALGORITHM_AES256);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new GCMParameterSpec(GlobalConstants.GCM_TAG_LENGTH * 8, getIvGcm()));
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	private byte[] getIvGcm() {
		return new byte[GlobalConstants.GCM_NONCE_LENGTH];
	}

}