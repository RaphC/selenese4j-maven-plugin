/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.context;

/**
 * @author Raphael
 * The context used to perform a transformation
 * 
 */
public class InfoContext {

	/**
	 * The given outputEncoding
	 */
	private String outputEncoding;
	
	/**
	 * 
	 * @param outputEncoding
	 */
	public InfoContext(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}

	/**
	 * @return the outputEncoding
	 */
	public String getOutputEncoding() {
		return outputEncoding;
	}

	/**
	 * @param outputEncoding the outputEncoding to set
	 */
	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}
	
}
