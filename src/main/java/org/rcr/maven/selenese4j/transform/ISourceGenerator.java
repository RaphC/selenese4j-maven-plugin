/**
 * 
 */
package org.rcr.maven.selenese4j.transform;

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
	 * @param methodReader
	 * @throws Exception
	 */
	public void generate(File scenariiRootDirectory, IMethodReader methodReader) throws Exception;
}
