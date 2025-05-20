package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;

public class BufferedCustomInputStream extends BufferedInputStream {

	private boolean REMOVE_CARRIAGES = true;
	protected int TIMEOUT = 10*1000;
	protected int INCR=100;
	private CustomFileOutputStream tmpfos = null;
	private boolean WRITE_TO_TEMP = false;

	public BufferedCustomInputStream(InputStream is, int i,boolean b) {
		super(is,i);
		REMOVE_CARRIAGES = b;
	}
	
	public BufferedCustomInputStream(InputStream is, int i) {
		this(is,i,true);
	}

	public BufferedCustomInputStream(InputStream is,boolean b) {
		super(is);
		REMOVE_CARRIAGES = b;
	}

	public BufferedCustomInputStream(InputStream is) {
		this(is,true);
	}

	public void setRemoveCarriages(boolean b) {
		REMOVE_CARRIAGES = b;
	}

	public void closeTemp() throws IOException {
		if(WRITE_TO_TEMP)
			tmpfos.close();
	}

	public void close() throws IOException {
		super.close();
		if(WRITE_TO_TEMP) {
			tmpfos.close();
			System.out.println("Found " + tmpfos.getCarriages() + " carriage returns");
		}
	}

	public void setTempfile(String s) throws IOException {
		tmpfos = new CustomFileOutputStream(s);
		WRITE_TO_TEMP = true;
	}		
		
	public byte[] readByteLine() throws IOException {
		Vector bytevector = new Vector();
		try {
			int CHECKCOUNT=0;
			while(true) {
				int character = this.read();
				if(WRITE_TO_TEMP) {
					tmpfos.write(character);
				}
				if(character == 10) break;
				if(character == 13) {
					if(REMOVE_CARRIAGES) continue;
				}
				byte tmpbyte = (byte)character;
				bytevector.add(new Byte(tmpbyte));
			}
			byte[] byteline = new byte[bytevector.size()];
			for(int i=0 ; i<bytevector.size(); i++) {
				byteline[i] = ((Byte)bytevector.elementAt(i)).byteValue();
			}
			return byteline;
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public String readLine() {
		StringBuffer sb = new StringBuffer();
		try {
			int CHECKCOUNT=0;
			while(true) {
				int character = this.read();
				if(WRITE_TO_TEMP) {
					tmpfos.write(character);
				}

				if(character == 10) break;
				if(character == 13) {
					if(REMOVE_CARRIAGES) continue;
				}
				char newchar = (char)character;
				sb.append(newchar);
			}
			return sb.toString();
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean ready() {
		try {
			return(this.available() > 0);
		} catch(Exception e) {
			return false;
		}
	}
				
	public static void main(String[] args) {
		try {
			String filename = args[0];
			BufferedCustomInputStream bis = new BufferedCustomInputStream(new FileInputStream(filename));
			byte[] byteline = null;
			while((byteline = bis.readByteLine()) != null) {
				System.out.println("Starting new byte line");
				for(int i=0 ; i<byteline.length; i++) {
					System.out.println("INDEX:" + i +
						" BYTE:" + byteline[i]);
				}
			}	
			bis.close();
			BufferedCustomInputStream is = new BufferedCustomInputStream(new FileInputStream(filename));
			
			String line = null;
			while((line = is.readLine()) != null)
				System.out.println(line);
			is.close();
		} catch(Exception e) {
			System.err.println("Usage: java BufferedCustomInputStream <file>");
		}	
	}

}