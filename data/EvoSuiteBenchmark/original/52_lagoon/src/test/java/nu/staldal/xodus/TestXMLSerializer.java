package nu.staldal.xodus;

import java.io.*;
import java.util.Properties;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

import junit.framework.*;


public class TestXMLSerializer extends TestCase
{
    private final String XHTML_NS = "http://www.w3.org/1999/xhtml";
    private final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    
    public TestXMLSerializer(String name)
    {
        super(name);
    }
	
    
    public void setUp() throws Exception
    {        
    }
    
        
    public void testSerializerXML1() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xml");                
        p.setProperty(OutputKeys.DOCTYPE_SYSTEM, "correctSystemId");

        OutputStream testOut = new FileOutputStream("test1.xml");
        
        Serializer th = Serializer.createSerializer(
            new StreamResult(testOut), p);
            
        th.startDocument();
        
        th.startDTD("html", "publicId", "bogusSystemId");
        th.notationDecl("myNotation1", "publicId", "systemId"); 
        th.notationDecl("myNotation2", null, "systemId"); 
        th.notationDecl("myNotation3", "publicId", null); 
        th.unparsedEntityDecl("unparsedEntity1", null, "sysid", "myNotation1");
        th.unparsedEntityDecl("unparsedEntity2", "publicId", "systemId", "myNotation2");
        th.elementDecl("html", "ANY");
        th.elementDecl("p", "(#PCDATA)");
        th.elementDecl("hr", "EMPTY");
        th.attributeDecl("p", "attr1", "CDATA", "#FIXED", "foobar");         
        th.attributeDecl("p", "style", "CDATA", "#IMPLIED", null);         
        th.attributeDecl("p", "class", "CDATA", null, "default");         
        th.externalEntityDecl("extEntity1", "publicId", "systemId");
        th.externalEntityDecl("extEntity2", null, "systemId");
        th.internalEntityDecl("intEntity", "FOOBAR");
        th.endDTD();
        
        th.startElement("", "html", "", new AttributesImpl());
        th.comment("this is a comment".toCharArray(),0,17);
        AttributesImpl attrs = new AttributesImpl();           
        attrs.addAttribute("", "class", "", "CDATA", "normal");
        attrs.addAttribute("", "style", "", "CDATA", "font-size: \"10pt\";");
        th.startElement("", "p", "", attrs);
        th.characters("xml \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,16);
        th.endElement("", "p", "");
        th.startElement("", "hr", "", new AttributesImpl());
        th.endElement("", "hr", "");        
        th.startElement("", "p", "", new AttributesImpl());
        th.processingInstruction("foo", "FOO BAR");
        th.endElement("", "p", "");
        th.processingInstruction("EMPTY_PI", "");
        th.processingInstruction(Result.PI_DISABLE_OUTPUT_ESCAPING, null);
        th.characters("xml \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,16);        
        th.processingInstruction(Result.PI_ENABLE_OUTPUT_ESCAPING, null);
        th.characters("xml \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,16);        
        th.skippedEntity("intEntity");        
        th.skippedEntity("[dtd]");        
        th.skippedEntity("%foo");
        th.startCDATA();
        th.characters("xml \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,16);
        th.endCDATA();       
        th.endElement("", "html", "");
        th.endDocument();                

        testOut.close();
    }

    public void testSerializerXML2() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xml");
        p.setProperty(OutputKeys.DOCTYPE_SYSTEM, "systemId");
        p.setProperty(OutputKeys.DOCTYPE_PUBLIC, "publicId");
        OutputStream testOut = new FileOutputStream("test2.xml");
        
        Serializer th = Serializer.createSerializer(
            new StreamResult(testOut), p);
            
        th.startDocument();
                
        th.startElement("", "html", "", new AttributesImpl());
        th.characters("xml \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,16);
        th.endElement("", "html", "");
        
        th.endDocument();                

        testOut.close();
    }
    
    
    public void testSerializerXMLIndent() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xml");
        p.setProperty(OutputKeys.INDENT, "yes");
        OutputStream testOut = new FileOutputStream("indent.xml");
        
        Serializer th = Serializer.createSerializer(
            new StreamResult(testOut), p);
            
        th.startDocument();
                
        th.startElement("", "root", "", new AttributesImpl());
        th.startElement("http://ns1", "pre", "", new AttributesImpl());
        AttributesImpl attrs = new AttributesImpl();           
        attrs.addAttribute(XML_NS, "att1", "", "CDATA", "normal");
        attrs.addAttribute("http://att/ns", "att2", "", "CDATA", "font-size: \"10pt\";");
        th.startElement("", "bar", "", attrs);
        th.characters("indent text".toCharArray(),0,11);
        th.endElement("", "bar", "");
        th.startElement("", "baz", "", new AttributesImpl());
        th.characters("more text".toCharArray(),0,9);
        th.endElement("", "baz", "");
        th.processingInstruction("foo", "FOO BAR");
        th.startElement("http://ns2", "foobar", "", new AttributesImpl());
        th.endElement("http://ns2", "foobar", "");
        th.endElement("http://ns1", "pre", "");
        th.endElement("", "root", "");
        
        th.endDocument();                

        testOut.close();
    }


    public void testSerializerXMLError() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xml");
        p.setProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "foo bar");
        
        try {
            Serializer th = Serializer.createSerializer(
                new StreamResult(), p);
                
            fail("Should throw java.lang.IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {            
        }                
    }


    public void testSerializerText() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "text");
        OutputStream testOut = new FileOutputStream("test.txt");
        
        Serializer th = Serializer.createSerializer(
            new StreamResult(testOut), p);
            
        th.startDocument();
                
        th.startElement("", "html", "html", new AttributesImpl());
        th.characters("text \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,17);
        th.processingInstruction("foo", "FOO BAR");
        th.characters("Some more text".toCharArray(),0,14);
        th.endElement("", "html", "html");
        
        th.endDocument();                

        testOut.close();
    }

    
    public void testSerializerTextWrongEncoding() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "text");
        p.setProperty(OutputKeys.ENCODING, "us-ascii");
        OutputStream testOut = new FileOutputStream("test2.txt");

        try {        
            Serializer th = Serializer.createSerializer(
                new StreamResult(testOut), p);
                
            th.startDocument();
                    
            th.startElement("", "html", "html", new AttributesImpl());
            th.characters("text \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,17);
            th.processingInstruction("foo", "FOO BAR");
            th.characters("Some more text".toCharArray(),0,14);
            th.endElement("", "html", "html");
            
            th.endDocument();
            
            fail("Should throw Exception");
        }
        catch (Exception e)
        {
        }        

        testOut.close();
    }

    public void testSerializerXHTML() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "xhtml");
        p.setProperty(OutputKeys.INDENT, "yes");
        OutputStream testOut = new FileOutputStream("xhtml.html");
        
        Serializer th = Serializer.createSerializer(
            new StreamResult(testOut), p);
            
        th.startDocument();
                
        th.startElement(XHTML_NS, "html", "", new AttributesImpl());
        th.comment("this is a comment".toCharArray(),0,17);
        AttributesImpl attrs = new AttributesImpl();           
        attrs.addAttribute("", "class", "class", "CDATA", "normal");
        attrs.addAttribute("", "style", "style", "CDATA", "font-size: 10pt;");
        th.startElement(XHTML_NS, "p", "", attrs);
        th.endElement(XHTML_NS, "p", "");
        th.startElement(XHTML_NS, "div", "", new AttributesImpl());
        th.startElement(XHTML_NS, "hr", "", new AttributesImpl());
        th.endElement(XHTML_NS, "hr", "");        
        attrs = new AttributesImpl();           
        attrs.addAttribute("", "src", "", "CDATA", "picture.png");
        th.startElement(XHTML_NS, "img", "", attrs);
        th.endElement(XHTML_NS, "img", "");
        th.endElement(XHTML_NS, "div", "");        
        th.startElement(XHTML_NS, "pre", "", new AttributesImpl());
        th.startElement(XHTML_NS, "hr", "", new AttributesImpl());
        th.endElement(XHTML_NS, "hr", "");        
        attrs = new AttributesImpl();           
        attrs.addAttribute("", "src", "", "CDATA", "picture.png");
        th.startElement("http://foo", "img", "", attrs);
        th.endElement("http://foo", "img", "");
        th.endElement(XHTML_NS, "pre", "");        
        th.startElement(XHTML_NS, "p", "", new AttributesImpl());
        th.characters("xhtml \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,18);
        th.startElement(XHTML_NS, "b", "", new AttributesImpl());
        th.characters("boldface".toCharArray(),0,8);
        th.endElement(XHTML_NS, "b", "");
        th.characters("some text".toCharArray(),0,9);
        th.endElement(XHTML_NS, "p", "");
        th.endElement(XHTML_NS, "html", "");
        
        th.endDocument();                

        testOut.close();
    }

    public void testSerializerHTML() throws Exception
    {
        Properties p = new Properties();
        p.setProperty(OutputKeys.METHOD, "html");
        p.setProperty(OutputKeys.INDENT, "yes");
        OutputStream testOut = new FileOutputStream("html.html");
        
        Serializer th = Serializer.createSerializer(
            new StreamResult(testOut), p);
            
        th.startDocument();
                
        th.startElement(XHTML_NS, "html", "", new AttributesImpl());
        th.comment("this is a comment".toCharArray(),0,17);
        AttributesImpl attrs = new AttributesImpl();           
        attrs.addAttribute("", "class", "class", "CDATA", "normal");
        attrs.addAttribute("", "style", "style", "CDATA", "font-size: 10pt;");
        th.startElement(XHTML_NS, "p", "", attrs);
        th.endElement(XHTML_NS, "p", "");
        th.startElement(XHTML_NS, "div", "", new AttributesImpl());
        th.startElement(XHTML_NS, "HR", "", new AttributesImpl());
        th.endElement(XHTML_NS, "HR", "");        
        attrs = new AttributesImpl();           
        attrs.addAttribute("", "src", "", "CDATA", "&{picture.png}");
        attrs.addAttribute("", "lowsrc", "", "CDATA", "&picture.png");
        attrs.addAttribute("", "ismap", "", "CDATA", "ismap");
        attrs.addAttribute("", "disabled", "", "CDATA", "<hey>");
        th.startElement(XHTML_NS, "img", "", attrs);
        th.endElement(XHTML_NS, "img", "");
        th.endElement(XHTML_NS, "div", "");        
        th.startElement(XHTML_NS, "pre", "", new AttributesImpl());
        th.startElement(XHTML_NS, "hr", "", new AttributesImpl());
        th.endElement(XHTML_NS, "hr", "");        
        attrs = new AttributesImpl();           
        attrs.addAttribute("", "src", "", "CDATA", "picture.png");
        th.startElement("http://foo", "img", "", attrs);
        th.endElement("http://foo", "img", "");
        th.endElement(XHTML_NS, "pre", "");        
        th.startElement(XHTML_NS, "p", "", new AttributesImpl());
        th.characters("html \"≈ƒ÷Â‰ˆ\" <&>".toCharArray(),0,17);
        th.startElement(XHTML_NS, "b", "", new AttributesImpl());
        th.characters("boldface".toCharArray(),0,8);
        th.endElement(XHTML_NS, "b", "");
        th.characters("some text".toCharArray(),0,9);
        th.endElement(XHTML_NS, "p", "");
        th.startElement(XHTML_NS, "script", "", new AttributesImpl());
        th.characters("if (a > b && b < c) d = e;".toCharArray(),0,26);
        th.endElement(XHTML_NS, "script", "");                
        th.endElement(XHTML_NS, "html", "");
        th.processingInstruction("foo", "FOO BAR");
        
        th.endDocument();                

        testOut.close();
    }
    

    /*
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
    */  
    
	public void tearDown() throws Exception
	{
	}
}

