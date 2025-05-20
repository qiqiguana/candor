package Newzgrabber;
import Newzgrabber.*;

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Downloader
{

 private String Group;
 private NNTP news;
 private BufferedCustomInputStream br;
 private PrintWriter pw;
 private String Server;
 protected static final int UUEncoded = 1;
 protected static final int Base64Encoded = 2;
 protected static final int yEncoded = 3;
 private int EncType;
 private String Filename;
 protected static int MARKLIMIT = 1000;
 private long[] ids;
 private long TotalLines;
 private String Title;
 private String Username;
 private String Password;
 private File DownloadDirectory;
 private Object[] DownloadData;
 private int TableIndex;
 private static int TIMEOUTCOUNT = 100;
 private int checkcount;
 private boolean NothingFound = false;
 private FileOutputStream fos;
 private boolean IsWindows = true;
 private int TIMEOUT = -1;
 public boolean ABORT = false;
 
 public Downloader()
 {
  try
  {
   IsWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
   if(Newzgrabber.optionspanel.DirectoryText != null)
   {
    if(Newzgrabber.optionspanel.DirectoryText.getText().trim().length() > 0)
    {
     DownloadDirectory = new File(
 	Newzgrabber.optionspanel.DirectoryText.getText().trim());
     if(DownloadDirectory.isDirectory() == false)
	DownloadDirectory = null;
    }
   }
  }
  catch(Exception e) { DownloadDirectory = null; }
  checkcount = 0; // used for timeout checks
  EncType = 0;
  TableIndex = -1;
 }

 public void setTimeout(int i)
 {
  TIMEOUT = i;
 }

 public int getTimeout()
 {
  return TIMEOUT;
 }

 public void setDownloadDirectory(File f)
 {
  DownloadDirectory = f;
 }

 public void setTableIndex(int s)
 {
  TableIndex = s;
 } 

 public void setServer(String s)
 {
  Server = s;
 }
 
 public void setIds(long[] ids)
 {
  this.ids = ids;
 }

 public void setTotalLines(long l)
 {
  TotalLines = l;
 }

 public long getTotalLines()
 {
  return TotalLines;
 }

 public void setTitle(String t)
 {
  Title = t;
 }

 public void setUsername(String u)
 {
  Username = u;
 }
 
 public void setPassword(String p)
 {
  Password = p;
 }

 public void setGroup(String g)
 {
  Group = g;
 }

 public String getGroup()
 {
  return Group;
 }

 public long getBaseMessageId()
 {
  if(ids != null) return ids[0];
  return -1;
 }

 public void setDownloadData(Object[] obj)
 {
  DownloadData = obj;
 }

 public void close()
 {
  if(Newzgrabber.verbose)
  {
   if(Filename != null)
   {
	System.out.println("Closing the downloader object for " + Filename);
   }
   else
   {
	System.out.println("Closing the downloader object for NULL Filename");
   }
  }   
  try
  {
   if(fos != null)
   {
    fos.close();
   }
   else
   {
    if(Newzgrabber.verbose)
	System.out.println("The output stream was null when closing the news object!");
   }
  }
  catch(Exception fe) 
  {
   if(Newzgrabber.verbose)
	System.out.println("There was a problem closing the filehandle\n" + fe.getMessage());
  }
  try
  {
   if(news != null) 
   {
    if(news.isOpen()) news.quit();
   }
  }
  catch(Exception ne) 
  {
   if(Newzgrabber.verbose)
	System.out.println("There was a problem closing the news socket object!\n" +
		ne.getMessage());
  }
 }

 public void startDownload() throws IOException
 {
  try
  {
   if(Newzgrabber.verbose) System.out.println("Started download!");
   String FullFilename = null;
   if(ids.length < 1) return;
   if(Newzgrabber.verbose) System.out.println("Attempting to connect to " + Server);
   news = Newzgrabber.nf.getNewsSocket(Server);
   if(Newzgrabber.verbose) System.out.println("Created NNTP object");
   if(Username != null && Password != null)
   {
    if(Newzgrabber.verbose)
    {
     System.out.println("Authorizing " + Username + " with " + Password);
    }
   }
   if(Newzgrabber.verbose)
	System.out.println("Trying to get the PrintWriter");
   pw = news.getWriter();
   if(Newzgrabber.verbose)
	System.out.println("Trying to get the BufferedReader");
   br = news.getReader();
   if(Newzgrabber.verbose)
	System.out.println("Got them both");
   while(br.ready()) 
   {
    if(Newzgrabber.verbose)
	System.out.println("Reader is ready...trying to read line");
    String NextLine = br.readLine();
    if(Newzgrabber.verbose)
 	System.out.println("Flushing '" + NextLine + "'");
   }
   if(Newzgrabber.verbose) System.out.println("Sending the group command");
   if(IsWindows)
	pw.println("group " + Group);
   else
	pw.print("group " + Group + "\r\n");
   pw.flush();
   if(Newzgrabber.verbose)
	System.out.println("Set the group command to " + Group);
   String groupcmd = br.readLine();
   if(Newzgrabber.verbose)
	System.out.println("The groupcmd returned was " + groupcmd);
   if(Newzgrabber.verbose) System.out.println(groupcmd);
   MimeDecoder md = null;
   fos = null;
   String NextLine = null;
   boolean FileFound = false;
   boolean STATOK = false;
   if(Newzgrabber.verbose) 
 	System.out.println("The total number of lines is: " + TotalLines);
   long cnt = 0;
   int loopcount=-1;
   for(int i=0 ; i<ids.length ; i++)
   {
    loopcount++;
    if(ABORT) 
    {
     md.ABORT = true;
     break;
    }
    if(Newzgrabber.verbose) System.out.println("Getting data from id " + ids[i]);
 //   debug.println("Starting decode of " + ids[i]);
    if(IsWindows)
	pw.println("stat " + ids[i]);
    else
	pw.print("stat " + ids[i] + "\r\n");
    pw.flush();
    if(Newzgrabber.verbose)
	System.out.println("Checking if " + br.toString() + " is ready...");
    while(br.ready() == false)
    {
     if(Newzgrabber.verbose)
	System.out.println("Stat line is not ready to read yet");
     try {
	Thread.sleep(100);
     } catch(Exception te) {
	continue;
     }
    }	
    String statline = br.readLine();
    if(Newzgrabber.verbose)
	System.out.println("The stat line for " + ids[i] + " was\n" +
		statline);
    try
    {
     StringTokenizer stst = new StringTokenizer(statline);
     int tmpstatus = Integer.parseInt(stst.nextToken());
     if(tmpstatus >= 200 && tmpstatus < 300)
 	STATOK = true;
     else
 	STATOK = false;
    }
    catch(Exception statE) { STATOK = false; }
 //   while(br.readLine() != null) {}
    if(STATOK == false)
    {
     if(Newzgrabber.verbose)
 	System.out.println("Could not get stat line for " + ids[i]);
     break;
    }
    String tmpfilename = DownloadDirectory.getAbsolutePath() +
	System.getProperty("file.separator") +
	Group.replace('.','_') + "." + ids[i];
//    br.setTempfile(tmpfilename);
    if(Newzgrabber.verbose) System.out.println("Just sent the body command");
    if(IsWindows)
	pw.println("body");
    else
	pw.print("body\r\n");
    pw.flush();
    if(Filename == null)
    {
     if(Newzgrabber.verbose)
	System.out.println("Getting ready to look for the filename");
     FileFound = this.findFile();
     if(Newzgrabber.verbose)
	System.out.println("The value of FileFound is " + FileFound);
     if(FileFound)
     {
      if(Newzgrabber.verbose)
	System.out.println("Found a filename!");
      if(!Newzgrabber.IsBatch)
      {
	if(TableIndex != -1)
	{
	 TransferPanel.downloading.setValueAt(Filename,TableIndex,0);
	}
      }
      if(DownloadDirectory != null)
 	FullFilename = DownloadDirectory.getAbsolutePath() +
 		System.getProperty("file.separator") + Filename;
      else
 	FullFilename = Filename;
      FullFilename = NewsFile.renameFile(FullFilename);
      fos = new FileOutputStream(FullFilename);
      if(!Newzgrabber.IsBatch)
      {
	if(TableIndex != -1)
	{
	 if(EncType == UUEncoded)
	  TransferPanel.downloading.setValueAt("UU Decode",TableIndex,2);
         else if(EncType == Base64Encoded)
          TransferPanel.downloading.setValueAt("Base64 Decode",TableIndex,2);
         else if(EncType == yEncoded)
          TransferPanel.downloading.setValueAt("yEnc Decode",TableIndex,2);
         else
          TransferPanel.downloading.setValueAt("Unknown",TableIndex,2);
          
       }
      }
      if(Newzgrabber.verbose) 
      {
       System.out.println("Got full filename of " + FullFilename);
      }
     }
     else
     {
      if(Newzgrabber.verbose)
	System.out.println("Could not find the filename");
      if(checkcount > TIMEOUTCOUNT)
      {
       NothingFound = true;
       break;
      }
      continue;
     }
    }
    if(EncType == UUEncoded)
    {
     if(Newzgrabber.verbose)
	System.out.println("Trying a UU decode");
     md = new UUDecoder(br,fos);
    }
    else if(EncType == Base64Encoded)
    {
     if(Newzgrabber.verbose)
	System.out.println("Trying a Base64 decode");
     md = new Base64Decoder(br,fos);
    }
    else if(EncType == yEncoded)
    {
     if(Newzgrabber.verbose)
	System.out.println("Trying a yEnc decode");
     md = new yEncDecoder(br,fos);
     if(loopcount == 0) md.setBegin(true);
     if(Newzgrabber.verbose)
	System.out.println("Got new yEnc object");
    }
    if(!Newzgrabber.IsBatch)
    {
     md.setStatusRow(TableIndex);
     md.setTotalLines(TotalLines);
     md.setLineCount(cnt);
    }
    if(Newzbatch.TIMEOUT)
	Newzbatch.resetCount();
    if(Newzgrabber.verbose)
	System.out.println("Calling the decodeStream() for object");
    md.decodeStream();
    cnt = md.getLineCount();
   }
   try
   {
    if(Newzgrabber.verbose)
	System.out.println("Shutting down news object");
    news.quit();
    fos.close();
   }
   catch(Exception ce)
   {
    if(Newzgrabber.verbose)
 	System.err.println("Could not close filehandle!\n" + ce.getMessage());
   }
 //  debug.close();
   if(Newzgrabber.verbose) System.out.println("Done downloading " + Filename);
   if(ABORT && !Newzgrabber.IsBatch)
	TransferPanel.downloading.setValueAt("Aborted",TableIndex,1);
   else if(STATOK == false)
   {
    try
    {
     File ff = new File(FullFilename);
     ff.delete();
    }
    catch(Exception deleteE) {}

    if(!Newzgrabber.IsBatch)
	TransferPanel.downloading.setValueAt("Article Not Found",TableIndex,1);
   }  
   else if(NothingFound == false)
   {
    if(!Newzgrabber.IsBatch)
    {
     TransferPanel.downloading.setValueAt("Done!",TableIndex,1);
     TransferPanel.downloading.setValueAt(FullFilename,TableIndex,3);
     if(Newzgrabber.verbose)
 	System.out.println("There are currently " + 
 		TransferPanel.CurrentDownloads + " downloads now");
     TransferPanel.incrementFinished();
    }
    else
    {
     StringBuffer sbids = new StringBuffer("(");
     for(int idindex=0 ; idindex<ids.length ; idindex++)
     {
      sbids.append(ids[idindex] + " ");
     }
     sbids.replace(sbids.length()-1,sbids.length(),")");
     String idstring = sbids.toString();
     if(EncType == UUEncoded)
     {
	System.out.println("UU Decoded " + FullFilename + " " + idstring);
     }
     else if(EncType == Base64Encoded)
     {
	System.out.println("Base64 Decoded " + FullFilename + " " + idstring);
     }
     else if(EncType == yEncoded)
     {
  	System.out.println("yEnc Decoded " + FullFilename + " " + idstring);
     }
     else
     {
	System.out.println("I don't understand encoding of " + idstring);
     }
    }
   }
   else
   {
    if(Newzgrabber.verbose)
    {
     System.out.println("Skipping because no file could be found");
    }
    if(!Newzgrabber.IsBatch)
	TransferPanel.downloading.setValueAt("Nothing found",TableIndex,1);
   }
  }
  catch(Exception downloaderE)
  {
   if(!Newzgrabber.IsBatch)
	TransferPanel.downloading.setValueAt(downloaderE.getMessage(),
		TableIndex,1);
   else
	System.out.println(downloaderE.getMessage());
  }
 }

 private boolean findFile()
 {
  if(Newzgrabber.verbose)
	System.out.println("Inside of the findFile method");
  try
  {
   int line_number = 1;
   String NextLine = null;
   while((NextLine = br.readLine()) != null)
   {
    checkcount++;
    if(Newzgrabber.verbose)
	System.out.println("Reading line number " + line_number + "\n" + NextLine);
    if(line_number == 1)
    {
     if(NextLine.trim().toLowerCase().startsWith("423 no"))
     {
      if(Newzgrabber.verbose)
	System.out.println("Returning because no article");
      return false;
     }
    }
    line_number++;
    if(NextLine.equals(".")) break;
    br.mark(MARKLIMIT);
    if(NextLine.startsWith("begin "))
    {
     StringTokenizer st = new StringTokenizer(NextLine);
     if(st.countTokens() > 2)
     {
      st.nextToken();
      try
      {
       int mode = Integer.parseInt(st.nextToken());
      }
      catch(Exception ie) { continue; }
      EncType = UUEncoded;
      StringBuffer sb = new StringBuffer("");
      while(st.hasMoreTokens())
      {
       sb.append(st.nextToken() + " ");
      }
      Filename = sb.toString().trim();
      if(Filename != null) this.fixFilename();
      br.reset();
      return true;
     }
    }
    else if(NextLine.toLowerCase().startsWith("=ybegin"))
    {
     if(Newzgrabber.verbose)
	System.out.println("Found ybegin line in\n" + NextLine);
     int nameindex = NextLine.toLowerCase().indexOf("name=");
     if(Newzgrabber.verbose)
	System.out.println("The name index is at " + nameindex);
     if(nameindex != -1)
     {
      Filename = NextLine.substring(nameindex + 5,
		NextLine.length());
      EncType = yEncoded;
      br.reset();
      return true;
     }
    }
    else if(NextLine.toLowerCase().startsWith("content-type:"))
    {
     if(NextLine.indexOf("name=") != -1)
     { 
      Filename = NextLine.substring(NextLine.indexOf("=")+1,
 		NextLine.length());
      if(Filename !=null) this.fixFilename();
     }
    } 
    else if(NextLine.toLowerCase().trim().startsWith("content-" +
	"transfer-encoding:"))
    {
     StringTokenizer st = new StringTokenizer(NextLine,": ");
     st.nextToken();
     if(st.nextToken().toLowerCase().trim().equals("base64"))
     {
      if(Newzgrabber.verbose)
	System.out.println("This looks like it's base64 encoded");
      if(Filename == null)
      {
       String FileLine = null;
       while((FileLine = br.readLine()) != null)
       {
        checkcount++;
        if(checkcount > TIMEOUTCOUNT) break;
        if(FileLine.equals(".")) break;
        if(Newzgrabber.verbose)
         System.out.println("Trying to get the filename from\n" + FileLine);
        if(FileLine.indexOf("filename=") != -1)
        {
         Filename = FileLine.substring(FileLine.indexOf("=")+1,
  		FileLine.length());
         if(Newzgrabber.verbose)
	  System.out.println("The NON-fixed filename is '" + Filename + "'");
         if(Filename != null) this.fixFilename();
         if(Newzgrabber.verbose)
	  System.out.println("The filename looks like '" + Filename + "'");
         br.reset();
         EncType = Base64Encoded;
         return true;
        }
        else
        {
         if(Newzgrabber.verbose) System.out.println("Could not find the filename!");
        }
       }
      }
      else
      {
       EncType = Base64Encoded;
       return true;
      }
     }
    }
   }
   return false;
  }
  catch(Exception e)
  {
   return false;
  }
 }

 private void fixFilename()
 {
  try
  {
   StringBuffer sb = new StringBuffer("");
   for(int i=0 ; i<Filename.length() ; i++)
   {
    char c = Filename.charAt(i);
    if(Character.isLetterOrDigit(c))
   	sb.append(c);
    else if(Character.isSpaceChar(c))
	sb.append(" ");
    else if(c == '.' || c == '_' || c == '-')
	sb.append(c);
   }
   Filename = sb.toString().trim();
  }
  catch(Exception e) {}
 }

}
