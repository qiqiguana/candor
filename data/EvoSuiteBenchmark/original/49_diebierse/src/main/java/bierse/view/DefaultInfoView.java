package bierse.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import bierse.model.Drink;
import bierse.model.Model;

public class DefaultInfoView extends JPanel implements IModelChangedListener, ImageObserver {

	private final static DecimalFormat df = new DecimalFormat("0.00");
	private final static Font font = new Font(Font.SERIF, Font.PLAIN, 60);
	private final ImageIcon trendNeutralIcon = new ImageIcon(getClass().getResource("/img/neutral.gif"));
	private final ImageIcon trendLowerIcon = new ImageIcon(getClass().getResource("/img/lower.gif"));
	private final ImageIcon trendHigherIcon = new ImageIcon(getClass().getResource("/img/higher.gif"));
	
	private Model model;
	private Image bgrImg;
	private JLabel timeLeftLabel;
	private RunningMessagePanel runningMessagePanel;
	private JFrame frame;
	
	public DefaultInfoView(Model model) {
		frame = new JFrame("Preise");
		frame.setUndecorated(true);
		Object mouseListener = new DefaultInfoViewMouseListener(frame);
		frame.addMouseListener((MouseListener)mouseListener);
		frame.addMouseMotionListener((MouseMotionListener)mouseListener);
		
		JPopupMenu contextMenu = new JPopupMenu();
		JMenuItem closeMenuItem = new JMenuItem("Schlieﬂen");
		closeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		contextMenu.add(closeMenuItem);
		
		
		this.model = model;
		model.registerModelChangedListener(this);
		frame.getContentPane().setLayout(new BorderLayout());
		
		JPanel infoPanel = createInfoPanel(model, contextMenu);
		frame.getContentPane().add(infoPanel, BorderLayout.SOUTH);
		
		setOpaque(false);
		
		add(createDrinkList());
		bgrImg = Toolkit.getDefaultToolkit().createImage(model.getSettings().getBackgroundImagePath());
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(bgrImg, 0);
		try {
			mt.waitForAll();
		} catch (Exception e) {
			model.getLog().error(this, e);
		}
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.setAlwaysOnTop(true);
		
		frame.setSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private JPanel createInfoPanel(Model model, JPopupMenu contextMenu) {
		JPanel infoPanel = new JPanel(new BorderLayout());
		
		infoPanel.setComponentPopupMenu(contextMenu);
		
		runningMessagePanel = new RunningMessagePanel(model.getSettings().getMessage(), model.getSettings().getMessageSpeed());
		infoPanel.add(runningMessagePanel, BorderLayout.CENTER);
		
		JPanel timeLeftPanel = createTimeLeftPanel(model, runningMessagePanel);	
		infoPanel.add(timeLeftPanel, BorderLayout.EAST);
		return infoPanel;
	}

	private JPanel createTimeLeftPanel(Model model, RunningMessagePanel runningMessagePanel) {
		JPanel timeLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timeLeftPanel.setOpaque(false);
		timeLeftLabel = new JLabel("Noch " + model.getTimeLeft() + " Sekunden");
		timeLeftPanel.add(timeLeftLabel);
		
		Dimension panelDim = new Dimension();
		int width = timeLeftLabel.getFontMetrics(timeLeftLabel.getFont()).stringWidth("Noch " + model.getSettings().getTimeInterval() + " Sekunden");
		panelDim.setSize(width + 10, runningMessagePanel.getStringHeight());
		timeLeftPanel.setPreferredSize(panelDim);
		timeLeftPanel.setMinimumSize(panelDim);
		return timeLeftPanel;
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(bgrImg,0,0,this.getWidth(),this.getHeight(),this);
		super.paintComponent(g);
	}
	
	private JPanel createDrinkList() {
		JPanel drinkListPanel = new JPanel();
		drinkListPanel.setOpaque(false);
		drinkListPanel.setLayout(new GridLayout(model.getLstUsedDrink().size(), 1));
		for(Drink drink : model.getLstUsedDrink()) {
			Box drinkBox = new Box(BoxLayout.X_AXIS);
			JLabel nameLabel = new JLabel(drink.getName() + ":  ");
			nameLabel.setFont(font);
			drinkBox.add(nameLabel);
			float price = drink.getCurrentPrice() / 100f;
			JLabel priceLabel = new JLabel(df.format(price) + " " + model.getSettings().getCurrency() + "  ");
			priceLabel.setFont(font);
			drinkBox.add(priceLabel);
			ImageIcon trendIcon = trendNeutralIcon;
			if(drink.getTrend()<0) {
				trendIcon = trendLowerIcon;
			} else if(drink.getTrend()>0) {
				trendIcon = trendHigherIcon; 
			}
			JLabel trendLabel = new JLabel(trendIcon);
			trendLabel.setFont(font);
			drinkBox.add(trendLabel);
			drinkListPanel.add(drinkBox);
		}
		return drinkListPanel;
	}

	@Override
	public void fireModelChanged(int eventType) {
		if((eventType & Model.EVENT_RECALCULATED + Model.EVENT_SETTINGS_CHANGED) > 0) {
			removeAll();
			add(createDrinkList());
			runningMessagePanel.setSpeed(model.getSettings().getMessageSpeed());
			runningMessagePanel.setMessage(model.getSettings().getMessage());
		}
		if((eventType & Model.EVENT_TIME_LEFT_CHANGED) > 0) {
			timeLeftLabel.setText("Noch " + model.getTimeLeft() + " Sekunden");
		}
		validate();
		repaint();
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
		return true;
	}



}
