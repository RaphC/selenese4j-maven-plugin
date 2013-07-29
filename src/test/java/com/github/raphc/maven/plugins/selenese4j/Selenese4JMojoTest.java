/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import com.github.raphc.maven.plugins.selenese4j.Selenese4JMojo;

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
        File testPom = new File(getBasedir(), "src/test/resources/unit/conf/plugin-config-sln1.xml");
        Selenese4JMojo mojo = (Selenese4JMojo) lookupMojo( "transform", testPom);
        assertNotNull(mojo);
        mojo.execute();
    }
}
