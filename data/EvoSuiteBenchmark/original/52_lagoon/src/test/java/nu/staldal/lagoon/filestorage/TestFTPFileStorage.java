package nu.staldal.lagoon.filestorage;

import java.io.*;

import nu.staldal.lagoon.core.*;

public class TestFTPFileStorage
{
    public static void main(String[] args) throws Exception
    {
        FileStorage fs = new FTPFileStorage();
        fs.open(args[0], null, null); // ***
        System.out.println("FileStorage opened with " + args[0]);
 
        OutputHandler oh = fs.createFile(args[1]);
        System.out.println("createFile returned");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(oh.getOutputStream()));
        pw.println("<html><head><title>Test</title></head>");
        pw.println("<body><p><i>Testar FTPFileStorage igen: " + System.currentTimeMillis() + "</i></p></body></html>");
        pw.flush();
        oh.commit();
        System.out.println("commitFile returned");
 
        long d = fs.fileLastModified(args[2]);
        System.out.println("fileLastModified(" + args[2] + "): " + d);
 
        fs.deleteFile(args[3]);
        System.out.println("fileDeleted: " + args[3]);
 
        fs.close();
        System.out.println("FileStorage closed");
    }
}
