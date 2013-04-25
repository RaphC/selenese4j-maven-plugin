/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

import java.util.logging.Level;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.functions.AbstractPreDefinedFunction;
import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.element.Element;
import com.github.raphc.maven.plugins.selenese4j.utils.FilteringUtils;

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
		} else if (c.getName().endsWith("AndWait")) {
			result = doAndWait(c);
		
		} else if (c.getName().startsWith("waitForNot")) {
			result = doWaitFor(c, NOT_FLAG, false);

		} else if (c.getName().startsWith("waitFor") && c.getName().endsWith("NotPresent")) {
			result = doWaitFor(c, "", true);
			
		} else if (c.getName().startsWith("waitFor")) {
			result = doWaitFor(c, "", false);

	
		} else if (c.getName().startsWith("assertNot")) {
			result = doAssert(c, NOT_FLAG, false);
		} else if (c.getName().startsWith("assert") && c.getName().endsWith("NotPresent")) {
			result = doAssert(c, "", true);
		} else if (c.getName().startsWith("assert")) {
			result = doAssert(c, "", false);
		
		} else if (c.getName().startsWith("verifyNot")) {
			result = doVerify(c, NOT_FLAG, false);
			if(result != null){
				result = "\n\t\ttry {\n\t\t\t" + result +"\n\t\t} catch (Error e) {\n\t\t\tverificationErrors.append(e.toString());\n\t\t}";
			}
		} else if (c.getName().startsWith("verify") && c.getName().endsWith("NotPresent")) {
			result = doVerify(c, "", true);
			if(result != null){
				result = "\n\t\ttry {\n\t\t\t" + result +"\n\t\t} catch (Error e) {\n\t\t\tverificationErrors.append(e.toString());\n\t\t}";
			}
		} else if (c.getName().startsWith("verify")) {
			result = doVerify(c, "", false);
			if(result != null){
				result = "\n\t\ttry {\n\t\t\t" + result +"\n\t\t} catch (Error e) {\n\t\t\tverificationErrors.append(e.toString());\n\t\t}";
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	private String doAndWait(Command c) {
		String mName = c.getName().substring( 0, c.getName().length() - "AndWait".length());
		Element element = adaptor.findByName(mName);
		if (element != null) {
			return element.process(c);
		}
		return null;
	}
	
	/**
	 * Transforme les commandes de type assertXXXXX en instruction Java
	 * @param c
	 * @param not
	 * @param methodNotPresent. Indique qu'il s'agit d'une commande du type assertXXXXXNotPresent
	 * @return
	 */
	private String doAssert(Command c, String not, boolean methodNotPresent) {
		String mName = c.getName().substring(("assert" + not).length());
		Element element = adaptor.findByName("is" + mName);
		boolean isMatcheable = StringUtils.contains(c.getValue(),"regexp:");
		
		//Creation de l'expected
		Object expectedElement = "\""+FilteringUtils.filter(c.getTarget())+"\"";
		
		if (element != null && ! isMatcheable) {
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "("+expectedElement+"," + element.process(c) + ");";
		}
		
		//Commande de type regexp
		if(element != null && isMatcheable){
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "("+expectedElement+"," + doMatch(c, element) + ");";
		}
		
		element = adaptor.findByName("get" + mName);
		if (element != null && ! isMatcheable) {
			return "Assert.assert" + not + "Equals("+expectedElement+"," + compareLeftRight(element, c) + ");";
		}
		
		//Commande de type regexp
		if(element != null && isMatcheable){
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "("+expectedElement+"," + doMatch(c, element) + ");";
		}
		
		//Commande de type assertXXXXXNotPresent
		if(methodNotPresent){
			mName = mName.replace(NOT_FLAG, "");
			element = adaptor.findByName("is" + mName);
			return "Assert.assertFalse("+expectedElement+"," + element.process(c) + ");";
		}
		return null;
	}
	
	/**
	 * Transforme une commande de type assert/verify by regex en instruction
	 * Java de type Match
	 * @param c
	 * @param mName
	 * @return
	 */
	private String doMatch(Command command, Element element) {
		return "Pattern.compile(\"" +FilteringUtils.filter(StringUtils.substringAfter(command.getValue(), "regexp:"))+ "\").matcher("+element.process(command)+").find()";
	}

	/**
	 * Returns String of the format ["name", session().getMethod("target")]
	 */
	private String compareLeftRight(Element element, Command command) {
//		boolean noArgs = m.getParameterTypes().length == 0;
//		boolean noArgs = false;
		String leftValue = "";
		if(StringUtils.isNotBlank(command.getValue())) {
			leftValue = command.getValue();
		} else {
			leftValue = command.getTarget();
		}
//		String left = noArgs? command.getTarget() : command.getValue();
		
		Class<?> returnType = element.getReturnType();
//		if(returnType != null){
//			return "new "+returnType.getSimpleName()+"(\"" + FilteringUtils.filter(left) + "\"), " + element.process(command);
//		} else {
//			return "\"" + FilteringUtils.filter(left) + "\", " + element.process(command);
//		}
		
		String leftElement = returnType == Integer.class ? leftValue: "\"" + FilteringUtils.filter(leftValue) + "\"";
		return leftElement + ", " + element.process(command);
	}
	
	/**
	 * Gestion des commandes de type verifyXXXXX
	 * @param c
	 * @param not
	 * @param methodNotPresent. Indique qu'il s'agit d'une commande du type verifyXXXXXNotPresent
	 * @return
	 */
	private String doVerify(Command c, String not, boolean methodNotPresent) {
		String mName = c.getName().substring(("verify" + not).length());
		Element element = adaptor.findByName("is" + mName);
		if (element != null) {
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "(" + element.process(c) + ");";
		}
		element = adaptor.findByName("get" + mName);
		if (element != null) {
			return "Assert.assert" + not + "Equals(" + compareLeftRight(element, c) + ");";
		}
		if(methodNotPresent){
			mName = mName.replace(NOT_FLAG, "");
			element = adaptor.findByName("is" + mName);
			return "Assert.assertFalse(" + element.process(c) + ");";
		}
		
		return null;
	}
	

	/**
	 * 
	 * @param c
	 * @param Not
	 * @param methodNotPresent. Indique qu'il s'agit d'une commande du type XXXXXNotPresent
	 * @return
	 */
	private String doWaitFor(Command c, String Not, boolean methodNotPresent) {
		String mName = c.getName().substring(("waitFor" + Not).length());
		String pipe = "";
		if(methodNotPresent){
			mName = mName.replace(NOT_FLAG, "");
			pipe = "!";
			
		} else if(Not.equals(NOT_FLAG)){
			pipe = "!";
		}
		Element element = adaptor.findByName("is" + mName);
		if (element != null) {
			return forBlock( "if (" + pipe + " " + element.process(c) +")", c);
		}
		
		
		element = adaptor.findByName("get" + mName);
		if (element != null) {
			return forBlock("if (" + pipe + " " + element.process(c) +")", c);
		}
		return null;
	}
	/**
	 * @param adaptor the adaptor to set
	 */
	public void setAdaptor(SeleniumWebDriverAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	
}
