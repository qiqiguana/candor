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
     * Search books for an author
     *
     * @since 1.0
     * @param authorName author to search for
     * @param page 1 2 3 etc.
     * @return ProductInfo
     */
    public ProductInfo AuthorSearch(String authorName, String page);
}
