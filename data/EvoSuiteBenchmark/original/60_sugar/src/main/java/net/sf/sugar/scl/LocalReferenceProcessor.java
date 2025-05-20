package net.sf.sugar.scl;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 07-Sep-2008
 * Time: 21:47:09
 *
 * @author kbishop
 * @version $Id$
 */
public interface LocalReferenceProcessor {

    public void processLocalReferences(List<LocalReference> localRefs) throws LocalReferenceException;
}
