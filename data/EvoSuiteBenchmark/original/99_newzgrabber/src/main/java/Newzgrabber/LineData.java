package Newzgrabber;
import Newzgrabber.*;

import java.util.*;
import java.io.*;

public class LineData implements Runnable
{

 String Server;
 String[] groups;
 String FromPattern;
 Hashtable Groupdata;
 Thread t;
 NNTP news;
 boolean READY;
 boolean SOCKDONE = false;
 
 public LineData(String s,String[] groups)
 {
  Server = s;
  this.groups = groups;
  Groupdata = new Hashtable();
  READY = false;
  t = new Thread(this);
  t.start();
 }

 public void setFromPattern(String f)
 {
  FromPattern = f;
 }

 public void stop()
 {
  try
  {
   news.quit();
   if(!Newzgrabber.IsBatch)
   {
    if(SearchPanel.NewsVector.contains(news))
	SearchPanel.NewsVector.remove(news);
   }
   t.join();
  }
  catch(Exception e) {}
 }

 public String getFrom(String g, long id)
 {
  try
  {
   String fromkey = g + "_FROMLINES";
   Hashtable h = this.getGroupData(fromkey);
   Long key = new Long(id);
   if(h.containsKey(key) == false) return null;
   return((String)(h.get(key)));
  }
  catch(Exception e) 
  { 
   if(Newzgrabber.verbose) System.err.println(e.getMessage());
   e.printStackTrace();
   return null;
  }
 }   
   
 public long getTotalLines(String g, long[] ids)
 {
  try
  {
   long TotalLines = 0;
   Hashtable h = this.getGroupData(g);
   for(int i=0 ; i<ids.length ; i++)
   {
    Long key = new Long(ids[i]);
    if(h.containsKey(key) == false) continue;
    TotalLines += ((Long)h.get(key)).longValue();
   }
   return TotalLines;
  }
  catch(Exception e) 
  { 
   if(Newzgrabber.verbose) System.err.println(e.getMessage());
   e.printStackTrace();
   return -1; 
  }
 }   

 public Hashtable getGroupData(String g)
 {
  try
  {
   while(true)
   {
    if(Groupdata.containsKey(g) == false)
    {
//     System.out.println("No key found yet for groupdata...");
     Thread.sleep(500);
     continue;
    }
    else
    {
     Hashtable h = (Hashtable)Groupdata.get(g); 
//     System.out.println("Found groupdata for " + g);
     if(((Boolean)h.get("READY")).booleanValue() == true)
     {
//      System.out.println("Getting ready to return data");
      return h;
     }
     else
     {
      Thread.sleep(500);
//      System.out.println("The data does not appear to be ready yet");
      continue;
     }
    }
   }
  }
  catch(Exception e)
  {
   return null;
  }
 }
   
 public void run()
 {
  try
  {
   news = Newzgrabber.nf.getNewsSocket(Server);
   if(news != null)
   {
    try
    {
	if(!Newzgrabber.IsBatch)
		SearchPanel.NewsVector.add(news);
    }
    catch(Exception e) {}
   }
//   System.out.println("Created NNTP object in ld thread");
   for(int i=0 ; i<groups.length ; i++)
   {
//    System.out.println("Getting data for " + groups[i]);
    Hashtable h = new Hashtable();
    Hashtable fromh = new Hashtable();
    h.put("READY", new Boolean(false));
    fromh.put("READY",new Boolean(false));
    Groupdata.put(groups[i],h);
    String fromkey = groups[i] + "_FROMLINES";
    Groupdata.put(fromkey,fromh);
    try
    {
     String[] groupinfo = news.genericCommand("group " + groups[i]);
     String gLine = groupinfo[0].trim();
     StringTokenizer st = new StringTokenizer(gLine);
     st.nextToken();
     st.nextToken();
     int lowid = Integer.parseInt(st.nextToken());
     int highid = Integer.parseInt(st.nextToken());
// this next part is just for testing and may be changed later
// in short, too many articles is causing the JVM to run out of
// memory.  I need to tweak here to find an optimal number
/*
     if((highid - lowid) > 20000)
	highid = lowid + 20000;
*/
     String cmd = "xpat Lines " + lowid + "-" + highid + " *[0-9]*";
     if(Newzgrabber.verbose) System.out.println(cmd);
     String[] lines = news.genericCommand(cmd);
     if(Newzgrabber.verbose) System.out.println("Done getting the Lines data");
     for(int j=0 ; j<lines.length ; j++)
     {
      String thisline = lines[j].trim();
      if(thisline.startsWith("221 ")) continue;
      try
      {
       StringTokenizer lst = new StringTokenizer(thisline);
       long nextid = Long.parseLong(lst.nextToken().trim());
       long linecount = Long.parseLong(lst.nextToken().trim());
       h.put(new Long(nextid),new Long(linecount));
//       System.out.println("JUST ADDED ANOTHER RECORD FOR " + groups[i]);
      }
      catch(Exception le) 
      {
       if(Newzgrabber.verbose)
       {
        System.err.println(le.getMessage());
        System.err.println("Continuing to gather data for " + groups[i]);
       }
       continue; 
      }
     }

     String fromcmd = null;
     if(FromPattern != null) 
     {
	if(Newzgrabber.verbose)
		System.out.println("Setting custom from pattern");
	String fpat = Search.formatPattern(FromPattern.trim());
	fromcmd = "xpat From " + lowid + "-" + highid + " " + fpat;
     }
     else
     {
	fromcmd = "xpat From " + lowid + "-" + highid + " *";
     }
     if(Newzgrabber.verbose) System.out.println(fromcmd);
     String[] froms = news.genericCommand(fromcmd);
     if(Newzgrabber.verbose) System.out.println("Done getting From data");
     for(int j=0 ; j<froms.length ; j++)
     {
      String thisline = froms[j].trim();
      if(Newzgrabber.verbose)
	System.out.println(thisline);
      if(thisline.startsWith("221 ")) continue;
      try
      {
       StringTokenizer lst = new StringTokenizer(thisline);
       long nextid = Long.parseLong(lst.nextToken().trim());
       String sFrom = lst.nextToken().trim();
       fromh.put(new Long(nextid),sFrom);
       if(Newzgrabber.verbose)
	System.out.println("Just added " + sFrom + " for " + nextid);
      }
      catch(Exception le) 
      {
       if(Newzgrabber.verbose)
       {
        System.err.println(le.getMessage());
        System.err.println("Continuing to gather data for " + groups[i]);
       }
       continue; 
      }
     }


    }
    catch(Exception te) {}
    if(Newzgrabber.verbose)
    {
     System.out.println("Getting ready to put data into main hash " +
	"in ld thread for " + groups[i]);
    }
    h.put("READY" , new Boolean(true));
    fromh.put("READY" , new Boolean(true));
    Groupdata.put(groups[i],h);
    Groupdata.put(fromkey,fromh);
   }
  }
  catch(Exception e)
  {
   if(Newzgrabber.verbose) 
   {
	System.err.println("Caught exception in LineData thread\n" +
	e.getMessage());
	e.printStackTrace();
   }   
  }
  try
  {
   if(news != null) news.quit();
  }
  catch(Exception closeE) {}
  finally
  {
   READY = true;
   SOCKDONE = true;
   if(!Newzgrabber.IsBatch)
	   if(SearchPanel.NewsVector.contains(news))
		SearchPanel.NewsVector.remove(news);
  }
 }

 public static Hashtable getWorthyArticles(String server,String group)
 {
  NNTP n = null;
  try
  {
   Hashtable h = new Hashtable();
   n = Newzgrabber.nf.getNewsSocket(server);
   String[] groupinfo = n.genericCommand("group " + group);
   StringTokenizer st = new StringTokenizer(groupinfo[0].trim());
   st.nextToken();
   st.nextToken();
   int lowid = Integer.parseInt(st.nextToken());
   int highid = Integer.parseInt(st.nextToken());
/*
   if((highid - lowid) > 10000)
	highid = lowid + 10000;
*/
   String cmd = "xpat Lines " + lowid + "-" +
	highid + " *[1-9][0-9][0-9]*";
   if(Newzgrabber.verbose) System.out.println("Executing '" + cmd + "'");
   String[] lines = n.genericCommand(cmd);
   try
   {
    n.quit();
   }
   catch(Exception quitE) {}
   for(int i=0 ; i<lines.length ; i++)
   {
    String thisline = lines[i].trim();
    if(thisline.startsWith("221 ")) continue;
    try
    {
     StringTokenizer lst = new StringTokenizer(thisline);
     int nextid = Integer.parseInt(lst.nextToken());
     h.put(new Integer(nextid) , new Integer(1));
//     System.out.println(lines[i] + " appears to have more than 100 lines");
    }
    catch(Exception he) { continue; }
   }
   return h;
  }
  catch(Exception e) 
  {
   if(n != null) 
   {
    try
    {
     n.quit();
    }
    catch(Exception qe) {}
   }
   if(Newzgrabber.verbose) System.err.println(e.getMessage());
   return null;
  }
 }
 
 public static long getTotalLines(String server,String group,long[] ids)
 {
  NNTP n = null;
  try
  {
   if(Newzgrabber.verbose)
	System.out.println("Getting news socket from getTotalLines");
   n = Newzgrabber.nf.getNewsSocket(server);
   if(Newzgrabber.verbose)
	System.out.println("DONE!");
   String[] groupinfo = n.genericCommand("group " + group);
   long total = 0;
   for(int i=0 ; i<ids.length ; i++)
   {
    PrintWriter pw = n.getWriter();
    BufferedCustomInputStream br = n.getReader();
//    n.genericCommand("stat " + ids[i]);
    if(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
	pw.println("stat " + ids[i]);
    else
	pw.print("stat " + ids[i] + "\r\n");
    pw.flush();
    br.readLine();
    String[] HeadInfo = n.genericCommand("head");
    for(int j=0 ; j<HeadInfo.length ; j++)
    {
     String line = HeadInfo[j].trim();
     if(line.trim().toLowerCase().startsWith("lines:"))
     {
//      System.out.println("Analyzing Lines line:\n" + line);
      String sLines = line.substring(line.indexOf(":")+1,
	line.length());
      total += Long.parseLong(sLines.trim());
      break;
     }
    }
   }
   try
   {
    n.quit();
   }
   catch(Exception qe) {}
   return total;
  }
  catch(Exception e)
  {
   if(n != null)
   {
    try
    {
     n.quit();
    }
    catch(Exception quitE) {}
   }
   if(Newzgrabber.verbose) System.err.println(e.getMessage());
   return -1;
  }
 }

}
