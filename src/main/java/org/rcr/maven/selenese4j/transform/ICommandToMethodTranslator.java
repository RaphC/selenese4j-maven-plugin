package org.rcr.maven.selenese4j.transform;

public interface ICommandToMethodTranslator {

	/**
	 * transforme la commane créé en instructions java
	 * @param c
	 * @return
	 */
	public String discovery(Command c);
}