/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;
import java.util.Locale;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class LocaleAndFormatNowFunctionTestCase {

	private LocaleAndFormatNowFunction localeAndFormatNowFunction = new LocaleAndFormatNowFunction();
	
	@Test
	public void matches(){
		Assert.assertFalse(localeAndFormatNowFunction.matches("{@function:now()}"));
		Assert.assertTrue(localeAndFormatNowFunction.matches("{@function:now('sv_SE','MM:yyyy')}"));
		Assert.assertFalse(localeAndFormatNowFunction.matches("{@function:now('chien mechant')}"));
	}
	
	@Test
	public void process(){
		Assert.assertEquals(DateFormatUtils.format(Calendar.getInstance(), "MM:yyyy" , new Locale("sv","SE")), localeAndFormatNowFunction.replaceByValue("{@function:now('sv_SE','MM:yyyy')}"));
		Assert.assertEquals(DateFormatUtils.format(Calendar.getInstance(), "MMMMM dd, yyyy", Locale.CANADA_FRENCH), localeAndFormatNowFunction.replaceByValue("{@function:now('fr_CA','MMMMM dd, yyyy')}"));
		
	}
}
