/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import junit.framework.Assert;

import org.junit.Test;

import com.github.raphc.maven.plugins.selenese4j.translator.UnManagedCommandToMethodTranslator;

/**
 * @author Raphael
 *
 */
public class UnManagedCommandToMethodTranslatorTestCase {

	private UnManagedCommandToMethodTranslator translator = new UnManagedCommandToMethodTranslator();
	
	@Test
	public void discoveryEmptyNameCommand(){
		Command command = new Command("","link=Test","4751");
		String result = translator.discovery(command);
		Assert.assertEquals("", result);
	}
	
	@Test
	public void discoveryOpenCommand(){
		Command command = new Command("open","login.html","");
		String result = translator.discovery(command);
		Assert.assertEquals("selenium.open(\"login.html\");", result);
	}
	
	@Test
	public void discoveryPauseCommand(){
		Command command = new Command("pause","180000","");
		String result = translator.discovery(command);
		Assert.assertEquals("Thread.sleep(180000);", result);
	}
	
	@Test
	public void discoveryAssertWithRegexpCommand(){
		Command command = new Command("assertText","xpath=//div[@id='header-login']","regexp:.*SARL TOTO \\[ Mr Integration Test \\].*");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"xpath=//div[@id='header-login']\",Pattern.compile(\".*SARL TOTO \\\\[ Mr Integration Test \\\\].*\").matcher(selenium.getText(\"xpath=//div[@id='header-login']\")).find());", result);
	}

	@Test
	public void discoverySnippetJavaCommand(){
		Command command = new Command("{@snippet:java selenium.waitForCondition(\"selenium.isConfirmationPresent()\",\"5000\");\nif(selenium.isConfirmationPresent()){\nAssert.assertTrue(selenium.getConfirmation().matches(\"Confirmez-vous l'annulation de(s) 1 pr.{1} ?\"));}	@snippet}","","");
		String result = translator.discovery(command);
		Assert.assertEquals(" selenium.waitForCondition(\"selenium.isConfirmationPresent()\",\"5000\");\nif(selenium.isConfirmationPresent()){\nAssert.assertTrue(selenium.getConfirmation().matches(\"Confirmez-vous l'annulation de(s) 1 pr.{1} ?\"));}	", result);
	}
	
	@Test
	public void discoverySnippetJavaCommandWrongLocation(){
		Command command = new Command("type","{@snippet:java System.out.println(\"Hello World\");","");
		String result = translator.discovery(command);
		Assert.assertEquals("selenium.type(\"{@snippet:java System.out.println(\\\"Hello World\\\");\",\"\");", result);
	}
	
	@Test(expected=Exception.class)
	public void discoveryFunctionDefinedInNameCommand(){
		Command command = new Command("{@function:now()}","My target in text: Hello World","true");
		translator.discovery(command);
	}
	
	@Test
	public void discoveryAssertElementNotPresentCommand(){
		Command command = new Command("assertElementNotPresent","link=my_link","");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertFalse(\"link=my_link\",selenium.isElementPresent(\"link=my_link\"));", result);
	}
	
	@Test
	public void discoveryDoubleClickCommand(){
		Command command = new Command("doubleClick","link=my_link","");
		String result = translator.discovery(command);
		Assert.assertEquals("selenium.doubleClick(\"link=my_link\");", result);
	}
}
