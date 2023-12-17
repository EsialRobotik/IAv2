package esialrobotik.ia.utils.web.handler;

public class HandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int httpCode;

	public HandlerException(String message, int httpCode, Throwable previous) {
		super(message, previous);
		this.httpCode = httpCode;
	}
	
	public HandlerException(String message, int httpCode) {
		super(message);
		this.httpCode = httpCode;
	}
	
	public int getHttpCode() {
		return this.httpCode;
	}
	
}
