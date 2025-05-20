package Newzgrabber;
import Newzgrabber.*;

import java.io.*;

public abstract class MimeDecoder
{
 
 protected BufferedCustomInputStream br = null;
 protected OutputStream os = null;
 protected int MARKLIMIT = 1000;
 public boolean ProgressSet = false;
 protected long LineCount;
 private Object StatusObject;
 protected boolean foundbegin = false;
 private long TotalLines;
 private int StatusRow;
 public boolean ABORT = false;
 
 public void setReader(InputStream i)
 {
  br = new BufferedCustomInputStream(i);
 }

 public void setBegin(boolean b) {
	foundbegin = b;
 }

 public void setOutputStream(OutputStream o)
 {
  os = o;
 }

 public void setTotalLines(long t)
 {
  TotalLines = t;
 }

 public void setStatusRow(int r)
 {
  StatusRow = r;
 }

 public void updateProgress()
 {
  if(TotalLines > 0)
  {
   int PercentDone = (int)((LineCount * 100)/ TotalLines);
   String status = String.valueOf(PercentDone) + "%";
   TransferPanel.downloading.setValueAt(status,StatusRow,1);
  }
 }

 public void setStatusObject(Object s)
 {
  StatusObject = s;
 }

 public BufferedCustomInputStream getReader()
 {
  return br;
 }

 public OutputStream getOutputStream()
 {
  return os;
 }

 public long getLineCount()
 {
  return LineCount;
 }

 public void setLineCount(long l)
 {
  LineCount = l;
 }
 
 public abstract void decodeStream()
	throws IOException; // must be overridden by subclass

}