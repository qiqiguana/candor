/* $Id: GLWorldPanel.java,v 1.3 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package gui.gl;

import gui.GUIFrame;
import module.GUIModule;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;

/**
 * This class serves as a container for the XithMapRenderer.
 *
 */
public class GLWorldPanel extends JPanel implements Observer {
/* implements WorldViewerInterface, WorldControllerInterface */

    /** */
    GUIModule source;

    /** */
    GUIFrame frame;

    /** Renderer used to render map. */
    private XithMapRenderer renderer;

    /** Xith3D Renderer Thread. */
    private Thread renderThread = 
	new Thread() {
	    public void run() {
		renderer.run();
	    }
	};

    /**
     * Creates a new GLWorldPanel instance.
     * @param mod the module used as source.
     */
    public GLWorldPanel(GUIModule source, GUIFrame frame) {
	this.source = source;
	this.frame = frame;
	renderer = new XithMapRenderer(this, 512, 384);
	renderer.init();
	renderThread.start();
    }

    public void update(Observable obs, Object obj) {
    }
}
