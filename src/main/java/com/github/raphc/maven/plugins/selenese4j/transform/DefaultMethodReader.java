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
    
	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * The default constructor
	 * @param templateFileName
	 * @param templatesDirectoryPath
	 * @param testBuildDirectory
	 */
	public DefaultMethodReader(String templateFileName, String templatesDirectoryPath, String testBuildDirectory){
		this.templateFileName = templateFileName;
		this.templatesDirectoryPath = templatesDirectoryPath;
		this.testBuildDirectory = testBuildDirectory;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.raphc.maven.plugins.selenese4j.transform.IMethodReader#writeSource(java.io.File, com.github.raphc.maven.plugins.selenese4j.transform.ClassInfo, com.github.raphc.maven.plugins.selenese4j.transform.ScenarioTokens)
	 */
	public void writeSource(File dir, ClassInfo classBean, ScenarioTokens tokens) throws Exception {
	      
	      String[] javaSourcePackageDirs = classBean.getPackageName().split("\\.");
	      String javaSourceDirName = "";
	      for (String elt : javaSourcePackageDirs) {
	    	  javaSourceDirName = javaSourceDirName + File.separator + elt;
	      }
	      
	      File packageDir = new File(testBuildDirectory + File.separator + javaSourceDirName + File.separator);
	      packageDir.mkdirs();
	      
	      ScenarioWriter writer = getScenarioConverter();
	      String javaSourceFile = packageDir.getAbsolutePath() + File.separator + classBean.getClassName() + ".java";
	      logger.log(Level.FINE, "Writing Java source  " + javaSourceFile + " ...");
	      writer.doWrite(classBean, tokens, javaSourceFile);
    
	}
	  
}
