package Newzgrabber;
import Newzgrabber.*;

import java.util.*;

public class SongList
{

 private Hashtable Songs;

 public SongList(String[] Subjects)
 {
  Songs = new Hashtable();
  this.buildHash(Subjects);
 }

 public Hashtable getSongs() 
 {
  return Songs;
 }

 public String[] getCompleteSongs()
 {
  try
  {
   Vector v = new Vector();
   Enumeration enume = Songs.keys();
   while(enume.hasMoreElements())
   {
    String key = (String)enume.nextElement();
    Hashtable h = (Hashtable)Songs.get(key);
    int UpperLimit = ((Integer)h.get("UPPERLIMIT")).intValue();
//    if(Newzgrabber.verbose) 
//	System.out.println("The UPPERLIMIT for " + key + " is\n" + UpperLimit);
    if(h.size() > UpperLimit)
    {
     v.add(key);
    }
    else
    {
//     if(Newzgrabber.verbose) System.out.println(key + " is incomplete");
    }
   }
   String[] s = new String[v.size()];
   for(int i=0 ; i<v.size() ; i++)
   {
    s[i] = (String)v.elementAt(i);
   }
   return s;
  } 
  catch(Exception e)
  {
   if(Newzgrabber.verbose) System.err.println(e.getMessage());
   return null;
  }
 }

 private void buildHash(String[] s)
 {
  try
  {
   for(int i=0 ; i<s.length ; i++)
   {
    try
    {
     if(s[i].trim().toLowerCase().startsWith("221 subject fields"))
 	continue;
     SubjectParser sp = new SubjectParser(s[i].trim());
     long id = sp.getId();
     String title = sp.getTitle().trim();
     int upper = sp.getUpperRange();
     int thispart = sp.getThisRange();
     SongInfo si = new SongInfo(id,thispart,upper);
     if(Songs.containsKey(title))
     {
      Hashtable h = (Hashtable)Songs.get(title);
      h.put(new Integer(thispart),si);
      Songs.put(title,h);
     }
     else
     {
      Hashtable h = new Hashtable();
      h.put(new Integer(thispart),si);
      h.put(new String("UPPERLIMIT"),new Integer(upper));
      Songs.put(title,h);
     }
 /*
     System.out.println("Just updated subhash");
     Hashtable tmph = (Hashtable)Songs.get(title);
     System.out.println("It now contains " + tmph.size() + " elements");
     Enumeration tmpe = tmph.keys();
     if(tmpe != null)
     {
      System.out.println("The enumeeration is not NULL");
      while(tmpe.hasMoreElements())
      {
       System.out.println("Getting the next element");
       try
       {
        Integer tmpi = (Integer)tmpe.nextElement();
        System.out.println("Got it!");
        if(tmpi == null)
        {
         System.out.println("Skipping next element because the key is NULL");
         continue;
        }
        System.out.println("Getting ready to print the key value...");
        System.out.println(String.valueOf(tmpi.intValue()));
       }
       catch(Exception ee)
       {
        System.err.println(ee.getMessage());
        ee.printStackTrace();
       }
      }
     }
     else
     {
      System.out.println("Skipping NULL enumeration");
      continue;
     }
 */
    }
    catch(Exception parseE)
    {
     if(Newzgrabber.verbose)
     {
	System.out.println(parseE.getMessage());
	parseE.printStackTrace();
     }
     continue;
    }
   }
  }
  catch(Exception e)
  {
   if(Newzgrabber.verbose)
   {
    System.err.println(e.getMessage());
    e.printStackTrace();
   }
  }
 }

}