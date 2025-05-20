package net.virtualinfinity.atrobots.gui.renderers;

import net.virtualinfinity.atrobots.snapshots.ScanSnapshot;

import java.awt.*;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public class ScanRenderer implements SnapshotRenderer<ScanSnapshot> {
    private boolean fillArcs = false;
    private float highlightMatchRadius = 50;
    private Color[] matchColors = new Color[]{new Color(1f, 0f, 0f, 1f), new Color(1f, 0f, 0f, .25f)};
    private float[] noMatchFractions = new float[]{0f, 1f};
    private Color[] noMatchFillColors = new Color[]{new Color(1f, 1f, 1f, .75f), new Color(0f, 0f, 0f, 0f)};
    private float[] matchFractions = new float[]{0f, 1f};
    private Color successOutline = Color.red;
    private Color failureOutline = Color.white;

    public void render(Graphics2D g2d, ScanSnapshot scanSnapshot, Set<Integer> selectedRobotIds) {
        g2d.setPaint(getOutlinePaint(scanSnapshot));
        final Shape scanArea = scanSnapshot.getScanArea();
        g2d.draw(scanArea);
        if (fillArcs) {
            g2d.setPaint(getFillPaint(scanSnapshot));
            g2d.fill(scanArea);
            if (scanSnapshot.isAccuracyAvailable()) {
                g2d.setPaint(new Color(.1f, 0f, .5f, .20f));
                g2d.fill(scanSnapshot.getAccuracyArea());
                g2d.setPaint(new Color(.4f, .4f, .5f, .5f));
                g2d.draw(scanSnapshot.getAccuracyArea());
            }
        }
    }

    public boolean isFillArcs() {
        return fillArcs;
    }

    public void setFillArcs(boolean fillArcs) {
        this.fillArcs = fillArcs;
    }

    private Paint getFillPaint(ScanSnapshot scanSnapshot) {
        if (scanSnapshot.isSuccessful()) {
            return new RadialGradientPaint(scanSnapshot.getMatchVector().toPoint2D(),
                    highlightMatchRadius, matchFractions, matchColors);
        } else {
            return new RadialGradientPaint(scanSnapshot.getPositionVector().toPoint2D(),
                    (float) scanSnapshot.getMaxDistance(), noMatchFractions, noMatchFillColors);
        }
    }

    private Paint getOutlinePaint(ScanSnapshot scanSnapshot) {
        return scanSnapshot.isSuccessful() ? successOutline : failureOutline;
    }

}
