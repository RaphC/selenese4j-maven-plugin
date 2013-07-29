/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.utils.FilteringUtils;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande de type assertTextPresent
 */
@WebDriverElement
public class IsTextPresentElement implements Element  {

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "isTextPresent";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) throws IllegalArgumentException {
		// driver.findElement(By.tagName("body")).getText().contains("Some text to search")
		// driver.getPageSource().contains("sometext")
		return "driver.findElement(By.tagName(\"body\")).getText().contains(\""+FilteringUtils.filter(command.getTarget())+"\")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getReturnType()
	 */
	public Class<?> getReturnType() {
		return String.class;
	}

	
}
