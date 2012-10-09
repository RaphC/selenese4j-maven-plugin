/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class LocaleAndFormatDateAddFunctionTestCase {

	
	private LocaleAndFormatDateAddFunction localeAndFormatDateAddFunction = new LocaleAndFormatDateAddFunction();
	
	@Test
	public void matches(){
		Assert.assertFalse(localeAndFormatDateAddFunction.matches("{@function:now()}"));
		Assert.assertTrue(localeAndFormatDateAddFunction.matches("{@function:dateadd('8','fr_FR','dd:yyyy-MM')}"));
		Assert.assertFalse(localeAndFormatDateAddFunction.matches("{@function:now('yyyy-MM/dd','dodo')}"));
	}
	
	@Test
	public void process(){
		GregorianCalendar expectedDay = (GregorianCalendar) GregorianCalendar.getInstance(Locale.FRANCE);
		expectedDay.add(GregorianCalendar.DATE, 8);
		Assert.assertEquals(DateFormatUtils.format(expectedDay, "dd:yyyy-MM"), localeAndFormatDateAddFunction.replaceByValue("{@function:dateadd('8','fr_FR','dd:yyyy-MM')}"));
	}
}
