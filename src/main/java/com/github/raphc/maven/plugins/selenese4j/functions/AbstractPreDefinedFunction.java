/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Raphael
 *
 */
public abstract class AbstractPreDefinedFunction implements PreDefinedFunction {

	public static final Pattern DEFINED_FUNCTION_PATTERN = Pattern.compile("[\\s\\S]*\\{@function\\:([\\s\\S]*)\\(([\\s\\S]*)\\)\\}[\\s\\S]*");
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * The functionname expected
	 */
	protected String functionName;
	
	/**
	 * The number of arguments expected
	 */
	protected int functionArgsNumber;
	
	/**
	 * The current arguments
	 */
	protected String[] functionArgs;

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#matches(java.lang.String)
	 */
	public boolean matches(String instruction) {
		
		Matcher functionMatcher = DEFINED_FUNCTION_PATTERN.matcher(instruction);
		if(functionMatcher.matches()){
			logger.log(Level.FINE, "This instruction [" +instruction+ "] contains a pre-defined function.");
			
			//On controle qu'il s'agit bien de la bonne fonction (nom + nombre argument)
			String instrFunctionName =functionMatcher.group(1);
			
			String[] instrFunctionArgs = StringUtils.split(functionMatcher.group(2),',');
			if(!functionName.equalsIgnoreCase(instrFunctionName) || functionArgsNumber != instrFunctionArgs.length){
				logger.log(Level.FINE, "Doesn't matches the pre-defined function name ["+functionName+"]");
				return false;
			}
			
			//On vire les simples quotes exterieures
			this.functionArgs = com.github.raphc.maven.plugins.selenese4j.utils.ArrayUtils.unQuotingArrayElement(instrFunctionArgs);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the functionArgs
	 */
	public String[] getFunctionArgs() {
		return functionArgs;
	}

	/**
	 * @param functionArgs the functionArgs to set
	 */
	public void setFunctionArgs(String[] functionArgs) {
		this.functionArgs = functionArgs;
	}
	
}
