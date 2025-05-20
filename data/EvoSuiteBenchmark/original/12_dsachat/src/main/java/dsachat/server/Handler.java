package dsachat.server;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dsachat.share.ChallReq;
import dsachat.share.Challenge;
import dsachat.share.ConnectionEndpoint;
import dsachat.share.InputEvent;
import dsachat.share.InputListener;
import dsachat.share.UserEvent;
import dsachat.share.hero.Hero;

/**
 * This class is a global listener for all connections.
 * It waits for input, procecces it and send the results to all
 * or one connected client
 * 
 * @author bernshausen
 *
 */
public class Handler implements InputListener{
	
	private Vector<Entry> clients = new Vector<Entry>();
	
	public Handler(){}
	
	/**
	 * add a new connection to a list
	 * Send all other users that this client has connected
	 * @param cl the connection endpoint which has connected
	 */
	public void addConnection(ConnectionEndpoint cl){
		Entry n = new Entry();
		n.ce = cl;
		n.name="";
		try {
			//wait for end of key exchange
			while(!cl.isConnected()){
				continue;
			}
			for(int i=0; i<clients.size();i++){
				UserEvent ev= new UserEvent(false,clients.elementAt(i).name);
				cl.send(ev);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clients.addElement(n);
		
	}
	
	/**
	 * Remove a connection from the list after a client disconnects
	 * @param cl the connection which was closed
	 */
	public void removeConnection(ConnectionEndpoint cl){
		String name = null;
		for(int i=0; i<clients.size(); i++){
			if(clients.elementAt(i).ce.getClientPort()==cl.getClientPort()){
				name=clients.elementAt(i).name;
				clients.removeElementAt(i);
				break;
			}
		}
		//signal other users that this one disconnects
		if(name != null){
			UserEvent newCon = new UserEvent(true,name);
			sendAllObj(newCon);
		}
	}
	

	/**
	 * handle the input from a connection
	 * The connection sends Objects, so the action differs for different
	 * Classes
	 */
	public void handleInput(InputEvent e) {
		Object o = e.getData();
		ConnectionEndpoint con = e.getSource();
		//String means chat or chat commands. also the gm can send a special command
		if(o.getClass().equals(String.class)){
			String msg = o.toString();
			//check for commands
			if(msg.startsWith("/")){
				String[] cmd = msg.substring(1).split("\\s", 2); 
				msg = cmd[1];
				//roll a dice
				if(cmd[0].matches("(d(ice)?)|(würfel)")){
					rollDice(con, msg.trim());
					return;
				}
				//whisper to another user
				if(cmd[0].matches("(w(hisper)?)|(f(lüster)?)")){
					whisper(con, msg.trim());
					return;
				}
				//display different
				if(cmd[0].matches("me")){
					sendAllObj(new String("*"+getName(con)+": "+msg));
					return;
				}
				//special command for gm to get all connected heros
				if(cmd[0].equals("/getheros")){
					sendHeros(con);
					return;
				}
				sendOneSystemMsg(con,"command /"+cmd[0]+" not found");
				return;
			}
			//if its not a command send broadcast
			sendAllStr(con,o.toString());
		}
		//execute a challenge
		if(o.getClass().equals(Challenge.class)){
			Challenge c = (Challenge)o;
			//the challenge itself produces the output
			String send = c.roll();
			//where to send the data
			String to = c.getTo();
			if(to == null){
				sendAllStr(con,send);
			}else{
				//silent means the sender doesn't get the response back
				if(!c.isSilent()){
					try {
						con.send(send);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//send to receiver
				sendToOneStr(con,to,send);
			}
		}
		//send a challenge request to the requested client
		if(o.getClass().equals(ChallReq.class)){
			ChallReq cr = (ChallReq)o;
			//send challenge to TO
			for(Entry i : clients){
				if(i.name.equals(cr.getTo())){
					try {
						i.ce.send(cr);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return;
				}
			}
			
		}
		/*
		 * A new connection opened. the hero was sent
		 * Other meaning: the hero was updated
		 */
		if(o.getClass().equals(Hero.class)){
			Hero h = (Hero)o;
			String name = updateHero(con,h);
			//if this user existed already
			if(name == null){
				System.out.println(getName(con)+" updated his hero");
				return;//exit
			}
			//else tell other clients that new user has arrived
			UserEvent newCon = new UserEvent(false,name);
			System.out.println(getName(con)+" connected");
			sendAllObj(newCon);
			try {
				//sent welcome message
				con.send("Welcome " +h.getName());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//GM connects with an userEvent
		if(o.getClass().equals(UserEvent.class)){
			if(!isGmConnected()){
				UserEvent newCon = (UserEvent)o;
				for(int i=0; i<clients.size(); i++){
					if(clients.elementAt(i).ce.getClientPort() == con.getClientPort()){
						clients.elementAt(i).name="Gamemaster";
					}
				}
				System.out.println("Gamemaster connected");
				sendAllObj(newCon);
				try {
					//sent welcome message
					con.send("Welcome " +newCon.getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				//TODO gm already connected. kill socket
			}
		}
	}

	/**
	 * send connected heros to gm
	 * @param con
	 */
	private void sendHeros(ConnectionEndpoint con) {
		Vector<Hero> vh = new Vector<Hero>();
		for(Entry e : clients){
			if(e.hero!=null){
				vh.addElement(e.hero);
			}
		}
		try {
			con.send(vh);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * test if a gm is already connected
	 * @return
	 */
	private boolean isGmConnected() {
		for(int i=0; i<clients.size(); i++){
			if(clients.elementAt(i).name.equals("Gamemaster")){
				return true;
			}
		}
		return false;
	}

	/**
	 * send a System message to one client
	 * @param con the connection to send to
	 * @param string the mesasge to send
	 */
	private void sendOneSystemMsg(ConnectionEndpoint con, String string) {
		try {
			con.send(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * send a System message to all clients 
	 * @param string the message
	 */
	private void sendAllSystemMsg(String string) {
		for(int i=0; i<clients.size(); i++){
			try {
				clients.elementAt(i).ce.send(string);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * whisper a message to one client, send also a message to sender
	 * @param con the connection this message came from
	 * @param cmd the tail of the sent message (without leading "/w ")
	 */
	private void whisper(ConnectionEndpoint con, String cmd) {
		String[] split = cmd.split("\\s",2);//split to max. 2 parts
		String to = split[0];
		String tail = split[1];
		String send = getName(con)+" whispers: "+tail;
		//send to receiver
		for(int i=0; i<clients.size();i++){
			if(clients.elementAt(i).name.equals(to)){
				sendOneSystemMsg(clients.elementAt(i).ce,send);
			}
		}
		//duplicated message for sender
		send = "You whisper to "+to+": "+tail;
		sendOneSystemMsg(con,send);
	}

	/**
	 * roll some dices
	 * @param con the connection which sent the command
	 * @param cmd the tailing command (without leading "/d ")
	 */
	private void rollDice(ConnectionEndpoint con, String cmd) {
		if(cmd.matches(".*\\+.*")){
			Matcher m = Pattern.compile("(\\d*)[wd](\\d+)\\+(\\d+).*").matcher(cmd);
			if (m.matches()){
				int count = (m.group(1)==null||m.group(1).equals(""))?
						1 : Integer.parseInt(m.group(1));
				int sides = Integer.parseInt(m.group(2));
				int mod = Integer.parseInt(m.group(3));
				String ret = getName(con)+" rolled "+cmd+" --> ";
				int sum=0;
				for(int i=0; i<count; i++){
					sum += Challenge.dice(sides);
				}
				sum += mod;
				ret += sum;
				//just send to all
				sendAllSystemMsg(ret);
			}
		}else{
			Matcher m = Pattern.compile("(\\d*)[wd](\\d+).*").matcher(cmd);
			if (m.matches()){
				int count = (m.group(1)==null||m.group(1).equals(""))?
						1 : Integer.parseInt(m.group(1));
				int sides = Integer.parseInt(m.group(2));
				
				String ret = getName(con)+" rolls "+cmd+" --> ";
				for(int i=0; i<count; i++){
					ret += Challenge.dice(sides)+ "  ";
				}
				//just send to all
				sendAllSystemMsg(ret);
			}
		}
	}

	/**
	 * Send a String message to a single client
	 * @param con the connection the message came from
	 * @param to name of the receiver
	 * @param send the String to send
	 */
	private void sendToOneStr(ConnectionEndpoint con, String to, String send) {
		String sender = getName(con);
		for(int i=0; i<clients.size(); i++){
			if(clients.elementAt(i).name.equals(to)){
				try {
					String msg = sender +": "+send;
					clients.elementAt(i).ce.send(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Update a formerly connected hero
	 * @param con the connection the update came from
	 * @param h the new hero
	 * @return null if the hero existed before,
	 *  the name of the connection if the hero was not sent before 
	 */
	private String updateHero(ConnectionEndpoint con, Hero h) {
		for(int i=0; i<clients.size(); i++){
			if(clients.elementAt(i).ce.getClientPort() == con.getClientPort()){
				clients.elementAt(i).hero=h;
				if(clients.elementAt(i).name.equals("")){
					clients.elementAt(i).name=h.getName();
					return h.getName();
				}else{
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * send a broadcast string message
	 * @param con the connection the message came from
	 * @param string the message
	 */
	private void sendAllStr(ConnectionEndpoint con, String string) {
		String sender = getName(con);
		for(int i=0; i<clients.size(); i++){
			try {
				String msg = sender +": "+string;
				clients.elementAt(i).ce.send(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * send an object to all clients
	 * @param o the object to send
	 */
	private void sendAllObj(Object o) {
		for(int i=0; i<clients.size(); i++){
			try {
				clients.elementAt(i).ce.send(o);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * return the name of a connection stored in the list
	 * @param con the connection
	 * @return the name
	 */
	private String getName(ConnectionEndpoint con) {
		for(int i=0; i<clients.size(); i++){
			if(clients.elementAt(i).ce.getClientPort() == con.getClientPort()){
				return clients.elementAt(i).name;
			}
		}
		return null;
	}
	
}

/**
 * private class for this listener to hold the connections in a list
 * @author bernshausen
 *
 */
class Entry{
	public String name;
	public ConnectionEndpoint ce;
	public Hero hero;
}
