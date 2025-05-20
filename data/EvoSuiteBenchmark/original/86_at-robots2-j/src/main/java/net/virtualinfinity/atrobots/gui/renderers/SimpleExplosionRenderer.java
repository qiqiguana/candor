package net.virtualinfinity.atrobots.gui.renderers;

import net.virtualinfinity.atrobots.snapshots.ExplosionSnapshot;

import java.awt.*;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public class SimpleExplosionRenderer implements SnapshotRenderer<ExplosionSnapshot> {
    public void render(Graphics2D g2d, ExplosionSnapshot explosionSnapshot, Set<Integer> selectedRobotIds) {
        g2d.setPaint(Color.yellow);
        g2d.draw(explosionSnapshot.getExplosionShape());
    }
}