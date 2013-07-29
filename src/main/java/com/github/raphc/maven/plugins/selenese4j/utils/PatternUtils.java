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
	 * Transform regexp token * and . by the rigth string chars
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
		
		if(pattern == null) {
			return null;
		}
		
		return "^"+StringUtils.replaceEach(pattern, new String[]{"*", "?",".","["}, new String[]{"[\\\\s\\\\S]*","[\\\\s\\\\S]","\\\\.","\\\\["})+"$";
	}
}
