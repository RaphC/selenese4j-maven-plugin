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
	 * @param subPackage
	 * @param classBean
	 * @param velocityBean
	 * @throws Exception
	 */
	void read(File dir, String subPackage, ClassBean classBean, VelocityBean velocityBean) throws Exception;
}
