import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Client extends JFrame
{
	 private JField Field = new JField();
	 private JMiniMap minimap = new JMiniMap();
	 private JRadioButton FoodBtn;
	 private JRadioButton FlagBtn;
	 private JLabel Power;
	 private JLabel Score;
	 private JLabel Time;
	 private JList doList;
	 private JTextPane  description;
	 private ArrayList<drawCircle> circles = new ArrayList<drawCircle>();
	 private boolean connected = false;
	 private ArrayList<Message> messages = new ArrayList<Message>();
	 private int top= 0;
	 private int left= 0;
	 private int worldSize = 2000;
	 private ServerThread S = null;
	 private Random gen = new Random();
	 private Sound sfx = null;
     private MP3 currentMP3 = null;
     private  OptionList theList = null;
	 private Timer ambience = new Timer(100000, new ambienceTimer());
	 private ArrayList<Chain> chains = new ArrayList<Chain>();
	 private ArrayList<ArrayList<ScorePoint>> elevation = new ArrayList<ArrayList<ScorePoint>>();
	 private ArrayList<ArrayList<ScorePoint>> scores = new ArrayList<ArrayList<ScorePoint>>();
	 private ArrayList<CatmullRomSpline> blobs = new ArrayList<CatmullRomSpline>();
	 private int maxScore = 30;
	 private int time = 0;
	 private boolean infoing = true;
	 private boolean chart = false;
	 private Client self = this; 
	 //private ArrayList<String> 
	 
	 private class ScorePoint
	 {
		 int x;
		 int y;
		 int color;
		 
		 public ScorePoint(int x, int y, int color)
		 {
			 this.x = x;
			 this.y = y;
			 this.color = color;
			// System.err.println("New scorepoint "+x+" "+y+" "+color);
		 }
	 }
	 
	 private class Chain
	 {
		 double[][] points = new double[30][2];
		 int length = 0;
		 public Chain(int length)
		 {
			 this.length = length;
		 }
	 }
	 
	 private class ambienceTimer implements ActionListener
	 {

		public void actionPerformed(ActionEvent arg0) {
			playMP3("ambient4.mp3");
		}
		 
	 }
	 
	 private void UpdateChains()
	 {
		 for(int i = 0; i < chains.size(); i++)
		 {
			 for(int j = 0; j < chains.get(i).points.length; j++)
			 {
				 chains.get(i).points[j][0] += gen.nextGaussian();
				 chains.get(i).points[j][1] += gen.nextGaussian();
			 }
			 for(int j = 0; j < chains.get(i).points.length-1; j++)
			 {
				 double dx =chains.get(i).points[j+1][0] -  chains.get(i).points[j][0];
				 double dy =chains.get(i).points[j+1][1] -  chains.get(i).points[j][1];
				 double mag = Math.sqrt(dx*dx+dy*dy);
				 chains.get(i).points[j+1][0]  = chains.get(i).points[j][0]+(dx/mag)*5;
				 chains.get(i).points[j+1][1]  = chains.get(i).points[j][1]+(dy/mag)*5;
			 }
		 }
		 for(int i = 0; i < blobs.size(); i++)
		 {
			 for(int j = 3; j < blobs.get(i).controlPoints_.length-6; j++)
			 {
				 if(blobs.get(i).controlPoints_[j] != 0)
					 blobs.get(i).controlPoints_[j] += gen.nextGaussian()*0.3;
				 
			 }
			blobs.get(i).controlPoints_[blobs.get(i).controlPoints_.length-6] = blobs.get(i).controlPoints_[3];
			 blobs.get(i).controlPoints_[blobs.get(i).controlPoints_.length-5] = blobs.get(i).controlPoints_[4];
			 blobs.get(i).controlPoints_[blobs.get(i).controlPoints_.length-4] = blobs.get(i).controlPoints_[5];
		 }

	 }

	 
	 public void connect(String string, int i) {
		// TODO Auto-generated method stub
			try {
				

				if(S == null)
				{
					S = new ServerThread(new Socket(string, i), this);
//					S.setPriority(Thread.MIN_PRIORITY);
					S.start();
				}
				else
				{
					sendMessage("quit");
					Thread.sleep(1000);
					circles.clear();
					S = new ServerThread(new Socket(string, i), this);
//					S.setPriority(Thread.MIN_PRIORITY);
					S.start();
				}

				} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(self, "Having trouble connecting to the server...");
			}

			//playMP3("ambient4.mp3");
		//	ambience.start();

	}


	private class Message
	 {
		 String message;
		 Color c;
		 long time;
		 boolean permanent = false;
		 public Message(int col, String m)
		 {
			 this.message = m;
			 this.c = new Color(col);

			 this.time = System.currentTimeMillis();
		 }
		 public Message(int col, String m, boolean b)
		 {
			 this.message = m;
			 this.c = new Color(col);

			 this.time = System.currentTimeMillis();
			 this.permanent = true;
		 }
	 }
	 
	 private class JMiniMap extends JPanel
	 {
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(new Color(200,230,230));
				g2.fillRect(0,0,this.getWidth(),this.getHeight());
				for(int i = 0; i < circles.size(); i++)
				{
					drawCircle dc = circles.get(i);
					g2.setColor(dc.c);
					int x = (int) ((dc.getX()*200)/worldSize);
					int y = (int) ((dc.getY()*200)/worldSize);
					int size = (int)((dc.size*200)/worldSize);
					if(size < 1)
						size = 1;
					g2.fillOval(x-size/2, y-size/2, size,size);
				}

				g2.setColor(Color.WHITE);
				g2.drawRect((200*(left))/worldSize,(200*(top))/worldSize,(700*200)/worldSize, (700*200)/worldSize);
			}
		 
	 }
	 private class windowCloseListener implements WindowListener
	 {

		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			if(connected) {
				System.err.println("Sent quit");
				sendMessage("quit");
			}
			sfx.playSound("exit.wav");
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void windowClosed(WindowEvent arg0) {

			//S.disconnect();
		}

		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
	 
	 private class miniClickListener implements MouseListener
	 {

		public void mouseClicked(MouseEvent arg0) {

		}

		public void mousePressed(MouseEvent arg0) {
			if(arg0.getButton() == arg0.BUTTON3)
			{
				sendMessage("Center");
			}
			else
			{
				// TODO Auto-generated method stub
				infoing = false;
				left = (arg0.getX()*worldSize)/200 -350 ;
				top = (arg0.getY()*worldSize)/200 -350 ;
				minimap.updateUI();
				Field.updateUI();
			}
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		 
	 }
	 
	 private class fieldClicker implements MouseListener
	 {

		public void mouseClicked(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) 
		{
			 /*
			 theList.items.add("Food Receptors 50");
			 theList.items.add("Simple Flagella 100");
			 theList.items.add("Senescence Seggregation 100");
			 theList.items.add("Toxic Apoptosis 120");
			 theList.items.add("Group Growth 150");
			 theList.items.add("Aggression 150");
			 theList.items.add("Cellular Walls 150");
			 theList.items.add("Lysozymes 150");
			 theList.items.add("Antibiotic Resistance 150");
			 theList.items.add("Place Food 200");
			 theList.items.add("Telomerase 250");
			 theList.items.add("Sibling Receptors 250");
			 theList.items.add("Place Pheremone 300");
			 theList.items.add("Complex Flagella 500");
			 theList.items.add("Photosynthesis 900");
			 theList.items.add("Triplication 700");
			 theList.items.add("Release Antibiotic 1200");
			 theList.items.add("Enzymatic Revolution 3000");
	*/		if(connected)
			{
				if(arg0.getButton() == MouseEvent.BUTTON1)
				{
					if(doList.getSelectedIndex() != -1)
					{
						
							sendMessage(theList.items.get(doList.getSelectedIndex())+" "+(arg0.getX()+left)+" "+(arg0.getY()+top));
					}
					else
					{
						sendMessage("info "+(arg0.getX()+left)+" "+(arg0.getY()+top));
						infoing = true;
					}
				}
				else if(arg0.getButton() == MouseEvent.BUTTON3)
				{
					sendMessage("info "+(arg0.getX()+left)+" "+(arg0.getY()+top));
					infoing = true;
				}
				else
					infoing = false;
				
			}
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		 
	 }
	 
	 private Color getColor(String trying)
	 {
	    Color c = Color.BLACK;
		
	      try {
	        Class ColClas = Class.forName("java.awt.Color");
	        Field f = ColClas.getField(trying);
	        c = (Color)f.get(null);
	      }
	      catch (Exception e) {
	        // You failed to pick a valid colour, stick your head in a pig!
	        } 
	      return c;
	}

	 
	 private class drawCircle
	 {
		 double x, y, size;
		 Color c;
		 int id;
		 public drawCircle(double x, double y, double size, int color, int id)
		 {
			 this.x = x;
			 this.y = y;
			 this.size = size;
			 this.c = new Color(color);
			 this.id = id;
		 }

		public Color getC() {
			return c;
		}

		public void setC(Color c) {
			this.c = c;
		}

		public double getSize() {
			return size;
		}

		public void setSize(double size) {
			this.size = size;
		}

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}
	 }
	 
	 
	 public Client()
	 {

		 initialize();
		 sfx = new Sound();
		 sfx.initialize();
		
		/*
		Random gen = new Random();
		worldSize = 2000;
		for(int i = 0; i < 1000; i++)
		{
			circles.add(new drawCircle(gen.nextInt(1000), gen.nextInt(1000), gen.nextInt(30), gen.nextInt(256+256*256+ 1000)));
		}
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			circles.clear();
			for(int i = 0; i < 2000; i++)
			{
				circles.add(new drawCircle(gen.nextInt(2000), gen.nextInt(2000), gen.nextInt(30), gen.nextInt(256+256*256+ 256*256*255)));
			}
			Field.updateUI();
			minimap.updateUI();
		}
		*/
	 }
	 
	 private void  initialize()
	 {
		 JMenuBar menu = new JMenuBar();
		 menu.setMaximumSize(new Dimension(80,15));
		 menu.setPreferredSize(new Dimension(80,15));
		 menu.setFont(new Font("Halvetica", Font.PLAIN, 10));
		 JMenu menuFile = new JMenu("File");
		 menu.add(menuFile);
		 JMenuItem menuConnect = new JMenuItem("Connect...");
		 menuConnect.setPreferredSize(new Dimension(60,15));
		 menuConnect.setFont(new Font("Halvetica", Font.PLAIN, 10));
		 menuConnect.addActionListener(new connectListener());
		 menuFile.add(menuConnect);
		 JMenuItem menuIntro = new JMenuItem("Intro...");
		 menuIntro.setPreferredSize(new Dimension(60,15));
		 menuIntro.setFont(new Font("Halvetica", Font.PLAIN, 10));
		 menuIntro.addActionListener(new introListener());
		 menuFile.add(menuIntro);
		this.setJMenuBar(menu);
		 this.addWindowListener(new windowCloseListener());
		 Container cp = this.getContentPane();
		 cp.setLayout(new BorderLayout());
		 Field.setMaximumSize(new Dimension(700,700));
		 Field.setPreferredSize(new Dimension(700,700));
		 Field.addMouseListener(new fieldClicker());
		 cp.add(Field, BorderLayout.CENTER);
		 JPanel right = new JPanel();
		 right.setLayout(new FlowLayout(5,5,FlowLayout.CENTER));
		 right.setMaximumSize(new Dimension(200,700));
		 right.setPreferredSize(new Dimension(200,700));
		 
		 minimap.setPreferredSize(new Dimension(200,200));
		 minimap.setMaximumSize(new Dimension(200,200));
		 minimap.addMouseListener(new miniClickListener());
		 right.add(minimap);
		 JPanel adders = new JPanel();
		 adders.setLayout(new BorderLayout());
		 Power = new JLabel("Evolution 0");
		 Time = new JLabel("Time 0");
		 Score = new JLabel("Score 0");
		 JPanel info = new JPanel();
		 info.setLayout(new GridLayout(4,1));
		 info.add(Power);
		 info.add(Score);
		 info.add(Time);
		 info.add(new JLabel("Possible Actions and Cost:"));
		 adders.add(info, BorderLayout.NORTH);
		 theList = new OptionList();

		 theList.items.add("Place Food 100");
		 theList.items.add("Place Pheremone 20");
		 theList.items.add("Place Inorganic 50");
		 theList.items.add("Food Receptors, 40");
		 theList.items.add("Simple Flagella, 70");
		 theList.items.add("Senescence Seggregation, 40");
		 theList.items.add("Toxic Apoptosis, 200");
		 theList.items.add("Antibiotic Receptors, 50");
		 theList.items.add("Group Growth, 250");
		 theList.items.add("Aggression, 78");
		 theList.items.add("Cellular Walls, 290");
		 theList.items.add("Lysozymes, 310");
		 theList.items.add("Antibiotic Resistance, 200");
		 theList.items.add("Telomerase, 500");
		 theList.items.add("Group Attraction, 150");
		 theList.items.add("Complex Flagella, 150");
		 theList.items.add("Photosynthesis, 700");
		 theList.items.add("Triplication, 650");
		 theList.items.add("Release Antibiotic, 3000");
		 theList.items.add("Enzymatic Revolution, 5000");
		 doList = new JList(theList);
		 doList.setFont(new Font("Helvetica",Font.PLAIN, 10));
		 JScrollPane sb = new JScrollPane(doList);
		 doList.addListSelectionListener(new doListListener());
		 sb.setPreferredSize(new Dimension(190,300));
		 sb.setMaximumSize(new Dimension(190,300));
		 right.add(adders);
		 right.add(sb);
		 description = new JTextPane();
		 description.setText("Click an action above for details...");
		 description.setEditable(false);
		 description.setPreferredSize(new Dimension(190,120));
		 description.setMaximumSize(new Dimension(190,120));
		 description.setFont(new Font("Helvetica",Font.PLAIN, 10));
		 right.add(description);
		 right.invalidate();
		 cp.add(right, BorderLayout.EAST);
		 this.validate();
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setSize(920,735);
		 this.setResizable(false);
		 this.setTitle("CellWars by Max Shokhirev 2008");

		 this.setVisible(true);
		 
	 }
	 private class introListener implements ActionListener
	 {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			sfx.playSound("welcome.wav");
		}
		 
	 }
	 
	 private class connectListener implements ActionListener
	 {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
				connect(JOptionPane.showInputDialog(null, "IP Address:"), 4000);

		}
		 
	 }
	 
	 private void inializeElevation()
	 {
		 
		 elevation.add(new ArrayList<ScorePoint>());
		 elevation.add(new ArrayList<ScorePoint>());
		 elevation.add(new ArrayList<ScorePoint>());
		 elevation.add(new ArrayList<ScorePoint>());
		 elevation.add(new ArrayList<ScorePoint>());
		 for(int i = 0; i <worldSize/100; i++)
		 {
			 int e = gen.nextInt(5);
			 int x = gen.nextInt(worldSize);
			 int y = gen.nextInt(worldSize);
			 for(int j = 4-e; j <= 4; j++)
			 {
				 elevation.get(j).add(new ScorePoint(x, y,(10*j)+10));
				 //System.err.println("Added new Scorepoint at "+x+" "+y+" level "+j);
			 }
		 }

	 }

	 
	 private void initializeBlobs()
	 {
		 int many = 1+gen.nextInt(3);
		 for(int i = 0; i < many; i++)
		 {
				 int x = gen.nextInt(worldSize);
				 int y = gen.nextInt(worldSize);
				 int dx = 0;
				 int dy = 0;
				 int ccount = 6;
				 
			    double[] c = new double[3*ccount + 3];
			    
			    for(int j = 0; j < ccount; j++)
			    {
				    c[j*3]  =   x;  
				    c[j*3 +1]  =   y;  
				    c[j*3 + 2]  =   0.0;
				    if(j < 3)
				    {
				    	dx += Math.cos(j*2)*40 + gen.nextInt(5)-gen.nextInt(5);
				    	dy += Math.sin(j*2)*40+ gen.nextInt(5)-gen.nextInt(5);
				    }
				    else
				    {
				    	dx -= Math.cos(j*2)*40+ gen.nextInt(5)-gen.nextInt(5);
				    	dy -= Math.sin(j*2)*40+ gen.nextInt(5)-gen.nextInt(5);
				    }
				    x += dx;
				    y += dy;
			    }
			    c[3*ccount] = c[0];
			    c[3*ccount+1] = c[1];
			    c[3*ccount+2] = c[2];

			    
			    blobs.add(new CatmullRomSpline(c, ccount*4));
		 }
	 }
	 private void initializeChains() {
		int numChains = 5+gen.nextInt(5);
		for(int i = 0; i < numChains; i++)
		{
			Chain c =  new Chain(10+gen.nextInt(15));
			c.points[0][0] = gen.nextDouble()*worldSize;
			c.points[0][1] = gen.nextDouble()*worldSize;
			for(int j = 1; j < c.length; j++)
			{
				c.points[j][0] = c.points[j-1][0]+gen.nextGaussian()*5;
				c.points[j][1] = c.points[j-1][1]+gen.nextGaussian()*5;
			}
			chains.add(c);
			
		}
	
	}


	private class doListListener implements ListSelectionListener
	 {
		 /*
		 theList.items.add("Place Food 130");
		 theList.items.add("Place Pheremone 20");
		 theList.items.add("Place Inorganic 70");
		 theList.items.add("Food Receptors, 30");
		 theList.items.add("Simple Flagella, 80");
		 theList.items.add("Senescence Seggregation, 40");
		 theList.items.add("Toxic Apoptosis, 400");
		 theList.items.add("Antibiotic Receptors, 70");
		 theList.items.add("Group Growth, 150");
		 theList.items.add("Aggression, 150");
		 theList.items.add("Cellular Walls, 90");
		 theList.items.add("Lysozymes, 110");
		 theList.items.add("Antibiotic Resistance, 150");
		 theList.items.add("Telomerase, 600");
		 theList.items.add("Group Attraction, 350");
		 theList.items.add("Complex Flagella, 200");
		 theList.items.add("Photosynthesis, 900");
		 theList.items.add("Triplication, 1500");
		 theList.items.add("Release Antibiotic, 2000");
		 theList.items.add("Enzymatic Revolution, 10000");
*/	
		public void valueChanged(ListSelectionEvent arg0) {
			infoing = false;
			if(doList.getSelectedIndex() == 0)
			{
				description.setText("Place a small food particle on the field.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}			
			else if(doList.getSelectedIndex() == 1)
			{
				description.setText("Place a pheremone molecule that all cells are attracted to.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 2)
			{
				description.setText("Place a clump of innorganic matterial.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 3)
			{
				description.setText("A cell evolves food receptors, attracting it to food if it is mobile.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 4)
			{
				description.setText("A cell is able to swim at medium velocity.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 5)
			{
				description.setText("Dying cells tend to move away from their siblings, causing them to free up resources.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 6)
			{
				description.setText("Cells that die leave behind toxic antibiotics which are harmfull to all cells.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 7)
			{
				description.setText("Antibiotic receptors allow your cell to sense antibiotics and to try to move away.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex()== 8)
			{
				description.setText("Your cells grow faster when near their siblings due to a distribution of labor within the colony.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 9)
			{
				description.setText("Your cells are attracted to rival cells which are smaller in size, but tend to move away from bigger opponents.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 10)
			{
				description.setText("The evolution of cellular walls cause one cell to be more resistant to attacks by rival cells.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 11)
			{
				description.setText("Lysozymes are secreted when attacking rival cells. These enzymes make the opponent more vulnerable to attack.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex()== 12)
			{
				description.setText("Antibiotic resistance reduces the negative effects of being next to antibiotic particles.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}

			else if(doList.getSelectedIndex() == 13)
			{
				description.setText("Telomerase more than doubles the cell's longevity.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 14)
			{
				description.setText("Sibling receptors make your cells attracted to its siblings. This causes your cells to move in groups.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex()== 15)
			{
				description.setText("Complex flagella evolution allows your cells to move at an increased rate.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 16)
			{
				description.setText("Photosynthesis allows your cells to slowly grow even in the absence of food\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 17)
			{
				description.setText("Triplication causes your cells to split into three different daughter cells at once!\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 18)
			{
				description.setText("Your cells release antibiotic particles in the direction of nearby rival cells.\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			else if(doList.getSelectedIndex() == 19)
			{
				description.setText("Your cells are able to metabolize inorganics as well as antibiotics and rival cells making them grow in almost all environments!\nThe cost is in evolution points\n" +
						"You can choose to do this by clicking anywhere on the field.");
			}
			description.updateUI();
			
		}
		 
	 }
	
	 private class OptionList implements ListModel
	 {
		 ArrayList<String> items = new ArrayList<String>();
		public int getSize() {
			// TODO Auto-generated method stub
			return items.size();
		}

		public Object getElementAt(int arg0) {
			// TODO Auto-generated method stub
			return items.get(arg0);
		}

		public void addListDataListener(ListDataListener arg0) {
			// TODO Auto-generated method stub
			
		}

		public void removeListDataListener(ListDataListener arg0) {
			// TODO Auto-generated method stub
			
		}
		 
	 }

	 public void sendMessage(String m)
	 {
		 if(S != null)
			 S.sendMessage(m); 
	 }

	private void playMP3(String name)
	{
		 currentMP3 = new MP3(name);
	}
	 
	private class ServerThread extends Thread
	 {
			Socket theSocket;
			PrintWriter out;
			BufferedReader in;
			Client theClient;
			
			public ServerThread(Socket theSock, Client G)
			{
				theSocket = theSock;
				theClient = G;
				G.S = this;
				theClient.setConnected(true);
				//this.setPriority(Thread.MAX_PRIORITY);
				try {
					out = new PrintWriter(theSocket.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					in = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
			
			public void run()
			{
				while(!theSocket.isClosed())
				{
					String incomming = "";
					try {
						if(!theSocket.isClosed())
							try{
							incomming = in.readLine();
							}
							catch(java.net.SocketException e)
							{
								System.err.println("Tried to read from a closed stream! Connection terminated.");
								theSocket.close();
							}
						//process the incomming line by the client.
						if(incomming != null)
							theClient.analyzeServerMessage(incomming);
						else
						{
							theClient.analyzeServerMessage("The connection was terminated by the server!");
							theSocket.close();
							theClient.setConnected(false);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Client quit abruptly!");
						
						e.printStackTrace();
						System.exit(1);
						
					}
				}
				
			}
			
			public void sendMessage(String message)
			{
				out.println(message);
				out.flush();
			}
			


			public void disconnect()
			{
				try {
					this.theSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Problem disconnecting...");
				}		
			}
			
	 }
	    
	
		public Image loadTransImage(String path)
		{
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image newImage = tk.getImage(path);
			MediaTracker mediaTracker = new MediaTracker(this);
			mediaTracker.addImage(newImage, 0);
			try
			{
				mediaTracker.waitForID(0);
			}
			catch (InterruptedException ie)
			{
				System.err.println(ie);
				System.exit(1);
			}
			return newImage;
		}

		public void analyzeServerMessage(String incomming) 
		{
			//System.err.println("Got incomming: "+incomming);
			if(incomming.startsWith("Sound ") && incomming.length() > 6)
			{
				StringTokenizer tokener = new StringTokenizer(incomming.substring(6,incomming.length()), ";");
				while(tokener.hasMoreTokens())
				{
					Scanner S = new Scanner(tokener.nextToken());
					String sound = S.next();
					int x = S.nextInt();
					int y = S.nextInt();
					if(x == -1 && y == -1)
					{
						sfx.playSound(sound);
					}
					else
					if((x>= left && x <= left+700)&&(y>= top && y <= top+700))
					{
						sfx.playSound(sound);
						System.err.println("Playing sound..."+sound+" x "+x+" y "+y);
					}
					
				}
			}
			else if(incomming.startsWith("MP3 "))
			{
				playMP3(incomming.substring(4,incomming.length()));
				
			}
			else if(incomming.startsWith("Message "))
			{
				Scanner S = new Scanner(incomming.substring(8,incomming.length()));
				messages.add(new Message(S.nextInt(), S.nextLine()));
				Field.updateUI();
			}
			else if(incomming.startsWith("Message2"))
			{
				Scanner S = new Scanner(incomming.substring(9,incomming.length()));
				messages.add(new Message(S.nextInt(), S.nextLine(),true));
				chart = true;
				Field.updateUI();
			}
			else if(incomming.startsWith("Size"))
			{
				this.worldSize = Integer.parseInt(incomming.substring(4, incomming.length()));
				initializeChains();
				inializeElevation();
				
				initializeBlobs();
			}
			else if(incomming.startsWith("Center"))
			{
				Scanner S = new Scanner(incomming.substring(6, incomming.length()));
				if(infoing)
				{
					this.left = S.nextInt()-350;
					this.top = S.nextInt()-350;
				}
			}
			else if(incomming.startsWith("Update"))
			{
				StringTokenizer tokener = new StringTokenizer(incomming.substring(7,incomming.length()), ";");
				int index = 0;
				while(tokener.hasMoreTokens())
				{
					Scanner S = new Scanner(tokener.nextToken());
					int color = Integer.parseInt(S.next());
					time = Integer.parseInt(S.next());
					int theScore = Integer.parseInt(S.next());
					if(theScore > maxScore) maxScore = theScore;
					if(scores.size() <= index)
					{
						ArrayList<ScorePoint> theList = new ArrayList<ScorePoint>();
						scores.add(theList);
					}
					scores.get(index).add(new ScorePoint(time,theScore,color));
					if(index == 0)
					{
						Score.setText("Score "+theScore);
						Time.setText("Time "+time);
						if(theScore < 100)
							Score.setForeground(Color.RED);
						else if(theScore >= 100 && theScore < 200)
							Score.setForeground(Color.ORANGE);
						else if(theScore >= 200 && theScore < 400)
							Score.setForeground(Color.BLACK);
						else if(theScore >= 400 && theScore < 600)
							Score.setForeground(Color.GREEN);
						else 
							Score.setForeground(Color.GREEN.brighter());
						double Evo =Double.parseDouble(S.next());
						if(Evo < 150)
							Power.setForeground(Color.RED);
						else if(Evo >= 150 && Evo < 500)
							Power.setForeground(Color.ORANGE);
						else if(Evo >= 500 && Evo < 1000)
							Power.setForeground(Color.BLACK);
						else if(Evo >= 1000 && Evo < 2000)
							Power.setForeground(Color.GREEN);
						else 
							Power.setForeground(Color.GREEN.brighter());
						Power.setText("Evolution "+(int)Evo);
						Time.updateUI();
						Score.updateUI();
						Power.updateUI();
						if(S.hasNext() && infoing)
						{
							String d = S.next();
							while(S.hasNext())
							{
								d+=" "+S.next();
							}
							try
							{
								description.setText(d);
								description.updateUI();
							}
							catch(NullPointerException e)
							{
								
							}
						}
					}
					index++;
				}
				
			}

			else if(incomming.startsWith("Circles"))
			{
				StringTokenizer tokener = new StringTokenizer(incomming.substring(8,incomming.length()), ";");
				while(tokener.hasMoreTokens())
				{
					Scanner S = new Scanner(tokener.nextToken());
					int id = S.nextInt();
					double x = S.nextDouble();
					double y = S.nextDouble();
					double size = S.nextDouble();
					int color = S.nextInt();
					if(size >= 8)
					{
						addCircle(new drawCircle(x,y,size,color, id));
					}
					else
					{
						removeWithId(id);
					}
					
				}
				UpdateChains();
				minimap.updateUI();
				Field.updateUI();

			}
			
		}

		private void removeWithId(int id)
		{
			for(int i = 0; i < circles.size(); i++)
			{
				if(circles.get(i).id == id)
				{
					circles.remove(i);
					return;
				}
			}
		}
		
		private void addCircle(drawCircle d)
		{
			boolean doAdd = true;
			for(int i = 0; i < circles.size(); i++)
			{
				if(circles.get(i).id == d.id)
				{
					doAdd = false;
					circles.get(i).c = d.c;
					circles.get(i).size = d.size;
					circles.get(i).x = d.x;
					circles.get(i).y = d.y;
					break;
				}
			}
			if(doAdd)
				circles.add(d);
		}

		private class JField extends JPanel
		{
		  		
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				if(connected|| chart)
				{
					g2.setColor(new Color(200,230,230));
					g2.fillRect(0,0,this.getWidth(),this.getHeight());
	
					//UpdateChains();
	
					for(int k = elevation.size()-1; k >= 0; k--)
					{
	//					for(int j = elevation.get(i).size()-1; j >= 0 ;j--)
						ArrayList<ScorePoint> level = elevation.get(k);
						Color c2 = Color.BLACK;
						if(k == 0)
							c2= new Color(175,205,205);
						else if(k == 1)
							c2= new Color(180,210,210);
						else if(k == 2)
							c2= new Color(185,215,215);
						else if(k == 3)
							c2= new Color(190,220,220);
						else
							c2= new Color(195,225,225);
						g2.setColor(c2);
						
						for(int j = 0; j < level.size(); j++)
	//					for(int j = elevation.get(k).size()-1; j >= 0 ;j--)
						{
	//						g2.setColor(new Color(gen.nextInt(256*256*256)));
							int size = level.get(j).color;
							if(level.get(j).x < left+700+(size/2) && level.get(j).x > left - (size/2) && level.get(j).y < top + 700 +(size/2)  && level.get(j).y> top -(size/2) )
								g2.fillOval(level.get(j).x-(size/2)-left,level.get(j).y-(size/2)-top,size,size);
	
						//	System.err.println("Drawing level "+k+" index "+j+" "+level.get(j).x+" "+level.get(j).y+" "+level.get(j).color+" with color "+c2.getRed()+" "+c2.getGreen()+" "+c2.getBlue());
						}
					}
					g2.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					for(int k = 0; k < blobs.size(); k++)
					{
	
						double[] spoints = blobs.get(k).generate();
						for(int i = 1; i < spoints.length/3; i++)
						{
							g2.setColor(new Color(200-gen.nextInt(45),230-gen.nextInt(45),230-gen.nextInt(45)));
							g2.drawLine(((int)spoints[(i-1)*3])-left, ((int)spoints[(i-1)*3+1])-top, (int)spoints[(i)*3]-left, (int)spoints[(i)*3+1]-top);
						}
					
					}
					g2.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					for(int i = 0; i < chains.size(); i++)
					{
						for(int j = 0; j < chains.get(i).length-1; j++)
						{
								g2.setColor(new Color(170+gen.nextInt(30),190+gen.nextInt(30),190+gen.nextInt(30)));
								g2.drawLine((int)chains.get(i).points[j][0]-left,(int)chains.get(i).points[j][1]-top,(int)chains.get(i).points[j+1][0]-left,(int)chains.get(i).points[j+1][1]-top);
						}
					}
						
					for(int i = 0; i < circles.size(); i++)
					{
						drawCircle c = circles.get(i);
						int re = c.getC().getRed();
						int gr = c.getC().getGreen();
						int bl = c.getC().getBlue();
						
						if((c.getX()+c.getSize()/2 > left && c.getX()-c.getSize()/2 < left+Field.getWidth())&&
								(c.getY()+c.getSize()/2 > top && c.getY()-c.getSize()/2 < top+Field.getHeight()))
						{
							
							g2.setColor(c.getC());
							g2.fillOval((int)(c.getX()-c.getSize()/2 -left), (int)(c.getY()-c.getSize()/2 - top),(int)(c.getSize()),(int)(c.getSize()));
							if(c.id >= 0)
							{
								g2.setColor(new Color(re+20,gr+20,bl+20));
								g2.fillOval((int)(c.getX()-c.getSize()/2 -left+1), (int)(c.getY()-c.getSize()/2 - top+1),(int)(c.getSize()-2),(int)(c.getSize()-2));
								g2.setColor(new Color(re+40,gr+40,bl+40));
								g2.fillOval((int)(c.getX()-c.getSize()/2 -left+2), (int)(c.getY()-c.getSize()/2 - top+2),(int)(c.getSize()-4),(int)(c.getSize()-4));
								if(c.getSize() >= 10)
								{
									for(int j = 0; j < c.getSize(); j+=4)
									{
										g2.setColor(new Color(c.getC().getRed()+gen.nextInt(10)-gen.nextInt(10),c.getC().getGreen()+gen.nextInt(10)-gen.nextInt(10),c.getC().getBlue()+gen.nextInt(10)-gen.nextInt(10)));
										g2.fillOval((int)(c.getX()-gen.nextDouble()*(c.getSize()/2)+2 -left), (int)(c.getY()-gen.nextDouble()*(c.getSize()/2)+2 - top),(int)(c.getSize()/4+gen.nextInt(2)),(int)(c.getSize()/4+gen.nextInt(2)));
									}
									for(int j = 0; j < c.getSize(); j+=6)
									{
										g2.setColor(new Color(c.getC().getRed()+gen.nextInt(10)-gen.nextInt(10),c.getC().getGreen()+gen.nextInt(10)-gen.nextInt(10),c.getC().getBlue()+gen.nextInt(10)-gen.nextInt(10)));
										g2.drawLine((int)(c.getX()-gen.nextDouble()*(c.size/2)+4 -left), (int)(c.getY()-gen.nextDouble()*(c.getSize()/2)+4 - top),(int)(c.getX()+gen.nextDouble()*(c.size/2)-4-left), (int)(c.getY()+gen.nextDouble()*(c.size/2)-4 - top));
									}
									
								}
							    //System.err.println("Drawing circle "+c.x+" "+c.y+" "+c.size);
							}
							else
							{
								
								if(re + 40 < 256 && gr + 40 < 256&& bl + 40 < 256 )
								{
									g2.setColor(new Color(re+10,gr+10,bl+10));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+2), (int)(c.getY()-c.getSize()/2 - top+2),(int)(c.getSize()-4),(int)(c.getSize()-4));
									g2.setColor(new Color(re+20,gr+20,bl+20));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+4), (int)(c.getY()-c.getSize()/2 - top+4),(int)(c.getSize()-8),(int)(c.getSize()-8));
									g2.setColor(new Color(re+30,gr+30,bl+30));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+6), (int)(c.getY()-c.getSize()/2 - top+6),(int)(c.getSize()-12),(int)(c.getSize()-12));
									g2.setColor(new Color(re+40,gr+40,bl+40));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+8), (int)(c.getY()-c.getSize()/2 - top+8),(int)(c.getSize()-16),(int)(c.getSize()-16));
								}
								else
								{
									g2.setColor(new Color(re-10,gr-10,bl-10));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+2), (int)(c.getY()-c.getSize()/2 - top+2),(int)(c.getSize()-4),(int)(c.getSize()-4));
									g2.setColor(new Color(re-20,gr-20,bl-20));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+4), (int)(c.getY()-c.getSize()/2 - top+4),(int)(c.getSize()-8),(int)(c.getSize()-8));
									g2.setColor(new Color(re-30,gr-30,bl-30));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+6), (int)(c.getY()-c.getSize()/2 - top+6),(int)(c.getSize()-12),(int)(c.getSize()-12));
									g2.setColor(new Color(re-40,gr-40,bl-40));
									g2.fillOval((int)(c.getX()-c.getSize()/2 -left+8), (int)(c.getY()-c.getSize()/2 - top+8),(int)(c.getSize()-16),(int)(c.getSize()-16));
									
								}
							}
						}
						
					}
					
					for(int i = 0; i < messages.size(); i++)
					{
						Message m = messages.get(i);
						if(messages.get(i).permanent)
						{
							g2.setFont(new Font("Halvetica",Font.ITALIC,22));
							g2.setColor(Color.lightGray);
							g2.drawString(messages.get(i).message, 51,51);						
							g2.setColor(messages.get(i).c);
							g2.drawString(messages.get(i).message, 50,50);						
						
							g2.setFont(new Font("Halvetica",Font.PLAIN,12));
						}
						else
						{
							if(System.currentTimeMillis()-m.time < 3000)
							{
								g2.setColor(Color.LIGHT_GRAY);
								g2.drawString(messages.get(i).message, 21,21+i*20);
								g2.setColor(messages.get(i).c);
								g2.drawString(messages.get(i).message, 20,20+i*20);
	
							}
							else
								messages.remove(i);
						}
					}
					
					if(chart)
					{
						
						for(int j = 0; j < scores.size(); j++ )
						{
							g2.setColor(new Color(scores.get(j).get(0).color));
							ArrayList<ScorePoint> list = scores.get(j);
							for(int i = 0; i < list.size()-1; i++)
							{
								g2.drawLine(((list.get(i).x*500)/time)+100, 600-((list.get(i).y*500)/maxScore),((list.get(i+1).x*500)/time) +100, 600-((list.get(i+1).y*500)/maxScore));
							}
							g2.setColor(new Color(scores.get(j).get(0).color).brighter());
							for(int i = 0; i < list.size()-1; i++)
							{
								g2.drawLine(((list.get(i).x*500)/time)+99, 599-((list.get(i).y*500)/maxScore),((list.get(i+1).x*500)/time) +99, 599-((list.get(i+1).y*500)/maxScore));
							}
						}
						g2.setFont(new Font("Halvetica", Font.PLAIN, 10));
						g2.setColor(Color.BLACK);
						for(int i = 0; i < 10; i++)
						{
							g2.drawString(""+(maxScore-(i*(maxScore/10))),60,100+i*50);
						}
						for(int i = 0; i < 11; i++)
						{
							g2.drawString(""+(i*(scores.get(0).get(scores.get(0).size()-1).x/10)),100+i*50,620);
						}
					}
				}
				if(!connected && !chart)
				{
					g2.setColor(Color.BLACK);
					g2.fillRect(0,0,this.getWidth(),this.getHeight());
					g2.setFont(new Font("Halvetica", Font.ITALIC, 80));
					g2.setColor(Color.LIGHT_GRAY);
					g2.drawString("Cell Wars!!",31,81);
					g2.setColor(Color.RED);
					g2.drawString("Cell Wars!!",30,80);
					g2.drawImage(loadTransImage("cell.png"), 30,105, null);
					g2.setFont(new Font("Halvetica", Font.ITALIC, 80));
					g2.setColor(Color.WHITE);
					g2.setFont(new Font("Halvetica", Font.BOLD, 16));
					g2.drawString("Welcome to CellWars by Max Shokhirev (c).", 30, 510);
					g2.setFont(new Font("Halvetica", Font.PLAIN, 12));
					g2.drawString("Start the game by selecting connect from the menu...", 30, 535);
					g2.drawString("Once connected, you will see your cell surrounded by a bunch of particles. If other people have joined, you must\n",30,550);
					g2.drawString("play until someone loses all of their cells. At first, your cells are immoble and cannot detect food (this is bad!).",30,565);
					g2.drawString(		"To evolve a trait in one of your cells, simply pick the trait from the list and click on one of your cells. You will\n",30,580);
					g2.drawString(		"need to have enough evolution points for each trait. As your cells grow, they will divide and die from old age. \n",30,595);
					g2.drawString(		"New cells will retain the properties of the cell they split from. It will be up to you to choose which traits to evolve\n",30,610);
					g2.drawString(		"and to choose which cells will recieve the benefit of a new trait. As time goes on, global events will occur that \n",30,625);
					g2.drawString(		"facilitate the game (i.e. pheremone trails, toxic contamination, and visits from killer alien cells). GLHF.",30, 640);
					g2.setFont(new Font("Halvetica", Font.PLAIN, 22));
					g2.setColor(Color.RED);
					g2.drawString("Particles...", 550, 75);
					g2.setFont(new Font("Halvetica", Font.BOLD, 16));
					g2.setColor(Color.WHITE);
					g2.drawString("Cell = ", 500, 110);
					int team = 0;
					int re = 123*(team+1)*((team+1)*19) % 256;
					int gr = 23*(team+1)*((team+1)*13) % 256;
					int bl = 51*(team+1)*((team+1)*3) % 256;
					Point c = new Point(650,100);
					Color col = new Color(re+gr*256+bl*(256*256));
					re = col.getRed();
					gr = col.getGreen();
					bl = col.getBlue();
					int size = 20;
					g2.setColor(new Color(re+20,gr+20,bl+20));
					g2.fillOval((int)(c.getX()-size/2 +1), (int)(c.getY()-size/2 +1),(int)(size-2),(int)(size-2));
					g2.setColor(new Color(re+40,gr+40,bl+40));
					g2.fillOval((int)(c.getX()-size/2 +2), (int)(c.getY()-size/2 +2),(int)(size-4),(int)(size-4));
					{
						for(int j = 0; j < size; j+=4)
						{
							g2.setColor(new Color(re+gen.nextInt(10)-gen.nextInt(10),gr+gen.nextInt(10)-gen.nextInt(10),bl+gen.nextInt(10)-gen.nextInt(10)));
							g2.fillOval((int)(c.getX()-gen.nextDouble()*(size/2)+2 -left), (int)(c.getY()-gen.nextDouble()*(size/2)+2 - top),(int)(size/4+gen.nextInt(2)),(int)(size/4+gen.nextInt(2)));
						}
						for(int j = 0; j < size; j+=6)
						{
							g2.setColor(new Color(re+gen.nextInt(10)-gen.nextInt(10),gr+gen.nextInt(10)-gen.nextInt(10),bl+gen.nextInt(10)-gen.nextInt(10)));
							g2.drawLine((int)(c.getX()-gen.nextDouble()*(size/2)+4 -left), (int)(c.getY()-gen.nextDouble()*(size/2)+4 - top),(int)(c.getX()+gen.nextDouble()*(size/2)-4-left), (int)(c.getY()+gen.nextDouble()*(size/2)-4 - top));
						}
					}
					g2.setColor(Color.WHITE);
					g2.drawString("Food = ", 500, 160);
					team = -1;
					if(team < 0)
						team = -(team+10);
					re = 123*(team+1)*((team+1)*19) % 256;
					gr = 23*(team+1)*((team+1)*13) % 256;
					bl = 51*(team+1)*((team+1)*3) % 256;
					c = new Point(650,150);
					if(re + 40 < 256 && gr + 40 < 256&& bl + 40 < 256 )
					{
						g2.setColor(new Color(re+10,gr+10,bl+10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re+20,gr+20,bl+20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re+30,gr+30,bl+30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re+40,gr+40,bl+40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
					}
					else
					{
						g2.setColor(new Color(re-10,gr-10,bl-10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re-20,gr-20,bl-20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re-30,gr-30,bl-30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re-40,gr-40,bl-40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
						
					}
					g2.setColor(Color.WHITE);
					g2.drawString("Inorganic = ", 500, 210);
					team = -2;
					if(team < 0)
						team = -(team+10);
					re = 123*(team+1)*((team+1)*19) % 256;
					gr = 23*(team+1)*((team+1)*13) % 256;
					bl = 51*(team+1)*((team+1)*3) % 256;
					c = new Point(650,200);
					if(re + 40 < 256 && gr + 40 < 256&& bl + 40 < 256 )
					{
						g2.setColor(new Color(re+10,gr+10,bl+10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re+20,gr+20,bl+20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re+30,gr+30,bl+30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re+40,gr+40,bl+40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
					}
					else
					{
						g2.setColor(new Color(re-10,gr-10,bl-10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re-20,gr-20,bl-20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re-30,gr-30,bl-30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re-40,gr-40,bl-40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
						
					}
					g2.setColor(Color.WHITE);

					g2.drawString("Antibiotic(bad) = ", 500, 260);
					team = -3;
					if(team < 0)
						team = -(team+10);
					re = 123*(team+1)*((team+1)*19) % 256;
					gr = 23*(team+1)*((team+1)*13) % 256;
					bl = 51*(team+1)*((team+1)*3) % 256;
					c = new Point(650,250);
					if(re + 40 < 256 && gr + 40 < 256&& bl + 40 < 256 )
					{
						g2.setColor(new Color(re+10,gr+10,bl+10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re+20,gr+20,bl+20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re+30,gr+30,bl+30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re+40,gr+40,bl+40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
					}
					else
					{
						g2.setColor(new Color(re-10,gr-10,bl-10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re-20,gr-20,bl-20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re-30,gr-30,bl-30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re-40,gr-40,bl-40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
						
					}
					g2.setColor(Color.WHITE);

					g2.drawString("Pheremone = ", 500, 310);
					team = -4;
					if(team < 0)
						team = -(team+10);
					re = 123*(team+1)*((team+1)*19) % 256;
					gr = 23*(team+1)*((team+1)*13) % 256;
					bl = 51*(team+1)*((team+1)*3) % 256;
					col = new Color(re+gr*256+bl*(256*256));
					re = col.getRed();
					gr = col.getGreen();
					bl = col.getBlue();
					c = new Point(650,300);
					if(re + 40 < 256 && gr + 40 < 256&& bl + 40 < 256 )
					{
						g2.setColor(new Color(re+10,gr+10,bl+10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re+20,gr+20,bl+20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re+30,gr+30,bl+30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re+40,gr+40,bl+40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
					}
					else
					{
						g2.setColor(new Color(re-10,gr-10,bl-10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re-20,gr-20,bl-20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re-30,gr-30,bl-30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re-40,gr-40,bl-40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
						
					}
					g2.setColor(Color.WHITE);
					g2.drawString("Alien = ", 500, 360);
					team = -5;
					if(team < 0)
						team = -(team+10);
					re = 123*(team+1)*((team+1)*19) % 256;
					gr = 23*(team+1)*((team+1)*13) % 256;
					bl = 51*(team+1)*((team+1)*3) % 256;
					c = new Point(650,350);
					col = new Color(re+gr*256+bl*(256*256));
					re = col.getRed();
					gr = col.getGreen();
					bl = col.getBlue();
					if(re + 40 < 256 && gr + 40 < 256&& bl + 40 < 256 )
					{
						g2.setColor(new Color(re+10,gr+10,bl+10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re+20,gr+20,bl+20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re+30,gr+30,bl+30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re+40,gr+40,bl+40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
					}
					else
					{
						g2.setColor(new Color(re-10,gr-10,bl-10));
						g2.fillOval((int)(c.getX()-size/2 -left+2), (int)(c.getY()-size/2 - top+2),(int)(size-4),(int)(size-4));
						g2.setColor(new Color(re-20,gr-20,bl-20));
						g2.fillOval((int)(c.getX()-size/2 -left+4), (int)(c.getY()-size/2 - top+4),(int)(size-8),(int)(size-8));
						g2.setColor(new Color(re-30,gr-30,bl-30));
						g2.fillOval((int)(c.getX()-size/2 -left+6), (int)(c.getY()-size/2 - top+6),(int)(size-12),(int)(size-12));
						g2.setColor(new Color(re-40,gr-40,bl-40));
						g2.fillOval((int)(c.getX()-size/2 -left+8), (int)(c.getY()-size/2 - top+8),(int)(size-16),(int)(size-16));
						
					}
					
				}
			}


		}
			



	  public static void main(String[] args)
	  {
		  new Client();
	  }

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}



	public JField getField() {
		return Field;
	}
	  
}
