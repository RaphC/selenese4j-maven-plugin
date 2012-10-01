/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

import com.github.raphc.maven.plugins.selenese4j.source.data.Td;
import com.github.raphc.maven.plugins.selenese4j.source.data.Tr;

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
	public static List<Command> convert(List<Tr> lines) {
		List<Command> result = new ArrayList<Command>();
		
		if(CollectionUtils.size(lines) == 0){return result;}
		
		for(Tr line : lines){
			Command command = new Command();
			List<Td> cells = line.getTds();
			logger.log(Level.FINE, "Converting cells ["+cells.get(0).getContent()+"]["+cells.get(1).getContent()+"]["+cells.get(2).getContent()+"] to command...");
			command.setName(cells.get(0).getContent());
			command.setTarget(cells.get(1).getContent());
			command.setValue(cells.get(2).getContent());
			result.add(command);
		}
		
		return result;
	}
}
