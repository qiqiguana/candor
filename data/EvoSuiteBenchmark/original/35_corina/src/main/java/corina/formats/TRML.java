//
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.formats;

import corina.Year;
import corina.Range;
import corina.Sample;
import corina.Element;
import corina.MetadataTemplate;
import corina.Weiserjahre;
import corina.util.StringUtils;
import corina.ui.I18n;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

/**
   TRML, my experimental Tree-Ring Markup Language.

   <p>It's long, it's ugly, it's probably fairly buggy, it has really
   lousy error handling, but it seems to work for the one file I've
   tested it on.</p>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i>
           cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: TRML.java,v 1.1 2004/01/18 17:58:11 aaron Exp $
*/
public class TRML implements Filetype {

    public String toString() {
	return I18n.getText("format.trml");
    }

    public String getDefaultExtension() {
	return ".trml";
    }

    public Sample load(BufferedReader r) throws IOException {
	// make sure it's an xml file: first lines starts with "+"
	r.mark(50); // ASSUMES: the first line is <=50 chars
	String line = r.readLine();
	if (line==null || !line.startsWith("<?xml"))
	    throw new WrongFiletypeException(); // no header found
	r.reset();

	try {
	    // make a new XML parser
	    XMLReader xr = XMLReaderFactory.createXMLReader();

	    // ... configure it to use a my SampleHandler ...
	    TRMLHandler loader = new TRMLHandler();
	    xr.setContentHandler(loader);
	    xr.setErrorHandler(loader);

	    // parse() seems to close() the file, so any subsequent
	    // loaders don't get a chance to run.  solution: if you
	    // try to load a file, and it's closed, re-open it before
	    // you try the next loader.

	    // ... and feed it the file
	    xr.parse(new InputSource(r));
	    return loader.getSample();
	} catch (SAXException se) {
	    // why would this happen?
	    throw new WrongFiletypeException();
	}
    }

    /** A SAX2 handler for loading saved TRML files. */
    private static class TRMLHandler extends DefaultHandler {
	private boolean readAnything = false;
	private Sample s = new Sample();
	public Sample getSample() { // (for returning result)
	    return s;
	}

        public void startElement(String uri, String name,
				 String qName,
				 Attributes atts) throws SAXException {
            // something has been read!  make sure it's a trml file
            if (!readAnything) {
                if (name.equals("treerings")) {
                    readAnything = true;
                    return;
                }

                // else
                throw new SAXException("Not a TRML file!");
		// can't i do better?  wfte?
	    }

	    // if inactive, set flag
	    Object a = atts.getValue("active");
	    if (a != null)
		active = a.equals("true");

	    // data type
	    if (name.equals("data"))
		type = atts.getValue("type");
	    // TODO: use units, too?
        }
	private boolean active = true;
	private String type;
	private StringBuffer data = new StringBuffer();
	public void characters(char ch[], int start, int length) {
            data.append(new String(ch, start, length));
	}
	private Year start=null, end=null;
	private Range range=null;
        public void endElement(String uri, String name, String qName) {
	    // range
	    if (name.equals("start"))
		start = new Year(data.toString());
	    else if (name.equals("end"))
		end = new Year(data.toString());
	    if (start!=null && end!=null && range==null) {
		range = new Range(start, end);
		s.range = range;
	    }
	    if (name.equals("start") || name.equals("end")) {
		data = new StringBuffer();
		return;
	    }

	    // other metadata field
	    Iterator i = MetadataTemplate.getFields();
	    while (i.hasNext()) {
		MetadataTemplate.Field f = (MetadataTemplate.Field) i.next();

		if (f.getVariable().equals(name)) {
		    s.meta.put(name, data.toString().trim());
		    data = new StringBuffer();
		    return;
		}
	    }

	    // count/incr/decr field
	    if (name.equals("v")) {
		// if list doesn't exist, create
		if (type.equals("width") && s.data==null) {
		    s.data = new ArrayList();
		}
		if (type.equals("count") && s.count==null) {
		    s.count = new ArrayList();
		}
		if (type.equals("incr") && s.incr==null) {
		    s.incr = new ArrayList();
		}
		if (type.equals("decr") && s.decr==null) {
		    s.decr = new ArrayList();
		}

		// parse value
		int x = Integer.parseInt(data.toString().trim());

		// add this value to list
		if (type.equals("width"))
		    s.data.add(new Integer(x));
		if (type.equals("count"))
		    s.count.add(new Integer(x));
		if (type.equals("incr"))
		    s.incr.add(new Integer(x));
		if (type.equals("decr"))
		    s.decr.add(new Integer(x));

		data = new StringBuffer();
		return;
	    }

	    // elements field
	    if (name.equals("element")) {
		if (s.elements == null)
		    s.elements = new ArrayList();
		Element e = new Element(data.toString().trim(), active);
		// REFACTOR toString().trim()?
		s.elements.add(e);
		active = true;
		data = new StringBuffer();
	    }
        }
    }

    // big fat disclaimer
    private final static String[] DISCLAIMER = new String[] {
	"<!-- This file was created by Corina (http://corina.sf.net/).   -->",
	"<!-- This is an experimental format, TRML, that is not intended -->",
	"<!-- for actual use.  No other programs can read this format,   -->",
	"<!-- and it is subject to change at any time.                   -->",
	"<!-- You have been warned.                                      -->",
	"<!-- Cheers, Ken (kbh7@cornell.edu)                             -->",
    };

    public void save(Sample s, BufferedWriter w) throws IOException {
	w.write("<?xml version=\"1.0\"?>");
	w.newLine();

	w.newLine();

	// disclaimer
	for (int i=0; i<DISCLAIMER.length; i++) {
	    w.write(DISCLAIMER[i]);
	    w.newLine();
	}

	w.newLine();

	w.write("<treerings>");
	w.newLine();

	w.write("   <metadata>");
	w.newLine();

	// range
	w.write("      <start>" + s.range.getStart() + "</start>");
	w.newLine();
	w.write("      <end>" + s.range.getEnd() + "</end>");
	w.newLine();

	// other fields
	Iterator i = MetadataTemplate.getFields();
	while (i.hasNext()) {
	    MetadataTemplate.Field f = (MetadataTemplate.Field) i.next();

	    Object x = s.meta.get(f.getVariable());
	    if (x != null) {
		String v = StringUtils.escapeForXML(x.toString());
		String t = f.getVariable();
		w.write("      <" + t + ">" + v + "</" + t + ">");
		w.newLine();
	    }
	}

	// TODO: if you want precision, make a new metadata field for it.
	// e.g., <precision>0.01</precision>

	w.write("   </metadata>");
	w.newLine();

	// data
	w.newLine();
	w.write("   <data type=\"width\" units=\"0.01mm\">");
	w.newLine();
	saveData(w, s.data);
	w.write("   </data>");
	w.newLine();
	// TODO: add per-year comments?

	// count
	if (s.count != null) {
	    w.newLine();
	    w.write("   <data type=\"count\" units=\"number\">");
	    w.newLine();
	    saveData(w, s.count);
	    w.write("   </data>");
	    w.newLine();
	}

	// weiserjahre
	if (s.incr != null) {
	    w.newLine();
	    w.write("   <data type=\"incr\" units=\"number\">");
	    w.newLine();
	    saveData(w, s.incr);
	    w.write("   </data>");
	    w.newLine();

	    w.newLine();
	    w.write("   <data type=\"decr\" units=\"number\">");
	    w.newLine();
	    saveData(w, s.decr);
	    w.write("   </data>");
	    w.newLine();
	}

	// elements
	if (s.elements != null) {
	    w.newLine();

	    w.write("   <elements>");
	    w.newLine();

	    for (int ii=0; ii<s.elements.size(); ii++) {
		Element e = (Element) s.elements.get(ii);
		w.write("      <element" +
			(e.isActive() ? "" : " active=\"false\"") + ">" +
			e + "</element>");
		w.newLine();
	    }

	    w.write("   </elements>");
	    w.newLine();
	}

	w.write("</treerings>");
	w.newLine();
    }

    // number of columns (years of data) per line.  it's XML, so it
    // doesn't matter how the data goes in the file, but for debugging
    // keeping it in columns helps.  it's more verbose than tucson, so
    // 10 columns won't fit.  5 is a good compromise.
    private static final int COLUMNS = 5;

    private void saveData(BufferedWriter w, List data) throws IOException {
	for (int i=0; i<data.size(); i++) {
	    if (i % COLUMNS == 0)
		w.write("      ");
	    w.write("<v>" + data.get(i) + "</v>");
	    if (i % COLUMNS == COLUMNS-1 || i == data.size()-1)
		w.newLine();
	    else
		w.write(" ");
	}
    }
}
