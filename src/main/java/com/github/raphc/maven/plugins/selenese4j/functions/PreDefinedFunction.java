/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

/**
 * @author Raphael
 *
 */
public interface PreDefinedFunction {

	/**
	 * 
	 * @param instruction
	 * @return
	 */
	public boolean matches(String instruction);
	
	/**
	 * 
	 * @param instruction
	 */
	public String process(String instruction);
	
}
