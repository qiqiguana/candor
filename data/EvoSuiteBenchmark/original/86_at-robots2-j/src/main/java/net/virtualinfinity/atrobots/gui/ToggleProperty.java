package net.virtualinfinity.atrobots.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public class ToggleProperty {
    private final Action action;

    private ToggleProperty(Action action) {
        this.action = action;
    }

    public ToggleProperty(BooleanAccessor booleanAccessor) {
        this(new Action(booleanAccessor));
    }

    public ToggleProperty(String name, BooleanAccessor booleanAccessor) {
        this(new Action(name, booleanAccessor));
    }

    public ToggleProperty(String name, Icon icon, BooleanAccessor booleanAccessor) {
        this(new Action(name, icon, booleanAccessor));
    }

    private void doConfigure(AbstractButton button) {
        button.setModel(action.model);
        button.setAction(action);
    }

    public <T extends JToggleButton> T configure(T button) {
        doConfigure(button);
        return button;
    }

    public <T extends JCheckBoxMenuItem> T configure(T button) {
        doConfigure(button);
        return button;
    }


    public Object getValue(String key) {
        return action.getValue(key);
    }

    public void putValue(String key, Object newValue) {
        action.putValue(key, newValue);
    }

    public boolean isEnabled() {
        return action.isEnabled();
    }

    public void setEnabled(boolean newValue) {
        action.setEnabled(newValue);
    }

    public Object[] getKeys() {
        return action.getKeys();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        action.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        action.removePropertyChangeListener(listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return action.getPropertyChangeListeners();
    }

    private static class Action extends AbstractAction {
        private final JToggleButton.ToggleButtonModel model = new JToggleButton.ToggleButtonModel() {
            @Override
            public void setSelected(boolean b) {
                super.setSelected(b);
                booleanAccessor.set(b);
            }

            @Override
            public boolean isSelected() {
                return booleanAccessor.get();
            }
        };
        private final BooleanAccessor booleanAccessor;

        private Action(BooleanAccessor booleanAccessor) {
            this.booleanAccessor = booleanAccessor;
        }

        private Action(String name, BooleanAccessor booleanAccessor) {
            super(name);
            this.booleanAccessor = booleanAccessor;
        }

        private Action(String name, Icon icon, BooleanAccessor booleanAccessor) {
            super(name, icon);
            this.booleanAccessor = booleanAccessor;
        }

        public void actionPerformed(ActionEvent e) {
        }
    }
}
