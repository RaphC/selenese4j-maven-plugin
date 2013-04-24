/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.translator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.github.raphc.maven.plugins.selenese4j.transform.Command;
import com.github.raphc.maven.plugins.selenese4j.translator.element.Element;
import com.github.raphc.maven.plugins.selenese4j.translator.element.WebDriverElement;

/**
 * @author Raphael
 *
 */
public class SeleniumWebDriverAdaptor {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	Map<String, Element> elements = new HashMap<String, Element>();
	
	/**
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void registerElements() throws IllegalAccessException, InstantiationException {
		//From internal class loader
		Set<URL> urls = ClasspathHelper.forClassLoader(getClass().getClassLoader());
		//Java classpath
		urls.addAll(ClasspathHelper.forJavaClassPath());
		//Classpath system
				
		Reflections reflections = new Reflections(
		          new ConfigurationBuilder()
		             .setUrls(urls)
		             .setScanners(new TypeAnnotationsScanner()));
		
		Set<Class<?>> wdElements = reflections.getTypesAnnotatedWith(WebDriverElement.class);
		
		logger.log(Level.INFO, " ["+wdElements.size()+"] WebDriverElement are found ...");	
		
		for(Class<?> functionClazz : wdElements){
			Element element = (Element) functionClazz.newInstance();
			logger.log(Level.INFO, "Adding ["+functionClazz.getCanonicalName()+"]to web driver element registered ...");	
			elements.put(element.getCommandName(), element);
		}
	}
	
	/**
	 * 
	 * @param command
	 * @return
	 */
	public Element find(Command command) {
		return elements.get(command.getName());
	}
}