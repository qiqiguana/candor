package Newzgrabber;
import Newzgrabber.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Newzgrabber extends JFrame implements WindowListener
{
 
 public static JTabbedPane jtp;
 public static OptionsPanel optionspanel;
 public static GroupsPanel groupspanel;
 public static SearchPanel searchpanel;
 public static boolean verbose = false;
 public static boolean debug = false;
 public static boolean batch = false;
 public static TransferPanel transferpanel;
 public static boolean IsBatch = false;
// public static BannerPanel bannerpanel;
 public static NewsFactory nf;
 private Vector affiliates;
// private Thread t;
 public static String Newzdirectory;
 public static String JarFilename = "Newz.jar";
 public static String BatchFilename = "batch.ini";
 public static String NewzInifilename = "Newzgrabber.ini";

// new constructor for supporting batch use

 public Newzgrabber()
 {
  IsBatch = true;
  this.setupBasicSystem();
  try 
  {
   String batchfile = Newzdirectory + 
	System.getProperty("file.separator") +
	BatchFilename;
   String newzini = Newzdirectory + 
	System.getProperty("file.separator") +
	NewzInifilename;
   if(Newzgrabber.verbose) 
	System.out.println("Getting batch info from " + batchfile);
   BatchDriver bd = new BatchDriver(newzini,batchfile);
   String[] BatchGroups = bd.getGroups();
   Vector GroupDownloadCount = new Vector();
   for(int i=0 ; i<BatchGroups.length ; i++)
   {
    System.out.println("Starting batch job for " + BatchGroups[i]);
    BatchJob bj = bd.getBatchJob(BatchGroups[i]);
    if(bj == null)
    {
	System.out.println("Returned NULL batch job object for " +
		BatchGroups[i]);
	continue;
    }
    if(Newzgrabber.verbose)
	System.out.println("Got batch job object");
    String[] grouparray = new String[1];
    grouparray[0] = bj.getGroup();
    if(Newzgrabber.verbose)
	System.out.println("Getting the line data");
    LineData ld = new LineData(bj.getServer(),grouparray);
    if(Newzgrabber.verbose)
	System.out.println("Done getting line data");
    String[] subjectlines = bd.getSubjectLines();
    SongList sl = new SongList(subjectlines);
    String[] complete = sl.getCompleteSongs(); 
    int nDownloads = 0;
    Vector FullList = new Vector();
    if(Newzgrabber.verbose)
	System.out.println("Found " + complete.length + " articles");
    for(int j=0 ; j<complete.length ; j++)
    {
     try
     {
      Object[] va = new Object[2];
      va[0] = bj.getGroup();
      va[1] = sl.getSongs().get(complete[j]);
      Hashtable tmp = (Hashtable)va[1];
      long[] OrderedIds = SongInfo.getOrderedIds(tmp);
      if(OrderedIds == null) continue;
      long TotalLines = ld.getTotalLines(BatchGroups[i],OrderedIds);
      if(((Integer)tmp.get("UPPERLIMIT")).intValue() < 2)
      {
       SongInfo si = (SongInfo)tmp.get(new Integer(1));
       long id = si.getId();
       long[] tmpids = new long[1];
       tmpids[0] = id;
       if(ld.getTotalLines(BatchGroups[i],tmpids) < 100) continue;
      }
      Object[] rowinfo = new Object[4];
      rowinfo[0] = complete[j];
      rowinfo[1] = BatchGroups[i];
      rowinfo[2] = new Long(TotalLines);
      rowinfo[3] = OrderedIds;
      FullList.add(rowinfo);
     }
     catch(Exception completeE)
     {
      System.out.println(completeE.getMessage());
      continue;
     }
    }
    for(int downindex=0 ; downindex<FullList.size() ; downindex++)
    {
     try
     {
      Object[] objarr = (Object[])FullList.elementAt(downindex);
      Downloader d = new Downloader();
      d.setDownloadDirectory(new File(bj.getDirectory()));
      d.setServer(bj.getServer());
      if(bj.getUsername() != null)
	d.setUsername(bj.getUsername().trim());
      if(bj.getPassword() != null)
	d.setPassword(bj.getPassword().trim());
      d.setGroup(BatchGroups[i]);
      d.setIds((long[])objarr[3]);
      d.setTotalLines(((Long)objarr[2]).longValue());
      Object[] addrow = new Object[5];
      addrow[0] = (String)objarr[0];
      addrow[1] = new String();
      addrow[2] = new String();
      addrow[3] = new String();
      addrow[4] = BatchGroups[i];
      d.startDownload();
      nDownloads++;
     }
     catch(Exception downloadE)
     {
      System.out.println(downloadE.getMessage());
      continue;
     }
    }
    Object[] DownloadData = new Object[2];
    DownloadData[0] = BatchGroups[i];
    DownloadData[1] = new Integer(nDownloads);
    GroupDownloadCount.add(DownloadData);
    this.updateIni(newzini,"Messageids",BatchGroups[i],
	String.valueOf(bj.getLastid()));
   }
   System.out.println("The following is the final download data:");
   System.out.println("GROUP\tDOWNLOADS");
   for(int cnt=0 ; cnt < GroupDownloadCount.size() ; cnt++)
   {
    Object[] downarr = (Object[])GroupDownloadCount.elementAt(cnt);
    String tmpGroup = (String)downarr[0];
    int tmpDownloads = ((Integer)downarr[1]).intValue();
    System.out.println(tmpGroup + "\t" + tmpDownloads);
   }
  }
  catch(Exception batchE) 
  {
   System.out.println("Caught exception");
   System.out.println(batchE.getMessage());
   System.exit(1);
  }
 }
 
 public Newzgrabber(String s)
 {
  super(s);
  IsBatch = false;
  this.getContentPane().setLayout(new BorderLayout());

  this.addWindowListener(this);
  this.setSize(600,500);
  setupBasicSystem();

  searchpanel = new SearchPanel(this);
  optionspanel = new OptionsPanel(this);
  groupspanel = new GroupsPanel(this);
  transferpanel = new TransferPanel(this);
  AboutPanel p4 = new AboutPanel();

  jtp = new JTabbedPane();
  jtp.addTab("Search",searchpanel);
  jtp.setSelectedIndex(0);
  jtp.addTab("Options",optionspanel);
  jtp.setSelectedIndex(1);
  jtp.addTab("Groups",groupspanel);
  jtp.setSelectedIndex(2);
  jtp.addTab("Transfer",transferpanel);
  jtp.setSelectedIndex(3);
  jtp.addTab("About",p4);
  jtp.setSelectedIndex(4);
  jtp.setPreferredSize(new Dimension(
	(int)(this.getWidth()-100),(int)(this.getHeight()-100)));
  this.getContentPane().add(jtp,BorderLayout.CENTER);
  this.setVisible(true);
  jtp.setSelectedIndex(0);
 }


 public static void setupBasicSystem()
 {
  Newzdirectory = findDirectory(JarFilename);
  if(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
  {
   try
   {
    System.setProperty("java.library.path" ,
	Newzdirectory +
	System.getProperty("path.separator") +
	System.getProperty("java.library.path"));
   }
   catch(Exception pathE)
   {
    if(Newzgrabber.debug)
    {
     System.err.println("There was a problem setting the lib path!");
     pathE.printStackTrace();
    }
   }
   if(Newzgrabber.debug || Newzgrabber.verbose)
   {
    System.out.println("The path variable is set to\n" +
	System.getProperty("java.library.path"));
   }
  }
  if(Newzgrabber.verbose)
	System.out.println("The newz dir is\n" + Newzdirectory);
  nf = new NewsFactory();
 }

 public void windowClosed(WindowEvent we) {}
 public void windowOpened(WindowEvent we) {}
 public void windowActivated(WindowEvent we) {}
 public void windowDeactivated(WindowEvent we) {}
 public void windowIconified(WindowEvent we) {}
 public void windowDeiconified(WindowEvent we) {}

 public void windowClosing(WindowEvent we)
 {
  try
  {
   optionspanel.updateIni();
   groupspanel.updateIni();
   transferpanel.updateIni();
  }
  catch(Exception ce)
  {
   System.err.println(ce.getMessage());
   ce.printStackTrace();
  }
  System.exit(0);
 }

 public static String findDirectory(String classfile)
 {
  try
  {
   String path = System.getProperty("java.class.path");
   StringTokenizer st = new StringTokenizer(path,
	System.getProperty("path.separator"));
   while(st.hasMoreTokens())
   {
    String nextpath = st.nextToken();
    try
    {
     File f = new File(nextpath);
     if(f.isFile())
     {
      if(f.getName().equals(classfile))
      {
       File parent = f.getParentFile();
       if(parent == null) continue;
       return(parent.getAbsolutePath());
      }
     }
    }
    catch(Exception parseE) 
    { 
     System.err.println("There was a parse error!\n" + parseE.getMessage());
     parseE.printStackTrace();
     continue; 
    }
   }
   return null;
  }
  catch(Exception e)
  {
   return null;
  }
 }

 public void updateIni(String inifile,String prop,String key,String value)
 {
  try
  {
   LinkedList ll = new LinkedList();
   if(Newzgrabber.verbose)
	System.out.println("Updating the ini file from Newzgrabber");
   Ini ini = new Ini(inifile,prop);
//   Hashtable Newids = ini.readIni();
   Vector Newids = ini.getIniVector();
   if(Newids.size() > 0)
   {
    for(int i=0 ; i<Newids.size() ; i++)
    {
     String[] stringarr = (String[])Newids.elementAt(i);
     String nextkey = stringarr[0];
     String thevalue = null;
     try
     {
      if(nextkey.toLowerCase().equals(key.toLowerCase()))
      {
       thevalue = value;
      }
      else
      {
       thevalue = stringarr[1];
      }
      String[] listobj = {nextkey,thevalue}; 
      System.out.println("Adding " + nextkey + " to hash with " + thevalue);
      ll.add(listobj);
     }
     catch(Exception lle) 
     { 
      System.out.println("Problem updating ini file!\n" + lle.getMessage());
      continue;   
     }
    }
   }
   ini.writeIni(ll);
  }
  catch(Exception e)
  {
   System.out.println("Problem updating ini file!\n" + e.getMessage());
  }
 }

 public static void main(String[] args)
 {
  try
  {
   System.out.println("The user directory is:\n" + 
	System.getProperty("user.dir"));
   if(args.length > 0)
   {
    for(int i=0; i<args.length ; i++)
    {
     if(args[i].toLowerCase().equals("-verbose")) 
     {
      Newzgrabber.verbose = true;
      System.out.println("Running Newzgrabber in verbose mode");
     }
     else if(args[i].toLowerCase().equals("-batch")) 
     {
      Newzgrabber.IsBatch = true;
      System.out.println("Running Newzgrabber in batch mode");
     }
     else if(args[i].toLowerCase().equals("-debug"))
     {
      Newzgrabber.debug = true;
      System.out.println("Running Newzgrabber in debug mode");
     }
     else if(args[i].toLowerCase().equals("-batch"))
     {
      Newzgrabber.batch = true;
      System.out.println("Running Newzgrabber in batch mode");
     }
    }
   }
   Newzgrabber n = null;
   Newzbatch nb = null;

   if(Newzgrabber.IsBatch)
	nb = new Newzbatch();
   else	
	n = new Newzgrabber("Newzgrabber");
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}