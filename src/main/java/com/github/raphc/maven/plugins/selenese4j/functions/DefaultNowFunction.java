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
public class DefaultNowFunction extends AbstractPreDefinedFunction {

	public DefaultNowFunction(){
		this.functionName = "now";
		this.functionArgsNumber = 0;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#process(java.lang.String)
	 */
	public String process(String instruction) {
		if(!matches(instruction)){
			throw new RuntimeException("this instruction ["+instruction+"] doesn't match now function");
		}
		return DateFormatUtils.ISO_DATE_FORMAT.format(Calendar.getInstance());
	}



}
