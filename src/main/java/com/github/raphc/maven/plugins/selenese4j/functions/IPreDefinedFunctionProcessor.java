/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

/**
 * @author Raphael
 *
 */
public interface IPreDefinedFunctionProcessor {

	/**
	 * 
	 * @param instruction
	 * @return
	 * @throws NotMatchedException
	 */
	public String process(String instruction) throws NotMatchedException;
}
