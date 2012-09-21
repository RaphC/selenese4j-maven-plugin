/**
 * 
 */
package org.rcr.maven.selenese4j.transform;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Raphael
 *
 */
public abstract class AbstractCommandToMethodTranslator implements ICommandToMethodTranslator {

	protected final static String DEFAULT_WAIT_FOR_PAGE_TOLOAD = "30000";
	protected final static String DEFAULT_TIMEOUT = "1000";
	protected final static String DEFAULT_LOOP = "60";
	
	protected final static Map<String, Method> methods = new HashMap<String, Method>();
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	public AbstractCommandToMethodTranslator(){
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
					|| m.getReturnType().isAssignableFrom(String.class)) {
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
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	protected static String filter(String s) {
		if(s != null){
			s = s.replace("\\", "\\\\");
			s = s.replace("\"", "\\\"");
		}
		return s;
	}

	
}
