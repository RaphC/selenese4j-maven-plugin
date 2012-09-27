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
@XStreamAlias("body")
public class Body implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2085070614558732828L;
	
	private Table table;

	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}
}
