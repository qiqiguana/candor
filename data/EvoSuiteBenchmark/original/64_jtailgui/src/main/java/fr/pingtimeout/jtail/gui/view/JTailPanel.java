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

import fr.pingtimeout.jtail.gui.controller.JTailSizeController;
import fr.pingtimeout.jtail.gui.controller.ScrollBarAdjustmentController;
import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class JTailPanel extends JPanel implements Observer {
    final JTailModel model;

    final JScrollPane scrollPane;
    final JScrollBar verticalScrollBar;
    final JTable dataTable;
    final DefaultTableModel tableModel;
    final int characterHeight;
    private int maxCharacterWidth;

    public JTailPanel(JTailModel model) {
        JTailLogger.debug("Building a new JTailPanel"); //NON-NLS
        this.model = model;
        this.model.addObserver(this);

        this.verticalScrollBar = new JScrollBar();
        this.tableModel = new DefaultTableModel(new String[]{"#", ""}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        this.dataTable = new JTable(this.tableModel);
        this.scrollPane = new JScrollPane(this.dataTable);

        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.verticalScrollBar.addAdjustmentListener(
                new ScrollBarAdjustmentController(this, this.model));
        this.addComponentListener(
                new JTailSizeController(this, this.model));

        this.setLayout(new BorderLayout());
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.verticalScrollBar, BorderLayout.EAST);

        final FontMetrics metrics = this.dataTable.getFontMetrics(this.dataTable.getFont());
        this.characterHeight = metrics.getHeight() - 1;
        this.maxCharacterWidth = metrics.stringWidth("0") + 2;
        // FIXME = ... +2 so that the overall column decoraction doesn't prevent the line numbers to be displayed
    }

    public int getCharacterHeight() {
        return characterHeight;
    }

    public JScrollBar getVerticalScrollBar() {
        return verticalScrollBar;
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable == this.model) {
            JTailModelEvent event = (JTailModelEvent) arg;

            JTailLogger.debug("JTailPanel just received a notification : " //NON-NLS
                    + arg);

            if (event.type == JTailModelEvent.Type.RESIZED) {
                JTailLogger.debug("The window has beed resized"); //NON-NLS

                // Mettre à jour la scrollbar
                final BoundedRangeModel rangeModel = this.verticalScrollBar.getModel();
                rangeModel.setValueIsAdjusting(true);
                rangeModel.setMinimum(event.minLine);
                rangeModel.setValue(event.firstLine);
                rangeModel.setExtent(event.nbDisplayedLines);
                rangeModel.setMaximum(event.maxLine);
                rangeModel.setValueIsAdjusting(false);

                if (event.minLine == event.firstLine && event.maxLine == event.nbDisplayedLines) {
                    // Si tout le contenu du fichier est affiché, désactiver la scrollbar
                    this.verticalScrollBar.setEnabled(false);
                } else if (!this.verticalScrollBar.isEnabled()) {
                    // Sinon, si la scrollbar est désactivée, la réactiver
                    this.verticalScrollBar.setEnabled(true);
                }

                // Mettre à jour le contenu du TextPane
                updateDataTable(this.model.getLineNumbers(), this.model.getContent());
            } else if (event.type == JTailModelEvent.Type.SCROLLED) {
                JTailLogger.debug("The user scrolled the file"); //NON-NLS

                final String lineNumbers = this.model.getLineNumbers();

                // Mettre à jour le contenu du TextPane
                updateDataTable(this.model.getLineNumbers(), this.model.getContent());
            }
        }
    }

    private void updateDataTable(String lineNumbers, String lines) {
        // Clear model
        while (this.tableModel.getRowCount() > 0) {
            this.tableModel.removeRow(0);
        }

        String[] lineNumbersArray = lineNumbers.split("\n");
        String[] linesArray = lines.split("\n");

        for (int i = 0; i < lineNumbersArray.length; i++) {
            String lineNumberTmp = lineNumbersArray[i];
            String lineTmp = i < linesArray.length ? linesArray[i] : "";
            this.tableModel.addRow(new String[]{
                    lineNumberTmp,
                    lineTmp
            });
        }

        this.dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JTailLogger.debug("Resizing column widths");
        JTailLogger.debug("DataTable width : {}", this.dataTable.getWidth());
        JTailLogger.debug("Highest line number : {}", lineNumbersArray[lineNumbersArray.length - 1]);

        final TableColumn numbersColumn = this.dataTable.getColumnModel().getColumn(0);
        final TableColumn linesColumn = this.dataTable.getColumnModel().getColumn(1);
        final int numbersColumnWidth = lineNumbersArray[lineNumbersArray.length - 1].length() * this.maxCharacterWidth;
        final int linesColumnWidth = this.dataTable.getWidth() - numbersColumnWidth;

        JTailLogger.debug("numbersColumnWidth : {}", numbersColumnWidth);
        JTailLogger.debug("linesColumnWidth : {}", linesColumnWidth);

        numbersColumn.setPreferredWidth(numbersColumnWidth);
        linesColumn.setPreferredWidth(linesColumnWidth);
    }
}
