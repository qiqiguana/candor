/*
 * FreemarkerTest.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud.freemarker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerTest
{
	public static final String TEMPLATE_DIRECTORY = "template";
	public static final String TEST_TEMPLATE = "helloNeko.ftl";
	
	public static final String EXPECTED_RESULT = "<html><head>  <title>Welcome!</title></head><body>  <h1>Welcome Big Joe!</h1>  <p>Our latest product:  <a href=\"products/greenmouse.html\">green mouse</a>!</body></html>";
	
	private Configuration configuration;
	
	@Before
	public void setUp() throws Exception {
		configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_DIRECTORY));
		configuration.setObjectWrapper(new DefaultObjectWrapper());
	}

	@After
	public void tearDown() throws Exception {
		configuration = null;
	}

	@Test
	public void testAlwaysSucceed() {
		assertTrue(true);
	}
	
	@Test
	public void testFreemarker000() throws Exception {
		Template template = configuration.getTemplate(TEST_TEMPLATE);
		
        Map<Object,Object> root = new HashMap<Object,Object>();
        root.put("user", "Big Joe");
        Map<Object,Object> latest = new HashMap<Object,Object>();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baos);
        template.process(root, out);
        out.flush();
        
        String result = baos.toString();
        result = result.replaceAll("\r", "");
        result = result.replaceAll("\n", "");
        assertEquals(EXPECTED_RESULT, result);
	}
}
