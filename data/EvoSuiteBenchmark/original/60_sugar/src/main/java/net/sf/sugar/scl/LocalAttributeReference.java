package net.sf.sugar.scl;

import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 07-Sep-2008
 * Time: 21:12:04
 */
public class LocalAttributeReference extends LocalReference{

    private String attributeName;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

}
