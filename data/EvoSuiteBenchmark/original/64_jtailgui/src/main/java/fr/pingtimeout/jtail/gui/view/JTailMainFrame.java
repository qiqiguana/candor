/*
 * Copyright (c) 2010 Pierre Laporte.
 *
 * This file is part of JTailPlus.
 *
 * JTailPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTailPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTailPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.pingtimeout.jtail.gui.view;

import fr.pingtimeout.jtail.gui.action.*;
import fr.pingtimeout.jtail.gui.model.JTailMainModel;
import fr.pingtimeout.jtail.gui.model.JTailMainModelEvent;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class JTailMainFrame extends JFrame implements Observer {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    final JTailMainModel model;

    final JPanel mainPanel;
    final JTabbedPane tabbedPane;

    final JMenuBar menuBar;
    final JMenu fileMenu;
    final JMenu toolsMenu;
    final JToolBar toolBar;

    final JMenuItem openMenuItem;
    final JMenuItem closeMenuItem;
    final JMenuItem closeAllMenuItem;
    final JMenuItem quitMenuItem;
    final JMenuItem highlightMenuItem;
    final JButton openButton;
    final JButton closeButton;
    final JButton closeAllButton;
    final JButton highlightButton;

    final MenuAction fileAction;
    final OpenFileAction openFileAction;
    final CloseAction closeAction;
    final CloseAllAction closeAllAction;
    final QuitAction quitAction;
    final MenuAction toolsAction;
    final HighlightAction highlightAction;

    public JTailMainFrame(JTailMainModel jTailMainModel,
                          MenuAction fileAction,
                          OpenFileAction openFileAction,
                          CloseAction closeAction,
                          CloseAllAction closeAllAction,
                          QuitAction quitAction,
                          MenuAction toolsAction,
                          HighlightAction highlightAction) {
        super(bundle.getString("jtail.title"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build the model
        this.model = jTailMainModel;
        this.model.addObserver(this);

        // Build the main panels
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setPreferredSize(new Dimension(800, 600));

        // Build the actions
        this.fileAction = fileAction;
        this.openFileAction = openFileAction;
        this.closeAction = closeAction;
        this.closeAllAction = closeAllAction;
        this.quitAction = quitAction;
        this.toolsAction = toolsAction;
        this.highlightAction = highlightAction;

        // Build the menu
        this.fileMenu = new JMenu(this.fileAction);
        this.toolsMenu = new JMenu(this.toolsAction);
        this.menuBar = new JMenuBar();
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.toolsMenu);

        // Build and add the menu items to the menubar
        this.openMenuItem = new JMenuItem(this.openFileAction);
        this.closeMenuItem = new JMenuItem(this.closeAction);
        this.closeAllMenuItem = new JMenuItem(this.closeAllAction);
        this.quitMenuItem = new JMenuItem(this.quitAction);
        this.highlightMenuItem = new JMenuItem(this.highlightAction);
        this.fileMenu.add(this.openMenuItem);
        this.fileMenu.add(this.closeMenuItem);
        this.fileMenu.add(this.closeAllMenuItem);
        this.fileMenu.addSeparator();
        this.fileMenu.add(this.quitMenuItem);
        this.toolsMenu.add(this.highlightMenuItem);

        // Build the toolbar
        this.toolBar = new JToolBar((String) this.fileAction.getValue(MenuAction.NAME));
        this.openButton = new JButton(this.openFileAction);
        this.openButton.setText("");
        this.closeButton = new JButton(this.closeAction);
        this.closeButton.setText("");
        this.closeAllButton = new JButton(this.closeAllAction);
        this.closeAllButton.setText("");
        this.highlightButton = new JButton(this.highlightAction);
        this.highlightButton.setText("");

        this.toolBar.add(this.openButton);
        this.toolBar.add(this.closeButton);
        this.toolBar.add(this.closeAllButton);
        this.toolBar.addSeparator();
        this.toolBar.add(this.highlightButton);

        this.mainPanel.add(this.tabbedPane, BorderLayout.CENTER);
        this.mainPanel.add(this.toolBar, BorderLayout.PAGE_START);
        this.setContentPane(this.mainPanel);
        this.setJMenuBar(this.menuBar);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable == this.model) {
            JTailLogger.debug("JTailMainFrame just received a notification : " //NON-NLS
                    + arg);

            final JTailMainModelEvent event = (JTailMainModelEvent) arg;
            switch (event.type) {
                case CREATED:
                    JTailLogger.debug("Adding a tab for {}", event.model.getFileName()); //NON-NLS
                    JTailPanel panel = new JTailPanel(event.model);

                    this.tabbedPane.add(event.model.getFileName(), panel);
                    this.tabbedPane.setSelectedComponent(panel);
                    break;

                case UPDATED:
                    JTailLogger.debug("Updating ... wait what ?"); //NON-NLS
                    throw new UnsupportedOperationException();
                    //break;

                case REMOVED:
                    JTailLogger.debug("Removing tab {}", event.index); //NON-NLS

                    this.tabbedPane.remove(event.index);
                    break;
            }
        }
    }
}
