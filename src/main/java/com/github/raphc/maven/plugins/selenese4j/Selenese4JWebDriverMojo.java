/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.github.raphc.maven.plugins.selenese4j.transform.DefaultMethodReader;
import com.github.raphc.maven.plugins.selenese4j.transform.GeneratorConfiguration;
import com.github.raphc.maven.plugins.selenese4j.transform.ISourceGenerator;

/**
 * Goal which transform selenium html file scenarii into JUnit Test cases java sources.
 *
 * @goal html2wd
 * 
 * @phase process-sources
 * @author Raphael
 */
public class Selenese4JWebDriverMojo extends AbstractMojo {

	/**
     * Location of the configuration directory.
     * @parameter 
     * @required
     */
    private File selenese4jConfigurationDirectory;
    
    /**
     * Location of the scenarii directories.
     * @parameter default-value="target/generated-it-java"
     */
    private String testSourceGenerationDirectoryPath;
    
    /**
     * Location of the scenarii directories.
     * @parameter
     * @required
     */
    private File scenariiRootDirectory;
    
    /**
     * The generator to use
     * @component role-hint="webdriver-generator-component" 
     */
    private ISourceGenerator generator;
    
    /**
     * template to use instead of default Templates.
     * @parameter
     */
    private String overrideTemplatesDirectoryPath;
    
    /**
    * A set of suite file patterns to perform
    * @parameter alias="includes"
    */
    private String[] suitePatternIncludes;

    
	/*
	 * (non-Javadoc)
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		
    	getLog().info("loading configuration...");
    	if(!(selenese4jConfigurationDirectory.exists())){
    		throw new MojoExecutionException("Unable to locate configuration directory.");
    	}
    	
    	// Load  globale properties
    	Properties globalProperties = new Properties();
    	InputStream gfis = null;
    	try {
    		gfis = new FileInputStream(selenese4jConfigurationDirectory+File.separator+Selenese4JProperties.GLOBAL_CONF_FILE_NAME);
    		globalProperties.load(gfis);
		} catch (FileNotFoundException e1) {
			throw new MojoExecutionException("Missing \"" + Selenese4JProperties.GLOBAL_CONF_FILE_NAME + "\" file at " + selenese4jConfigurationDirectory + ".");
		} catch (IOException ioe) {
			throw new MojoExecutionException("Missing \"" + Selenese4JProperties.GLOBAL_CONF_FILE_NAME + "\" file at " + selenese4jConfigurationDirectory + ".");
		} finally {
			IOUtils.closeQuietly(gfis);
		}
    	
    	if(!(scenariiRootDirectory.exists())){
    		getLog().info(scenariiRootDirectory + "doesn't exists.");
    		return;
    	}
    	
    	getLog().info("generating tests sources...");
    	
    	//Nettoyage du repertoire
    	File testSourceGenerationDirectory = new File(testSourceGenerationDirectoryPath);
		if (testSourceGenerationDirectory.exists()) {
			testSourceGenerationDirectory.delete();
		}
		testSourceGenerationDirectory.mkdir();
		getLog().info("directory " + testSourceGenerationDirectory + " created.");

		// Generation
		//Check if an impl is defined and if it exists
		try {
			generator.generate(scenariiRootDirectory, suitePatternIncludes, 
					new DefaultMethodReader(
					GeneratorConfiguration.WEBDRIVER_TEST_TEMPLATE_NAME, this.overrideTemplatesDirectoryPath, this.testSourceGenerationDirectoryPath
					)
			);
		} catch (Exception e) {
			throw new MojoExecutionException("Exception", e);
		}
		
	}
	
	/**
     * 
     * @param includes
     */
    public void setIncludes(String[] includes) {
    	suitePatternIncludes = includes; 
    }

}
