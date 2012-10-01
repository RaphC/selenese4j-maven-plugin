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
public class ArrayUtilsTestCase {

	@Test
	public void unQuotingArrayElement(){
		String[] arraysTest = new String[]{"\"un\"","\"deux\"","\"trois\""};
		String[] result = ArrayUtils.unQuotingArrayElement(arraysTest);
		Assert.assertEquals("deux", result[1]);
	}
}
