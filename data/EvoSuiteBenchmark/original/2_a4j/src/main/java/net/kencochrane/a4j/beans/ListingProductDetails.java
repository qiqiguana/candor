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

import java.io.Serializable;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 16, 2003
 * Time: 5:41:57 PM
 *
 *
 */
public class ListingProductDetails implements Serializable {

    private String exchangeId,listingId,exchangeTitle,exchangePrice,exchangeAsin,
    exchangeEndDate,exchangeOfferingType,exchangeSellerId,exchangeSellerNickname,
    exchangeStartDate,exchangeStatus,exchangeQuantity,exchangeQuantityAllocated,
    exchangeFeaturedCategory,exchangeConditionType,exchangeAvailability,
    exchangeSellerState,exchangeSellerCountry,exchangeSellerRating;

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getExchangeTitle() {
        return exchangeTitle;
    }

    public void setExchangeTitle(String exchangeTitle) {
        this.exchangeTitle = exchangeTitle;
    }

    public String getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(String exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public String getExchangeAsin() {
        return exchangeAsin;
    }

    public void setExchangeAsin(String exchangeAsin) {
        this.exchangeAsin = exchangeAsin;
    }

    public String getExchangeEndDate() {
        return exchangeEndDate;
    }

    public void setExchangeEndDate(String exchangeEndDate) {
        this.exchangeEndDate = exchangeEndDate;
    }

    public String getExchangeOfferingType() {
        return exchangeOfferingType;
    }

    public void setExchangeOfferingType(String exchangeOfferingType) {
        this.exchangeOfferingType = exchangeOfferingType;
    }

    public String getExchangeSellerId() {
        return exchangeSellerId;
    }

    public void setExchangeSellerId(String exchangeSellerId) {
        this.exchangeSellerId = exchangeSellerId;
    }

    public String getExchangeSellerNickname() {
        return exchangeSellerNickname;
    }

    public void setExchangeSellerNickname(String exchangeSellerNickname) {
        this.exchangeSellerNickname = exchangeSellerNickname;
    }

    public String getExchangeStartDate() {
        return exchangeStartDate;
    }

    public void setExchangeStartDate(String exchangeStartDate) {
        this.exchangeStartDate = exchangeStartDate;
    }

    public String getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String getExchangeQuantity() {
        return exchangeQuantity;
    }

    public void setExchangeQuantity(String exchangeQuantity) {
        this.exchangeQuantity = exchangeQuantity;
    }

    public String getExchangeQuantityAllocated() {
        return exchangeQuantityAllocated;
    }

    public void setExchangeQuantityAllocated(String exchangeQuantityAllocated) {
        this.exchangeQuantityAllocated = exchangeQuantityAllocated;
    }

    public String getExchangeFeaturedCategory() {
        return exchangeFeaturedCategory;
    }

    public void setExchangeFeaturedCategory(String exchangeFeaturedCategory) {
        this.exchangeFeaturedCategory = exchangeFeaturedCategory;
    }

    public String getExchangeConditionType() {
        return exchangeConditionType;
    }

    public void setExchangeConditionType(String exchangeConditionType) {
        this.exchangeConditionType = exchangeConditionType;
    }

    public String getExchangeAvailability() {
        return exchangeAvailability;
    }

    public void setExchangeAvailability(String exchangeAvailability) {
        this.exchangeAvailability = exchangeAvailability;
    }

    public String getExchangeSellerState() {
        return exchangeSellerState;
    }

    public void setExchangeSellerState(String exchangeSellerState) {
        this.exchangeSellerState = exchangeSellerState;
    }

    public String getExchangeSellerCountry() {
        return exchangeSellerCountry;
    }

    public void setExchangeSellerCountry(String exchangeSellerCountry) {
        this.exchangeSellerCountry = exchangeSellerCountry;
    }

    public String getExchangeSellerRating() {
        return exchangeSellerRating;
    }

    public void setExchangeSellerRating(String exchangeSellerRating) {
        this.exchangeSellerRating = exchangeSellerRating;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();

        output.append(" ----------- <br />\n");
        output.append("ASIN " + this.exchangeAsin + "<br />\n");
        output.append("Avail " + this.exchangeAvailability + "<br />\n");
        output.append("Condition Type " + this.exchangeConditionType + "<br />\n");
        output.append("EndDate " + this.exchangeEndDate + "<br />\n");
        output.append("Featured Cat " + this.exchangeFeaturedCategory + "<br />\n");
        output.append("Ex ID " + this.exchangeId + "<br />\n");
        output.append("Offer Type " + this.exchangeOfferingType + "<br />\n");
        output.append("Ex Price " + this.exchangePrice + "<br />\n");
        output.append("Ex Quant " + this.exchangeQuantity + "<br />\n");
        output.append("Quantity Allocated " + this.exchangeQuantityAllocated + "<br />\n");
        output.append("Seller Country " + this.exchangeSellerCountry + "<br />\n");
        output.append("Seller Id " + this.exchangeSellerId + "<br />\n");
        output.append("Seller Nickname " + this.exchangeSellerNickname + "<br />\n");
        output.append("Seller Rating " + this.exchangeSellerRating + "<br />\n");
        output.append("Seller State " + this.exchangeSellerState + "<br />\n");
        output.append("Start date " + this.exchangeStartDate + "<br />\n");
        output.append("Status " + this.exchangeStatus + "<br />\n");
        output.append("Title " + this.exchangeTitle + "<br />\n");
        output.append(" ----------- <br />\n");

        return output.toString();
    }

}
