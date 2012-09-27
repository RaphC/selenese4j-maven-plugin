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
@XStreamAlias("html")
public class Html implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6304392350558372451L;
	
	private Head head;
	
	private Body body;

	/**
	 * @return the head
	 */
	public Head getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(Head head) {
		this.head = head;
	}

	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(Body body) {
		this.body = body;
	}

}
