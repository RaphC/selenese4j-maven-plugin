/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data.test;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author Raphael
 * The 'tbody' XStream class
 */
@XStreamAlias("tbody")
public class TestTbody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688273347359513186L;

	/**
	 * The 'tr' tags
	 */
	@XStreamImplicit(itemFieldName="tr")
	private List<TestTr> trs;

	/**
	 * @return the trs
	 */
	public List<TestTr> getTrs() {
		return trs;
	}

	/**
	 * @param trs the trs to set
	 */
	public void setTrs(List<TestTr> trs) {
		this.trs = trs;
	}
	
}
