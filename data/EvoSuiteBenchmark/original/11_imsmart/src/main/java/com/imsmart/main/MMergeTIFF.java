package com.imsmart.main;

import com.imsmart.misc.MLog;
import com.imsmart.misc.MProperties;
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
import java.util.Date;

public class MMergeTIFF extends Thread {

    private static String TARGET_DIRECTORY;
    private static String TIFF_DIR;
    private static SortedSet filesToMergeSet;
    private static Iterator fileToMergeIterator;
    private static HashMap documentsMap;
    private static MProperties properties = MProperties.getInstance();
    private static MLog logger = MLog.getInstance();

    /**
     * @param args
     */
    public MMergeTIFF(String name) {
        super(name);
        TIFF_DIR = properties.getPropertyValue(MProperties.IMAGE_DIR);
        TARGET_DIRECTORY = properties.getPropertyValue(MProperties.MERGED_DIR);

    }

    public MMergeTIFF() {
        super();
        TIFF_DIR = properties.getPropertyValue(MProperties.IMAGE_DIR);
        TARGET_DIRECTORY = properties.getPropertyValue(MProperties.MERGED_DIR);
    }

    public static void startMerge() {
        MMergeTIFF test = new MMergeTIFF();
        logger.info("Merging Started: " + new Date());
        File dir = new File(TIFF_DIR);
        String fileNames[] = dir.list();
        MMergeTIFF.setDocuments(fileNames);
        
        Set keys = documentsMap.keySet();

        SortedSet set = new TreeSet(keys);
        MMergeTIFF.setFilesToMerge(set);
        MMergeTIFF.fileToMergeIterator = set.iterator();
        String strThreadCount = properties.getPropertyValue(MProperties.THREAD_COUNT);
        int threadCount = Integer.parseInt(strThreadCount);
        for (int i = 1; i <= threadCount; i++) {
            MMergeTIFF merge = new MMergeTIFF("Thread -" + i);
            merge.start();
        }

    }

    private synchronized String getNextFileToMerge() {
        if (fileToMergeIterator == null) {
            fileToMergeIterator = filesToMergeSet.iterator();
        }

        String fileName = (String) fileToMergeIterator.next();
        return fileName;

    }

    public void run() {
        logger.info("Starting new thread - " + getName());
        while (fileToMergeIterator.hasNext()) {
            String fileName = getNextFileToMerge();
            List pageList = (List) documentsMap.get(fileName);

            merge(fileName, pageList);
        }
        logger.info("End of thread - " + getName());
    }

    public static void setDocuments(String pages[]) {
        HashMap documentMap = new HashMap();
        for (int i = 0; i < pages.length; i++) {
            String pageName = pages[i];
            if (pageName.endsWith("tif") || pageName.endsWith("TIF")) {
                int index = pageName.indexOf(".");

                String multiPageFileName = pageName.substring(0, index);
                if (!documentMap.containsKey(multiPageFileName)) {
                    List pageList = new ArrayList();
                    pageList.add(pageName);
                    documentMap.put(multiPageFileName, pageList);
                } else {
                    List pageList = (List) documentMap.get(multiPageFileName);
                    pageList.add(pageName);
                    documentMap.put(multiPageFileName, pageList);
                }
            }
        }
        
        documentsMap = documentMap;
        //return documentMap;
    }

    private void merge(String fileName, List pageList) {
        SortedSet sortedList = new TreeSet(pageList);
        try {
            List imageList = new ArrayList();
            for (Iterator iterator = sortedList.iterator(); iterator.hasNext();) {
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
            for (int j = 0; j < imageList.size(); j++) {
                rImage[j] = (RenderedImage) imageList.get(j);
            }
            saveMultiPageTif(rImage, TARGET_DIRECTORY + fileName + ".tif");

        //logger.info("Merged " + rImage.length + " pages!!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void saveMultiPageTif(RenderedImage[] image, String file)
            throws java.io.IOException {
        String filename = file;
        if (!filename.endsWith(".tif")) {
            filename = new String(file + ".tif");
        }
        OutputStream out = new FileOutputStream(filename);
        TIFFEncodeParam param = new TIFFEncodeParam();
        // I tried different compression but on windows tif viewer I was not
        // able to see.
        param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
        ImageEncoder encoder = ImageCodec.createImageEncoder("TIFF", out, param);

        List list = new ArrayList();

        for (int i = 0; i < image.length; i++) {
            list.add(image[i]);
        }
        RenderedImage firstPage = (RenderedImage) list.get(0);
        list.remove(0);
        param.setExtraImages(list.iterator());
        encoder.encode(firstPage);

        out.close();
    }

    public static void setDocumentsMap(HashMap documents) {
        documentsMap = documents;
    }

    public static void setFilesToMerge(SortedSet set) {
        filesToMergeSet = set;
    }
}
