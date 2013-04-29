/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.utils.FilteringUtils;

/**
 * @author Raphael
 *
 */
public abstract class AbstractCommandToMethodTranslator implements ICommandToMethodTranslator {

	protected final static String DEFAULT_WAIT_FOR_PAGE_TOLOAD = "30000";
	protected final static String DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT = "60";
	protected final static String DEFAULT_TIMEOUT = "1000";
	protected final static String DEFAULT_LOOP = "60";
	protected static final Pattern SNIPPET_FRAGMENT_PATTERN = Pattern.compile("[\\s\\S]*\\{@snippet\\:java([\\s\\S]*)@snippet\\}[\\s\\S]*");
	
	protected final static String NOT_FLAG = "Not";
	
	/**
	 * The logger
	 */
	protected Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * Transforme la commande pause en Thread.sleep
	 * @param c
	 * @return
	 */
	protected String doPause(Command c) {
		return "Thread.sleep("+c.getTarget()+");";
	}
	
	/**
	 * 
	 * @param condition
	 * @param c
	 * @return
	 */
	protected String forBlock(String condition, Command c) {
		String descr = c.getTarget();
		if(c.getValue() != null && !c.getValue().equals("")){
			descr = c.getValue();
		}
		descr = c.getName() + ":" + descr;
		return "for (int second = 0;; second++) {" +
		"\n\t\t	if (second >= " + DEFAULT_LOOP + ") Assert.fail(\"timeout '" + FilteringUtils.filter(descr) + "' \");" +
		"\n\t\t	try { " + condition + " break; } catch (Exception e) {}" +
		"\n\t\t	Thread.sleep(" + DEFAULT_TIMEOUT + ");" +
		"\n\t\t}";
	}
	
	/**
	 * Transforme la commande echo en System.out.println
	 * @param c
	 * @return
	 */
	protected String doEcho(Command c) {
		return "System.out.println(\"" + FilteringUtils.filter(c.getTarget()) + "\");";
	}
}
