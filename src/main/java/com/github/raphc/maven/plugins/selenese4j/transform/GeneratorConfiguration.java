/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.Locale;

/**
 * @author Raphael
 * Hold the constant values used by the plugin
 */
public interface GeneratorConfiguration {
	
	/**
	 * Default velocity template for selenium generation
	 */
	public final static String SELENIUM_TEST_TEMPLATE_NAME = "junit-selenium-test-class.vm";
	  
	/**
	 * Default velocity template for suite generation
	 */
	public final static String ORDERED_TESTS_SUITE_TEMPLATE_NAME = "junit-ordered-test-suite.vm";
	
	/**
	 * Default velocity template for webdriver generation
	 */
	public final static String WEBDRIVER_TEST_TEMPLATE_NAME = "junit-webdriver-test-class.vm";
	
	
	/**
	 * The default file searched if no includes parameter is set
	 */
	public final static String DEFAULT_SUITE_FILE_NAME = "suite.html";

	/**
	 * The default templates directory. This directory is located into the plugin classpath
	 */
	public final static String DEFAULT_TEMPLATE_DIRECTORY_PATH = "templates/";

	/**
	 * The Velocity loader file prefix
	 */
	public static final String VELOCITY_FILE_LOADER = "file";

	/**
	 * The Velocity loader classpath prefix
	 */
	public static final String VELOCITY_JAR_LOADER = "jar";

	/**
	 * The Velocity loader class prefix
	 */
	public static final String VELOCITY_CLASSPATH_LOADER = "class";

	/**
	 * The default Velocity loader. By default, the classpath loader is used
	 */
	public static final String DEFAULT_VELOCITY_LOADER = VELOCITY_CLASSPATH_LOADER;

	/**
	 * The generated Java Test Class Suffix
	 */
	public static final String GENERATED_JAVA_TEST_CLASS_SUFFIX = "TestCase";

	/**
	 * prefix identifiant dans le fichier source (scenario html) les messages
	 * multi-langues
	 */
	public static final String SOURCE_FILE_I18N_TOKENS_PREFIX = "messages";

	/**
	 * cle fournissant la langue dans laquelle seront traduites les cles de type
	 * ${messages.xxxxxxx}. Par defaut en_GB
	 */
	public static final String I18N_MESSAGES_LOCALE = "i18n.messages.locale";

	/**
	 * Langue par defaut
	 */
	public static final Locale DEFAULT_I18N_MESSAGES_LOCALE = Locale.ENGLISH;

	/**
	 * nom minimal des fichiers de messages internationalisables
	 */
	public static final String I18N_MESSAGES_FILE_BASENAME = "messages";

	/**
	 * Excluded directory which will not be scanned by the plugin
	 */
	public static final String[] EXCLUDED_TEST_RESOURCES_DIR = new String[] { ".svn" };

	/**
	 *  The property name used to specify the base package value
	 */
	public static final String PROP_BASED_TESTS_SOURCES_PACKAGE = "basedTestsSourcesPackage";

	/**
	 * The html suite file extension
	 */
	public static final String SCENARIO_FILE_SUFFIX = ".html";
	
	/**
	 * Encodage definit par defaut
	 */
	public static final String DEFAULT_ENCODING_TO_USE = "UTF-8";
	
	/**
	 * Encodage des templates
	 */
	public static final String VELOCITY_TEMPLATE_ENCODING = "UTF-8";
}
