package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;

public class TransferPanel extends JPanel implements Runnable,
	ListSelectionListener,ActionListener
{
 
 private Frame Parent;
 private JSplitPane jsp;
 public static DefaultTableModel downloading;
 private JTable downloadtable;
// public static DefaultListModel finished;
// private JList finishedtable;
 private static boolean Queued = false;
 public static int CurrentDownloads = 0;
 static Hashtable Started;
 private static Hashtable Waiting;
 private Vector DownloadVector;
 private Thread monitor;
 private static JLabel CurrentDownloadsLabel;
 private static JLabel FinishedLabel;
 private JButton AbortButton;
 private ListSelectionModel lsm;
 private Hashtable Messageids;
 protected static String OptionsFilename = "Newzgrabber.ini";

 public TransferPanel(Frame p)
 {
  super(new BorderLayout());
  this.initializeMessageids();
  if(Messageids == null)
 	Messageids = new Hashtable();
  Parent = p;
  DownloadVector = new Vector();
  Started = new Hashtable();
  Waiting = new Hashtable();
  downloading = new DefaultTableModel();
  downloading.addColumn("Title");
  downloading.addColumn("Status");
  downloading.addColumn("Decode Type");
  downloading.addColumn("Location");
  downloading.addColumn("Newsgroup");
  downloadtable = new JTable();
  downloadtable.setModel(downloading);
  lsm = downloadtable.getSelectionModel();
  lsm.addListSelectionListener(this);
  CurrentDownloadsLabel = new JLabel("0");
  FinishedLabel = new JLabel("0");
  JPanel northlabel = new JPanel(new GridLayout(1,4));
  JPanel northpanel = new JPanel(new BorderLayout());
//  northlabel.add(new JLabel("Download(s) Status"));
  northlabel.add(new JLabel("Current Downloads:"));
  northlabel.add(CurrentDownloadsLabel);
  northlabel.add(new JLabel("Finished downloads:"));
  northlabel.add(FinishedLabel);
  northpanel.add(northlabel,BorderLayout.NORTH);
  JPanel southpanel = new JPanel(new GridLayout(1,2));
  AbortButton = new JButton("Abort Transfer(s)");
  AbortButton.setActionCommand("ABORT");
  AbortButton.addActionListener(this);
  AbortButton.setEnabled(false);
  southpanel.add(AbortButton);
//  JPanel southlabel = new JPanel(new GridLayout(1,2));
//  southlabel.add(FinishedLabel);
//  southlabel.add(FinishedLabel);
//  southpanel.add(southlabel,BorderLayout.NORTH);
  JScrollPane scrollnorth = new JScrollPane(downloadtable,
	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  northpanel.add(scrollnorth,BorderLayout.CENTER);
//  finished = new DefaultListModel();
//  finishedtable = new JList(finished);
//  finishedtable.addMouseListener(this);
//  finishedtable.setSelectionMode(0);
//  JScrollPane scrollsouth = new JScrollPane(finishedtable);
//	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//  southpanel.add(scrollsouth,BorderLayout.CENTER);
  jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
  jsp.setTopComponent(northpanel);
//  jsp.setBottomComponent(southpanel);
  int ParentHeight = Parent.getHeight();
//  jsp.setDividerLocation((int)(ParentHeight/2));
  this.add(jsp,BorderLayout.CENTER);
  this.add(southpanel,BorderLayout.SOUTH);
  monitor = new Thread(this);
  monitor.start();
 }

 public long getLastId(String g)
 {
  if(Newzgrabber.verbose || Newzgrabber.debug)
  {
   System.out.println("The Messageids has " + Messageids.size() + " elements");
  }
  if(Messageids.containsKey(g))
	return(((Long)Messageids.get(g)).longValue());
  return -1;
 }

// Currently removed MouseListener so following methods are not needed
/*
 public void mouseEntered(MouseEvent me) {}
 public void mouseExited(MouseEvent me) {}
 public void mousePressed(MouseEvent me) {}
 public void mouseReleased(MouseEvent me) {}
*/
/*
 public void mouseClicked(MouseEvent me)
 {
  try
  {
   if(me.getClickCount() == 2)
   {

    if(Newzgrabber.verbose)
	System.out.println("Mouse selection detected!");
    String os = System.getProperty("os.name");
    if(os.toLowerCase().indexOf("windows") > -1)
    {
//     final String filename = ((String)finishedtable.getSelectedValue()).trim();
     String ext = filename.substring(filename.lastIndexOf(".") + 1);
     final String association = WinUtility.getFileAssociation(
	ext.toLowerCase().trim());
	 try
	 {
 	  int lastspace = association.trim().lastIndexOf(" ");
	  if(lastspace > -1)
	  {
	   File checkfile = new File(filename);
	   if(checkfile.exists())
	   {
//	    StringBuffer cmdline = new StringBuffer("");
	    String Program = association.trim().substring(0,lastspace);
	    String runcmd = Program + " \"" + 
		WinUtility.getEscapedFilename(checkfile) + "\"";
	    Runtime.getRuntime().exec(runcmd);
	    if(Newzgrabber.verbose)
	    {
		System.out.println("Executing:\n" + runcmd);
	    }
   	   }
	   else
 	   {
	    if(Newzgrabber.verbose)
		System.out.println("No such file!\n" + filename);
	   }
   	  }
	 }
	 catch(Exception runE)
	 {
	  if(Newzgrabber.verbose)
		System.err.println(runE.getMessage());
	 }
    }
   }
  }
  catch(Exception winrunE)
  {
   if(Newzgrabber.verbose)
   {
	System.err.println("Problem running external call");
	System.err.println(winrunE.getMessage());
   }
  }	
 }
*/

 public void valueChanged(ListSelectionEvent le)
 {
  AbortButton.setEnabled((downloadtable.getSelectedRowCount() != 0));
 }

 public static synchronized void decrementCurrentDownloads()
 {
  try
  {
   int current = Integer.parseInt(CurrentDownloadsLabel.getText().trim());
   current--;
   CurrentDownloadsLabel.setText(String.valueOf(current));
  }
  catch(Exception e) {}
 }

 public static synchronized void incrementCurrentDownloads()
 {
  try
  {
   int current = Integer.parseInt(CurrentDownloadsLabel.getText().trim());
   current++;
   CurrentDownloadsLabel.setText(String.valueOf(current));
  }
  catch(Exception e) {}
 }

 public static synchronized void incrementFinished()
 {
  try
  {
   int done = Integer.parseInt(FinishedLabel.getText().trim());
   done++;
   FinishedLabel.setText(String.valueOf(done));
  }
  catch(Exception e) {}
 }
 
 public synchronized void addDownload(Object[] obj)
 {
  try
  {
   Downloader d = new Downloader();
   String subject = (String)obj[0];
   String group = (String)obj[1];
   long TotalLines = ((Long)obj[2]).longValue();
   long[] Ids = (long[])obj[3];
   String server = Newzgrabber.optionspanel.ServerText.getText();
   d.setServer(server);
   String Username = null;
   String Password = null;
   if(Newzgrabber.optionspanel.UsernameText.getText().trim().length() > 0)
   {
    Username = Newzgrabber.optionspanel.UsernameText.getText().trim();
   }
   try
   {
    Password = new String(Newzgrabber.optionspanel.PasswordText.getPassword());
   }
   catch(Exception e) { Password = null; }
   if(Username != null && Password != null)
   {
    d.setUsername(Username);
    d.setPassword(Password);
   }
   d.setTitle(subject);
   d.setGroup(group);
   d.setIds(Ids);
   d.setTotalLines(TotalLines);
   Object[] addrow = new Object[5];
   addrow[0] = subject;
   addrow[1] = new String("Queued");
   addrow[2] = new String("");
   addrow[3] = new String("");
   addrow[4] = group;
   downloading.addRow(addrow);
   d.setDownloadData(addrow);
   DownloadVector.add(d);
   Queued = true;
  }
  catch(Exception de)
  {
   System.err.println(de.getMessage());
  }
 }
 
 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getActionCommand().equals("ABORT"))
  {
   if(downloadtable.getSelectedRowCount() == 0) return;
   try
   {
    synchronized(downloading)
    {
     synchronized(DownloadVector)
     {
      int[] indexes = downloadtable.getSelectedRows();
      for(int i=indexes.length - 1 ; i>= 0; i--)
      {
       if(Started.containsKey(new Integer(indexes[i])))
       {
        String percent = (String)downloading.getValueAt(indexes[i],1); 
        if(percent.indexOf("%") != -1)
        {
         Downloader d = (Downloader)DownloadVector.elementAt(indexes[i]);
         d.ABORT = true;
         continue;
        }
       } 
       downloading.removeRow(indexes[i]);
       DownloadVector.removeElementAt(indexes[i]);
      }
     }
    }
   }
   catch(Exception e)
   {
   }
  } 
 }

 public void updateIni()
 {
  try
  {
   if(Newzgrabber.debug)
	System.out.println("There are " + Messageids.size() + " ids to update");
   if(Messageids.size() < 1) return;
   Ini ini = new Ini(
	Newzgrabber.Newzdirectory +
	System.getProperty("file.separator") +
	OptionsFilename,
	"Messageids");
   LinkedList ll = new LinkedList();
   Enumeration keys = Messageids.keys();
   while(keys.hasMoreElements())
   {
    String nextkey = (String)keys.nextElement();
    String nextvalue = String.valueOf(
	((Long)Messageids.get(nextkey)).longValue());
    String[] s = new String[2];
    s[0] = nextkey;
    s[1] = nextvalue;
    ll.add(s);
   }
   ini.writeIni(ll);
  }
  catch(Exception e)
  {
   if(Newzgrabber.verbose || Newzgrabber.debug)
   {
	System.err.println("There was a problem updating INI for transfers");
	System.err.println(e.getMessage());
	e.printStackTrace();
   }
  }
 }
      
 public void run()
 {
  try
  {
   while(true)
   {
    try
    {
     Thread.sleep(500);
    }
    catch(Exception sleepE) {}
    int maxthreads;
    if(downloading.getRowCount() > 0)
    {
     for(int cnt=0 ; cnt<downloading.getRowCount() ; cnt++)
     {
      Waiting.put(new Integer(cnt), new Integer(1));
     }
    }
    if(Waiting.size() > Started.size())
	Queued = true;
    else
	Queued = false;
    try
    {
     maxthreads = Integer.parseInt(
	Newzgrabber.optionspanel.ThreadsText.getText().trim());
    }
    catch(Exception te) { maxthreads = 1; }
    int tmpi=-1;
    if(Queued)
    {
     for(int i=0 ; i<downloading.getRowCount() ; i++)
     {
      Integer tmp = new Integer(i);
      if(Started.containsKey(tmp)) continue;
      else
      {
       if(Newzgrabber.verbose)
       {
	System.out.println("Trying to start a new download session");
	System.out.println("Got new download index " + i);
       }
       tmpi = i;
       break;
      }
     }
     final int idx=tmpi;
     final DefaultTableModel dtm = downloading;
     if(idx < 0) continue;
     if((maxthreads >= 0) && (CurrentDownloads >= maxthreads)) continue;
     Started.put(new Integer(idx), new Integer(1));
//     Waiting.remove(new Integer(idx));
     if(Newzgrabber.verbose)
	System.out.println("There are now " + Started.size() + " running and " +
		Waiting.size() + " waiting");
     Thread t = new Thread() {
	public void run()
	{
	 Downloader d = null;
	 try
	 {
 	  d = (Downloader)DownloadVector.elementAt(idx);
//          JProgressBar jpb = new JProgressBar(0,(int)d.getTotalLines());
	  Object status = dtm.getValueAt(idx,1);
          Object FileObj = dtm.getValueAt(idx,0);
	  d.setTableIndex(idx);
          TransferPanel.CurrentDownloads++;
	  TransferPanel.incrementCurrentDownloads();
	  d.startDownload();
	 }
	 catch(Exception downE)
	 {
  	  System.err.println(downE.getMessage());
 	 }
	 finally
  	 {
	  d.close();
	  TransferPanel.CurrentDownloads--;
	  TransferPanel.decrementCurrentDownloads();
	  String group = d.getGroup();
	  long mainid = d.getBaseMessageId();
	  if(mainid != -1)
	  {
 	   mainid++;
	   if(Messageids.containsKey(group))
	   {
		if(mainid > ((Long)Messageids.get(group)).longValue())
			Messageids.put(group,new Long(mainid));
	   }
	   else
	   {
	    Messageids.put(group, new Long(mainid));
	   }
           if(Newzgrabber.verbose)
		System.out.println("Closing the Downloader object");
	  }
	 }
	}
     };
     t.start();
    } 
   }
  }
  catch(Exception mainE)
  {
   System.err.println(mainE.getMessage());
  }
 }

 private void initializeMessageids()
 {
  try
  {
   Ini ini = new Ini(
	Newzgrabber.Newzdirectory +
	System.getProperty("file.separator") +
	OptionsFilename,
	"Messageids");
   Hashtable Tmp = ini.readIni();
   Messageids = new Hashtable();
   if(Tmp.size() > 0)
   {
    Enumeration keys = Tmp.keys();
    while(keys.hasMoreElements())
    {
     String nextkey = (String)keys.nextElement();
     try
     {
      String longStr = (String)Tmp.get(nextkey);
      Long value = new Long(longStr.trim());
      Messageids.put(nextkey,value);
     }
     catch(Exception msgE) { continue; }
    }
   }
  }
  catch(Exception e)
  {
   if(Newzgrabber.verbose || Newzgrabber.debug)
   {
	System.err.println(e.getMessage());
	e.printStackTrace();
   }
  }
 }

} 
