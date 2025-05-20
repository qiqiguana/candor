package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.beans.*;

public class GroupsPanel extends JPanel implements ActionListener,
	ListSelectionListener
{

 private Frame Parent;
 private JList SubscribeList;
 private JList ActiveList;
 private JScrollPane SubscrivePane;
 private JScrollPane ActivePane;
 private JPanel SubscribePanel;
 private JPanel ActivePanel;
 private JSplitPane splitter;
 private JButton SearchButton;
 private JLabel EastBorder;
 private JLabel WestBorder;
 private OptionsPanel options;
 private DefaultListModel subdlm;
 private DefaultListModel actdlm;
 private JButton ActivateButton;
 private JButton UnsubscribeButton;
 private JButton DeactivateButton;
 private JPanel northpanel;
 private final static String IniFilename = "Newzgrabber.ini";
 private final static String GroupFilename = "Newsgroups.ini";

 public GroupsPanel(Frame p)
 {
  super(new BorderLayout());
  Parent = p;
  
  SearchButton = new JButton("Subscribe To Newsroup(s)");
  SearchButton.addActionListener(this);
  SearchButton.setActionCommand("SEARCH");
  EastBorder = new JLabel("     ");
  WestBorder = new JLabel("     ");
  northpanel = new JPanel(new BorderLayout(50,0));
  northpanel.add(SearchButton,BorderLayout.CENTER);
  northpanel.add(EastBorder,BorderLayout.EAST);
  northpanel.add(WestBorder,BorderLayout.WEST);
  subdlm = new DefaultListModel();
  actdlm = new DefaultListModel();
  SubscribeList = new JList(subdlm); 
  ActiveList = new JList(actdlm);
  SubscribeList.addListSelectionListener(this);
  ActiveList.addListSelectionListener(this);
  SubscribePanel = new JPanel(new BorderLayout());
  ActivePanel = new JPanel(new BorderLayout());
  SubscribePanel.add(new JLabel("Subscribed Groups"),BorderLayout.NORTH);
  ActivePanel.add(new JLabel("Active Groups (Required for Search)"),
	BorderLayout.NORTH);
  JScrollPane SubscribePane = new JScrollPane(SubscribeList,
	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  JScrollPane ActivePane = new JScrollPane(ActiveList,
	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  SubscribePanel.add(SubscribePane,BorderLayout.CENTER);
  JPanel SubSouth = new JPanel(new GridLayout(1,2,20,20));
  ActivateButton = new JButton("Activate");
  ActivateButton.addActionListener(this);
  ActivateButton.setActionCommand("ACTIVATE");
  ActivateButton.setEnabled(false);
  UnsubscribeButton = new JButton("Unsubscribe");
  UnsubscribeButton.addActionListener(this);
  UnsubscribeButton.setActionCommand("UNSUBSCRIBE");
  UnsubscribeButton.setEnabled(false);
  SubSouth.add(ActivateButton);
  SubSouth.add(UnsubscribeButton);
  SubscribePanel.add(SubSouth,BorderLayout.SOUTH);
  ActivePanel.add(ActivePane,BorderLayout.CENTER);
  JPanel ActSouth = new JPanel(new GridLayout(1,2,20,20));
  DeactivateButton = new JButton("Deactivate");
  DeactivateButton.addActionListener(this);
  DeactivateButton.setActionCommand("DEACTIVATE");
  DeactivateButton.setEnabled(false);
  ActSouth.add(DeactivateButton);
  ActivePanel.add(ActSouth,BorderLayout.SOUTH); 
  splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
  splitter.setLeftComponent(SubscribePanel);
  splitter.setRightComponent(ActivePanel); 
  this.add(splitter,BorderLayout.CENTER);
//  this.add(SearchButton,BorderLayout.NORTH);
  this.add(northpanel,BorderLayout.NORTH);
  this.initializeOptions();
 }

 public String[] getActiveGroups()
 {
  try
  {
   String[] ActiveGroups = new String[actdlm.size()];
   for(int i=0 ; i<actdlm.size() ; i++)
   {
    ActiveGroups[i] = (String)actdlm.elementAt(i);
   }
   return ActiveGroups;
  }
  catch(Exception e) { return null; }
 }

 private void sortList(DefaultListModel d)
 {
  try
  {
   Object[] oa = d.toArray();
   StringSorter ss = new StringSorter(true);
   for(int i=0 ; i<oa.length ; i++)
   {
    ss.addString((String)oa[i]);
   }
   String[] sorted = ss.getSortedArray();
   d.removeAllElements();
   for(int j=0 ; j<sorted.length ; j++)
   {
    d.addElement(sorted[j]);
   }
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

 private void initializeOptions()
 {
  try
  {
   Ini ini = new Ini(
	Newzgrabber.Newzdirectory +
	System.getProperty("file.separator") +
	IniFilename,
	"Newsgroups");
   Hashtable h = ini.readIni();
   Enumeration grouparray = h.keys();
   while(grouparray.hasMoreElements())
   {
    String group = (String)grouparray.nextElement();
    String value = (String)h.get(group);
    subdlm.addElement(group);
    if(value.toLowerCase().equals("on"))
    {
     actdlm.addElement(group);
    }
   }
   this.sortList(subdlm);
//   this.sortList(actdlm);
  }
  catch(Exception e) {}
 }

 public void valueChanged(ListSelectionEvent ae)
 {
  if(ae.getSource() == SubscribeList)
  {
   int idx = SubscribeList.getSelectedIndex();
   ActivateButton.setEnabled(idx != -1);
   UnsubscribeButton.setEnabled(idx != -1);
  }
  else if(ae.getSource() == ActiveList)
  {
   int idx = ActiveList.getSelectedIndex();
   DeactivateButton.setEnabled(idx != -1);
  }
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("SEARCH"))
  {
   try
   {
    File f = new File(
	Newzgrabber.Newzdirectory +
	System.getProperty("file.separator") +
	GroupFilename);
    if(f.exists() == false)
    {
     MessageDialog md = new MessageDialog(Parent,"You must first build\n" +
	"a Newsgroups file from\n" +
	"the Options panel's\n" +
	"\"Build Group List\"");
     return;
    }
   }
   catch(Exception e)
   {
    System.err.println(e.getMessage());
   }
   GroupsDialog gd = new GroupsDialog(Parent,subdlm);
   gd.setVisible(true);
   this.sortList(subdlm);
  }
  else if(ae.getActionCommand().equals("ACTIVATE"))
  {
   Object[] activates = SubscribeList.getSelectedValues();
   for(int i=0 ; i< activates.length ; i++)
   {
    if(actdlm.contains(activates[i])) continue;
    actdlm.addElement((String)activates[i]);
   }
   try
   {
    Object[] subs = subdlm.toArray();
    subdlm.removeAllElements();
    for(int i=0 ; i<subs.length ; i++)
    {
     subdlm.addElement((String)subs[i]);
    }
    ActivateButton.setEnabled(false);
    UnsubscribeButton.setEnabled(false);
   }
   catch(Exception subE) {}
   this.sortList(subdlm);
//   this.sortList(actdlm);
  }
  else if(ae.getActionCommand().equals("UNSUBSCRIBE"))
  {
   Object[] unsubscribes = SubscribeList.getSelectedValues();
   for(int i=0 ; i<unsubscribes.length ; i++)
   {
    subdlm.removeElement(unsubscribes[i]);
   }
   ActivateButton.setEnabled(false);
   UnsubscribeButton.setEnabled(false);
   this.sortList(subdlm);
//   this.sortList(actdlm);
  }
  else if(ae.getActionCommand().equals("DEACTIVATE"))
  {
   Object[] deactivates = ActiveList.getSelectedValues();
   for(int i=0 ; i<deactivates.length ; i++)
   {
    actdlm.removeElement(deactivates[i]);
   }
   DeactivateButton.setEnabled(false);
   this.sortList(subdlm);
//   this.sortList(actdlm);
  }
 }

 public void updateIni()
 {
  try
  {
   Ini ini = new Ini(
	Newzgrabber.Newzdirectory +
	System.getProperty("file.separator") +
	IniFilename,
	"Newsgroups");
   LinkedList ll = new LinkedList();
   for(int i=0 ; i<subdlm.size() ; i++)
   {
    String group = (String)subdlm.elementAt(i); 
    String value = "OFF";
    if(actdlm.contains(group)) value = "ON";
    String[] s = {group,value};
    ll.add(s);
   }
   for(int j=0 ; j<actdlm.size() ; j++)
   {
    String group = (String)actdlm.elementAt(j);
    if(subdlm.contains(group)) continue;
    String[] s = {group,"ON"};
    ll.add(s);
   }
   ini.writeIni(ll);
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}