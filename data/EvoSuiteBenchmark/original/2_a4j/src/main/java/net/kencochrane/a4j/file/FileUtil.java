/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.file;

import net.kencochrane.a4j.data.Query;
import net.kencochrane.a4j.util.LoadProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

//import org.apache.log4j.Logger;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 23, 2003
 * Time: 9:30:23 AM
 *
 *
 */
public class FileUtil {
    //  Logger log = Logger.getLogger(this.getClass());
    protected String cacheDir;
    protected long oldestAge;

    public FileUtil() {
        Properties props = LoadProperties.instance().getProperties();
        this.cacheDir = props.getProperty("cacheDir");
        try {
            this.oldestAge = Long.parseLong(props.getProperty("cacheLife"));
        } catch (Exception e) {
            this.oldestAge = 86400000;
        }
    }

    //download file

    public boolean downloadOneASINFile(String asin, String type, String offer, String page, String saveFileName) {
        //     log.debug("download");
        //     log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        ArrayList asins = new ArrayList();
        Query xml = new Query();
        String searchType = "AsinSearch";
        //String offer = "all";
        String response = new String();
        asins.add(asin);
        try {
            //        log.debug("download - try");
            response = xml.sendRequest(xml.queryGenerator(searchType, type, page, offer, asins));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //       log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //      log.error(e.toString());
            downloaded = false;
        }

        return downloaded;
    }

    //delete file
    public void deleteFile(String fileName) {
        //      log.debug("In delete");
        boolean deleted;
        File file = new File(fileName);
        if (file != null) {
            if (file.exists()) {
                deleted = file.delete();
                //            log.debug("deleted? " + deleted);
                //             log.debug("delete file");
            }
        }
        //     log.debug("out of delete");
    }

    //check file age
    public boolean isAgeGood(File file) {
        //      log.debug("is good - in");
        Date now = new Date();
        if (file != null) {
            long fileAge = file.lastModified();
            long timeNow = now.getTime();
            long timeDiff = timeNow - fileAge;

            if (file.length() < 1000) {
                //        log.debug("File is Bad");
                return false;
            }

            if (timeDiff < oldestAge) {
                //        log.debug("Good");
                return true;
            } else {
                //         log.debug("bad");
                return false;
            }
        } else {
            //         log.debug("bad file is null");
            return false;
        }
    }

    //rename file
    public void renameFile(String oldFileName, String newFileName) {
        boolean renamed;
        //      log.debug("rename - in");
        File file = new File(oldFileName);
        File newFile = new File(newFileName);
        try {
            if (file != null) {
                //          log.debug("file isn't null");
                if (file.exists()) {
                    //             log.debug("file exists rename it ");
                    renamed = file.renameTo(newFile);
                    //              log.debug("renamed? = " + renamed);
                }
            }
        } catch (Exception e) {
            //        log.error(e.toString());
        }
        //      log.debug("rename - out");
    }

    //cleanup old files older then a week

    //get file

    public File getASINFile(String asin, String type, String offer, String page) {
        //     log.debug("In getASINFile");
        String filename = asin + "_" + offer + "_" + type + "_" + page + ".xml";
        String tFileName = "t_" + asin + "_" + offer + "_" + type + "_" + page + ".xml";

        String cachedFileName = cacheDir + filename.trim().toUpperCase();
        String tempFilename = cacheDir + tFileName.trim().toUpperCase();
        // have all files uppercase so that there is no diffs
        File cachedFile = new File(cachedFileName);

        // check if cachedFile is there.
        if (cachedFile != null) {
            // check if cachedFile is there.
            if (cachedFile.exists()) {
                if (isAgeGood(cachedFile)) { // check the age of the cachedFile
                    return cachedFile; // if age is ok return that cachedFile
                } else {
                    //              log.debug("else");
                    // if age is too old get a new cachedFile
                    if (downloadOneASINFile(asin, type, offer, page, tempFilename)) {
                        // if getting a new File was successful then delete old one
                        deleteFile(cachedFileName);
                        //and rename the temp to the asin.xml cachedFile
                        renameFile(tempFilename, cachedFileName);
                        return new File(cachedFileName); //cachefile is now the new file
                    } else {
                        // if getting a new File failed return the old one
                        // and delete the temp if it exists
                        deleteFile(tempFilename);
                        return cachedFile;
                    }
                }

            } else {
                //cachedFile not there
                // if the cachedFile wasn't there get it
                if (downloadOneASINFile(asin, type, offer, page, tempFilename)) {
                    // if getting the File was successful return it
                    //rename temp to asin.xml
                    renameFile(tempFilename, cachedFileName);
                    return new File(cachedFileName);
                } else {
                    // if getting the File failed then return an error or something letting the user know there was a problem
                    return null;
                }

            }
        } else   //cachedFile not there
        {
            // if the cachedFile wasn't there get it
            if (downloadOneASINFile(asin, type, offer, page, tempFilename)) {
                // if getting the File was successful return it
                //rename temp to asin.xml
                renameFile(tempFilename, cachedFileName);
                return new File(cachedFileName);
            } else {
                // if getting the File failed then return an error or something letting the user know there was a problem
                return null;
            }
        }

    }

    public FileInputStream fetchASINFile(String asin, String type, String offer, String page) {
        try {
            //       log.debug("In FetchFile");
            File asinFile = getASINFile(asin, type, offer, page);
            if (asinFile != null) {
                FileInputStream in = new FileInputStream(asinFile);
                //             log.debug("got File");
                return in;
            } else {
                return null;
            }

        } catch (FileNotFoundException fnfe) {
            //           log.error("error in fetchfile");
            return null;
        }
    }

    public boolean downloadBrowseNodeFile(String mode, String node, String page, String saveFileName) {
        //      log.debug("download");
        //      log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String type = "lite";
        String offer = "new";
        String response = new String();
        try {
//            log.debug("download - try");
            response = xml.sendRequest(xml.browseNodeQueryGenerator(type, page, offer, mode, node));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //            log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }

        } catch (Exception e) {
            //       log.error(e.toString());
            downloaded = false;
        }

        return downloaded;
    }

    public File getBrowseNodeFile(String mode, String node, String page) {
        //      log.debug("In getBrowseNodeFile");
        String filename = mode + "_" + node + "_" + page + ".xml";
        String tFileName = "t_" + mode + "_" + node + "_" + page + ".xml";

        String cachedFileName = cacheDir + filename.trim().toUpperCase();
        String tempFilename = cacheDir + tFileName.trim().toUpperCase();
        // have all files uppercase so that there is no diffs
        File cachedFile = new File(cachedFileName);

        // check if cachedFile is there.
        if (cachedFile != null) {
            // check if cachedFile is there.
            if (cachedFile.exists()) {
                if (isAgeGood(cachedFile)) { // check the age of the cachedFile
                    return cachedFile; // if age is ok return that cachedFile
                } else {
                    //              log.debug("else");
                    // if age is too old get a new cachedFile
                    if (downloadBrowseNodeFile(mode, node, page, tempFilename)) {
                        // if getting a new File was successful then delete old one
                        deleteFile(cachedFileName);
                        //and rename the temp to the asin.xml cachedFile
                        renameFile(tempFilename, cachedFileName);
                        return new File(cachedFileName); //cachefile is now the new file
                    } else {
                        // if getting a new File failed return the old one
                        // and delete the temp if it exists
                        deleteFile(tempFilename);
                        return cachedFile;
                    }
                }

            } else {
                //cachedFile not there
                // if the cachedFile wasn't there get it
                if (downloadBrowseNodeFile(mode, node, page, tempFilename)) {
                    // if getting the File was successful return it
                    //rename temp to asin.xml
                    renameFile(tempFilename, cachedFileName);
                    return new File(cachedFileName);
                } else {
                    // if getting the File failed then return an error or something letting the user know there was a problem
                    return null;
                }

            }
        } else   //cachedFile not there
        {
            // if the cachedFile wasn't there get it
            if (downloadBrowseNodeFile(mode, node, page, tempFilename)) {
                // if getting the File was successful return it
                //rename temp to asin.xml
                renameFile(tempFilename, cachedFileName);
                return new File(cachedFileName);
            } else {
                // if getting the File failed then return an error or something letting the user know there was a problem
                return null;
            }
        }

    }

    public FileInputStream fetchBNFile(String mode, String node, String page) {
        try {
//          log.debug("In FetchBNFile");
            File file = getBrowseNodeFile(mode, node, page);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
//         log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
//        log.error("error in fetchfile");
            return null;
        }
    }

    public File downloadBlendedSearchFile(String searchTerm, String type) {
        //     log.debug("download");

        Date timestamp = new Date();
        Random r = new Random();
        String fileName = "b_" + Long.toString(timestamp.getTime()) + "_" + r.nextInt() + ".xml";
        String saveFileName = cacheDir + fileName.trim().toUpperCase();
        //      log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String response = new String();
        try {
            //          log.debug("download - try");
            response = xml.sendRequest(xml.BlendedSearchGenerator(type, searchTerm));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //           log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //      log.error(e.toString());
            downloaded = false;
        }

        if (downloaded) {
            return new File(saveFileName);
        } else {
            return null;
        }
    }

    public File downloadKeywordSearchFile(String searchTerm, String productLine, String type, String page) {
        //      log.debug("download");

        Date timestamp = new Date();
        Random r = new Random();
        String fileName = "k_" + Long.toString(timestamp.getTime()) + "_" + r.nextInt() + ".xml";
        String saveFileName = cacheDir + fileName.trim().toUpperCase();
        //     log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String response = new String();
        try {
            //          log.debug("download - try");
            response = xml.sendRequest(xml.KeywordSearchGenerator(searchTerm, productLine, type, page));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //           log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //      log.error(e.toString());
            downloaded = false;
        }

        if (downloaded) {
            return new File(saveFileName);
        } else {
            return null;
        }
    }

    public FileInputStream fetchBlendedSearchFile(String searchTerm, String type) {
        try {
            //        log.debug("In FetchBlendedSearchFile");
            File file = downloadBlendedSearchFile(searchTerm, type);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
                //        log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            //        log.error("error in fetchfile");
            return null;
        }
    }

    public FileInputStream fetchKeywordSearchFile(String searchTerm, String productLine, String type, String page) {
        try {
            //         log.debug("In FetchKeywordSearchFile");
            File file = downloadKeywordSearchFile(searchTerm, productLine, type, page);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
                //         log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            //          log.error("error in fetchfile");
            return null;
        }
    }

    public File downloadGenericSearchFile(String searchType, String searchTerm, String mode, String type, String page, String offer) {
        //      log.debug("download");

        Date timestamp = new Date();
        Random r = new Random();
        String fileName = "g_" + Long.toString(timestamp.getTime()) + "_" + r.nextInt() + ".xml";
        String saveFileName = cacheDir + fileName.trim().toUpperCase();
        //       log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String response = new String();
        try {
            //         log.debug("download - try");
            response = xml.sendRequest(xml.SearchQueryGenerator(searchType, searchTerm, mode, type, page, offer));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //           log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //      log.error(e.toString());
            downloaded = false;
        }

        if (downloaded) {
            return new File(saveFileName);
        } else {
            return null;
        }
    }

    public FileInputStream fetchGenericSearchFile(String searchType, String searchTerm, String mode, String type, String page, String offer) {
        try {
            //        log.debug("In fetchGenericSearchFile");
            File file = downloadGenericSearchFile(searchType, searchTerm, mode, type, page, offer);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
                //        log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            //         log.error("error in fetchfile");
            return null;
        }
    }

    public File downloadThirdPartySearchFile(String sellerId, String type, String page, String status) {
        //      log.debug("download");

        Date timestamp = new Date();
        Random r = new Random();
        String fileName = "3rd_" + Long.toString(timestamp.getTime()) + "_" + r.nextInt() + ".xml";
        String saveFileName = cacheDir + fileName.trim().toUpperCase();
        //     log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String response = new String();
        try {
            //           log.debug("download - try");
            response = xml.sendRequest(xml.SearchThirdPartyGenerator(sellerId, type, page, status));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //           log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //      log.error(e.toString());
            downloaded = false;
        }

        if (downloaded) {
            return new File(saveFileName);
        } else {
            return null;
        }
    }

    public FileInputStream fetchThirdPartySearchFile(String sellerId, String type, String page, String status) {
        try {
            //         log.debug("In fetchThirdPartySearchFile");
            File file = downloadThirdPartySearchFile(sellerId, type, page, status);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
                //        log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            //         log.error("error in fetchfile");
            return null;
        }
    }


    public File getAccessories(String asin, ArrayList asins) {
        //      log.debug("In getAccessories");
        String filename = "a_" + asin + ".xml";
        String tFileName = "ta_" + asin + ".xml";

        String cachedFileName = cacheDir + filename.trim().toUpperCase();
        String tempFilename = cacheDir + tFileName.trim().toUpperCase();
        // have all files uppercase so that there is no diffs
        File cachedFile = new File(cachedFileName);

        // check if cachedFile is there.
        if (cachedFile != null) {
            // check if cachedFile is there.
            if (cachedFile.exists()) {
                if (isAgeGood(cachedFile)) { // check the age of the cachedFile
                    return cachedFile; // if age is ok return that cachedFile
                } else {
                    //                log.debug("else");
                    // if age is too old get a new cachedFile
                    if (downloadAccessoriesFile(asin, asins, tempFilename)) {
                        // if getting a new File was successful then delete old one
                        deleteFile(cachedFileName);
                        //and rename the temp to the asin.xml cachedFile
                        renameFile(tempFilename, cachedFileName);
                        return new File(cachedFileName); //cachefile is now the new file
                    } else {
                        // if getting a new File failed return the old one
                        // and delete the temp if it exists
                        deleteFile(tempFilename);
                        return cachedFile;
                    }
                }

            } else {
                //cachedFile not there
                // if the cachedFile wasn't there get it
                if (downloadAccessoriesFile(asin, asins, tempFilename)) {
                    // if getting the File was successful return it
                    //rename temp to asin.xml
                    renameFile(tempFilename, cachedFileName);
                    return new File(cachedFileName);
                } else {
                    // if getting the File failed then return an error or something letting the user know there was a problem
                    return null;
                }

            }
        } else   //cachedFile not there
        {
            // if the cachedFile wasn't there get it
            if (downloadAccessoriesFile(asin, asins, tempFilename)) {
                // if getting the File was successful return it
                //rename temp to asin.xml
                renameFile(tempFilename, cachedFileName);
                return new File(cachedFileName);
            } else {
                // if getting the File failed then return an error or something letting the user know there was a problem
                return null;
            }
        }

    }

    public boolean downloadAccessoriesFile(String asin, ArrayList asins, String saveFileName) {
        //      log.debug("download");
        //      log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String searchType = "AsinSearch";
        String page = "1";
        String offer = "all";
        String response = new String();
        try {
            //          log.debug("download - try");
            response = xml.sendRequest(xml.queryGenerator(searchType, "lite", page, offer, asins));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //           log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //      log.error(e.toString());
            downloaded = false;
        }

        return downloaded;
    }

    public FileInputStream fetchAccessories(String asin, ArrayList asins) {
        try {
            //          log.debug("In fetchAccessories");
            File file = getAccessories(asin, asins);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
                //        log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            //         log.error("error in fetchfile");
            return null;
        }
    }

    public File getSimilarItems(String asin, String page) {
        //     log.debug("In getSimilarItems");
        String filename = "s_" + asin + ".xml";
        String tFileName = "ts_" + asin + ".xml";

        String cachedFileName = cacheDir + filename.trim().toUpperCase();
        String tempFilename = cacheDir + tFileName.trim().toUpperCase();
        // have all files uppercase so that there is no diffs
        File cachedFile = new File(cachedFileName);

        // check if cachedFile is there.
        if (cachedFile != null) {
            // check if cachedFile is there.
            if (cachedFile.exists()) {
                if (isAgeGood(cachedFile)) { // check the age of the cachedFile
                    return cachedFile; // if age is ok return that cachedFile
                } else {
                    //               log.debug("else");
                    // if age is too old get a new cachedFile
                    if (downloadSimilaritesFile(asin, page, tempFilename)) {
                        // if getting a new File was successful then delete old one
                        deleteFile(cachedFileName);
                        //and rename the temp to the asin.xml cachedFile
                        renameFile(tempFilename, cachedFileName);
                        return new File(cachedFileName); //cachefile is now the new file
                    } else {
                        // if getting a new File failed return the old one
                        // and delete the temp if it exists
                        deleteFile(tempFilename);
                        return cachedFile;
                    }
                }

            } else {
                //cachedFile not there
                // if the cachedFile wasn't there get it
                if (downloadSimilaritesFile(asin, page, tempFilename)) {
                    // if getting the File was successful return it
                    //rename temp to asin.xml
                    renameFile(tempFilename, cachedFileName);
                    return new File(cachedFileName);
                } else {
                    // if getting the File failed then return an error or something letting the user know there was a problem
                    return null;
                }

            }
        } else   //cachedFile not there
        {
            // if the cachedFile wasn't there get it
            if (downloadSimilaritesFile(asin, page, tempFilename)) {
                // if getting the File was successful return it
                //rename temp to asin.xml
                renameFile(tempFilename, cachedFileName);
                return new File(cachedFileName);
            } else {
                // if getting the File failed then return an error or something letting the user know there was a problem
                return null;
            }
        }

    }

    public boolean downloadSimilaritesFile(String asin, String page, String saveFileName) {
        //       log.debug("download");
        //       log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        ArrayList asins = new ArrayList();
        String searchType = "SimilaritySearch";
        String offer = "all";
        String response = new String();
        asins.add(asin);
        try {
            //           log.debug("download - try");
            response = xml.sendRequest(xml.queryGenerator(searchType, "lite", page, offer, asins));
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
                //          log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
            //       log.error(e.toString());
            downloaded = false;
        }

        return downloaded;
    }

    public FileInputStream fetchSimilarItems(String asin, String page) {
        try {
            //        log.debug("fetchSimilarItems");
            File file = getSimilarItems(asin, page);
            if (file != null) {
                FileInputStream in = new FileInputStream(file);
                //          log.debug("got File");
                return in;
            } else {
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            //         log.error("error in fetchfile");
            return null;
        }
    }

    public File downloadCart(String cartQuery) {
//       log.debug("download");

        Date timestamp = new Date();
        Random r = new Random();
        String fileName = "c_" + Long.toString(timestamp.getTime()) + "_" + r.nextInt() + ".xml";
        String saveFileName = cacheDir + fileName.trim().toUpperCase();
//      log.debug("saveFilename = " + saveFileName);
        boolean downloaded;
        Query xml = new Query();
        String response = new String();
        try {
            //         log.debug("download - try");
            response = xml.sendRequest(cartQuery);
            FileOutputStream out = new FileOutputStream(saveFileName);
            byte[] byteMe = response.getBytes();
            out.write(byteMe);
            out.close();
            File file = new File(saveFileName);
            if (file != null && file.length() < 1000) {
//            log.debug("FileSize = " + file.length());
                downloaded = false;
            } else {
                downloaded = true;
            }
        } catch (Exception e) {
//       log.error(e.toString());
            downloaded = false;
        }

        if (downloaded) {
            return new File(saveFileName);
        } else {
            return null;
        }
    }
}
