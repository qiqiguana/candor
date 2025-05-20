package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;

public class UUDecoder extends MimeDecoder
{

 public UUDecoder(BufferedCustomInputStream i, OutputStream o)
 {
  this.br = i;
  os = o;
 }

 public void decodeStream() throws IOException
 {
  String NextLine = null;
//  while((NextLine = br.readLine()) != null)
  while(true)
  {
   if(ABORT) break;
   NextLine = br.readLine();
   if(!Newzgrabber.IsBatch)
	if((++LineCount % 25) == 0) this.updateProgress();
   if(NextLine.length() < 1)
   {
    continue;
   }
   if(NextLine.equals("."))
   {
    break;
   } 
   char lenChar = ' ';
   int TotalByteLength = 0, TotalCharLength = 0, ordValue = 0;
   int Expected = 0;
   char[] TheChars = NextLine.toCharArray();
   lenChar = TheChars[0];
   ordValue = (lenChar - 32) & 63;
   Expected = ((ordValue+2)/3)<<2;
   if((int)((ordValue+2)/3) != (int)((NextLine.length())/4)) 
   {
    if(Newzgrabber.verbose)
	System.out.println("Skipping:" + LineCount + "'" + NextLine + "'");
    continue;
   }
   try
   {
    this.writeOut(TheChars, ordValue);
//    Downloader.debug.println(NextLine);
   }
   catch(Exception oob)
   {
/*
    if(Downloader.debug != null)
    {
     Downloader.debug.println("There was a problem decoding:\n" + NextLine +
	oob.getMessage());
    }
*/
   }
  }
 }
 

 private void writeOut(char[] charBuf , int ordValue) 
	throws ArrayIndexOutOfBoundsException,IOException
 {
  int bufPos = 1;
  while(ordValue > 0) 
  {
   int c1 = (this.decChar(charBuf[bufPos])<<2) | (this.decChar(charBuf[bufPos+1])>>4);
   int c2 = (this.decChar(charBuf[bufPos+1])<<4) | (this.decChar(charBuf[bufPos+2])>>2);
   int c3 = (this.decChar(charBuf[bufPos+2])<<6) | (this.decChar(charBuf[bufPos+3]));
   if(ordValue >= 1) 
   {
    Integer thisInt = new Integer(c1); 
    os.write(thisInt.byteValue());
   } 
   if(ordValue >= 2)
   {
    Integer thisInt = new Integer(c2);
    os.write(thisInt.byteValue());
   } 
   if(ordValue >= 3)
   {
    Integer thisInt = new Integer(c3);
    os.write(thisInt.byteValue());
   } 
   bufPos += 4;
   ordValue -= 3;
  }
 }
       
 private int decChar(char c)
 {
  return((c-32)&63);
 }

 public static void main(String[] args)
 {
  try
  {
   String infile = args[0];
   String ofile = args[1];
   BufferedCustomInputStream fis = new BufferedCustomInputStream(
	new FileInputStream(infile));
   FileOutputStream fos = new FileOutputStream(ofile);
   UUDecoder u = new UUDecoder(fis,fos);
   u.decodeStream();
   fis.close();
   fos.close();
   System.out.println("Decoded file");
  }
  catch(Exception e)
  {
   System.err.println(e.getMessage());
   System.err.println("Usage: java UUDecoder <input file> <output file>");
  }
 }

} 
	
	
