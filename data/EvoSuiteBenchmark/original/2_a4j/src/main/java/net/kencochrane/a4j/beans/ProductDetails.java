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
package net.kencochrane.a4j.beans;

//import org.apache.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 14, 2003
 * Time: 5:30:07 PM
 *
 *
 */
public class ProductDetails implements Serializable {
    //   Logger log = Logger.getLogger(this.getClass());

    protected String asin,productName,catalog,releaseDate,manufacturer,imageUrlSmall,imageUrlMedium,imageUrlLarge;
    protected String media,isbn,availability,mpn,theatricalReleaseDate,distributor,upc,encoding,status;
    protected String readingLevel,mpaaRating,esrbRating,ageGroup;

    //int's that are strings
    protected String listPrice,ourPrice,usedPrice,thirdPartyNewPrice,salesRank,numberOfItems,refurbishedPrice,collectiblePrice;
    protected String numberOfOfferings, thirdPartyNewCount, usedCount, collectibleCount, refurbishedCount;
    //
    protected String url;

    protected Tracks tracks = new Tracks();
    protected Lists lists = new Lists();
    protected Artists artists = new Artists();
    protected Features features = new Features();
    protected SimilarProducts similarProducts = new SimilarProducts();  //list of ASIN's
    protected Reviews reviews = new Reviews();
    protected BrowseList browseList = new BrowseList();
    protected Accessories accessories = new Accessories();  //list of ASIN's
    protected Directors directors = new Directors();
    protected Starring starring = new Starring();
    protected Authors authors = new Authors();
    protected Platforms platforms = new Platforms();
    protected ThirdPartyProductInfo thirdPartyProductInfo = new ThirdPartyProductInfo();

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImageUrlSmall() {
        return imageUrlSmall;
    }

    public void setImageUrlSmall(String imageUrlSmall) {
        this.imageUrlSmall = imageUrlSmall;
    }

    public String getImageUrlMedium() {
        return imageUrlMedium;
    }

    public void setImageUrlMedium(String imageUrlMedium) {
        this.imageUrlMedium = imageUrlMedium;
    }

    public String getImageUrlLarge() {
        return imageUrlLarge;
    }

    public void setImageUrlLarge(String imageUrlLarge) {
        this.imageUrlLarge = imageUrlLarge;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getOurPrice() {
        return ourPrice;
    }

    public void setOurPrice(String ourPrice) {
        this.ourPrice = ourPrice;
    }

    public String getUsedPrice() {
        return usedPrice;
    }

    public void setUsedPrice(String usedPrice) {
        this.usedPrice = usedPrice;
    }

    public String getThirdPartyNewPrice() {
        return thirdPartyNewPrice;
    }

    public void setThirdPartyNewPrice(String thirdPartyNewPrice) {
        this.thirdPartyNewPrice = thirdPartyNewPrice;
    }

    public String getSalesRank() {
        return salesRank;
    }

    public void setSalesRank(String salesRank) {
        this.salesRank = salesRank;
    }

    public String getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(String numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getTheatricalReleaseDate() {
        return theatricalReleaseDate;
    }

    public void setTheatricalReleaseDate(String theatricalReleaseDate) {
        this.theatricalReleaseDate = theatricalReleaseDate;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(String readingLevel) {
        this.readingLevel = readingLevel;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getRefurbishedPrice() {
        return refurbishedPrice;
    }

    public void setRefurbishedPrice(String refurbishedPrice) {
        this.refurbishedPrice = refurbishedPrice;
    }

    public String getCollectiblePrice() {
        return collectiblePrice;
    }

    public void setCollectiblePrice(String collectiblePrice) {
        this.collectiblePrice = collectiblePrice;
    }

    public String getNumberOfOfferings() {
        return numberOfOfferings;
    }

    public void setNumberOfOfferings(String numberOfOfferings) {
        this.numberOfOfferings = numberOfOfferings;
    }

    public String getThirdPartyNewCount() {
        return thirdPartyNewCount;
    }

    public void setThirdPartyNewCount(String thirdPartyNewCount) {
        this.thirdPartyNewCount = thirdPartyNewCount;
    }

    public String getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(String usedCount) {
        this.usedCount = usedCount;
    }

    public String getCollectibleCount() {
        return collectibleCount;
    }

    public void setCollectibleCount(String collectibleCount) {
        this.collectibleCount = collectibleCount;
    }

    public String getRefurbishedCount() {
        return refurbishedCount;
    }

    public void setRefurbishedCount(String refurbishedCount) {
        this.refurbishedCount = refurbishedCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public Lists getLists() {
        return lists;
    }

    public void setLists(Lists lists) {
        this.lists = lists;
    }

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public SimilarProducts getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(SimilarProducts similarProducts) {
        this.similarProducts = similarProducts;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public BrowseList getBrowseList() {
        return browseList;
    }

    public void setBrowseList(BrowseList browseList) {
        this.browseList = browseList;
    }

    public Accessories getAccessories() {
        return accessories;
    }

    public void setAccessories(Accessories accessories) {
        this.accessories = accessories;
    }

    public Directors getDirectors() {
        return directors;
    }

    public void setDirectors(Directors directors) {
        this.directors = directors;
    }

    public Starring getStarring() {
        return starring;
    }

    public void setStarring(Starring starring) {
        this.starring = starring;
    }

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public Platforms getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Platforms platforms) {
        this.platforms = platforms;
    }

    public ThirdPartyProductInfo getThirdPartyProductInfo() {
        return thirdPartyProductInfo;
    }

    public void setThirdPartyProductInfo(ThirdPartyProductInfo thirdPartyProductInfo) {
        this.thirdPartyProductInfo = thirdPartyProductInfo;
    }

    public String getSavings() {
        String out;
        int decimalPlace = 2;
        BigDecimal bd = null;
        String search = "$";
        String replace = " ";
        double list,our,savings,percent;
        String myListPrice,myOurPrice;
        if (this.listPrice != null && this.ourPrice != null) {
            try {
                char searchChar = search.charAt(0);
                char replaceChar = replace.charAt(0);
                myListPrice = this.listPrice.replace(searchChar, replaceChar);
                myOurPrice = this.ourPrice.replace(searchChar, replaceChar);
                list = Double.parseDouble(myListPrice);
                our = Double.parseDouble(myOurPrice);
                savings = list - our;
                bd = new BigDecimal(savings);
                bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
                savings = bd.doubleValue();
                DecimalFormat formatter = new DecimalFormat("#.00");
                percent = (savings / list) * 100;
                bd = new BigDecimal(percent);
                bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
                percent = bd.doubleValue();
                out = " (You save $" + formatter.format(savings) + " that's " + percent + "% off the list price!)";
                return out;
            } catch (Exception e) {
                //     log.info("EXCEPTION  = " + e.toString());
                return null;
            }
        } else {
            return null;
        }
    }

    public String getRatingsImgURL() {
        String url = new String();
        String aveRating;
        int rating;
        if (this.reviews != null && this.reviews.getAvgCustomerRating() != null) {
            try {
                aveRating = this.reviews.getAvgCustomerRating();
                // log.debug("AveRating = " + aveRating);
                rating = (int) Double.parseDouble(aveRating);
                //  log.debug("rating = " + rating);
                if (rating > 0 && rating < 6) {
                    url = "/images/stars-" + rating + ".gif";
                    return url;
                } else {
                    return null;
                }
            } catch (Exception e) {
                //       log.info("Error = " + e.toString());
                return null;
            }
        } else {
            return null;
        }
    }

    public String getRecommendation() {
        String aveRating;
        String recommend;
        int rating;
        if (this.reviews != null && this.reviews.getAvgCustomerRating() != null) {
            try {
                String percent = null;
                aveRating = this.reviews.getAvgCustomerRating();
                //    log.debug("AveRating = " + aveRating);
                rating = (int) Double.parseDouble(aveRating);
                //    log.debug("rating = " + rating);
                if (rating > 0 && rating < 6) {
                    if (rating == 1) {
                        percent = "20%";
                    } else if (rating == 2) {
                        percent = "40%";
                    } else if (rating == 3) {
                        percent = "60%";
                    } else if (rating == 4) {
                        percent = "80%";
                    } else if (rating == 5) {
                        percent = "100%";
                    }
                    if (percent != null) {
                        recommend = percent + " of our Customers recommend this Product!";
                        return recommend;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                //      log.info("Error = " + e.toString());
                return null;
            }
        } else {
            return null;
        }
    }


    public String toString() {
        StringBuffer output = new StringBuffer();

        output.append("ASIN - " + getAsin() + "\n");
        output.append("ProductAction Name - " + getProductName() + "\n");
        output.append("URL - " + getUrl() + "\n");
        output.append("Age Group - " + getAgeGroup() + "\n");
        output.append("Availability - " + getAvailability() + "\n");
        output.append("Catalog - " + getCatalog() + "\n");
        output.append("Collectibles Count - " + getCollectibleCount() + "\n");
        output.append("Collectibles Price - " + getCollectiblePrice() + "\n");
        output.append("Distributor - " + getDistributor() + "\n");
        output.append("Encoding - " + getEncoding() + "\n");
        output.append("ESRB Rating- " + getEsrbRating() + "\n");
        output.append("LargeImage - " + getImageUrlLarge() + "\n");
        output.append("Medium Image - " + getImageUrlMedium() + "\n");
        output.append("Small Image - " + getImageUrlSmall() + "\n");
        output.append("ISBN - " + getIsbn() + "\n");
        output.append("List Price - " + getListPrice() + "\n");
        output.append("Manufactor - " + getManufacturer() + "\n");
        output.append("Media - " + getMedia() + "\n");
        output.append("MPAA Rating - " + getMpaaRating() + "\n");
        output.append("MPN - " + getMpn() + "\n");
        output.append("# of items - " + getNumberOfItems() + "\n");
        output.append("# of offerings - " + getNumberOfOfferings() + "\n");
        output.append("Our Price - " + getOurPrice() + "\n");
        output.append("Reading Level - " + getReadingLevel() + "\n");
        output.append("Refurbished Count - " + getRefurbishedCount() + "\n");
        output.append("Refurbised Price - " + getRefurbishedPrice() + "\n");
        output.append("Released Date - " + getReleaseDate() + "\n");
        output.append("Sales Rank - " + getSalesRank() + "\n");
        output.append("Status - " + getStatus() + "\n");
        output.append("Theatre Release Date - " + getTheatricalReleaseDate() + "\n");
        output.append("third party new count - " + getThirdPartyNewCount() + "\n");
        output.append("third party new price - " + getThirdPartyNewPrice() + "\n");
        output.append("UPC - " + getUpc() + "\n");
        output.append("Used Count - " + getUsedCount() + "\n");
        output.append("Used Price - " + getUsedPrice() + "\n");

        Tracks trackBean = new Tracks();
        trackBean = getTracks();
        output.append(trackBean + "\n");

        Lists listBean = new Lists();
        listBean = getLists();
        output.append(listBean + "\n");

        Artists artists = new Artists();
        artists = getArtists();
        output.append(artists + "\n");

        Features features = new Features();
        features = getFeatures();
        output.append(features + "\n");

        SimilarProducts simProducts = new SimilarProducts();
        simProducts = getSimilarProducts();
        output.append(simProducts + "\n");

        Reviews reviews = new Reviews();
        reviews = getReviews();
        output.append(reviews + "\n");

        BrowseList browseList = new BrowseList();
        browseList = getBrowseList();
        output.append(browseList + "\n");

        Accessories accessories = new Accessories();
        accessories = getAccessories();
        output.append(accessories + "\n");

        Directors directors = new Directors();
        directors = getDirectors();
        output.append(directors + "\n");

        Starring starring = new Starring();
        starring = getStarring();
        output.append(starring + "\n");

        Authors authors = new Authors();
        authors = getAuthors();
        output.append(authors + "\n");

        Platforms platforms = new Platforms();
        platforms = getPlatforms();
        output.append(platforms + "\n");

        ThirdPartyProductInfo thirdPartyProductInfo = new ThirdPartyProductInfo();
        thirdPartyProductInfo = getThirdPartyProductInfo();
        output.append(thirdPartyProductInfo + "\n");

        return output.toString();
    }
}
