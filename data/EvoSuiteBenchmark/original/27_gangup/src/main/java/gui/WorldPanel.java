/* $Id: WorldPanel.java,v 1.5 2004/04/01 10:34:09 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Emil Lundström <emill@kth.se>
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.5 $
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

/**
 * This class provides a container for the world display.
 */
public class WorldPanel extends JScrollPane implements Observer {

    /** The module from which to receive events. */
    protected GUIModule source;

    /** The currently active map. */
    protected Map map;

    /** The gui frame this worldpanel belongs to. */
    protected GUIFrame frame;
    

    /** Offscreen image which we use to draw the map on. */
    protected BufferedImage[] img = new BufferedImage[Map.N_LAYERS];

    protected Graphics2D[] gfx = new Graphics2D[Map.N_LAYERS];

    /** Keep a reference to the vieport for easy access.*/
    private JViewport view;

    /** Navigation speed. */
    private float speed = 15.0f;

    /** Mouse coordinates in viewport. */
    private int mouseX = 0;
    private int mouseY = 0;

    private int startDragX;
    private int startDragY;

    /** True if we're in navigation mode. */
    private boolean navigating = false;

    /** Direction of navigation when in navigation mode. */
    private double theta = 0;

    // DEBUG
    private Map.MapCell playercell;
    private Point target;

    /**
     * This class provides all mouse handling for the viewer.
     *
     */
    private class WorldMouseListener extends MouseAdapter 
	implements MouseMotionListener {

	public void mouseClicked(MouseEvent ev) {
	    int button = ev.getButton();
	    Point p = view.getViewPosition();

	    Point s = new Point(p.x + ev.getX(),p.y + ev.getY());
	    Point g = Map.gridTransform(s.x, s.y);
	    
	    if (button == MouseEvent.BUTTON3) {
		
		target = g;
		
	    } else if (button == MouseEvent.BUTTON1) {
		Map.MapCell c = map.lookup(g.x,g.y);
		if (c instanceof Map.MapPlayerCell) {
		    frame.guistate.setSelected( ((Map.MapPlayerCell)c).p );
		}
	    }
	}

	public void mousePressed(MouseEvent ev) {
	    if (ev.getButton() == MouseEvent.BUTTON2) {
		startDragX = ev.getX();
		startDragY = ev.getY();
		navigating = true;
	    }
	}

	public void mouseReleased(MouseEvent ev) {
	    int button = ev.getButton();
	    navigating = false;
	}

	public void mouseMoved(MouseEvent ev) {	    
	    mouseX = ev.getX();
	    mouseY = ev.getY();
	}

	public void mouseDragged(MouseEvent ev) {
	    mouseX = ev.getX();
	    mouseY = ev.getY();
	}
    }

    /**
     * This class provides a canvas on which to draw the map. The WorldView
     * implments Runnable and runs an animation thread.
     */
    private class WorldView extends Component implements Runnable {

	private Dimension dim;
	private Polygon arrow;
	private Thread animator;

	/**
	 * Create a new instance of the WorldView class.
	 *
	 */
	public WorldView() {

	    dim = new Dimension(640,480);
	    for (int i=0; i < Map.N_LAYERS; i++) {
		img[i] = new BufferedImage
		    (640,480,BufferedImage.TYPE_INT_ARGB);
		gfx[i] = (Graphics2D) img[i].getGraphics();
	    }

	    // fill background layer with clear colour
	    gfx[0].setColor(Color.decode("#2271bc"));
	    gfx[0].fillRect(0,0,640,480);
	    
	    // create the arrow polygon 

	    arrow = new Polygon();
	    arrow.addPoint(  0,  5);
	    arrow.addPoint(  0, 10);
	    arrow.addPoint( 15,  0);
	    arrow.addPoint(  0,-10);
	    arrow.addPoint(  0, -5);
	    arrow.addPoint(-15, -5);
	    arrow.addPoint(-15,  5);

	    try {
		map = new Map("dat/maps/ist.map");
		for (int i=0; i < Map.N_LAYERS; i++) {
		    updateLayer(i);
		}
	    } catch (IOException e) {
		e.printStackTrace();	// fixme - this should not be ignored!
	    }

	    // start the animator. 

	    animator = new Thread(this);
	    animator.start();
	}

	

	/**
	 * The animation thread function.
	 *
	 */
	public void run() {
	    try {
		int i = 0;
		while (true) {
		    if (navigating) {
			performNavigation();
		    }
		    // redraw animation every 2 frames
		    if (i++ >= 1) {

		       
			if (frame.state != null && target != null &&
			    frame.state.getMe() != null) {
			    
			    int dir = 0;
			    Player me = frame.state.getMe();
			    //System.err.println("### ME: ("+me.getX() + "," +
				//	       me.getY() + ")");
			    //System.err.println("### TRG: ("+target.x + "," +
				//	       target.y + ")");
			    
			    // calculate what direction to move in
			    if (me.getY() > target.y+Player.TOLERANCE) {
				dir += 0x1;
			    }
			    if (me.getX() > target.x+Player.TOLERANCE) {
				dir += 0x2;
			    }
			    if (me.getY() < target.y-Player.TOLERANCE) {
				dir += 0x4;
			    }
			    if (me.getX() < target.x-Player.TOLERANCE) {
				dir += 0x8;
			    }
			    if (dir != 0) {
				source.sendMoveAction(dir);
			    }

			    //System.err.println("### DIR:" + dir);
			}
			
			updateLayer(Map.PLAYER_LAYER);
			i=0;
		    }
		    repaint();
		    Thread.sleep(100);
		}
	    } catch (InterruptedException e) {
	    }
	}

	/**
	 * Scroll the view port in the direction of the vector from center
	 * of the viewport to the position of the mouse, with a speed 
	 * proportional to the distance.
	 */
	private void performNavigation() {

	    // get the dimension and location of the viewport.

	    Dimension d = view.getExtentSize();
	    Point p = view.getViewPosition();

	    // normalize the viewport space, and find the mouse coords.
		    
	    //float s = (float)(2 * mouseX) / d.width  - 1;
	    //float t = (float)(2 * mouseY) / d.height - 1;

	    float s = (float)(mouseX-startDragX) / d.width;
	    float t = (float)(mouseY-startDragY) / d.height;

	    // calculate the speed with which to scroll.

	    float v = (float)(speed * sqrt(s*s + t*t));

	    // Calculate the direction.

	    theta = (float)(atan2(-t, s));
	    
	    // Calculate and update new viewport location.

	    int x = (int)(v*cos(theta));
            int y = (int)(v*sin(theta));
	    
            if (x < 0 && p.x <= 0 || x > 0 && p.x >= 640 - d.width) { x = 0; }
            if (y > 0 && p.y <= 0 || y < 0 && p.y >= 480 - d.height) { y = 0; }

            p.y -= y;
            p.x += x;

	    view.setViewPosition(p);
	}

	public void update(Graphics g) {
	    paint(g); // eliminate flickering.
	}

	public void paint(Graphics g) {

	    Graphics2D g2 = (Graphics2D) g;
	    for (int i=0; i < Map.N_LAYERS; i++) {
		g2.drawImage(img[i],0,0,null);
	    }

	    // if were in navigation mode draw an arrow pointing
	    // in the direction of motion.

	    if (navigating) {

		Dimension d = view.getExtentSize();
		Point p = view.getViewPosition();

		AffineTransform aft = new AffineTransform();

		double dist = sqrt((mouseX-startDragX) * (mouseX-startDragX) +
				   (mouseY-startDragY) * (mouseY-startDragY));

		// draw filled shapes

		g2.setColor(Color.YELLOW);
		g2.fillOval(p.x + startDragX - 5, 
			    p.y + startDragY - 5, 10, 10);

		if (dist > 25) {

		    aft.translate(p.x + startDragX, p.y + startDragY);
		    aft.rotate(-theta);
		    aft.translate((int)(0.25 * dist) + 25, 0);
		    g2.fill(aft.createTransformedShape(arrow));

		    g2.setColor(Color.BLACK);
		    g2.draw(aft.createTransformedShape(arrow));

		} else {
		    g2.setColor(Color.BLACK);
		}

		g2.drawOval(p.x + startDragX - 5, 
			    p.y + startDragY - 5, 10, 10);
	    }
	}

	public Dimension getPreferredSize() {
	    return dim;
	}

	public Dimension getMinimumSize() {
	    return dim;
	}
    }

    public synchronized void updateLayer(int layer) {
	map.sortCells(layer);
	gfx[layer].setComposite(AlphaComposite.
				getInstance(AlphaComposite.CLEAR, 0.0f));
	gfx[layer].fillRect(0,0,640,480);
	gfx[layer].setComposite(AlphaComposite.SrcOver);
	map.draw(gfx[layer],layer);
	
    }

    /**
     * Update defined in Observer interface.
     *
     * @param o The observable object.
     * @param arg The argument passed to notifyObservers.
     */
    public void update(Observable o, Object arg) {
	if (o instanceof GUIState) {
	    updateLayer(Map.PLAYER_LAYER);
	}
	else if (o instanceof GameState) {
	    if (arg instanceof Player) {
		map.updatePlayerCell((Player)arg);
	    }
	    else if (arg instanceof GameState) {
		GameState g = (GameState) arg;
		for (Player p : g.players()) {
		    if (p != null) 
			map.addPlayerCell(p);
		}
	    }
	}
    }


    
    /**
     * Creates a new WorldPanel. 
     * @param mod the event source for this class.
     */
    public WorldPanel(GUIModule mod, GUIFrame frm) {

	frame = frm;
	source = mod;

	setViewportView(new WorldView());
	view = getViewport();

	WorldMouseListener ml = new WorldMouseListener();

	view.addMouseListener(ml);
	view.addMouseMotionListener(ml);

    }
}
