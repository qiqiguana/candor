/* $Id: HeadUpDisplay.java,v 1.4 2004/05/04 21:33:58 emill Exp $
 *
 * Based on Jens Lehmann's excellent tutorial found at 
 * http://xith.org/tutes/GettingStarted/html/simple_header.html
 *
 * @author Joel Andersson <bja@kth.se>
 * @version $Revision: 1.4 $
 */

package gui.gl;

import com.xith3d.scenegraph.Node;
import com.xith3d.scenegraph.BoundingSphere;
import com.xith3d.scenegraph.Canvas3D;
import com.xith3d.scenegraph.Shape3D;
import com.xith3d.userinterface.UIOverlay;
import com.xith3d.userinterface.UIOverlayInterface;
import com.xith3d.userinterface.UIWindow;
import com.xith3d.userinterface.UIWindowManager;
import com.xith3d.userinterface.UIPositionedWindow;
import javax.vecmath.Point3f;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JPanel;

/**
 *
 *
 */
public class HeadUpDisplay extends UIWindowManager {

    /** */
    private Canvas3D canvas;
    
    /**
     *
     *
     */
    public HeadUpDisplay(Canvas3D c) {
	super(c);
	this.canvas = c;
    }

    /**
     *
     *
     */
    public UIPositionedWindow addOverlay(UIOverlayInterface o) {

	if (o instanceof Overlay) {
	    ((Overlay) o).setWindowManager(this);
	}

	if (o instanceof NodeOverlay) {
	    ((NodeOverlay) o).setCanvas(canvas);
	}

	setPosition(o, 0, 0);
	setVisible(o, true);

	return super.addOverlay(o);
    }
}

/**
 *
 *
 */
class SwingTextOverlay extends UIWindow {
    
    /** */
    private JTextArea textArea;

    /** */
    private JPanel root;

    /** */
    private Color background;

    /** */
    private Color foreground;

    /**
     *
     * @param width
     * @param height
     */
    public SwingTextOverlay(int width, int height) {
	super(width, height, true, true);
	background = new Color(0.0f,0.0f,0.0f,0.0f);
	foreground = new Color(1.0f,1.0f,1.0f,1.0f);
	textArea = new JTextArea();
	textArea.setForeground(foreground);
	textArea.setBackground(background);
	root = new JPanel();
	root.setLayout(new BorderLayout());
	root.setBackground(background);
	root.setSize(new Dimension(width, height));
	root.add(BorderLayout.CENTER, textArea);
	setRoot(root);
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
	textArea.setText(text);
    }

    public void append(String text) {
	textArea.append(text);
    }

    public void clear() {
	setText("");
    }
}

class TextOverlay implements Overlay {

    public static final Font FONT_PLAIN;
    static { FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 12); }
    private static final Color transparent = new Color(0,0,0,0);

    private UIOverlay overlay;
    private FontMetrics fontMetrics;
    private String text;
    private Font font;
    private Graphics2D gfx = null;
    private int width = 0;
    private int height = 0;
    private UIWindowManager uiWinMgr;
    private Color foreground;
    private Color background;
    private Color shadow;
    private Color outline;

    /**
     * Creates a new TextOverlay with the specified text.
     * @param text the text associated with this overlay.
     */
    TextOverlay(String text) {
	setFont("SansSerif",Font.PLAIN, 12);

	setBackground(transparent);
	setForeground(Color.WHITE);
	setShadow(transparent);
	setOutline(Color.black);

	setText(text);
	setVisible(true);
	setPosition(0,0);
    }

    public void setFont(String name, int style, int size) {
	try {
	    font = new Font(name, style, size);
	    fontMetrics = getFontMetrics(font);
	    overlay.repaint();
	} catch (NullPointerException e) {
	    // text not set, ignore this exception.
	}
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {

	this.text = text;

	int w = fontMetrics.stringWidth(text);
	int h = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

	if (w > width || h > height) {

	    width = w + h; // add space for round edge
	    height = h;

	    overlay = new UIOverlay(width, height+2, true, false) {
		    public void paint(Graphics2D g) {
			int ascent = fontMetrics.getMaxAscent();

			// background
			g.setColor(background);
			g.fillArc(0,0,height,height,90,180);
			g.fillArc(width-height,0,height,height,270,180);
			g.fillRect(height/2,0,width-height,height);

			// border
			/*
			g.setColor(outline);
			g.drawArc(0,0,height-1,height,90,180);
			g.drawArc(width-height,0,height-1,height,270,180);
			g.drawLine(height/2,0,width-height/2,0);
			g.drawLine(height/2,height-1,width-height/2,height-1);
			g.drawArc(2,2,height-3,height-3,90,180);
			g.drawArc(width-height-2,2,height-3,height-3,270,180);
			g.drawLine(height/2,2,width-height/2,2);
			g.drawLine(height/2,height-3,width-height/2,height-3);
			g.setColor(foreground);
			g.drawArc(1,1,height-2,height-2,90,180);
			g.drawArc(width-height-1,1,height-2,height-2,270,180);
			g.drawLine(height/2,1,width-height/2,1);
			g.drawLine(height/2,height-2,width-height/2,height-2);
			*/

			// outlined
			g.setColor(outline);
 			g.drawString(getText(), height/2, ascent-1);
 			g.drawString(getText(), height/2, ascent+1);
 			g.drawString(getText(), height/2-1, ascent);
 			g.drawString(getText(), height/2+1, ascent);

			// shadowed
			g.setColor(shadow);
			g.drawString(getText(), height/2+1, ascent+1);
			g.drawString(getText(), height/2+2, ascent+2);

			// foreground
			g.setColor(foreground);
			g.drawString(getText(), height/2, ascent);
		    }
		};

	    BufferedImage img = overlay.getBackgroundImage();
	    overlay.setBackgroundMode(UIOverlay.BACKGROUND_COPY);
	    overlay.setBackgroundImage(img);
	    gfx = img.createGraphics();

	    if (uiWinMgr != null) {
		uiWinMgr.removeOverlay(this);
		uiWinMgr.addOverlay(this);
	    }
	}

	overlay.repaint();
    }

    public void setForeground(Color color) {
	try {
	    this.foreground = color;
	    overlay.repaint();
	} catch (NullPointerException e) {
	    // text not set, ignore this.
	}
    }

    public void setBackground(Color color) {
	try {
	    this.background = color;
	    overlay.repaint();
	} catch (NullPointerException e) {
	    // text not set, ignore this.
	}
    }

    public void setShadow(Color color) {
	try {
	    this.shadow = color;
	    overlay.repaint();
	} catch (NullPointerException e) {
	    // text not set, ignore this.
	}
    }

    public void setOutline(Color color) {
	try {
	    this.outline = color;
	    overlay.repaint();
	} catch (NullPointerException e) {
	    // text not set, ignore this.
	}
    }

    public void setPosition(int x, int y) {
	if (uiWinMgr != null) {
	    uiWinMgr.setPosition(this, x-width/2, y-height/2);
	} else { 
	    overlay.setPosition(x, y);
	}
    }

    public void setVisible(boolean visible) {
	if (uiWinMgr != null) {
	    uiWinMgr.setVisible(this, visible);
	} else {
	    overlay.setVisible(visible);
	}
    }

    public void setWindowManager(UIWindowManager mgr) {
	this.uiWinMgr = mgr;
    }

    public Node getRoot() {
	return overlay.getRoot();
    }

    public void getSize(Dimension dim) {
	overlay.getSize(dim);
    }

    public boolean isOpaque() {
	return overlay.isOpaque();
    }

    public void update() {
	overlay.update();
    }

    private static FontMetrics getFontMetrics(Font font) {
	GraphicsEnvironment env = 
	    GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice dev = env.getDefaultScreenDevice();
	GraphicsConfiguration cfg = dev.getDefaultConfiguration();
	Graphics2D g = cfg.createCompatibleImage(1,1).createGraphics();
	return g.getFontMetrics(font);
    }
}

class NodeTextOverlay extends TextOverlay implements NodeOverlay {

    private Canvas3D canvas;
    private Node node;

    NodeTextOverlay(Node node) {
	this(node, node.getName());
    }

    NodeTextOverlay(Node node, String text) {
	super(text);
	this.node = node;
    }

    public void update() {
	BoundingSphere sphere = (BoundingSphere) node.getBounds();
	javax.vecmath.Point3f q = sphere.getCenter();
	q.y = 0.6f;
	node.getLocalToVworld().transform(q);
	q = Util3D.toScreen(canvas, q);
	setPosition((int)q.x, (int)q.y);
	if (node instanceof Actor) {
	    if (((Actor)node).dead)
		setPosition(1000,1000);
	}
	super.update();
    }

    public void setCanvas(Canvas3D canvas) {
	this.canvas = canvas;
    }
}

class ImageOverlay implements Overlay {

    private Graphics2D gfx;
    private BufferedImage image;
    private UIOverlay overlay;
    private UIWindowManager uiWinMgr;

    public ImageOverlay() {
    }

    public ImageOverlay(BufferedImage img) {
	setImage(img);
    }

    public void setImage(BufferedImage img) {

	overlay = new UIOverlay(img.getWidth(), img.getHeight(), true, false);
	overlay.setBackgroundMode(UIOverlay.BACKGROUND_COPY);
	image = overlay.getBackgroundImage();
	overlay.setBackgroundImage(img);
	gfx = image.createGraphics();
	gfx.drawImage(img,null,0,0);

	if (uiWinMgr != null) {
	    uiWinMgr.removeOverlay(this);
	    uiWinMgr.addOverlay(this);
	}

//	overlay.repaint();
    }

    public BufferedImage getImage() {
	return image;
    }

    public Graphics2D getGraphics() {
	return gfx;
    }

    public void setPosition(int x, int y) {
	if (uiWinMgr != null) {
	    uiWinMgr.setPosition(this, 
				 x-image.getWidth()/2, 
				 y-image.getHeight()/2);
	} else { 
	    overlay.setPosition(x, y);
	}
    }

    public void setWindowManager(UIWindowManager mgr) {
	this.uiWinMgr = mgr;
    }

    public Node getRoot() {
	return overlay.getRoot();
    }

    public void getSize(Dimension dim) {
	overlay.getSize(dim);
    }

    public boolean isOpaque() {
	return overlay.isOpaque();
    }

    public void update() {
	overlay.update();
    }
}

class NodeImageOverlay extends ImageOverlay implements NodeOverlay {

    private Node node;
    private Canvas3D canvas;

    public NodeImageOverlay(Node node, BufferedImage img) {
	super(img);
	this.node = node;
    }

    public void update() {
	BoundingSphere sphere = (BoundingSphere) node.getBounds();
	Point3f q = sphere.getCenter();
	q.y = sphere.getRadius();
	node.getLocalToVworld().transform(q);
	q = Util3D.toScreen(canvas, q);
	setPosition((int)q.x, (int)q.y);
	super.update();
    }

    public void setCanvas(Canvas3D canvas) {
	this.canvas = canvas;
    }
}

interface Overlay extends UIOverlayInterface {
    public void setWindowManager(UIWindowManager mgr);
}

interface NodeOverlay extends Overlay {
    public void setCanvas(Canvas3D canvas);
}

class HudOverlay extends UIOverlay implements UIOverlayInterface {
    HudOverlay(int w, int h, boolean clip, boolean blend) {
	super(w,h,clip,blend);
    }
    public void paint(Graphics2D g) {}
}
