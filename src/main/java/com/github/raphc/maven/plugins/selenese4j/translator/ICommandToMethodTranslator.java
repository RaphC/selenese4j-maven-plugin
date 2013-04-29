package com.github.raphc.maven.plugins.selenese4j.translator;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;

public interface ICommandToMethodTranslator {

	/**
	 * transform the given command to a Java source code
	 * @param c
	 * @return
	 */
	public String discovery(Command c);
}