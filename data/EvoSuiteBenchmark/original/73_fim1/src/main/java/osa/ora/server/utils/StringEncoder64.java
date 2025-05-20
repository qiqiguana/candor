package osa.ora.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class StringEncoder64 {
    
    static final char[] charTab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    
    public static String encodeStringUTF8(String data){
        try {
            return encode(data.getBytes("UTF8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String decodeStringUTF8(String data){
        try {
            return new String(decode(data),"UTF8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String encode(byte[] data) {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        String ret = null;
        if (encode(data, 0, data.length, byteArrayOutputStream))
            ret = new String(byteArrayOutputStream.toByteArray());
        return ret;
    }
    
    public static String encode(byte[]data,int start,int len){
        StringBuffer buffer=null;
        return encode(data,start,len,buffer).toString();
    }
    
    /**
     * Encodes the part of the given byte array denoted by start and len to the StringEncoder64 format. The encoded data is
     * appended to the given StringBuffer. If no StringBuffer is given, a new one is created automatically. The
     * StringBuffer is the return value of this method.
     */
    
    public static StringBuffer encode(byte[] data, int start, int len, StringBuffer buf) {
        
        if (buf == null)
            buf = new StringBuffer(data.length * 3 / 2);
        
        int end = len - 3;
        int i = start;
        // int n = 0;
        
        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);
            
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append(charTab[(d >> 6) & 63]);
            buf.append(charTab[d & 63]);
            
            i += 3;
            
            // if (n++ >= 14) {
            // n = 0;
            // buf.append("\r\n");
            // }
        }
        
        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);
            
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append(charTab[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;
            
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append("==");
        }
        
        return buf;
    }
    /**
     * Encodes the part of the given byte array denoted by start and len to the StringEncoder64 format. The encoded data is
     * written to the given {@link OutputStream}. If no OutputStream is given, a {@link NullPointerException} is thrown.
     *
     * @return true if succeded to encode, false otherwise
     * @throws NullPointerException if outputStream is null.
     */
    
    public static boolean encode(byte[] data, int start, int len, OutputStream outputStream) {
        
        try {
            if (outputStream == null)
                throw new NullPointerException();
            
            int end = len - 3;
            int i = start;
            int n = 0;
            
            while (i <= end) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);
                
                outputStream.write(charTab[(d >> 18) & 63]);
                outputStream.write(charTab[(d >> 12) & 63]);
                outputStream.write(charTab[(d >> 6) & 63]);
                outputStream.write(charTab[d & 63]);
                
                i += 3;
                
                if (n++ >= 14) {
                    n = 0;
                    outputStream.write('\r');
                    outputStream.write('\n');
                }
            }
            
            if (i == start + len - 2) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);
                
                outputStream.write(charTab[(d >> 18) & 63]);
                outputStream.write(charTab[(d >> 12) & 63]);
                outputStream.write(charTab[(d >> 6) & 63]);
                outputStream.write('=');
            } else if (i == start + len - 1) {
                int d = (((int) data[i]) & 0x0ff) << 16;
                
                outputStream.write(charTab[(d >> 18) & 63]);
                outputStream.write(charTab[(d >> 12) & 63]);
                outputStream.write('=');
                outputStream.write('=');
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    static int decode(char c) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    System.out.println("C="+c);
                    throw new RuntimeException("unexpected code: " + c);
            }
    }
    
    /**
     * Decodes the given StringEncoder64 encoded String to a new byte array. The byte array holding the decoded data is returned.
     */
    
    public static byte[] decode(String s) {
        //System.out.println("decode String s="+s);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return bos.toByteArray();
    }
    
    public static void decode(String s, OutputStream os) throws IOException {
        int i = 0;
        
        int len = s.length();
        
        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;
            
            if (i+3 >= len)
                break;
            
            int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
            + (decode(s.charAt(i + 3)));
            
            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);
            
            i += 4;
        }
    }
}
