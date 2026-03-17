package original;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


class StringToMd5 {
    /**
     * Given a string 'text', return its md5 hash equivalent string.
     * If 'text' is an empty string, return null.
     *
     * >>> stringToMd5('Hello world') == '3e25960a79dbc69b674cd4ec67a72c62'
     *
     * > stringToMd5("Hello world")
     * "3e25960a79dbc69b674cd4ec67a72c62"
     * > stringToMd5("")
     * null
     * > stringToMd5("A B C")
     * "0ef78513b0cb8cef12743f5aeb35f888"
     * > stringToMd5("password")
     * "5f4dcc3b5aa765d61d8327deb882cf99"
     */
    public static String stringToMd5(String text) {
        if (text.equals("")) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}