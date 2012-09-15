/**
 * 
 */
package org.rcr.maven.selenese4j.transform;


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
	 * @param methodReader
	 * @throws Exception
	 */
	public void generate(IMethodReader methodReader) throws Exception;
}
