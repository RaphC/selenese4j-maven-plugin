package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author Raphael
 *
 */
public class ScenarioHtmlParser {

	private static Logger logger = Logger.getLogger(ScenarioHtmlParser.class.getName());
	
	/**
	 * 
	 * @param suiteFile
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static Collection<File> parseSuite(File suiteFile) throws Exception {

		String line;

		BufferedReader br = new BufferedReader(new FileReader(suiteFile));
		Collection<File> testFiles = new ArrayList<File>();
		while ((line = br.readLine()) != null) {
			if (line.contains("<a href=")) {
				String[] parts = line.split("\"");
				File f = new File(suiteFile.getParentFile() + File.separator + parts[1]);
				if (!f.exists()) {
					throw new RuntimeException("Missing \"" + suiteFile.getParentFile() + File.separator + f + ".");
				}
				logger.log(Level.INFO, "Adding [" + f.getName() + "] to process from suite file ["+suiteFile+"]...");
				testFiles.add(f);
			}
		}
		return testFiles;
	}

}
