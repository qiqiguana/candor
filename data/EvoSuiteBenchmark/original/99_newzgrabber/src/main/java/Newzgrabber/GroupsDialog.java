package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class GroupsDialog extends JDialog implements ActionListener,
	ListSelectionListener
{

 private JButton SearchButton;
 private JTextField SearchText;
 private JButton AddButton;
 private JButton CancelButton;
 private JButton StatsButton;
 private ListSelectionModel lsm;
 private DefaultListModel SubModel;
 private DefaultListModel dlm;
 private JList GroupList;
 private final static String GroupFilename = "Newsgroups.ini";
 
 public GroupsDialog(Frame p,DefaultListModel d)
 {
  super(p,"Newsgroup Search Dialog");
  SubModel = d;
  this.getContentPane().setLayout(new BorderLayout());
  SearchButton = new JButton("Search");
  SearchButton.addActionListener(this);
  SearchButton.setActionCommand("SEARCH");
  StatsButton = new JButton("Group Stats");
  StatsButton.setActionCommand("STATS");
  StatsButton.addActionListener(this);
  StatsButton.setEnabled(false);
  SearchText = new JTextField();
  JPanel northpanel = new JPanel(new GridLayout(1,2,20,20));
  northpanel.add(SearchButton);
  northpanel.add(SearchText);
  dlm = new DefaultListModel();
  GroupList = new JList(dlm);
  lsm = GroupList.getSelectionModel();
  lsm.addListSelectionListener(this);
  JScrollPane jsp = new JScrollPane(GroupList,
	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  AddButton = new JButton("Add");
  CancelButton = new JButton("Cancel");
  AddButton.addActionListener(this);
  AddButton.setActionCommand("ADD");
  CancelButton.addActionListener(this);
  CancelButton.setActionCommand("CANCEL");
  JPanel southpanel = new JPanel(new GridLayout(1,3,20,30));
  southpanel.add(AddButton);
  southpanel.add(CancelButton);
  southpanel.add(StatsButton);
  this.getContentPane().add(jsp,BorderLayout.CENTER);
  this.getContentPane().add(northpanel,BorderLayout.NORTH);
  this.getContentPane().add(southpanel,BorderLayout.SOUTH);
  this.getContentPane().setSize(400,300);
  this.setSize(400,300);
 }

 public void valueChanged(ListSelectionEvent le)
 {
  StatsButton.setEnabled((GroupList.getSelectedIndices()).length == 1);
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("SEARCH"))
  {
   this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
   try
   {
    dlm.removeAllElements();
    String pattern = SearchText.getText().trim();
    BufferedReader br = new BufferedReader(new FileReader(
	Newzgrabber.Newzdirectory +
	System.getProperty("file.separator") +
	GroupFilename));
    String NextLine = null;
    while((NextLine = br.readLine()) != null)
    {
     if(pattern != null && pattern.length() > 0)
     {
      if(NextLine.indexOf(pattern) < 0) continue;
     }
     dlm.addElement(NextLine);
    }
    br.close(); 
   }
   catch(Exception e)
   {
    System.err.println(e.getMessage());
   }
   this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
  else if(ae.getActionCommand().equals("ADD"))
  {
   try
   {
    Object[] values = GroupList.getSelectedValues();
    for(int i=0 ; i<values.length ; i++)
    {
     if(SubModel.contains(values[i])) continue;
     SubModel.addElement((String)values[i]);
    }
    Object[] oa = SubModel.toArray();
    StringSorter ss = new StringSorter(true);
    for(int i=0 ; i<oa.length ; i++)
    {
     ss.addString((String)oa[i]);
    }
    String[] sortedgroups = ss.getSortedArray();
    SubModel.removeAllElements();
    for(int j=0 ; j<sortedgroups.length ; j++)
    {
     SubModel.addElement(sortedgroups[j]);
    }
    this.setVisible(false);
   }
   catch(Exception e)
   {
    System.err.println(e.getMessage());
   }
  }
  else if(ae.getActionCommand().equals("STATS"))
  {
   String group = (String)GroupList.getSelectedValue();
   StatsDialog sd = new StatsDialog(this,group);
  }
  else if(ae.getActionCommand().equals("CANCEL"))
  {
   try
   {
    dlm.removeAllElements();   
    SearchText.setText("");
    this.setVisible(false);
   }
   catch(Exception e)
   {
    System.err.println(e.getMessage());
   }
  }
 }

}