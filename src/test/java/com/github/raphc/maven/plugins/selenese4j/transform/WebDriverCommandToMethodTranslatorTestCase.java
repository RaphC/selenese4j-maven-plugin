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
	
	private static SeleniumWebDriverAdaptor adaptor = null;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		adaptor = new SeleniumWebDriverAdaptor();
		translator.setAdaptor(adaptor);
	}
	
	@Test
	public void discoveryOpenCommand(){
		Command command = new Command("open","login.html","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.get(baseUrl+\"/login.html\");", result);
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
	
	@Test
	public void discoveryStoreCommand(){
		Command command = new Command("store","5487","eltNumber");
		String result = translator.discovery(command);
		Assert.assertEquals("String eltNumber = \"5487\";", result);
	}
	
	@Test
	public void discoveryCheckByIdCommand(){
		Command command = new Command("check","id='test'","");
		String result = translator.discovery(command);
		Assert.assertEquals("if (!driver.findElement(By.id(\"'test'\")).isSelected()) {\ndriver.findElement(By.id(\"'test'\")).click();\n};", result);
	}
	
	@Test
	public void discoveryCheckByNameCommand(){
		Command command = new Command("check","name='test'","");
		String result = translator.discovery(command);
		Assert.assertEquals("if (!driver.findElement(By.name(\"'test'\")).isSelected()) {\ndriver.findElement(By.name(\"'test'\")).click();\n};", result);
	}
	
	@Test
	public void discoveryCheckAndWaitByNameCommand(){
		Command command = new Command("checkAndWait","name='test'","");
		String result = translator.discovery(command);
		Assert.assertEquals("if (!driver.findElement(By.name(\"'test'\")).isSelected()) {\ndriver.findElement(By.name(\"'test'\")).click();\n};", result);
	}
	
	@Test
	public void discoveryClickByIdCommand(){
		Command command = new Command("click","id='test'","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.findElement(By.id(\"'test'\")).click();", result);
	}
	
	@Test
	public void discoveryClickByLinkCommand(){
		Command command = new Command("clickAndWait","link=regexp:.*REJ-[0-9]{6}-[0-9]*\\.xml","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.findElement(By.linkText(\"regexp:.*REJ-[0-9]{6}-[0-9]*\\\\.xml\")).click();", result);
	}
	
	@Test
	public void discoveryCloseCommand(){
		Command command = new Command("close","","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.close();", result);
	}
	
	@Test
	public void discoveryAssertAttributeWithRegexpCommand(){
		Command command = new Command("assertAttribute","id=tresor@name","regexp:.*SARL TOTO.*");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"id=tresor@name\",Pattern.compile(\".*SARL TOTO.*\").matcher(driver.findElement(By.id(\"tresor\")).getAttribute(\"name\")).find());", result);
	}
	
	@Test
	public void discoveryAssertAttributeWithValueCommand(){
		Command command = new Command("assertAttribute","id=tresor@type","radio");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"id=tresor@type\",\"radio\", driver.findElement(By.id(\"tresor\")).getAttribute(\"type\"));", result);
	}
	
	@Test
	public void discoveryAssertBodyTextWithValueCommand(){
		Command command = new Command("assertBodyText","","simple text");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"\",\"simple text\", driver.findElement(By.tagName(\"BODY\")).getText());", result);
	}
	
	@Test
	public void discoveryAssertLocationWithRegexpCommand(){
		Command command = new Command("assertLocation","","regexp:.*all");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"\",Pattern.compile(\".*all\").matcher(driver.getCurrentUrl()).find());", result);
	}
	
	@Test
	public void discoveryAssertTextCommand(){
		Command command = new Command("assertText","id=tresor","argent");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"id=tresor\",\"argent\", driver.findElement(By.id(\"tresor\")).getText());", result);
	}
	
	@Test
	public void discoveryAssertTitleCommand(){
		Command command = new Command("assertTitle","titre","");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"titre\",\"titre\", driver.getTitle());", result);
	}
	
	@Test
	public void discoveryGoBackCommand(){
		Command command = new Command("goBack","","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.navigate().back();", result);
	}
	
	@Test
	public void discoveryAssertValueWithValueCommand(){
		Command command = new Command("assertValue","id=blue","chien");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"id=blue\",\"chien\", driver.findElement(By.id(\"blue\")).getAttribute(\"value\"));", result);
	}

	@Test
	public void discoveryAssertXpathCountCommand(){
		Command command = new Command("assertXpathCount","//td","6");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"//td\",6, driver.findElements(By.xpath(\"//td\")).size());", result);
	}
	
	@Test
	public void discoveryAssertCssCountByIdCommand(){
		Command command = new Command("assertCssCount","id=unik","8");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"id=unik\",8, driver.findElements(By.id(\"unik\")).size());", result);
	}
	
	@Test
	public void discoveryAssertCssCountByCssCommand(){
		Command command = new Command("assertCssCount","css=table","0");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertEquals(\"css=table\",0, driver.findElements(By.cssSelector(\"table\")).size());", result);
	}
	
	@Test
	public void discoveryIsCheckedByXpathCommand(){
		Command command = new Command("assertChecked","xpath=//td[@id='123']","");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"xpath=//td[@id='123']\",driver.findElement(By.xpath(\"//td[@id='123']\")).isSelected());", result);
	}
	
	@Test
	public void discoveryIsCheckedBySlashCommand(){
		Command command = new Command("assertChecked","//td[@id='123']","");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"//td[@id='123']\",driver.findElement(By.xpath(\"//td[@id='123']\")).isSelected());", result);
	}
	
	@Test
	public void discoveryIsDisplayedByIdCommand(){
		Command command = new Command("assertVisible","id=yum","");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"id=yum\",driver.findElement(By.id(\"yum\")).isDisplayed());", result);
	}
	
	@Test
	public void discoveryRefreshCommand(){
		Command command = new Command("refresh","","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.navigate().refresh();", result);
	}
	
	@Test
	public void discoverySubmitByCssCommand(){
		Command command = new Command("submit","css=#form","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.findElement(By.cssSelector(\"#form\")).submit();", result);
	}
	
	@Test
	public void discoveryTypeByIdCommand(){
		Command command = new Command("type","id=78-uyt","231");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.findElement(By.id(\"78-uyt\")).clear();\n\t\tdriver.findElement(By.id(\"78-uyt\")).sendKeys(\"231\");", result);
	}
	
	@Test
	public void discoverySendKeysByIdCommand(){
		Command command = new Command("sendKeys","id=78-uyt","231");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.findElement(By.id(\"78-uyt\")).sendKeys(\"231\");", result);
	}
	
	@Test
	public void discoveryUnCheckByNameCommand(){
		Command command = new Command("uncheck","name='test'","");
		String result = translator.discovery(command);
		Assert.assertEquals("if (driver.findElement(By.name(\"'test'\")).isSelected()) {\ndriver.findElement(By.name(\"'test'\")).click();\n};", result);
	}
	
	@Test
	public void discoverySelectCommand(){
		Command command1 = new Command("select","id=menu","label=regexp:^[Oo]ther");
		String result1 = translator.discovery(command1);
		Assert.assertEquals("new Select(driver.findElement(By.id(\"menu\"))).selectByVisibleText(\"regexp:^[Oo]ther\");", result1);

		Command command2 = new Command("select","id=menu","value=other");
		String result2 = translator.discovery(command2);
		Assert.assertEquals("new Select(driver.findElement(By.id(\"menu\"))).selectByValue(\"other\");", result2);
		
		Command command3 = new Command("select","id=menu","id=option1");
		String result3 = translator.discovery(command3);
		Assert.assertEquals("new Select(driver.findElement(By.id(\"menu\"))).selectByVisibleText(\"option1\");", result3);
		
		Command command4 = new Command("select","id=menu","index=2");
		String result4 = translator.discovery(command4);
		Assert.assertEquals("new Select(driver.findElement(By.id(\"menu\"))).selectByIndex(2);", result4);
	}
	
	@Test
	public void discoveryVerifyCssCountCommand(){
		Command command = new Command("verifyCssCount","id=douze","7");
		String result = translator.discovery(command);
		Assert.assertEquals("\n\t\ttry {\n\t\t\tAssert.assertEquals(7, driver.findElements(By.id(\"douze\")).size());\n\t\t} catch (Error e) {\n\t\t\tverificationErrors.append(e.toString());\n\t\t}", result);
	}
	
	@Test
	public void discoveryWaitForCheckedCommand(){
		Command command = new Command("waitForChecked","xpath=//input[@id=treize]","");
		String result = translator.discovery(command);
		Assert.assertEquals("for (int second = 0;; second++) {" +
		"\n\t\t	if (second >= 60) Assert.fail(\"timeout 'waitForChecked:xpath=//input[@id=treize]' \");" +
		"\n\t\t	try { if ( driver.findElement(By.xpath(\"//input[@id=treize]\")).isSelected()) break; } catch (Exception e) {}" +
		"\n\t\t	Thread.sleep(1000);" +
		"\n\t\t}", result);
	}
	
	@Test
	public void discoveryAssertTextPresentCommand(){
		Command command1 = new Command("assertTextPresent","toto part à la plage","");
		String result1 = translator.discovery(command1);
		Assert.assertEquals("Assert.assertTrue(\"toto part à la plage\",driver.findElement(By.tagName(\"body\")).getText().contains(\"toto part à la plage\"));", result1);
		
		Command command2 = new Command("assertTextPresent","The IP address of the user is \"blacklisted\"","");
		String result2 = translator.discovery(command2);
		Assert.assertEquals("Assert.assertTrue(\"The IP address of the user is \\\"blacklisted\\\"\",driver.findElement(By.tagName(\"body\")).getText().contains(\"The IP address of the user is \\\"blacklisted\\\"\"));", result2);
	}
	
	@Test
	public void discoveryAssertElementPresentCommand(){
		Command command = new Command("assertElementPresent","id=menu","");
		String result = translator.discovery(command);
		Assert.assertEquals("Assert.assertTrue(\"id=menu\",isElementPresent(By.id(\"menu\")));", result);
	}
	
	@Test
	public void discoveryAssertClickAndWaitCommand(){
		Command command = new Command("clickAndWait","xpath=//div[@id=\"ui-accordion-accordion-panel-7\"]/form[@id=\"updateClientForm\"]/input[@value='Update client configuration']/","");
		String result = translator.discovery(command);
		Assert.assertEquals("driver.findElement(By.xpath(\"//div[@id=\\\"ui-accordion-accordion-panel-7\\\"]/form[@id=\\\"updateClientForm\\\"]/input[@value='Update client configuration']/\")).click();", result);
	}
	
	@Test
	public void discoveryWaitForLocationWithPlainValueCommand(){
		Command command = new Command("waitForLocation","/*all*/","");
		String result = translator.discovery(command);
		Assert.assertEquals("(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {" +
		    "\n\t\t\tpublic Boolean apply(WebDriver d) {" +
		        "\n\t\t\t\treturn driver.getCurrentUrl().matches(\"^/[\\\\s\\\\S]*all[\\\\s\\\\S]*/$\");" +
		    "\n\t\t\t}" +
		"\n\t\t});", result);
	}
	
	@Test
	public void discoveryWaitForLocationWithRegexpCommand(){
		Command command = new Command("waitForLocation","regexp:.*all","");
		String result = translator.discovery(command);
		Assert.assertEquals("(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {" +
		    "\n\t\t\tpublic Boolean apply(WebDriver d) {" +
		        "\n\t\t\t\treturn Pattern.compile(\".*all\").matcher(driver.getCurrentUrl()).find();" +
			    "\n\t\t\t}" +
				"\n\t\t});", result);
	}
}
