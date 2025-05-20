package org.saxpath.helpers;

import org.saxpath.XPathReader;
import org.saxpath.SAXPathException;

/**
 * Create an {@link org.saxpath.XPathReader} from
 *  either a system property, or a named class.
 *
 *  <p>
 *  Similar to the SAX API, the <code>XPathReaderFactory</code>
 *  can create an <code>XPathReader</code> from a name of a
 *  class passed in directly, or by inspecting the system
 *  property <code>org.saxpath.driver</code>.
 *
 * @author bob mcwhirter (bob@werken.com)
 */
public class XPathReaderFactory {

    public static XPathReader createReader(String className) throws SAXPathException {
        Class readerClass = null;
        XPathReader reader = null;
        try {
            // Use the full version of Class.forName(), so as to
            // work better in sandboxed environments, such as
            // Servlet contains, and Applets.
            readerClass = Class.forName(className, true, XPathReaderFactory.class.getClassLoader());
            // Double-check that it's actually the right kind of class
            // before attempting instantiation.
            if (!XPathReader.class.isAssignableFrom(readerClass)) {
                throw new SAXPathException("Class [" + className + "] does not implement the org.saxpath.XPathReader interface.");
            }
        } catch (ClassNotFoundException e) {
            throw new SAXPathException(e.getMessage());
        }
        try {
            reader = (XPathReader) readerClass.newInstance();
        } catch (IllegalAccessException e) {
            throw new SAXPathException(e.getMessage());
        } catch (InstantiationException e) {
            throw new SAXPathException(e.getMessage());
        }
        if (reader == null) {
            throw new SAXPathException("Unable to create XPathReader");
        }
        return reader;
    }
}
