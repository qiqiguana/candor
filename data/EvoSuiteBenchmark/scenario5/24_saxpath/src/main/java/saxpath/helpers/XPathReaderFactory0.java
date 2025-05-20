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

    /**
     * Create an <code>XPathReader</code> using the value of
     *  the <code>org.saxpath.driver</code> system property.
     *
     * @return An instance of the <code>XPathReader</code> specified
     *          by the <code>org.saxpath.driver</code> property.
     * @throws SAXPathException if the property is unset, or if
     *          the class can not be instantiated for some reason.,
     *          or if the class doesn't implement the <code>XPathReader</code>
     *          interface.
     */
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

    /**
     * Create an <code>XPathReader</code> using the passed
     *  in class name.
     *
     *  @param className The name of the class which implements
     *         the <code>XPathReader</code> interface.
     *
     *  @throws SAXPathException if the class can not be
     *          instantiated for some reason, or if the
     *          class doesn't implement the <code>XPathReader</code>
     *          interface.
     */
    public static XPathReader createReader(String className) throws SAXPathException;
}
