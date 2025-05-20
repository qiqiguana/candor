/*
 * @(#)XmlTest.java
 * Created on 2005-8-12
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.allenstudio.ir.util.XmlElement;
import com.allenstudio.ir.util.XmlIO;

import junit.framework.TestCase;
public class XmlTest extends TestCase {

    /*
     * Test method for 'test.XmlIO.load(InputStream)'
     */
    public void testLoadInputStream() throws IOException {
        XmlElement expected = new XmlElement("Inspirento");

        XmlElement child1 = new XmlElement("window");
        child1.addAttribute("location", "510, 200");
        child1.addAttribute("size", "400, 300");
        child1.setData("Test");
        expected.addElement(child1);

        XmlIO xmlIO = new XmlIO(expected);
        FileOutputStream baos = new FileOutputStream("test.xml");
        xmlIO.write(baos);
        
        baos.close();

        xmlIO = new XmlIO();
        assertTrue("Could not parse the written XML",
            xmlIO.load(new FileInputStream("test.xml")));
        System.out.println(xmlIO.getRoot().getElement("Inspirento.window").getAttributes());
        XmlElement actual = xmlIO.getRoot().getElement("Inspirento");
        assertEquals("The original and the written XML element are not equal",
            expected, actual);
    }

}
