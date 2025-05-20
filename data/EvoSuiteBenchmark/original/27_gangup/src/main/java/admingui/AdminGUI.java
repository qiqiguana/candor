

import com.l2fprod.gui.*;
import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.util.*;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.FactoryConfigurationError;  
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;
import java.util.Hashtable;
import java.util.Enumeration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.html.parser.AttributeList;

public class AdminGUI extends JFrame {

    JTabbedPane tabs = new JTabbedPane();
    JPanel panels[];
    JTextField textfields[] = new JTextField[10];
    JButton next, prev, print;
    JFrame gui;

    ButtonHandler bh = new ButtonHandler();
    int current = 0;

    Hashtable table;
    Document document;
    //ConfigModule cm;

    public AdminGUI() {

	super("Gangup Administration");
	
	//	cm = new ConfigModule();
	gui = this;
	table = new Hashtable();
	loadConfig("config.xml");
	buildGUI();
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(tabs, BorderLayout.CENTER);

	/*	next = new JButton("next");
	next.setActionCommand("next");
	next.addActionListener(bh);
	prev = new JButton("prev");
	prev.setActionCommand("prev");
	prev.addActionListener(bh);
	*/
	print = new JButton("print");
	print.setActionCommand("print");
	print.addActionListener(bh);
	
	//getContentPane().add(next, BorderLayout.NORTH);
	// getContentPane().add(prev, BorderLayout.SOUTH);
	getContentPane().add(print, BorderLayout.EAST);
	
	setSize(400,300);
	setVisible(true);

    }

    public void buildGUI() {
	
	NodeList sections = document.getElementsByTagName("section");
	NodeList variables = document.getElementsByTagName("variable");
	Element currentSection;
	Element currentVariable;

	panels = new JPanel[sections.getLength()];
	
	for (int i=0;i<sections.getLength();i++) {
	    
	    GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();	    
	    
	    currentSection = (Element) sections.item(i);
	    
	    panels[i] = new JPanel();
	    for (int j=0;j<variables.getLength();j++) {
	    
		currentVariable = (Element) variables.item(j);
		Element parent = (Element) currentVariable.getParentNode();
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.RELATIVE;

		if (currentSection == parent) {
		    
		    System.out.println(parent.getAttribute("name") + " -> " +
				       currentVariable.getAttribute("name"));
		    
		    panels[i].setLayout(gridbag);
		    JLabel tmpL = new JLabel(currentVariable.getAttribute("name"));
		    JTextField tmpT = new JTextField(currentVariable.getAttribute("value"));
		  
		    table.put(currentSection.getAttribute("name") + "." +
			      currentVariable.getAttribute("name"), tmpT);
		    tmpT.setText(currentVariable.getAttribute("value"));
		    gridbag.setConstraints(tmpL, c);
		    c.gridwidth = GridBagConstraints.REMAINDER;
		    gridbag.setConstraints(tmpT, c);				     
		    
		    panels[i].add(tmpL);
		    panels[i].add(tmpT);

		    

		}
		/*  if (sMap.item(i) == vMap.item(k).getParentNode()) {
		    textfields[i] = new JTextField("Testar stuff "+ i +":"+
		    vMap.item(k).getNodeValue());
		    // textfields[i].
		    panels[i].add(textfields[i]);
		    }
		    /* for(int j=0;j<children.getLength();j++) {
		    NamedNodeMap cAttrib = children.item(i).getAttributes();
		    //panels[i].add(new JLabel(map.item(j).getNodeName()));
		    //panels[i].add(new JTextField(map.item(j).getNodeValue());
		    System.out.println(cAttrib.item(0).getNodeName());
		    }
		*/
		
		
	    }
	    
	    tabs.add(panels[i], currentSection.getAttribute("name"));
	}
    
    }
	    
    public int loadConfig(String file) {

	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try {
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    document = builder.parse( new File(file) );
	} catch (SAXException sxe) {
	    // Error generated during parsing
	    Exception  x = sxe;
	    if (sxe.getException() != null)
		x = sxe.getException();
	    x.printStackTrace();
	    
	} catch (ParserConfigurationException pce) {
	    // Parser with specified options can't be built
	    pce.printStackTrace();
	    
	} catch (IOException ioe) {
	    // I/O error
	    ioe.printStackTrace();
	}

	return 0;
    }

    public static void main(String args[]) {
	// first tell SkinLF which theme to use
		try {
	    Skin theSkinToUse = SkinLookAndFeel.loadThemePack("toxicthemepack.zip");
        SkinLookAndFeel.setSkin(theSkinToUse);

        // finally set the Skin Look And Feel
        UIManager.setLookAndFeel(new SkinLookAndFeel());
	} catch (Exception e) {
	    System.out.println(e);
	    }
	AdminGUI a = new AdminGUI();

	a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private class ButtonHandler implements ActionListener {

	public void actionPerformed(ActionEvent e) {
	    
	    if (e.getActionCommand() == "print") {
		Enumeration en = table.keys();
		while (en.hasMoreElements()) {
		    String key = (String)en.nextElement();
		    JTextField f = (JTextField)table.get(key );
		    System.out.println(key + ": " + f.getText());

		}
	    } 
	}

    }

} 
