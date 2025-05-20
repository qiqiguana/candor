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
     * Returns whether this field is required in the specified component
     *
     * @param component - a component
     * @return whether this field is required in the specified component
     */
    public boolean isRequiredInComponent(ComponentInfo component) {
        if (requiringComponents != null) {
            return requiringComponents.contains(component);
        } else {
            return false;
        }
    }
}
