package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Newzbatch implements Runnable
{

 private static int TIMEOUTCOUNT = 0;
 public static boolean TIMEOUT = false;
 public static int TIMEOUTMAX = -1;
 private boolean killthread = false;
 private Downloader tDownloader;
 Thread t = null;

 public Newzbatch()
 {
  Newzgrabber.IsBatch = true;
  Newzgrabber.setupBasicSystem();
  try 
  {
   Newzgrabber.Newzdirectory = Newzgrabber.findDirectory(
	Newzgrabber.JarFilename);
   String batchfile = Newzgrabber.Newzdirectory + 
	System.getProperty("file.separator") +
	Newzgrabber.BatchFilename;
   String newzini = Newzgrabber.Newzdirectory + 
	System.getProperty("file.separator") +
	Newzgrabber.NewzInifilename;
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
      killthread = false;
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
      if(TIMEOUT)
	resetCount();
      tDownloader = d;
      if(TIMEOUT)
      {
       t = new Thread(this);
       t.start();
      }
      d.startDownload();
      killthread = true;
      d.close();
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
 
 public static synchronized void resetCount()
 {
  if(Newzgrabber.verbose)
	System.out.println("Resetting the timeout count");
  TIMEOUTCOUNT = 0;
 }

 public void run()
 {
  try
  {
   while(TIMEOUTCOUNT < TIMEOUTMAX)
   {
    if(Newzgrabber.verbose)
	System.out.println("Checking the downloader in thread with count of " + TIMEOUTCOUNT);
    for(int i=0 ; i<10 ; i++)
    {
     Thread.sleep(100);
     if(killthread) break;
    } 
    if(killthread)
    {
     if(Newzgrabber.verbose)
	System.out.println("Download completed at timeout count of " + TIMEOUTCOUNT);
     break;
    }
    TIMEOUTCOUNT++;
   }
   if(tDownloader != null)
   {
	System.out.println("A timeout has been reached!");
	tDownloader.close();
	System.out.println("Closing the Downloader object");
	resetCount();
   }
  }
  catch(Exception e)
  {
   System.out.println("The timeout has been reached");
   TIMEOUTCOUNT = 0;
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
   boolean addedit = false;
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
       addedit = true;
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
   if(!addedit)
   {
    String[] addobj = {key,value};
    ll.add(addobj);
   } 
   ini.writeIni(ll);
  }
  catch(Exception e)
  {
   System.out.println("Problem updating ini file!\n" + e.getMessage());
  }
 }

}