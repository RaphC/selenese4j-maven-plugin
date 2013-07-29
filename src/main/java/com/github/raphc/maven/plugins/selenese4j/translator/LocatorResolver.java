/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.utils.FilteringUtils;

/**
 * @author Raphael
 * 
 */
public class LocatorResolver {

	/**
	 * Return the locator from the given {@linkplain selector}
	 * @param selector
	 * @return a String matching a webDriver locator
	 * @throws IllegalArgumentException if selector is null or no locator can be found
	 */
	public static String resolve(String selector) throws IllegalArgumentException {

		if (selector == null) {
			throw new IllegalArgumentException("The command target has no locator");
		}

		String locator=selector.trim();
		
		if (StringUtils.startsWith(locator, "/")) {
			return "By.xpath(\""+FilteringUtils.filter(locator)+"\")";
		}
		
		String[] selElt = StringUtils.splitByWholeSeparator(locator, "=", 2);
		locator = selElt[0].toLowerCase().trim();
		
		String target = locator;
		if(selElt.length > 1){
			target = selElt[1];
		}
		
		if ("xpath".equalsIgnoreCase(locator)) {
			return "By.xpath(\""+FilteringUtils.filter(target)+"\")";
		}
		
		if ("css".equalsIgnoreCase(locator)) {
			return "By.cssSelector(\""+target+"\")";
		}
		if ("id".equalsIgnoreCase(locator)) {
			return "By.id(\""+target+"\")";
		}
		if ("link".equalsIgnoreCase(locator)) {
			return "By.linkText(\""+FilteringUtils.filter(target)+"\")";
		}
		if ("name".equalsIgnoreCase(locator)) {
			return "By.name(\""+target+"\")";
		}
		if ("tag_name".equalsIgnoreCase(locator)) {
			return "By.tagName(\""+target+"\")";
		}
		if ("dom".equalsIgnoreCase(locator)) {
			throw new IllegalArgumentException("Error: Dom locators are not implemented yet!");
		}
		if ("class".equalsIgnoreCase(locator)) {
			return "By.className(\""+target+"\")";
		}
		throw new IllegalArgumentException("Error: unknown strategy for locator [" + selector + "]");
	}
}
