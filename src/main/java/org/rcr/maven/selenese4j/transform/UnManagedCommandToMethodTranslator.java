/**
 * 
 */
package org.rcr.maven.selenese4j.transform;

import java.lang.reflect.Method;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;

/**
 * @author Raphael
 *
 */
public class UnManagedCommandToMethodTranslator extends AbstractCommandToMethodTranslator implements ICommandToMethodTranslator {

	private final static String SELENIUM = "selenium";
	
	/**
	 * 
	 */
	public UnManagedCommandToMethodTranslator() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.roussev.selenium4j.transform.ICommandToMethodTranslator#discovery(org.roussev.selenium4j.transform.Command)
	 */
	public String discovery(Command c) {
		String instr = null;
		Method m = methods.get(c.getName());
		if(m == null){
			instr = discoveryCustom(c);
		} else {
			instr = getMethodBody(m, c) + ";";
		}
		logger.log(Level.FINE, "Java instruction generated ["+instr+"].");
		return instr;
	}
	
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
					sb.append("\"" + filter(c.getTarget()) + "\"");
				} else if (i == 2) {
					sb.append(",");
					sb.append("\"" + filter(c.getValue()) + "\"");
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
			result = doWaitFor(c, "Not", false);

		} else if (c.getName().startsWith("waitFor") && c.getName().endsWith("NotPresent")) {
			result = doWaitFor(c, "", true);
			
		} else if (c.getName().startsWith("waitFor")) {
			result = doWaitFor(c, "", false);

		} else if (c.getName().startsWith("verifyNot")) {
			result = doVerify(c, "Not", false);

		} else if (c.getName().startsWith("verify") && c.getName().endsWith("NotPresent")) {
			result = doVerify(c, "", true);

		} else if (c.getName().startsWith("verify")) {
			result = doVerify(c, "", false);

		} else if (c.getName().startsWith("assertNot")) {
			result = doAssert(c, "Not", false);
			
		} else if (c.getName().startsWith("assert") && c.getName().endsWith("NotPresent")) {
			result = doAssert(c, "", true);
			
		} else if (c.getName().startsWith("assert")) {
			result = doAssert(c, "", false);
		
		} else if (c.getName().equalsIgnoreCase("pause")) {
			result = doPause(c);	
		
		} else if (c.getName().endsWith("AndWait")) {
			result = doAndWait(c);
		
		} else if (c.getName().startsWith("store")) {
			result = doStore(c);
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
	 * @param methodNotPresent
	 * @return
	 */
	private String doAssert(Command c, String not, boolean methodNotPresent) {
		String mName = c.getName().substring(("assert" + not).length());
		Method m = methods.get("is" + mName);
		boolean isMatcheable = StringUtils.contains(c.getValue(),"regexp:");
		
		if (m != null && ! isMatcheable) {
			return "Assert.assert" + not + "True(\""+c.getTarget()+"\"," + getMethodBody(m, c) + ");";
		}
		
		//Commande de type regexp
		if(m != null && isMatcheable){
			return "Assert.assert" + not + "True(\""+c.getTarget()+"\"," + doMatch(c, mName) + ");";
		}
		
		m = methods.get("get" + mName);
		if (m != null && ! isMatcheable) {
			return "Assert.assert" + not + "Equals(\""+c.getTarget()+"\"," + compareLeftRight(m, c) + ");";
		}
		
		//Commande de type regexp
		if(m != null && isMatcheable){
			return "Assert.assert" + not + "True(\""+c.getTarget()+"\"," + doMatch(c, mName) + ");";
		}
		
		if(methodNotPresent){
			mName = mName.replace("Not", "");
			m = methods.get("is" + mName);
			return "verifyFalse(" + getMethodBody(m, c) + ");";
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
	 * @param Not
	 * @param methodNotPresent
	 * @return
	 */
	private String doVerify(Command c, String Not, boolean methodNotPresent) {
		String mName = c.getName().substring(("verify" + Not).length());
		Method m = methods.get("is" + mName);
		if (m != null) {
			return "Assert.assert" + Not + "True(" + getMethodBody(m, c) + ");";
		}
		m = methods.get("get" + mName);
		if (m != null) {
			return "Assert.assert" + Not + "Equals(" + compareLeftRight(m, c) + ");";
		}
		if(methodNotPresent){
			mName = mName.replace("Not", "");
			m = methods.get("is" + mName);
			return "verifyFalse(" + getMethodBody(m, c) + ");";
		}
		return null;
	}

	private String doWaitFor(Command c, String Not, boolean methodNotPresent) {
		String mName = c.getName().substring(("waitFor" + Not).length());
		String pipe = "";
		if(methodNotPresent){
			mName = mName.replace("Not", "");
			pipe = "!";
			
		} else if(Not.equals("Not")){
			pipe = "!";
		}
		Method m = methods.get("is" + mName);
		if (m != null) {
			return forBlock( "if (" + pipe + SELENIUM + "." + m.getName() + "(\"" + processRegex(filter(c.getTarget())) + "\"))", c);
		}
		m = methods.get("get" + mName);
		if (m != null) {
			boolean noArgs = m.getParameterTypes().length == 0;
			if(noArgs){
				return forBlock("if (" + pipe + " " + SELENIUM + "." + m.getName() + "().matches(\"" + processRegex(filter(c.getTarget())) + "\"))", c);
			} else {
				return 
				forBlock("if (" +pipe + " " + SELENIUM + "." + m.getName() + "(\"" + filter(c.getTarget()) + "\").matches(\"" + processRegex(filter(c.getValue())) + "\"))", c);
			}
		}
		return null;
	}

	private static String forBlock(String condition, Command c) {
		String descr = c.getTarget();
		if(c.getValue() != null && !c.getValue().equals("")){
			descr = c.getValue();
		}
		descr = c.getName() + ":" + descr;
		return "for (int second = 0;; second++) {" +
		"\n\t\t	if (second >= " + DEFAULT_LOOP + ") Assert.fail(\"timeout '" + filter(descr) + "' \");" +
		"\n\t\t	try { " + condition + " break; } catch (Exception e) {}" +
		"\n\t\t	Thread.sleep(" + DEFAULT_TIMEOUT + ");" +
		"\n\t\t}";
	}
	
	
	private String doAndWait(Method m, Command c) {
		String s = getMethodBody(m, c);
		return s + ";\n\t\t" + SELENIUM + ".waitForPageToLoad(\"" + DEFAULT_WAIT_FOR_PAGE_TOLOAD + "\");";
	}
	
	/**
	 * Transforme la commande pause en Thread.sleep
	 * @param c
	 * @return
	 */
	private String doPause(Command c) {
		return "Thread.sleep("+c.getValue()+");";
	}
	
	/**
	 * Transforme la commande storeXXXX en instruction Java
	 * @param c
	 * @return
	 */
	private String doStore(Command c) {
		String mName = c.getName().substring(("store").length());
		return "String "+c.getValue()+" = selenium.get"+mName+"(\""+c.getTarget()+"\");";
	}
	
	/**
	 * Transforme une commande de type assert/verify by regex en instruction
	 * Java de type Match
	 * @param c
	 * @param mName
	 * @return
	 */
	private String doMatch(Command c, String mName) {
		return "Pattern.compile(\"" +filter(StringUtils.substringAfter(c.getValue(), "regexp:"))+ "\").matcher(selenium.get" +mName+ "(\"" +c.getTarget()+ "\")).find()";
	}
	
	/**
	 * Returns String of the format ["name", session().getMethod("target")]
	 */
	private String compareLeftRight(Method m, Command c) {
		boolean noArgs = m.getParameterTypes().length == 0;
		String left = noArgs? c.getTarget() : c.getValue();
		return "\"" + filter(left) + "\", " + getMethodBody(m, c);
	}
	
	private static String processRegex(String value) {
		String result = value;
		
		if(result != null){
			result = result.replace("*", "[\\\\s\\\\S]*");
			result = result.replace(".", "\\\\.");
		}
		
		return result;
	}

}
