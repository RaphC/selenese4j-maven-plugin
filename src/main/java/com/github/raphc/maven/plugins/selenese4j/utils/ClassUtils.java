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
	 * Transform the {@linkplain fileName} string to a valid class name
	 * @param fileName
	 * @return
	 */
	public static String normalizeClassName(String fileName) {
		String className = StringUtils.removeEndIgnoreCase(fileName, GeneratorConfiguration.SCENARIO_FILE_SUFFIX).concat(GeneratorConfiguration.GENERATED_JAVA_TEST_CLASS_SUFFIX);
		String normalizedClassName = Normalizer.normalize(className, Normalizer.Form.NFD).replaceAll("\\W", "");
		return StringUtils.capitalize(normalizedClassName);
	}
	
	/**
	 * Build a valid package name from the {@linkplain basedPackageName} and additional {@linkplain childs}
	 * @param basedPackageName
	 * @param childs
	 * @return
	 */
	public static String buildPackageName(String basedPackageName, String...childs) {
		if(basedPackageName != null){
			return basedPackageName + "." + childs[0] + "." + normalizePackageName(childs[1]);
		} else {
			return childs[0] + "." + normalizePackageName(childs[1]);
		}
	}
	
	/**
	 * Transform the {@linkplain resourceName} string to a valid package name
	 * @param resourceName
	 * @return
	 */
	public static String normalizePackageName(String resourceName) {
		String resourceNameWithNoSpecChars = Normalizer.normalize(resourceName, Normalizer.Form.NFKD).replaceAll("[^a-zA-Z0-9]", "");
		return StringUtils.lowerCase(resourceNameWithNoSpecChars);
	}

}
