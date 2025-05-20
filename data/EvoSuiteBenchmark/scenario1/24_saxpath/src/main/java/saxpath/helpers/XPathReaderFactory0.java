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

    public static XPathReader createReader() throws SAXPathException {
        String className = null;
        boolean securityException = false;
        try {
            className = System.getProperty(DRIVER_PROPERTY);
        } catch (SecurityException e) {
            securityException = true;
        }
        if (className == null || "".equals(className)) {
            if (USE_DEFAULT) {
                className = DEFAULT_DRIVER;
            } else {
                if (securityException) {
                    throw new SAXPathException("Reading of property " + DRIVER_PROPERTY + " disallowed.");
                } else {
                    throw new SAXPathException("Property " + DRIVER_PROPERTY + " not set");
                }
            }
        }
        return createReader(className);
    }
}
