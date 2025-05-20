package ghm.follow.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Extension of {@link java.util.Properties} which allows one to specify property values which are
 * Lists of Strings.
 *
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class EnumeratedProperties extends Properties {

    /**
     * Returns the List value of the property with the supplied key. Note that one can call
     * getEnumeratedProperty() for a given key successfully if and only if setEnumeratedProperty()
     * for that key was called some time beforehand. All members of the list returned will be
     * Strings.
     *
     * @param key lookup of the enumerated property to be retrieved.
     * @return list containing String values
     */
    public List<String> getEnumeratedProperty(String key);
}
