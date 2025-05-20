package net.virtualinfinity.atrobots.gui.renderers;

import net.virtualinfinity.atrobots.snapshots.ExplosionSnapshot;

import java.awt.*;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public class GradientExplosionRenderer implements SnapshotRenderer<ExplosionSnapshot> {
    public void render(Graphics2D g2d, ExplosionSnapshot explosionSnapshot, Set<Integer> selectedRobotIds) {
        g2d.setPaint(new RadialGradientPaint(explosionSnapshot.getPositionVector().toPoint2D(), (float) explosionSnapshot.getRadius(), new float[]{0, 1}, new Color[]{Color.yellow, Color.red}));
        final Composite composite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.85f));
        g2d.fill(explosionSnapshot.getExplosionShape());
        g2d.setComposite(composite);

    }

}
