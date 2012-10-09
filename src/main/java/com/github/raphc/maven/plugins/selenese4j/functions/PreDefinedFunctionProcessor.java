/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raphael
 *
 */
public class PreDefinedFunctionProcessor implements IPreDefinedFunctionProcessor {

	private List<PreDefinedFunction> functions = new ArrayList<PreDefinedFunction>();
	
	public PreDefinedFunctionProcessor(){
		functions.add(new DefaultNowFunction());
		functions.add(new FormattedNowFunction());
		functions.add(new LocaleAndFormatNowFunction());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.functions.IPreDefinedFunctionProcessor#process(java.lang.String)
	 */
	public String process(String instruction) throws NotMatchedException {
		
		for(PreDefinedFunction predefinedFunction : functions){
			//Verification
			if(predefinedFunction.matches(instruction)){
				return predefinedFunction.process(instruction);
			}
		}
		// No function found
		return instruction;
	}

}
