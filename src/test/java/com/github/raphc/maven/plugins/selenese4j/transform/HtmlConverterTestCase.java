/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.raphc.maven.plugins.selenese4j.context.InfoContext;
import com.github.raphc.maven.plugins.selenese4j.context.ThreadLocalInfoContext;
import com.github.raphc.maven.plugins.selenese4j.source.data.test.TestTd;
import com.github.raphc.maven.plugins.selenese4j.source.data.test.TestTr;

/**
 * @author Raphael
 *
 */
public class HtmlConverterTestCase {

	@Before
	public void before(){
		InfoContext infoContext = new InfoContext(GeneratorConfiguration.DEFAULT_ENCODING_TO_USE);
		ThreadLocalInfoContext.set(infoContext);
	}
	
	@Test
	public void convert() throws UnsupportedEncodingException{
		List<TestTr> lines = new ArrayList<TestTr>();
		
		TestTr tr1 = new TestTr();
		List<TestTd> cells1 = new ArrayList<TestTd>();
		TestTd cells11 = new TestTd();
		cells11.setContent("sandra");
		cells1.add(cells11);
		TestTd cells12 = new TestTd();
		cells12.setContent("nocouverture");
		cells1.add(cells12);
		TestTd cells13 = new TestTd();
		cells13.setContent("a froid");
		cells1.add(cells13);
		tr1.setTds(cells1);
		lines.add(tr1);
		
		TestTr tr2 = new TestTr();
		List<TestTd> cells2 = new ArrayList<TestTd>();
		TestTd cells21 = new TestTd();
		cells21.setContent("sandra");
		cells2.add(cells21);
		TestTd cells22 = new TestTd();
		cells22.setContent("aveccouverture");
		cells2.add(cells22);
		TestTd cells23 = new TestTd();
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
	
	@After
	public void after(){
		ThreadLocalInfoContext.unset();
	}
}
