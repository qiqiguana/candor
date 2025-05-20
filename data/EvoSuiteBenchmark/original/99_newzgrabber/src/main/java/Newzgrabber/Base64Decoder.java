package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;

public class Base64Decoder extends MimeDecoder
{

 private int tmpcount = 0;

 public Base64Decoder(BufferedCustomInputStream i, OutputStream o)
 {
  this.br = i;
  os = o;
 }

 public void decodeStream() throws IOException
 {
  String NextLine = null;
  if(Newzgrabber.verbose)
	System.out.println("The following are the lines from the base64 decode");
  while((NextLine = br.readLine()) != null)
  {
   if(Newzgrabber.verbose)
	System.out.println(NextLine);
   if(ABORT) break;
   if(!Newzgrabber.IsBatch)
	if((++LineCount % 25) == 0) this.updateProgress();
   if(NextLine.equals(".")) break;
   else if(NextLine.length() < 1)
   {
    continue;
   }
   else if((NextLine.length() > 1)&&((NextLine.length() % 4) == 0))
   {
    int Index = 0;
    int[] ibuf = new int[4];
    int[] obuf = new int[3];
    boolean Ignore = false;
    boolean Endtext = false;
    char[] TheChars = NextLine.toCharArray();
    int StrLength = 0;
    for(int i=0 ; i < TheChars.length ; i++)
    {
     char TheChar = TheChars[i];
     if(TheChar >= 'A' && TheChar <= 'Z')
     TheChar = (char)((int)TheChar - 'A');
     else if(TheChar >= 'a' && TheChar <= 'z')
      TheChar = (char)((int)TheChar - 'a' + 26);
     else if(TheChar >= '0' && TheChar <= '9')
      TheChar = (char)((int)TheChar - '0' + 52);
     else if(TheChar == '+')
      TheChar = (char)(62);
     else if(TheChar == '=')
     {
/*
      if(Endtext)
      {
       DoneDecoding = true;
      }
*/
      Endtext = true;
      Index--;
      if(Index < 0)
       Index = 3;
     }
     else if(TheChar == '/')
      TheChar = (char)(63);
     else
      Ignore = true;
     if(!Ignore)
     {
      if(!Endtext)
      {
       ibuf[Index] = (int)TheChar;
       Index++;
       Index = Index & 3;
      }
      if(Index == 0 || Endtext)
      {
       obuf[0] = (ibuf[0]<<2) | ((ibuf[1]&48)>>4);
       obuf[1] = ((ibuf[1]&15)<<4) | ((ibuf[2]&60)>>2);
       obuf[2] = ((ibuf[2]&3)<<6) | (ibuf[3]&63);
       Integer Obuf0Int = new Integer(obuf[0]);
       Integer Obuf1Int = new Integer(obuf[1]);
       Integer Obuf2Int = new Integer(obuf[2]);
       switch(Index)
       {
        case 1:
         os.write(Obuf0Int.byteValue());
         StrLength++;
         break;
        case 2:
         os.write(Obuf0Int.byteValue());
         os.write(Obuf1Int.byteValue());
         StrLength += 2;
         break;
        default:
         os.write(Obuf0Int.byteValue());
         os.write(Obuf1Int.byteValue());
         os.write(Obuf2Int.byteValue());
         StrLength += 3;
         break;
       }
      }
      if(Endtext)
      {
       try
       {
        os.close();
       }
       catch(IOException e)
       {
         continue;
       }
       Endtext = false;
       break;
      } 
     }
    }
   }
   else
    continue;
  }
 }

} 