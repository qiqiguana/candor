/**
 * MyGridServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package mygrid.web;

public interface MyGridServiceSoap extends java.rmi.Remote {
    public mygrid.web._LogonResponse logon(mygrid.web._Logon parameters) throws java.rmi.RemoteException;
    public mygrid.web._RequestResponse request(mygrid.web._Request parameters) throws java.rmi.RemoteException;
    public mygrid.web._CompleteResponse complete(mygrid.web._Complete parameters) throws java.rmi.RemoteException;
    public mygrid.web._FailResponse fail(mygrid.web._Fail parameters) throws java.rmi.RemoteException;
    public mygrid.web._ProgressResponse progress(mygrid.web._Progress parameters) throws java.rmi.RemoteException;
    public mygrid.web._AvailableJobsResponse availableJobs(mygrid.web._AvailableJobs parameters) throws java.rmi.RemoteException;
    public mygrid.web._SetEngineInfoResponse setEngineInfo(mygrid.web._SetEngineInfo parameters) throws java.rmi.RemoteException;
    public mygrid.web._GetEngineResponse getEngine(mygrid.web._GetEngine parameters) throws java.rmi.RemoteException;
}
