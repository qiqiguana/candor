package sbmlreader2;

/*
 Copyright (c) 2010 Matthias Koenig

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
*/

import java.awt.event.ActionEvent;
import java.util.Properties;

import javax.swing.JOptionPane;

import cytoscape.Cytoscape;
import cytoscape.CytoscapeInit;
import cytoscape.data.ImportHandler;
import cytoscape.plugin.CytoscapePlugin;
import cytoscape.util.CytoscapeAction;


/**
 * Plugin for import of SBML files
 *
 * Matthias Koenig, Computational Systems Biochemistry, Charite Berlin
 * matthias.koenig [at] charite.de
 */

public class SBMLReaderPlugin2 extends CytoscapePlugin {
    /** SBMLReader2 version */
	public static final String VERSION = "v0.11";
	
	/**
	 * This constructor creates an action and adds it to the Plugins menu.
	 */
	public SBMLReaderPlugin2() {
		
		ImportHandler ih = Cytoscape.getImportHandler();
		ih.addFilter(new SBMLFilter());
		
		//Add the libSBML library to the classpath
		Properties props = CytoscapeInit.getProperties();
		String defaultPath = "/tmp";
		//String defaultPath = "/usr/local/lib/libsbmlj.jar";
		String libSBMLPath = props.getProperty("libSBMLPath", defaultPath);
		System.out.println("libSBMLPath: " + libSBMLPath);
		try {	
			AddToClasspath.addFile(libSBMLPath);
		}
		catch (Exception e){
			System.err.println("Could not add libsbmlj.jar to CLASSPATH");
		}
		
	    // Create a new action to respond to menu activation and set in menu
	    SBMLReader2Action action = new SBMLReader2Action();
	    action.setPreferredMenu("Plugins");
	    Cytoscape.getDesktop().getCyMenus().addAction(action);
	}
	
	@SuppressWarnings("serial")
	public class SBMLReader2Action extends CytoscapeAction {
		/** The constructor sets the text that should appear on the menu item.*/
	    public SBMLReader2Action() {super("Test SBMLReader2");}
	    
	    /** This method is called when the user selects the menu item.*/
	    public void actionPerformed(ActionEvent ae) {
			String info = "Testing ...\n";
			// Test if C++ sbmlj library is available
			try
		    {
			  System.loadLibrary("sbmlj");
			  info += "... 'sbmlj' available\n";
		    }
		    catch (java.lang.UnsatisfiedLinkError e)
			{
		        info += "... 'sbmlj' not available\n";
		        info += "... set LD_CONFIG_PATH for libSBML befor Cytoscape start.\n";
		        info += "... for example: 'export LD_LIBRARY_PATH=/usr/local/lib'\n";
		        info += "\n Test failed.";
		        JOptionPane.showMessageDialog(null, info, "SBMLReader2 Test", JOptionPane.WARNING_MESSAGE);
		        return;
		    }
		    
		    /* Extra check to be sure we have access to libSBML: */
	    	try 
		    {  
			  Class.forName("org.sbml.libsbml.libsbml");
		      info += "... 'libsbml available'\n";
		    }
		    catch (ClassNotFoundException e)
			{
		    	info += "... 'libsbml not available'\n";
		    	info += "... 'set Cytoscape startup option to location of 'libsbmlj.jar'.\n";
		    	JOptionPane.showMessageDialog(null, info, "SBMLReader2", JOptionPane.WARNING_MESSAGE);
		    	return;
		    } 
		    JOptionPane.showMessageDialog(null, info, "SBMLReader2", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
}
