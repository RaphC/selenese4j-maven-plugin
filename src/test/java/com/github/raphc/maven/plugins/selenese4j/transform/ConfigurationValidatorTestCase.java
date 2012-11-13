/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import com.github.raphc.maven.plugins.selenese4j.exception.ConfigurationException;

/**
 * @author Raphael
 *
 */
public class ConfigurationValidatorTestCase {

	IConfigurationValidator configurationValidator = new ConfigurationValidator();
	
	@Test
	public void validate() throws Exception {
		Properties	myGlobalProperties = new Properties();
		InputStream gfis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties-ext/my-global.properties");
    	myGlobalProperties.load(gfis);
    	
    	//OK
    	try {
    		configurationValidator.validate(myGlobalProperties);
    	} catch(ConfigurationException ce){
    		Assert.fail();
    	}
    	
    	//NOK
    	myGlobalProperties.put(GeneratorConfiguration.PROP_BASED_TESTS_SOURCES_PACKAGE, "some-domain.toto");
    	try {    	
    		configurationValidator.validate(myGlobalProperties);
    	} catch(ConfigurationException ce){
    		Assert.assertEquals("The "+GeneratorConfiguration.PROP_BASED_TESTS_SOURCES_PACKAGE+" token [some-domain.toto] is invalid", ce.getMessage());
    	}
	}
}
