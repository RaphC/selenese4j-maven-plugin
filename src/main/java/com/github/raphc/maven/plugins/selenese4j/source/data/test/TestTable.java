/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data.test;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author Raphael
 * The 'table' XStream class
 */
@XStreamAlias("table")
public class TestTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8333553222779437405L;
	
	/**
	 * The 'thead' tag
	 */
	@XStreamOmitField
	private TestThead thead;
	
	/**
	 * The 'tbody' tag
	 */
	private TestTbody tbody;

	/**
	 * @return the tbody
	 */
	public TestTbody getTbody() {
		return tbody;
	}

	/**
	 * @param tbody the tbody to set
	 */
	public void setTbody(TestTbody tbody) {
		this.tbody = tbody;
	}

	/**
	 * @return the thead
	 */
	public TestThead getThead() {
		return thead;
	}

	/**
	 * @param thead the thead to set
	 */
	public void setThead(TestThead thead) {
		this.thead = thead;
	}
	
}
