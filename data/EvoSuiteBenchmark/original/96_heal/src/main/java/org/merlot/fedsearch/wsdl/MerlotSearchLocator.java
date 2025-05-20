/**
 * MerlotSearchLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package org.merlot.fedsearch.wsdl;

public class MerlotSearchLocator extends org.apache.axis.client.Service implements org.merlot.fedsearch.wsdl.MerlotSearch {

    public MerlotSearchLocator() {
    }


    public MerlotSearchLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MerlotSearchLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SearchServicePort
    private java.lang.String SearchServicePort_address = "http://matanzas.merlot.org:9090/jaxrpc-MerlotSearch/searchservice";

    public java.lang.String getSearchServicePortAddress() {
        return SearchServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SearchServicePortWSDDServiceName = "SearchServicePort";

    public java.lang.String getSearchServicePortWSDDServiceName() {
        return SearchServicePortWSDDServiceName;
    }

    public void setSearchServicePortWSDDServiceName(java.lang.String name) {
        SearchServicePortWSDDServiceName = name;
    }

    public org.merlot.fedsearch.wsdl.SearchService getSearchServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SearchServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSearchServicePort(endpoint);
    }

    public org.merlot.fedsearch.wsdl.SearchService getSearchServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.merlot.fedsearch.wsdl.SearchServiceBindingStub _stub = new org.merlot.fedsearch.wsdl.SearchServiceBindingStub(portAddress, this);
            _stub.setPortName(getSearchServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSearchServicePortEndpointAddress(java.lang.String address) {
        SearchServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.merlot.fedsearch.wsdl.SearchService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.merlot.fedsearch.wsdl.SearchServiceBindingStub _stub = new org.merlot.fedsearch.wsdl.SearchServiceBindingStub(new java.net.URL(SearchServicePort_address), this);
                _stub.setPortName(getSearchServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SearchServicePort".equals(inputPortName)) {
            return getSearchServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://fedsearch.merlot.org/wsdl", "MerlotSearch");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://fedsearch.merlot.org/wsdl", "SearchServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("SearchServicePort".equals(portName)) {
            setSearchServicePortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
