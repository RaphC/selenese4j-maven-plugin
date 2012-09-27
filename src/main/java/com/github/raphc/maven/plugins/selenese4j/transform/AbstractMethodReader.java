/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;

import org.apache.commons.lang.StringUtils;

/**
 * @author Raphael
 *
 */
public abstract class AbstractMethodReader  implements IMethodReader {

	
	protected String templatesDirectoryPath = null;
	 
	protected String testBuildDirectory = null;
	
	/**
	 * Retourne une instance du traducteur Velocity
	 * @return
	 */
	public ScenarioConverter getScenarioConverter(){
		ScenarioConverter out = null;
		
		String externalTemplateDir = StringUtils.substringAfter(templatesDirectoryPath, GeneratorConfiguration.VELOCITY_FILE_LOADER+":");
		if(StringUtils.isNotEmpty(externalTemplateDir) 
				&& (new File(externalTemplateDir + File.separator + GeneratorConfiguration.SELENIUM_TEST_TEMPLATE_NAME)).exists()){
			out = new ScenarioConverter(GeneratorConfiguration.VELOCITY_FILE_LOADER, externalTemplateDir, GeneratorConfiguration.SELENIUM_TEST_TEMPLATE_NAME);
		} else {
			out = new ScenarioConverter(GeneratorConfiguration.DEFAULT_VELOCITY_LOADER, GeneratorConfiguration.DEFAULT_TEMPLATE_DIRECTORY_PATH, GeneratorConfiguration.SELENIUM_TEST_TEMPLATE_NAME);
		}
		return out;
	}

	/*
	 * (non-Javadoc)
	 * @see org.rcr.maven.selenese4j.transform.IMethodReader#getTemplatesDirectoryPath()
	 */
	public String getTemplatesDirectoryPath() {
		return templatesDirectoryPath;
	}

	/*
	 * (non-Javadoc)
	 * @see org.rcr.maven.selenese4j.transform.IMethodReader#getTestBuildDirectory()
	 */
	public String getTestBuildDirectory() {
		return testBuildDirectory;
	}
	
}
