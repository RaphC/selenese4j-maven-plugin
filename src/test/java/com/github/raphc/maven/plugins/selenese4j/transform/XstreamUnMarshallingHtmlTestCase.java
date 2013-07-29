/**
 * 
 */
package com.github.raphc.maven.plugins.selenese4j.transform;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.github.raphc.maven.plugins.selenese4j.source.data.test.TestHtml;
import com.github.raphc.maven.plugins.selenese4j.xstream.converter.TdContentConverter;
import com.thoughtworks.xstream.XStream;

/**
 * @author Raphael
 *
 */
public class XstreamUnMarshallingHtmlTestCase {

	
	@Test
	public void unMarshallTheWholeDocument(){
		
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(TestHtml.class);
		xstream.registerConverter(new TdContentConverter());
		
		TestHtml html = (TestHtml) xstream.fromXML(new File(System.getProperty("user.dir")+ "/src/test/resources/plugin-conf/scenarii/myscenario/PlainScenario.html"));
		Assert.assertEquals(5, html.getBody().getTable().getTbody().getTrs().size());
		Assert.assertEquals("open", html.getBody().getTable().getTbody().getTrs().get(0).getTds().get(0).getContent());
	}
	
}
