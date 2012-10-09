/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class DefaultNowFunctionTestCase {

	private DefaultNowFunction defaultNowFunction = new DefaultNowFunction();
	
	@Test
	public void matches(){
		Assert.assertTrue(defaultNowFunction.matches("{@function:now()}"));
		Assert.assertFalse(defaultNowFunction.matches("{@function:nowd()}"));
		Assert.assertFalse(defaultNowFunction.matches("{@function:now('dodo')}"));
	}
	
	@Test
	public void process(){
		Assert.assertEquals(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"), defaultNowFunction.replaceByValue("{@function:now()}"));
	}
}
