/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.Properties;

import com.github.raphc.maven.plugins.selenese4j.exception.ConfigurationException;

/**
 * @author Raphael
 * Interface implemented by the configuration validator
 */
public interface IConfigurationValidator {

	/**
     * Role used to register component implementations with the container.
     */
    String ROLE = IConfigurationValidator.class.getName();
    
    /**
     * Check the presence of {@linkplain GeneratorConfiguration#PROP_BASED_TESTS_SOURCES_PACKAGE} and its correct syntax.
     * If failed, the method trows a {@link ConfigurationException}
     * @param globalProperties
     * @throws ConfigurationException
     */
	public void validate(Properties globalProperties) throws ConfigurationException;
}
