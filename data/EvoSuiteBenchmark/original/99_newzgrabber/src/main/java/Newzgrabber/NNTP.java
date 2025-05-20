package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class NNTP extends Socket
{

 private String Server = null;
 protected PrintWriter pw = null;
 protected BufferedCustomInputStream br = null;
 protected InputStream sis = null;
 private int Port;
 private int STATUS;
 private Hashtable commands = new Hashtable();
 private boolean ALREADYQUIT = false;
 private boolean SOCKETOPEN = false;
 private boolean INTERRUPT = false;

 public NNTP(String s, int p) throws UnknownHostException, IOException
 {
  super(s,p);
  if(Newzgrabber.verbose)
	System.out.println("Called socket object from NNTP");
  SOCKETOPEN = true;
  this.Server = s;
  this.Port = p;
  this.setSoTimeout(0); // infinite timeout
  this.buildHash();
  br = new BufferedCustomInputStream(this.getInputStream());
  if(Newzgrabber.verbose)
	System.out.println("SET IT!");
  pw = new PrintWriter(new OutputStreamWriter(this.getOutputStream()));
  pw.flush();
  String ConnectStatus = br.readLine();
  Status statobj = new Status(ConnectStatus);
  if(statobj.statusOK() == false) 
  {
   throw(new IOException("Connect status is bad -> " + statobj.getCode()));
  }
  if(Newzgrabber.verbose)
	System.out.println("Connecting to server...\n" + ConnectStatus);
//  while(br.ready()) br.readLine();
 }

 public String[] authInfo(String u,String p) throws IOException
 {
  if(Newzgrabber.verbose)
	System.out.println("Authenticating username and password");
  this.genericCommand("authinfo USER " + u);
  return(this.genericCommand("authinfo PASS " + p));
 }

 public void println(String s)
 {
  if(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
	pw.println(s);
  else
	pw.print(s + "\r\n");
 }

 public void setInterrupt(boolean b)
 {
  INTERRUPT = b;
 }

 public boolean getInterrupt()
 {
  return INTERRUPT;
 }

 public NNTP(String s) throws UnknownHostException, IOException
 {
  this(s,119);
 }

 protected void buildHash()
 {
//  commands.put("group" , new Boolean(false));
  commands.put("help" , new Boolean(true));
  commands.put("article" , new Boolean(true));
  commands.put("body" , new Boolean(true));
  commands.put("head" , new Boolean(true));
  commands.put("newsgroups" , new Boolean(true));
  commands.put("xover" , new Boolean(true));
  commands.put("newnews" , new Boolean(true));
 }

 public BufferedCustomInputStream getReader()
 {
  if(Newzgrabber.verbose)
	System.out.println("Sending back reader " + br.toString());
  return br;
 }

 public PrintWriter getWriter()
 {
  return pw;
 }

 public String[] genericCommand(String cmd) throws IOException
 {
  if(cmd.trim().toLowerCase().startsWith("xpat "))
  { 
   return(this.genericCommand(cmd,true));
  } 
  else if(commands.containsKey((String)cmd))
  {
   boolean b = ((Boolean)commands.get((String)cmd)).booleanValue();
   return this.genericCommand(cmd,b);
  }
  else
  {
   return this.genericCommand(cmd,false);
  }
 }

 public String[] genericCommand(String cmd, boolean d) throws IOException
 {
  pw.flush();
  pw.print(cmd + "\r\n");
  pw.flush();
  String[] s;
  if(d == true)
  {
   s = this.readUntilDot();
  }
  else
  {
   s = this.readLine();
  }
  return s;
 } 

 public String[] readLine() throws IOException,ArrayIndexOutOfBoundsException
 {
  String[] s = new String[1];
  s[0] = br.readLine();
  return s;
 }

 public synchronized boolean isOpen()
 {
  return SOCKETOPEN;
 }

 public synchronized void quit() throws IOException
 {
  if(ALREADYQUIT == false)
  {
	ALREADYQUIT = true;
  }
  else 
  {
	if(Newzgrabber.debug)
		System.out.println("Already quit news object...Returning");
	return;
  }
  
  if(Newzgrabber.verbose || Newzgrabber.debug)
	System.out.println("Calling quit method on " + this.toString());
  pw.print("quit\r\n");
  pw.flush();
  pw.close();
  br.close();
  Newzgrabber.nf.releaseConnection();
  super.close();
  SOCKETOPEN = false;
 } 

 private String[] readUntilNull() throws IOException, 
	ArrayIndexOutOfBoundsException
 {
  Vector v = new Vector();
  String NextLine = null;
  while((NextLine = br.readLine()) != null)
  {
   v.add(NextLine);
   if(INTERRUPT) break;
  }
  String[] s = new String[v.size()];
  for(int i=0 ; i<v.size() ; i++)
  {
   s[i] = (String)v.elementAt(i);
  }
  return s;
 }

 private String[] readUntilDot() throws IOException,
	ArrayIndexOutOfBoundsException
 {
  Vector v = new Vector();
  String NextLine = null;
  while((NextLine = br.readLine()) != null)
  {
   if(Newzgrabber.verbose)
	System.out.println("'" + NextLine + "'");
   if(NextLine.equals(".")) break;
   v.add(NextLine);
   if(INTERRUPT) break;
  }
  String[] s = new String[v.size()];
  for(int i=0 ; i<v.size() ; i++)
  {
   s[i] = (String)v.elementAt(i);
  }
  return s;
 }

 public static void main(String[] args)
 {
  try 
  {
   NNTP n = new NNTP("news-server");
   BufferedCustomInputStream stdin = new BufferedCustomInputStream(
	System.in);
   while(true)
   {
    System.out.print("COMMAND:");
    String cmd = null;
    cmd = stdin.readLine();
    if(cmd.trim().equalsIgnoreCase("quit"))
    {
     n.quit();
     break;
    } 
    String[] result = n.genericCommand(cmd);
    for(int i=0 ; i<result.length ; i++)
    {
     System.out.println(result[i]);
    }
   }    
   stdin.close();
   System.out.println("Quitting...");
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
   e.printStackTrace();
  }
 }
	
}
