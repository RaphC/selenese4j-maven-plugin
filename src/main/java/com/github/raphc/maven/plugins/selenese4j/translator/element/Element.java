/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;

/**
 * @author Raphael
 *
 */
public interface Element {

	/**
	 * Returns the command label
	 * @return
	 */
	String getCommandName();
	
	/**
	 * 
	 * @param command
	 * @return
	 */
	String process(Command command);
}
