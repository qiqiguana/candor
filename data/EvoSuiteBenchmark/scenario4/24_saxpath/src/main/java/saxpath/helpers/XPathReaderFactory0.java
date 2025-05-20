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
     * The <code>org.saxpath.driver</code> property name.
     */
    public static final String DRIVER_PROPERTY = "org.saxpath.driver";

    /**
     * The default driver to use if none is configured.
     */
    protected static final String DEFAULT_DRIVER = "com.werken.saxpath.XPathReader";

    /**
     * Should the default driver be used
     */
    private static boolean USE_DEFAULT = true;

    /**
     * Create an <code>XPathReader</code> using the value of
     *  the <code>org.saxpath.driver</code> system property.
     *
     *  @return An instance of the <code>XPathReader</code> specified
     *          by the <code>org.saxpath.driver</code> property.
     *
     *  @throws SAXPathException if the property is unset, or if
     *          the class can not be instantiated for some reason.,
     *          or if the class doesn't implement the <code>XPathReader</code>
     *          interface.
     */
    public static XPathReader createReader() throws SAXPathException;

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
