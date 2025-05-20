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

    public static FileEncrypter getInstance() {
        if (fileEncrypter == null)
            fileEncrypter = new FileEncrypter("FIM".getBytes());
        return fileEncrypter;
    }
}
