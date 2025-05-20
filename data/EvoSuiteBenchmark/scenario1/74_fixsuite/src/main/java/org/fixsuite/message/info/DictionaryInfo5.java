package org.fixsuite.message.info;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents a FIX version specification. DictionaryInfo acts as a central
 * storage for all items defined in a version.
 *
 * @author jramoyo
 */
public class DictionaryInfo {

    public MessageInfo getMessage(String name) {
        if (messagesByName != null) {
            return messagesByName.get(name);
        } else {
            return null;
        }
    }
}
