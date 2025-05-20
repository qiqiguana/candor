package net.virtualinfinity.atrobots.gui.renderers;

import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;

import java.awt.*;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public interface SnapshotRenderer<T extends ArenaObjectSnapshot> {
    public void render(Graphics2D g2d, T object, Set<Integer> selectedRobotIds);
}
