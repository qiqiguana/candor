package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;

public class BatchDriver
{
 
 private Ini groupsini;
 private Ini batchini;
 private Hashtable NewsgroupsHash;
 private Hashtable BatchgroupsHash;
 private Hashtable BatchretrieveHash;
 private Hashtable OptionsHash;
 private Vector BatchgroupsVector;
 private String[] subjectlines;
 
 public BatchDriver(String newzini,String batchfile) 
	throws NullPointerException,IOException
 {
  BatchgroupsVector = new Vector();
  Ini groupsini = new Ini(newzini,"Messageids");
  Ini batchgroups = new Ini(batchfile,""); 
  NewsgroupsHash = groupsini.readIni();
  batchgroups.setProperty("Newsgroups");
  BatchgroupsHash = batchgroups.readIni();
  BatchgroupsVector = batchgroups.getIniVector();
  batchgroups.setProperty("Retrieve");
  BatchretrieveHash = batchgroups.readIni();
  batchgroups.setProperty("Options");
  OptionsHash = batchgroups.readIni();
 }
  
 public String[] getGroups()
	throws ArrayIndexOutOfBoundsException
 {
  String[] groups = new String[BatchgroupsVector.size()];
  for(int i=0 ; i<BatchgroupsVector.size() ; i++)
  {
   String[] groupstuff = (String[])BatchgroupsVector.elementAt(i);
   groups[i] = groupstuff[0];
  }
  return groups;
 }

 public BatchJob getBatchJob(String group)
 {
  try
  {
   String server = null;
   String directory = null;
   try
   {
    server = (String)OptionsHash.get("server");
    directory = ((String)BatchgroupsHash.get(group)).trim();
   }
   catch(Exception sdE) 
   { 
    System.out.println("Could not get server and directory for " + group);
    return null; 
   }
   String username=null;
   String password=null;
   if(OptionsHash.containsKey("username"))
	username = ((String)OptionsHash.get("username")).trim();
   if(OptionsHash.containsKey("password"))
	password = ((String)OptionsHash.get("password")).trim();
   int nThreads = 1;
   int TIMEOUT = -1;
   try
   {
    nThreads = Integer.parseInt(((String)OptionsHash.get("threads")).trim());
   }
   catch(Exception threadsE)
   {
    nThreads = 1;
   }
   try
   {
    TIMEOUT = Integer.parseInt(((String)OptionsHash.get("timeout")).trim());
    Newzbatch.TIMEOUT = true;
    Newzbatch.TIMEOUTMAX = TIMEOUT;
    System.out.println("Setting the max timeout value to " + TIMEOUT);
   }
   catch(Exception threadsE)
   {
    TIMEOUT = -1;
   }
   
   if(Newzgrabber.verbose)
	System.out.println("Getting NNTP object from BatchDriver " +
		"with the server " + server);
   Newzgrabber.nf.setThreadLimit(nThreads);
   NNTP news = Newzgrabber.nf.getNewsSocket(server);
   if(news == null)
   {
    System.out.println("Could not get news socket in BatchDriver");
    return null;
   }
   if(username != null && password != null)
	news.authInfo(username,password);
   String[] groupinfo = news.genericCommand("group " + group);
   if(Newzgrabber.verbose)
	System.out.println("Got the following group line from BatchDriver\n" +
		groupinfo[0]);
   Status thestatus = new Status(groupinfo[0].trim());
   if(thestatus.statusOK() == false) return null;
   StringTokenizer st = new StringTokenizer(groupinfo[0].trim());
   st.nextToken();
   st.nextToken();
   int tmpFirst = Integer.parseInt(st.nextToken());
   int tmpLast = Integer.parseInt(st.nextToken());
   if(Newzgrabber.IsBatch)
   {
	System.out.println("The first id in group is " + tmpFirst);
	System.out.println("The last id in group is " + tmpLast);
   }
   int firstid=0;
   int lastid=tmpLast;
   
   if(NewsgroupsHash.containsKey(group))
   {
    try
    {
     firstid = Integer.parseInt((String)NewsgroupsHash.get(group));
     firstid++;
     if(Newzgrabber.IsBatch)
	System.out.println("ini file shows " + firstid + " as being the id");
    }
    catch(Exception firstidE) { firstid=0; }
   }
   if(tmpFirst > firstid)
   {
    firstid = tmpFirst;
   }

   String cmd = "xpat Subject " + firstid + "-" + lastid + " *";
   System.out.println("Getting ready to get subjects with\n" + cmd);
   subjectlines = news.genericCommand(cmd);
   news.quit();
   BatchJob bj = new BatchJob(server,group,directory,firstid,lastid);
   if(username != null && password != null)
   {
    bj.setUsername(username.trim());
    bj.setPassword(password.trim());
   }
   return bj;
  }
  catch(Exception e)
  {
   System.out.println(e.getMessage());
   return null;
  }  
 }

 public String[] getSubjectLines()
 {
  return subjectlines;
 }


}