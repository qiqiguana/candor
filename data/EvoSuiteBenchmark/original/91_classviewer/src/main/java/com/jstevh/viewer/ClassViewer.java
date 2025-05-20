/* 
 * Class Viewer - program for viewing public class information
 *
 * Copyright (C) 2004-2010  James Harris 
 *
 * This program is free software; you can redistribute it 
 *
 * and/or modify it under the terms of the GNU General Public
 *
 * License as published by the Free Software Foundation;
 *
 * either version 2 of the License, or (at your option) any 
 *
 * later version.
 *
 *
 * This program is distributed in the hope that it will be 
 *
 * useful, but WITHOUT ANY WARRANTY; without even the implied 
 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 *
 * PURPOSE. See the GNU General Public License for more details.
 *
 *
 * You should have received a copy of the GNU General Public 
 *
 * License along with this program; if not, write to the Free 
 *
 * Software Foundation, Inc., 59 Temple Place, Suite 330, 
 *
 * Boston, MA 02111-1307 USA
 *
 */


package com.jstevh.viewer;

import javax.swing.*;
import javax.swing.WindowConstants;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.net.*;
import java.applet.*;
import java.util.*;

import com.jstevh.tools.*;

/**
* Viewing class that runs as an application or applet whichs
* is paired with an internal ClassInfo which is the data class. 
* <p>
* This class is for viewing data and managing.
*
* @author   James Harris
* @version  5.0.3
*/              
public class ClassViewer extends JApplet
{

	static final long serialVersionUID = -8097082256025613859L;
	
	private static String noWarrantyStr="";
	{
		noWarrantyStr+="Class Viewer version "+VERSION+", Copyright (C) "+YEAR+" by James Harris";
		noWarrantyStr+="\ncomes with ABSOLUTELY NO WARRANTY; for details";
		noWarrantyStr+="\nsee http://www.opensource.org/licenses/gpl-license.php\n";
		noWarrantyStr+="\nThis is free software, and you are welcome\n";
	
		noWarrantyStr+="to redistribute it under certain conditions;";
		noWarrantyStr+="\ncheck at http://www.opensource.org/licenses/gpl-license.php";
		noWarrantyStr+="\nfor details.";
	}
            
    JButton b1 = new JButton("Documents");
            
                        
    JTextArea txtArea, resWin, fldArea, cstrArea, infoArea;
    
    
    JComboBox txt = new JComboBox();
    JTextField txtTextField;
    
    JTextField txt2 = new JTextField(10);
                
    JPopupMenu popup = new JPopupMenu();
    
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem search = new JMenuItem("Search");
    JMenuItem goJavaDoc = new JMenuItem("Documents");
    
    JMenu[] menus = {new JMenu("Help"), new JMenu("Weblinks"), new JMenu("Command")};
    JMenuBar menuBar = new JMenuBar();
    
    JMenuItem instructions = new JMenuItem("Instructions");  
    JMenuItem about = new JMenuItem("About");  
    
    JMenuItem link1 = new JMenuItem("Class Viewer Home");  
    JMenuItem link2 = new JMenuItem("Basic Use Instructions"); 
    JMenuItem link3 = new JMenuItem("Beyond Mundane Blog");
    JMenuItem link4 = new JMenuItem("Java.net--Tools");

    JMenuItem command1 = new JMenuItem("Set classpath");
    JMenuItem command2 = new JMenuItem("Edit file");
    
    JTabbedPane panes = new JTabbedPane();
    
    static final int RESWIN=0, CSTRWIN=1, FLDWIN=2;
   
    public static boolean mainRun=false;

    public static String classPath;
    public static String currentDir;
    public static String operating_system;
    public static String file_separator;
    public static String file_editor;

    public static final String VERSION="5.0.3";
    public static final int YEAR=2011;
   
    private ClassInfo myClassInfo;
    
    private String startClass;
    
    private DirManager locManager;

    private HashMap location;


    private StringList localPackages;
    private boolean isLocalFile=false;


    private String[] strMethods;
    private String[] displayMethods;

    private Dimension scrSize;
    private boolean autoSearch=false;
    
    private String[] resWinData;
    private int resWinLine;

 
   
    public ClassViewer(){
        
       // try{
          //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       // }
       // catch (Exception e){e.printStackTrace();}

	txt.requestFocus();
        
    }
    
    public ClassViewer(String name){
        
        //try{
        //  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //}
        //catch (Exception e){e.printStackTrace();}
	
	startClass = name;
	
	txt.requestFocus();
        
    }
	                
    public void init(){
	    
	    if (mainRun){
		    	try{
				classPath = System.getProperty("java.class.path");
				currentDir = System.getProperty("user.dir");
				operating_system = System.getProperty("os.name");
				file_separator = System.getProperty("file.separator");
			}
			catch (java.security.AccessControlException access){
				access.printStackTrace();
				System.out.println("Should never go in here");				
				mainRun = false;
			}
	    }
	    
	    scrSize = getSize();//getToolkit().getScreenSize();
	    
	    double ratio = scrSize.getWidth()/1024;
	    
	    
	    
	    txtArea = new JTextArea((int)(20*ratio), (int)(40*ratio));
	    
	    txtArea.setLineWrap(true);
	    
	    fldArea = new JTextArea((int)(20*ratio), (int)(40*ratio));
	    
	    resWin = new JTextArea((int)(20*ratio), (int)(40*ratio));
	    
	    cstrArea = new JTextArea((int)(20*ratio), (int)(40*ratio));
	    
	    infoArea = new JTextArea((int)(5*ratio), (int)(20*ratio));
	    
	    //Adding in menus, first one is the help
	    //need to switch to fields with names
	    
	    menus[0].add(instructions);
	    menus[0].add(about);
	    
	    menus[1].add(link1);
	    menus[1].add(link2);
	    menus[1].add(link3);
	    menus[1].add(link4);

	    menus[2].add(command1);
	    menus[2].add(command2);


	    command2.setEnabled(false);
	    
	    
	    for (int i=0; i<(menus.length); i++){
		    menuBar.add(menus[i]);
	    }
	    
	    
	    setJMenuBar(menuBar);
            
	    if (mainRun) popup.add(copy);
	    popup.add(search);
	    popup.add(goJavaDoc);
	    
	    instructions.addActionListener(showInstructions);
	    about.addActionListener(showInfo);
	    
	    link1.addActionListener(goHome);
	    link2.addActionListener(goBUO);
	    link3.addActionListener(goBeyond);
	    link4.addActionListener(goJN);

	    command1.addActionListener(setClasspath);
	    command2.addActionListener(editFile);
	    
	    //menu items for pop-up menu getting ActionListeners
	    copy.addActionListener(copyToClipBoard);
	    search.addActionListener(srchMethods);
	    goJavaDoc.addActionListener(srchThenDocuments);
	    b1.addActionListener(getDocuments);
	    
	    txt.setEditable(true);
	    txt.setMaximumRowCount(5);
	    txt.addActionListener(inMeth);
	    
	    //Found this value by trying but put in a backup
	    Object checkComponent = txt.getComponent(2);
	    
	    try{
		    txtTextField = (JTextField)checkComponent;
	    }catch(ClassCastException e){
		    txtTextField = findTextField(txt);
	    }
	    
	    txt2.addActionListener(srchMethods2);
	    
	    txtArea.setEditable(false);
	    txtArea.addMouseListener(new ml());
	    resWin.addMouseListener(new ml());
	    
	    fldArea.setEditable(false);
	    infoArea.setEditable(false);
	    
	    Container cp = getContentPane();
	    
	    JPanel 	bottomPanel = new JPanel();
	    
	    JPanel 	bigWindows = new JPanel(),
	    		smallWindow = new JPanel(),
			classNameInput = new JPanel(),
			searchWindow = new JPanel();
			
	if (!mainRun){
		TitledBorder mode = new TitledBorder("Running in applet mode");
		mode.setTitleColor(Color.BLUE);
		mode.setTitleJustification(TitledBorder.CENTER);
		bottomPanel.setBorder(mode);
	}
	
	bigWindows.setBorder(new TitledBorder("Public Methods"));
	smallWindow.setBorder(new TitledBorder("General Class Information"));
	classNameInput.setBorder(new TitledBorder("Class Name"));
	searchWindow.setBorder(new TitledBorder("Search String"));
	
	//cp.setLayout( new BoxLayout(cp, BoxLayout.Y_AXIS ));
	
	bigWindows.add( new JScrollPane(txtArea));
	bigWindows.add( panes);

	panes.addTab("Results Window", new JScrollPane(resWin));
	panes.addTab("Constructors", new JScrollPane(cstrArea));                
	panes.addTab("Fields", new JScrollPane(fldArea));
	
	smallWindow.add(infoArea);
	
	classNameInput.add(txt);
	
	searchWindow.add(txt2);
	
	bottomPanel.add(bigWindows);
	
	bottomPanel.add(smallWindow, BorderLayout.PAGE_START);
	bottomPanel.add(classNameInput);
	
	bottomPanel.add(b1);
	
	bottomPanel.add(searchWindow);
	
	cp.add(bottomPanel);
	
	//Dimension d = getPreferredSize();
	
	//d = getSize();
	
	txt.requestFocus();
	
	
	locManager = new DirManager(mainRun);
	
	if (!locManager.loaded()){
		System.out.println(locManager.fileNotFoundError());
		
		if (locManager.fileNotFoundError()){
			String errorStr = "File packagedirectory.xml not found.";
			errorStr +="\n\nLooking in directory--\n"+locManager.currentDir+locManager.fileSeparator;
			errorStr += "\n\nPlease put that file in the same folder as the main";
			errorStr += "\nClass Viewer program and restart program.\n\n";
			
			resWin.setText(errorStr);
			
			errorStr += noWarrantyStr;
			txtArea.setText(errorStr);

		}
	}
	else {
		if(mainRun) txtArea.setText("classpath="+classPath+"\n\n" + noWarrantyStr);
		else txtArea.setText(noWarrantyStr);		
	}
	
	
	location = locManager.getDirectory();
	
	if (startClass!=null && startClass.compareTo("")!=0){
		txt.addItem(startClass);
		System.out.println("adding "+startClass);
	}


	localPackages = locManager.getLocalPackages();

	file_editor = locManager.getEditor();
	
    }
    
    /**
    * Returns the JTextField that should contain user input
    * <p>
    * 
    *
    * @param  in JComboBox to search within
    *               
    * @return      a JTextField component
    */              
    private JTextField findTextField(Container in){
	    
	    System.out.println("Looking for a JTextField inside of JComboBox");
	    
	    Component[] allComponents = in.getComponents();
	    JTextField retValue;
	    
	    for (int i=0; i<allComponents.length; i++){
		    try{
			    retValue = (JTextField)allComponents[i];
		    }catch(ClassCastException e){continue;}
		    
		    return retValue;
		    
		    
	    }
	    
	    System.out.println("Couldn't find JTextField inside of JComboBox");
	    
	    return null;
	    
    }


    /**
    * Returns information about the projects creator
    * <p>
    * This class is called when the user clicks Help and then About
    *
    * @return      string with author information
    */      
    public String getAppletInfo(){

        return "ClassViewer for Java"+'\n'+"Author: James Harris"+'\n'+"Version: "+VERSION;

    }

    /**
    * Primary ActionListener that creates ClassInfo and populates
    * data on screen 
    * <p>
    * This class is called when a class name is entered.
    *
    * 
    */              
    ActionListener inMeth = new ActionListener(){
        
        public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals("comboBoxChanged")){
		    
		    String tempStr = (String)txt.getSelectedItem();
	
		    if (tempStr==null || tempStr.trim().equals("")) return;                       
		    
		    try{
			myClassInfo = new ClassInfo(tempStr, locManager);
			txtArea.setText("");
			if (txtArea.getLineWrap()) txtArea.setLineWrap(false);
			if (resWin.getLineWrap()) resWin.setLineWrap(false);
			
			resWin.setText("");
			fldArea.setText("");
			cstrArea.setText("");

			int pos = txt.getSelectedIndex();
			if (pos!=-1) txt.removeItemAt(pos); 			
			
			txt.insertItemAt(myClassInfo.getClassName(), 0);
			if (txt.getItemCount()>4) txt.removeItemAt(4);
			txt.setSelectedIndex(0);
			
			txtTextField.setText("");			
			
			if (!myClassInfo.isInterface()){
			    if (myClassInfo.isAbstract()) infoArea.setText("Abstract class ");
			    else infoArea.setText("Class ");
			}
			else infoArea.setText("Interface ");
			
			infoArea.append(myClassInfo.getClassName()+'\n');
			
			tempStr = myClassInfo.getSuperClassName();
			
			if (tempStr!=null) infoArea.append("Superclass is "+tempStr+'\n');
			
			addInterfaces(infoArea);
			
			strMethods = myClassInfo.printMethods(ClassInfo.NO_INHERITED_METHODS);
			
			if (strMethods!=null){
	
			    displayMethods = new String[strMethods.length];
			    for (int i=0; i<strMethods.length; i++) {
				
				tempStr = removePackages(removeDeclarations(strMethods[i]));
				displayMethods[i]=tempStr;
				
				txtArea.append(tempStr+'\n');
			    }
			    
			    txtArea.setCaretPosition(0);
			}
			else txtArea.append("No public methods.");
			
			addConstructors(cstrArea);
			
			panes.setSelectedIndex(CSTRWIN);
			
			addFields(fldArea);
			
			txtTextField.setText("");

			if (localPackages.contains(myClassInfo.getClassPackage())) isLocalFile=true;


			if (isLocalFile) command2.setEnabled(true);
			else command2.setEnabled(false);
				     
		    }
		    catch (ClassNotFoundException j){
	
			txtArea.setText("Class '"+tempStr+"' not found.  Case matters and for unknown\n packages, the full class name is required.");
		    } 
		}
	}
    };

        
    /**
    * MouseAdapter class that generates pop up when you right click
    * on selected text.  It also does the search.
    *
    * 
    */                 
    class ml extends MouseAdapter{
	    
	    public void mouseReleased(MouseEvent e){
		    
		    if (myClassInfo!=null){
			    if(resWin.isFocusOwner() && e.getClickCount()<2){
				    try{
					    resWinLine = resWin.getLineOfOffset(resWin.getSelectionStart());
				    }
				    catch (javax.swing.text.BadLocationException k){
					    //if there's this exception, for now, do nothing, and move on
				    }
			    }
		    }		    
	    }
	    
	    public void mouseClicked(MouseEvent e){
		    
		    int event = e.getModifiers();
		    
		    if (myClassInfo!=null && event==InputEvent.BUTTON1_MASK  && e.getClickCount()==2){
			    
			    if (txtArea.isFocusOwner()){
				
				    resWin.setText("");
				    String[] result = myClassInfo.srchMethods(txtArea.getSelectedText(), displayMethods);
				    
				    
				    if (result==null) resWin.append("No methods found.");
				    else {
					    addStrings(resWin, result, resWinData);
					    resWin.setCaretPosition(0);
				    }
				    
				    resWinLine = 0;
				    
				    panes.setSelectedIndex(RESWIN);
			    }
			    else if(resWin.isFocusOwner()){
				    int lineStart, lineEnd;
				    
				    try{
					    
					    resWinLine = resWin.getLineOfOffset(resWin.getSelectionStart());
					    
					    lineStart = resWin.getLineStartOffset(resWinLine);
					    lineEnd = resWin.getLineEndOffset(resWinLine);
					    
					    resWin.setCaretPosition(lineStart);
					    resWin.moveCaretPosition(lineEnd);
					    
					    doDocuments(resWinLine);
					    
				    }
				    catch(Exception j){ 
					    j.printStackTrace();
				    }
				    
			    }
				
		    }
		    
		    if (myClassInfo!=null && event==InputEvent.BUTTON3_MASK){
			    popup.setVisible(true);
			    popup.show(txtArea, e.getX(),e.getY());                
		    }
	    }
    }
    
    /**
    * ActionListener that does search when "Search" chosen from pop-up
    * menu. 
    * 
    */                  
    ActionListener srchMethods = new ActionListener(){
        public void actionPerformed(ActionEvent e){

            String tempStr = txtArea.getSelectedText();
                                                    
            resWin.setText("");
                
            if (myClassInfo!=null){
                                        
                String[] result = myClassInfo.srchMethods(tempStr, displayMethods);                
                    
                if (result==null) resWin.append("No methods found.");             
                else {
			addStrings(resWin, result, resWinData);
			resWin.setCaretPosition(0);
		}
                
                panes.setSelectedIndex(RESWIN);
                
            }             
            
        }
    };
    
    /**
    * ActionListener that does search when text is entered
    * in input field. 
    * 
    */         
    ActionListener srchMethods2 = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
            String tempStr = txt2.getText().trim();
                                                    
            resWin.setText("");
                
            if (myClassInfo!=null){

		String[] result;

		if(tempStr.indexOf(" ")!=-1){

			int pos = tempStr.indexOf(" ");

			StringList hold = new StringList();

			String segment;

			int count=0;

			while (pos!=-1 && count<10){

				segment = tempStr.substring(0,pos);
				tempStr = tempStr.substring(pos).trim();

				hold.add(segment);
				
				pos = tempStr.indexOf(" ");

				count++;

			}

			hold.add(tempStr);

			String[] tempResults;

			result = myClassInfo.srchMethods(hold.get(0));

			for(int i=0; i<hold.size(); i++){

        			result = StringTools.searchStrings(result, hold.get(i));

			}

		}
		else result = myClassInfo.srchMethods(tempStr);                
                    
            if (result==null) resWin.append("No methods found.");             
            else {
			addStrings(resWin, result);
			resWin.setCaretPosition(0);
		}
                
                panes.setSelectedIndex(RESWIN);
                
            }             
            
        }
    };
     
    /**
    * ActionListener calls srchMethods and getDocuments when "Documents"
    * is selected from pop-up menu. 
    * 
    */             
    ActionListener srchThenDocuments = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
            srchMethods.actionPerformed(e);
            getDocuments.actionPerformed(e);
            
        }
    };

    /**
    * ActionListener shows About information, selected from main menu.
    * 
    */             
    ActionListener showInfo = new ActionListener(){
        public void actionPerformed(ActionEvent e){

            
           JOptionPane.showMessageDialog(null, getAppletInfo(),"About ClassViewer", JOptionPane.INFORMATION_MESSAGE);

        }
    };
    
    /**
    * ActionListener go to Class Viewer Home webpage
    * 
    */             
    ActionListener goHome = new ActionListener(){
        public void actionPerformed(ActionEvent e){
		
		callBrowser("http://classviewer.sourceforge.net");

        }
    };

    /**
    * ActionListener go to Basic Use Overview webpage
    * 
    */             
    ActionListener goBUO = new ActionListener(){
        public void actionPerformed(ActionEvent e){
		
		callBrowser("http://classviewer.sourceforge.net/useoverview.html");

        }
    };
    
        /**
    * ActionListener go to Java Tools page at Java.net
    * 
    */             
    ActionListener goJN = new ActionListener(){
        public void actionPerformed(ActionEvent e){
		
		callBrowser("http://community.java.net/javatools/");

        }
    };
    
    ActionListener goBeyond = new ActionListener(){
        public void actionPerformed(ActionEvent e){
		
		callBrowser("http://beyondmund.blogspot.com/");

        }
    };

    ActionListener setClasspath = new ActionListener(){
        public void actionPerformed(ActionEvent e){


        	String newPath = JOptionPane.showInputDialog("Set Classpath", classPath);

		if (newPath!=null){
		
			try{
					System.setProperty("java.class.path", newPath);

					classPath = System.getProperty("java.class.path");

			}
			catch (java.security.AccessControlException access){
				access.printStackTrace();
				System.out.print("Crashed trying to set classpath");				
			}

		}

        }
    };
    
    ActionListener editFile = new ActionListener(){
        public void actionPerformed(ActionEvent e){
		
		doCommand("editFile", "");

        }
    };

    
    /**
    * ActionListener shows Instructions, selected from main menu.
    * 
    */             
    ActionListener showInstructions = new ActionListener(){
        public void actionPerformed(ActionEvent e){

		String instrStr="";

        	instrStr+="Please input a full class name in the window below then";
		instrStr+="\npress <Enter>\n";
		instrStr+="\nCase matters but known package names like";
		instrStr+="\njava.lang can be left off.\n";
		instrStr+="\nSee packagedirectory.xml file for list of known packages";
		instrStr+="\nand add packages as needed.\n\n";

		instrStr+="After the methods load you can select a search string in the ";
		instrStr+="\nleft panel by highlighting some segment then right click.\n";
		instrStr+="\nOr you can double-click on some string segment.\n";
		instrStr+="\nOr you can enter a search string in the search field.  Searches\n";
		instrStr+="in that field are space delimited and case insensitive.\n";
		instrStr+="\nClick <Documents> button after a class is entered or after";
		instrStr+="\nsearching for methods to get documents to a particular method.\n";
		instrStr+="\nRecommended Java version is Java 1.5.0 or higher";


        	resWin.setText(instrStr);
		
		resWin.setCaretPosition(0);
        }
    };

    /**
    * ActionListener copies to System clipboard, selected from pop-up menu.
    * 
    */      
    ActionListener copyToClipBoard = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
            String tempStr = txtArea.getSelectedText();
            if (tempStr!=null){
               
                StringSelection selString = new StringSelection(tempStr);
                getToolkit().getSystemClipboard().setContents(selString,selString);                                
            }
            
        }
    };

    /**
    * ActionListener gets Documents
    * 
    */       
    ActionListener getDocuments = new ActionListener(){
	    
        public void actionPerformed(ActionEvent e){
		
		actionPerformed(e, resWinLine);
		
		resWin.requestFocus();
		
		int lineStart, lineEnd;
		
		try{
			lineStart = resWin.getLineStartOffset(resWinLine);
			lineEnd = resWin.getLineEndOffset(resWinLine);
			
			resWin.setCaretPosition(lineStart);
			resWin.moveCaretPosition(lineEnd);
		}
		catch (javax.swing.text.BadLocationException k){
			//if there's this exception, for now, do nothing, and move on
		}
	}
        
        public void actionPerformed(ActionEvent e, int line){
		doDocuments(line);
	}

    };
    
    private boolean doDocuments(int line){
	    
            String className, packageName, tempStr=null;            
            
            if (myClassInfo!=null){
                
                boolean check = false;
		boolean found = false;
                String[] tempArray;
                
                MethodData mData = myClassInfo.getFoundMethod(line);
                
                if (mData!=null){ 
                    packageName = mData.getMethPackage();
                    className = mData.getMethClass();
                }
                else {
                    
                    packageName = myClassInfo.getClassPackage();
                    className = myClassInfo.getClassName();
                }
		
		if (locManager==null){
			
			locManager = new DirManager(mainRun);
			location = locManager.getDirectory();
		
		}
		String locString = (String)location.get(packageName);
                    
            if ((locString!=null && locString.trim().compareTo("")!=0) || packageName.indexOf("java.")!=-1 || packageName.indexOf("javax.")!=-1){

            	    String runString="";
		    String localBrowser="";


		    if (locString!=null) runString=locString;
		    else if (packageName.indexOf("java.")!= -1) runString = (String)location.get("java.lang");
		    else if (packageName.indexOf("javax.")!= -1) runString = (String)location.get("javax.swing.border");
			
	                        
                    tempStr = className.replace('.','/');                
                        
                    try{
                        
                        
                        if (tempStr!=null){
                            tempStr+=".html";
                            if (mData!=null){
                                String tempStr2 = mData.getMethName();

                                if (tempStr2.indexOf(",")!=-1){
                                    StringBuffer buff = new StringBuffer(tempStr2);
                                    int pos=0;
                                                                        
                                    do{
                                        pos = buff.toString().indexOf(",", pos);                                        
                                        
                                        //buff.insert(++pos, ' '); 
                                        buff.insert(++pos, '%'); 
                                        buff.insert(++pos, '2');
                                        buff.insert(++pos, '0');	
			
                                        
                                    }while (buff.toString().indexOf(",", pos)!=-1);
                                    
                                    tempStr2 = buff.toString();
                                }
                                tempStr+="#"+tempStr2;
                            }
                            runString+=tempStr;
                        }

                        callBrowser(runString);
			
			return true;
		    }
		    catch (NullPointerException j){

			System.out.println("Null pointer exception"); 
			j.printStackTrace();
			return false;
		    }            
                        
                }
		else if (isLocalFile){

			if (doCommand("editMethod", mData.getMethName())) return true;
			else return false;
			
		}
                else resWin.setText("Unknown package.  Unable to lookup Documents.\n  Please add package to packagedirectory.xml file.");
		return false;
            }
	    
	    return false;
	    
	    
    }
    
    public boolean callBrowser(String runString){
	    
	    String tempStr="";

	    if (locManager==null) locManager = new DirManager(mainRun);
	    
	    if (!mainRun){
		    try{
			    AppletContext appC = getAppletContext();
			    URL ur = new URL(runString);
			    appC.showDocument(ur,"_blank");
		    }catch (MalformedURLException l){
			    l.printStackTrace();
			    return false;
		    }
	    }
	    else{
		    try{
			    Runtime r = Runtime.getRuntime();

			    tempStr = locManager.getLocalBrowser()+" ";
			    tempStr+=runString;
			    r.exec(tempStr);
		    }catch (Exception m){
			    m.printStackTrace();
			    JOptionPane.showMessageDialog(null, "Run call: "+tempStr, "Error calling browser", JOptionPane.ERROR_MESSAGE);	

			    return false;
		    }
	    }
	    
	    return true;
	    	    
    }



    private boolean doCommand(String comm, String infoString){
	    
	    String tempStr="";

	    if (locManager==null) locManager = new DirManager(mainRun);

	    
	    if (mainRun){
			
		    if (comm.compareTo("editFile")==0){

			tempStr=findFile();
			if (tempStr==null){
          		
				JOptionPane.showMessageDialog(null, "File not found.  Please check your classpath.", "Error", JOptionPane.ERROR_MESSAGE);	
				return false;

			}
			else tempStr = file_editor+" "+tempStr;
			
		
		    }
		    else if (comm.compareTo("editMethod")==0){

			int lineNum=0;

			tempStr=findFile();
			if (tempStr==null){
          		
				JOptionPane.showMessageDialog(null, "File not found.  Please check your classpath.", "Error", JOptionPane.ERROR_MESSAGE);	
				return false;

			}

			lineNum = getMethodLineNum(tempStr, infoString);			
			if (lineNum!=-1){
				if (locManager.acceptsLineNumber()) tempStr = file_editor+" "+tempStr+" "+locManager.lineNumberParameter()+lineNum;
           			else{

					tempStr = file_editor+" "+tempStr;
					JOptionPane.showMessageDialog(null, "Not all text editors will accept the line number as an argument so giving it to you here.\nClick ok and your text editor will run.  Method is at line# "+lineNum,"Line Number Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else {

				JOptionPane.showMessageDialog(null,  "Error reading file at: "+tempStr, "File read error", JOptionPane.ERROR_MESSAGE);	
			}
			
		
		    }

		    if (tempStr!=null){	

			System.out.println("tempStr="+tempStr);

		    	try{
			    Runtime r = Runtime.getRuntime();

			    r.exec(tempStr);
		    	}catch (Exception m){
			    m.printStackTrace();
			    JOptionPane.showMessageDialog(null,  "Run call: "+tempStr, "Runtime error", JOptionPane.ERROR_MESSAGE);	

			    return false;
		    	}

			return true;
		    }
			
	    }
	    return false;
	
	    	    
    }


    private String findFile(){

		String packageInfo = myClassInfo.getClassName();

		String primary_path="";

		primary_path = packageInfo.replace('.', file_separator.charAt(0));

		primary_path+=".java";


		String full_path=null;
		String[] bases;

		StringList partials = new StringList();
		String buildString="";
		char addChar;

		for (int i=0; i<classPath.length(); i++){

			addChar = classPath.charAt(i);
			
			if (addChar!=';') buildString+=addChar;
			else{

				partials.add(buildString);

				buildString="";
			}
			
			
		}

		if (buildString.length()!=0) partials.add(buildString);


		bases = partials.toArray();

		File test_path;
		String tempPath;

		for (int i=0; i<bases.length; i++){

			tempPath = bases[i];

			if (tempPath.charAt(tempPath.length()-1)!=file_separator.charAt(0)) tempPath+=file_separator;	

			test_path = new File(tempPath+primary_path);
			if (test_path.exists()){

				full_path = tempPath+primary_path;
			}
		}


		return full_path;
	
    }

    private int getMethodLineNum(String file_path, String methodName){

		StringList fullMethod = new StringList();

		String buildStr="";
		char addChar;

		fullMethod.add("public");

		int pos;
		pos = methodName.indexOf("(");

		fullMethod.add(methodName.substring(0, pos));


		for (int i=pos+1; i<methodName.length(); i++){

			addChar = methodName.charAt(i);

			if (addChar==')'){
				fullMethod.add(removePackages(buildStr));
				break;
			}

			if (addChar!=',') buildStr+=addChar;

			if (addChar==' ' || addChar==','){
				fullMethod.add(removePackages(buildStr));
				buildStr="";
			}

		}


		int count=-1;

		try{


			File temp_file = new File(file_path);
			FileReader fileReader = new FileReader(temp_file);
			BufferedReader reader = new BufferedReader(fileReader);

			String tempLine;

			count = 0;

			String[] method_array = fullMethod.toArray();

			boolean testArgs = false;
			int i=0;


			while ((tempLine = reader.readLine())!=null){

				pos=0;

				count++;

				pos = tempLine.indexOf(method_array[0]);

				if (pos!=-1){

					testArgs = true;
					i=1;

					while ( i<method_array.length && testArgs){

						pos = tempLine.indexOf(method_array[i]);

						if (pos==-1) testArgs = false;
						else tempLine = tempLine.substring(pos);
			
						i++;
					}
				}

				if (testArgs) break;
					
			}
	

		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}



		return count;
	
    }
               
    public String removeDeclarations(String in){
        
        String tempStr=null;
        
        tempStr = myClassInfo.getClassName()+'.';
        
        String[] known = {"public ",tempStr};
                               
        return StringTools.removeStrings(in, known);        
    }
    
    public String removePackages(String in){
        
        
        String[] known = myClassInfo.knownPackages;
        
        return StringTools.removeStrings(in, known);
    }       
       
                
    private void addStrings(JTextArea textArea, String[] strings){
                                
        if (strings!=null){
            
            String tempStr;
            
            for (int i=0; i<strings.length; i++) {
                
                tempStr = removePackages(removeDeclarations(strings[i]));
                
                textArea.append(tempStr+'\n');
            }
        }
    }
    
    private void addStrings(JTextArea textArea, String[] strings, String[] output){
                                
        if (strings!=null){
		
		output = new String[strings.length];
		
		String tempStr;
		
		for (int i=0; i<strings.length; i++) {
			tempStr = removePackages(removeDeclarations(strings[i]));
			textArea.append(tempStr+'\n');
			output[i] = tempStr;
		}
	}
    }
    
    private void addInterfaces(JTextArea textArea){
        
        String[] strings = myClassInfo.printInterfaces();
                        
        if (strings!=null){
            textArea.append("Interfaces:"+'\n');
            for (int i=0; i<strings.length; i++) {
                textArea.append(strings[i]+'\n');
            }
        }
        else textArea.append("No interfaces.");
    }
    
    private void addConstructors(JTextArea textArea){
        
        String[] strings = myClassInfo.printConstructors();
                        
        if (strings!=null){
            textArea.append("Constructors:"+'\n');
            String tempStr;
            
            for (int i=0; i<strings.length; i++) {
                
                tempStr = removePackages(removeDeclarations(strings[i]));
                
                textArea.append(tempStr+'\n');
            }
        }
        else if (!myClassInfo.isInterface()) textArea.setText("No public constructors.");
        else textArea.append("It's an interface.");
    }
    
    private void addFields(JTextArea textArea){
        String[] strings = myClassInfo.printFields();      
            
        if (strings!=null){
            
            String tempStr;
            
            for (int i=0; i<strings.length; i++) {
                
                tempStr = removePackages(removeDeclarations(strings[i]));
                    
                textArea.append(tempStr+'\n');
            }
        }
        else textArea.setText("No public fields.");
    
    }

    public void trySettingFocus(){

	//method call for setting focus to input field

	txt.requestFocus();

    }      
                         
    public static void main(String[] args)throws Exception{
	    
	    mainRun = true;
	    
	    JApplet viewer;
	    
	    if (args.length==1){
		    
		    viewer = new ClassViewer(args[0].trim());
	    }
	    else viewer = new ClassViewer();
	    
	    JFrame frame = new JFrame("ClassViewer");
	    
	    frame.addWindowListener(new WindowAdapter() {
		    
		    public void windowClosing(WindowEvent e) {
			    
			    System.exit(0);
		    }
	    });
	    
	    frame.getContentPane().add(viewer);
	    
	    int width,height;

	    double width_adjust,height_adjust;
	    
	    Dimension dim  = frame.getToolkit().getScreenSize();

	    //using screen resolution of my laptop where I'm currently developing
	    width_adjust = 1280/dim.getWidth();	
	    height_adjust = 800/dim.getHeight();	
	    
	    width =(int)(width_adjust*0.85*dim.getWidth());
	    height =(int)(height_adjust*0.85*dim.getHeight());
	    
	    frame.setSize(width, height);
	    
	    viewer.setSize(width,height);
	    
	    viewer.init();
	    
	    frame.setVisible(true);
	    
	    //sets focus after start of app
	    
	    ((ClassViewer)viewer).trySettingFocus();
	    
    }
}
