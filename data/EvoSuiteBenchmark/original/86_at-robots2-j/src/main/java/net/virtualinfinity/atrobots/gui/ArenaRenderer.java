package net.virtualinfinity.atrobots.gui;

import net.virtualinfinity.atrobots.gui.renderers.*;
import net.virtualinfinity.atrobots.snapshots.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Set;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class ArenaRenderer {
    private RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private Collection<ArenaObjectSnapshot> toPaint;
    private RobotStatusPane robotStatusPane;
    private static final Rectangle2D.Double ARENA_SIZE = new Rectangle2D.Double(0, 0, 1000, 1000);
    private SnapshotRenderer<? super RobotSnapshot> robotRenderer = new RobotRenderer();
    private SnapshotRenderer<? super MissileSnapshot> missileRenderer = new MissileRenderer();
    private SnapshotRenderer<? super MineSnapshot> mineRenderer = new MineRenderer();
    private SnapshotRenderer<? super ExplosionSnapshot> explosionRenderer = new SimpleExplosionRenderer();
    private SnapshotRenderer<? super ScanSnapshot> scanRenderer = new ScanRenderer();

    void innerPaint(final Graphics2D g2d) {
        if (!hasFullFrame()) {
            return;
        }
        final RenderingHints originalRenderingHints = g2d.getRenderingHints();
        g2d.addRenderingHints(hints);
        final Paint paint = g2d.getPaint();
        final Stroke stroke = g2d.getStroke();
        final AffineTransform transform = g2d.getTransform();
        final SnapshotVisitor visitor = new SnapshotPainter(g2d, robotStatusPane.getSelectedRobotIds());
        for (ArenaObjectSnapshot snapshot : toPaint) {
            snapshot.visit(visitor);
            g2d.setTransform(transform);
            g2d.setPaint(paint);
            g2d.setStroke(stroke);
        }
        g2d.setRenderingHints(originalRenderingHints);
    }

    public RenderingHints getHints() {
        return hints;
    }

    public void setHints(RenderingHints hints) {
        this.hints = hints;
    }

    public SnapshotRenderer<? super RobotSnapshot> getRobotRenderer() {
        return robotRenderer;
    }

    public void setRobotRenderer(SnapshotRenderer<? super RobotSnapshot> robotRenderer) {
        this.robotRenderer = robotRenderer;
    }

    public SnapshotRenderer<? super MissileSnapshot> getMissileRenderer() {
        return missileRenderer;
    }

    public void setMissileRenderer(SnapshotRenderer<? super MissileSnapshot> missileRenderer) {
        this.missileRenderer = missileRenderer;
    }

    public SnapshotRenderer<? super MineSnapshot> getMineRenderer() {
        return mineRenderer;
    }

    public void setMineRenderer(SnapshotRenderer<? super MineSnapshot> mineRenderer) {
        this.mineRenderer = mineRenderer;
    }

    public SnapshotRenderer<? super ExplosionSnapshot> getExplosionRenderer() {
        return explosionRenderer;
    }

    public void setExplosionRenderer(SnapshotRenderer<? super ExplosionSnapshot> explosionRenderer) {
        this.explosionRenderer = explosionRenderer;
    }

    public SnapshotRenderer<? super ScanSnapshot> getScanRenderer() {
        return scanRenderer;
    }

    public void setScanRenderer(SnapshotRenderer<? super ScanSnapshot> scanRenderer) {
        this.scanRenderer = scanRenderer;
    }

    private boolean hasFullFrame() {
        return toPaint != null;
    }

    public void setToPaint(Collection<ArenaObjectSnapshot> toPaint) {
        this.toPaint = toPaint;
    }

    public void setRobotStatusPane(RobotStatusPane robotStatusPane) {
        this.robotStatusPane = robotStatusPane;
    }

    public void drawBorder(Graphics2D g2d) {
        final RenderingHints originalRenderingHints = g2d.getRenderingHints();
        g2d.addRenderingHints(hints);
        g2d.setPaint(Color.green);
        g2d.draw(ARENA_SIZE);
        g2d.setRenderingHints(originalRenderingHints);
    }

    private class SnapshotPainter implements SnapshotVisitor {
        private final Graphics2D g2d;
        private final Set<Integer> selectedRobotIds;

        public SnapshotPainter(Graphics2D g2d, Set<Integer> selectedRobotIds) {
            this.g2d = g2d;
            this.selectedRobotIds = selectedRobotIds;
        }

        public void acceptRobot(RobotSnapshot robotSnapshot) {
            robotRenderer.render(g2d, robotSnapshot, selectedRobotIds);
        }

        public void acceptMissile(MissileSnapshot missileSnapshot) {
            missileRenderer.render(g2d, missileSnapshot, selectedRobotIds);
        }

        public void acceptMine(MineSnapshot mineSnapshot) {
            mineRenderer.render(g2d, mineSnapshot, selectedRobotIds);
        }

        public void acceptExplosion(ExplosionSnapshot explosionSnapshot) {
            explosionRenderer.render(g2d, explosionSnapshot, selectedRobotIds);
        }


        public void acceptScan(ScanSnapshot scanSnapshot) {
            scanRenderer.render(g2d, scanSnapshot, selectedRobotIds);
        }

        public void acceptUnknown(UnknownSnapshot unknownSnapshot) {
            // TODO: Handle the unknown ;-)
        }
    }
}
