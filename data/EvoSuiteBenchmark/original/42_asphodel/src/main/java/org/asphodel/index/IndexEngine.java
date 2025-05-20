package org.asphodel.index;

import org.asphodel.FtrException;

import java.util.Collection;

/**
 * @author sunwj
 * @since 0.1
 * Date: Apr 1, 2007
 * Time: 9:21:59 PM
 * index processing.
 */
public interface IndexEngine {
    /**
     * create index using the given content.
     * @param indexee the target to be indexed
     * */
    public void createIndex(Indexee indexee)throws FtrException;

    public void createIndex(Collection<Indexee> indexees)throws FtrException;
}
