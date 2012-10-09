/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author Raphael
 *
 */
public class FormattedNowFunction extends AbstractPreDefinedFunction {

	public FormattedNowFunction(){
		this.functionName = "now";
		this.functionArgsNumber = 1;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#process(java.lang.String)
	 */
	public String process(String instruction) {
		if(!matches(instruction)){
			throw new RuntimeException("this instruction ["+instruction+"] doesn't match FormattedNow function");
		}
		
		//Extraction du 1er arg : correspond au format
		String format = this.functionArgs[0];
		
		return DateFormatUtils.format(Calendar.getInstance(), format);
	}



}
