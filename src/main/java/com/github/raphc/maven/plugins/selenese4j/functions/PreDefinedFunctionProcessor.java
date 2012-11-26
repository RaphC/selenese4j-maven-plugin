/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * @author Raphael
 *
 */
public class PreDefinedFunctionProcessor implements IPreDefinedFunctionProcessor {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private List<PreDefinedFunction> functions = new ArrayList<PreDefinedFunction>();
	
	/**
	 * Charge la liste des fonctions pre-definies exposees
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public PreDefinedFunctionProcessor() throws IllegalAccessException, InstantiationException {
		
		
		//From internal class loader
		Set<URL> urls = ClasspathHelper.forClassLoader(getClass().getClassLoader());
		//Java classpath
		urls.addAll(ClasspathHelper.forJavaClassPath());
		//Classpath system
		
		Reflections reflections = new Reflections(
		          new ConfigurationBuilder()
	              .setUrls(urls)
	              .setScanners(new SubTypesScanner()));
		
		Set<Class<? extends AbstractPreDefinedFunction>> predefinedFuctions = reflections.getSubTypesOf(AbstractPreDefinedFunction.class);
		
		logger.log(Level.INFO, " ["+predefinedFuctions.size()+"] extended AbstractPreDefinedFunction.class are found ...");	
		
		for(Class<? extends AbstractPreDefinedFunction> functionClazz : predefinedFuctions){
			logger.log(Level.INFO, "Adding ["+functionClazz.getCanonicalName()+"]to pre-defined function ...");	
			functions.add(functionClazz.newInstance());
		}
	}
	
	/**
	 * 
	 * @param instruction
	 * @return
	 * @throws NotMatchedException
	 */
	public final String process(String instruction) throws NotMatchedException {
		String initialInstruction = instruction;
		
		for(PreDefinedFunction predefinedFunction : functions){
			//Verification
			if(predefinedFunction.matches(instruction)){
				return ((AbstractPreDefinedFunction) predefinedFunction).process(instruction);
			}
		}
		// No function found
		return initialInstruction;
	}

}
