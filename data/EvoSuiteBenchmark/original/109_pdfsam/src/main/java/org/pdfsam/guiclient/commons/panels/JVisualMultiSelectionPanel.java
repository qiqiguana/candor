/*
 * Created on 30-Aug-2008
 * Copyright (C) 2008 by Andrea Vacondio.
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
package org.pdfsam.guiclient.commons.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.pdfsam.guiclient.business.ClosableTabbedPanelAdder;
import org.pdfsam.guiclient.commons.components.CommonComponentsFactory;
import org.pdfsam.guiclient.commons.components.sharedchooser.SharedJFileChooser;
import org.pdfsam.guiclient.commons.components.sharedchooser.SharedJFileChooserType;
import org.pdfsam.guiclient.commons.dnd.droppers.JVisualMultiSelectionDropper;
import org.pdfsam.guiclient.dto.PdfFile;
import org.pdfsam.guiclient.dto.VisualPageListItem;
/**
 * Panel to let the user select multiple pdf document and show thumbnails in a tabbed panel
 * @author Andrea Vacondio
 *
 */
public class JVisualMultiSelectionPanel extends JPanel {

	private static final long serialVersionUID = -1794723446687409093L;
	
    private final JButton openButton = CommonComponentsFactory.getInstance().createButton(CommonComponentsFactory.ADD_BUTTON_TYPE);       
    private final CloseableTabbedPane inputTabbedPanel = new CloseableTabbedPane();
    private final JPanel topPanel = new JPanel();
    private JPanel currentTopPanel = new JPanel();
    private ClosableTabbedPanelAdder tabsAdder;
    private DropTarget tabbedPanelDropTarget;
    
	public JVisualMultiSelectionPanel() {
		init();
	}

	/**
     * init the component
     */
    private void init(){
    	setLayout(new GridBagLayout());
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.setPreferredSize(new Dimension(400,30));		
		topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		topPanel.add(openButton);
		topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		topPanel.add(currentTopPanel);
		
	    Icon normal = new ImageIcon(this.getClass().getResource("/images/crossclose.png"));
	    Icon hover = new ImageIcon(this.getClass().getResource("/images/crosscloseover.png"));
	    Icon pressed = new ImageIcon(this.getClass().getResource("/images/crossclosedown.png"));
	    inputTabbedPanel.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	    inputTabbedPanel.setCloseIcons(normal, hover, pressed);
	    inputTabbedPanel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				CloseableTabbedPane source = (CloseableTabbedPane)(e.getSource());
				Component selected = source.getSelectedComponent();
				JPanel subTopPanel = new JPanel();
				if(selected != null){
					subTopPanel = ((JVisualPdfPageSelectionPanel)selected).getTopPanel();
				}
				topPanel.remove(currentTopPanel);
				currentTopPanel = subTopPanel;
				topPanel.add(currentTopPanel);
				topPanel.revalidate();
				topPanel.repaint();
			}
		});
	    
	    //files drop
	    tabsAdder = new ClosableTabbedPanelAdder(inputTabbedPanel);
	    JVisualMultiSelectionDropper dropper = new JVisualMultiSelectionDropper(tabsAdder);
	    tabbedPanelDropTarget = new DropTarget(inputTabbedPanel,dropper);
		
		openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = SharedJFileChooser.getInstance(SharedJFileChooserType.PDF_FILE, JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);
                if (fileChooser.showOpenDialog(JVisualMultiSelectionPanel.this) == JFileChooser.APPROVE_OPTION){
                	File[] chosenFiles = fileChooser.getSelectedFiles();
                    tabsAdder.addTabs(chosenFiles); 
                }               
                
            }
        });
		
		GridBagConstraints topConstraints = new GridBagConstraints();
		topConstraints.fill = GridBagConstraints.BOTH  ;
		topConstraints.gridx=0;
		topConstraints.gridy=0;
		topConstraints.gridwidth=3;
		topConstraints.gridheight=1;
		topConstraints.insets = new Insets(5,5,5,5);
		topConstraints.weightx=1.0;
		topConstraints.weighty=0.0;		
		add(topPanel, topConstraints);

		GridBagConstraints thumbConstraints = new GridBagConstraints();
		thumbConstraints.fill = GridBagConstraints.BOTH;
		thumbConstraints.gridx=0;
		thumbConstraints.gridy=1;
		thumbConstraints.gridwidth=3;
		thumbConstraints.gridheight=2;
		thumbConstraints.insets = new Insets(5,5,5,5);
		thumbConstraints.weightx=1.0;
		thumbConstraints.weighty=1.0;		
		add(inputTabbedPanel, thumbConstraints);
    }

	/**
	 * @return the tabbedPanelDropTarget
	 */
	public DropTarget getTabbedPanelDropTarget() {
		return tabbedPanelDropTarget;
	}

	/**
	 * @param tabbedPanelDropTarget the tabbedPanelDropTarget to set
	 */
	public void setTabbedPanelDropTarget(DropTarget tabbedPanelDropTarget) {
		this.tabbedPanelDropTarget = tabbedPanelDropTarget;
	}
    
	/**
	 * 
	 * @return selected elements of the current panel or an empty array
	 */
	public VisualPageListItem[] getSelectedElements(){
		VisualPageListItem[] retVal = new VisualPageListItem[0];
		if(inputTabbedPanel!=null && inputTabbedPanel.getTabCount()>0){
			Component currentTab = inputTabbedPanel.getSelectedComponent();
			if(currentTab!=null){
				retVal = ((JVisualPdfPageSelectionPanel)currentTab).getSelectedElements();
			}
		}
		return retVal;
	}
	
	/**
	 * @return an array of the input documents opened in this panel
	 */
	public PdfFile[] getInputDocuments(){
		PdfFile[] retVal = null;
		if(inputTabbedPanel!=null && inputTabbedPanel.getTabCount()>0){
			retVal = new PdfFile[inputTabbedPanel.getComponentCount()];
			for(int i=0; i<inputTabbedPanel.getComponentCount(); i++){
				JVisualPdfPageSelectionPanel currentPanel = (JVisualPdfPageSelectionPanel)inputTabbedPanel.getComponentAt(i);
				retVal[i] = new PdfFile(currentPanel.getSelectedPdfDocument(), currentPanel.getSelectedPdfDocumentPassword());
			}
		}
		return retVal;
	}
	/**
     * @return current zoomin button or null if the tabbed panel is empty
     */
	public JButton getCurrentZoomOutButton(){
		JButton retVal = null;
		if(inputTabbedPanel!=null && inputTabbedPanel.getTabCount()>0){
			Component currentTab = inputTabbedPanel.getSelectedComponent();
			if(currentTab!=null){
				retVal = ((JVisualPdfPageSelectionPanel)currentTab).getZoomOutButton();
			}
		}
		return retVal;
	}
	
    /**
     * @return current zoomin button or null if the tabbed panel is empty
     */
	public JButton getCurrentZoomInButton(){
		JButton retVal = null;
		if(inputTabbedPanel!=null && inputTabbedPanel.getTabCount()>0){
			Component currentTab = inputTabbedPanel.getSelectedComponent();
			if(currentTab!=null){
				retVal = ((JVisualPdfPageSelectionPanel)currentTab).getZoomInButton();
			}
		}
		return retVal;
	}
	
	/**
	 * @param propertyChangeListener the propertyChangeListener to set, listen the JVisualPdfPageSelectionPanel.OUTPUT_PATH_PROPERTY changes
	 */
	public void setOutputPathPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		if(tabsAdder != null){
			tabsAdder.setOutputPathPropertyChangeListener(propertyChangeListener);
		}
	}

	/**
	 * @return the openButton
	 */
	public JButton getOpenButton() {
		return openButton;
	}

	/**
	 * @return the currentTopPanel
	 */
	public JPanel getCurrentTopPanel() {
		return currentTopPanel;
	}
	/**
	 * add the files to the tabbed panel
	 * @param fileList
	 */
	public void addTabs(List<File> fileList){
		if(fileList!=null && fileList.size()>0){
			tabsAdder.addTabs(fileList); 
		}
	}
	/**
	 * add a file to the tabbed panel
	 * @param file
	 * @param password file password
	 */
	public void addTab(File file, String password){
		if(file!=null){			
			tabsAdder.addTab(file, password); 
		}
	}
	
	/**
	 * add a file to the tabbed panel
	 * @param file
	 */
	public void addTab(File file){
		addTab(file, null);
	}
	
	/**
	 * Remove all tabs
	 */
	public void removeAllTabs(){
		inputTabbedPanel.removeAll();
	}
}
