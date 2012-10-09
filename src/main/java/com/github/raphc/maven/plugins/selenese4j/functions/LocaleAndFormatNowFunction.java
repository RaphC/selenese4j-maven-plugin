/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author Raphael
 *
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
	public String process(String instruction) {
		if(!matches(instruction)){
			throw new RuntimeException("Doesn't match");
		}
		
		//Extraction du 1er arg : correspond au format
		String locale = this.functionArgs[0];
		String format = this.functionArgs[1];
		
		return DateFormatUtils.format(Calendar.getInstance(), format, LocaleUtils.toLocale(locale));
	}



}
