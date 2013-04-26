package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
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
class ScenarioConverter {

	private Logger logger = Logger.getLogger(getClass().getSimpleName());
	
	private String templateFile;

	private String velocityLoader;

	private String velocityResourceLoaderPath = ".";

	/**
	 * 
	 * @param velocityLoader
	 * @param velocityFileResourceLoaderPath
	 * @param templateFile
	 */
	public ScenarioConverter(String velocityLoader, String velocityFileResourceLoaderPath, String templateFile) {
		this.velocityLoader = velocityLoader;
		this.velocityResourceLoaderPath = velocityFileResourceLoaderPath;
		this.templateFile = templateFile;
	}

	/**
	 * 
	 * @param classBean
	 * @param velocityBean
	 * @param fileOut
	 */
	public void doWrite(ClassInfo classBean, ScenarioTokens velocityBean, String fileOut) {
		logger.log(Level.INFO, "Flushing content to java file [" + fileOut + "] from template file [" + velocityResourceLoaderPath + "," + templateFile + "] ...");
		try {

			String templateResource = null;

			Properties props = new Properties();
			props.put(Velocity.RESOURCE_LOADER, "file, class");
			props.put("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			props.put("file.resource.loader.class",	"org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			if (GeneratorConfiguration.VELOCITY_FILE_LOADER.equalsIgnoreCase(velocityLoader)) {
				props.put(Velocity.FILE_RESOURCE_LOADER_PATH,velocityResourceLoaderPath);
				props.put(Velocity.FILE_RESOURCE_LOADER_CACHE, "false");
				props.put("file.resource.loader.modificationCheckInterval", "0");
				templateResource = templateFile;
			} else {
				templateResource = velocityResourceLoaderPath.concat(templateFile);
			}

			Velocity.init(props);

			VelocityContext context = new VelocityContext();
			context.put("packageName", classBean.getPackageName());
			context.put("className", classBean.getClassName());
			context.put("methodBody", classBean.getMethodBody());
			Map<String, String> templateEntries = velocityBean.getTemplateEntries(classBean.getClassName());
			logger.log(Level.INFO,
					"Populating source [" + classBean.getClassName() + "] with template keys (" + CollectionUtils.size(templateEntries) + " keys) ...");
			for (String key : templateEntries.keySet()) {
				context.put(key, templateEntries.get(key));
				logger.log(Level.FINE, "Adding [" + key + "] to velocity context with value [" + templateEntries.get(key) + "]...");
			}

			Template template = null;

			try {
				template = Velocity.getTemplate(templateResource);
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
		logger.log(Level.INFO, "tests done.");
	}

}
