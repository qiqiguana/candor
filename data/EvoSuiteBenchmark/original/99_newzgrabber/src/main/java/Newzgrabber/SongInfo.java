package Newzgrabber;
import Newzgrabber.*;

import java.util.*;

public class SongInfo
{
 
 private long MessageId;
 private int ThisPart;
 private int UpperRange;

 public SongInfo(long m, int p, int u)
 {
  MessageId = m;
  ThisPart = p;
  UpperRange = u;
 }

 public long getId()
 {
  return MessageId;
 }
 
 public int getThisPart()
 {
  return ThisPart;
 }

 public int getUpperRange()
 {
  return UpperRange;
 }

 public static long[] getOrderedIds(Hashtable info)
 {
  try
  {
   Vector v = new Vector();
   int max = ((Integer)info.get("UPPERLIMIT")).intValue();
   for(int i=0 ; i<=max ; i++)
   {
    if(info.containsKey(new Integer(i)))
    {
     SongInfo si = (SongInfo)info.get(new Integer(i));
     long id = si.getId();
     v.add(new Long(id));
    }
   }
   long[] ids = new long[v.size()];
   for(int j=0 ; j<v.size() ; j++)
   {
    ids[j] = ((Long)v.elementAt(j)).longValue();
   }
   return ids;
  }
  catch(Exception e)
  {
   if(Newzgrabber.verbose) System.err.println(e.getMessage());
   return null;
  }
 }

}