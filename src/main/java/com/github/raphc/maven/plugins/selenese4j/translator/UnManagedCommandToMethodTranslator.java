/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.functions.AbstractPreDefinedFunction;
import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.utils.FilteringUtils;
import com.github.raphc.maven.plugins.selenese4j.utils.PatternUtils;
import com.thoughtworks.selenium.Selenium;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author Raphael
 *
 */
public class UnManagedCommandToMethodTranslator extends AbstractCommandToMethodTranslator implements ICommandToMethodTranslator {

	private final static String SELENIUM = "selenium";
	
	protected final static Map<String, Method> methods = new HashMap<String, Method>();
	
	/**
	 * 
	 */
	public UnManagedCommandToMethodTranslator(){
		init();
	}
	
	/**
	 * Chargement des methodes exposees par l'API Selenium
	 */
	private static void init() {
		Class<Selenium> selC = Selenium.class;
		for (Method m : selC.getMethods()) {
			if ("void".equals(m.getReturnType().toString()) 
					||"boolean".equals(m.getReturnType().toString()) 
					|| m.getReturnType().isAssignableFrom(String.class)
					|| m.getReturnType().isAssignableFrom(Number.class)) {
				Class<?>[] types = m.getParameterTypes();
				if (types.length == 0) {
					methods.put(m.getName(), m);
					continue;
				}
				for (Class<?> t : types) {
					if (!t.isAssignableFrom(String.class)) {
						continue;
					}
				}
				methods.put(m.getName(), m);
			}
		}
	}

	/*
	 * (non-Javadoc)
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
			Method m = methods.get(c.getName());
			if(m == null){
				instr = discoveryCustom(c);
			} else {
				instr = getMethodBody(m, c) + ";";
			}
		}
		logger.log(Level.FINE, "Java instruction generated ["+instr+"].");
		return instr;
	}
	
	/**
	 * Transforme la methode m issue de la commande c en instruction java interpretable par l'API selenium
	 * Ne sont supportés que les m retournant un type {@link String}
	 * @param m
	 * @param c
	 * @return the java code based on selenium API and matching the [@link c}
	 */
	private String getMethodBody(Method m, Command c) {
		StringBuilder sb = new StringBuilder(SELENIUM);
		sb.append(".");
		sb.append(m.getName());
		sb.append("(");
		int i = 1;
		Class<?>[] pTypes = m.getParameterTypes();
		for (Class<?> cl : pTypes) {
			if (cl.isAssignableFrom(String.class)) {
				if (i == 1) {
					sb.append("\"" + FilteringUtils.filter(c.getTarget()) + "\"");
				} else if (i == 2) {
					sb.append(",");
					sb.append("\"" + FilteringUtils.filter(c.getValue()) + "\"");
				}
				i++;
			} else {
				throw new RuntimeException("Not a supported type.");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * traite les assert, verify et wait
	 * @param c
	 * @return
	 */
	private String discoveryCustom(Command c) {

		String result = null;

		if ("".equals(c.getName()) || c.getName() == null) {
			return "";// empty step
			
		} else if (c.getName().startsWith("waitForNot")) {
			result = doWaitFor(c, NOT_FLAG, false);

		} else if (c.getName().startsWith("waitFor") && c.getName().endsWith("NotPresent")) {
			result = doWaitFor(c, "", true);
			
		} else if (c.getName().startsWith("waitFor")) {
			result = doWaitFor(c, "", false);

		} else if (c.getName().startsWith("verifyNot")) {
			result = doVerify(c, NOT_FLAG, false);

		} else if (c.getName().startsWith("verify") && c.getName().endsWith("NotPresent")) {
			result = doVerify(c, "", true);

		} else if (c.getName().startsWith("verify")) {
			result = doVerify(c, "", false);

		} else if (c.getName().startsWith("assertNot")) {
			result = doAssert(c, NOT_FLAG, false);
			
		} else if (c.getName().startsWith("assert") && c.getName().endsWith("NotPresent")) {
			result = doAssert(c, "", true);
			
		} else if (c.getName().startsWith("assertXpathCount")) {
			result = doXpathCount(c);
			
		} else if (c.getName().startsWith("assert")) {
			result = doAssert(c, "", false);
		
		} else if (c.getName().equalsIgnoreCase("pause")) {
			result = doPause(c);	
		
		} else if (c.getName().equalsIgnoreCase("echo")) {
			result = doEcho(c);
		} else if (c.getName().endsWith("AndWait")) {
			result = doAndWait(c);
		} else if (c.getName().startsWith("store")) {
			result = doStore(c);
		} else if (c.getName().equalsIgnoreCase("sendKeys")) {
            result = doKeyPress(c);
        }

		if (result == null) {
			return "ERROR: Method \"" + c.getName() + "\" not supported yet.";
		}

		return result;
	}
	
	
	/**
	 * Transforme les commandes de type assertXXXXX en instruction Java
	 * @param c
	 * @param not
	 * @param methodNotPresent Indique qu'il s'agit d'une commande du type assertXXXXXNotPresent
	 * @return
	 */
	private String doAssert(Command c, String not, boolean methodNotPresent) {
		String mName = c.getName().substring(("assert" + not).length());
		Method m = methods.get("is" + mName);
		boolean isMatcheable = StringUtils.contains(c.getValue(),"regexp:");
		
		//Creation de l'expected
		Object expectedElement = "\""+FilteringUtils.filter(c.getTarget())+"\"";
		
		if (m != null && ! isMatcheable) {
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "("+expectedElement+"," + getMethodBody(m, c) + ");";
		}
		
		//Commande de type regexp
		if(m != null && isMatcheable){
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "("+expectedElement+"," + doMatch(c, mName) + ");";
		}
		
		m = methods.get("get" + mName);
		//TODO Gerer de façon dynamique le type des expected m.getReturnType() 
		//+ gestion des Abstract et des interfaces + des constructeurs avec arg de type String en non String
//		Class<?> classToInstantiate = m.getReturnType();
//		InstantiateFactory.getInstance(m.getReturnType(), new Class[]{String.class}, expectedElement);
//		Factory factory = InstantiateFactory.getInstance(method.getReturnType(), new Class[]{String.class}, new Object[]{expectedValue});
//		// TODO On a besoin le l'instruction correspondant a l'instanciation
//		return "new ";
		if (m != null && ! isMatcheable) {
			return "Assert.assert" + not + "Equals("+expectedElement+"," + compareLeftRight(m, c) + ");";
		}
		
		//Commande de type regexp
		if(m != null && isMatcheable){
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "("+expectedElement+"," + doMatch(c, mName) + ");";
		}
		
		//Commande de type assertXXXXXNotPresent
		if(methodNotPresent){
			mName = mName.replace(NOT_FLAG, "");
			m = methods.get("is" + mName);
			return "Assert.assertFalse("+expectedElement+"," + getMethodBody(m, c) + ");";
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private String doAndWait(Command c) {
		String mName = c.getName().substring( 0, c.getName().length() - "AndWait".length());
		Method m = methods.get(mName);
		if (m != null) {
			return doAndWait(m, c);
		}
		return null;
	}

	/**
	 * Gestion des commandes de type verifyXXXXX
	 * @param c
	 * @param not
	 * @param methodNotPresent Indique qu'il s'agit d'une commande du type verifyXXXXXNotPresent
	 * @return
	 */
	private String doVerify(Command c, String not, boolean methodNotPresent) {
		String mName = c.getName().substring(("verify" + not).length());
		Method m = methods.get("is" + mName);
		if (m != null) {
			return "Assert.assert" + (StringUtils.equalsIgnoreCase(not, NOT_FLAG) ? "False" : "True") + "(" + getMethodBody(m, c) + ");";
		}
		m = methods.get("get" + mName);
		if (m != null) {
			return "Assert.assert" + not + "Equals(" + compareLeftRight(m, c) + ");";
		}
		if(methodNotPresent){
			mName = mName.replace(NOT_FLAG, "");
			m = methods.get("is" + mName);
			return "Assert.assertFalse(" + getMethodBody(m, c) + ");";
		}
		return null;
	}

	/**
	 * 
	 * @param c
	 * @param Not
	 * @param methodNotPresent Indique qu'il s'agit d'une commande du type XXXXXNotPresent
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
		Method m = methods.get("is" + mName);
		if (m != null) {
			return forBlock( "if (" + pipe + SELENIUM + "." + m.getName() + "(\"" + PatternUtils.processPattern(FilteringUtils.filter(c.getTarget())) + "\"))", c);
		}
		m = methods.get("get" + mName);
		if (m != null) {
			boolean noArgs = m.getParameterTypes().length == 0;
			if(noArgs){
				return forBlock("if (" + pipe + " " + SELENIUM + "." + m.getName() + "().matches(\"" + PatternUtils.processPattern(FilteringUtils.filter(c.getTarget())) + "\"))", c);
			} else {
				return 
				forBlock("if (" + pipe + " " + SELENIUM + "." + m.getName() + "(\"" + FilteringUtils.filter(c.getTarget()) + "\")" + buildAssert(c, m, c.getValue()) + ")", c);
			}
		}
		return null;
	}
	
	/**
	 * Transforme la commande waitForPageToLoad en selenium.waitForPageToLoad(30000);
	 * @param m
	 * @param c
	 * @return
	 */
	private String doAndWait(Method m, Command c) {
		String s = getMethodBody(m, c);
		return s + ";\n\t\t" + SELENIUM + ".waitForPageToLoad(\"" + DEFAULT_WAIT_FOR_PAGE_TOLOAD + "\");";
	}
	
	/**
	 * Transforme la commande storeXXXX en instruction Java
	 * @param c
	 * @return
	 */
	private String doStore(Command c) {
		String mName = c.getName().substring(("store").length());
		return "String "+c.getValue()+" = " + SELENIUM + ".get"+mName+"(\""+c.getTarget()+"\");";
	}
	
	/**
	 * Transforme une commande de type assert/verify by regex en instruction
	 * Java de type Match
	 * @param c
	 * @param mName
	 * @return
	 */
	private String doMatch(Command c, String mName) {
		return "Pattern.compile(\"" +FilteringUtils.filter(StringUtils.substringAfter(c.getValue(), "regexp:"))+ "\").matcher(" + SELENIUM + ".get" +mName+ "(\"" +c.getTarget()+ "\")).find()";
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	private String doXpathCount(Command c) {
		return "Assert.assertEquals(new Integer(\""+c.getValue()+"\"), " + SELENIUM + ".getXpathCount(\"" +c.getTarget()+ "\"));";
	}

    /**
     *
     * @param c
     * @return
     */
    private String doKeyPress(Command c) {
        return SELENIUM + ".keyPress(\"" + FilteringUtils.filter(c.getTarget()) + "\", \""+ c.getValue() + "\");";
    }

    /**
     *
     * @param c
     * @param m
     * @param value
     * @return
     */
    private String buildAssert(Command c, Method m, String value){
        if(m.getReturnType().isAssignableFrom(String.class)) {
            return ".matches(\"" + PatternUtils.processPattern(FilteringUtils.filter(value)) + "\")";
        }

        if(m.getReturnType().isAssignableFrom(Integer.class)
                && NumberUtils.isNumber(value)){
            return ".intValue() == " + value + "";
        }

        if(m.getReturnType().isAssignableFrom(Long.class)
                && NumberUtils.isNumber(value)){
            return ".longValue() == " + value + "";
        }

        if(m.getReturnType().isAssignableFrom(Float.class)
                && NumberUtils.isNumber(value)){
            return ".floatValue() == " + value + "";
        }

        throw new IllegalArgumentException(
                String.format("invalid command %s. Due to either a wrong value '%s' type or an unsupported return type of the corresponding method '%s' ", c.getName(), value, m.getReturnType().getName()));
    }
	
	/**
	 * Returns String of the format ["name", session().getMethod("target")]
	 */
	private String compareLeftRight(Method m, Command c) {
		boolean noArgs = m.getParameterTypes().length == 0;
		String left = noArgs? c.getTarget() : c.getValue();
		return "\"" + FilteringUtils.filter(left) + "\", " + getMethodBody(m, c);
	}

}
