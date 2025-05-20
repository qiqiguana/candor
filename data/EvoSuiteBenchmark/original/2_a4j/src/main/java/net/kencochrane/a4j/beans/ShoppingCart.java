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

import net.kencochrane.a4j.util.a4jUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 24, 2003
 * Time: 12:46:47 PM
 *
 *
 */
public class ShoppingCart implements Serializable {
    //Request, CartId, HMAC, PurchaseURL, Items?
    // Logger log = Logger.getLogger(this.getClass());
    String cartId,HMAC,purchaseURL;
    Items items;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getHMAC() {
        return HMAC;
    }

    public void setHMAC(String HMAC) {
        this.HMAC = HMAC;
    }

    public String getPurchaseURL() {
        return purchaseURL;
    }

    public void setPurchaseURL(String purchaseURL) {
        this.purchaseURL = purchaseURL;
    }

    public Items getItems() {
        return this.items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("HMAC = " + this.HMAC + "\n");
        buffer.append("Purchase URL = " + this.purchaseURL + "\n");
        buffer.append("CartId = " + this.cartId + "\n");
        buffer.append("items = " + this.items + "\n");
        return buffer.toString();
    }

    public String getTotalCartCost() {
        a4jUtil jawsUtil = new a4jUtil();
        String totalCost = null;
        BigDecimal totalCostInt = new BigDecimal(0.00);
        if (this.items != null && this.items.getItemsArrayList() != null && this.items.getItemsArrayList().size() > 0) {
            ArrayList cartItems = this.items.getItemsArrayList();
            Item item = new Item();
            String itemPrice = null;
            BigDecimal intPrice = new BigDecimal(0.00);
            BigDecimal totalPrice = new BigDecimal(0.00);
            BigDecimal mtotal = new BigDecimal(0.00);
            int quant = 0;
            for (int x = 0; x < cartItems.size(); x++) {
                item = (Item) cartItems.get(x);
                itemPrice = item.getOurPrice();
                try {
                    quant = Integer.parseInt(item.getQuantity());
                } catch (Exception e) {
                    quant = 1;
                }
                //        log.debug("quantity = " +quant);
                try {
                    intPrice = jawsUtil.getPrice(itemPrice);
                    //         log.debug("intPrice = " +intPrice);
                    mtotal = new BigDecimal(quant);
                    //         log.debug("mtotal = " + mtotal);
                    totalPrice = intPrice.multiply(mtotal);
                    //        log.debug("total a  = " + totalPrice);
                    totalCostInt = totalCostInt.add(totalPrice);
                    //         log.debug("total = " + totalCostInt);
                } catch (Exception e) {
                    //            log.error("error " + e.toString());
                }
            }
        }
        totalCost = totalCostInt.toString();
        return totalCost;
    }

    public Item getItem(String itemId) {
        Item item = null;
        if (this.items != null && this.items.getItemsArrayList() != null && this.items.getItemsArrayList().size() > 0) {
            ArrayList cartItems = this.items.getItemsArrayList();
            for (int x = 0; x < cartItems.size(); x++) {
                item = (Item) cartItems.get(x);
                if (item.getItemId().trim().equalsIgnoreCase(itemId.trim())) {
                    return item;
                }
            }
        }
        return item;
    }

    public String getNumItems() {
        int count = 0,quant = 0;
        Item item = null;
        if (this.items != null && this.items.getItemsArrayList() != null && this.items.getItemsArrayList().size() > 0) {
            ArrayList cartItems = this.items.getItemsArrayList();
            for (int x = 0; x < cartItems.size(); x++) {
                item = (Item) cartItems.get(x);
                if (item != null) {
                    try {
                        quant = Integer.parseInt(item.getQuantity());
                    } catch (Exception e) {
                        quant = 1;
                    }
                    count = count + quant;
                }
            }
        }
        return String.valueOf(count);
    }

}
