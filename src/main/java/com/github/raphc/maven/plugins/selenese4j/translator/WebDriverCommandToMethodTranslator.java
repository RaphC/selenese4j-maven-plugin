/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

import java.util.logging.Level;
import java.util.regex.Matcher;

import com.github.raphc.maven.plugins.selenese4j.functions.AbstractPreDefinedFunction;
import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.element.Element;

/**
 * @author Raphael
 *
 */
public class WebDriverCommandToMethodTranslator extends AbstractCommandToMethodTranslator  implements ICommandToMethodTranslator {

	/**
	 * traducteur
	 * @component role="com.github.raphc.maven.plugins.selenese4j.translator.SeleniumWebDriverAdaptor"
	 */
	private SeleniumWebDriverAdaptor adaptor;
	
	/* (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.transform.ICommandToMethodTranslator#discovery(com.github.raphc.maven.plugins.selenese4j.transform.Command)
	 */
	public String discovery(Command c) {
		String instr = null;
		
		//On verifie que le command name n'est pas une defined function
		Matcher functionMatcher = AbstractPreDefinedFunction.DEFINED_FUNCTION_PATTERN.matcher(c.getName());
		if(functionMatcher.matches()){
			logger.log(Level.FINE, "The command [" +c.getName()+ "] contains a function. This is not supported in name field.");
			throw new RuntimeException("The command [" +c.getName()+ "] contains a function. This is not supported in name field.");
		}
		
		//On verifie si il ne s'agit pas de snippet
		Matcher snippetMatcher = SNIPPET_FRAGMENT_PATTERN.matcher(c.getName());
		if(snippetMatcher.matches()){
			logger.log(Level.FINE, "The command [" +c.getName()+ "] is a snippet. No transformation processed.");
			instr = snippetMatcher.group(1);
		} else {
			// Transciption des commandes selenese
			Element element = adaptor.find(c);
			if(element == null){
				instr = discoveryCustom(c);
			} else {
				// Transcription à l'aide du commandProcessor
				instr = element.process(c);
			}
		}
		logger.log(Level.FINE, "Java instruction generated ["+instr+"].");
		return instr;
	}

	
	/**
	 * Genere les instructions Java pour le command selenese dont aucun equivalent existe
	 * 
	 * @param c
	 * @return
	 */
	private String discoveryCustom(Command c) {
		
		String result = null;

		if ("".equals(c.getName()) || c.getName() == null) {
			return "";
		} else if (c.getName().equalsIgnoreCase("pause")) {
			result = doPause(c);
		} else if (c.getName().equalsIgnoreCase("echo")) {
			result = doEcho(c);
		}
		
		return result;
	}


	/**
	 * @param adaptor the adaptor to set
	 */
	public void setAdaptor(SeleniumWebDriverAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	
}
