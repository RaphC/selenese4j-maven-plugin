/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.exception.ConfigurationException;

/**
 * @author Raphael
 * The configuration validator.
 * Check the presence of {@linkplain GeneratorConfiguration#PROP_BASED_TESTS_SOURCES_PACKAGE} and its corrent syntax.
 */
public class ConfigurationValidator implements IConfigurationValidator {

	private static final String PROP_BASED_TESTS_SOURCES_PACKAGE_VALUE_PATTERN = "[a-z0-9\\.]*";
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.transform.IConfigurationValidator#validate(java.util.Properties)
	 */
	public void validate(Properties globalProperties) throws ConfigurationException {
		
		String basedTestsSourcesPackageValue = (String) globalProperties.get(GeneratorConfiguration.PROP_BASED_TESTS_SOURCES_PACKAGE);
		if(StringUtils.isNotBlank(basedTestsSourcesPackageValue) 
				&& ! basedTestsSourcesPackageValue.matches(PROP_BASED_TESTS_SOURCES_PACKAGE_VALUE_PATTERN)){
			throw new ConfigurationException("The "+GeneratorConfiguration.PROP_BASED_TESTS_SOURCES_PACKAGE+" token ["+basedTestsSourcesPackageValue+"] is invalid");
		}

	}

}
