/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class FilteringUtilsTestCase {

	
	@Test
	public void filter(){
		String quotedString = "\"my-string\\\"";
		Assert.assertEquals("\\\"my-string\\\\\\\"", FilteringUtils.filter(quotedString));
	}
}
