package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class yEncDecoder extends MimeDecoder {

	protected final static int BUFFERSIZE=4096*8;
	private int tmpcount=0;

	public yEncDecoder(BufferedCustomInputStream is,OutputStream os) {
		this.br = is;
		this.os = os;
	}

	public yEncDecoder(BufferedCustomInputStream is) {
		this.br = is;
	}
 
	public void decodeStream() throws IOException {
		try {
			boolean foundend = false;
			if(br.markSupported() == false)
				throw new IOException("Mark not supported " +
					"by " + br.toString());
			
			Vector bytelines = null;
			if(Newzgrabber.verbose)
				System.out.println("Calling readLine()");
//			bytelines = this.readLines();
			
			String line = null;
			while((line = br.readLine()) != null) {
				tmpcount++;
				if(!Newzgrabber.IsBatch)
					if((++LineCount % 25) == 0)
						this.updateProgress();
				/*
				if(tmpcount < 10) {
					if(Newzgrabber.verbose) {
						System.out.println("LINE:" + tmpcount + "\n" + line);
					}
				}
				*/
				if(line.startsWith("=ybegin")) {
					foundbegin = true;
					String filename = null;
					int fileindex = line.indexOf("name=");
					int endindex = line.indexOf(" ",fileindex);
					if(endindex == -1)
						endindex = line.length();
					if(fileindex > -1) {
						filename = line.substring(fileindex + 5 , endindex);
						if(os == null)
							os = new FileOutputStream(filename);
					}
				} else if(line.startsWith("=yend")) {
					foundend = true;
					continue;
				} else if(line.equals(".")) {
					break;
				} else if(line.startsWith("=y")) {
					continue;
				} else if(foundend) {
					continue;
				} else if(foundbegin) {
					if(line.startsWith("..")) {
						line = line.substring(1);
					}
			
					Vector vbytes = new Vector();
					int byteswritten=0;
					
					for(int x=0;x<line.length();x++) {
						char tmpc = line.charAt(x);
						if(tmpc == '\r' ||
							tmpc == '\n' ||
							tmpc <= 0 ||
							tmpc > 255)
							continue;
						char writec;
						if(tmpc == '=') {
							tmpc = line.charAt(++x);
							writec = (char)(((((tmpc - 64) % 255) - 42) % 255));
//							System.out.println("Adding special byte for " + character + " at " + byteswritten);
						} else {
							writec = (char)((((tmpc - 42) % 255)));
						}
						os.write(writec);
						byteswritten++;
					}
/*
					for(int i=0 ; i<vbytes.size(); i++)
						os.write(((Integer)vbytes.elementAt(i)).intValue());
*/
				}
			}
//			br.close();
/*
			if(foundbegin == false)
				throw new IOException("No =ybegin line found");
*/
			if(Newzgrabber.verbose)
				System.out.println("The foundbegin was " + foundbegin);
		} catch(Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	private String bytesToString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for(int i=0 ; i<bytes.length ; i++) {
			int character = (int)bytes[i];
			if(character < 0) {
				character = Math.abs(character) + 127;
			}
			char c = (char)character;
			sb.append(c);
		}
		return sb.toString();
	}
			
			
	private Vector readLines() {
		Vector allbytes = new Vector();
		try {
			int read = -1;
			int loopcnt=0;
			boolean foundnewline = false;
			boolean FoundDot = false;
			Vector bytevector = new Vector();
			byte[] buffer = new byte[BUFFERSIZE];
			if(Newzgrabber.verbose)
				System.out.println("Calling read on is");
			while(true) {
				read = br.read(buffer,0,BUFFERSIZE);
				if(read < 0) break;
				if(Newzgrabber.verbose)
					System.out.println("Read chunk " +
						loopcnt + "...");
				loopcnt++;
				for(int i=0 ; i<read ; i++) {
					if(buffer[i] == 13) continue;
					if(buffer[i] == 10) {	
						foundnewline = true;
						byte[] newbytes = new byte[bytevector.size()];
						for(int j=0 ; j<bytevector.size() ; j++) {
							newbytes[j] = ((Byte)bytevector.elementAt(j)).byteValue();
						}
						if(newbytes.length == 1 && newbytes[0] == 46) {
							FoundDot = true;
							break;
						}
						allbytes.add(newbytes);
						bytevector = new Vector();
						continue;	
					}
					bytevector.add(new Byte(buffer[i]));
				}
				if(FoundDot) {
					if(Newzgrabber.verbose)
						System.out.println("Breaking (found dot)");
					break;
				}
			}
		} catch(Exception e) {
			return null;
		}
		return allbytes;
	}


	public static void main(String[] args) {
		try {
			String[] files = {"file.enc"};
			String outfile = "decode.jpg";
			FileOutputStream fos = new FileOutputStream(outfile);
			for(int i=0 ; i<files.length ; i++) {
				FileInputStream fis = new FileInputStream(files[i]);
				BufferedCustomInputStream bis = new BufferedCustomInputStream(fis);
				yEncDecoder y = new yEncDecoder(bis,fos);
				y.decodeStream();
				bis.close();
				System.out.println("Decoded " + files[i]);
			}
			fos.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
		
}

 