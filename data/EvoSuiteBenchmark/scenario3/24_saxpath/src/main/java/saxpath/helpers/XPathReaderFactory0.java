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
    public static XPathReader createReader() throws SAXPathException;
}
