/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.github.raphc.maven.plugins.selenese4j.source.data.Td;
import com.github.raphc.maven.plugins.selenese4j.source.data.Tr;

/**
 * @author Raphael
 *
 */
public class HtmlConverterTestCase {

	
	@Test
	public void convert(){
		List<Tr> lines = new ArrayList<Tr>();
		
		Tr tr1 = new Tr();
		List<Td> cells1 = new ArrayList<Td>();
		Td cells11 = new Td();
		cells11.setContent("sandra");
		cells1.add(cells11);
		Td cells12 = new Td();
		cells12.setContent("nocouverture");
		cells1.add(cells12);
		Td cells13 = new Td();
		cells13.setContent("a froid");
		cells1.add(cells13);
		tr1.setTds(cells1);
		lines.add(tr1);
		
		Tr tr2 = new Tr();
		List<Td> cells2 = new ArrayList<Td>();
		Td cells21 = new Td();
		cells21.setContent("sandra");
		cells2.add(cells21);
		Td cells22 = new Td();
		cells22.setContent("aveccouverture");
		cells2.add(cells22);
		Td cells23 = new Td();
		cells23.setContent("a chaud");
		cells2.add(cells23);
		tr2.setTds(cells2);
		lines.add(tr2);
		
		List<Command> result =  HtmlConverter.convert(lines);
		
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("a chaud", result.get(1).getValue());
		Assert.assertEquals("sandra", result.get(0).getName());
		Assert.assertEquals("aveccouverture", result.get(1).getTarget());
	}
}
