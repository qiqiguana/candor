package net.sf.sugar.scl;

import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 05-Sep-2008
 * Time: 00:43:49
 *
 * @author kbishop
 * @version $Id$
 */
public class IncludeHolder {

    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }


    public String toString() {
        return "IncludeHolder{" +
                "document=" + document +
                '}';
    }
}
