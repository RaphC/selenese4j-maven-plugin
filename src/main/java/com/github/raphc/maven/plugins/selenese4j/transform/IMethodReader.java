/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;

/**
 * @author Raphael
 *
 */
public interface IMethodReader {
	
	/**
	 * Write the output to the Java source file
	 * @param dir. The Java source file directory
	 * @param classBean. The Java class information.
	 * @param tokens. The tokens available during the file velocity merge operation
	 * @throws Exception
	 */
	void writeSource(File dir, ClassInfo classBean, ScenarioTokens tokens) throws Exception;
	
	/**
	 * @return the template directory path
	 */
	public String getTemplatesDirectoryPath();
	
	/**
	 * 
	 * @return the build directory
	 */
	public String getTestBuildDirectory();
}
