/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;


/**
 * @author Raphael
 *
 */
public interface ISourceGenerator {

	/**
     * Role used to register component implementations with the container.
     */
    String ROLE = ISourceGenerator.class.getName();
	
	/**
	 * Handle the end to end generation process.
	 * @param scenariiRootDirectory. The input file directory
	 * @param suiteFilePattern. The suite file pattern used to detect a file to process
	 * @param methodReader. The writer
	 * @throws Exception
	 */
	public void generate(File scenariiRootDirectory, String[] suiteFilePattern, IMethodReader methodReader) throws Exception;
}
