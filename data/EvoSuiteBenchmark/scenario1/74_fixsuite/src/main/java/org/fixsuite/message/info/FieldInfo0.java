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

    public boolean isRequiredInComponent(ComponentInfo component) {
        if (requiringComponents != null) {
            return requiringComponents.contains(component);
        } else {
            return false;
        }
    }
}
