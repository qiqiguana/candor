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
     * Create an <code>XPathReader</code> using the passed
     *  in class name.
     *
     * @param className The name of the class which implements
     *         the <code>XPathReader</code> interface.
     * @throws SAXPathException if the class can not be
     *          instantiated for some reason, or if the
     *          class doesn't implement the <code>XPathReader</code>
     *          interface.
     */
    public static XPathReader createReader(String className) throws SAXPathException;
}
