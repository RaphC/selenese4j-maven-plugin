/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.xstream.converter;

import com.github.raphc.maven.plugins.selenese4j.source.data.Td;
import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * @author Raphael
 *
 */
public class TdContentConverter implements SingleValueConverter {

	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class clazz) {
		return clazz.equals(Td.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.SingleValueConverter#toString(java.lang.Object)
	 */
	public String toString(Object obj) {
		return ((Td) obj).getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.SingleValueConverter#fromString(java.lang.String)
	 */
	public Object fromString(String s) {
		Td td = new Td();
		td.setContent(s);
		return td;
	}

}
