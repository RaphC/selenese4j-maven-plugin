/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.Locale;

/**
 * @author Raphael
 *
 */
public interface GeneratorConfiguration {
	
	 public final static String SELENIUM_TEST_TEMPLATE_NAME = "junit-selenium-test-class.vm";
	  
//	  public final static String ALL_SUITES_TEMPLATE_NAME = "AllSuites.vm";
//	  
//	  public final static String ALL_SEQUENTIAL_TEMPLATE_NAME = "AllSequentialTests.vm";
//	  
//	  public final static String ALL_TEST_TEMPLATE_NAME = "AllTests.vm";
	  
	  public final static String ORDERED_TESTS_SUITE_TEMPLATE_NAME = "junit-ordered-test-suite.vm";
	  
	  /**
	   * 
	   */
	  public final static String SUITE_FILE_NAME = "suite.html";
	  
	  public final static String DEFAULT_TEMPLATE_DIRECTORY_PATH = "templates/";
	  
	  public static final String VELOCITY_FILE_LOADER = "file";
	  
	  public static final String VELOCITY_JAR_LOADER = "jar";
	  
	  public static final String VELOCITY_CLASSPATH_LOADER = "class";
	  
	  public static final String DEFAULT_VELOCITY_LOADER = VELOCITY_CLASSPATH_LOADER;
	  
	  public static final String GENERATED_JAVA_TEST_CLASS_SUFFIX = "TestCase";
	  
	  /**
	   * prefix identifiant dans le fichier source (scenario html) les messages multi-langues
	   */
	  public static final String SOURCE_FILE_I18N_TOKENS_PREFIX = "messages";
	  
	  /**
	   * cle fournissant la langue dans laquelle seront traduites les cles de type ${messages.xxxxxxx}.
	   * Par defaut en_GB
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
	   * 
	   */
	  public static final String[] EXCLUDED_TEST_RESOURCES_DIR = new String[] { ".svn" };
	  
	  /**
	   * 
	   */
	  public static final String TEST_RESOURCES_DIR = System.getProperty("user.dir") + "/src/it/resources/selenium";
	  
	  /** parametre specifique a la generation **/
	  public static  final String PROP_BASED_TESTS_SOURCES_PACKAGE = "basedTestsSourcesPackage";
	  
	  public static  final String SCENARIO_FILE_SUFFIX = ".html";
}
