package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

public class SearchPanel extends JPanel implements ActionListener,
	ListSelectionListener
{

 private JButton SearchButton;
 private JButton StopButton;
 private JTextField PatternText;
 private JTextField FromText;
// private javax.swing.JList SubjectList;
 private JTable SubjectTable;
 private Checkbox[] Newsgroups;
 private JButton DownloadButton;
 private JButton ClearButton;
 private JLabel StatusLabel;
 private Vector FullList;
 private DefaultTableModel dtm;
 private SortFilterModel sfm;
 private JFrame parent;
 private JScrollPane jsp;
 public static Vector NewsVector;

 public SearchPanel(JFrame p)
 {
  super();
  parent = p;
//  this.addWindowListener(this);
  this.setBackground(Color.lightGray);
  this.setLayout(new BorderLayout());
  StatusLabel = new JLabel("");
  NewsVector = new Vector();
  FullList = new Vector();
  SearchButton = new JButton("Start Search");
  SearchButton.addActionListener(this); 
  SearchButton.setActionCommand("SEARCH");
  StopButton = new JButton("Stop Search");
  StopButton.setEnabled(false);
  StopButton.addActionListener(this); 
  StopButton.setActionCommand("STOPSEARCH");
  PatternText = new JTextField();
  FromText = new JTextField();
  dtm = new DefaultTableModel();
  dtm.addColumn("Index");
  dtm.addColumn("From");
  dtm.addColumn("Subject");
  dtm.addColumn("Message ID");
  dtm.addColumn("Lines");
  dtm.addColumn("Newsgroup");
  if(Newzgrabber.debug)	
	System.out.println("Getting ready to setup the filter model");
  sfm = new SortFilterModel(dtm);
  SubjectTable = new JTable(sfm);
  if(Newzgrabber.debug)
	System.out.println("Done initializing filter object");
//  sfm.addMouseListener(SubjectTable);
  sfm.setTable(SubjectTable);
  SubjectTable.getSelectionModel().addListSelectionListener(this);
  if(Newzgrabber.debug) {
	System.out.println("Getting ready to add table listener");
	System.out.println("this object is " + this.toString());
  }
  jsp = new JScrollPane(SubjectTable,
	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  DownloadButton = new JButton("Download");
  DownloadButton.addActionListener(this);
  DownloadButton.setActionCommand("DOWNLOAD");
  DownloadButton.setEnabled(false);
  ClearButton = new JButton("Clear");
  ClearButton.addActionListener(this);
  ClearButton.setActionCommand("CLEAR");
  if(Newzgrabber.debug)
	System.out.println("Done setting up clear and download buttons");
  JPanel northpanel = new JPanel();
  northpanel.setLayout(new GridLayout(2,3,5,5));
  northpanel.add(new JLabel("Subject Filter"));
  northpanel.add(PatternText);
  northpanel.add(SearchButton);
  northpanel.add(new JLabel("From Filter"));
  northpanel.add(FromText);
  northpanel.add(StopButton);
  JPanel southpanel = new JPanel(new BorderLayout());
  southpanel.add(StatusLabel,BorderLayout.CENTER);
  JPanel southbuttonpanel = new JPanel();
  southbuttonpanel.setLayout(new GridLayout(1,2,20,20));
  southbuttonpanel.add(DownloadButton);
  southbuttonpanel.add(ClearButton);
  southpanel.add(southbuttonpanel,BorderLayout.SOUTH);
  this.add(northpanel,BorderLayout.NORTH);
  this.add(jsp,BorderLayout.CENTER);
  this.add(southpanel,BorderLayout.SOUTH);
 }

 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("CLEAR"))
  {
   try
   {
    synchronized(dtm)
    {
     int dtmsize = dtm.getRowCount() - 1;
     for(int i=dtmsize ; i>=0 ; i--)
     {
      dtm.removeRow(i);
     }
     sfm.updateTable();
    }
    PatternText.setText("");
    FromText.setText("");
    synchronized(DownloadButton) 
    {
     DownloadButton.setEnabled(false);
    }
   }
   catch(Exception e) {}
  }
  else if(ae.getActionCommand().equals("SEARCH"))
  {
   if(Newzgrabber.debug)
	System.out.println("Getting ready to start search");
   sfm.setSort(true);
   SubjectTable.clearSelection();
   int maxidx = dtm.getRowCount() - 1;
   for(int i=maxidx ; i>=0 ; i--)
   {
    try
    {
     dtm.removeRow(i);
     sfm.updateTable();
    }
    catch(Exception re) { continue; }
   }

   StatusLabel.setText("");
   StatusLabel.setText("Preparing to start search");
   StopButton.setEnabled(true); 
   final String[] groups = Newzgrabber.groupspanel.getActiveGroups();
   final String Server = Newzgrabber.optionspanel.ServerText.getText().trim();
   final LineData ld = new LineData(Server,groups);
   final String SearchPattern = PatternText.getText();
   final String FromPattern = FromText.getText().trim();
   final JLabel status = StatusLabel;
   final JFrame TmpParent = parent;
   final JButton tmpstop = StopButton;
   final String Username = OptionsPanel.UsernameText.getText().trim();
   final String pTemp = new String(OptionsPanel.PasswordText.getPassword());
   final String Password = pTemp.trim();
//   final JPanel obj = this;
   final JScrollPane obj = jsp;
   Thread t = new Thread()
   {
    public void run()
    {
     obj.setCursor(new Cursor(Cursor.WAIT_CURSOR));
     if(FromPattern.length() > 0) {
	ld.setFromPattern(FromPattern);
	if(Newzgrabber.verbose)
		System.out.println("Setting the from pattern to " + 
		FromPattern);
     }
     int TotalFound = 0;
     FullList = new Vector();
     SearchPanel.NewsVector = new Vector();
     NNTP n = null;
     try
     {
      n = Newzgrabber.nf.getNewsSocket(Server); 
//      if(Username.length() > 0 && Password.length() > 0)
//	n.authInfo(Username,Password);
      if(n != null) SearchPanel.NewsVector.add(n);
      String pattern = Search.formatPattern(SearchPattern.trim());
      for(int i=0 ; i<groups.length ; i++)
      {
       String group = groups[i];
       status.setText("");
       status.setText("Searching " + group);
       String[] groupinfo = n.genericCommand("group " + group);
       if(Newzgrabber.verbose)
	System.out.println(groupinfo[0]);
       Status thestatus = new Status(groupinfo[0].trim());
       if(thestatus.statusOK() == false) continue;
       StringTokenizer st = new StringTokenizer(groupinfo[0]);
       st.nextToken();
       st.nextToken();
       int lowid = Integer.parseInt(st.nextToken());
       int highid = Integer.parseInt(st.nextToken());
       if(Newzgrabber.optionspanel.searchAll() == false)
       {
        try
        {
         long tmpid = Newzgrabber.transferpanel.getLastId(group);
         if(tmpid != -1)
		lowid = (int)tmpid;
        }
        catch(Exception longE)
        {
         if(Newzgrabber.verbose || Newzgrabber.debug)
         {
          System.err.println("MAXID Exception!\n" + longE.getMessage());
          longE.printStackTrace();
         }
        }
       }
       String xpatcmd = "xpat Subject " + lowid + "-" + highid + " " + pattern;
       if(Newzgrabber.verbose)
	System.out.println("The xpat command is\n" + xpatcmd);
       String[] list = n.genericCommand(xpatcmd);
       SongList sl = new SongList(list);
       String[] complete = sl.getCompleteSongs();
       synchronized(dtm)
       { 
        for(int j=0 ; j<complete.length ; j++)
        {
         try
         {
          Object[] va = new Object[2];
          va[0] = group;
          va[1] = sl.getSongs().get(complete[j]);
          Hashtable tmp = (Hashtable)va[1];
          long[] OrderedIds = SongInfo.getOrderedIds(tmp);
          if(OrderedIds == null) continue;
          long TotalLines = ld.getTotalLines((String)group,OrderedIds);
          if(((Integer)tmp.get("UPPERLIMIT")).intValue() < 2)
          {
           SongInfo si = (SongInfo)tmp.get(new Integer(1));
           long id = si.getId();
           long[] tmpids = new long[1];
           tmpids[0] = id;
           if(ld.getTotalLines(group,tmpids) < 100)
           {
            continue;
           }
          }	
          synchronized(FullList)
          {
	   if(FromPattern.trim().length() > 0)
 	   {
	    if(ld.getFrom(group,OrderedIds[0]) == null) continue;
	   }
           int ObjectIndex = FullList.size() + 1;
           Object[] newrow = new Object[6];
           Object[] rowinfo = new Object[4];
           newrow[0] = new Integer(ObjectIndex);
  	   newrow[1] = new String(ld.getFrom(group,OrderedIds[0]));
           newrow[2] = complete[j];
           newrow[3] = new Long(OrderedIds[0]);
           newrow[4] = new Long(TotalLines);
           newrow[5] = (String)group;
       	   dtm.addRow(newrow);
	   sfm.updateTable();
           rowinfo[0] = newrow[2];
           rowinfo[1] = newrow[5];
           rowinfo[2] = newrow[4];
           rowinfo[3] = OrderedIds;
           FullList.add(rowinfo);
          }
         }
         catch(Exception addE)
         {
          continue;
         }
	 if(FromPattern.trim().length() > 0)
		System.out.println("The from pattern was " + FromPattern);
        }
       }
      }
      TotalFound = FullList.size();
      if(groups.length > 0)
      {
       status.setText("");
       status.setText("Found " + dtm.getRowCount() + " matches");
      }
      else
      {
       MessageDialog md = new MessageDialog(TmpParent,
	"You muse have at least 1 active group in your Groups panel!",
	350,120);
       status.setText("");
      }
      n.quit();
      SearchPanel.NewsVector.remove(n);
      n = null;
     }
     catch(Exception se)
     {
      if(n != null)
      {
       try
       {
        n.quit();
       }
       catch(Exception quitE) {}
      }
     }
     sfm.sortTable(3);
     tmpstop.setEnabled(false);
     if(SearchPanel.NewsVector.contains(n))
	SearchPanel.NewsVector.remove(n);
     obj.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     if(Newzgrabber.debug)
	System.out.println("There are " +
		SearchPanel.NewsVector.size() + 
		" news vectors now");
    }
   };
   t.start();
  }    
  else if(ae.getActionCommand().equals("STOPSEARCH"))
  {
   synchronized(SearchPanel.NewsVector)
   {
	if(Newzgrabber.debug)
	 System.out.println("There are " +
		SearchPanel.NewsVector.size() + 
		" objects in the news vector before loop");
	for(int i=0 ; i<SearchPanel.NewsVector.size() ; i++)
	{
		NNTP tmp = (NNTP)SearchPanel.NewsVector.elementAt(i);
		tmp.setInterrupt(true);
	}
	if(Newzgrabber.debug)
	 System.out.println("There are " +
		SearchPanel.NewsVector.size() + 
		" objects in the news vector after loop");
   }
  }
  else if(ae.getActionCommand().equals("DOWNLOAD"))
  {
   int[] indexes = SubjectTable.getSelectedRows();
   for(int i=0 ; i<indexes.length ; i++)
   { 
    if(Newzgrabber.verbose)
	System.out.println("Adding download object for index " + 
		indexes[i]);
    int realindex = ((Integer)SubjectTable.getValueAt(indexes[i],0)).intValue();
    Newzgrabber.transferpanel.addDownload(
	(Object[])FullList.elementAt(realindex-1));
   }
   Newzgrabber.jtp.setSelectedIndex(3);
  }
 }

 public void valueChanged(ListSelectionEvent lse)
 {
  int rowcount = SubjectTable.getSelectedRowCount();
  DownloadButton.setEnabled(rowcount != 0);
 }

 public String[] getSelectedGroups()
 {
  try
  {
   Vector v = new Vector();
   String[] groups = null;
   for(int i=0 ; i<Newsgroups.length ; i++)
   {
    Checkbox tmp = (Checkbox)Newsgroups[i];
    if(tmp.getState())
    {
     v.add(tmp.getLabel());
    }
   }
   groups = new String[v.size()];
   for(int j=0 ; j<v.size() ; j++)
   {
    groups[j] = (String)v.elementAt(j);
   }
   return groups;
  }
  catch(Exception e) { return null; }
 }
 
 public void windowOpened(WindowEvent e) {}
 public void windowClosed(WindowEvent e) {}
 public void windowActivated(WindowEvent e) {}
 public void windowDeactivated(WindowEvent e) {}
 public void windowIconified(WindowEvent e) {}
 public void windowDeiconified(WindowEvent e) {}

 public static void main(String[] args)
 {
  try
  {
   JFrame test = new JFrame("Test Frame");
   try
   {
    for(int i=0; i<args.length ; i++)
    {
    }
   }
   catch(Exception parseE) {}
   SearchPanel n = new SearchPanel(test);
   n.setVisible(true);
  }
  catch(Exception e)
  {
   e.printStackTrace();
   System.err.println(e.getMessage());
  }
 }

}