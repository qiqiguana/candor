package osa.ora.server.utils;

// -----------------------------------------------------------------------------
// FileEncrypter.java
// -----------------------------------------------------------------------------
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import java.io.IOException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class FileEncrypter {
    private static FileEncrypter fileEncrypter = null;
    private Cipher enCipther;
    private Cipher deCipther;
    /**
     * Singleton
     */
    public static FileEncrypter getInstance(){
        if (fileEncrypter == null)
            fileEncrypter = new FileEncrypter("FIM".getBytes());
        return fileEncrypter;
    }
    public static FileEncrypter getInstanceInit(byte[] strkey){
        fileEncrypter = new FileEncrypter(strkey);
        return fileEncrypter;
    }

    /**
     * private Constructor used to create this object.  Responsible for setting
     * and initializing this object's encrypter and decrypter Chipher instances
     * given a Pass Phrase and algorithm.
     * @param strkey Pass the key used to initialize both the encrypter and
     *                   decrypter instances.
     *
     */
    private FileEncrypter(byte[] strkey) {
           byte[] ivr = {
            (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
            (byte)0x56, (byte)0x34, (byte)0xE3, (byte)0x03};
        try {
            SecretKeySpec key = new SecretKeySpec(strkey, "Blowfish");
             enCipther = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
             deCipther = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
             IvParameterSpec oIV = new IvParameterSpec(ivr);
             enCipther.init(Cipher.ENCRYPT_MODE, key,oIV);
             deCipther.init(Cipher.DECRYPT_MODE, key,oIV);
        } catch (Exception e) {
            System.out.println("EXCEPTION:"+e.getMessage());
        }
    }
    /**
     * to encrypt a box of byte array
     * @param to_encrypt
     * @return byte[]
     */
    private byte[] encryptBlowfish(byte[] to_encrypt){
        try {
            //to_encrypt=StringEncoder64.encode(to_encrypt).getBytes("UTF8");
            return enCipther.doFinal(to_encrypt);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * to decrypt a box of byte array
     * @param to_decrypt
     * @return byte[]
     */
    private byte[] decryptBlowfish(byte[] to_decrypt){
        try {
            to_decrypt=deCipther.doFinal(to_decrypt);
            //to_decrypt=StringEncoder64.decode(new String(to_decrypt,"UTF8"));
            return to_decrypt;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * to encrypt file using temp file and return the data into byte[]
     * and delete the temp file
     * @param file
     * @param file2
     * @return byte[] of encrypted file
     */
    public byte[] fileEncrypt(File file,File file2) {
            FileInputStream fis=null;
            FileOutputStream fos=null;
            try{
                fos=new FileOutputStream(file2);
                fis=new FileInputStream(file);
                int lenght=fis.available();
                //System.out.println("Length="+lenght);
                String len=""+lenght;
                while(len.length()<8){
                    len="0"+len;
                }
                fos.write(len.getBytes());
                byte[] data=null;
                for(int i=0;i<lenght;i=i+8){
                    data=new byte[8];
                    fis.read(data);
                    data=StringEncoder64.encode(data).getBytes("UTF8");
                    byte[] enc=encryptBlowfish(new String(data).getBytes("UTF8"));
                    //System.out.println("Length="+enc.length);
                    fos.write(enc);
                }
                fos.close();
                fis.close();
                fis=new FileInputStream(file2);
                int n=fis.available();
                data=new byte[n];
                fis.read(data);
                fis.close();
                file2.delete();
                return data;
            } catch (Exception ex) {
                //ex.printStackTrace();
                return null;
            }finally {
                try {
                    if(fis!=null) fis.close();
                    if(fos!=null) fos.close();
                } catch (IOException ex) {
                }
            }
    }
    /**
     * to decrypt array of bytes and create an output file
     * @param inputData
     * @param file2 the decrypted file.
     */
    public void fileDecrypt(byte[] inputData,File file2) {
            FileOutputStream fos=null;
            try{
                fos=new FileOutputStream(file2);
                byte[] data=new byte[8];
                System.arraycopy(inputData, 0, data, 0,8);
                int lenght=Integer.parseInt(new String(data));
                //System.out.println("Original size="+lenght);
                int partitions=lenght/8;
                //System.out.println("partitions="+partitions);
                int last=lenght%8;
                //System.out.println("last size="+last);
                int current=0;
                for(int i=0;i<partitions;i++){
                    data=new byte[16];
                    System.arraycopy(inputData, 8+i*16, data, 0,16);
                    byte[] dec=decryptBlowfish(data);
                    dec=StringEncoder64.decode(new String(dec,"UTF8"));
                    fos.write(dec);
                    //System.out.println("Current="+current);
                    current++;
                }
                if(last!=0){
                    //System.out.println("in last");
                    data=new byte[16];
                    System.arraycopy(inputData, 8+partitions*16, data, 0,16);
                    byte[] dec=decryptBlowfish(data);
                    dec=StringEncoder64.decode(new String(dec,"UTF8"));
                    byte[] temp=new byte[last];
                    System.arraycopy(dec, 0, temp, 0, last);
                    //System.out.println("last="+temp);
                    fos.write(temp);
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
                return;
            }finally {
                try {
                    if(fos!=null) fos.close();
                } catch (IOException ex) {
                }
            }
    }
    /**
     * main test method
     * @param args
     */
    public static void main(String[] args) {
        FileEncrypter stringEnc = FileEncrypter.getInstanceInit("Modern Chat".getBytes());
        try {
            byte[] data=stringEnc.fileEncrypt(new File("/osama2.zip"),new File("/osama3.zip"));
            stringEnc.fileDecrypt(data,new File("/osama4.zip"));
        } catch (Exception ex) {

        }
    }
}