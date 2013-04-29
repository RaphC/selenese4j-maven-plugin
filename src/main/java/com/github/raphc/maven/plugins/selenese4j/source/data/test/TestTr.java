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
 * The 'tr' XStream class
 */
@XStreamAlias("tr")
public class TestTr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5909323723987858723L;
	
	/**
	 * The 'td' tag list
	 */
	@XStreamImplicit(itemFieldName="td")
	private List<TestTd> tds;

	/**
	 * @return the tds
	 */
	public List<TestTd> getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(List<TestTd> tds) {
		this.tds = tds;
	}
}
