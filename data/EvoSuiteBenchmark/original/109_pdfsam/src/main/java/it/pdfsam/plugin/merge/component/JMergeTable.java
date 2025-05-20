/*
 * Created on 07-Mar-2006
 *
 * Copyright (C) 2006 by Andrea Vacondio.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; 
 * either version 2 of the License.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, write to the Free Software Foundation, Inc., 
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package it.pdfsam.plugin.merge.component;

import java.awt.Container;

import javax.swing.JTable;
import javax.swing.JViewport;
/**
 * Code snippet found on forum.java.sun by Deudeu.
 * @author Andrea Vacondio
 *
 */
public class JMergeTable extends JTable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3191192690780868897L;
    
    public JMergeTable(){
        super();
        setToolTipText("ALT-ARROWUP, ALT-ARROWDOWN, CANC");
    }

        public boolean getScrollableTracksViewportWidth() {
            if (autoResizeMode == AUTO_RESIZE_OFF) {
                return(false);
            } 
            else {
                Container p = getParent();
                if (p instanceof JViewport) {
                        if (p.getSize().getWidth()<getPreferredSize().getWidth()) {
                            return(false);
                        } else {
                            return(true);
                        }
                }
                return(false);
            }
        }


} 
