/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.LocatorResolver;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande sendKeys
 */
@WebDriverElement
public class SendKeysElement implements Element  {

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "sendKeys";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) throws IllegalArgumentException {
		String[] cmdElt = StringUtils.splitByWholeSeparator(command.getTarget(), "=", 2);
		String locator = LocatorResolver.resolve(cmdElt[0].toLowerCase().trim());
		return "driver.findElement(By."+locator+"(\"" +cmdElt[1]+ "\")).sendKeys(\""+command.getValue()+"\");";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getReturnType()
	 */
	public Class<?> getReturnType() {
		return null;
	}

	
}
