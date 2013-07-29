/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author Raphael
 *
 */
public class Selenese4JWebDriverMojoTest extends AbstractMojoTestCase {
    
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
        File testPom = new File(getBasedir(), "src/test/resources/unit/conf/plugin-config-sln2.xml");
        Selenese4JWebDriverMojo mojo = (Selenese4JWebDriverMojo) lookupMojo( "html2wd", testPom);
        assertNotNull(mojo);
        mojo.execute();
    }
}
