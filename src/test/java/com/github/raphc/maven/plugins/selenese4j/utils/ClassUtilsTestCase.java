/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class ClassUtilsTestCase {

	
	@Test
	public void normalizeClassName() {
		Assert.assertEquals("SimpleAndCorrectFileNameTestCase", ClassUtils.normalizeClassName("simpleAndCorrectFileName"));
		Assert.assertEquals("SimpleWithBlankAndDashFileNameTestCase", ClassUtils.normalizeClassName("simpleWith Blank And Dash -FileName"));
		Assert.assertEquals("SimpleAndCorrectFileNameTestCase", ClassUtils.normalizeClassName("simpléAndCorrèctFileNàme"));
	}
}
