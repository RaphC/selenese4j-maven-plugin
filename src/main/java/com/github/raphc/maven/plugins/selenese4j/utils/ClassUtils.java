/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.utils;

import java.text.Normalizer;

import org.apache.commons.lang.StringUtils;

import com.github.raphc.maven.plugins.selenese4j.transform.GeneratorConfiguration;

/**
 * @author Raphael
 *
 */
public final class ClassUtils {
	
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String normalizeClassName(String fileName) {
		String className = StringUtils.removeEndIgnoreCase(fileName, GeneratorConfiguration.SCENARIO_FILE_SUFFIX).concat(GeneratorConfiguration.GENERATED_JAVA_TEST_CLASS_SUFFIX);
		String normalizedClassName = Normalizer.normalize(className, Normalizer.Form.NFD).replaceAll("\\W", "");
		return StringUtils.capitalize(normalizedClassName);
	}

}
