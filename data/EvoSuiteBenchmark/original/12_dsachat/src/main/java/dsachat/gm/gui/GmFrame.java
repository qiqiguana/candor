package dsachat.gm.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import dsachat.Main;
import dsachat.client.net.SecClient;
import dsachat.share.ChallReq;
import dsachat.share.Challenge;
import dsachat.share.ConnectionEndpoint;
import dsachat.share.InputEvent;
import dsachat.share.InputListener;
import dsachat.share.UserEvent;
import dsachat.share.hero.Attribute;
import dsachat.share.hero.Hero;
import dsachat.share.hero.Spell;
import dsachat.share.hero.Talent;
import dsachat.share.hero.Weapon;

/**
 * Display the Main Window for Game Master
 * There will be a chat frame and additional Frames for Heros and Enemies
 * The GM can load all online Heros. He also can load generated enemies.
 * 
 * @author bernshausen
 *
 */
public class GmFrame extends JFrame implements ActionListener, InputListener{

	
	private static final long serialVersionUID = 5032616017013540649L;
	JDesktopPane desktop;
	SecClient cl;
	private InternalGmChatFrame igcf;
	
	/**
	 * Set up the Frame.
	 * Set the size and load the internal components
	 */
	public GmFrame(){
		super("DSA Chat GameMaster");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width -inset*2,
                  screenSize.height - inset*2);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        setContentPane(desktop);
        setJMenuBar(createMenuBar());
        setDefaultLookAndFeelDecorated(true);
        //Make dragging a little faster but perhaps uglier.
//        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createInternalFrames();
	}
	
	/**
	 * Create the Chat view
	 */
	private void createInternalFrames() {
		igcf = new InternalGmChatFrame(this);
		igcf.setVisible(true);
		desktop.add(igcf);
		try {
            igcf.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
	}

	/**
	 * Create the MenuBar for this Window
	 * @return the MenuBar
	 */
	protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the file menu.
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        
        //load hero item
        JMenuItem menuItem = new JMenuItem("Load enemies");
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("load");
        menuItem.addActionListener(this);
        menuItem.setToolTipText("Multiple selections allowed");
        menu.add(menuItem);
        
        //save chat log
        menuItem = new JMenuItem("Save Chat Log");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("save");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the quit menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        
        JMenu connectMenu = new JMenu("Connection");
        connectMenu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(connectMenu);
        
        //Set up the connect menu item.
        menuItem = new JMenuItem("Connect");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("connect");
        menuItem.addActionListener(this);
        connectMenu.add(menuItem);
        
        //load hero item
        menuItem = new JMenuItem("Load online heros");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("downloadHeros");
        menuItem.addActionListener(this);
        connectMenu.add(menuItem);
        
        //menuItem for disconnecting
        menuItem = new JMenuItem("Disconnect");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_D, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("disconnect");
        menuItem.addActionListener(this);
        connectMenu.add(menuItem);
        //help section
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Help");
        menuItem.setMnemonic(KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_H, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("help");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("About");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("about");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        

        return menuBar;
    }

	
	public void actionPerformed(ActionEvent e) {
		/*
		 * switch action types
		 * most actions will send something to server
		 */
		//connect client to server
		if(e.getActionCommand().equals("connect")){
    		cl = new SecClient();
    		try {
    			//ask user to calculate parameters
    			String[] options = {"Yes","No"};
    			int res = JOptionPane.showOptionDialog(this,
    					"Calculate new DH Parameters?\n" +
    					"This can take VERY long", "new parameters",
    					JOptionPane.YES_NO_OPTION,
    					JOptionPane.QUESTION_MESSAGE,
    					null, options , options[1]);
    			boolean calcparam = (res==JOptionPane.YES_OPTION)?true:false;
				cl.initialize(calcparam);
				//connect. when we can't connect ask user again
				if(!connect(null))
					return;
				cl.addInputListener(this);
				cl.start();
				//exchange DH public keys
				cl.exchange();
				//wait for connection
				while(!cl.isConnected()){
					continue;
				}
				//send that GM has connected
				send(new UserEvent(false, "Gamemaster"));
				//TODO activate load heros
			} catch (GeneralSecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		//disconnect and quit
		if(e.getActionCommand().equals("quit")){
			try {
				cl.disconnect();
			} catch (IOException e1) {
				// exit anyway
			}
			System.exit(0);
		}
		if(e.getActionCommand().equals("disconnect")){
			try {
				cl.disconnect();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//load heros from server
		if(e.getActionCommand().equals("downloadHeros")){
			send("//getheros now");
		}
		//send chat message to server
		if(e.getActionCommand().equals("sendInput")){
			try {
				JTextField source = (JTextField)e.getSource();
				source.select(0, source.getText().length());
				cl.send(source.getText());
			} catch (IOException e1) {
				igcf.addText("*You are disconnected! Your message could not be sent");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//send a challenge and let the server execute it
		if(e.getActionCommand().equals("challenge")){
			//get the frame where the data can be found through component tree
			InternalGmHeroFrame fr = (InternalGmHeroFrame)((JRootPane)((JLayeredPane)((JPanel)
					((JPanel)((JButton)e.getSource()).getParent()).getParent())
					.getParent()).getParent()).getParent();
			//get selected item of that frame
			TreePath tp = fr.getSelectedPath();
			Hero h = (Hero)tp.getPathComponent(1);
			Object o = tp.getLastPathComponent();
			if(o == null){
				//signal user that he has to select an item
				JOptionPane.showInternalMessageDialog(fr,
						"No Item selected, nothing sent", "Warning",
						JOptionPane.INFORMATION_MESSAGE);
				//do not send a challenge
				return;
			}
			int mod = fr.getMod();
			if(fr.getFrameName().equals("Heros")){
				ChallReq cr =new ChallReq(o,mod,h.getName(),"Gamemaster");
				send(cr);
			}
			else{
				String to = fr.getReceiver();
				boolean silent = fr.silent();
				if(to.equals(h.getName())) silent=true;
				Challenge c;
				//Talent and Spell are quite the same
				if (o.getClass().equals(Talent.class)||o.getClass().equals(Spell.class)){
					Talent t = (Talent)o;
					String talName = t.getName();
					c = new Challenge(talName,mod,h,silent,
							(to.equals("All")?null:to));
				//an attribute is handled different
				}else if(o.getClass().equals(Attribute.class)){
					Attribute a = (Attribute)o;
					String shortc = a.getShortcut();
					//Note that the order is lightly changed
					c = new Challenge(shortc,h,mod,silent,
							(to.equals("All")?null:to));
				}else if(o.getClass().equals(Weapon.class)){
					Weapon w = (Weapon)o;
					c=new Challenge(w,fr.isAttack(),h,mod,silent,
							(to.equals("All")?null:to));
				}else{
					//Add new extensions here
					System.out.println(o.getClass());
					return;
				}
				send(c);
			}
		}
		//save Chat Log
		if(e.getActionCommand().equals("save")){
			File outFile; 
			JFileChooser chooser = new JFileChooser(
					new File(System.getProperty("user.home", ".")));
		    int returnVal = chooser.showSaveDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	outFile = chooser.getSelectedFile();
		    }else{
		    	return; //do nothing when user doesn't want to
		    }
		    try {
		    	outFile.createNewFile();
				PrintStream out = new PrintStream(new FileOutputStream(outFile));
				out.println(igcf.getChatlog());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//show a version 
		if(e.getActionCommand().equals("about")){
			String about = "Version: "+Main.version;
			JOptionPane.showMessageDialog(this, about, "About",
					JOptionPane.INFORMATION_MESSAGE);
		}
		//show a short help section
		if(e.getActionCommand().equals("help")){
			String help =
				"Chat:\n" +
				"\"/w <user> <text>\"\t whisper to another user\n" +
				"\"/d <num>w<sides>\"\t roll dices\n" +
				"\n" +
				"You can also change the color of the users in your text-view\n" +
				"and the background color of the text view. (by right click)\n";
			JOptionPane.showMessageDialog(this, help, "Help",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getActionCommand().equals("load")){
			loadEnemies();
		}
	}
	
	/**
	 * Load enemies from Selected Files
	 */
	private void loadEnemies(){
		//get files from user
		File[] input; 
		JFileChooser chooser = new JFileChooser(
				new File(System.getProperty("user.home", ".")));
	    FileFilter ff = new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				if(arg0.getAbsolutePath().endsWith(".xml"));
				return false;
			}
			
			public String getDescription(){
				return "XML Files";
			}
	    	
	    };

	    chooser.setFileFilter(ff);
	    chooser.setMultiSelectionEnabled(true);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       input = chooser.getSelectedFiles();
	    }else{
	    	return; //do nothing when user doesn't want to
	    }
	    //parse documents
	    Vector<Hero> heros = new Vector<Hero>();
	    for(File f : input){
	    	Hero hero = null;
    		SAXReader reader = new SAXReader();
    	    Document doc;
			try {
				doc = reader.read(f);
				hero = new Hero(doc);
				heros.addElement(hero);
			} catch (MalformedURLException e1) {//Hero could not be parsed
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {//Hero could not be parsed
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    //just update the enemies if there's already a Frame for enemies
	    Vector<Hero> oldEnemies = null;
	    InternalGmHeroFrame ighf = null;
	    for(JInternalFrame jif :desktop.getAllFrames()){
			if(jif.getClass().equals(InternalGmHeroFrame.class)){
				ighf = (InternalGmHeroFrame) jif;
				if(ighf.getFrameName().equals("Enemies")){
					oldEnemies = ighf.getData();
					break;
				}
			}
		}
	    if(oldEnemies == null){//no enemies loaded before
	    	desktop.add(new InternalGmHeroFrame(this,heros,"Enemies"));	    	
	    } else {
	    	for(Hero h1 : heros){
	    		boolean exists = false;
	    		for(Hero h2 : oldEnemies){
	    			if(h1.getName().equals(h2.getName())){
	    				exists = true;
	    				break;
	    			}
	    		}
	    		if(!exists){
	    			heros.addElement(h1);
	    		}
	    	}
	    	ighf.updateHero(heros);
	    }
	}

	/**
	 * Connect to the server.
	 * Get host and port from user. If there's an error redo.
	 * 
	 * @param msg an error message to show user whats gone wrong
	 * @return true if connection was succesful, false if user cancelled
	 */
	private boolean connect(String msg) {
		String message = (msg==null)?
				"Specify the location of the chat server\n"+
                "This must be in form of host:port\n" +
                "where host is either a name or an IPv4-Address.":
                "Something went wrong:\n" +
                msg+"\n"+
                "Please specify Server in form of\n" +
                "\"host:port\" where host is a name or an IPv4-address\n" +
                "and port is a number between 1 and 65535";
		String title = "Server address";
		int type = (msg==null)?JOptionPane.PLAIN_MESSAGE:JOptionPane.ERROR_MESSAGE;
		String s = (String)JOptionPane.showInputDialog(
                this,
                message,
                title,
                type,
                null,
                null,
                "localhost:20001");
		if(s==null){
			return false; //user canceled
		}
		try {
			String[] addr = s.split(":");
			//check IP not possible because it could be a hostname
			String ip = addr[0];
			int port = Integer.parseInt(addr[1]);
			if(port < 1 || port > 65535)
				throw new Exception("Port not in range 1 to 65535");
			cl.connect(ip, port);
		} catch (Exception e) {
			//do connect again until connection succeeded or user cancels
			return connect(e.getMessage());
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public void handleInput(InputEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getData();
		ConnectionEndpoint ce = e.getSource();
		ce.toString();//TODO just for warning. please delete
		if(o.getClass().equals(String.class)){
			String s = (String)o;
			igcf.addText(s);
		}
		if(o.getClass().equals(UserEvent.class)){
			UserEvent ev = (UserEvent)o;
			if(ev.isDisconnect()){
				igcf.deleteUser(ev.getName());
			}else{
				igcf.addUser(ev.getName());
			}
		}
		if(o.getClass().equals(Vector.class)){
			Vector<Hero> vh = (Vector<Hero>)o;
			//see if we already have a "Heros" frame
			for(JInternalFrame jif :desktop.getAllFrames()){
				if(jif.getClass().equals(InternalGmHeroFrame.class)){
					InternalGmHeroFrame ighf = (InternalGmHeroFrame) jif;
					if(ighf.getFrameName().equals("Heros")){
						ighf.updateHero(vh);
						return;
					}
				}
			}
			desktop.add(new InternalGmHeroFrame(this,vh,"Heros"));
		}
	}
	
	/**
	 * Send the object to server
	 * @param o the object to send
	 */
	private void send(Object o){
		try {
			cl.send(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
