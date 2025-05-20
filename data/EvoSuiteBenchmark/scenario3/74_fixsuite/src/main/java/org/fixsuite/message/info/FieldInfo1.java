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

    /**
     * Returns whether a given value is valid
     *
     * @param value - a value
     * @return whether a given value is valid
     */
    public boolean isValidValue(String value);
}
