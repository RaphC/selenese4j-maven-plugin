/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande store
 */
@WebDriverElement
public class CheckElement implements Element  {

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "check";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) throws IllegalArgumentException {
		String[] cmdElt = StringUtils.split(command.getTarget(), '=');
		String locator = cmdElt[0].toLowerCase().trim();
		
		if(locator == null || ! locator.matches("^(name|id)*")){
			throw new IllegalArgumentException("The command target has to specify id or name locator");
		}
		return "if (!driver.findElement(By."+locator+"(\"" +cmdElt[1]+ "\")).isSelected()) {\ndriver.findElement(By."+locator+"(\"" +cmdElt[1]+ "\")).click();\n};";
	}

	
}
