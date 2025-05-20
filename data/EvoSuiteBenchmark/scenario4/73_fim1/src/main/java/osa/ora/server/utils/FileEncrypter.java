package osa.ora.server.utils;

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
    public static FileEncrypter getInstance();

    public static FileEncrypter getInstanceInit(byte[] strkey);

    /**
     * private Constructor used to create this object.  Responsible for setting
     * and initializing this object's encrypter and decrypter Chipher instances
     * given a Pass Phrase and algorithm.
     * @param strkey Pass the key used to initialize both the encrypter and
     *                   decrypter instances.
     */
    private FileEncrypter(byte[] strkey) {
    }

    /**
     * to encrypt a box of byte array
     * @param to_encrypt
     * @return byte[]
     */
    private byte[] encryptBlowfish(byte[] to_encrypt);

    /**
     * to decrypt a box of byte array
     * @param to_decrypt
     * @return byte[]
     */
    private byte[] decryptBlowfish(byte[] to_decrypt);

    /**
     * to encrypt file using temp file and return the data into byte[]
     * and delete the temp file
     * @param file
     * @param file2
     * @return byte[] of encrypted file
     */
    public byte[] fileEncrypt(File file, File file2);

    /**
     * to decrypt array of bytes and create an output file
     * @param inputData
     * @param file2 the decrypted file.
     */
    public void fileDecrypt(byte[] inputData, File file2);

    /**
     * main test method
     * @param args
     */
    public static void main(String[] args);
}
