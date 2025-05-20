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
     * @param type
     * @return
     */
    public BlendedSearch Blended(String searchTerm, String type);
}

/**
 * http://www.KenCochrane.net
 * User: Ken Cochrane
 * Date: Aug 1, 2003
 * Time: 7:39:44 PM
 */
public class A4j {

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
        return search.Blended(searchTerm, type);
    }
}
