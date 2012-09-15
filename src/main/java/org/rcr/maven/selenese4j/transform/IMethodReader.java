/**
 * 
 */
package org.rcr.maven.selenese4j.transform;

import java.io.File;

/**
 * @author Raphael
 *
 */
public interface IMethodReader {
	
	/**
	 * 
	 * @param dir
	 * @param classBean
	 * @param tokens
	 * @throws Exception
	 */
	void read(File dir, ClassInfo classBean, ScenarioTokens tokens) throws Exception;
	
	/**
	 * 
	 * @return
	 */
	public String getTemplatesDirectoryPath();
	
	/**
	 * 
	 * @return
	 */
	public String getTestBuildDirectory();
}
