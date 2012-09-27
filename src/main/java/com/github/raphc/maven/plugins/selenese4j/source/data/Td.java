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
@XStreamAlias("td")
public class Td implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5909323723987858723L;
	
	private String content;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
