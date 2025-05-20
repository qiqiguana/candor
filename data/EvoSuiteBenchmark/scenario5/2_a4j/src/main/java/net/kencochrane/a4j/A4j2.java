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
     * Search for an actor
     *
     * @param actorName name to search for
     * @param mode (dvd, vhs, video)
     * @param page
     * @return
     */
    public ProductInfo ActorSearch(String actorName, String mode, String page);
}

/**
 * http://www.KenCochrane.net
 * User: Ken Cochrane
 * Date: Aug 1, 2003
 * Time: 7:39:44 PM
 */
public class A4j {

    /**
     * Search for an actor
     *
     * @since 1.0
     * @param actorName - actor or actresses name to search for
     * @param mode (dvd, vhs, video)
     * @param page 1 2 3 etc
     * @return ProductInfo
     */
    public ProductInfo ActorSearch(String actorName, String mode, String page) {
        Search search = new Search();
        return search.ActorSearch(actorName, mode, page);
    }
}
