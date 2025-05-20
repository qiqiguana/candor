package net.virtualinfinity.atrobots.gui;

import net.virtualinfinity.atrobots.arena.SimulationFrame;
import net.virtualinfinity.atrobots.arena.SimulationFrameBuffer;
import net.virtualinfinity.atrobots.arena.SimulationObserver;
import net.virtualinfinity.atrobots.snapshots.RobotSnapshot;
import net.virtualinfinity.atrobots.snapshots.SnapshotAdaptor;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class RobotStatusPane extends JList implements SimulationObserver {
    private final DefaultListModel robotList;
    private final List<RobotItem> reorderedList = new ArrayList<RobotItem>();
    private final Map<Integer, RobotItem> items = new HashMap<Integer, RobotItem>();

    private RobotStatusPane() {
        robotList = new DefaultListModel();
        setModel(robotList);
        this.setCellRenderer(new RobotStatusRenderer());
        final RobotSnapshot robotSnapshot = new RobotSnapshot();
        robotSnapshot.setName("SUPER DUPER LONG NAME!");
        robotSnapshot.setLastMessage("VERY LONG ERROR MESSAGE FOR THIS ROBOT");
        robotSnapshot.setTotalKills(10000000);
        this.setPrototypeCellValue(new RobotItem(robotSnapshot));
    }

    public void frameAvailable(final SimulationFrameBuffer frameBuffer) {
        EventQueue.invokeLater(new UpdateRobotList(frameBuffer));
    }

    private void updateRobotStatus(RobotSnapshot robotSnapshot) {
        final RobotItem robotItem = items.get(robotSnapshot.getId());
        if (robotItem == null) {
            addItem(new RobotItem(robotSnapshot));
        } else {
            robotItem.setRobotSnapshot(robotSnapshot);

        }
        revalidate();
    }

    private void addItem(RobotItem robotItem) {
        items.put(robotItem.getId(), robotItem);
        robotList.addElement(robotItem);
    }

    public static RobotStatusPane createRobotStatusPane() {
        return new RobotStatusPane();
    }

    public Set<Integer> getSelectedRobotIds() {
        final Object[] objects = this.getSelectedValues();
        final Set<Integer> selectedIds = new HashSet<Integer>();
        for (Object o : objects) {
            selectedIds.add(((RobotItem) o).getId());

        }
        return selectedIds;
    }

    public void reset() {
        robotList.removeAllElements();
    }

    private static class Bar extends JComponent {
        private BoundedRangeModel model;
        private final ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                repaint();
            }
        };
        private float[] foregroundGradientFractions;
        private Color[] foregroundGradientColors;

        @Override
        protected void paintComponent(Graphics g) {
            final Graphics2D g2d = (Graphics2D) g;
            final Insets insets = getInsets(null);
            final int maxWidth = getWidth() - insets.left - insets.right;
            final int maxHeight = getHeight() - insets.top - insets.bottom;
            if (foregroundGradientFractions != null) {
                g2d.setPaint(new LinearGradientPaint(insets.left, 0, getWidth() - insets.right, 0, foregroundGradientFractions, foregroundGradientColors));
            } else {
                g2d.setPaint(getForeground());
            }
            g2d.fillRect(insets.left, insets.top, maxWidth * model.getValue() / model.getMaximum(), maxHeight);
            g2d.drawRect(insets.left, insets.top, maxWidth - 1, maxHeight - 1);
        }

        public float[] getForegroundGradientFractions() {
            return foregroundGradientFractions;
        }

        public void setForegroundGradientFractions(float[] foregroundGradientFractions) {
            this.foregroundGradientFractions = foregroundGradientFractions;
        }

        public Color[] getForegroundGradientColors() {
            return foregroundGradientColors;
        }

        public void setForegroundGradientColors(Color[] foregroundGradientColors) {
            this.foregroundGradientColors = foregroundGradientColors;
        }

        private Bar() {
            setModel(new DefaultBoundedRangeModel());
        }

        public void setModel(BoundedRangeModel model) {
            if (this.model != null) {
                model.removeChangeListener(changeListener);
            }
            model.addChangeListener(changeListener);
            this.model = model;
        }

        public int getMinimum() {
            return getModel().getMinimum();
        }

        public void setMinimum(int newMinimum) {
            getModel().setMinimum(newMinimum);
        }

        public int getMaximum() {
            return getModel().getMaximum();
        }

        public void setMaximum(int newMaximum) {
            getModel().setMaximum(newMaximum);
        }

        public int getValue() {
            return getModel().getValue();
        }

        public void setValue(int newValue) {
            getModel().setValue(newValue);
        }

        public void setValueIsAdjusting(boolean b) {
            getModel().setValueIsAdjusting(b);
        }

        public boolean getValueIsAdjusting() {
            return getModel().getValueIsAdjusting();
        }

        public int getExtent() {
            return getModel().getExtent();
        }

        public void setExtent(int newExtent) {
            getModel().setExtent(newExtent);
        }

        public BoundedRangeModel getModel() {
            return model;
        }
    }

    private static class RobotStatusRenderer extends JPanel implements ListCellRenderer {
        private final JLabel name = new JLabel();
        private final JLabel roundKills = new JLabel();
        private final JLabel gameStats = new JLabel();
        private final Bar armor = new Bar();
        private final Bar heat = new Bar();
        private final JLabel lastMessage = new JLabel();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            final Color background = isSelected ? Color.blue : Color.black;
            setBackground(background);
            setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.lightGray, Color.darkGray));
            final Color foreground = isSelected ? Color.yellow : Color.gray;
            armor.setForegroundGradientFractions(new float[]{0, .25f, 1});
            armor.setForegroundGradientColors(new Color[]{new Color(0, 0, .25f), Color.blue, Color.green});
            heat.setForegroundGradientFractions(new float[]{0, .25f, 1});
            heat.setForegroundGradientColors(new Color[]{new Color(.2f, 0, 0), Color.red, Color.yellow});
            name.setForeground(foreground);
            roundKills.setForeground(foreground);
            gameStats.setForeground(foreground);
            lastMessage.setForeground(foreground);
            removeAll();
            setLayout(new GridLayout(6, 1, 0, 1));
            add(name);
            add(roundKills);
            add(armor);
            add(heat);
            add(gameStats);
            add(lastMessage);
            final RobotItem item = (RobotItem) value;
            final RobotSnapshot robotSnapshot = item.getRobotSnapshot();
            roundKills.setText("Kills (round/total): " + robotSnapshot.getRoundKills() + "/" + robotSnapshot.getTotalKills());
            gameStats.setText("Wins/Ties/Deaths: " + robotSnapshot.getTotalWins() + "/" + robotSnapshot.getTotalTies() + "/" + robotSnapshot.getTotalDeaths());
            if (!item.isDead()) {
                armor.setValue((int) Math.round(Math.min(100, robotSnapshot.getArmor())));
                heat.setValue((int) Math.round(Math.min(100, robotSnapshot.getTemperature().getLogScale() * .2)));
                name.setText(robotSnapshot.getName());
            } else {
                armor.setValue(0);
                heat.setValue(0);
                name.setText("<html><strike>" + robotSnapshot.getName() + "</html></strike>");
            }
            lastMessage.setText(robotSnapshot.getLastMessage() == null ? "" : robotSnapshot.getLastMessage());
            return this;
        }
    }

    private class UpdateRobotList implements Runnable {
        private final SimulationFrameBuffer frameBuffer;

        public UpdateRobotList(SimulationFrameBuffer frameBuffer) {
            this.frameBuffer = frameBuffer;
        }

        public void run() {
            final SimulationFrame currentFrame = frameBuffer.getCurrentFrame();
            currentFrame.visitRobots(new SnapshotAdaptor() {
                @Override
                public void acceptRobot(RobotSnapshot robotSnapshot) {
                    updateRobotStatus(robotSnapshot);
                }
            });

            if (currentFrame.isRoundOver()) {
                reorderedList.clear();
                reorderedList.addAll(items.values());
                Collections.sort(reorderedList);
                robotList.setSize(reorderedList.size());
            }
            for (int i = 0; i < reorderedList.size(); ++i) {
                final RobotItem existingItem = (RobotItem) robotList.get(i);
                final RobotItem reorderedItem = reorderedList.get(i);
                if (reorderedItem.isChanged() || existingItem != reorderedItem) {
                    robotList.set(i, reorderedItem);
                }
            }
        }
    }

    private static class RobotItem implements Comparable<RobotItem> {
        private RobotSnapshot robotSnapshot;
        private boolean dead;
        private boolean updated;
        private boolean changed;

        private RobotItem() {
        }

        public RobotItem(RobotSnapshot robotSnapshot) {
            setRobotSnapshot(robotSnapshot);
        }

        public RobotSnapshot getRobotSnapshot() {
            return robotSnapshot;
        }

        public boolean isUpdated() {
            return updated;
        }

        public void clearUpdated() {
            updated = false;
            changed = false;
        }

        public void setRobotSnapshot(RobotSnapshot robotSnapshot) {
            changed = this.robotSnapshot == null || !this.robotSnapshot.equals(robotSnapshot);
            this.updated = true;
            this.dead = robotSnapshot.getArmor() <= 0;
            this.robotSnapshot = robotSnapshot;
        }

        public boolean isChanged() {
            return changed;
        }

        public boolean isDead() {
            return dead;
        }

        public int getId() {
            return getRobotSnapshot().getId();
        }

        public int compareTo(RobotItem o) {
            if (robotSnapshot == null && o.robotSnapshot == null) {
                return 0;
            }
            if (robotSnapshot == null) {
                return -1;
            }
            if (o.robotSnapshot == null) {
                return 1;
            }
            return -compare(robotSnapshot.getTotalWins(), o.robotSnapshot.getTotalWins(), 2) +
                    -compare(robotSnapshot.getTotalTies(), o.robotSnapshot.getTotalTies(), 1);
        }

        private int compare(int left, int right, int priority) {
            return left == right ? 0 : left < right ? -priority : priority;
        }
    }
}
