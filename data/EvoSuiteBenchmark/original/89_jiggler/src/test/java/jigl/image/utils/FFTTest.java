package jigl.image.utils;

import static org.junit.Assert.*;
import jigl.image.types.ComplexImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealGrayImage;
import jigl.util.ArrayUtil;

import org.junit.Test;

public class FFTTest {

	@Test
	public void testForward() {
		final int[][] signal = {{1, 1, 1, 1},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		final Float[][] realArrTrue = {{1f,0f,0f,0f}};
		final Float[][] imagArrTrue = {{0f,0f,0f,0f}};
		
		GrayImage img = new GrayImage(signal);
		ComplexImage fft = FFT.forward(img);
		
		RealGrayImage real = fft.real();
		Float[][] realArr = real.getData();
		assertTrue(ArrayUtil.arrayEquals(realArr, realArrTrue));
		
		RealGrayImage imaginary = fft.imaginary();
		Float[][] imaginaryArr = imaginary.getData();
		assertTrue(ArrayUtil.arrayEquals(imaginaryArr, imagArrTrue));
	}
	


//	@Test
//	public void testReverse() {
//		fail("Not yet implemented");
//	}

}
