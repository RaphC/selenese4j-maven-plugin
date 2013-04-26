/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.context;

/**
 * @author Raphael
 *
 */
public class InfoContext {

	private String outputEncoding;
	
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
