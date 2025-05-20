package nu.staldal.lagoon.filestorage;

import nu.staldal.lagoon.core.*;

import java.io.*;

public class TestSSHFileStorage
{
    public static void main(String[] args) throws Exception
    {
		if (args.length < 5)
		{
			System.out.println(
				"Syntax: SSHFileStorage <url> "
				+ "<file to create> <file to check> <file to abort> "
				+ "<file to delete>");
			return;
		}

		FileStorage fs = new SSHFileStorage();
        fs.open(args[0], null, null); // ***
        System.out.println("FileStorage opened");

    	OutputHandler oh = fs.createFile(args[1]);
        System.out.println("createFile returned");

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(oh.getOutputStream()));
		pw.println("<html><head><title>Test</title></head>");
		pw.println("<body><p><i>Testar SSHFileStorage: " + System.currentTimeMillis() + "</i></p></body></html>");
		pw.flush();
		oh.commit();
		System.out.println("commitFile returned");

		long d = fs.fileLastModified(args[2]);
		System.out.println("fileLastModified(" + args[2] + "): " + d);

		oh = fs.createFile(args[3]);
        System.out.println("createFile returned");
		oh.getOutputStream().write(56);
		oh.discard();
		System.out.println("discardFile returned");

		fs.deleteFile(args[4]);
		System.out.println("deleteFile returned");

		fs.close();
		System.out.println("FileStorage closed");
    }
}
