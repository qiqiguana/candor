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
 * Date: May 13, 2003
 * Time: 6:57:11 PM
 *
 *
 */
public class ProductInfo implements Serializable {

    ArrayList products;
    ProductDetails details;

    String totalResults,totalPages,listName;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ProductDetails[] getDetails() {
        ProductDetails[] reviewArray = new ProductDetails[products.size()];
        return (ProductDetails[]) products.toArray(reviewArray);
    }

    public void setDetails(ProductDetails[] productDetails) {
        products = new ArrayList(productDetails.length);
        for (int i = 0; i < productDetails.length; i++) {
            products.add(productDetails[i]);
        }
    }

    public ArrayList getProductsArrayList() {
        return products;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        ProductDetails product = new ProductDetails();

        output.append("Total results = " + getTotalResults() + "\n");
        output.append("Total pages = " + getTotalPages() + "\n");

        if (getProductsArrayList() != null) {
            for (int x = 0; x < getProductsArrayList().size(); x++) {
                product = (ProductDetails) getProductsArrayList().get(x);
                output.append(product + "\n");
            }

            output.append("# of products = " + getProductsArrayList().size() + "\n");
        } else {
            output.append("products is null \n");
        }

        return output.toString();
    }

    public String printProductList() {
        StringBuffer output = new StringBuffer();
        ProductDetails product = new ProductDetails();

        output.append("Total results = " + getTotalResults() + "\n");
        output.append("Total pages = " + getTotalPages() + "\n");

        if (getProductsArrayList() != null) {
            for (int x = 0; x < getProductsArrayList().size(); x++) {
                product = (ProductDetails) getProductsArrayList().get(x);
                output.append("< " + x + " > " + product.getAsin() + " : " + product.getProductName() + " - " + product.getOurPrice() + "\n");
            }

            output.append("# of products = " + getProductsArrayList().size() + "\n");
        } else {
            output.append("products is null \n");
        }

        return output.toString();
    }
}
