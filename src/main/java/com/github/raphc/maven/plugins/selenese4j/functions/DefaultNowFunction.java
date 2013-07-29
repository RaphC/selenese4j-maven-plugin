/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author Raphael
 * The function which convert 'now' function as a current date
 */
public class DefaultNowFunction extends AbstractPreDefinedFunction {

	public DefaultNowFunction(){
		this.functionName = "now";
		this.functionArgsNumber = 0;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#process(java.lang.String)
	 */
	public String replaceByValue(String token) {
		if(!matches(token)){
			throw new RuntimeException("this instruction ["+token+"] doesn't match now function");
		}
		// Devrait retournaut la valeur entiere et pas uniquement la valorisation du token
		return DateFormatUtils.ISO_DATE_FORMAT.format(Calendar.getInstance());
	}



}
