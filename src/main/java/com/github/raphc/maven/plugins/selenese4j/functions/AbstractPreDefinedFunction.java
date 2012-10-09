/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.concurrent.atomic.AtomicReference;
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

	public static final Pattern DEFINED_FUNCTION_PATTERN = Pattern.compile("[\\s\\S]*(\\{@function\\:([\\s\\S]*)\\(([\\s\\S]*)\\)\\})[\\s\\S]*");
	
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
	private AtomicReference<String[]> functionArgs = new AtomicReference<String[]>();

	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.PreDefinedFunction#matches(java.lang.String)
	 */
	public boolean matches(String instruction) {
		
		Matcher functionMatcher = DEFINED_FUNCTION_PATTERN.matcher(instruction);
		if(functionMatcher.matches()){
			logger.log(Level.FINE, "This instruction [" +instruction+ "] contains a pre-defined function.");
			
			//On controle qu'il s'agit bien de la bonne fonction (nom + nombre argument)
			String instrFunctionName = functionMatcher.group(2);
			
			String[] instrFunctionArgs = new String[0];
			if(StringUtils.isNotBlank(functionMatcher.group(3))) {
				instrFunctionArgs = functionMatcher.group(3).split("'\\s*,\\s*'");
			}
			
			if(!functionName.equalsIgnoreCase(instrFunctionName) || functionArgsNumber != instrFunctionArgs.length){
				logger.log(Level.FINE, "Doesn't matches the pre-defined function name ["+functionName+"]");
				return false;
			}
			
			//On vire les simples quotes exterieures
			this.functionArgs.set(com.github.raphc.maven.plugins.selenese4j.utils.ArrayUtils.unQuotingArrayElement(instrFunctionArgs));
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param content
	 * @return
	 * @throws NotMatchedException
	 */
	public final String process(String content) throws NotMatchedException {
		String initialString = content;
		
		Matcher matcher = DEFINED_FUNCTION_PATTERN.matcher(content);
		if(matcher.matches()){
			String tokenPart = matcher.group(1);
			String value = replaceByValue(tokenPart);
			return StringUtils.isNotBlank(value) ? StringUtils.replace(initialString, tokenPart, value) : initialString;
		}
		
		return initialString;
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
		return functionArgs.get();
	}

	/**
	 * @param functionArgs the functionArgs to set
	 */
	public void setFunctionArgs(String[] functionArgs) {
		this.functionArgs.set(functionArgs);
	}
	
}
