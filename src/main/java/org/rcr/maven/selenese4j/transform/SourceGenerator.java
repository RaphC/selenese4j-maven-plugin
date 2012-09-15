/**
 * 
 */
package org.rcr.maven.selenese4j.transform;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.rcr.maven.selenese4j.Selenese4JProperties;

/**
 * @author Raphael
 *
 */
public class SourceGenerator implements ISourceGenerator {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Locale i18nMessagesLocale = GeneratorConfiguration.DEFAULT_I18N_MESSAGES_LOCALE;
	
	
	
	/**
	 * @component
	 */
	private ICommandToMethodTranslator commandToMethodTranslator;
	
	public SourceGenerator(){}
	
	/*
	 * (non-Javadoc)
	 * @see org.rcr.maven.selenese4j.transform.ISourceGenerator#generate(org.rcr.maven.selenese4j.transform.IMethodReader)
	 */
	public void generate(IMethodReader methodReader) throws Exception {
		File dir = new File(GeneratorConfiguration.TEST_RESOURCES_DIR);
		
		FileFilter dirFilter = new FileFilter() {
		      public boolean accept(File file) {
		        return file.isDirectory() && ! ArrayUtils.contains(GeneratorConfiguration.EXCLUDED_TEST_RESOURCES_DIR, file.getName());
		      }
		    };
		
		File[] suiteDirs =    dir.listFiles(dirFilter);
		
		if(ArrayUtils.isEmpty(suiteDirs)){
			logger.log(Level.WARNING, "No suite directories found into ["+GeneratorConfiguration.TEST_RESOURCES_DIR+"] !!!!");
			return;
		}
		
		for (File suiteDir : suiteDirs) {
			logger.info("reading file ["+suiteDir+"]...");
			doTests(suiteDir, methodReader, null);
		}

	}
	
	/**
	 * 
	 * @param overrideTemplatesDirectoryPath
	 * @param buildDir
	 * @param dir
	 * @param methodReader
	 * @param tokens
	 * @throws Exception
	 */
	private void doTests(File dir, IMethodReader methodReader, ScenarioTokens tokens) throws Exception {
		logger.log(Level.FINE, "Reading " + dir + " tests...");
		ScenarioTokens scenarioTokens = new ScenarioTokens();
		if (tokens != null) {
			scenarioTokens.setSubstituteEntries(tokens.getSubstituteEntries());
			scenarioTokens.setSuiteContext(tokens.getSuiteContext());
		}

//		loadTestProperties(dir, scenarioTokens);

		File suiteFile = new File(dir, "suite.html");
		if (!suiteFile.exists()) {
			throw new RuntimeException("Missing \"suite.html\" file at " + dir + ".");
		}
		Collection<File> files = ScenarioHtmlParser.parseSuite(suiteFile);
		Collection<String> classBeans = new ArrayList<String>();

		//On definit le nom des packages
		File propFile = new File(dir, Selenese4JProperties.GLOBAL_CONF_FILE_NAME);
		Properties properties = new Properties();
		String basedPackageName = null;
		try {
			properties.load(new FileInputStream(propFile));
			basedPackageName = properties.getProperty(GeneratorConfiguration.PROP_BASED_TESTS_SOURCES_PACKAGE);
			logger.log(Level.FINE, "Property ["+GeneratorConfiguration.PROP_BASED_TESTS_SOURCES_PACKAGE+"] loaded ["+basedPackageName+"].");
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("Missing \"" + Selenese4JProperties.GLOBAL_CONF_FILE_NAME + "\" file at " + dir + ".");
		}
		
		String packName = null;
		packName = basedPackageName != null ? basedPackageName + "." + dir.getName() : dir.getName();
		loadSuiteContext(dir, scenarioTokens);

		for (File f : files) {
			
			String scenariiGenerationDisabledValue = properties.getProperty("scenarii.generation.disabled");
			if(StringUtils.contains(scenariiGenerationDisabledValue, f.getName())){
				logger.log(Level.FINE, "The conversion of the following scenario file has been disabled [" + f.getName() + "]. Conversion skipped.");
				continue;
			}
			
			logger.log(Level.FINE, "Processing [" + f.getName() + "]...");
			StringBuilder sb = new StringBuilder();
			String className = getClassNameSuffixed(getFileNameNoExtension(f));
			// Parsing du fichier. On extrait les commandes
			Collection<Command> cmds = ScenarioHtmlParser.parseHTML(f);
			//Traduction des commandes en instruction java
			for (Command c : cmds) {
				String cmdStr = commandToMethodTranslator.discovery(c);
				cmdStr = getPopulatedCmd(dir, className, cmdStr, scenarioTokens);
				sb.append("\n\t\t" + cmdStr);
			}
			
			logger.log(Level.FINE, "Generating ["+packName+"]["+className+"] ...");
			
			ClassInfo classInfo = new ClassInfo();
			classInfo.setPackageName(packName);
			classInfo.setClassName(className);
			classInfo.setMethodBody(sb.toString());
			writeTestFile(dir, methodReader, classInfo, scenarioTokens, classBeans);
			
		}

		// Generation de la classe annoté @Suite
		//base sur l'ordre de presentation dans le fichier suite.html
		createOrderedSuite(methodReader.getTemplatesDirectoryPath(), methodReader.getTestBuildDirectory(), classBeans, scenarioTokens, packName, dir.getName());
	}
	
	/**
	 * Supprime l'extension du nom du fichier
	 * @param f
	 * @return
	 */
	private static String getFileNameNoExtension(File f) {
		String fileName = f.getName();
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}
	
	/**
	 * Ajoute le suffixe de la classe
	 * @param f
	 * @return
	 */
	private static String getClassNameSuffixed(String className) {
		return className.concat(GeneratorConfiguration.GENERATED_JAVA_TEST_CLASS_SUFFIX);
	}
	
	/**
	 * Remplace les tokens ${xxxxx} par une possible clé de substitution 
	 * presente <i>substitute.yyyyyyy.xxxxxx</i> dans le fichier selenium4j où yyyyyyy correspond au nom du testcase YYYYYTestCase.
	 * Remplace les tokens ${messages.xxxxx} par un messages i18n correspond à la langue fournie.
	 * Par defaut, la recherche est realise en ${@link GeneratorConfiguration#DEFAULT_I18N_MESSAGES_LOCALE}.
	 * Si absent aucun modification n'est faite.
	 * @param basedir
	 * @param className
	 * @param cmdStr
	 * @param scenarioTokens
	 * @return
	 */
	private String getPopulatedCmd(File basedir, String className, String cmdStr, ScenarioTokens scenarioTokens) {
		String newCmdStr = cmdStr;
		
		Map<String, String> subEntries = scenarioTokens.getSubstituteEntries(className);
		
		//Application des clés de subsitution
		logger.log(Level.FINE, "Populating cmd ["+cmdStr+"] with substitue keys of ["+className+"] ("+CollectionUtils.size(subEntries)+" keys) ...");
		
		for (String key : subEntries.keySet()) {
			if (cmdStr.contains("${" + key + "}")) {
				String ek = subEntries.get(key);
				logger.log(Level.FINE, "Replacing string [${" + key + "}] in cmd ["+cmdStr+"] by [" + ek + "].");
				newCmdStr = cmdStr.replace("${" + key + "}", ek);
			}
		}
		
		//Application du bundling i18n
		try {
			URL[] urls = new URL[]{basedir.toURI().toURL()};
			URLClassLoader loader = new URLClassLoader(urls);
			ResourceBundle resource = ResourceBundle.getBundle(GeneratorConfiguration.I18N_MESSAGES_FILE_BASENAME, i18nMessagesLocale, loader);
			
			Pattern i18nTokensPattern = Pattern.compile("[.\\s]*(\\$\\{" + GeneratorConfiguration.SOURCE_FILE_I18N_TOKENS_PREFIX + "\\.([\\S&&[^$]]*)\\})+[.\\s]*");
			Matcher matcher = i18nTokensPattern.matcher(newCmdStr);
			while(matcher.find()){
				String i18nToken = matcher.group(2);
				logger.log(Level.FINE, "Found i18n token [" +i18nToken+ "] in cmd [" +newCmdStr+ "]");
				if(! resource.containsKey(i18nToken)){
					logger.log(Level.FINE, "The matching token [" +i18nToken+ "] has been not found in resource bundle [" +resource+"]. The conversion will be skipped.");
				} else {
					String value = resource.getString(i18nToken);
					logger.log(Level.FINE, "Replacing string [${" + GeneratorConfiguration.SOURCE_FILE_I18N_TOKENS_PREFIX.concat(".").concat(i18nToken) + "}] in cmd ["+newCmdStr+"] by [" + value + "].");
					newCmdStr = newCmdStr.replace("${" + GeneratorConfiguration.SOURCE_FILE_I18N_TOKENS_PREFIX.concat(".").concat(i18nToken) + "}", value);
				}
			}
		} catch(Exception e){
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		//Application des variables locales
		//Substitution basique : Dooit prendre en compte le contexte du positionnement
		Pattern localVariableTokensPattern = Pattern.compile("[.\\s]*(\\$\\{local\\.variable\\.([\\S]*)\\})+[.\\s]*");
		Matcher matcher = localVariableTokensPattern.matcher(newCmdStr);
		while(matcher.find()){
			String localVariableToken = matcher.group(2);
			logger.log(Level.FINE, "Replacing java class local variable token [" +localVariableToken+ "] in cmd [" +newCmdStr+ "]");
			newCmdStr = newCmdStr.replace("${local.variable." + localVariableToken + "}", "\" + " +localVariableToken + " + \"\"");
		}
		
		return newCmdStr;
	}
	
	/**
	 * 
	 * @param dir
	 * @param scenarioTokens
	 * @throws Exception
	 */
	private void loadSuiteContext(File dir, ScenarioTokens scenarioTokens) throws Exception {
		logger.log(Level.FINE, "Loading SuiteContext for '" + dir + "'");
		File propFile = new File(dir, Selenese4JProperties.GLOBAL_CONF_FILE_NAME);
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propFile));

			// load the context. entries
			for (Object key : properties.keySet()) {
				String key_ = (String) key;
				if (key_.startsWith("context.")) {
					String ctxKey = key_.replace("context.", "");
					scenarioTokens.addToContext(ctxKey, properties.getProperty(key_));
					logger.log(Level.FINE, "Context key [" + key_ + "] added as [" + ctxKey + "] [" + properties.getProperty(key_) + "] .");
				}
			}

			// load the html substitute. entries
			for (Object key : properties.keySet()) {
				String key_ = (String) key;
				if (key_.startsWith("substitute.")) {
					String ctxKey = key_.replace("substitute.", "");
					scenarioTokens.addSubstituteEntry(ctxKey, properties.getProperty(key_));
					logger.log(Level.FINE, "Substitute key [" + key_ + "] added as [" + ctxKey + "] [" + properties.getProperty(key_) + "] .");
				}
			}

			// load the dedicated template. velocity entries
			for (Object key : properties.keySet()) {
				String key_ = (String) key;
				if (key_.startsWith("template.")) {
					String ctxKey = key_.replace("template.", "");
					scenarioTokens.addTemplateEntry(ctxKey, properties.getProperty(key_));
					logger.log(Level.FINE, "Template key [" + key_ + "] added as [" + ctxKey + "] [" + properties.getProperty(key_)	+ "] .");
				}
			}

			// load global properties
			String definedLocale = properties.getProperty(GeneratorConfiguration.I18N_MESSAGES_LOCALE);
			if (StringUtils.isNotBlank(definedLocale)) {
				i18nMessagesLocale = LocaleUtils.toLocale(definedLocale);
			}

		} catch (FileNotFoundException e1) {
			throw new RuntimeException("Missing \"" + Selenese4JProperties.GLOBAL_CONF_FILE_NAME + "\" file at " + dir + ".");
		}
	}
	
	/**
	 * 
	 * @param dir
	 * @param methodReader
	 * @param classInfo
	 * @param tokens
	 * @param classBeans
	 * @throws Exception
	 */
	private void writeTestFile(File dir, IMethodReader methodReader, ClassInfo classInfo, ScenarioTokens tokens, Collection<String> classBeans) throws Exception {
		methodReader.read(dir, classInfo, tokens);
		String classBeanCanonicalName = classInfo.getPackageName() + "." + classInfo.getClassName();
		classBeans.add(classBeanCanonicalName);
		logger.log(Level.FINE, "ClassBean [" + classBeanCanonicalName + "] added.");
	}
	
	/**
	 * 
	 * @param classBeans
	 * @param tokens
	 * @param packageName
	 * @param dirName
	 */
	private void createOrderedSuite(String overrideTemplatesDirectoryPath, String buildDir, Collection<String> classBeans, ScenarioTokens tokens, String packageName, String dirName) {
		logger.log(Level.FINE, "Building Order Suite for " + dirName);
		VelocitySuiteTranslator t = getVelocitySuiteTranslator(overrideTemplatesDirectoryPath, "OrderedTestsSuite.vm");

		String[] packageDirs = packageName.split("\\.");
		String allDirName = "";
		for (String s : packageDirs) {
			allDirName = allDirName + File.separator + s;
		}

		File targetFile = new File(buildDir + allDirName);
		targetFile.mkdirs();

		String fileOut = buildDir + allDirName + File.separator + "OrderedTestSuite.java";
		t.doWrite(classBeans, tokens, packageName, fileOut, false, false);
	}

	/**
	 * Retourne une instance du traducteur Velocity
	 * 
	 * @return
	 */
	private VelocitySuiteTranslator getVelocitySuiteTranslator(String overrideTemplatesDirectoryPath, String templateFile) {
		VelocitySuiteTranslator out = null;

		String externalTemplateDir = StringUtils.substringAfter(overrideTemplatesDirectoryPath, GeneratorConfiguration.VELOCITY_FILE_LOADER + ":");
		if (StringUtils.isNotEmpty(externalTemplateDir)
				&& (new File(externalTemplateDir + File.separator
						+ templateFile)).exists()) {
			out = new VelocitySuiteTranslator(GeneratorConfiguration.VELOCITY_FILE_LOADER, externalTemplateDir, templateFile);
		} else {
			out = new VelocitySuiteTranslator(GeneratorConfiguration.DEFAULT_VELOCITY_LOADER, GeneratorConfiguration.DEFAULT_TEMPLATE_DIRECTORY_PATH, templateFile);
		}
		return out;
	}
}
