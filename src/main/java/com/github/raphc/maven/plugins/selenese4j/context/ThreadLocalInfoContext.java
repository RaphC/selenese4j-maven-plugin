/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.context;

/**
 * @author Raphael
 * Stores the transformation context
 */
public class ThreadLocalInfoContext {

	/**
	 * Thread local variable which keep the transformation context
	 */
	public static final ThreadLocal<InfoContext> executionThreadLocal = new ThreadLocal<InfoContext>();

	public static void set(InfoContext infoContext) {
		executionThreadLocal.set(infoContext);
	}

	public static void unset() {
		executionThreadLocal.remove();
	}

	public static InfoContext get() {
		return executionThreadLocal.get();
	}
}
