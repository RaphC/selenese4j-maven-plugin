/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

/**
 * @author Raphael
 *
 */
public class ArrayUtils {

	/**
	 * Removed leading and trailing double quotes from all String element contained into the array
	 * @param arr
	 * @return
	 */
	public static String[] unQuotingArrayElement(String[] arr){
		String[] result = new String[arr.length];
		
		int i = 0;
		for(String emt : arr){
			result[i] = emt.replaceAll("^\"|\"$", "");
			i++;
		}
		return result;
	}
}
