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
public class FormattedNowFunctionTestCase {

	private FormattedNowFunction formattedNowFunction = new FormattedNowFunction();
	
	@Test
	public void matches(){
		Assert.assertFalse(formattedNowFunction.matches("{@function:now()}"));
		Assert.assertTrue(formattedNowFunction.matches("{@function:now('dd/')}"));
		Assert.assertFalse(formattedNowFunction.matches("{@function:now('yyyy-MM/dd','dodo')}"));
	}
	
	@Test
	public void process(){
		Assert.assertEquals(DateFormatUtils.format(Calendar.getInstance(), "dd/MM/yyyy"), formattedNowFunction.replaceByValue("{@function:now('dd/MM/yyyy')}"));
	}
}
