/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.exception;

/**
 * @author Raphael
 * Exception thrown when a wrong configuration has been detected as not valid
 *
 */
public class ConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7576144560881780852L;

	/**
	 * 
	 */
	public ConfigurationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ConfigurationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ConfigurationException(Throwable cause) {
		super(cause);
	}

	
}
