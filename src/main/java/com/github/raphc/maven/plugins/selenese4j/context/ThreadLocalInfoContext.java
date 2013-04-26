/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.context;

/**
 * @author Raphael
 * 
 */
public class ThreadLocalInfoContext {

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
