/* $Id: Packer.java,v 1.3 2004/04/27 19:26:22 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package util;

/**
 * This class provides a set of functions for converting data types from
 * their normal representation to byte arrays.
 *
 */
public class Packer {

    /**
     * Packs n shorts of the short array v into the specified byte array b,
     * starting at position s and putting result starting at d.
     *
     * @param v source array.
     * @param s source offset.
     * @param d destination array.
     * @param d destination offset.
     * @param n number of values to convert.
     */
    public static void pack16v(short[] v, int s, byte[] b, int d, int n) {
	for (int i=0; i<n; i++,d+=2) { pack16(v[s+i], b, d); }
    }

    /**
     * Unpacks n shorts of the byte array b into the specified short array v,
     * starting at position s and putting result starting at d.
     *
     * @param b source array.
     * @param s source offset.
     * @param v destination array.
     * @param d destination offset.
     * @param n number of values to convert.
     */
    public static void unpack16v(byte[] b, int s, short v[], int d, int n) {
	for (int i=0; i<n; i++,s+=2) { v[d+i] = unpack16(b, s); }
    }

    public static void pack16(short x, byte[] b, int offset) {
	b[offset++] = (byte) ((x>> 8) & 0xff);
	b[offset++] = (byte) ((x    ) & 0xff);
    }

    public static short unpack16(byte[] b, int offset) {
	short y = 0;
	y |= ((short)b[offset++] & 0xff) << 8;
	y |= ((short)b[offset++] & 0xff);
	return y;
    }

    public static byte[] pack16(short x) {
	byte[] b = new byte[4];
	b[0] = (byte) ((x>> 8) & 0xff);
	b[1] = (byte) ((x    ) & 0xff);
	return b;
    }

    public static short unpack16(byte[] b) {
	short y = 0;
	y |= ((short)b[0] & 0xff) << 8;
	y |= ((short)b[1] & 0xff);
	return y;
    }

    /**
     * Packs n integers of the integer array v into the specified byte array b,
     * starting at position s and putting result starting at d.
     *
     * @param v source array.
     * @param s source offset.
     * @param d destination array.
     * @param d destination offset.
     * @param n number of values to convert.
     */
    public static void pack32v(int[] v, int s, byte[] b, int d, int n) {
	for (int i=0; i<n; i++,d+=4) { pack32(v[s+i], b, d); }
    }

    /**
     * Unpacks n integers of the byte array b into the specified integer 
     * array v, starting at position s and putting result starting at d.
     *
     * @param b source array.
     * @param s source offset.
     * @param v destination array.
     * @param d destination offset.
     * @param n number of values to convert.
     */
    public static void unpack32v(byte[] b, int s, int v[], int d, int n) {
	for (int i=0; i<n; i++,s+=4) { v[d+i] = unpack32(b, s); }
    }

    public static void pack32(int x, byte[] b, int offset) {
	b[offset++] = (byte) ((x>>24) & 0xff);
	b[offset++] = (byte) ((x>>16) & 0xff);
	b[offset++] = (byte) ((x>> 8) & 0xff);
	b[offset++] = (byte) ((x    ) & 0xff);
    }

    public static int unpack32(byte[] b, int offset) {
	int y = 0;
	y |= ((int)b[offset++] & 0xff) << 24;
	y |= ((int)b[offset++] & 0xff) << 16;
	y |= ((int)b[offset++] & 0xff) <<  8;
	y |= ((int)b[offset++] & 0xff);
	return y;
    }

    public static byte[] pack32(int x) {
	byte[] b = new byte[4];
	b[0] = (byte) ((x>>24) & 0xff);
	b[1] = (byte) ((x>>16) & 0xff);
	b[2] = (byte) ((x>> 8) & 0xff);
	b[3] = (byte) ((x    ) & 0xff);
	return b;
    }

    public static int unpack32(byte[] b) {
	int y = 0;
	y |= ((int)(b[0]) & 0xff) << 24;
	y |= ((int)(b[1]) & 0xff) << 16;
	y |= ((int)(b[2]) & 0xff) <<  8;
	y |= ((int)(b[3]) & 0xff);
	return y;
    }

    /**
     * Packs n longs in the long array v into the specified byte array b,
     * starting at position s and putting result starting at d.
     *
     * @param v source array.
     * @param s source offset.
     * @param d destination array.
     * @param d destination offset.
     * @param n number of values to convert.
     */
    public static void pack64v(long[] v, int s, byte[] b, int d, int n) {
	for (int i=0; i<n; i++,d+=8) { pack64(v[s+i], b, d); }
    }

    /**
     * Unpacks n longs of the byte array b into the specified long array v,
     * starting at position s and putting result starting at d.
     *
     * @param b source array.
     * @param s source offset.
     * @param v destination array.
     * @param d destination offset.
     * @param n number of values to convert.
     */
    public static void unpack64v(byte[] b, int s, long v[], int d, int n) {
	for (int i=0; i<n; i++,s+=8) { v[d+i] = unpack64(b, s); }
    }

    public static void pack64(long x, byte[] b, int offset) {
	b[offset++] = (byte) ((x>>56) & 0xff);
	b[offset++] = (byte) ((x>>48) & 0xff);
	b[offset++] = (byte) ((x>>40) & 0xff);
	b[offset++] = (byte) ((x>>32) & 0xff);
	b[offset++] = (byte) ((x>>24) & 0xff);
	b[offset++] = (byte) ((x>>16) & 0xff);
	b[offset++] = (byte) ((x>> 8) & 0xff);
	b[offset++] = (byte) ((x    ) & 0xff);
    }

    public static long unpack64(byte[] b, int offset) {
	long y = 0;
	y |= ((long)b[offset++] & 0xff) << 56;
	y |= ((long)b[offset++] & 0xff) << 48;
	y |= ((long)b[offset++] & 0xff) << 40;
	y |= ((long)b[offset++] & 0xff) << 32;
	y |= ((long)b[offset++] & 0xff) << 24;
	y |= ((long)b[offset++] & 0xff) << 16;
	y |= ((long)b[offset++] & 0xff) <<  8;
	y |= ((long)b[offset++] & 0xff);
	return y;
    }

    public static byte[] pack64(long x) {
	byte[] b = new byte[8];
	b[0] = (byte) ((x>>56) & 0xff);
	b[1] = (byte) ((x>>48) & 0xff);
	b[2] = (byte) ((x>>40) & 0xff);
	b[3] = (byte) ((x>>32) & 0xff);
	b[4] = (byte) ((x>>24) & 0xff);
	b[5] = (byte) ((x>>16) & 0xff);
	b[6] = (byte) ((x>> 8) & 0xff);
	b[7] = (byte) ((x    ) & 0xff);
	return b;
    }

    public static long unpack64(byte[] b) {
	long y = 0;
	y |= ((long)b[0] & 0xff) << 56;
	y |= ((long)b[1] & 0xff) << 48;
	y |= ((long)b[2] & 0xff) << 40;
	y |= ((long)b[3] & 0xff) << 32;
	y |= ((long)b[4] & 0xff) << 24;
	y |= ((long)b[5] & 0xff) << 16;
	y |= ((long)b[6] & 0xff) <<  8;
	y |= ((long)b[7] & 0xff);
	return y;
    }

    /**
     * Converts the specified float to four bytes.
     * @param f the float array that is to be packed as a bytes.
     */
    public static byte[] packf(float f) {
	return pack32(Float.floatToIntBits(f));
    }

    /**
     * Converts the specified float array to an array of bytes.
     * @param fv the float array that is to be packed as a byte array.
     * @param start the offset in fv from which to start packing.
     * @param count the number of floats to pack.
     */
    public static byte[] packfv(float[] fv, int start, int count) {
	byte[] buf = new byte[count << 4];
	count += start;
	for (int i=start,j=0; i<count; i++,j+=4) {
	    pack32(Float.floatToIntBits(fv[i]), buf, j);
	}
	return buf;
    }

    /**
     * Converts the specified byte vector to an float.
     * @param b the byte data that is to be unpacked as a float.
     */
    public static float unpackf(byte[] b) {
	return Float.intBitsToFloat(unpack32(b));
    }

    /**
     * Converts the specified integer to a byte vector.
     * @param i the integer that is to be packed.
     */
    public static byte[] packi(int i) {
	return pack32(i);
    }

    /**
     * Converts the specified byte vector to an integer.
     * @param b the byte data that is to be unpacked as an integer.
     */
    public static int unpacki(byte[] b) {
	return unpack32(b);
    }

    /**
     * Converts the specified long to a byte vector.
     * @param l the long that is to be packed.
     */
    public static byte[] packl(long l) {
	return pack64(l);
    }

    /**
     * Converts the specified byte vector to a long.
     * @param b the byte data that is to be unpacked as a long.
     */
    public static long unpackl(byte[] b) {
	return unpack64(b);
    }

    /**
     * Converts the specified double to a byte vector.
     * @param d the double that is to be packed.
     */
    public static byte[] packd(double d) {
	return pack64(Double.doubleToRawLongBits(d));
    }

    /**
     * Converts the specified byte vector to an double.
     * @param b the byte data that is to be unpacked as an double.
     */
    public static double unpackd(byte[] b) {
	return Double.longBitsToDouble(unpack64(b));
    }

    /**
     * Converts the specified string to a byte vector.
     * @param s the string that is to be packed.
     * @param b the byte vector to put result in.
     * @param offset the offset at which to put result.
     */
    public static void packs(String s, byte[] b, int offset) {
	byte[] p = packs(s);
	System.arraycopy(p, 0, b, offset, p.length);
    }

    /**
     * Converts the specified byte vector to a string.
     * @param b the byte vector containing the packed string.
     * @param offset the offset at which to start unpacking.
     * @param length the number of bytes to convert.
     */
    public static String unpacks(byte[] b, int offset, int length) {
	try {
	    return new String(b,offset,length,"UTF-8");
	} catch (java.io.UnsupportedEncodingException e) {
	    /* all jvm:s support UTF-8 */
	}
	return null;
    }

    /**
     * Converts the specified string to a byte vector.
     * @param s the string that is to be packed.
     */
    public static byte[] packs(String s) {
	try {
	    return s.getBytes("UTF-8");
	} catch (java.io.UnsupportedEncodingException e) {
	    /* all jvm:s support UTF-8 */
	}
	return null;
    }

    /**
     * Converts the specified byte vector to a String.
     * @param data the byte data that is to be unpacked as a String.
     */
    public static String unpacks(byte[] b) {
	try {
	    return new String(b,"UTF-8");
	} catch (java.io.UnsupportedEncodingException e) {
	    /* all jvm:s support UTF-8 */
	}
	return null;
    }
}
