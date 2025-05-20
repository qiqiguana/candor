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
package net.kencochrane.a4j.DAO;

import com.wutka.jox.JOXBeanInputStream;
import net.kencochrane.a4j.beans.BlendedSearch;
import net.kencochrane.a4j.beans.ProductInfo;
import net.kencochrane.a4j.beans.SellerSearch;
import net.kencochrane.a4j.file.FileUtil;

import java.io.FileInputStream;

//import org.apache.log4j.Logger;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 23, 2003
 * Time: 2:49:48 PM
 *
 */
public class Search {
    //   Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param searchTerm
     * @param type
     * @return
     */
    public BlendedSearch Blended(String searchTerm, String type) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        BlendedSearch testBean = new BlendedSearch();
        try {
            FileInputStream fileIn = fileUtil.fetchBlendedSearchFile(searchTerm, type);

            if (fileIn != null) {
                joxIn = new JOXBeanInputStream(fileIn);
                testBean = (BlendedSearch) joxIn.readObject(BlendedSearch.class);
            } else {
                //           log.debug("Error no fileInput");
                testBean = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return testBean;
    }

    /**
     *
     * @param searchTerm
     * @param productLine
     * @param type
     * @param page
     * @return
     */
    public ProductInfo Keyword(String searchTerm, String productLine, String type, String page) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ProductInfo productInfo = new ProductInfo();
        try {
            FileInputStream fileIn = fileUtil.fetchKeywordSearchFile(searchTerm, productLine, type, page);

            if (fileIn != null) {
                joxIn = new JOXBeanInputStream(fileIn);
                productInfo = (ProductInfo) joxIn.readObject(ProductInfo.class);
            } else {
                //            log.debug("Error no fileInput");
                productInfo = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return productInfo;
    }

    /**
     *
     * @param searchType
     * @param searchTerm
     * @param mode
     * @param type
     * @param page
     * @param offer
     * @return
     */
    public ProductInfo Generic(String searchType, String searchTerm, String mode, String type, String page, String offer) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ProductInfo productInfo = new ProductInfo();

        try {
            FileInputStream fileIn = fileUtil.fetchGenericSearchFile(searchType, searchTerm, mode, type, page, offer);

            if (fileIn != null) {
                joxIn = new JOXBeanInputStream(fileIn);
                productInfo = (ProductInfo) joxIn.readObject(ProductInfo.class);
            } else {
                //              log.debug("Error no fileInput");
                productInfo = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


        return productInfo;
    }

    /**
     * Search for an actor
     * @param actorName name to search for
     * @param mode (dvd, vhs, video)
     * @param page
     * @return
     */
    public ProductInfo ActorSearch(String actorName, String mode, String page) {
        String searchType = "ActorSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, actorName, mode, type, page, offer);
    }

    /**
     * Search for an Artist
     * @param artistName name to search for
     * @param mode (music, classical)
     * @param page
     * @return
     */
    public ProductInfo ArtistSearch(String artistName, String mode, String page) {
        String searchType = "ArtistSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, artistName, mode, type, page, offer);
    }

    /**
     * Search for the author of books
     * @param authorName name of the author to search for
     * @param page
     * @return
     */
    public ProductInfo AuthorSearch(String authorName, String page) {
        String searchType = "AuthorSearch";
        String type = "lite";
        String offer = "all";
        String mode = "books";
        return Generic(searchType, authorName, mode, type, page, offer);
    }

    /**
     * Search for the director of movies
     * @param directorName
     * @param mode (dvd, vhs, video)
     * @param page
     * @return
     */
    public ProductInfo DirectorSearch(String directorName, String mode, String page) {
        String searchType = "DirectorSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, directorName, mode, type, page, offer);
    }

    /**
     *  Search by Manufacture
     * @param manufactureName
     * @param mode (electronics, kitchen, videogames, software, photo, pc-hardware)
     * @param page
     * @return
     */
    public ProductInfo ManufactureSearch(String manufactureName, String mode, String page) {
        String searchType = "ManufacturerSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, manufactureName, mode, type, page, offer);
    }

    /**
     * Search for cd's by UPC
     * @param upc
     * @param mode (music, classical)
     * @param page
     * @return
     */
    public ProductInfo UpcSearch(String upc, String mode, String page) {
        String searchType = "UpcSearch";
        String type = "lite";
        String offer = "all";
        return Generic(searchType, upc, mode, type, page, offer);
    }

    //listmania
    //TODO: test to make sure it works correctly. it is a little different then the generic search
    public ProductInfo ListmaniaSearch(String listId) {
        String searchType = "ListManiaSearch";
        String type = "lite";
        String offer = "all";
        String page = "1";
        String mode = "mode";
        return Generic(searchType, listId, mode, type, page, offer);
    }

    //WishList
   //todo never used need to confirm if works it is different then generic search
    public ProductInfo WishListSearch(String wishListId) {
        String searchType = "WishlistSearch";
        String type = "lite";
        String offer = "all";
        String mode = "mode";
        String page = "1";
        return Generic(searchType, wishListId, mode, type, page, offer);
    }

    //thirdpartysearch
    /**
     *
     * @param sellerId The sellers ID
     * @param type heavy or lite
     * @param page page number
     * @param status open or closed
     * @return
     */
    public SellerSearch ThirdParty(String sellerId, String type, String page, String status) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        SellerSearch sellerDetails = new SellerSearch();

        try {
            FileInputStream fileIn = fileUtil.fetchThirdPartySearchFile(sellerId, type, page, status);

            if (fileIn != null) {
                //         log.debug("file is good");
                joxIn = new JOXBeanInputStream(fileIn);
                sellerDetails = (SellerSearch) joxIn.readObject(SellerSearch.class);
            } else {
                //           log.debug("Error no fileInput");
                sellerDetails = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


        return sellerDetails;
    }


    //similarities
    public ProductInfo SimilaritesSearch(String asin, String page) {

        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ProductInfo productInfo = new ProductInfo();

        try {
            //       log.debug("in Similar Search");
            FileInputStream fileIn = fileUtil.fetchSimilarItems(asin, page);

            if (fileIn != null) {
                //        log.debug("Similar Search Filein not null");
                joxIn = new JOXBeanInputStream(fileIn);
                productInfo = (ProductInfo) joxIn.readObject(ProductInfo.class);
            } else {
                //           log.debug("Error no fileInput");
                productInfo = null;
            }
        } catch (Exception exc) {
            //       log.error(exc.toString());
            exc.printStackTrace();
        }

        //     log.debug("out similar search");
        return productInfo;
    }

    //todo sellerprofile

    //todo powersearch

    //todo MarketPlaceSearch

    //todo ExchangeSearch

    //todo add keywords to normal searches for more refined searching

    //todo add price ranges to searches to get within a low and high price level

    //todo add sorting to searches

    //todo create an advanced search that combines powersearch with keywords and price ranges and sorting.

}
