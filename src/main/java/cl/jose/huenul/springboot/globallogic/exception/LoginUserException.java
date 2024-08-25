package cl.jose.huenul.springboot.globallogic.exception;

public class LoginUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5438781147615720971L;

	public LoginUserException(String message) {
		super(message);
	}

	public LoginUserException(Throwable exception) {
		super(exception);
	}

	public LoginUserException(String message, Throwable exception) {
		super(message, exception);
	}

}
