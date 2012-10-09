/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

import com.github.raphc.maven.plugins.selenese4j.source.data.test.TestTd;
import com.github.raphc.maven.plugins.selenese4j.source.data.test.TestTr;

/**
 * @author Raphael
 *
 */
public class HtmlConverter {

	private static Logger logger = Logger.getLogger(HtmlConverter.class.getName());
	
	/**
	 * 
	 * @param lines
	 * @return
	 */
	public static List<Command> convert(List<TestTr> lines) {
		List<Command> result = new ArrayList<Command>();
		
		if(CollectionUtils.size(lines) == 0){return result;}
		
		for(TestTr line : lines){
			Command command = new Command();
			List<TestTd> cells = line.getTds();
			logger.log(Level.FINE, "Converting cells ["+cells.get(0).getContent()+"]["+cells.get(1).getContent()+"]["+cells.get(2).getContent()+"] to command...");
			command.setName(cells.get(0).getContent());
			command.setTarget(cells.get(1).getContent());
			command.setValue(cells.get(2).getContent());
			result.add(command);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param lines
	 * @param suiteFile
	 * @return
	 */
	public static List<File> convert(List<TestTr> lines, File suiteFile) {
		List<File> result = new ArrayList<File>();
		
		if(CollectionUtils.size(lines) == 0){return result;}
		
		for(TestTr line : lines){
			List<TestTd> cells = line.getTds();
			logger.log(Level.FINE, "Converting cells ["+cells.get(0).getContent()+"] to file...");
			File file = null;
			String content = cells.get(0).getContent();
			if (content.contains("<a href=")) {
				String[] parts = content.split("\"");
				file = new File(suiteFile.getParentFile() + File.separator + parts[1]);
				if (!file.exists()) {
					throw new RuntimeException("Missing \"" + suiteFile.getParentFile() + File.separator + file + ".");
				}
				
			}
			
			logger.log(Level.INFO, "Adding [" + file.getName() + "] to process from suite file ["+suiteFile+"]...");
			result.add(file);
		}
		
		return result;
	}
}
