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
public class IncludePartial extends Include {
    
    private String xPathQuery;

    public String getXPathQuery() {
        return xPathQuery;
    }

    public void setXPathQuery(String xPathQuery) {
        this.xPathQuery = xPathQuery;
    }
}
