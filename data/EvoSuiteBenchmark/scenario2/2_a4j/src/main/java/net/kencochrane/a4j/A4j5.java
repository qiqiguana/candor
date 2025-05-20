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
 */
public class A4j {

    /**
     * Add to product to your Shopping cart
     *
     * @since 1.0
     * @param asin amazon id for the product
     * @param quantity number of items to add
     * @return ShoppingCart
     */
    public ShoppingCart AddtoCart(String asin, String quantity) {
        Cart cart = new Cart();
        return cart.AddtoCart(asin, quantity);
    }
}
