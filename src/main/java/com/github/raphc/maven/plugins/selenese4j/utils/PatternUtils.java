/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author Raphael
 *
 */
public final class PatternUtils {

	private PatternUtils(){}
	
	/**
	 * Transformation :
	 * commence par ^
	 * * = [\\s\\S]*
	 * ? = [\\s\\S]
	 * . = \\.
	 * [ = \\[
	 * termine par $
	 * 	
	 * @param pattern
	 */
	public static String processPattern(String pattern) {
		return "^"+StringUtils.replaceEach(pattern, new String[]{"*", "?",".","["}, new String[]{"[\\\\s\\\\S]*","[\\\\s\\\\S]","\\\\.","\\\\["})+"$";
	}
}
