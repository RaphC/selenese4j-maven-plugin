package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Properties;

import org.apache.maven.plugin.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * 
 * @author Raphael
 *
 */
class VelocitySuiteTranslator {

	/**
	 * 
	 */
	private Log logger = new org.apache.maven.plugin.logging.SystemStreamLog();

	private String templateFile;
	
	private String velocityLoader;
	  
	private String velocityResourceLoaderPath = ".";

	VelocitySuiteTranslator(String velocityLoader, String velocityFileResourceLoaderPath, String templateFile) {
		this.velocityLoader = velocityLoader;
	    this.velocityResourceLoaderPath = velocityFileResourceLoaderPath;
	    this.templateFile = templateFile;
	}

	void doWrite(Collection<String> classesList, ScenarioTokens tokens, String packageName, String fileOut, boolean setupDirExist, boolean teardownDirExist) {
		logger.debug("Flushing content to java file [" + fileOut + "] from template file ["+velocityResourceLoaderPath+","+templateFile+"] ...");

		try {
			String templateResource = null;
			
			Properties props = new Properties();
			props.put("resource.loader", "file, class");
			props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			props.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			
			if(GeneratorConfiguration.VELOCITY_FILE_LOADER.equalsIgnoreCase(velocityLoader)){
				props.put("file.resource.loader.path", velocityResourceLoaderPath);
				props.put("file.resource.loader.cache", "false");
				props.put("file.resource.loader.modificationCheckInterval", "0");
				templateResource = templateFile;
			} else {
				templateResource = velocityResourceLoaderPath.concat(templateFile);
			}
			  
			Velocity.init(props);
			
			VelocityContext context = new VelocityContext();
			context.put("testClasses", classesList);
			context.put("package", packageName);

			Template template = null;

			try {
				template = Velocity.getTemplate(templateResource);
			} catch (ResourceNotFoundException rnfe) {
				logger.error("VelocitySuiteTranslator : error : cannot find template " + templateResource, rnfe);
				return;
			} catch (ParseErrorException pee) {
				logger.error("VelocitySuiteTranslator : Syntax error in template " + templateResource, pee);
				return;
			}

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));

			if (template != null) {
				template.merge(context, writer);
			}

			writer.flush();
			writer.close();

			logger.debug("File [" + fileOut + "] written.");

		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("AllTest suite transformation done.");
	}

}
