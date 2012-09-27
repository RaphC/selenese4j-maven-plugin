/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author Raphael
 *
 */
@XStreamAlias("thead")
public class Thead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XStreamImplicit(itemFieldName="tr")
	private List<Tr> trs;

	/**
	 * @return the trs
	 */
	public List<Tr> getTrs() {
		return trs;
	}

	/**
	 * @param trs the trs to set
	 */
	public void setTrs(List<Tr> trs) {
		this.trs = trs;
	}
}
