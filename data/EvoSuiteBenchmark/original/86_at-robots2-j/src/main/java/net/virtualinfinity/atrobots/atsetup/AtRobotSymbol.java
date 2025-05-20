package net.virtualinfinity.atrobots.atsetup;

import java.util.Collection;

/**
 * A built-in constant that has a value and a collection of aliases.
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface AtRobotSymbol {
    int getSymbolValue();

    Collection<String> getSymbolNames();
}
