/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data.test;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Raphael
 * The 'html' XStream class
 */
@XStreamAlias("html")
public class TestHtml implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6304392350558372451L;
	
	/**
	 * The 'head' tag
	 */
	private TestHead head;
	
	/**
	 * The 'body' tag
	 */
	private TestBody body;

	/**
	 * @return the head
	 */
	public TestHead getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(TestHead head) {
		this.head = head;
	}

	/**
	 * @return the body
	 */
	public TestBody getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(TestBody body) {
		this.body = body;
	}

}
