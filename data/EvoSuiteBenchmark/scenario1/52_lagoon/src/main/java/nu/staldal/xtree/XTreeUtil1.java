package nu.staldal.xtree;

import java.util.*;
import java.io.*;
import java.net.URL;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.*;
import org.iso_relax.verifier.*;
import nu.staldal.xmlutil.ContentHandlerFixer;

/**
 * Some utility methods for XTree. All methods in this class are static.
 */
public final class XTreeUtil {

    public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, String schemaType, InputSource schema, ElementHandler handler) throws SAXParseException, SAXException, IOException {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            parserFactory.setValidating(validateDTD);
            XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
            SequentialTreeBuilder tb = new SequentialTreeBuilder(handler);
            if (schema != null) {
                VerifierFactory vf = VerifierFactory.newInstance(schemaType);
                Verifier verifier = vf.newVerifier(schema);
                VerifierFilter filter = verifier.getVerifierFilter();
                filter.setParent(xmlReader);
                xmlReader = filter;
            }
            xmlReader.setContentHandler(tb);
            xmlReader.setErrorHandler(tb);
            xmlReader.parse(xmlInput);
            if ((schema != null) && !((VerifierFilter) xmlReader).isValid()) {
                throw new SAXParseException("Invalid XML data", null, null, -1, -1);
            }
            return tb.getRootElement();
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            throw new Error("XML parser configuration error: " + e.getMessage());
        } catch (VerifierConfigurationException e) {
            throw new Error("XML verifier configuration error: " + e.getMessage());
        }
    }
}
