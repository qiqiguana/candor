package Newzgrabber;
import Newzgrabber.*;

import java.util.*;

public class StringSorter extends TreeMap
{

 private boolean IgnoreCase = false;

 public StringSorter(boolean b)
 {
  super(new StringCompare());
  IgnoreCase = b;
 }

 public StringSorter()
 {
  this(false);
 }

 public void addString(String s)
 {
  if(IgnoreCase)
  {
   this.put(s.toLowerCase(),s);
  }
  else
  {
   this.put(s,s);
  }
 }

 public String[] getSortedArray()
 {
  Set es = this.keySet();
  Object[] oa = es.toArray();
  String[] sorted = new String[oa.length];
  for(int i=0; i<oa.length ; i++)
  {
   sorted[i] = (String)this.get((String)oa[i]);
  }
  return sorted;
 }
 
 public static void main(String[] args)
 {
  try
  {
   if(args.length < 1)
   {
    System.err.println("Usage: java Sorter <word1> <word2> [<word3> etc.]");
    System.exit(1);
   }
   StringSorter s = new StringSorter(true);
   for(int i=0 ; i<args.length ; i++)
   {
    s.addString(args[i]);
   }
   String[] sortedstrings = s.getSortedArray();
   for(int i=0 ; i<sortedstrings.length ; i++)
   {
    System.out.println(sortedstrings[i]);
   }
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}