package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.github.raphc.maven.plugins.selenese4j.context.ThreadLocalInfoContext;

/**
 * 
 * @author Raphael
 * The Java Test Suite Class writer
 */
class SuiteWriter {

	/**
	 * 
	 */
	private Logger logger = Logger.getLogger(getClass().getSimpleName());

	private String templateFile;
	
	private String velocityLoader;
	  
	private String velocityResourceLoaderPath = ".";
	
	private static Velocity engine = new Velocity();
	
	SuiteWriter(String velocityLoader, String velocityFileResourceLoaderPath, String templateFile) {
		this.velocityLoader = velocityLoader;
	    this.velocityResourceLoaderPath = velocityFileResourceLoaderPath;
	    this.templateFile = templateFile;
	}

	@SuppressWarnings("static-access")
	void doWrite(Collection<String> classesList, ScenarioTokens tokens, String packageName, String fileOut, boolean setupDirExist, boolean teardownDirExist) {
		logger.log(Level.INFO, "Flushing content to java file [" + fileOut + "] from template file [" + velocityResourceLoaderPath + "," + templateFile + "] ...");

		try {
			
			String templateResource = null;
			
			Properties props = new Properties();
			props.put("resource.loader", "file, class");
			props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			props.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			
			// Input encoding  for template
			props.put(Velocity.INPUT_ENCODING, GeneratorConfiguration.VELOCITY_TEMPLATE_ENCODING);
			// Output encoding
			props.put(Velocity.OUTPUT_ENCODING, ThreadLocalInfoContext.get().getOutputEncoding());
			
			if(GeneratorConfiguration.VELOCITY_FILE_LOADER.equalsIgnoreCase(velocityLoader)){
				props.put("file.resource.loader.path", velocityResourceLoaderPath);
				props.put("file.resource.loader.cache", "false");
				props.put("file.resource.loader.modificationCheckInterval", "0");
				templateResource = templateFile;
			} else {
				templateResource = velocityResourceLoaderPath.concat(templateFile);
			}
			  
			engine.init(props);
			
			VelocityContext context = new VelocityContext();
			context.put("testClasses", classesList);
			context.put("package", packageName);

			Template template = null;

			try {
				template = engine.getTemplate(templateResource);
			} catch (ResourceNotFoundException rnfe) {
				logger.log(Level.WARNING, getClass().getSimpleName()+" : error : cannot find template " + templateResource);
				return;
			} catch (ParseErrorException pee) {
				logger.log(Level.WARNING, getClass().getSimpleName()+" : Syntax error in template " + templateResource + ":" + pee);
				return;
			}

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)));

			if (template != null) {
				template.merge(context, writer);
			}

			writer.flush();
			writer.close();

			logger.log(Level.INFO, "File [" + fileOut + "] written.");

		} catch (Exception e) {
			logger.log(Level.WARNING, getClass().getSimpleName(), e);
		}
		logger.log(Level.INFO, "suite done.");
	}

}
