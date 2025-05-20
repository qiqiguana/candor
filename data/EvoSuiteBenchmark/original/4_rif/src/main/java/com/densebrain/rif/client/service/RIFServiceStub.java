/**
        * RIFServiceStub.java
        *
        * This file was auto-generated from WSDL
        * by the Apache Axis2 version: 1.0 May 05, 2006 (12:31:13 IST)
        */
package com.densebrain.rif.client.service;


/*
*  RIFServiceStub java implementation
*/
public class RIFServiceStub extends org.apache.axis2.client.Stub
    implements RIFService {
    //default axis home being null forces the system to pick up the mars from the axis2 library
    public static final java.lang.String AXIS2_HOME = null;
    protected static org.apache.axis2.description.AxisOperation[] _operations;

    //http://JGLANZLT:10001/rif/services/RIFService
    private static javax.xml.namespace.QName[] qNameArray = {  };

    //hashmaps to keep the fault mapping
    private java.util.HashMap faultExeptionNameMap = new java.util.HashMap();
    private java.util.HashMap faultExeptionClassNameMap = new java.util.HashMap();
    private java.util.HashMap faultMessageMap = new java.util.HashMap();
    private javax.xml.namespace.QName[] opNameArray = null;

    public RIFServiceStub(
        org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint) throws java.lang.Exception {
        //To populate AxisService
        populateAxisService();
        populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,
                _service);
        _serviceClient.getOptions()
                      .setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));

        //Set the soap version
        _serviceClient.getOptions()
                      .setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
    }

/**
     * Default Constructor
     */
    public RIFServiceStub() throws java.lang.Exception {
        this("http://JGLANZLT:10001/rif/services/RIFService");
    }

/**
     * Constructor taking the target endpoint
     */
    public RIFServiceStub(java.lang.String targetEndpoint)
        throws java.lang.Exception {
        this(org.apache.axis2.context.ConfigurationContextFactory.createConfigurationContextFromFileSystem(
                AXIS2_HOME, null), targetEndpoint);
    }

    private void populateAxisService() {
        //creating the Service
        _service = new org.apache.axis2.description.AxisService("RIFService");

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];

        __operation = new org.apache.axis2.description.OutInAxisOperation();

        __operation.setName(new javax.xml.namespace.QName("", "invoke"));

        _operations[0] = __operation;
        _service.addOperation(__operation);
    }

    //populates the faults
    private void populateFaults() {
    }

    /**
     * Auto generated method signature
     *
     * @param param2
     *
     * @see com.densebrain.rif.client.service.RIFService#invoke
     */
    public com.densebrain.rif.client.service.types.InvokeResponse invoke(
        com.densebrain.rif.client.service.types.Invoke param2)
        throws java.rmi.RemoteException {
        try {
            org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
            _operationClient.getOptions().setAction("urn:invoke");
            _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

            // create SOAP envelope with that payload
            org.apache.axiom.soap.SOAPEnvelope env = null;

            //Style is Doc.
            env = toEnvelope(getFactory(_operationClient.getOptions()
                                                        .getSoapVersionURI()),
                    param2,
                    optimizeContent(new javax.xml.namespace.QName("", "invoke")));

            // create message context with that soap envelope
            org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

            //execute the operation client
            _operationClient.execute(true);

            org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
            org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

            java.lang.Object object = fromOM(getElement(_returnEnv, "document"),
                    com.densebrain.rif.client.service.types.InvokeResponse.class,
                    getEnvelopeNamespaces(_returnEnv));
            _messageContext.getTransportOut().getSender()
                           .cleanup(_messageContext);

            return (com.densebrain.rif.client.service.types.InvokeResponse) object;
        } catch (org.apache.axis2.AxisFault f) {
            org.apache.axiom.om.OMElement faultElt = f.getDetail();

            if (faultElt != null) {
                if (faultExeptionNameMap.containsKey(faultElt.getQName())) {
                    //make the fault by reflection
                    try {
                        java.lang.String exceptionClassName = (java.lang.String) faultExeptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.rmi.RemoteException ex = (java.rmi.RemoteException) exceptionClass.newInstance();

                        //message class
                        java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,
                                messageClass, null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                new java.lang.Class[] { messageClass });
                        m.invoke(ex, new java.lang.Object[] { messageObject });

                        throw ex;
                    } catch (java.lang.ClassCastException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                } else {
                    throw f;
                }
            } else {
                throw f;
            }
        }
    }

    /**
     * A utility method that copies the namepaces from the SOAPEnvelope
     */
    private java.util.Map getEnvelopeNamespaces(
        org.apache.axiom.soap.SOAPEnvelope env) {
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();

        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getName());
        }

        return returnMap;
    }

    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        if (opNameArray == null) {
            return false;
        }

        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;
            }
        }

        return false;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.densebrain.rif.client.service.types.Invoke param,
        boolean optimizeContent) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                    new org.apache.axis2.util.StreamWrapper(param.getPullParser(
                            com.densebrain.rif.client.service.types.Invoke.MY_QNAME)));
            org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();
            ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null); // remove the parent link

            return documentElement;
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.densebrain.rif.client.service.types.Invoke param,
        boolean optimizeContent) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new org.apache.axis2.databinding.ADBSOAPModelBuilder(param.getPullParser(
                        com.densebrain.rif.client.service.types.Invoke.MY_QNAME),
                    factory);

            return builder.getEnvelope();
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.densebrain.rif.client.service.types.InvokeResponse param,
        boolean optimizeContent) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                    new org.apache.axis2.util.StreamWrapper(param.getPullParser(
                            com.densebrain.rif.client.service.types.InvokeResponse.MY_QNAME)));
            org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();
            ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null); // remove the parent link

            return documentElement;
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.densebrain.rif.client.service.types.InvokeResponse param,
        boolean optimizeContent) {
        if (param instanceof org.apache.axis2.databinding.ADBBean) {
            org.apache.axis2.databinding.ADBSOAPModelBuilder builder = new org.apache.axis2.databinding.ADBSOAPModelBuilder(param.getPullParser(
                        com.densebrain.rif.client.service.types.InvokeResponse.MY_QNAME),
                    factory);

            return builder.getEnvelope();
        } else {
            //todo finish this onece the bean serializer has the necessary methods
            return null;
        }
    }

    /**
     * get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type, java.util.Map extraNamespaces) {
        try {
            if (com.densebrain.rif.client.service.types.Invoke.class.equals(
                        type)) {
                return com.densebrain.rif.client.service.types.Invoke.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.densebrain.rif.client.service.types.InvokeResponse.class.equals(
                        type)) {
                return com.densebrain.rif.client.service.types.InvokeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
