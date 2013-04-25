/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

/**
 * @author Raphael
 * 
 */
public class LocatorResolver {

	public static String resolve(String selector)
			throws IllegalArgumentException {

		if (selector == null) {
			throw new IllegalArgumentException(
					"The command target has to specify id or name locator");
		}

		if ("xpath".equalsIgnoreCase(selector)) {
			return "xpath";
		}
		if ("css".equalsIgnoreCase(selector)) {
			return "cssSelector";
		}
		if ("id".equalsIgnoreCase(selector)) {
			return "id";
		}
		if ("link".equalsIgnoreCase(selector)) {
			return "linkText";
		}
		if ("name".equalsIgnoreCase(selector)) {
			return "name";
		}
		if ("tag_name".equalsIgnoreCase(selector)) {
			return "tagName";
		}
		if ("dom".equalsIgnoreCase(selector)) {
			throw new IllegalArgumentException("Error: Dom locators are not implemented yet!");
		}
		if ("class".equalsIgnoreCase(selector)) {
			return "className";
		}
		throw new IllegalArgumentException("Error: unknown strategy [' + locatorType + '] for locator [' + locator + ']");
	}
}
