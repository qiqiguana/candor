/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class JspWriterStub extends JspWriter {
	
	private StringBuffer mBuffer = new StringBuffer();
	
	public JspWriterStub() {
		super(1024, true);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#clear()
	 */
	@Override
	public void clear() throws IOException {
		mBuffer = new StringBuffer();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#clearBuffer()
	 */
	@Override
	public void clearBuffer() throws IOException {
		mBuffer = new StringBuffer();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#close()
	 */
	@Override
	public void close() throws IOException {
		mBuffer = new StringBuffer();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#flush()
	 */
	@Override
	public void flush() throws IOException {
		// nope
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#getRemaining()
	 */
	@Override
	public int getRemaining() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#newLine()
	 */
	@Override
	public void newLine() throws IOException {
		mBuffer.append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(boolean)
	 */
	@Override
	public void print(boolean pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(char)
	 */
	@Override
	public void print(char pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(int)
	 */
	@Override
	public void print(int pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(long)
	 */
	@Override
	public void print(long pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(float)
	 */
	@Override
	public void print(float pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(double)
	 */
	@Override
	public void print(double pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(char[])
	 */
	@Override
	public void print(char[] pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(java.lang.String)
	 */
	@Override
	public void print(String pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#print(java.lang.Object)
	 */
	@Override
	public void print(Object pArg0) throws IOException {
		mBuffer.append(pArg0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println()
	 */
	@Override
	public void println() throws IOException {
		mBuffer.append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(boolean)
	 */
	@Override
	public void println(boolean pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(char)
	 */
	@Override
	public void println(char pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(int)
	 */
	@Override
	public void println(int pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(long)
	 */
	@Override
	public void println(long pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(float)
	 */
	@Override
	public void println(float pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(double)
	 */
	@Override
	public void println(double pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(char[])
	 */
	@Override
	public void println(char[] pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(java.lang.String)
	 */
	@Override
	public void println(String pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspWriter#println(java.lang.Object)
	 */
	@Override
	public void println(Object pArg0) throws IOException {
		mBuffer.append(pArg0).append("\n");
	}

	/* (non-Javadoc)
	 * @see java.io.Writer#write(char[], int, int)
	 */
	@Override
	public void write(char[] pCbuf, int pOff, int pLen) throws IOException {
		mBuffer.append(new String(pCbuf, pOff, pLen));
	}
	
	public StringBuffer getBuffer() {
		return mBuffer;
	}

}
