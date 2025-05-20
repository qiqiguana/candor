/* $Id: XLoader.java,v 1.3 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package module;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Vector;

import java.lang.reflect.*;
import java.lang.annotation.*;

/**
 * This class provides the main entry point into the application. It is
 * responisble for loading the kernel and essential modules.
 *
 */
public class XLoader extends JFrame implements ActionListener {

    private final JFileChooser chooser;
    private final DefaultListModel listModel;
    private final ModuleBrowserDialog browser;

    private Kernel kernel;
    private SnoopyModule snoopy;
    private ConfigModule config;

    private JTextField topic = new JTextField();
    private JTextArea body = new JTextArea();
    private JTextArea debug = new JTextArea();

    private class SnoopyModule extends AbstractModule {

	SnoopyModule(Kernel krn) 
	    throws ModuleRegisterException {
	    super(krn);
	}

	public void sendTextMessage(String topic, String data) {
	    sendMessage(new Message(topic, data));
	}

	protected void processKernelMessage(Message m) {
	    try {
		debug.append(m.toString() + "\n");
		if (m.body.equals("REGISTERED")) {
		    subscribe("*");
		}
	    } catch (Exception e) {
		debug.append(e.toString());
	    }
	}

	protected void processMessage(Message m) {
	    debug.append(m.toString() + "\n");
	}
    };

    public XLoader(String fileName) {

	initModules(fileName);

	listModel = new DefaultListModel();
	browser = new ModuleBrowserDialog(this, kernel);
	chooser = new JFileChooser();

	initGUI();

    }

    private void initModules(String fileName) {

	try {
	    
	    kernel = new Kernel();
	    config = new ConfigModule(kernel);
	    snoopy = new SnoopyModule(kernel);

	    /* Start running the config module. */

	    config.start();
	    snoopy.start();

	    /* Load the default system configuration */

	    config.read(fileName);
	    
	    /* Find list of additional modules to load */

	    String modules = config.getCVar("Kernel", "autoload");

	    if (modules != null) {
		String[] mod = modules.split(",");
		for (int i=0; i<mod.length; i++) {
		    kernel.loadModule(mod[i]);
		}
	    }

	} catch (Exception e) {

	    e.printStackTrace(System.err);
	    System.exit(1);

	}
    }

    private void initGUI() {

	Container c = getContentPane();

	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints cons = new GridBagConstraints();

	setTitle("XLoader ($Revision: 1.3 $)");
	setBounds(100,100,400,400);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	c.setLayout(new BorderLayout());

	listModel.addElement("Kernel");
	listModel.addElement("Config");

	JPanel panel0 = new JPanel();
	JLabel label0 = new JLabel("Topic: ");
	JLabel label1 = new JLabel("Body: ");
	JLabel label2 = new JLabel("Debug: ");
	JButton button0 = new JButton("Send");
	JScrollPane scroll0 = new JScrollPane(body);
	JScrollPane scroll1 = new JScrollPane(debug);

	button0.setActionCommand("send");
	button0.addActionListener(this);
	
	cons.weightx = 0;
	cons.weighty = 0;
	cons.fill = GridBagConstraints.BOTH;
	layout.setConstraints(label0, cons);
	cons.weightx = 1;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	layout.setConstraints(topic, cons);
	layout.setConstraints(label1, cons);
	cons.weighty = 1;
	layout.setConstraints(scroll0, cons);
	cons.weighty = 0;
	layout.setConstraints(label2, cons);
	cons.weighty = 0.5;
	layout.setConstraints(scroll1, cons);
	cons.weighty = 0;
	layout.setConstraints(button0, cons);

	panel0.setLayout(layout);
	panel0.add(label0);
	panel0.add(topic);
	panel0.add(label1);
	panel0.add(scroll0);
	panel0.add(scroll1);
	panel0.add(button0);

	JList modlist = new JList(listModel);

	//c.add(BorderLayout.WEST, modlist);
	c.add(BorderLayout.CENTER, panel0);

	initMenu();

	setVisible(true);
    }

    private void initMenu() {
	
	JMenuBar menu = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem fileNew  = new JMenuItem("New");
	JMenuItem fileOpen  = new JMenuItem("Open...");
	JMenuItem fileSave  = new JMenuItem("Save...");
	JMenuItem fileQuit  = new JMenuItem("Quit");
	JMenu modMenu = new JMenu("Modules");
	JMenuItem modBrowse = new JMenuItem("Browse...");
	JMenu msgMenu = new JMenu("Messages");
	JMenuItem msgTxt    = new JMenuItem("new TextMessage...");
	JMenuItem msgNet    = new JMenuItem("new NetworkMessage...");
	JMenuItem msgAct    = new JMenuItem("new ActionMessage...");
	JMenuItem msgSend   = new JMenuItem("Send");

	/* File */

	fileNew.addActionListener(this);
	fileQuit.addActionListener(this);
	fileSave.addActionListener(this);
	fileOpen.addActionListener(this);

	fileQuit.setActionCommand("quit");
	fileNew.setActionCommand("new");
	fileOpen.setActionCommand("open");
	fileSave.setActionCommand("save");	

	fileMenu.add(fileNew);
	fileMenu.add(fileOpen);
	fileMenu.add(fileSave);
	fileMenu.addSeparator();
	fileMenu.add(fileQuit);

	/* Modules */

	modBrowse.addActionListener(this);
	modBrowse.setActionCommand("browse");
	modMenu.add(modBrowse);

	/* Messages */

	msgTxt.addActionListener(this);
	msgTxt.setActionCommand("new_text_message");
	msgMenu.add(msgTxt);

	msgNet.addActionListener(this);
	msgNet.setActionCommand("new_network_message");
	msgMenu.add(msgNet);

	msgAct.addActionListener(this);
	msgAct.setActionCommand("new_action_message");
	msgMenu.add(msgAct);

	msgMenu.addSeparator();

	msgSend.addActionListener(this);
	msgSend.setActionCommand("send");
	msgMenu.add(msgSend);

	/* Main menubar */

	menu.add(fileMenu);
	menu.add(modMenu);
	menu.add(msgMenu);

	setJMenuBar(menu);
    }

    public void actionPerformed(ActionEvent ev) {

	String cmd = ev.getActionCommand();

	if (cmd.equals("open")) {

	    int returnVal = chooser.showOpenDialog(this);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		System.out.println("You chose to open this file: " +
				   chooser.getSelectedFile().getName());
	    }
	} else if (cmd.equals("browse")) {
	    browser.setVisible(true);
	} else if (cmd.equals("send")) {
	    snoopy.sendTextMessage(topic.getText().toUpperCase(),
				   body.getText());
	} else if (cmd.equals("quit")) {
	    System.exit(0);
	}
    }

    public static final void main(String[] argv) {
	try {
	    XLoader loader = new XLoader(argv[0]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("Usage: java XLoader CONFIG");
	}
    }
}


/**
 * Provides a browser for finding modules.
 *
 */
class ModuleBrowserDialog extends JDialog implements MouseListener,
      ListSelectionListener, ActionListener {

    DefaultListModel model;
    ModuleInfo[] modv;
    JList list;

    private class ModuleFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
	    return name.matches(".*[.]class$");
	}
    };

    /** File filter to use when looking for modules. */
    private ModuleFilter filter;

    /** */
    private Kernel kernel;

    /** 
     * Creates a new instance of the ModuleBrowserDialog class.
     *
     * @param parent the JFrame that opened this dialog.
     * @param kern the Kernel associated with this browser.
     */
    ModuleBrowserDialog(JFrame parent, Kernel kern) {

	Container c;
	JScrollPane scroll;

	kernel = kern;

	setTitle("Module Browser");
	setSize(150,300);

	model = new DefaultListModel();
	filter = new ModuleFilter();

	c = getContentPane();
	c.setLayout(new BorderLayout());

	list = new JList(model);
	scroll = new JScrollPane(list);

	c.add(scroll);

	/* fixa */
	modv = findAvailableModules("obj/module");
	for (ModuleInfo m : modv) {
	    model.addElement(m.name);
	}

	list.setSelectedIndex(0);
	list.addMouseListener(this);
	list.addListSelectionListener(this);

	initMenus();
    }

    private void initMenus() {
	JPopupMenu popup    = new JPopupMenu();
	JMenuItem mnuLoad   = new JMenuItem("Load");
	JMenuItem mnuUnload = new JMenuItem("Unload");
	JMenuItem mnuDump   = new JMenuItem("Dump");
	    
	popup.add(mnuLoad);
	popup.add(mnuUnload);
	popup.add(mnuDump);
	    
	mnuLoad.addActionListener(this);
	mnuUnload.addActionListener(this);
	mnuDump.addActionListener(this);

	mnuLoad.setActionCommand("load");
	mnuUnload.setActionCommand("unload");
	mnuDump.setActionCommand("dump");

	list.setComponentPopupMenu(popup);
    }

    /** 
     * Returns the modules found in the specified directory.
     * @param path the directory in which to search for modules.
     * @return the modules found in the specified directory.
     */
    private ModuleInfo[] findAvailableModules(String path) {

	Vector<ModuleInfo> r = new Vector<ModuleInfo>();

	try {

	    Class ref = Class.forName("module.Module");
	    File dir = new File(path);

	    /* check all .class files in the specified directory */

	    for (String f : dir.list(filter)) {

		/* remove trailing .class suffix and get class. */

		String name = f.substring(0, f.length()-6);
		Class cls = Class.forName("module." + name); // fixa!

		/* ignore interfaces */

		if (!cls.isInterface()) {

		    /* check if ref is super type of cls */

		    if (ref.isAssignableFrom(cls)) {
			ModuleInfo m = new ModuleInfo(cls.getAnnotations());
			m.name = (m.name == null) ? m.name = name : m.name;
			r.add(m);
		    }
		}
	    }

	} catch (Exception e) { /* FileNotFoundException */
	    e.printStackTrace(System.err);
	}

	/*
  	   ModuleInfo[] mv = new ModuleInfo[r.size()];
	   for (int i=0; i<mv.length; i++) { mv[i] = r.get(i); }
	   r = null;
	*/

	return r.toArray(new ModuleInfo[r.size()]);
    }

    /* Listeners */

    public void actionPerformed(ActionEvent ev) {

	String cmd = ev.getActionCommand();

	try {
	    if (cmd.equals("load")) {

		kernel.loadModule("module." + 
				  modv[list.getSelectedIndex()].name);

	    } else if (cmd.equals("unload")) {

		kernel.unloadModule("module." +
				    modv[list.getSelectedIndex()].name);

	    } else if (cmd.equals("dump")) {
		System.err.println(modv[list.getSelectedIndex()]);
	    }
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}
    }

    public void mouseClicked(MouseEvent ev) {
	int button = ev.getButton();

	if ((button & MouseEvent.BUTTON1_MASK) > 0) {
	    if (ev.getClickCount() >= 2) {
		System.err.println("double-click");
		System.err.println(list.getSelectedValue());
	    }
	} else if ((button & MouseEvent.BUTTON2_MASK) > 0) {
	    
	}
    }

    public void valueChanged(ListSelectionEvent ev) {}

    /* unused */

    public void mouseEntered(MouseEvent ev) {}
    public void mouseExited(MouseEvent ev) {}
    public void mousePressed(MouseEvent ev) {}
    public void mouseReleased(MouseEvent ev) {}

}
