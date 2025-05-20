package nu.staldal.xodus;

import java.io.*;
import java.util.Properties;

import javax.xml.transform.OutputKeys;

import junit.framework.*;


public class TestOutputConfig extends TestCase
{
    
    public TestOutputConfig(String name)
    {
        super(name);
    }
	
    
    public void setUp() throws Exception
    {        
    }
    
    
    public void testWrongOutputConfig()
	{
        try {
            OutputConfig.createOutputConfig(new Properties());
            fail("No output method should be detected");
        }
        catch (IllegalArgumentException e)
        {
        }
        
        try {
            Properties p = new Properties();
            p.setProperty(OutputKeys.METHOD, "foo");
            OutputConfig.createOutputConfig(p);
            fail("Unknown output method should be detected");
        }
        catch (IllegalArgumentException e)
        {
        }
	}   

    public void testOutputConfig()
    {
        Properties p;
        OutputConfig oc;
        
        p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xml");
        oc = OutputConfig.createOutputConfig(p);
        assertEquals("xml", oc.method);
        assertTrue(!oc.isXhtml);
        assertTrue(!oc.isHtml);
        assertTrue(!oc.isText);
        assertEquals("1.0", oc.version);
        assertEquals("UTF-8", oc.encoding);
        // assertTrue(!oc.omit_xml_declaration);
        assertTrue(!oc.standalone);
        assertNull(oc.doctype_public);
        assertNull(oc.doctype_system);
        assertTrue(oc.cdata_section_elements.isEmpty());
        assertEquals("text/xml", oc.media_type);        

        p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xhtml");
        setProps(p);
        oc = OutputConfig.createOutputConfig(p);
        assertEquals("xhtml", oc.method);
        assertTrue(oc.isXhtml);
        assertTrue(!oc.isHtml);
        assertTrue(!oc.isText);
        assertEquals("1.1", oc.version);
        assertEquals("us-ascii", oc.encoding);
        // assertTrue(oc.omit_xml_declaration);
        assertTrue(oc.standalone);
        assertEquals("doctypePublic", oc.doctype_public);
        assertEquals("doctypeSystem", oc.doctype_system);
        assertTrue(!oc.cdata_section_elements.isEmpty());
        assertTrue(oc.cdata_section_elements.contains("foo"));
        assertTrue(oc.cdata_section_elements.contains("bar"));
        assertTrue(oc.cdata_section_elements.contains("baz"));
        assertTrue(!oc.cdata_section_elements.contains("xxx"));
        assertEquals("application/foo", oc.media_type);        
    }

    
    private void setProps(Properties p)
	{
        p.setProperty(OutputKeys.VERSION, "1.1");
        p.setProperty(OutputKeys.ENCODING, "us-ascii");
        p.setProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        p.setProperty(OutputKeys.STANDALONE, "yes");
        p.setProperty(OutputKeys.DOCTYPE_PUBLIC, "doctypePublic");
        p.setProperty(OutputKeys.DOCTYPE_SYSTEM, "doctypeSystem");
        p.setProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "foo bar baz");
        p.setProperty(OutputKeys.INDENT, "yes");
        p.setProperty(OutputKeys.MEDIA_TYPE, "application/foo");
    }
        
    
	public void tearDown()
	{
	}
}

