/*
 * @(#)XMLPropertiesTest.java
 * Created on 2005-8-10
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

import java.io.*;
import java.util.*;

public class XMLPropertiesTest {

    public static void main(String[] args) throws Exception {
        FileInputStream inputFile = new FileInputStream(
                new File("config\\config.xml"));
        Properties properties = new Properties();
        properties.loadFromXML(inputFile);
        System.out.println(properties.getProperty("windowLocation"));
        inputFile.close();
    }
}
