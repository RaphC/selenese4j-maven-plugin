package com.github.raphc.maven.plugins.selenese4j.transform;

public interface ICommandToMethodTranslator {

	/**
	 * transforme la commane créé en instructions java
	 * @param c
	 * @return
	 */
	public String discovery(Command c);
}