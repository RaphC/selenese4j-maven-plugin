/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande open
 */
@WebDriverElement
public class OpenElement implements Element  {

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "open";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) {
		return "driver.get(\"" +command.getTarget()+ "\");";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getReturnType()
	 */
	public Class<?> getReturnType() {
		return null;
	}

	
}
