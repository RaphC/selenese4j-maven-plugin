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
	 * 
	 * @param scenariiRootDirectory
	 * @param suiteFilePattern
	 * @param methodReader
	 * @throws Exception
	 */
	public void generate(File scenariiRootDirectory, String[] suiteFilePattern, IMethodReader methodReader) throws Exception;
}
