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
public class StringSplittingUtilsTestCase {
	
	@Test
	public void split(){
		String[] result = StringSplittingUtils.split("\"{@function:now('en_US','MMMMM dd, yyyy')}\",\"0.00 €\"" ,'"','"');
		Assert.assertEquals(2, result.length);
		Assert.assertEquals("{@function:now('en_US','MMMMM dd, yyyy')}", result[0]);
		Assert.assertEquals("0.00 €", result[1]);
	}

}
