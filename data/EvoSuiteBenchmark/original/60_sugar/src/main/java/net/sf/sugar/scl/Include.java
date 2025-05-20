package net.sf.sugar.scl;

import org.w3c.dom.Element;


import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 30-Aug-2008
 * Time: 23:40:12
 */
public class Include {

    private boolean fullyResolved;

    private URI parentURI;

    private URI includeURI;

    private Element parentElement;

    private IncludeHolder includeHolder;

    public boolean isFullyResolved() {
        return fullyResolved;
    }

    public void setFullyResolved() {
        this.fullyResolved = true;
    }

    public URI getParentURI() {
        return parentURI;
    }

    public void setParentURI(URI parentIncludeURI) {
        this.parentURI = parentIncludeURI;
    }

    public URI getIncludeURI() {
        return includeURI;
    }

    public void setIncludeURI(URI includeURI) {
        this.includeURI = includeURI;
    }

    public Element getParentElement() {
        return parentElement;
    }

    public void setParentElement(Element parentElement) {
        this.parentElement = parentElement;
    }

    public IncludeHolder getIncludeHolder() {
        return includeHolder;
    }

    public void setIncludeHolder(IncludeHolder includeHolder) {
        this.includeHolder = includeHolder;
    }


    public String toString() {
        return "Include{" +
                "fullyResolved=" + fullyResolved +
                ", parentURI=" + parentURI +
                ", includeURI=" + includeURI +
                ", parentElement=" + parentElement +
                ", includeHolder=" + includeHolder +
                '}';
    }
}
