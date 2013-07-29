/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.source.data.test;

import java.io.Serializable;

import com.github.raphc.maven.plugins.selenese4j.xstream.converter.TdContentConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * @author Raphael
 * The 'td' XStream class processed by {@link TdContentConverter} to the content attribute
 */
@XStreamAlias("td")
@XStreamConverter(value=TdContentConverter.class, strings={"content"})
public class TestTd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5909323723987858723L;
	
	/**
	 * The 'td' tag content
	 */
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
