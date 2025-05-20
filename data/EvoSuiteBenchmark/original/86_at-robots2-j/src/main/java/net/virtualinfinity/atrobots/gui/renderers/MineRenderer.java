package net.virtualinfinity.atrobots.gui.renderers;

import net.virtualinfinity.atrobots.snapshots.MineSnapshot;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public class MineRenderer implements SnapshotRenderer<MineSnapshot> {
    public void render(Graphics2D g2d, MineSnapshot mineSnapshot, Set<Integer> selectedRobotIds) {
        final Ellipse2D.Double ellipse = new Ellipse2D.Double();
        ellipse.setFrameFromCenter(mineSnapshot.getX(), mineSnapshot.getY(), mineSnapshot.getX() + mineSnapshot.getTriggerRadius(), mineSnapshot.getY() + mineSnapshot.getTriggerRadius());
        g2d.setPaint(new RadialGradientPaint(mineSnapshot.getPositionVector().toPoint2D(), (float) mineSnapshot.getTriggerRadius(),
                new float[]{0f, 1f}, new Color[]{new Color(0, 0f, 0, .2f), new Color(.1f, 1f, .2f, .2f)}));
        g2d.fill(ellipse);
        g2d.setPaint(new RadialGradientPaint(mineSnapshot.getPositionVector().toPoint2D(), 3,
                new float[]{0f, 1f}, new Color[]{new Color(.8f, .8f, .9f, 1f), new Color(.1f, .1f, .2f, 1f)}));
        ellipse.setFrameFromCenter(mineSnapshot.getX(), mineSnapshot.getY(), mineSnapshot.getX() + 3, mineSnapshot.getY() + 3);
        g2d.fill(ellipse);
    }
}
