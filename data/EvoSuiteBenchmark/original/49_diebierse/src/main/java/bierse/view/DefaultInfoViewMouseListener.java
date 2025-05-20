package bierse.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class DefaultInfoViewMouseListener implements MouseListener, MouseMotionListener {
	
	private Point clickPoint;
	private Point startFrameLoc;
	private Dimension prevSize;
	
	private JFrame defaultInfoFrame;
	
	public DefaultInfoViewMouseListener(JFrame defaultInfoFrame) {
		this.defaultInfoFrame = defaultInfoFrame;
		prevSize = new Dimension(800, 600);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Point newPoint = e.getLocationOnScreen();
		double x = newPoint.getX() - clickPoint.getX();
		double y = newPoint.getY() - clickPoint.getY();
		double newX = startFrameLoc.getX() + x;
		double newY = startFrameLoc.getY() + y;
		defaultInfoFrame.setLocation(new Double(newX).intValue(), new Double(newY).intValue());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (defaultInfoFrame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
				prevSize = defaultInfoFrame.getSize();
				defaultInfoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else {
				defaultInfoFrame.setSize(prevSize);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		clickPoint = e.getLocationOnScreen();
		startFrameLoc = defaultInfoFrame.getLocation();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
