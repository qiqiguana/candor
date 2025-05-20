package nu.staldal.ftp;

import java.io.*;

// import junit.framework.*;

public class TestFTPClient // extends TestCase
{
    public TestFTPClient(String name)
    {
        // super(name);
    }

	private void sendData(OutputStream os, String str)
		throws IOException
	{
		os.write(str.getBytes("iso-8859-1"));
	}

    public void testFTPClient(String[] args) throws Exception
	{
        FTPClient ftp = new FTPClient(args[0], args[1]);
        System.out.println("Connected to FTP server");

        OutputStream os;
		
		os = ftp.store("foo/store.txt");
        sendData(os, "foo/store.txt\n");
        os.close();

        os = ftp.store("bar/append.txt");
        sendData(os, "bar/append.txt\n");
        os.close();

        os = ftp.append("bar/append.txt");
        sendData(os, "bar/append.txt 2\n");
        os.close();

        os = ftp.append("foo/append.txt");
        sendData(os, "foo/append.txt\n");
        os.close();

        os = ftp.store("bar/store.txt");
        sendData(os, "bar/store.txt\n");
        os.close();

        os = ftp.store("bar/store.txt");
        sendData(os, "bar/store.txt 2\n");
        os.close();

        os = ftp.storeUnique("bar/");
        sendData(os, "bar/UNIQUE\n");
        os.close();

        os = ftp.storeUnique("");
        sendData(os, "UNIQUE\n");
        os.close();

        os = ftp.storeUnique("");
        sendData(os, "UNIQUE 2\n");
        os.close();

        os = ftp.store("delete.txt");
        sendData(os, "delete.txt 2\n");
        os.close();
		
		ftp.deleteFile("delete.txt");

        ftp.close();
        System.out.println("Disconnected from FTP server");
	}

	
	public static void main(String[] args) throws Exception
	{
		(new TestFTPClient("foo")).testFTPClient(args);
	}
}

