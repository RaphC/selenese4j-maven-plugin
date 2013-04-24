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
	
	@Test
	public void discoveryPauseCommand(){
		Command command = new Command("pause","180000","");
		String result = translator.discovery(command);
		Assert.assertEquals("Thread.sleep(180000);", result);
	}
	
	@Test
	public void discoveryEchoCommand(){
		Command command = new Command("echo","Mon nom est personne","");
		String result = translator.discovery(command);
		Assert.assertEquals("System.out.println(\"Mon nom est personne\");", result);
	}
}
