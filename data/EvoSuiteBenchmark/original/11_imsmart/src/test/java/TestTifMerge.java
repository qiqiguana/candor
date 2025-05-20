import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;

public class TestTifMerge
{

	private static final String TARGET_DIRECTORY = "C:/";

	private static final String TIFF_DIR = "C:/Documents and Settings/gowerdh/My Documents/Infocrossing/Migration/sample_data/Graphics/Auto_Claims";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TestTifMerge test = new TestTifMerge();
		test.start();
	}

	public void start()
	{
		try
		{
			File dir = new File(TIFF_DIR);
			String fileNames[] = dir.list();
			HashMap documentsMap = getDocuments(fileNames);

			Set keys = documentsMap.keySet();
			SortedSet set = new TreeSet(keys);

			for (Iterator iterator = set.iterator(); iterator.hasNext();)
			{
				String fileName = (String) iterator.next();
				List pageList = (List) documentsMap.get(fileName);

				merge(fileName, pageList);

			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private HashMap getDocuments(String pages[])
	{
		HashMap documentMap = new HashMap();
		for (int i = 0; i < pages.length; i++)
		{
			String pageName = pages[i];
			if (pageName.endsWith("tif") || pageName.endsWith("TIF"))
			{
				int index = pageName.indexOf(".");

				String multiPageFileName = pageName.substring(0, index);
				if (!documentMap.containsKey(multiPageFileName))
				{
					List pageList = new ArrayList();
					pageList.add(pageName);
					documentMap.put(multiPageFileName, pageList);
				}
				else
				{
					List pageList = (List) documentMap.get(multiPageFileName);
					pageList.add(pageName);
					documentMap.put(multiPageFileName, pageList);
				}
			}
		}
		return documentMap;
	}

	private void merge(String fileName, List pageList)
	{
		SortedSet sortedList = new TreeSet(pageList);
		System.out.println("Merging Pages to: " + fileName + ".tif");
		try
		{
			List imageList = new ArrayList();
			for (Iterator iterator = sortedList.iterator(); iterator.hasNext();)
			{
				String name = (String) iterator.next();
				// String name = (String) pageList.get(i);
				File file = new File(TIFF_DIR + "/" + name);

				FileSeekableStream ss = new FileSeekableStream(file);
				ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss,
						null);
				TIFFEncodeParam param = new TIFFEncodeParam();
				param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
				param.setLittleEndian(false); // Intel
				RenderedImage singlePageTif = dec.decodeAsRenderedImage(0);
				imageList.add(singlePageTif);

			}

			RenderedImage rImage[] = new RenderedImage[imageList.size()];
			for (int j = 0; j < imageList.size(); j++)
			{
				rImage[j] = (RenderedImage) imageList.get(j);
			}
			saveMultiPageTif(rImage, TARGET_DIRECTORY + fileName + ".tif");

			System.out.println("Merged " + rImage.length + " pages!!");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	public void saveMultiPageTif(RenderedImage[] image, String file)
			throws java.io.IOException
	{
		String filename = file;

		if (!filename.endsWith(".tif"))
			filename = new String(file + ".tif");
		OutputStream out = new FileOutputStream(filename);
		TIFFEncodeParam param = new TIFFEncodeParam();
		// I tried different compression but on windows tif viewer I was not
		// able to see.
		// param.setCompression(TIFFEncodeParam.COMPRESSION_NONE);
		ImageEncoder encoder = ImageCodec
				.createImageEncoder("TIFF", out, param);

		List list = new ArrayList();

		for (int i = 0; i < image.length; i++)
		{
			list.add(image[i]);
		}
		RenderedImage firstPage = (RenderedImage) list.get(0);
		list.remove(0);
		param.setExtraImages(list.iterator());
		encoder.encode(firstPage);

		out.close();
	}

}
