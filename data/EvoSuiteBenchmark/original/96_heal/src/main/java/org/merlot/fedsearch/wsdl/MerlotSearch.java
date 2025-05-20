/**
 * MerlotSearch.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package org.merlot.fedsearch.wsdl;

public interface MerlotSearch extends javax.xml.rpc.Service {
    public java.lang.String getSearchServicePortAddress();

    public org.merlot.fedsearch.wsdl.SearchService getSearchServicePort() throws javax.xml.rpc.ServiceException;

    public org.merlot.fedsearch.wsdl.SearchService getSearchServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
