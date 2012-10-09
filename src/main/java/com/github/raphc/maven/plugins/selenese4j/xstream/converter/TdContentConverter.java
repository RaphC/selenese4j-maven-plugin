/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.xstream.converter;

import com.github.raphc.maven.plugins.selenese4j.source.data.test.TestTd;
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
		return clazz.equals(TestTd.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.SingleValueConverter#toString(java.lang.Object)
	 */
	public String toString(Object obj) {
		return ((TestTd) obj).getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.SingleValueConverter#fromString(java.lang.String)
	 */
	public Object fromString(String s) {
		TestTd td = new TestTd();
		td.setContent(s);
		return td;
	}

}
