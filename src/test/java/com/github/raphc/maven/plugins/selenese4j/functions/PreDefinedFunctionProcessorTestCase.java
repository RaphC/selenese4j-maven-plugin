/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.functions;

import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Raphael
 *
 */
public class PreDefinedFunctionProcessorTestCase {

	private PreDefinedFunctionProcessor processor = null;
	
	
	@Before
	public void before() throws Exception {
		if(processor == null){
			processor = new PreDefinedFunctionProcessor();
		}
	}
	
	@Test
	public void processOK() throws Exception {
		Assert.assertEquals(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"), processor.process("{@function:now()}"));
		Assert.assertEquals("Je suis un simple label : "+DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"), processor.process("Je suis un simple label : {@function:now()}"));
	}
	
	@Test
	public void processNotMatched() throws Exception {
		Assert.assertEquals("{@function:changer_structure_pied('carton')}", processor.process("{@function:changer_structure_pied('carton')}"));
	}
}
