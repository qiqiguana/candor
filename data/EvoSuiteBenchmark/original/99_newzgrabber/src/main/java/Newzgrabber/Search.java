package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;

public class Search
{

 public static String formatPattern(String pattern)
 {
  try
  {
   StringBuffer sb = new StringBuffer("");
   String tmpPat = pattern.trim();
   if(tmpPat.startsWith("*") == false)
   {
    sb.append("*");
   }
   for(int i=0 ; i<tmpPat.length() ; i++)
   {
    char c = tmpPat.charAt(i);
    if(Character.isLetter(c))
    {
     sb.append("[" + Character.toUpperCase(c) + 
	Character.toLowerCase(c) + "]");
    }
    else if(Character.isSpaceChar(c))
    {
     sb.append("*");
    }
    else
    {
     sb.append(c);
    }
   }
   if((sb.charAt(sb.length()-1) == '*') == false)
   {
    sb.append("*");
   }
   return sb.toString();
  }
  catch(Exception e)
  {
   return null;
  }
 }

 public static void main(String[] args)
 {
  try
  {
   if(args.length < 1)
   {
    System.out.println("Usage: java Search <args>");
    System.exit(1);
   }
   StringBuffer sb = new StringBuffer("");
   for(int i=0 ; i<args.length ; i++)
   {
    sb.append(args[i] + " ");
   }
   String fPat = formatPattern(sb.toString().trim());
   System.out.println("The formatted pattern is:\n" + fPat);
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}