package org.asphodel.search;

import org.asphodel.search.QueryCommand;
import org.asphodel.FtrException;

/**
 * @author sunwj
 * @since 0.1
 * @version 0.1
 * Date: Apr 3, 2007
 * Time: 12:22:54 PM
 */
public interface SearchEngine {

    /**
     * just search the index repository using the given query string.
     * @param queryCommand: the condition used to retrieve result encapsulated as a command object
     * @todo it can contain query syntax? but at current time,it is just a queryPrase or individual words 
     * @return Collection: contains the result which is type of :
     * */
    public FtrSearchResult search(QueryCommand queryCommand)throws FtrException;

    /**
     * @param repository,relative path
     * */
    public FtrSearchResult search(String repository,QueryCommand queryCommand)throws FtrException;
}
