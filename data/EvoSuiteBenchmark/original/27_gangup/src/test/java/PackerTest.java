import static util.Packer.*;

public class PackerTest {

    public static final void main(String[] argv) {

	String test;

	short[] a16 = {-1, 0, Short.MAX_VALUE, 4711, 1119, 666};
	short[] b16 = new short[6];
	int[] a32 = {-1, 0, Integer.MAX_VALUE, 4711, 1119, 666};
	int[] b32 = new int[6];
	long[] a64 = {-1L, 0L, Long.MAX_VALUE, 4711L, 1119L, 666L};
	long[] b64 = new long[6];

	byte b[] = new byte[48];

	System.err.print("Testing: unpack16(pack16(0x8080)): ");

	if (unpack16(pack16((short)0x8080)) == (short)0x8080) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack16(pack16(0xffff)): ");

	if (unpack16(pack16((short)0xffff)) == (short)0xffff) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack16v(pack16v(a16)): ");

	pack16v(a16,0, b, 0, 6);
	unpack16v(b, 0, b16, 0, 6);

	if (a16[0] == b16[0] && a16[1] == b16[1] && a16[2] == b16[2] &&
	    a16[3] == b16[3] && a16[4] == b16[4] && a16[5] == b16[5]) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack16v(pack16v(a16)) 2-5: ");

	b16[0]= b16[1] = b16[2] = b16[3] = b16[4] = b16[5] = 999;

	pack16v(a16, 1, b, 1, 4);
	unpack16v(b, 1, b16, 1, 4);

	if (a16[1] == b16[1] && a16[2] == b16[2] &&
	    a16[3] == b16[3] && a16[4] == b16[4]) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}


	System.err.print("Testing: unpack32(pack32(0x80808080)): ");

	if (unpack32(pack32((short)0x80808080)) == (short)0x80808080) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack32(pack32(0xffffffff)): ");

	if (unpack32(pack32(0xffffffff)) == 0xffffffff) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack32v(pack32v(a32)): ");

	pack32v(a32,0, b, 0, 6);
	unpack32v(b, 0, b32, 0, 6);

	if (a32[0] == b32[0] && a32[1] == b32[1] && a32[2] == b32[2] &&
	    a32[3] == b32[3] && a32[4] == b32[4] && a32[5] == b32[5]) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack32v(pack32v(a32)) 2-5: ");

	b32[0]= b32[1] = b32[2] = b32[3] = b32[4] = b32[5] = 999;

	pack32v(a32, 1, b, 1, 4);
	unpack32v(b, 1, b32, 1, 4);

	if (a32[1] == b32[1] && a32[2] == b32[2] &&
	    a32[3] == b32[3] && a32[4] == b32[4]) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack64(pack64(0x8080808080808080)): ");

	if (unpack64(pack64(0x8080808080808080L)) == 0x8080808080808080L) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack64(pack64(0xffffffffffffffff)): ");

	if (unpack64(pack64(0xffffffffffffffffL)) == 0xffffffffffffffffL) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack64v(pack64v(a64)): ");

	pack64v(a64,0, b, 0, 6);
	unpack64v(b, 0, b64, 0, 6);

	if (a64[0] == b64[0] && a64[1] == b64[1] && a64[2] == b64[2] &&
	    a64[3] == b64[3] && a64[4] == b64[4] && a64[5] == b64[5]) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpack64v(pack64v(a64)) 2-5: ");

	b64[0]= b64[1] = b64[2] = b64[3] = b64[4] = b64[5] = 999L;

	pack64v(a64, 1, b, 1, 4);
	unpack64v(b, 1, b64, 1, 4);

	if (a64[1] == b64[1] && a64[2] == b64[2] &&
	    a64[3] == b64[3] && a64[4] == b64[4]) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpackd(packd(Math.PI)): ");

	if (unpackd(packd(Math.PI)) == Math.PI) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpackf(packf((float)Math.PI)): ");

	if (unpackf(packf((float)Math.PI)) == (float)Math.PI) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}

	System.err.print("Testing: unpacks(packs(\"Hello, World!\"): ");

	if (unpacks(packs("Hello, World!")).equals("Hello, World!")) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}
	
	System.err.print("Testing: ISO-8859-1 ENCODING: ");

	test  = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
	test += "abcdefghijklmnopqrstuvwxyzåäö";
	test += "01234567890!\"#¤%&/()=?`¡@£$¥{[]}\\±";

	if (unpacks(packs(test)).equals(test)) {
	    System.err.println("OK");
	} else {
	    System.err.println("FAIL");
	}
    }
}
