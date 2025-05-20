package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;

public class Ini
{
 
 private File Filename;
 private String Property;
 private Vector lastgroup;

 public Ini(File f,String p) throws NullPointerException
 {
  Filename = f;
  Property = p;
  if(Newzgrabber.verbose)
	System.out.println("Created a new ini from file\n" + f.getAbsolutePath());
 }

 public void setProperty(String s)
 {
  Property = s;
 }

 public String getProperty()
 {
  return Property;
 }

 public Ini(String f,String p) throws NullPointerException
 {
  this(new File(f),p);
 }

 public Vector getIniVector() throws IOException
 {
  if(lastgroup == null)
	this.readIni();
  return lastgroup;
 }

 public Hashtable readIni() throws IOException
 {
  String[] lines = this.getIniLines();
  lastgroup = new Vector();
  Hashtable h = new Hashtable();
  boolean inprop = false;
  String NextLine = null;
  for(int i=0 ; i<lines.length ; i++)
  {
   NextLine = lines[i].trim();
   if(NextLine.toLowerCase().equals("[" + Property.toLowerCase() + "]"))
   {
    inprop = true;
    continue;
   }
   if(inprop)
   {
    if(NextLine.startsWith("["))
    {
     inprop = false;
     break;
    }
    else
    {
     StringTokenizer st = new StringTokenizer(NextLine , "=");
     String key = null;
     try
     {
      key = st.nextToken().toLowerCase();
      String value = st.nextToken();
      h.put(key,value);
      String[] stringarr = {key,value};
      lastgroup.add(stringarr);
     }
     catch(Exception ste) 
     {
      if(h.containsKey(key)) h.remove(key);
     }
    }
   }
  }
  return h;
 }

 public synchronized void writeIni(LinkedList ll) throws IOException
 {
  String[] lines = this.getIniLines();
  PrintWriter fw = new PrintWriter(new FileWriter(Filename));
  boolean inprop = false;
  boolean foundtag = false;
  for(int i=0 ; i<lines.length ; i++)
  {
   String NextLine = lines[i].trim();
   if(NextLine.toLowerCase().equals("[" + Property.toLowerCase() + "]"))
   {
    foundtag = true;
    inprop = true;
    fw.println(NextLine);
    ListIterator li = ll.listIterator(0);
    while(li.hasNext())
    {
     String[] nextpair = (String[])li.next();
     fw.println(nextpair[0] + "=" + nextpair[1]);
    } 
    continue;
   } 
   if(inprop)
   {
    if(NextLine.startsWith("["))
    {
     inprop = false;
    }
    else
    {
     continue;
    }
   }
   fw.println(NextLine);
  }
  if(foundtag == false)
  {
   fw.println("[" + Property.trim() + "]"); 
   ListIterator li = ll.listIterator(0);
   while(li.hasNext())
   {
    String[] nextpair = (String[])li.next();
    fw.println(nextpair[0] + "=" + nextpair[1]);
   } 
  }
  fw.flush();
  fw.close();
 }

 private String[] getIniLines() throws FileNotFoundException,
	IOException
 {
  BufferedReader br = new BufferedReader(new FileReader(Filename));
  Vector v = new Vector();
  String NextLine = null;
  while((NextLine = br.readLine()) != null)
  {
   v.add(NextLine);
  }
  br.close();
  String[] lines = new String[v.size()];
  for(int i=0 ; i<v.size() ; i++)
  {
   lines[i] = (String)v.elementAt(i);
  }
  return lines;
 }

 public static void main(String[] args)
 {
  try
  {
   File f = new File("Newzgrabber.ini");
   String prop = "Options";
   LinkedList l = new LinkedList();
   String[] s1 = {"Server","NNTP"};
   String[] s2 = {"Username","Jeffee"};
   String[] s3 = {"Password","Yesum"};
   String[] s4 = {"Threads","4"};
   l.add(s1);
   l.add(s2);
   l.add(s3);
   l.add(s4);
   Ini iu = new Ini(f,prop);
   iu.writeIni(l);
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}