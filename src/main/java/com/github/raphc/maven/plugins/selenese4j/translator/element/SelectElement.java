/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.LocatorResolver;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande de type select
 */
@WebDriverElement
public class SelectElement implements Element  {

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "select";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) throws IllegalArgumentException {
		return "new Select(driver.findElement("+LocatorResolver.resolve(command.getTarget())+"))."+processOptionLocator(command.getValue())+";";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getReturnType()
	 */
	public Class<?> getReturnType() {
		return String.class;
	}


	/**
	 * On conserve le traitement meme si le retour est identique
	 * Si pas de prefixe, on selectionne label (valeur par defaut)
	 * @param optionLocator
	 * @return
	 */
	private String processOptionLocator(String optionLocator){
		
		String prefix = "label";
		String value = optionLocator;
		if(StringUtils.contains(optionLocator, '=')){
			String[] optElts = StringUtils.splitByWholeSeparator(optionLocator, "=", 2);
			prefix = optElts[0];
			value = optElts[1];
		}
		
		if ("label".equalsIgnoreCase(prefix)) {
			return "selectByVisibleText(\""+value+"\")";
		}
		if ("value".equalsIgnoreCase(prefix)) {
			return "selectByValue(\""+value+"\")";
		}
		if ("id".equalsIgnoreCase(prefix)) {
			// TODO Ne semble pas gere
			return "selectByVisibleText(\""+value+"\")";
		}
		if ("index".equalsIgnoreCase(prefix)) {
			return "selectByIndex("+value+")";
		}
		return value;
	}
}
