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
package net.kencochrane.a4j;

import net.kencochrane.a4j.DAO.Cart;
import net.kencochrane.a4j.DAO.Product;
import net.kencochrane.a4j.DAO.Search;
import net.kencochrane.a4j.beans.*;

/**
 * http://www.KenCochrane.net
 * User: Ken Cochrane
 * Date: Aug 1, 2003
 * Time: 7:39:44 PM
 * 
 * 
 */
public class A4j {

    /**
     * Given an ASIN it will return a populated fullProduct bean
     *
     * @since 1.0
     * @param asin
     * @param offer (all, ThirdPartyNew, Used, Collectible and Refurbished)
     * @param page 1 2 3 4 etc.
     * @return FullProduct
     */
    public FullProduct getFullProductFromASIN(String asin, String offer, String page) {
        Product product = new Product();
        return product.getProduct(asin,offer,page);
    }

    /**
     * With a single query, developers can retrieve results across all the different product categories,
     * sorted by relevance.  For a blended search, you pass in search keywords but no mode or page parameter.
     * This will return up to 3 results for each of the product categories currently available.
     * This can currently mean up to 45 results for a single query.
     *
     * @since 1.0
     * @param searchTerm the term you want to search for
     * @param type heavy or lite
     * @return BlendedSearch
     */
    public BlendedSearch BlendedSearch(String searchTerm, String type) {
        Search search = new Search();
        return search.Blended(searchTerm,type);
    }

    /**
     * A keyword is a general search term that is used to find products in the Amazon.com
     * catalog. Often, more than one keyword is used at the same time to form a short phrase
     * (such as finance software).
     *
     * @since 1.0
     * @param searchTerm the term you want to search for
     * @param productLine  books, dvd, etc
     * @param type heavy or lite
     * @param page 1 2 3 etc
     * @return ProductInfo
     */
     public ProductInfo KeywordSearch(String searchTerm, String productLine, String type, String page) {
        Search search = new Search();
        return search.Keyword(searchTerm, productLine, type, page);
     }

     /**
      * Search for an actor
      * @since 1.0
      * @param actorName - actor or actresses name to search for
      * @param mode (dvd, vhs, video)
      * @param page 1 2 3 etc
      * @return ProductInfo
      */
     public ProductInfo ActorSearch(String actorName, String mode, String page) {
        Search search = new Search();
        return search.ActorSearch(actorName,mode,page);
     }

    /**
     * Search for an artist
     * @since 1.0
     * @param artistName - artist name to search for.
     * @param mode (music, classical)
     * @param page 1 2 3 etc.
     * @return ProductInfo
     */
     public ProductInfo ArtistSearch(String artistName, String mode, String page) {
        Search search = new Search();
        return search.ArtistSearch(artistName,mode,page);
     }

    /**
     * Search books for an author
     * @since 1.0
     * @param authorName author to search for
     * @param page 1 2 3 etc.
     * @return ProductInfo
     */
     public ProductInfo AuthorSearch(String authorName, String page) {
        Search search = new Search();
        return search.AuthorSearch(authorName,page);
     }

    /**
     * Search for movie directors
     * @since 1.0
     * @param directorName
     * @param mode (dvd, vhs, video)
     * @param page 1 2 3 etc.
     * @return ProductInfo
     */
     public ProductInfo DirectorSearch(String directorName, String mode, String page) {
        Search search = new Search();
        return search.DirectorSearch(directorName, mode, page);
     }

     /**
     *  Search by Manufacture
     * @since 1.0
     * @param manufactureName
     * @param mode (electronics, kitchen, videogames, software, photo, pc-hardware)
     * @param page
     * @return  ProductInfo
     */
     public ProductInfo ManufactureSearch(String manufactureName, String mode, String page) {
        Search search = new Search();
        return search.ManufactureSearch(manufactureName,mode,page);
     }

    /**
     * Search by UPC
     * @since 1.0
     * @param upc
     * @param mode (music, classical) might be more now, need to confirm.
     * @param page
     * @return ProductInfo
     */
     public ProductInfo UpcSearch(String upc, String mode, String page) {
        Search search = new Search();
        return search.UpcSearch(upc,mode,page);
     }

     /**
      * Search for a Listmania
      *
      * ~todo - Not sure if this works, never confirmed it, if it works let me know.~
      *
      * @since 1.0
      * @param listId
      * @return ProductInfo
      */
     public ProductInfo ListmaniaSearch(String listId) {
        Search search = new Search();
        return search.ListmaniaSearch(listId);
     }

    /**
     * search for wishlist
     *
     * ~todo - Not sure if this works, never confirmed it, if it works let me know.~
     *
     * @since 1.0
     * @param wishListId
     * @return ProductInfo
     */
     public ProductInfo WishListSearch(String wishListId) {
        Search search = new Search();
        return search.WishListSearch(wishListId);
     }

    /**
     * There are thousands of third-party sellers who offer their own products on the Amazon.com Web site.
     * If you are interested in receiving an XML list of products offered by a specific seller, you simply
     * need to plug that seller's unique ID into the request format below. To find the seller ID, visit the
     * seller's member profile page. The ID appears in that page's URL after the '/ts/customer-glance' string.
     * For example, in the following URL, the seller ID would be A2OFEB6YLE4L1K.
     *
     * http://s1.amazon.com/exec/varzea/ts/customer-glance/A2OFEB6YLE4L1K
     *
     * @param sellerId
     * @param type  heavy or lite
     * @param page
     * @param status open or closed
     * @return
     */
     public SellerSearch ThirdParty(String sellerId, String type, String page, String status) {
        Search search = new Search();
        return search.ThirdParty(sellerId,type,page,status);
     }

    /**
     * Add to product to your Shopping cart
     * @since 1.0
     * @param asin amazon id for the product
     * @param quantity number of items to add
     * @return ShoppingCart
     *
     */
    public ShoppingCart AddtoCart(String asin, String quantity) {
        Cart cart = new Cart();
        return cart.AddtoCart(asin,quantity);
    }

    /**
     *  If you already have a shopping cart created add to it with this
     * @since 1.0
     * @param cartId
     * @param hmac
     * @param asin
     * @param quantity
     * @return ShoppingCart
     */
    public ShoppingCart addToExistingCart(String cartId, String hmac, String asin, String quantity) {
        Cart cart = new Cart();
        return cart.addToExistingCart(cartId, hmac, asin, quantity);
    }

    /**
     * Clear the shopping cart so that there is no items in it.
     * @since 1.0
     * @param hmac
     * @param cartId
     * @return ShoppingCart
     */
    public ShoppingCart clearCart(String hmac, String cartId) {
        Cart cart = new Cart();
        return cart.clearCart(hmac, cartId);
    }

    /**
     * Modify the cart by changing the quantity for an item in the cart
     * @since 1.0
     * @param hmac
     * @param cartId
     * @param itemId
     * @param quantity
     * @return ShoppingCart
     */
    public ShoppingCart modifyCart(String hmac, String cartId, String itemId, String quantity) {
        Cart cart = new Cart();
        return cart.modifyCart(hmac,cartId,itemId,quantity);
    }

    /**
     * Get all of the items that are in the cart
     * @since 1.0
     * @param hmac
     * @param cartId
     * @return ShoppingCart
     */
    public ShoppingCart GetItemsFromCart(String hmac, String cartId) {
        Cart cart = new Cart();
        return cart.GetItemsFromCart(hmac,cartId);
    }

    /**
     * Remove an Item from the cart
     * @since 1.0
     * @param hmac
     * @param cartId
     * @param itemId
     * @return ShoppingCart
     */
    public ShoppingCart RemoveFromCart(String hmac, String cartId, String itemId) {
        Cart cart = new Cart();
        return cart.RemoveFromCart(hmac, cartId, itemId);
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
