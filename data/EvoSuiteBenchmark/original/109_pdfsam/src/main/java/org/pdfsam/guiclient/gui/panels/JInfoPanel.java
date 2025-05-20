/*
 * Created on 07-Feb-2006
 * About panel. It shows informations about the application.
 * Copyright (C) 2006 by Andrea Vacondio.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; 
 * either version 2 of the License.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, write to the Free Software Foundation, Inc., 
 *  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.pdfsam.guiclient.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;
import org.pdfsam.console.business.ConsoleServicesFacade;
import org.pdfsam.guiclient.GuiClient;
import org.pdfsam.guiclient.configuration.Configuration;
import org.pdfsam.guiclient.exceptions.LoadJobException;
import org.pdfsam.guiclient.exceptions.SaveJobException;
import org.pdfsam.guiclient.plugins.interfaces.AbstractPlugablePanel;
import org.pdfsam.guiclient.plugins.models.PluginDataModel;
import org.pdfsam.guiclient.plugins.models.PluginsTableModel;
import org.pdfsam.i18n.GettextResource;
/**
 * "About" window. It shows informations about the software
 * @author Andrea Vacondio
 */
public class JInfoPanel extends AbstractPlugablePanel{   

	private static final long serialVersionUID = 8500896540097187242L;

	private static final Logger log = Logger.getLogger(JInfoPanel.class.getPackage().getName());
	
    private JTable tablePlugins;
    private JTextPane textInfoArea;
    private final JScrollPane textInfoScrollPanel = new JScrollPane();
    private String javaHome = "";
    private String javaVersion = "";
    private String configPath = "";
    private Map<PluginDataModel, AbstractPlugablePanel> plugins;
    
    private final InfoFocusPolicy infoFocusPolicy = new InfoFocusPolicy();
    
    private final static String PLUGIN_AUTHOR = "Andrea Vacondio";    
    private final static String PLUGIN_VERSION = "0.0.4e";

    
    /**
     * Constructor. It provides initialization.
     * @param plugins Informations about loaded plugins
     */
    public JInfoPanel(Map<PluginDataModel, AbstractPlugablePanel> plugins) {
        this.plugins = plugins;
        initialize();
    }

    private void initialize() {
    	setPanelIcon("/images/info.png");
    	 try{
             javaHome = System.getProperty("java.home");
             javaVersion = System.getProperty("java.runtime.name")+" "+System.getProperty("java.runtime.version");
             configPath = Configuration.getInstance().getConfigurationInformations();
         }catch(Exception e){
        	 log.error("error:", e);  
         }
         
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//TEXT_AREA        
        textInfoScrollPanel.setPreferredSize(new Dimension(300, 100));

        textInfoArea = new JTextPane();
        textInfoArea.setFont(new Font("Dialog", Font.PLAIN, 9));
        textInfoArea.setPreferredSize(new Dimension(300,100));
        textInfoArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        textInfoArea.setContentType("text/html");   
        textInfoArea.setText("<html><head></head><body>"+GuiClient.getApplicationName()+"<br><br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Version: ")+GuiClient.getVersion()+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Console version: ")+ConsoleServicesFacade.VERSION+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Developed by: ")+GuiClient.AUTHOR+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Build date: ")+GuiClient.getBuildDate()+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Java home: ")+javaHome+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Java version: ")+javaVersion+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Max memory: ")+(Runtime.getRuntime().maxMemory()/1048576)+"Mb<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Configuration file: ")+configPath+"<br>"
        		+GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Website: ")+"<a href=\"http://www.pdfsam.org\" title=\"pdfsam\">http://www.pdfsam.org</a>"
        		+"<br><br>"+getThanksText()+"</body></html>");
        textInfoArea.setEditable(false);
        textInfoScrollPanel.setViewportView(textInfoArea);

//END_TEXT_AREA
//TABLE_PLUGS        
        final JScrollPane installedPluginsScrollPanel = new JScrollPane();
        installedPluginsScrollPanel.setPreferredSize(new Dimension(300,100));

        tablePlugins = new JTable();
        PluginsTableModel tablePluginsModel = new PluginsTableModel(plugins);
        String[] i18nColumnsName = {GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Name"),GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Version"),GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Author")};
        tablePluginsModel.setColumnNames(i18nColumnsName);
        tablePlugins.setModel(tablePluginsModel);
        tablePlugins.setGridColor(Color.LIGHT_GRAY);
        tablePlugins.setFocusable(false);
        tablePlugins.setRowSelectionAllowed(false);
        tablePlugins.setIntercellSpacing(new Dimension(2, 2));
        tablePlugins.setBorder(new EtchedBorder(EtchedBorder.LOWERED));      
        installedPluginsScrollPanel.setViewportView(tablePlugins);
//END_TABLE_PLUGS
        add(textInfoScrollPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(installedPluginsScrollPanel);
//END_THANKS_TEXT_AREA                
}
    
    
    /**
     * @return Returns the Plugin author.
     */
    public String getPluginAuthor() {
        return PLUGIN_AUTHOR;
    }


    /**
     * @return Returns the Plugin name.
     */
    public String getPluginName() {
    	return GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"About");
    }

    /**
     * @return Returns the version.
     */
    public String getVersion() {
        return PLUGIN_VERSION;
    } 
    
    public org.dom4j.Node getJobNode(org.dom4j.Node arg0, boolean savePasswords) throws SaveJobException {
		return arg0;
	}

	public void loadJobNode(org.dom4j.Node arg0) throws LoadJobException {
		log.debug(GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Unimplemented method for JInfoPanel"));
	}

    protected String getThanksText(){
        String[] contributors = new String[]{"SourceForge", "Freshmeat", "Launchpad", "Rosetta translators", "Ubuntu", "iText", "GNU", "OpenOffice", "jcmdline", "JGoodies", "Eclipse", "Xenoage Software", "Elisa Bortolotti", "Bigpapa", "Alberto Bortolotti", "Chiara Casamatti", "dom4j", "jaxen", "log4j", "BouncyCastle", "All the donors and contributors"};
        StringBuilder contributes = new StringBuilder(GettextResource.gettext(Configuration.getInstance().getI18nResourceBundle(),"Contributes: "));
        for (int i=0; i<contributors.length; i++){
        	contributes.append(contributors[i]).append(" - ");
        }
        return contributes.toString();
    }
    public FocusTraversalPolicy getFocusPolicy(){
        return (FocusTraversalPolicy)infoFocusPolicy;
        
    }
  
    
    /**
     * 
     * @author Andrea Vacondio
     * Focus policy for info panel
     *
     */
    public class InfoFocusPolicy extends FocusTraversalPolicy {
        public InfoFocusPolicy(){
            super();
        }
        
        public Component getComponentAfter(Container CycleRootComp, Component aComponent){            
            if (aComponent.equals(textInfoArea)){
                return textInfoArea;
            }
            return textInfoArea;
        }
        
        public Component getComponentBefore(Container CycleRootComp, Component aComponent){
            
            if (aComponent.equals(textInfoArea)){
                return textInfoArea;
            }
            return textInfoArea;
        }
        
        public Component getDefaultComponent(Container CycleRootComp){
            return textInfoArea;
        }

        public Component getLastComponent(Container CycleRootComp){
            return textInfoArea;
        }

        public Component getFirstComponent(Container CycleRootComp){
            return textInfoArea;
        }
    }


	public void resetPanel() {
		
	}

}
