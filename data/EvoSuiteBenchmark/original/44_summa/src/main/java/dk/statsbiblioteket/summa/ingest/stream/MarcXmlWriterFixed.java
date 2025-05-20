/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.ingest.stream;

import com.ibm.icu.text.Normalizer;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.marc4j.Constants;
import org.marc4j.MarcException;
import org.marc4j.MarcWriter;
import org.marc4j.converter.CharConverter;
import org.marc4j.marc.*;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Iterator;

/**
 * Nearly direct copy of {@link org.marc4j.MarcXmlWriter} that fixed the
 * duplicate default namespace declaration bug by reverting to the old
 * implementation.
 * </p><p>
 * This class will be removed from Summa when the bug is fixed natively in
 * marc4j.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te, marc4j")
public class MarcXmlWriterFixed implements MarcWriter {
    protected static final String CONTROL_FIELD = "controlfield";
    protected static final String DATA_FIELD = "datafield";
    protected static final String SUBFIELD = "subfield";
    protected static final String COLLECTION = "collection";
    protected static final String RECORD = "record";
    protected static final String LEADER = "leader";

    /**
     * True if the output XML should be indented.
     */
    private boolean indent = false;
    /**
     * The XML handler.
     */
    private TransformerHandler handler = null;
    /**
     * The writer.
     */
    private Writer writer = null;

    /**
     * Charecter converter.
     */
    private CharConverter converter = null;

    /**
     * True if XMl should be normalized.
     */
    private boolean normalize = false;

    /**
     * Constructs an instance with the specified output stream.
     * <p/>
     * The default character encoding for UTF-8 is used.
     *
     * @param out The output stream.
     * @throws org.marc4j.MarcException
     */
    public MarcXmlWriterFixed(OutputStream out) {
        this(out, false);
    }

    /**
     * Constructs an instance with the specified output stream and indentation.
     * <p/>
     * The default character encoding for UTF-8 is used.
     *
     * @param out    The output stream.
     * @param indent True if XML should be indented.
     * @throws org.marc4j.MarcException
     */
    public MarcXmlWriterFixed(OutputStream out, boolean indent) {
        this(out, "UTF8", indent);
    }

    /**
     * Constructs an instance with the specified output stream and character
     * encoding.
     *
     * @param out      The output stream.
     * @param encoding The encoding.
     * @throws org.marc4j.MarcException
     */
    public MarcXmlWriterFixed(OutputStream out, String encoding) {
        this(out, encoding, false);
    }

    /**
     * Constructs an instance with the specified output stream, character
     * encoding and indentation.
     *
     * @param out      The output stream.
     * @param encoding The encoding.
     * @param indent   True if XML should be indented.
     * @throws org.marc4j.MarcException
     */
    public MarcXmlWriterFixed(OutputStream out, String encoding, boolean indent) {
        if (out == null) {
            throw new NullPointerException("null OutputStream");
        }
        if (encoding == null) {
            throw new NullPointerException("null encoding");
        }
        try {
            setIndent(indent);
            writer = new OutputStreamWriter(out, encoding);
            writer = new BufferedWriter(writer);
            setHandler(new StreamResult(writer), null);
        } catch (UnsupportedEncodingException e) {
            throw new MarcException(e.getMessage(), e);
        }
        writeStartDocument();
    }

    /**
     * Constructs an instance with the specified result.
     *
     * @param result The result.
     */
    public MarcXmlWriterFixed(Result result) {
        if (result == null) {
            throw new NullPointerException("null Result");
        }
        setHandler(result, null);
        writeStartDocument();
    }

    /**
     * Constructs an instance with the specified stylesheet location and result.
     *
     * @param result        The results.
     * @param stylesheetUrl The stylesheet URL.
     */
    public MarcXmlWriterFixed(Result result, String stylesheetUrl) {
        this(result, new StreamSource(stylesheetUrl));
    }

    /**
     * Constructs an instance with the specified stylesheet source and result.
     *
     * @param result     The result.
     * @param stylesheet The style sheet.
     */
    public MarcXmlWriterFixed(Result result, Source stylesheet) {
        if (stylesheet == null) {
            throw new NullPointerException("null Source");
        }
        if (result == null) {
            throw new NullPointerException("null Result");
        }
        setHandler(result, stylesheet);
        writeStartDocument();
    }

    /**
     * Closes the XML stream.
     */
    @Override
    public void close() {
        writeEndDocument();
        try {
            writer.close();
        } catch (IOException e) {
            throw new MarcException(e.getMessage(), e);
        }
    }

    /**
     * Returns the character converter.
     *
     * @return CharConverter the character converter
     */
    @Override
    public CharConverter getConverter() {
        return converter;
    }

    /**
     * Sets the character converter.
     *
     * @param converter the character converter
     */
    @Override
    public void setConverter(CharConverter converter) {
        this.converter = converter;
    }

    /**
     * If set to true this writer will perform Unicode normalization on data
     * elements using normalization form C (NFC). The default is false.
     * <p/>
     * The implementation used is ICU4J 2.6. This version is based on Unicode
     * 4.0.
     *
     * @param normalize true if this writer performs Unicode normalization, false
     *                  otherwise
     */
    public void setUnicodeNormalization(boolean normalize) {
        this.normalize = normalize;
    }

    /**
     * Returns true if this writer will perform Unicode normalization, false
     * otherwise.
     *
     * @return boolean - true if this writer performs Unicode normalization,
     *         false otherwise.
     */
    public boolean getUnicodeNormalization() {
        return normalize;
    }

    protected void setHandler(Result result, Source stylesheet) throws MarcException {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            if (!factory.getFeature(SAXTransformerFactory.FEATURE)) {
                throw new UnsupportedOperationException("SAXTransformerFactory is not supported");
            }
            SAXTransformerFactory saxFactory = (SAXTransformerFactory) factory;
            handler = stylesheet == null ?
                      saxFactory.newTransformerHandler() :
                      saxFactory.newTransformerHandler(stylesheet);
            handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
            handler.setResult(result);

        } catch (Exception e) {
            throw new MarcException(e.getMessage(), e);
        }
    }

    /**
     * This method is rewritten by Toke Eskildsen and is the only change from
     * the original class.
     */
    protected void writeStartDocument() {
        try {
//            AttributesImpl atts = new AttributesImpl();
            handler.startDocument();
            // The next line duplicates the namespace declaration for Marc XML
            handler.startPrefixMapping("", Constants.MARCXML_NS_URI);
            // add namespace declaration using attribute - need better solution
//            atts.addAttribute(Constants.MARCXML_NS_URI, "xmlns", "xmlns",
//                              "CDATA", Constants.MARCXML_NS_URI);
//            handler.startElement(Constants.MARCXML_NS_URI, COLLECTION, COLLECTION, atts);
            handler.startElement("", COLLECTION, COLLECTION, null);
        } catch (SAXException e) {
            throw new MarcException("SAX error occured while writing start document", e);
        }
    }

    /**
     * Writes the root end tag to the result.
     */
    protected void writeEndDocument() {
        try {
            if (indent) {
                handler.ignorableWhitespace("\n".toCharArray(), 0, 1);
            }
            handler.endElement(Constants.MARCXML_NS_URI, COLLECTION, COLLECTION);
            handler.endPrefixMapping("");
            handler.endDocument();
        } catch (SAXException e) {
            throw new MarcException("SAX error occured while writing end document", e);
        }
    }

    /**
     * Writes a Record object to the result.
     *
     * @param record the <code>Record</code> object
     */
    @Override
    public void write(Record record) {
        try {
            toXml(record);
        } catch (SAXException e) {
            throw new MarcException("SAX error occured while writing record", e);
        }
    }

    /**
     * Returns true if indentation is active, false otherwise.
     *
     * @return boolean
     */
    public boolean hasIndent() {
        return indent;
    }

    /**
     * Activates or deactivates indentation. Default value is false.
     *
     * @param indent True if this should be indented.
     */
    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    /**
     * Updates the handler, so it contains an XML representation of a record.
     *
     * @param record The record.
     * @throws SAXException If error creating XML.
     */
    protected void toXml(Record record) throws SAXException {
        char temp[];
        AttributesImpl atts = new AttributesImpl();
        if (indent) {
            handler.ignorableWhitespace("\n  ".toCharArray(), 0, 3);
        }
        handler.startElement(Constants.MARCXML_NS_URI, RECORD, RECORD, atts);

        if (indent) {
            handler.ignorableWhitespace("\n    ".toCharArray(), 0, 5);
        }
        handler.startElement(Constants.MARCXML_NS_URI, LEADER, LEADER, atts);
        Leader leader = record.getLeader();
        temp = leader.toString().toCharArray();
        handler.characters(temp, 0, temp.length);
        handler.endElement(Constants.MARCXML_NS_URI, LEADER, LEADER);

        Iterator i = record.getControlFields().iterator();
        while (i.hasNext()) {
            ControlField field = (ControlField) i.next();
            atts = new AttributesImpl();
            atts.addAttribute("", "tag", "tag", "CDATA", field.getTag());

            if (indent) {
                handler.ignorableWhitespace("\n    ".toCharArray(), 0, 5);
            }
            handler.startElement(Constants.MARCXML_NS_URI, CONTROL_FIELD, CONTROL_FIELD, atts);
            temp = getDataElement(field.getData());
            handler.characters(temp, 0, temp.length);
            handler.endElement(Constants.MARCXML_NS_URI, CONTROL_FIELD, CONTROL_FIELD);
        }

        i = record.getDataFields().iterator();
        while (i.hasNext()) {
            DataField field = (DataField) i.next();
            atts = new AttributesImpl();
            atts.addAttribute("", "tag", "tag", "CDATA", field.getTag());
            atts.addAttribute("", "ind1", "ind1", "CDATA", String.valueOf(field.getIndicator1()));
            atts.addAttribute("", "ind2", "ind2", "CDATA", String.valueOf(field.getIndicator2()));

            if (indent) {
                handler.ignorableWhitespace("\n    ".toCharArray(), 0, 5);
            }
            handler.startElement(Constants.MARCXML_NS_URI, DATA_FIELD, DATA_FIELD, atts);
            for (Object o : field.getSubfields()) {
                Subfield subfield = (Subfield) o;
                atts = new AttributesImpl();
                atts.addAttribute("", "code", "code", "CDATA", String.valueOf(subfield.getCode()));

                if (indent) {
                    handler.ignorableWhitespace("\n      ".toCharArray(), 0, 7);
                }
                handler.startElement(Constants.MARCXML_NS_URI, SUBFIELD, SUBFIELD, atts);
                temp = getDataElement(subfield.getData());
                handler.characters(temp, 0, temp.length);
                handler.endElement(Constants.MARCXML_NS_URI, SUBFIELD, SUBFIELD);
            }

            if (indent) {
                handler.ignorableWhitespace("\n    ".toCharArray(), 0, 5);
            }
            handler.endElement(Constants.MARCXML_NS_URI, DATA_FIELD, DATA_FIELD);
        }

        if (indent) {
            handler.ignorableWhitespace("\n  ".toCharArray(), 0, 3);
        }
        handler.endElement(Constants.MARCXML_NS_URI, RECORD, RECORD);
    }

    /**
     * Return the data for a given element.
     *
     * @param data The data element.
     * @return The data.
     */
    protected char[] getDataElement(String data) {
        String dataElement;
        if (converter == null) {
            return data.toCharArray();
        }
        dataElement = converter.convert(data);
        if (normalize) {
            dataElement = Normalizer.normalize(dataElement, Normalizer.NFC);
        }
        return dataElement.toCharArray();
    }
}
