/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Raphael
 *
 */
public class DefaultMethodReader extends AbstractMethodReader {
    
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * 
	 * @param templatesDirectoryPath
	 * @param testBuildDirectory
	 */
	public DefaultMethodReader(String templatesDirectoryPath, String testBuildDirectory){
		this.templatesDirectoryPath = templatesDirectoryPath;
		this.testBuildDirectory = testBuildDirectory;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.roussev.selenium4j.transform.MethodReader#read(java.io.File, java.lang.String, org.roussev.selenium4j.transform.ClassBean, org.roussev.selenium4j.transform.VelocityBean, org.roussev.selenium4j.transform.VelocityBean.DriverBean)
	 */
	public void read(File dir, ClassInfo classBean, ScenarioTokens tokens) throws Exception {
	      File subDir = new File(dir.getName());
	      String dirName = subDir.getName();
	      
	      String[] javaSourcePackageDirs = classBean.getPackageName().split("\\.");
	      String javaSourceDirName = "";
	      for (String elt : javaSourcePackageDirs) {
	    	  javaSourceDirName = javaSourceDirName + File.separator + elt;
	      }
	      
	      File packageDir = new File(testBuildDirectory + File.separator + javaSourceDirName + File.separator + dirName);
	      packageDir.mkdirs();
	      
	      ScenarioConverter t = getScenarioConverter();
	      logger.log(Level.FINE, "Writing Java source  " + packageDir.getAbsolutePath() + File.separator + classBean.getClassName() + ".java ...");
	      t.doWrite(classBean, tokens, dirName, packageDir.getAbsolutePath() + File.separator + classBean.getClassName() + ".java");
    
	}
	  
}
