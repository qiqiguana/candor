package net.sf.sugar.scl;

import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 07-Sep-2008
 * Time: 21:21:04
 */
public class LocalReference {

    private boolean resolved;

    private Element parentElement;

    private String xPathQuery;

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved() {
        this.resolved = true;
    }

    public Element getParentElement() {
        return parentElement;
    }

    public void setParentElement(Element parentElement) {
        this.parentElement = parentElement;
    }

    public String getXPathQuery() {
        return xPathQuery;
    }

    public void setXPathQuery(String xPathQuery) {
        this.xPathQuery = xPathQuery;
    }
}
