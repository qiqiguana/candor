package net.virtualinfinity.atrobots.hardware.scanning;

import net.virtualinfinity.atrobots.measures.AngleBracket;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface ScanSource {
    ScanResult scan(AngleBracket angleBracket, double maxDistance, boolean calculateAccuracy, boolean includeTargetDetails);
}
