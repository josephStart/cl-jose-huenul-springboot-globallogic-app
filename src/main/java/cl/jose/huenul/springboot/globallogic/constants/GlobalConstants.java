package cl.jose.huenul.springboot.globallogic.constants;

public class GlobalConstants {

	public static final String MENSAJE_ERROR_EXISTS_EMAIL = "El correo ingresado ya existe";
	public static final String MENSAJE_ERROR_BLANK_NAME = "El nombre no puede estar vacio";
	public static final String MENSAJE_ERROR_BLANK_EMAIL = "El email no puede estar vacio";
	public static final String MENSAJE_ERROR_BLANK_PASSWORD = "La contraseña no puede estar vacia";
	public static final String MENSAJE_ERROR_EMAIL = "El email recibido no es válido";
	public static final String MENSAJE_ERROR_PASSWORD = "La contraseña debe tener por lo menos una mayúscula, dos números y minúsculas";
	public static final String REGEX_PATTERN_PASSWORD = "^(?=(?:.*[0-9]){2})(?=.*[a-z])(?=(?:.*[A-Z]){1})([a-zA-Z0-9]{0,})$";
	public static final String ERROR_MESSAGE_PASSWORD_LENGTH = "El largo de la contraseña debe ser de 8 a 12 carácteres";
	public static final String ZONEID = "America/Santiago";
	public static final String ALGORITHM_AES256 = "AES/GCM/NoPadding";
	public static final int GCM_TAG_LENGTH = 16;
	public static final int GCM_NONCE_LENGTH = 12;
	public static final String DATE_FORMAT_OUTPUT = "MMM dd, yyyy HH:mm:ss a";
	public static final String AES = "AES";
}
