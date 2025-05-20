/* $Id: WorldGLPanel.java,v 1.1 2004/05/01 22:12:42 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Emil Lundström <emill@kth.se>
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.1 $
 *
 */

package gui;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

import module.GUIModule;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

import java.io.IOException;

import state.*;
import gui.gl.*;

/**
 * This class provides a container for the world display.
 */
public class WorldGLPanel extends JPanel {

    /** The module from which to receive events. */
    protected GUIModule source;

    /** The gui frame this worldpanel belongs to. */
    protected GUIFrame frame;

    /** The GL scene canvas. */
    protected XithMapRenderer xmr;

    public XithMapRenderer getXMR() {
	return xmr;
    }
    
    /**
     * Creates a new WorldPanel. 
     * @param mod the event source for this class.
     */
    public WorldGLPanel(GUIModule mod, GUIFrame frm) {
	frame = frm;
	source = mod;
	xmr = new XithMapRenderer(this,512,384);

    }

    public XithMapRenderer startXithRenderer() {
	xmr.init();
	new Thread(xmr).start();
	return xmr;
    }
}
