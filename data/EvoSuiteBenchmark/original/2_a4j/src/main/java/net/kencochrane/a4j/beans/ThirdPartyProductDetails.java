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

import net.kencochrane.a4j.util.a4jUtil;

import java.io.Serializable;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 16, 2003
 * Time: 3:01:35 PM
 *
 *
 */
public class ThirdPartyProductDetails implements Serializable {

    String sellerId,sellerNickname,exchangeId,offeringPrice,condition,conditionType
    ,exchangeAvailability,sellerCountry,sellerState,sellerRating,shipComments;

    a4jUtil jawsUtil = new a4jUtil();

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNickname() {
        return sellerNickname;
    }

    public void setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getOfferingPrice() {
        return offeringPrice;
    }

    public void setOfferingPrice(String offeringPrice) {
        this.offeringPrice = offeringPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getExchangeAvailability() {
        return exchangeAvailability;
    }

    public void setExchangeAvailability(String exchangeAvailability) {
        this.exchangeAvailability = exchangeAvailability;
    }

    public String getSellerCountry() {
        return sellerCountry;
    }

    public void setSellerCountry(String sellerCountry) {
        this.sellerCountry = sellerCountry;
    }

    public String getSellerState() {
        return sellerState;
    }

    public void setSellerState(String sellerState) {
        this.sellerState = sellerState;
    }

    public String getShipComments() {
        return shipComments;
    }

    public void setShipComments(String shipComments) {
        this.shipComments = shipComments;
    }

    public String getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(String sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();

        output.append("------------------- \n");
        output.append("SellerId = " + getSellerId() + "\n");
        output.append("SellerNickName = " + getSellerNickname() + "\n");
        output.append("ExchangeID = " + getExchangeId() + "\n");
        output.append("Price =  " + getOfferingPrice() + "\n");
        output.append("Condition = " + getCondition() + "\n");
        output.append("Condition Type = " + getConditionType() + "\n");
        output.append("Exchange Availability = " + getExchangeAvailability() + "\n");
        output.append("County = " + getSellerCountry() + "\n");
        output.append("State = " + getSellerState() + "\n");
        output.append("Comments = " + getShipComments() + "\n");
        output.append("------------------- \n");

        return output.toString();
    }
}
