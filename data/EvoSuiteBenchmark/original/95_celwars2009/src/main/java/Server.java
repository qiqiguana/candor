import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import sun.misc.GC.LatencyRequest;


public class Server extends Thread
{

	private ArrayList<ClientThread> players = new ArrayList<ClientThread>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> send = new ArrayList<Entity>();
    private int time;
    private Server self;
    private int worldSize;
    private Random gen = new Random();
    private boolean playerJoined = false;
    private ClientListener CL;
    private String sounds = "";
	private ArrayList<ClientThread> alive = new ArrayList<ClientThread>();
	private long stime = 0;
	
	public void addSound(String s)
	{
		sounds += s;
	}
	
	public void run()
	{
		while(true)
		{
			if(System.currentTimeMillis()-stime > 50) 
			{
				stime = System.currentTimeMillis();
				Itterate();
			}
		}
	}
		
	public void Itterate() 
	{
		//timer.stop();
		time++;
			//System.err.println("Time "+time);
		if(time % 1005 == 0)
			System.gc();
		//poll for new clients...
		sounds = "Sound ";
		int totalFood = 0;
		int totalPoison = 0;
		for(int i = 0; i < players.size(); i++)
			players.get(i).run();
		if(playerJoined)
		{
			for(int i = 0; i < entities.size(); i++)
			{
				send.add(entities.get(i));
			}
			playerJoined = false;
			System.err.print("player joined");
		}
		else
		{
			//System.out.println("Normal "+time);
			alive.clear();
			for(int i = 0; i < players.size(); i++)
			{
				if(players.get(i).getP().getNumCells() > 0 )alive.add(players.get(i));
				players.get(i).getP().setScore(0);
			}
			if(alive.size() == 1 && players.size() > 1 &&  time > 1500)
			{
				//that player won
				alive.get(0).sendMessage("Message2 2000 You won!!! You are the only surviving cell line remaining!");
				alive.get(0).sendMessage("MP3 victory.mp3");
				alive.get(0).sendMessage("Sound victory.wav -1 -1;");
				this.stop();
				alive.get(0).disconnect();
				System.exit(0);
			}
			if(alive.size() == 0 && time > 1500)
			{
				System.out.println("Restarting the server...");
				time = 0;
			}
			for(int i = 0; i < entities.size(); i++)
			{
				if(entities.get(i).getTeam() == -1)
					totalFood+= entities.get(i).getSize();
				if(entities.get(i).getTeam() == -3)
					totalPoison+= entities.get(i).getSize();
				if(entities.get(i).getTeam() > -1 && players.size() > entities.get(i).getTeam())
				{
					players.get(entities.get(i).getTeam()).getP().setScore((players.get(entities.get(i).getTeam()).getP().getScore()+(int)(entities.get(i).getSize())));
				}					
				entities.get(i).integrate(self);
			}
			//System.err.println("Done integrating entities for time "+time);
			if(time > 3000 && time % 500 ==0 && gen.nextBoolean())
			{
				double x = gen.nextDouble()*(worldSize/2) +worldSize/2-gen.nextDouble()*(worldSize/2);
				double y = gen.nextDouble()*(worldSize/2) +worldSize/2-gen.nextDouble()*(worldSize/2);
				double dx =  gen.nextDouble()*75 -gen.nextDouble()*75;
				double dy =  gen.nextDouble()*75 -gen.nextDouble()*75;
				String spores = "";
				for(int i = 0; i < time/400; i++)
				{
					Entity E = new Entity(x,y,-4);
					x+=dx+gen.nextDouble()*9 -gen.nextDouble()*9;
					y+= dy+gen.nextDouble()*9 -gen.nextDouble()*9;
					int val = (i+1-time/800);
					if(val == 0)
						val = 1;
					E.setSize(Math.abs(30/val)+(8+time/1000));
					entities.add(E);
					spores+= E.getProperties().get("id").intValue()+" "+E.getX()+" "+E.getY()+" "+E.getSize()+" "+getTeamColor(-4)+";";
				}
				sendToAll("Circles "+spores);
				sendToAll("Message 100 Fight!!!");
			
			}
			if(time > 8000 && time < 15000 && time % 318 ==0 && gen.nextInt(10)  < 5 )
			{
				String spores = "";
				for(int i = 0; i < time/200; i++)
				{
					Entity E = new Entity(gen.nextDouble()*(worldSize) ,gen.nextDouble()*(worldSize) ,-3);
					E.setSize(gen.nextDouble()*5+(time/1000));
					entities.add(E);
					spores +=E.getProperties().get("id").intValue()+" "+E.getX()+" "+E.getY()+" "+E.getSize()+" "+getTeamColor(-3)+";";
				}
				sendToAll("Circles "+spores);
				sendToAll("Message 100 Toxic Contamination!!!");
			
			}
			if(time > 15000 && time % 2000 == 0 && gen.nextBoolean() )
			{
				//create terminator cell line!!!!
				Entity E = new Entity(gen.nextDouble()*(worldSize) ,gen.nextDouble()*(worldSize) ,-5);
				E.setSize(gen.nextDouble()*10+8);
				E.getProperties().put("Complex Flagella",1.0);
				E.setVx(gen.nextGaussian()*20);
				E.setVy(gen.nextGaussian()*20);
				entities.add(E);
				sendToAll("Circles "+E.getProperties().get("id").intValue()+" "+E.getX()+" "+E.getY()+" "+E.getSize()+" "+getTeamColor(-5)+";");
				sendToAll("Message 100 An alien form of life has appeared!!!");
			}
			if(totalFood < worldSize/6 && gen.nextInt(30) > 27)
			{
				Entity E = new Entity(gen.nextDouble()*worldSize,gen.nextDouble()*worldSize,-1);
				E.setSize(gen.nextDouble()*20+15);
				entities.add(E);
				sendToAll("Circles "+E.getProperties().get("id").intValue()+" "+E.getX()+" "+E.getY()+" "+E.getSize()+" "+getTeamColor(-1)+";");

			}
			if(totalPoison < worldSize/10 && gen.nextInt(30) > 27)
			{
				Entity E = new Entity(gen.nextDouble()*worldSize,gen.nextDouble()*worldSize,-3);
				E.setSize(gen.nextDouble()*10+10);
				entities.add(E);
				sendToAll("Circles "+E.getProperties().get("id").intValue()+" "+E.getX()+" "+E.getY()+" "+E.getSize()+" "+getTeamColor(-3)+";");
			}
			alive.clear();
			for(int i = 0; i < players.size(); i++)
			{
				if(players.get(i).getP().getNumCells() > 0 && time > 1)
				{

					players.get(i).getP().setPower(players.get(i).getP().getPower()+0.1);
					String sending = "";
					if(players.get(i).getP().getObserving() != null)
					{
						Entity Ent = players.get(i).getP().getObserving();
						players.get(i).sendMessage("Center "+(int)Ent.getX()+" "+(int)Ent.getY());
					}

					if(time % 5 == 0)
					{
						sending = "Update "+getTeamColor(i)+" "+time+" "+players.get(i).getP().getScore()+" "+players.get(i).getP().getPower();
						String name = "";
						if(players.get(i).getP().getObserving() != null)
						{
							Entity Ent = players.get(i).getP().getObserving();
							players.get(i).sendMessage("Center "+(int)Ent.getX()+" "+(int)Ent.getY());
							if(Ent.getTeam() == -3)
								name = "Antibiotic: A toxic particle that will harm any cell. Location "+(int)Ent.getX()+", "+(int)Ent.getY();
							else if(Ent.getTeam() == -2)
								name = "Innorganic solid: Dense particle that usually cannot be processed by cells. Location "+(int)Ent.getX()+", "+(int)Ent.getY();
							else if(Ent.getTeam() == -1)
								name = "Organic solid: Slowly growing clumps of metabolizable molecules easily processed by cells. Location "+(int)Ent.getX()+", "+(int)Ent.getY();
							else if(Ent.getTeam() == -4)
								name = "Pheremone: Small particle that strongly attracts all life. Location "+(int)Ent.getX()+", "+(int)Ent.getY();
							else
							{
								name = "Cell for player "+(Ent.getTeam()+1)+": This is cell with a diameter of "+(int)Ent.getSize()+" Location "+(int)Ent.getX()+", "+(int)Ent.getY()+" It has the following traits: cell";
								Iterator<String> itr = Ent.getProperties().keySet().iterator();
								while(itr.hasNext())
								{
									String next = itr.next();
									if(next.compareTo("age")!= 0 && next.compareTo("id") != 0)
									name+=", "+next;
									else if(next.compareTo("id") != 0)
										name+=", age = "+Ent.getProperties().get("age").intValue();
								}
								sending+=" "+name;
							}
							
						}
					}
					for(int j = 0; j < players.size(); j++)
					{
						if(i != j)
						sending += "; "+getTeamColor(j)+" "+time+" "+players.get(j).getP().getScore()+" "+players.get(j).getP().getPower();
					}
					players.get(i).sendMessage(sending);

				}
			}

		}
		sendCircles();
		sendToAll(sounds);
		//timer.start();
	}
		



		private void sendCircles()
		{
			if(players.size() > 0)
			{
				//System.err.println("Sending circles...");
				String s = "Circles ";
				
				for(int i = 0; i < send.size(); i++)
				{
					Entity e = send.get(i);
					s+=e.getProperties().get("id").intValue()+" "+e.getX()+" "+e.getY()+" "+e.getSize()+" "+getTeamColor(e.getTeam())+";";
					if(e.getSize() <= 8)
						send.remove(e);
				}

				sendToAll(s);
			}
		}


		

	private int getTeamColor(int team) {
		if(team < 0)
			team = -(team+10);
		int r = 123*(team+1)*((team+1)*19) % 256;
		int g = 23*(team+1)*((team+1)*13) % 256;
		int b = 51*(team+1)*((team+1)*3) % 256;
		return r+g*256+b*(256*256);
	}
	public void createWorld()
	{
		//generates the basic world with food and everything then integrates the world for a while
		
		int foods = gen.nextInt((int)(worldSize/3))+worldSize/4;
		while(foods > 0)
		{
			int size = gen.nextInt(30)+10;
			foods-= size;
			Entity f = new Entity(gen.nextInt(worldSize), gen.nextInt(worldSize), -1);
			f.setSize(size);
			entities.add(f);
			
		}
		int rocks = gen.nextInt((int)(worldSize/3))+worldSize/4;
		while(rocks > 0)
		{
			int size = gen.nextInt(20)+20;
			rocks-= size;
			Entity r = new Entity(gen.nextInt(worldSize), gen.nextInt(worldSize), -2);
			r.setSize(size);
			entities.add(r);
		}
		int poison = gen.nextInt((int)(worldSize/10))+worldSize/10;
		while(poison > 0)
		{
			int size = gen.nextInt(20)+15;
			poison -= size;
			Entity r = new Entity(gen.nextInt(worldSize), gen.nextInt(worldSize), -3);
			r.setSize(size);

			entities.add(r);
		}
		
		for(int i = 0; i < 50; i++)
		{
			for(int j = 0; j < entities.size(); j++)
			{
				entities.get(j).integrate(this);
			}
		}
		
		//entities.add(new Entity(100,100, 0));
		
		
	}

	public Server (int port)
	{
		self = this;
		worldSize = Integer.parseInt(JOptionPane.showInputDialog(null, "worldSize"));
		createWorld();
		CL = new ClientListener(this, port );
		CL.start();
		this.start();
	}
	
	
	public class ClientThread
	{
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;
		Server creator;
		Random gen = new Random();
		Player p;
		
		public Player getP() {
			return p;
		}

		public ClientThread(Socket theSock, Server theServer)
		{
			clientSocket = theSock;
			//clientSocket.setPerformancePreferences();
			creator = theServer;
			try {
				out = new PrintWriter(theSock.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in = new BufferedReader(new InputStreamReader(theSock.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(theServer.getPlayers().size() < theServer.maxPlayer())
			{
				p = new Player();
				double x = gen.nextDouble()*worldSize;
				double y = gen.nextDouble()*worldSize;
				sendMessage("Message 0 Welcome to CellWars! You are player "+(creator.getPlayers().size()+1)+".");
				Entity e =  new Entity(x, y, creator.getPlayers().size());
				creator.getEntities().add( e);
				sendMessage("Size"+worldSize);
				sendMessage("Center "+(int)x+" "+(int)y);
				p.setObserving(e);
				creator.getPlayers().add(this);	
				for(int i = 0; i < send.size();i++)
				{
					if(send.get(i).getTeam() == creator.getPlayers().size()-1)
						send.remove(i);
				}
				creator.playerJoined = true;

			
			}
			else
				sendMessage("Message 255 This server only allows "+creator.getPlayers().size()+" players to play. Try again later.");
			
			
		}
		
		public void run() 
		{
			if( clientSocket.isConnected() && !clientSocket.isClosed() && clientSocket.isBound())
			{
				String incomming =""; 
				try {
					if(clientSocket.getInputStream().available() > 0)
					incomming = in.readLine();
					//need to process command by GameServer;
					if(incomming == null)
					{
						this.disconnect();
					}
					creator.processCommand(incomming, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch(NullPointerException ee)
				{
					
					if(creator == null)
						System.err.println("null creator");
					else if(p == null)
						System.err.println("null thisPlayer");
					else if(incomming == null)
						System.err.println("null incomming");
					else if(in == null)
						System.err.println("null in");
					else
						System.err.println("null ?");
					ee.printStackTrace();
					
				}
				
			}
		}
		
		@SuppressWarnings("deprecation")
		public void disconnect()
		{

			try
			{
				this.clientSocket.close();
			}
			catch(IOException e)
			{
				System.err.println("Could not disconnect");
			}
		}
		
		public String readLine()
		{
			String incomming = null;
			try {
				incomming = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return incomming;
		}
		public void sendMessage(String msg)
		{
			
			if(this.out != null && msg != null)
			{
				this.out.println(msg);
				this.out.flush();
			}
			else
				System.err.println("NULL MESSAGE BEING SENT!");
		}

	}
	
	
	public class ClientThread2
	{
		Socket clientSocket;
		PrintWriter out;
//		BufferedReader in;
		Server creator;
//		Random gen = new Random();
//		Player p;
		

		public ClientThread2(Socket theSock, Server theServer)
		{
			clientSocket = theSock;
			creator = theServer;
			try {
				out = new PrintWriter(theSock.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sendMessage("Message 200 Sorry but the game has already begun! Try again later.");
			this.disconnect();
			
		}
		
		public void run() 
		{
		}
		
		@SuppressWarnings("deprecation")
		public void disconnect()
		{

			try
			{
				this.clientSocket.close();
			}
			catch(IOException e)
			{
				System.err.println("Could not disconnect");
			}
			try {
				creator.getPlayers().remove(this);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		public void sendMessage(String msg)
		{
			
			if(this.out != null && msg != null)
			{
				this.out.println(msg);
				this.out.flush();
			}
			else
				System.err.println("NULL MESSAGE BEING SENT!");
		}

	}
	
	
	public void sendMessage(String message, int player)
	{
		players.get(player).sendMessage(message);
	}
	

	private class ServerWindow extends JFrame
	{
		JLabel start = new JLabel("Close to kill server");
		Server s = null;
		public ServerWindow(Server s2)
		{
			s= s2;
			this.setTitle("Server Control");
			this.setSize(120,40);
			this.getContentPane().add(start);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addWindowListener(new ServerCloseListener(this));
		}
	}
	
	private class ServerCloseListener implements WindowListener
	{
		ServerWindow sw = null;
		public ServerCloseListener(ServerWindow sw)
		{
			this.sw = sw;
		}
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosing(WindowEvent arg0) {
			sw.s = null;
			System.out.println("Killed the server");
		}

		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
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


	public static void main(String[] args)
	{
		Server s = new Server(4000);
		s.new ServerWindow(s);
	}
	
	private class ClientListener extends Thread
	{
		Server creator;
		ServerSocket socket;
		public ClientListener(Server theServer, int port)
		{
			this.setPriority(Thread.MIN_PRIORITY);
			creator = theServer;
			try {
				socket = new ServerSocket(port);
				System.out.println("CellWars server listening on port: "+port+". The game will begin withing a few minutes...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run()
		{
			while(true)
			{
				Socket newSock = null;
				try {
					newSock = socket.accept();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
				if(newSock != null)
				{
					if(creator.getTime() < 1000)
					{
						ClientThread  newClient = new ClientThread(newSock, creator);
						//players.add(newClient);
						System.out.println("New client connected to host...");
					}
					else
					{
						
						creator.sendToAll("Message 100 This game has officially started. No other players may join at this time. Good luck.");
						PrintWriter out = null;
						try {
							out = new PrintWriter(newSock.getOutputStream());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ClientThread2  newClient = new ClientThread2(newSock, creator);
						return;
					}
					//newClient.sendMessage("Welcome to PuzzleMUD. You can only progress by solving the puzzles around you. The farther you progress, the more elaborate the puzzle, and the more significant the reward. You can perform several <blue>actions<black>.");
					//players.add(newClient);
				}
			}
		}
	
	}


	public int maxPlayer() {
		
		return 8;
	}

	public void processCommand(String incomming, ClientThread c) {
		Player p = c.getP();
		//System.err.println("Got "+incomming);
		if(incomming == null || incomming.length() == 0)
			return;
		if(incomming.startsWith("Place Food"))
		{
			Scanner S = new Scanner(incomming.substring(10, incomming.length()));
			int size = S.nextInt();
			int x = S.nextInt();
			int y = S.nextInt();

			if(p.getPower() > size)
			{
				p.setPower(p.getPower()-size);
				Entity E = new Entity(x,y,-1);
				E.setSize(15);
				entities.add(E);
				sendToAll("Circles "+E.getProperties().get("id").intValue()+" "+x+" "+y+" "+15+" "+getTeamColor(-1)+";");
				addSound("plop2.wav "+(int)x+" "+(int)y+";");
			}
			else
				c.sendMessage("Message 100 You don't have enough power to place that!");
			
		}
		else if(incomming.startsWith("info") )
		{
			Scanner S = new Scanner(incomming.substring(5, incomming.length()));

			int x = S.nextInt();
			int y = S.nextInt();
			Entity Ent = null;
			for(int i = 0; i < entities.size(); i++)
			{
				Entity e = entities.get(i);
				if(e.dist(e.getX(),e.getY(),x,y) <= e.getSize()/2)
					Ent = e;
			}
			if(Ent != null)
			{
				p.setObserving(Ent);
			}
		}
		else if(incomming.startsWith("Center") )
		{
			ArrayList<Entity> yours = new ArrayList<Entity>();
			for(int i = 0; i < entities.size(); i++)
			{
				if(entities.get(i).getTeam() == players.indexOf(c))
				{
					yours.add(entities.get(i));
				}
			}
			Entity one = yours.get(gen.nextInt(yours.size()));
			c.sendMessage("Center "+(int)one.getX()+" "+(int)one.getY());
			c.getP().setObserving(one);
		}
		else if(incomming.startsWith("Place Pheremone") )
		{
			Scanner S = new Scanner(incomming.substring(15, incomming.length()));
			int Cost = S.nextInt();
			int x = S.nextInt();
			int y = S.nextInt();
			if(p.getPower() >= Cost)
			{
				p.setPower(p.getPower()-Cost);
				Entity E = new Entity(x,y,-4);
				E.setSize(10);
				entities.add(E);
				sendToAll("Circles "+E.getProperties().get("id").intValue()+" "+x+" "+y+" "+10+" "+getTeamColor(-4)+";");
				addSound("plop2.wav "+(int)x+" "+(int)y+";");
			}
		}
		else if(incomming.startsWith("Place Inorganic") )
		{
			Scanner S = new Scanner(incomming.substring(15, incomming.length()));
			int Cost = S.nextInt();
			int x = S.nextInt();
			int y = S.nextInt();
			if(p.getPower() >= Cost)
			{
				p.setPower(p.getPower()-Cost);
				Entity E = new Entity(x,y,-2);
				E.setSize(15);
				entities.add(E);
				sendToAll("Circles "+E.getProperties().get("id").intValue()+" "+x+" "+y+" "+15+" "+getTeamColor(-2)+";");
				addSound("splat.wav "+(int)x+" "+(int)y+";");
			}
		}
		else if(incomming.compareTo("quit") == 0)
		{
			for(int i = 0; i < players.size(); i++)
			{
				if(players.get(i).getP().equals(p))
				{
					System.err.println("Removing player "+i);


					for(int j = 0; j < entities.size();j++)
					{
						if(entities.get(j).getTeam() == i)
							entities.remove(j);
					}
					for(int j = 0; j < send.size(); j++)
					{
						if(send.get(j).getTeam() == i)
							send.remove(j);
					}
					players.get(i).getP().setNumCells(0);
					players.get(i).disconnect();
					players.remove(i);
					sendToAll("Message 300 Player "+i+" has left the game!!!!");				}
					break;
			}
		}
		else
		{
			StringTokenizer tokener = new StringTokenizer(incomming, ",");
			String trait = "";
			double value = 0;
			int x = 0;
			int y = 0;
			if(tokener.hasMoreTokens());
			trait = tokener.nextToken();
			if(tokener.hasMoreTokens())
			{
				Scanner S = new Scanner(tokener.nextToken());
				value = S.nextInt();
				x = S.nextInt();
				y = S.nextInt();
			}
			System.err.println("got "+incomming);
			Entity Ent = null;
			for(int i = 0; i < entities.size(); i++)
			{
				Entity e = entities.get(i);
				if(e.getTeam() == getPlayerIndex(c.getP()))
				{
					if(e.dist(e.getX(),e.getY(),x,y) <= e.getSize()/2)
						Ent = e;
				}
			}
			if(Ent != null)
			{
				if(Ent.getProperties().get(trait) == null)
				{
					if(c.getP().getPower() >= value)
					{
						Ent.getProperties().put(trait, 1.0);
						c.getP().setPower(c.getP().getPower()-value);
						c.sendMessage("Message 100 One of your cells has evolved "+trait);
						addSound("plop3.wav "+(int)x+" "+(int)y+";");
					}
					else
						c.sendMessage("Message 100 You need more evolution points to evolve that property in that cell");
				}
				else
					c.sendMessage("Message 200000 That cell already has that property!");
			}
			else
				c.sendMessage("Message 300000 You need to click on one of your cells for it to evolve this property");
		}

		
	}

	private int getPlayerIndex(Player p) {
		for(int i = 0; i < players.size(); i++)
		{
			if(players.get(i).getP().equals(p))
				return i;
		}
		return 0;
	}
	public void sendToAll(String string) {
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).sendMessage(string);
		}
	}

	public ArrayList<ClientThread> getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}



	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getWorldSize() {
		return worldSize;
	}

	public void setWorldSize(int worldSize) {
		this.worldSize = worldSize;
	}

	public ArrayList<Entity> getSend() {
		return send;
	}
	
	
}
