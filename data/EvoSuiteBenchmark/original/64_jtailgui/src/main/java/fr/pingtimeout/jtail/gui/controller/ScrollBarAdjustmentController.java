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

package fr.pingtimeout.jtail.gui.controller;

import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.view.JTailPanel;
import fr.pingtimeout.jtail.util.JTailLogger;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by IntelliJ IDEA.
 * User: plaporte
 * Date: 18 avr. 2010
 * Time: 23:42:52
 * To change this template use File | Settings | File Templates.
 */
public class ScrollBarAdjustmentController implements AdjustmentListener {
    private final JTailPanel panel;
    private final JTailModel model;

    public ScrollBarAdjustmentController(JTailPanel panel, JTailModel model) {
        this.panel = panel;
        this.model = model;
        JTailLogger.debug("Creating AdjustmentController for panel {} and model {}" //NON-NLS
                , this.panel, this.model);
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        model.setFirstDisplayedLine(adjustmentEvent.getValue());
    }
}
