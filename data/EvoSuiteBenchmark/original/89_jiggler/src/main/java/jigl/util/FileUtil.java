package jigl.util;

import java.io.IOException;

import jigl.image.Image;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.io.IllegalPBMFormatException;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;
import jigl.image.types.GrayImage;

public final class FileUtil {
	private FileUtil() {}
	
	public static Image<?> load(final String filename) {
		try {
			ImageInputStream iis = new ImageInputStream(filename);
			return iis.read();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ImageNotSupportedException e) {
			e.printStackTrace();
		} catch (IllegalPBMFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void save(final GrayImage image, final String filename) {
		try {
			ImageOutputStream ios = new ImageOutputStream(filename);
			ios.write(image);
			ios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
