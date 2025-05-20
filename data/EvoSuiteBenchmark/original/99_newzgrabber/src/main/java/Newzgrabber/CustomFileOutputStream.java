package Newzgrabber;
import Newzgrabber.*;

import java.io.*;

public class CustomFileOutputStream extends FileOutputStream {

	private int lastchar;
	private int CARRIAGES=0;

	public CustomFileOutputStream(String s) throws IOException {
		super(s);
		lastchar = -1;
	}

	public int getCarriages() {
		return CARRIAGES;
	}

	public void write(int c) throws IOException {
		if(c != 0 && c != 13) {
			super.write(c);
			lastchar = c;
			if(c == 13) {
				System.out.println("Just wrote carriage return");
				CARRIAGES++;
			}
		}
	}

}
