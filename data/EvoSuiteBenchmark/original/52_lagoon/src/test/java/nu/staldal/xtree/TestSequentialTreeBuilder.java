package nu.staldal.xtree;

import java.io.*;

import org.xml.sax.*;

import junit.framework.*;

public class TestSequentialTreeBuilder extends TestCase
{
    public TestSequentialTreeBuilder(String name)
    {
        super(name);
    }

    public void testSequentialTreeBuilder() throws Exception
	{
		Element root = SequentialTreeBuilder.parseXMLSequential(
			new InputSource(getClass().getResourceAsStream("xtree.xml")), false,
			new ElementHandler() {
				public void processElement(Element el) throws SAXException
				{
					if (el.getLocalName().equals("Distributor"))
						assertEquals("bar", el.getNamespaceURI());
					else if (el.getLocalName().equals("News"))
						assertEquals("foo", el.getNamespaceURI());
					else
						fail("localName should be Distributor or News");
					
					assertEquals("Information", 
						((Element)el.getParent()).getLocalName());
					assertEquals("foo", 
						((Element)el.getParent()).getNamespaceURI()); 
				}
			});
		
		assertEquals("Information", root.getLocalName());
		assertEquals("foo", root.getNamespaceURI()); 
	}
}

