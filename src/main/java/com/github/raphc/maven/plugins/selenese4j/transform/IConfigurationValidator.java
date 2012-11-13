/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.Properties;

import com.github.raphc.maven.plugins.selenese4j.exception.ConfigurationException;

/**
 * @author Raphael
 *
 */
public interface IConfigurationValidator {

	/**
     * Role used to register component implementations with the container.
     */
    String ROLE = IConfigurationValidator.class.getName();
    
    /**
     * 
     * @param globalProperties
     * @throws ConfigurationException
     */
	public void validate(Properties globalProperties) throws ConfigurationException;
}
