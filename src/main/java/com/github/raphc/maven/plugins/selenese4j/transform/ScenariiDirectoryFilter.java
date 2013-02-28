/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author Raphael
 *
 */
public class ScenariiDirectoryFilter implements FileFilter {

	public ScenariiDirectoryFilter(){}

	/*
	 * (non-Javadoc)
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File pathname) {
		return pathname.isDirectory() && ! ArrayUtils.contains(GeneratorConfiguration.EXCLUDED_TEST_RESOURCES_DIR, pathname.getName());
	}
}
