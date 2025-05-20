package net.virtualinfinity.atrobots.gui;

import net.virtualinfinity.atrobots.arena.SimulationFrame;
import net.virtualinfinity.atrobots.arena.SimulationFrameBuffer;
import net.virtualinfinity.atrobots.arena.SimulationObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * A GUI component which renders the game arena.
 * This object should be registered as a {@link net.virtualinfinity.atrobots.arena.SimulationObserver} in the
 * {@link net.virtualinfinity.atrobots.arena.FrameBuilder}
 *
 * @author Daniel Pitts
 */
public class ArenaPane extends JComponent implements SimulationObserver {
    private SimulationFrame currentFrame;
    private final ArenaRenderer arenaRenderer = new ArenaRenderer();
    private static final double BORDER_WIDTH = 50.0;
    private static final double BORDER_HEIGHT = 50.0;

    public ArenaPane() {
    }


    public ArenaRenderer getArenaRenderer() {
        return arenaRenderer;
    }

    public void frameAvailable(SimulationFrameBuffer buffer) {
        EventQueue.invokeLater(new UpdateFrame(buffer));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (hasNewFrame()) {
            makeNewFrameCurrentFrame();
        }
        if (isOpaque()) {
            final Paint paint = g2d.getPaint();
            g2d.setPaint(getBackground());
            g2d.fill(getBounds());
            g2d.setPaint(paint);
        }
        final AffineTransform originalTransform = g2d.getTransform();
        try {
            g2d.scale(getWidth() / (1000.0 + BORDER_WIDTH * 2), getHeight() / (1000.0 + BORDER_HEIGHT * 2));
            g2d.translate(BORDER_WIDTH, BORDER_HEIGHT);
            arenaRenderer.drawBorder(g2d);
            arenaRenderer.innerPaint(g2d);
        } finally {
            g2d.setTransform(originalTransform);
        }
    }


    private void makeNewFrameCurrentFrame() {
        arenaRenderer.setToPaint(currentFrame.getAllObjects());
    }

    private boolean hasNewFrame() {
        return currentFrame != null;
    }

    public void setRobotStatusPane(RobotStatusPane robotStatusPane) {
        arenaRenderer.setRobotStatusPane(robotStatusPane);
    }

    public void reset() {
        currentFrame = null;
        arenaRenderer.setToPaint(null);
        repaint();
    }

    private class UpdateFrame implements Runnable {
        private final SimulationFrameBuffer buffer;

        public UpdateFrame(SimulationFrameBuffer buffer) {
            this.buffer = buffer;
        }

        public void run() {
            repaint();
            currentFrame = buffer.getCurrentFrame();
        }
    }

}
