/**
 * 
 */
package org.rcr.maven.selenese4j;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author Raphael
 *
 */
public class Selenese4JMojoTest extends AbstractMojoTestCase {
    
	/**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @throws Exception
     */
    public void testMojoGoal() throws Exception {
        File testPom = new File(getBasedir(), "src/test/resources/unit/conf/plugin-config.xml");
        Selenese4JMojo mojo = (Selenese4JMojo) lookupMojo( "transform", testPom);
        assertNotNull(mojo);
        mojo.execute();
    }
}
