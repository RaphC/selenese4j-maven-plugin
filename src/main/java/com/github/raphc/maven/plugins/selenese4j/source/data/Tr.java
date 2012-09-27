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
@XStreamAlias("tr")
public class Tr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5909323723987858723L;
	
	@XStreamImplicit(itemFieldName="td")
	private List<Td> tds;

	/**
	 * @return the tds
	 */
	public List<Td> getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(List<Td> tds) {
		this.tds = tds;
	}
}
