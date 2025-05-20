package org.fixsuite.message.info;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents a Field as defined by the FIX specification.
 *
 * @author jramoyo
 */
public class FieldInfo implements FixInfo {

    public boolean isValidValue(String value) {
        return validValues.keySet().contains(value);
    }
}
