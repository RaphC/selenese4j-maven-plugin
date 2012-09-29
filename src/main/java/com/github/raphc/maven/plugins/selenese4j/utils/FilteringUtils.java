/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

/**
 * @author Raphael
 *
 */
public class FilteringUtils {

	/**
	 * 
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
