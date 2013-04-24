/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.raphc.maven.plugins.selenese4j.translator.SeleniumWebDriverAdaptor;
import com.github.raphc.maven.plugins.selenese4j.translator.WebDriverCommandToMethodTranslator;

/**
 * @author Raphael
 *
 */
public class WebDriverCommandToMethodTranslatorTestCase {

	private static WebDriverCommandToMethodTranslator translator = new WebDriverCommandToMethodTranslator();
	
	private static SeleniumWebDriverAdaptor adaptor = new SeleniumWebDriverAdaptor();
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		adaptor.registerElements();
		translator.setAdaptor(adaptor);
	}
	
	@Test
	public void discoveryOpenCommand(){
		Command command = new Command("open","login.html","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.get(\"login.html\");", result);
	}
}
