/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data.test;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Raphael
 * The 'head' XStream class
 */
@XStreamAlias("head")
public class TestHead implements Serializable {

	/**
	 * The 'meta' tag
	 */
	private String meta;
	
	/**
	 * The 'title' tag
	 */
	private String title;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8379149301482626905L;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the meta
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(String meta) {
		this.meta = meta;
	}
	
}
