/*
 * $Id: EncodingDecodingTest.java,v 1.4 2005/12/29 01:54:21 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/test/org/sourceforge/ifx/EncodingDecodingTest.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx;

import org.sourceforge.ifx.utils.*;
import org.sourceforge.ifx.framework.element.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.HashMap;
import org.jdom.*;
import junit.framework.*;

/**
 * Builds a IFX bean from scratch and then passes it to the encoder. Generates
 * a file containing the IFX XML that is generated.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.4 $
 */
public class EncodingDecodingTest extends TestCase {
    
    private String xsdLocation;

    public static void main(String[] argv) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(EncodingDecodingTest.class);
    }

    public EncodingDecodingTest(String name) {
        super(name);
    }

    protected void setUp() {
        xsdLocation = System.getProperty("xsd.location");
    }

    public void testEncodeDecode() throws Exception {
        System.out.println("Running testEncodeDecode...");
        // build the bean
        System.out.println("...Building bean");
        IFX ifx = BeanBuilder.buildBankSvcRq();
        // encode to file
        System.out.println("...Testing encoding");
        Document doc = IFXDocumentHandler.build(ifx, null,
            "http://sourceforge.net/ifx-framework/ifx");
        IFXDocumentHandler.write(doc, 2, 
            IFXDocumentHandler.DEFAULT_LINE_SEPARATOR, 
            new FileOutputStream("testEncoding.xml"));
        // decode it
        System.out.println("...Testing decoding");
        Map props = new HashMap();
        props.put("http://sourceforge.net/ifx-framework/ifx", xsdLocation);
        Document doc2 = IFXDocumentHandler.read(
            new FileInputStream("testEncoding.xml"), true, props);
        IFX ifx2 = (IFX) IFXDocumentHandler.parse(doc);
        // check that we made it ok
        System.out.println("...Comparing original with decoded bean");
        assertNotNull("Decoded IFX Bean is null", ifx2);
        assertNotNull("Decoded IFX.SignonRq is null", ifx2.getSignonRq());
        assertNotNull("Decoded IFX.BankSvcRq is null", ifx2.getBankSvcRq());
        assertEquals(ifx, ifx2);
        // cleaning up 
        System.out.println("...Cleaning up");
        File tempFile = new File("testEncoding.xml");
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    public void testEncodeDecodeMulti() throws Exception {
        System.out.println("Running testEncodeDecodeMulti...");
        // build the bean
        System.out.println("...Building bean");
        IFX ifx = BeanBuilder.buildBankSvcRqMulti();
        // encode to file
        System.out.println("...Testing encoding");
        Document doc = IFXDocumentHandler.build(ifx, null,
            "http://sourceforge.net/ifx-framework/ifx");
        IFXDocumentHandler.write(doc, 2, 
            IFXDocumentHandler.DEFAULT_LINE_SEPARATOR,
            new FileOutputStream("testEncodingMulti.xml"));
        // decode it
        System.out.println("...Testing decoding");
        Map props = new HashMap();
        props.put("http://sourceforge.net/ifx-framework/ifx", xsdLocation);
        Document doc2 = IFXDocumentHandler.read(
            new FileInputStream("testEncodingMulti.xml"), true, props);
        IFX ifx2 = (IFX) IFXDocumentHandler.parse(doc);
        // check that we made it ok
        System.out.println("...Comparing original with decoded bean");
        assertNotNull("Decoded IFX bean is null", ifx2);
        assertNotNull("Decoded IFX.SignonRq is null", ifx2.getSignonRq());
        assertNotNull("Decoded IFX.BankSvcRq is null", ifx2.getBankSvcRq());
        assertEquals("Size of IFX.BankSvcRq is not 2", 2, 
            ifx2.getBankSvcRq().length);
        // cleaning up
        System.out.println("...Cleaning up");
        File tempfile = new File("testEncodingMulti.xml");
        if (tempfile.exists()) {
            tempfile.delete();
        }
    }
}
