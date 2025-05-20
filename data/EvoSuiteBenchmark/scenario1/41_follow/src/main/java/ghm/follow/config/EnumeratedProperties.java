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

    public List<String> getEnumeratedProperty(String key) {
        ArrayList<String> values = new ArrayList<String>();
        int i = 0;
        String value;
        while ((value = this.getProperty(key + delimiter + i++)) != null) {
            values.add(value);
        }
        return values;
    }
}
