/**
 * MyGridServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package mygrid.web;

public class MyGridServiceLocator extends org.apache.axis.client.Service implements mygrid.web.MyGridService {

    // Use to get a proxy class for MyGridServiceSoap
    private final java.lang.String MyGridServiceSoap_address = "http://localhost/MyGrid.Web/MyGrid.asmx";

    public java.lang.String getMyGridServiceSoapAddress() {
        return MyGridServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MyGridServiceSoapWSDDServiceName = "MyGridServiceSoap";

    public java.lang.String getMyGridServiceSoapWSDDServiceName() {
        return MyGridServiceSoapWSDDServiceName;
    }

    public void setMyGridServiceSoapWSDDServiceName(java.lang.String name) {
        MyGridServiceSoapWSDDServiceName = name;
    }

    public mygrid.web.MyGridServiceSoap getMyGridServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MyGridServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMyGridServiceSoap(endpoint);
    }

    public mygrid.web.MyGridServiceSoap getMyGridServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mygrid.web.MyGridServiceSoapStub _stub = new mygrid.web.MyGridServiceSoapStub(portAddress, this);
            _stub.setPortName(getMyGridServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mygrid.web.MyGridServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                mygrid.web.MyGridServiceSoapStub _stub = new mygrid.web.MyGridServiceSoapStub(new java.net.URL(MyGridServiceSoap_address), this);
                _stub.setPortName(getMyGridServiceSoapWSDDServiceName());
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
        String inputPortName = portName.getLocalPart();
        if ("MyGridServiceSoap".equals(inputPortName)) {
            return getMyGridServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "MyGridService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("MyGridServiceSoap"));
        }
        return ports.iterator();
    }

}
