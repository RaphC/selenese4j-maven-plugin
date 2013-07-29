/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

/**
 * @author Raphael
 *
 */
public class FilteringUtils {

	private FilteringUtils(){}
	
	/**
	 * Replace the given {@linkplan s} to an escaped string
	 * '\\' string to '\\\\' string
	 * '\' string to '\\\' string
	 * @param s
	 * @return
	 */
	public static String filter(String s) {
		if(s != null){
			s = s.replace("\\", "\\\\");
			s = s.replace("\"", "\\\"");
		}
		return s;
	}
}
