/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author Raphael
 * The function which convert 'now' function as a current date with a given pattern and locale
 */
public class LocaleAndFormatNowFunction extends AbstractPreDefinedFunction {

	public LocaleAndFormatNowFunction(){
		this.functionName = "now";
		this.functionArgsNumber = 2;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#process(java.lang.String)
	 */
	public String replaceByValue(String token) {
		if(!matches(token)){
			throw new RuntimeException("this instruction ["+token+"] doesn't match localeAndFormatNow function");
		}
		
		//Extraction du 1er arg : correspond au format
		String locale = getFunctionArgs()[0];
		String format = getFunctionArgs()[1];
		
		return DateFormatUtils.format(Calendar.getInstance(), format, LocaleUtils.toLocale(locale));
	}



}
