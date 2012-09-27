/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Raphael
 *
 */
@XStreamAlias("table")
public class Table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8333553222779437405L;
	
	private Thead thead;
	
	private Tbody tbody;

	/**
	 * @return the tbody
	 */
	public Tbody getTbody() {
		return tbody;
	}

	/**
	 * @param tbody the tbody to set
	 */
	public void setTbody(Tbody tbody) {
		this.tbody = tbody;
	}

	/**
	 * @return the thead
	 */
	public Thead getThead() {
		return thead;
	}

	/**
	 * @param thead the thead to set
	 */
	public void setThead(Thead thead) {
		this.thead = thead;
	}
	
}
