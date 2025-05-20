package bierse.view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RunningMessagePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6901526441438331392L;

	private String message;
	private static Timer timer;
	private int count;
	private int stringWidth;
	private int stringHeight;
	
	private int startX;
	
	public RunningMessagePanel(String message, int speed) {
		this.message = message;
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
		this.setFont(font);
		JLabel tmpLabel = new JLabel(message);
		tmpLabel.setFont(font);
		stringWidth = tmpLabel.getFontMetrics(font).stringWidth(message);
		stringHeight = tmpLabel.getFontMetrics(font).getHeight();
		if (timer == null) {
			timer = new Timer(speed, this);
			timer.start();
		} else {
			timer.addActionListener(this);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (message != null) {
			g.drawString(message, startX, 20);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		count++;
		startX = getWidth() - count; 
		if (startX + stringWidth + 30 < 0) {
			count = 0;
		}
		this.repaint();
	}
	
	public int getStringWidth() {
		return stringWidth;
	}
	
	public int getStringHeight() {
		return stringHeight;
	}
	
	public void setSpeed(int speed) {
		timer.setDelay(speed);
	}
	
	public void setMessage(String message) {
		this.message = message;
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
		this.setFont(font);
		JLabel tmpLabel = new JLabel(message);
		tmpLabel.setFont(font);
		stringWidth = tmpLabel.getFontMetrics(font).stringWidth(message);
	}

}
