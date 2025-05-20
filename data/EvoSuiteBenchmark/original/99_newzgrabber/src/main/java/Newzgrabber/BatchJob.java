package Newzgrabber;
import Newzgrabber.*;

import java.io.*;

public class BatchJob
{

 private String server;
 private String group;
 private String username;
 private String password;
 private String directory;
 private int firstid;
 private int lastid; 
 private boolean SetupOK = false;

 public BatchJob(String s,String g,String d,int f,int l)
 {
  server = s;
  group = g;
  directory = d;
  firstid = f;
  lastid = l;
  this.createDirectory();
 }

 public boolean createDirectory()
 {
  try
  {
   File dir = new File(directory);
   if(dir.isDirectory()) SetupOK = true;
   SetupOK = dir.mkdirs();
  }
  catch(Exception createE)
  {
   if(Newzgrabber.verbose)
	System.out.println("Could not create directory for " + directory);
   SetupOK = false;
  }
  return SetupOK;
 }

 public void setUsername(String s)
 {
  username = s;
 }

 public void setPassword(String s)
 {
  password = s;
 }

 public String getUsername()
 {
  return username;
 }

 public String getPassword()
 {
  return password;
 }

 public String getGroup()
 {
  return group;
 }

 public String getDirectory()
 {
  return directory;
 }

 public int getFirstid()
 {
  return firstid;
 }

 public int getLastid()
 {
  return lastid;
 }

 public String getServer()
 {
  return server;
 }

}