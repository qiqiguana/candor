package Newzgrabber;
import Newzgrabber.*;

import java.util.*;
import java.io.*;

public class PassEnc
{
 
 public static String encodePassword(String s)
 {
  StringBuffer sb = new StringBuffer();
  for(int i=0 ; i<s.length() ; i++)
  {
   char c = s.charAt(i);
   char newchar = (char)((int)c + i + 2);
   sb.append(newchar);
  }
  return sb.toString();
 }

 public static String decodePassword(String s)
 { 
  StringBuffer sb = new StringBuffer();
  for(int i=0 ; i<s.length() ; i++)
  {
   char c = s.charAt(i);
   char newchar = (char)((int)c - i - 2);
   sb.append(newchar);
  }
  return sb.toString();
 }

 public static void main(String[] args)
 {
  try
  {
   if(args.length < 1)
   {
    System.err.println("Usage: java PassEnc <password>");
    System.exit(1);
   }
   String password = args[0];
   String eP = PassEnc.encodePassword(password);
   String dP = PassEnc.decodePassword(eP);
   System.out.println("The encoded password is '" + eP + "'"); 
   System.out.println("The decoded password is '" + dP + "'"); 
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
  }
 }

}