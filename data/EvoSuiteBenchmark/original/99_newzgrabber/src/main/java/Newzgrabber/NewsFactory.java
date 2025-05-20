package Newzgrabber;
import Newzgrabber.*;

import java.net.*;
import java.util.*;

public class NewsFactory
{

 protected static int THREADLIMIT;
 protected static String Username;
 protected static String Password;
 private static int CURRENTCONNECTIONS = 0;

 public synchronized NNTP getNewsSocket(String s,int p)
 {
  if(Newzgrabber.verbose)
	System.out.println("Calling getNewsSocket with " + s + " and " + p);
  try
  {
   try
   {
    if(!Newzgrabber.IsBatch)
    {
     THREADLIMIT = Integer.parseInt(
	Newzgrabber.optionspanel.ThreadsText.getText().trim());
    }
   }
   catch(Exception LIMITE)
   {
    THREADLIMIT = 1;
   }
   if(THREADLIMIT >= 0)
   {
    while(CURRENTCONNECTIONS >= THREADLIMIT) 
    {
     if(Newzgrabber.verbose || Newzgrabber.debug) {
	System.out.println("Waiting for news socket...");
	System.out.println("There are " + CURRENTCONNECTIONS + 
		" open connections and a " + THREADLIMIT + " limit");
//	Thread.sleep(5000);
     }
     wait();
     try {
 	Thread.sleep(500);
     }
     catch(Exception se) {}
    }
   }
   NNTP n = null;
   boolean CONNECTED = false;
   while(CONNECTED == false) {
	try {
		n = new NNTP(s,p);
		CONNECTED = true;
	} catch(Exception nntpe) {
		System.out.println("Got exception creating new NNTP\n" +
			nntpe.getMessage() + "\nRetrying connection...");
		CONNECTED = false;
		wait();
		try {
			Thread.sleep(500);
		} catch(Exception sleepy) {}
	}
  }
		
   if(Newzgrabber.verbose)
	System.out.println("Got news object " + n.toString());
   CURRENTCONNECTIONS++;
   if(!Newzgrabber.IsBatch)
   {
    Username = OptionsPanel.UsernameText.getText().trim();
    String pTemp = new String(OptionsPanel.PasswordText.getPassword());
    Password = pTemp.trim();
   }
     
   if(Username != null && Password != null)
   {
    if(Username.length() > 0 && Password.length() > 0)
	if(Newzgrabber.verbose)
		System.out.println("Authenticating info");
	n.authInfo(Username,Password);
	if(Newzgrabber.verbose)
		System.out.println("DONE!");
   }

   if(Newzgrabber.verbose || Newzgrabber.debug) {
	System.out.println("Got News socket!");
	System.out.println("There are " + CURRENTCONNECTIONS + 
		" connections now");
   }
   return n;
  }
  catch(Exception e) { return null; }
 }

 public synchronized NNTP getNewsSocket(String s)
 {
  return(this.getNewsSocket(s,119));
 }

 public synchronized NNTP getNewsSocket()
 {
  return(this.getNewsSocket(
	Newzgrabber.optionspanel.ServerText.getText().trim()));
 }

 public synchronized void releaseConnection()
 {
  CURRENTCONNECTIONS--;
  if(Newzgrabber.verbose || Newzgrabber.debug)
	System.out.println("Just released connection\n" +
	"There are now " + CURRENTCONNECTIONS + " current connections");
  notifyAll();
 }

 public static synchronized void setUsername(String s)
 {
  Username = s;
 }

 public static synchronized void setPassword(String s)
 {
  Password = s;
 }

 public static synchronized void setThreadLimit(int i)
 {
  THREADLIMIT = i;
 }

 public static int getThreadLimit()
 {
  return THREADLIMIT;
 }

}
