package net.sf.sugar.scl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 03-Aug-2008
 * Time: 23:13:28
 *
 * @author kbishop
 * @version $Id$
 */
public interface IncludeProcessor {

    void prepareInclude(Element parentElement,
                        String includeLocation,
                        URI parentLocation) throws IncludeException;

    void prepareInclude(Element parentElement,
                        String includeLocation,
                        URI parentLocation,
                        String xpathQuery) throws IncludeException;

    void prepareIncludeValue(Element parentElement,
                             String propertyName,
                             String includeLocation,
                             URI parentLocation,
                             String xpathQuery) throws IncludeException;

    void resolveIncludes(Document currentDocument) throws IncludeException;

    void loadUnparsedInclude(Element parentElement,
                             String propertyName,
                             String includeLocation,
                             URI parentLocation) throws IncludeException;
}
