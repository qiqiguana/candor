package net.virtualinfinity.atrobots.gui.renderers;

import net.virtualinfinity.atrobots.snapshots.MissileSnapshot;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public class MissileRenderer implements SnapshotRenderer<MissileSnapshot> {
    public void render(Graphics2D g2d, MissileSnapshot missileSnapshot, Set<Integer> selectedRobotIds) {
        g2d.setPaint(missileSnapshot.isOverburn() && (missileSnapshot.getAge().getCycles() & 2) == 0 ? Color.yellow : Color.white);
        final Stroke oldStroke = g2d.getStroke();
        if (missileSnapshot.isOverburn() && (missileSnapshot.getAge().getCycles() & 1) == 0) {
            g2d.setStroke(new BasicStroke(3));
        }
        g2d.draw(new Line2D.Double(missileSnapshot.getX(), missileSnapshot.getY(), missileSnapshot.getX() + missileSnapshot.getVelocityX(), missileSnapshot.getY() + missileSnapshot.getVelocityY()));
        g2d.setStroke(oldStroke);
    }
}
