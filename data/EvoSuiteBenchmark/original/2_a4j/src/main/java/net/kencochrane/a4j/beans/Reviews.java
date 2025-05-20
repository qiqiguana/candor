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
import java.util.ArrayList;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 16, 2003
 * Time: 11:18:59 AM
 *
 *
 */
public class Reviews implements Serializable {
    String avgCustomerRating,TotalCustomerReviews;

    private ArrayList reviews;

    CustomerReview customerReview = new CustomerReview();

    public String getAvgCustomerRating() {
        return avgCustomerRating;
    }

    public void setAvgCustomerRating(String avgCustomerRating) {
        this.avgCustomerRating = avgCustomerRating;
    }

    public String getTotalCustomerReviews() {
        return TotalCustomerReviews;
    }

    public void setTotalCustomerReviews(String totalCustomerReviews) {
        TotalCustomerReviews = totalCustomerReviews;
    }

    public CustomerReview[] getCustomerReview() {
        CustomerReview[] reviewArray = new CustomerReview[reviews.size()];
        return (CustomerReview[]) reviews.toArray(reviewArray);
    }

    public void setCustomerReview(CustomerReview[] customerReview) {
        reviews = new ArrayList(customerReview.length);
        for (int i = 0; i < customerReview.length; i++) {
            reviews.add(customerReview[i]);
        }
    }

    public ArrayList getReviewsArrayList() {
        return reviews;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        CustomerReview custReview = new CustomerReview();

        output.append(getAvgCustomerRating() + "\n");
        output.append(getTotalCustomerReviews() + "\n");

        if (getReviewsArrayList() != null) {
            for (int x = 0; x < getReviewsArrayList().size(); x++) {
                custReview = (CustomerReview) getReviewsArrayList().get(x);
                output.append(custReview + "\n");
            }

            output.append("# of reviews = " + getReviewsArrayList().size());
        } else {
            output.append("reviews is null ");
        }

        return output.toString();
    }
}
