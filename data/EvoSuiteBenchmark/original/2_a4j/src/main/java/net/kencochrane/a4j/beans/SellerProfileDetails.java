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
 * Time: 3:39:14 PM
 *
 *
 */
public class SellerProfileDetails implements Serializable {
    private String sellerNickname,overallFeedbackRating,numberOfFeedback,
    numberofCanceledAuctions,storeId,storeName;

    private SellerFeedback sellerFeedBack = new SellerFeedback();

    public String getSellerNickname() {
        return sellerNickname;
    }

    public void setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
    }

    public String getOverallFeedbackRating() {
        return overallFeedbackRating;
    }

    public void setOverallFeedbackRating(String overallFeedbackRating) {
        this.overallFeedbackRating = overallFeedbackRating;
    }

    public String getNumberOfFeedback() {
        return numberOfFeedback;
    }

    public void setNumberOfFeedback(String numberOfFeedback) {
        this.numberOfFeedback = numberOfFeedback;
    }

    public String getNumberofCanceledAuctions() {
        return numberofCanceledAuctions;
    }

    public void setNumberofCanceledAuctions(String numberofCanceledAuctions) {
        this.numberofCanceledAuctions = numberofCanceledAuctions;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public SellerFeedback getSellerFeedBack() {
        return sellerFeedBack;
    }

    public void setSellerFeedBack(SellerFeedback sellerFeedBack) {
        this.sellerFeedBack = sellerFeedBack;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();

        output.append("NickName = " + this.sellerNickname + "\n");
        output.append("OverallRating = " + this.overallFeedbackRating + "\n");
        output.append("# of feedbacks = " + this.numberOfFeedback + "\n");
        output.append("# of Canceled Auctions = " + this.numberofCanceledAuctions + "\n");
        output.append("StoreId = " + this.storeId + "\n");
        output.append("StoreName = " + this.storeName + "\n");
        output.append("FeedBack = \n" + this.sellerFeedBack + "\n");
        return output.toString();
    }
}
