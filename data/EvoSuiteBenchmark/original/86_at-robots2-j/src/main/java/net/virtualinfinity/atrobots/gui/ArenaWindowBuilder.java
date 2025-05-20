package net.virtualinfinity.atrobots.gui;

import net.virtualinfinity.atrobots.gui.renderers.GradientExplosionRenderer;
import net.virtualinfinity.atrobots.gui.renderers.RobotRenderer;
import net.virtualinfinity.atrobots.gui.renderers.ScanRenderer;
import net.virtualinfinity.atrobots.gui.renderers.SimpleExplosionRenderer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public abstract class ArenaWindowBuilder {
    protected JFrame mainFrame;
    protected JMenuBar menubar;
    protected ArenaPane arenaPane;
    protected RobotStatusPane robotStatusPane;
    protected RobotRenderer robotRenderer;
    protected ScanRenderer scanRenderer;
    protected final ToggleProperty toggleRobotStatusBars;
    protected final ToggleProperty toggleRenderDeadRobots;
    protected final ToggleProperty toggleFillScanArc;
    protected final ToggleProperty toggleFillShields;
    protected final ToggleProperty toggleShowNames;

    protected ArenaWindowBuilder() {
        toggleRobotStatusBars = new ToggleProperty("Robot Status Bars", new ShowStatusBarsAccessor());
        toggleRenderDeadRobots = new ToggleProperty("Dead Robots", new ShowDeadRobotsAccessor());
        toggleFillScanArc = new ToggleProperty("Filled Scans", new ShowFilledScansAccessor());
        toggleFillShields = new ToggleProperty("Filled Shields", new ShowFillShieldAccessor());
        toggleShowNames = new ToggleProperty("Label Robots", new ShowRobotNameAccessor());
    }

    protected void initializeWindow() {
        initializeSystemLookAndFeel();
        mainFrame = new JFrame("AT-Robots 2 Clone 0.0.01");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerCloseListener();
        menubar = new JMenuBar();
        mainFrame.setJMenuBar(menubar);
        buildMenuBar();
        robotStatusPane = RobotStatusPane.createRobotStatusPane();
        final JScrollPane robotStatusScroller = new JScrollPane(robotStatusPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainFrame.getContentPane().add(robotStatusScroller, BorderLayout.EAST);
        arenaPane = new ArenaPane();
        mainFrame.getContentPane().add(arenaPane, BorderLayout.CENTER);
        arenaPane.setBackground(Color.black);
        arenaPane.setOpaque(true);
        arenaPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.gray, Color.darkGray));
        arenaPane.setPreferredSize(new Dimension(500, 500));
        arenaPane.setRobotStatusPane(robotStatusPane);
        robotRenderer = new RobotRenderer();
        scanRenderer = new ScanRenderer();
        arenaPane.getArenaRenderer().setRobotRenderer(robotRenderer);
        arenaPane.getArenaRenderer().setScanRenderer(scanRenderer);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLocationByPlatform(true);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    protected abstract void buildMenuBar();

    protected abstract void registerCloseListener();

    protected JMenu createViewMenu() {
        final JMenu viewMenu = new JMenu("View");
        viewMenu.add(toggleShowNames.configure(new JCheckBoxMenuItem()));
        viewMenu.add(toggleRobotStatusBars.configure(new JCheckBoxMenuItem()));
        viewMenu.add(toggleFillShields.configure(new JCheckBoxMenuItem()));
        viewMenu.add(toggleRenderDeadRobots.configure(new JCheckBoxMenuItem()));
        viewMenu.addSeparator();
        viewMenu.add(toggleFillScanArc.configure(new JCheckBoxMenuItem()));
        viewMenu.add(new JCheckBoxMenuItem(new AbstractAction("Gradiant Explosions") {
            public void actionPerformed(ActionEvent e) {
                AbstractButton aButton = (AbstractButton) e.getSource();
                boolean selected = aButton.getModel().isSelected();
                arenaPane.getArenaRenderer().setExplosionRenderer(selected ? new GradientExplosionRenderer() : new SimpleExplosionRenderer());
            }
        }));
        return viewMenu;
    }

    private void initializeSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
    }

    private abstract class RepaintArenaAfterSetBooleanAccessor implements BooleanAccessor {
        public final void set(boolean value) {
            doSet(value);
            arenaPane.repaint();
        }

        protected abstract void doSet(boolean value);
    }

    private class ShowStatusBarsAccessor extends RepaintArenaAfterSetBooleanAccessor {
        public boolean get() {
            return robotRenderer.isShowStatusBars();
        }

        public void doSet(boolean value) {
            robotRenderer.setShowStatusBars(value);
        }
    }

    private class ShowFillShieldAccessor extends RepaintArenaAfterSetBooleanAccessor {
        public boolean get() {
            return robotRenderer.isFillShield();
        }

        public void doSet(boolean value) {
            robotRenderer.setFillShield(value);
        }
    }

    private class ShowRobotNameAccessor extends RepaintArenaAfterSetBooleanAccessor {
        public boolean get() {
            return robotRenderer.isShowName();
        }

        public void doSet(boolean value) {
            robotRenderer.setShowName(value);
        }
    }


    private class ShowDeadRobotsAccessor extends RepaintArenaAfterSetBooleanAccessor {
        public boolean get() {
            return robotRenderer.isRenderDead();
        }

        public void doSet(boolean value) {
            robotRenderer.setRenderDead(value);
        }
    }

    private class ShowFilledScansAccessor extends RepaintArenaAfterSetBooleanAccessor {
        public boolean get() {
            return scanRenderer.isFillArcs();
        }

        public void doSet(boolean value) {
            scanRenderer.setFillArcs(value);
        }
    }
}
