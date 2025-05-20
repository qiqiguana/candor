package net.virtualinfinity.atrobots.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.VolatileImage;

/**
 * @author Daniel Pitts
 */
public class TestAccel extends JComponent implements Runnable {
    private VolatileImage volatileImage;

    public void run() {
        final JFrame jFrame = new JFrame();
        jFrame.getContentPane().add(this);
        setPreferredSize(new Dimension(400, 400));
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    Color[] colors = new Color[]{
            Color.black,
            Color.blue,
            Color.green,
            Color.red,
            Color.yellow,
            Color.pink
    };
    private int c;

    protected void paintComponent(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
//        if (!g2d.getDeviceConfiguration().getImageCapabilities().isAccelerated()) {
//            try {
//                if (volatileImage == null || volatileImage.validate(g2d.getDeviceConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE || volatileImage.getWidth() != getWidth() || volatileImage.getHeight()!= getHeight()) {
//                    System.out.println("Creating volatile image");
//                    volatileImage = createVolatileImage(getWidth(), getHeight(), new ImageCapabilities(true));
//                }
//                try {
//                    do {
//                        final Graphics2D graphics = volatileImage.createGraphics();
//                        timedDraw(graphics);
//                        graphics.dispose();
//                        g2d.drawImage(volatileImage, 0, 0, null);
//                    } while (volatileImage.contentsLost());
//                } finally {
//                    volatileImage.flush();
//                }
//            } catch (Throwable e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//
        timedDraw(g2d);
        repaint();
    }

    private void timedDraw(Graphics2D g2d) {
        final long l = System.nanoTime() + 1000 * 1000 * 60;
        final Ellipse2D.Double s = new Ellipse2D.Double();
        double i = 0;
        while (System.nanoTime() < l) {
            s.setFrameFromCenter(getWidth() / 2, getHeight() / 2, i, i);
            if (c >= colors.length) {
                c = 0;
            }
            g2d.setPaint(colors[c++]);
            g2d.fill(s);
            i += 1;
        }
        System.out.println(i);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new TestAccel());
    }
}
