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

	protected String templateFileName = null;
	
	protected String templatesDirectoryPath = null;
	 
	protected String testBuildDirectory = null;
	
	/**
	 * Retourne une instance du traducteur Velocity
	 * @return
	 */
	public ScenarioWriter getScenarioConverter(){
		ScenarioWriter out = null;
		
		String externalTemplateDir = StringUtils.substringAfter(templatesDirectoryPath, GeneratorConfiguration.VELOCITY_FILE_LOADER+":");
		
		if(StringUtils.isNotEmpty(externalTemplateDir) 
				&& (new File(externalTemplateDir + File.separator + this.templateFileName)).exists()){
			out = new ScenarioWriter(GeneratorConfiguration.VELOCITY_FILE_LOADER, externalTemplateDir, this.templateFileName);
		} else {
			out = new ScenarioWriter(GeneratorConfiguration.DEFAULT_VELOCITY_LOADER, GeneratorConfiguration.DEFAULT_TEMPLATE_DIRECTORY_PATH, this.templateFileName);
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
