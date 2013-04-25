/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.LocatorResolver;

/**
 * @author Raphael
 * Genere l'instruction Java correspondant à la commande de type getAttribute
 */
@WebDriverElement
public class GetAttributeElement implements Element  {

	
	Pattern pattern = Pattern.compile("By\\.(.*)\\(\"(.*)\"\\)");
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getCommand()
	 */
	public String getCommandName() {
		return "getAttribute";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#process(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String process(Command command) throws IllegalArgumentException {
//		String[] cmdElt = StringUtils.splitByWholeSeparator(command.getTarget(), "=", 2);
//		String locator = LocatorResolver.resolve(cmdElt[0].toLowerCase().trim());
		String locator = LocatorResolver.resolve(command.getTarget());
		
		Matcher matcher = pattern.matcher(locator);
		if(! matcher.matches()){
			return null;
		}
		
		String location = matcher.group(1);
		String[] target = StringUtils.split(matcher.group(2), '@');
		return "driver.findElement(By." +location+ "(\""+target[0]+"\")).getAttribute(\""+target[1]+"\")";
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.translator.element.Element#getReturnType()
	 */
	public Class<?> getReturnType() {
		return String.class;
	}

	
}
