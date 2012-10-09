package com.github.raphc.maven.plugins.selenese4j.transform;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Raphael
 * 
 */
public class Command {

	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String target;
	
	/**
	 * 
	 */
	private String value;

	/**
	 * 
	 */
	public Command(){}
	
	/**
	 * @param name
	 * @param target
	 * @param value
	 */
	public Command(String name, String target, String value) {
		this.name = name;
		this.target = target;
		this.value = value;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param t
	 */
	public void setTarget(String t) {
		this.target = t;
	}

	/**
	 * 
	 * @param v
	 */
	public void setValue(String v) {
		this.value = v;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
