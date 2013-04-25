/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.LocatorResolver;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande submit
 */
@WebDriverElement
public class SubmitElement implements Element  {

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "submit";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) throws IllegalArgumentException {
		return "driver.findElement("+LocatorResolver.resolve(command.getTarget())+").submit();";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getReturnType()
	 */
	public Class<?> getReturnType() {
		return null;
	}

	
}
