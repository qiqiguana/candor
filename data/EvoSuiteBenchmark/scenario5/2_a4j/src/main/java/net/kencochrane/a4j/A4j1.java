package net.kencochrane.a4j;

import net.kencochrane.a4j.DAO.Cart;
import net.kencochrane.a4j.DAO.Product;
import net.kencochrane.a4j.DAO.Search;
import net.kencochrane.a4j.beans.*;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 23, 2003
 * Time: 2:49:48 PM
 */
public class Search {

    /**
     * @param searchTerm
     * @param productLine
     * @param type
     * @param page
     * @return
     */
    public ProductInfo Keyword(String searchTerm, String productLine, String type, String page);
}

/**
 * http://www.KenCochrane.net
 * User: Ken Cochrane
 * Date: Aug 1, 2003
 * Time: 7:39:44 PM
 */
public class A4j {

    /**
     * A keyword is a general search term that is used to find products in the Amazon.com
     * catalog. Often, more than one keyword is used at the same time to form a short phrase
     * (such as finance software).
     *
     * @since 1.0
     * @param searchTerm the term you want to search for
     * @param productLine books, dvd, etc
     * @param type heavy or lite
     * @param page 1 2 3 etc
     * @return ProductInfo
     */
    public ProductInfo KeywordSearch(String searchTerm, String productLine, String type, String page) {
        Search search = new Search();
        return search.Keyword(searchTerm, productLine, type, page);
    }
}
