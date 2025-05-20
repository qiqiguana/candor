/*
 * @(#)FrameMediator.java
 * Created on 2005-8-1
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package com.allenstudio.ir.core;

/**
 * According to the book "Design Patterns",
 * in order to decouple various components,
 * a "Mediator" pattern is employed here. This
 * class defines the basic behavior that a
 * mediator should has. Other objects will recognize
 * their own mediator as their instance variable.
 * When these objects' state changes, they will invoke
 * the widgetChanged() method to interact with the
 * mediator. So, in other words, classes communicate with
 * each other not via the complicated connections among them,
 * but via a single mediator. And the mediator might have
 * several implementations in different situations. 
 * 
 * @author Allen Chue
 */
public interface InspirentoMediator {
    
    /**
     * When a widget in Inspirento changes its state,
     * this method should be invoked. That widget should
     * transmit its own reference accompanied.
     * @param iw the widget that has its state changed
     */
    public void widgetChanged(InspirentoWidget iw);
    
}
