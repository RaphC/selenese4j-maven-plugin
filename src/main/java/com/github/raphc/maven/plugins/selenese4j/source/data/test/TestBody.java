/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data.test;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Raphael
 * The 'body' XStream class 
 */
@XStreamAlias("body")
public class TestBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2085070614558732828L;
	
	/**
	 * The 'table tag
	 */
	private TestTable table;

	/**
	 * @return the table
	 */
	public TestTable getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(TestTable table) {
		this.table = table;
	}
}
