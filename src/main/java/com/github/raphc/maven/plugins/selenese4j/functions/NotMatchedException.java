/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

/**
 * @author Raphael
 *
 */
public class NotMatchedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 136162220875056317L;

	/**
	 * 
	 */
	public NotMatchedException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotMatchedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NotMatchedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NotMatchedException(Throwable cause) {
		super(cause);
	}

	
}
