package net.sf.sugar.scl;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 31-Aug-2008
 * Time: 20:20:09
 * 
 * @author kbishop
 * @verion $Id$
 */
public class IncludeValue extends Include {

    private String propertyName;

    private String xPathQuery;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getXPathQuery() {
        return xPathQuery;
    }

    public void setXPathQuery(String xPathQuery) {
        this.xPathQuery = xPathQuery;
    }
}