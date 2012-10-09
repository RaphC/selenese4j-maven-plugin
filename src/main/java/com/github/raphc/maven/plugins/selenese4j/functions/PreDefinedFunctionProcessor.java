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
		functions.add(new LocaleAndFormatDateAddFunction());
	}
	
	/**
	 * 
	 * @param instruction
	 * @return
	 * @throws NotMatchedException
	 */
	public final String process(String instruction) throws NotMatchedException {
		String initialInstruction = instruction;
		
		for(PreDefinedFunction predefinedFunction : functions){
			//Verification
			if(predefinedFunction.matches(instruction)){
				return ((AbstractPreDefinedFunction) predefinedFunction).process(instruction);
			}
		}
		// No function found
		return initialInstruction;
	}

}
