/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ResourceManager.java,v 1.3 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;
import javax.jms.JMSException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.common.uuid.UUID;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.Service;
import org.exolab.jms.tranlog.DataTransactionLogEntry;
import org.exolab.jms.tranlog.ExternalXid;
import org.exolab.jms.tranlog.StateTransactionLogEntry;
import org.exolab.jms.tranlog.TransactionLog;
import org.exolab.jms.tranlog.TransactionLogException;
import org.exolab.jms.tranlog.TransactionState;


/**
 * The resource manager provides XA support for the JMS Server.
 * <p/>
 * The resource manager is responsible for managing the various transaction
 * identifiers and managing the association between transaction ids and
 * connections.
 * <p/>
 * The resource manager will store the global XID's and their state in the
 * database for recovery purposes.
 * <p/>
 * Messages that arrive, and are associated with an XID are not processed
 * through the {@link MessageMgr}. Instead they are routed to this resource
 * managers where they are cached until the associated XID is committed or
 * rolled back. If the transaction is successfully committed, through the 2PC
 * protocol the messages will pass through the system.
 * <p/>
 * Similarly, messages that are sent to consumers, either synchronously or
 * asynchronously are also cached by the resource manager until the global
 * transaction completes.
 * <p/>
 * On startup the resource manager will read all incomplete transactions, which
 * are incompleted into memory. It will then process trnasactions that have
 * timed out.
 * <p/>
 * The transaction manager will call the {@link #recover} method  and obtain a
 * list of incomplete transaction for the purpose of completing them where
 * possible.
 *
 * @author <a href="mailto:jima@intalio.com">Jim Alateras</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 07:26:49 $
 */
public class ResourceManager extends Service {

    /**
     * The extension for all transaction log files
     */
    public final static String RM_LOGFILE_EXTENSION = ".log";

    /**
     * This is used to indicate the garbage collection has been disabled and
     * that the client will take responsibility for all aspects of log file
     * management. This is useful in situations where the client wants to
     * archive the transaction log files
     * <p/>
     * This is the default mode for GC.
     */
    public static final int GC_DISABLED = 0;

    /**
     * Synchronous gabrage collection is used to remove processed log files when
     * the last trnasaction, in that log file, has been successfully processed.
     * This is more efficient means since the log files does not need to be
     * scanned asynchronously to determine whether all the transactions have
     * been processed.
     */
    public static final int GC_SYNCHRONOUS = 1;

    /**
     * Asynchronous garbage collection is used to remove processed log files
     * asynchronous (i.e in a different thread context). This is rather
     * expensive since it must manually scan each log file and determine whether
     * all transactions, in that file, have been closed. If this is the case
     * then it will remove the log file.
     */
    public static final int GC_ASYNCHRONOUS = 2;

    /**
     * The message manager.
     */
    private final MessageManager _messages;

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * This is the maximum size, in bytes, of each transaction log file. The
     * value can be overriden by the user
     */
    private int _logFileSize = 1000000;

    /**
     * Maintains a collection of transaction log files currently in use by this
     * resource manager
     */
    private TreeSet _logs = new TreeSet(new TranLogFileComparator());

    /**
     * Maintain a mapping between the TRID (transaction id and the log file it
     * is associated with.
     */
    private HashMap _tridToLogCache = new HashMap();

    /**
     * Maintain a list of open TRIDs for a particular  {@link TransactionLog}
     */
    private HashMap _logToTridCache = new HashMap();

    /**
     * This attribute is used to synchronize the modifications to the _tridToLog
     * _logToTrid attributes
     */
    private final Object _cacheLock = new Object();

    /**
     * This maintains a cache of all open transactions and the corresponding
     * data. The key is the transaction identifier and the object is a
     * LinkedList transaction entries, which includes both state and data
     */
    private HashMap _activeTransactions = new HashMap();

    /**
     * The directory where the log files are stored. This can be set by the
     * client
     */
    private String _logDirectory = ".";

    /**
     * This is the number of the last log file created by the ResourceManager
     */
    private long _lastLogNumber = 0;

    /**
     * The expiry time for transaction associated with this resource manager.
     * This will either be configured or passed in with the transaction context
     * The value is specified in seconds.
     */
    private int _txExpiryTime = 120;

    /**
     * This attribute caches the garbage collection mode for the resouce
     * managers. Valid values are specified by the GC_* constants.
     * <p/>
     * By default garbage collection is disabled.
     */
    private int _gcMode = GC_SYNCHRONOUS;

    /**
     * This is the id associated with this resource...need to work out who or
     * what sets this.
     */
    private String _rid = UUID.next();

    /**
     * The name of the service
     */
    private final static String RM_SERVICE_NAME = "XAResourceManager";

    /**
     * The prefix used for all transaction log files, which are created and
     * managed by the {@link TransactionLog}
     */
    private final static String RM_LOGFILE_PREFIX = "ojmsrm";

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(ResourceManager.class);


    /**
     * Construct a resource manager using the default directory for its log
     * files.
     * <p/>
     * If the directory does not exist or there is no permisssion to access it,
     * then throw a ResourceManagerException.
     *
     * @param messages     the message manager
     * @param destinations the destination manager
     * @param database     the database service
     * @throws ResourceManagerException
     */
    public ResourceManager(MessageManager messages,
                           DestinationManager destinations,
                           DatabaseService database)
            throws ResourceManagerException {
        super(RM_SERVICE_NAME);

        _messages = messages;
        _destinations = destinations;
        /*

        final String dir = "./logs";
        _logDirectory = dir;
        File file = new File(dir);
        if ((!file.exists()) ||
                (!file.isDirectory())) {
            throw new ResourceManagerException(dir
                                               +
                                               " does not exist or is not a directory");
        }

        // build the list of existing log files.
        buildLogFileList();

        // recover te list of log files
        recover();
        */
    }

    /**
     * Check whether garbage collection has been disabled
     *
     * @return boolean - true if gc is disabled
     */
    public boolean gcDisabled() {
        return (_gcMode == GC_DISABLED) ? true : false;
    }

    /**
     * Log this published message so that it can be passed through the system
     * when the associated global transaction commits.
     *
     * @param xid     - the global transaction identity
     * @param message - the message published
     * @throws TransactionLogException  - error adding the entry
     * @throws ResourceManagerException - error getting the trnasaction log
     * @throws JMSException             - if there is an issue with prep'ing the
     *                                  message
     */
    public synchronized void logPublishedMessage(Xid xid, MessageImpl message)
            throws TransactionLogException, ResourceManagerException,
            JMSException {
        _messages.prepare(message);
        logTransactionData(new ExternalXid(xid), _rid,
                           createPublishedMessageWrapper(message));
    }

    /**
     * Log that this message handle was sent to the consumer within the
     * specified global transaction identity. The message will be acknowledged
     * when the global transaction commits. Alternatively, if the global
     * transaction is rolled back the message handle will be returned to the
     * destination
     *
     * @param xid    the global transaction identity
     * @param id     the consumer receiving this message
     * @param handle - the handle of the message received
     * @throws TransactionLogException  - error adding the entry
     * @throws ResourceManagerException - error getting the transaction log
     */
    public synchronized void logReceivedMessage(Xid xid, long id,
                                                MessageHandle handle)
            throws TransactionLogException, ResourceManagerException {
        logTransactionData(new ExternalXid(xid), _rid,
                           createReceivedMessageWrapper(id, handle));
    }

    /**
     * Add an {@link StateTransactionLogEntry} using the specified txid, rid and
     * state
     *
     * @param xid   - the transaction identifier
     * @param state - the transaction log state
     * @throws TransactionLogException  - error adding the entry
     * @throws ResourceManagerException - error getting the trnasaction log
     */
    public synchronized void logTransactionState(Xid xid,
                                                 TransactionState state)
            throws TransactionLogException, ResourceManagerException {
        ExternalXid txid = new ExternalXid(xid);
        switch (state.getOrd()) {
            case TransactionState.OPENED_ORD:
                {
                    TransactionLog log = getCurrentTransactionLog();
                    addTridLogEntry(txid, log);
                    log.logTransactionState(txid, _txExpiryTime * 1000, _rid,
                                            state);

                    // cache the transaction state
                    _activeTransactions.put(txid, new LinkedList());
                }
                break;

            case TransactionState.PREPARED_ORD:
                // cache the transaction state
                LinkedList list = (LinkedList) _activeTransactions.get(txid);
                if (list != null) {
                    list.add(state);
                } else {
                    throw new ResourceManagerException("Trasaction " + txid +
                                                       " is not active.");
                }
                break;

            case TransactionState.CLOSED_ORD:
                {
                    TransactionLog log = getTransactionLog(txid);
                    log.logTransactionState(txid, _txExpiryTime * 1000, _rid,
                                            state);
                    removeTridLogEntry(txid, log);

                    // check whether this log has anymore open transactions
                    synchronized (_cacheLock) {
                        if ((_logToTridCache.get(log) == null) &&
                                (!isCurrentTransactionLog(log))) {
                            log.close();

                            // now check if gc mode is GC_SYNCHRONOUS. If it is
                            // remove the log file
                            if (_gcMode == GC_SYNCHRONOUS) {
                                try {
                                    log.destroy();
                                } catch (TransactionLogException exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }
                    }

                    // we also want to remove this entry from the list
                    // of active transactions
                    _activeTransactions.remove(txid);
                }
                break;

            default:
                throw new ResourceManagerException("Cannot process tx state " +
                                                   state);
        }
    }

    /**
     * Add an {@link DataTransactionLogEntry} using the specified txid, rid and
     * data
     *
     * @param txid - the transaction identifier
     * @param rid  - the resource identifier
     * @throws TransactionLogException  - error adding the entry
     * @throws ResourceManagerException - error getting the trnasaction log
     */
    synchronized void logTransactionData(ExternalXid txid, String rid,
                                         Object data)
            throws ResourceManagerException, TransactionLogException {
        getTransactionLog(txid).logTransactionData(txid, _txExpiryTime * 1000,
                                                   rid, data);

        // we also want to add this to the transaction data for that
        // txid
        LinkedList list = (LinkedList) _activeTransactions.get(txid);
        if (list != null) {
            list.add(data);
        } else {
            throw new ResourceManagerException("Trasaction " + txid +
                                               " is not active.");
        }
    }

    /**
     * This is the entry point for the garbage collection callback. It scans
     * through the each transaction log file and determines whether it can be
     * garbage collected. If it can then it simply destroys the corresponding
     * TransactionLog.
     */
    public void garbageCollect() {
        try {
            int gcfiles = 0;

            // if there are no transaction log files then return
            if (_logs.size() == 0) {
                return;
            }

            TreeSet copy = null;
            synchronized (_logs) {
                copy = new TreeSet(_logs);
            }

            // remove the current log file, since this is likely to be the
            // current log file
            copy.remove(_logs.last());

            // process each of the remaining log files
            while (copy.size() > 0) {
                TransactionLog log = (TransactionLog) copy.first();
                copy.remove(log);
                if (log.canGarbageCollect()) {
                    // destroy the log
                    log.destroy();

                    // remove it from the log cache
                    synchronized (_logs) {
                        _logs.remove(log);
                    }

                    // increment the number of garbafe collected files
                    ++gcfiles;
                }
            }

            // print an informative message
            _log.info("[RMGC] Collected " + gcfiles + " files.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Ensure that a transaction with the specified xid is currently active. If
     * this is the case then commit the transaction based onb the value of the
     * onePhase flag.
     * <p/>
     * This will have the effect of passing all messages through
     *
     * @param id       - the xa transaction identity
     * @param onePhase - treu if it is a one phase commit
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized void commit(Xid id, boolean onePhase)
            throws XAException {
        // check that the xid is not null
        if (id == null) {
            throw new XAException(XAException.XAER_NOTA);
        }

        // covert to our internal representation of an xid
        ExternalXid xid = new ExternalXid(id);

        // check to see that the transaction is active and open. We should
        // not be allowed to commit a committed transaction.
        if (!isTransactionActive(xid)) {
            throw new XAException(XAException.XAER_PROTO);
        }

        // process all the messages associated with this global transaction
        // If a message has been  published then sent it to the message mgr
        // for processing. If a message has been consumed then remove it
        // from the list of unconsumed messages.
        try {
            // retrieve a list of recrods for the specified global transaction
            // and process them. Ignore the state records and only process the
            // data records, which are of type TransacitonalObjectWrapper.
            Object[] records = getTransactionRecords(xid, _rid);
            for (int index = 0; index < records.length; index++) {
                if (records[index] instanceof TransactionalObjectWrapper) {
                    TransactionalObjectWrapper wrapper =
                            (TransactionalObjectWrapper) records[index];
                    if (wrapper.isPublishedMessage()) {
                        // send the published message to the message manager
                        MessageImpl message = (MessageImpl) wrapper.getObject();
                        _messages.add(message);

                    } else if (wrapper.isReceivedMessage()) {
                        // if it is a received message handle then simply
                        // delete it and mark it as acknowledged
                        MessageHandle handle = ((ReceivedMessageWrapper) (wrapper)).getMessageHandle();
                        handle.destroy();
                    }
                } else {
                    // ignore since it is a state records.
                }
            }
        } catch (Exception exception) {
            _log.error(exception, exception);
            throw new XAException("Failed in ResourceManager.commit : " +
                                  exception.toString());
        } finally {
            // and now mark the transaction as closed
            try {
                logTransactionState(xid, TransactionState.CLOSED);
            } catch (Exception exception) {
                throw new XAException("Error processing commit : " + exception);
            }
        }
    }

    /**
     * Ends the work performed on behalf of a transaction branch. The resource
     * manager disassociates the XA resource from the transaction branch
     * specified and let the transaction be completedCommits an XA transaction
     * that is in progress.
     *
     * @param id    - the xa transaction identity
     * @param flags - one of TMSUCCESS, TMFAIL, or TMSUSPEND
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized void end(Xid id, int flags)
            throws XAException {
        //check the xid is not null
        if (id == null) {
            throw new XAException(XAException.XAER_NOTA);
        }

        // covert to our internal representation of an xid
        ExternalXid xid = new ExternalXid(id);

        // check that the flags are valid for this method
        if ((flags != XAResource.TMSUSPEND) ||
                (flags != XAResource.TMSUCCESS) ||
                (flags != XAResource.TMFAIL)) {
            throw new XAException(XAException.XAER_PROTO);
        }

        switch (flags) {
            case XAResource.TMFAIL:
                // check that the transaction exists
                if (!isTransactionActive(xid)) {
                    throw new XAException(XAException.XAER_PROTO);
                }

                // do not process that associated data, simply rollback
                rollback(xid);
                break;

            case XAResource.TMSUSPEND:
                // check that the transaction is opened
                if (!isTransactionActive(xid)) {
                    throw new XAException(XAException.XAER_PROTO);
                }
                break;

            case XAResource.TMSUCCESS:
                // nothing to do here but check that the resource manager is
                // in a consistent state wrt to this xid. The xid should not
                // be active if it received the commit, forget etc.
                if (isTransactionActive(xid)) {
                    throw new XAException(XAException.XAER_PROTO);
                }
                break;
        }
    }

    /**
     * Tell the resource manager to forget about a heuristically completed
     * transaction branch.
     *
     * @param id - the xa transaction identity
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized void forget(Xid id)
            throws XAException {
        //check the xid is not null
        if (id == null) {
            throw new XAException(XAException.XAER_NOTA);
        }

        // covert to our internal representation of an xid
        ExternalXid xid = new ExternalXid(id);

        // check to see that the xid actually exists
        if (!isTransactionActive(xid)) {
            throw new XAException(XAException.XAER_PROTO);
        }

        // call rollback to complete the work
        rollback(id);
    }

    /**
     * Return the transaction timeout for this instance of the resource
     * manager.
     *
     * @return int - the timeout in seconds
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized int getTransactionTimeout()
            throws XAException {
        return _txExpiryTime;
    }

    /**
     * Ask the resource manager to prepare for a transaction commit of the
     * transaction specified in xid
     *
     * @param xares
     * @return int - XA_RDONLY or XA_OK
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized boolean isSameRM(XAResource xares)
            throws XAException {
        boolean result = false;

        if ((xares == this) ||
                ((xares instanceof ResourceManager) &&
                (((ResourceManager) xares)._rid.equals(_rid)))) {
            result = true;
        }

        return result;
    }

    /**
     * Obtain a list of prepared transaction branches from a resource manager.
     * The transaction manager calls this method during recovery to obtain the
     * list of transaction branches that are currently in prepared or
     * heuristically completed states.
     *
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized int prepare(Xid id)
            throws XAException {
        //check the xid is not null
        if (id == null) {
            throw new XAException(XAException.XAER_NOTA);
        }

        // covert to our internal representation of an xid
        ExternalXid xid = new ExternalXid(id);

        // check to see that the xid actually exists
        if (!isTransactionActive(xid)) {
            throw new XAException(XAException.XAER_PROTO);
        }

        // can a prepare for the same resource occur multiple times
        // ????

        try {
            logTransactionState(xid, TransactionState.PREPARED);
        } catch (Exception exception) {
            throw new XAException("Error processing prepare : " + exception);
        }

        return XAResource.XA_OK;
    }

    /**
     * Inform the resource manager to roll back work done on behalf of a
     * transaction branch
     *
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized Xid[] recover(int flag)
            throws XAException {

        Xid[] result = new Xid[0];

        if ((flag == XAResource.TMNOFLAGS) ||
                (flag == XAResource.TMSTARTRSCAN) ||
                (flag == XAResource.TMENDRSCAN)) {
            LinkedList xids = new LinkedList();
            Iterator iter = _activeTransactions.keySet().iterator();
            while (iter.hasNext()) {
                Xid xid = (Xid) iter.next();
                LinkedList list = (LinkedList) _activeTransactions.get(xid);
                if (list.size() > 1) {
                    // need at least a start in the chain.
                    Object last = list.getLast();
                    if ((last instanceof StateTransactionLogEntry)
                            &&
                            (((StateTransactionLogEntry) last).getState()
                            .isPrepared())) {
                        xids.add(xid);
                    }
                }

            }
            result = (Xid[]) xids.toArray();
        }

        return result;
    }

    /**
     * Set the current transaction timeout value for this XAResource instance.
     *
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized void rollback(Xid id)
            throws XAException {
        //check the xid is not null
        if (id == null) {
            throw new XAException(XAException.XAER_NOTA);
        }

        // covert to our internal representation of an xid
        ExternalXid xid = new ExternalXid(id);

        // check to see that the xid actually exists
        if (!isTransactionActive(xid)) {
            throw new XAException(XAException.XAER_PROTO);
        }

        // process the data in that transaction. If it was a published message
        // then drop it. If it was a consumed message then return it back to
        // the destination.
        try {
            // retrieve a list of recrods for the specified global transaction
            // and process them. Ignore the state records and only process the
            // data records, which are of type TransacitonalObjectWrapper.
            Object[] records = getTransactionRecords(xid, _rid);
            for (int index = 0; index < records.length; index++) {
                if (records[index] instanceof TransactionalObjectWrapper) {
                    TransactionalObjectWrapper wrapper =
                            (TransactionalObjectWrapper) records[index];
                    if (wrapper.isPublishedMessage()) {
                        // we don't need to process these messages since the
                        // global transaction has been rolled back.
                    } else if (wrapper.isReceivedMessage()) {
                        ReceivedMessageWrapper rmsg_wrapper =
                                (ReceivedMessageWrapper) wrapper;
                        MessageHandle handle =
                                (MessageHandle) rmsg_wrapper.getObject();
                        JmsDestination dest = handle.getDestination();
                        DestinationCache cache =
                                _destinations.getDestinationCache(dest);
                        cache.returnMessageHandle(handle);
                    }
                } else {
                    // ignore since it is a state records.
                }
            }
        } catch (Exception exception) {
            throw new XAException("Failed in ResourceManager.rollback : " +
                                  exception.toString());
        } finally {
            // and now mark the transaction as closed
            try {
                logTransactionState(xid, TransactionState.CLOSED);
            } catch (Exception exception) {
                throw new XAException(
                        "Error processing rollback : " + exception);
            }
        }
    }

    /**
     * Start work on behalf of a transaction branch specified in xid If TMJOIN
     * is specified, the start is for joining a transaction previously seen by
     * the resource manager
     *
     * @throws XAException - if there is a problem completing the call
     */
    public synchronized boolean setTransactionTimeout(int seconds)
            throws XAException {
        _txExpiryTime = seconds;
        return true;
    }

    // implementation of XAResource.start
    public synchronized void start(Xid id, int flags)
            throws XAException {

        //check the xid is not null
        if (id == null) {
            throw new XAException(XAException.XAER_NOTA);
        }

        // covert to our internal representation of an xid
        ExternalXid xid = new ExternalXid(id);

        // check that the flags are valid for this method
        if ((flags != XAResource.TMNOFLAGS) ||
                (flags != XAResource.TMJOIN) ||
                (flags != XAResource.TMRESUME)) {
            throw new XAException(XAException.XAER_PROTO);
        }

        switch (flags) {
            case XAResource.TMNOFLAGS:
                // check to see that the xid does not already exist
                if (isTransactionActive(xid)) {
                    throw new XAException(XAException.XAER_DUPID);
                }

                // otherwise log the start of the transaction
                try {
                    logTransactionState(xid, TransactionState.OPENED);
                } catch (Exception exception) {
                    throw new XAException(
                            "Error processing start : " + exception);
                }
                break;

            case XAResource.TMJOIN:
            case XAResource.TMRESUME:
                // joining a transaction previously seen by the resource
                // manager
                if (!isTransactionActive(xid)) {
                    throw new XAException(XAException.XAER_PROTO);
                }
                break;
        }
    }

    /**
     * Return the resource manager identity
     *
     * @return the resource manager identity
     */
    public String getResourceManagerId() {
        return _rid;
    }

    /**
     * Create the next {@link TransactionLog} and add it to the list of managed
     * transaction logs.
     * <p/>
     * The method will throw ResourceManagerException if there is a problem
     * completing the request.
     *
     * @throws ResourceManagerException
     */
    protected TransactionLog createNextTransactionLog()
            throws ResourceManagerException {
        TransactionLog newlog = null;

        synchronized (_logs) {
            try {
                // get the last log number
                long last = 1;
                if (!_logs.isEmpty()) {
                    last
                            = getSequenceNumber(
                                    ((TransactionLog) _logs.last()).getName());
                }

                // now that we have the last log number, increment it and use
                // it to build the name of the next log file.
                String name = _logDirectory
                        + System.getProperty("file.separator") +
                        RM_LOGFILE_PREFIX + Long.toString(++last)
                        + RM_LOGFILE_EXTENSION;

                // create a transaction log and add it to the collection
                newlog = new TransactionLog(name, true);
                _logs.add(newlog);
            } catch (TransactionLogException exception) {
                throw new ResourceManagerException(
                        "Error in createNextTransactionLog " + exception);
            }
        }

        return newlog;
    }

    /**
     * Build a list of all log files in the specified log directory
     *
     * @throws IllegalArgumentException - if the directory does not exist.
     */
    protected void buildLogFileList() {
        File dir = new File(_logDirectory);
        if ((!dir.exists()) ||
                (!dir.isDirectory())) {
            throw new IllegalArgumentException(_logDirectory +
                                               " is not a directory");
        }

        try {
            File[] list = dir.listFiles(new FilenameFilter() {

                // implementation of FilenameFilter.accept
                public boolean accept(File dir, String name) {
                    boolean result = false;

                    if ((name.startsWith(RM_LOGFILE_PREFIX)) &&
                            (name.endsWith(RM_LOGFILE_EXTENSION))) {
                        result = true;
                    }

                    return result;
                }
            });

            // add the files to the list
            synchronized (_logs) {
                for (int index = 0; index < list.length; index++) {
                    _logs.add(new TransactionLog(list[index].getPath(), false));
                }
            }
        } catch (Exception exception) {
            // replace this with the exception strategy
            exception.printStackTrace();
        }

    }

    /**
     * This method will process all the transaction logs, in the log diretory
     * and call recover on each of them.
     *
     * @throws ResourceManagerException - if there is a problem recovering
     */
    private synchronized void recover()
            throws ResourceManagerException {
        try {
            if (!_logs.isEmpty()) {
                Iterator iter = _logs.iterator();
                while (iter.hasNext()) {
                    TransactionLog log = (TransactionLog) iter.next();
                    HashMap records = log.recover();
                }
            }
        } catch (Exception exception) {
            throw new ResourceManagerException("Error in recover " +
                                               exception.toString());
        }
    }

    /**
     * Retrieve the transaction log for the specified transaction id
     *
     * @param txid - the transaction identity
     * @return TransactionLog
     * @throws TransactionLogException  - if there is tx log exception
     * @throws ResourceManagerException - if there is a resource problem.
     */
    private TransactionLog getTransactionLog(ExternalXid txid)
            throws TransactionLogException, ResourceManagerException {
        TransactionLog log = (TransactionLog) _tridToLogCache.get(txid);
        if (log == null) {
            log = getCurrentTransactionLog();
            addTridLogEntry(txid, log);
        }

        return log;
    }

    /**
     * Get the current transaction log. It will check the last transaction log
     * opened by the resource manager and determine whether there is space
     * enough to process another transaction.
     * <p/>
     * If there is space enough then it will return that transaction, otherwise
     * it will create a new transaction log for the resource
     *
     * @return TransactionLog - the transaction log to use
     * @throws ResourceManagerException
     * @throws TransactionLogException
     */
    private TransactionLog getCurrentTransactionLog()
            throws TransactionLogException, ResourceManagerException {
        TransactionLog log = null;

        synchronized (_logs) {
            if (_logs.size() > 0) {
                log = (TransactionLog) _logs.last();
            }

            if ((log == null) ||
                    (log.size() > _logFileSize)) {
                log = createNextTransactionLog();
            }
        }

        return log;
    }

    /**
     * Add an entry to the trid log cache table for the specified trid and
     * transaction log mapping.
     *
     * @param trid - the transaction identifier
     * @param log  - the transaction log
     */
    private void addTridLogEntry(ExternalXid trid, TransactionLog log) {
        synchronized (_cacheLock) {
            // one to one relationship
            _tridToLogCache.put(trid, log);

            // one to many relationship
            Vector trids = (Vector) _logToTridCache.get(log);
            if (trids == null) {
                trids = new Vector();
                _logToTridCache.put(log, trids);
            }
            trids.addElement(trid);
        }
    }

    /**
     * Check whether the specified log is also the current log
     *
     * @param log - the log to check
     * @return boolean - true if it is
     */
    private boolean isCurrentTransactionLog(TransactionLog log) {
        boolean result = false;

        if (_logs.size() > 0) {
            result = log.equals(_logs.last());
        }

        return result;
    }

    /**
     * Remove an entry to the trid log cache table for the specified trid and
     * transaction log mapping.
     *
     * @param trid - the transaction identifier
     * @param log  - the transaction log
     */
    private void removeTridLogEntry(ExternalXid trid, TransactionLog log) {
        synchronized (_cacheLock) {

            // one to one relationship
            _tridToLogCache.remove(trid);

            // one to many relationship
            Vector trids = (Vector) _logToTridCache.get(log);
            if (trids != null) {
                trids.remove(trid);
                if (trids.size() == 0) {
                    _logToTridCache.remove(log);
                }
            }
        }
    }

    /**
     * Return an arrya of records, both state and date, for the specified global
     * transaction
     *
     * @param xid - the global transaction id
     * @param rid - the resource id
     * @return Object[] - array of records
     */
    protected Object[] getTransactionRecords(ExternalXid xid, String rid) {
        Object[] records;

        // we also want to add this to the transaction data for that
        // txid
        LinkedList list = (LinkedList) _activeTransactions.get(xid);
        if (list != null) {
            records = list.toArray();
        } else {
            records = new Object[0];
        }

        return records;
    }


    /**
     * Return the sequence number of the file files are associated with a unique
     * number
     *
     * @param name - the file name to investigate
     * @return long - the transaction log number
     * @throws ResourceManagerException
     */
    protected long getSequenceNumber(String name)
            throws ResourceManagerException {
        int start = name.indexOf(RM_LOGFILE_PREFIX) +
                RM_LOGFILE_PREFIX.length();
        int end = name.indexOf(RM_LOGFILE_EXTENSION);

        // the number must be between the start and end positions
        try {
            return Long.parseLong(name.substring(start, end));
        } catch (NumberFormatException exception) {
            throw new ResourceManagerException(
                    "Invalid name assigned to resource manager file " + name);
        }
    }

    /**
     * Return true if the specified transaction is active
     *
     * @param xid - the gobal transaction identifier
     */
    private synchronized boolean isTransactionActive(ExternalXid xid) {
        return _activeTransactions.containsKey(xid);
    }

    /**
     * Dump the specified records to the screen
     */
    private void dumpRecovered(HashMap records) {
        Iterator iter = records.keySet().iterator();
        while (iter.hasNext()) {
            ExternalXid txid = (ExternalXid) iter.next();
            LinkedList list = (LinkedList) records.get(txid);
            Iterator oiter = list.iterator();
            while (oiter.hasNext()) {
                Object object = oiter.next();
                if (object instanceof StateTransactionLogEntry) {
                    System.err.println(
                            "Recovered [" + txid + "] Class " +
                            object.getClass().getName()
                            + " ["
                            +
                            ((StateTransactionLogEntry) object).getState()
                            .toString()
                            + "]");
                } else {
                    System.err.println("Recovered [" + txid + "] Class " +
                                       object.getClass().getName());
                }
            }
        }
    }


    /**
     * Helper and type-safe method for creating a wrapper object for published
     * messages
     *
     * @param message - the message published
     * @return PublishedMessageWrapper
     */
    private PublishedMessageWrapper createPublishedMessageWrapper(
            MessageImpl message) {
        return new PublishedMessageWrapper(message);
    }

    /**
     * Helper and type-safe method for creating a wrapper object for received
     * messages
     *
     * @param id     - the identity of the consumer receiving the message
     * @param handle - the handle of the message received
     * @return ReceivedMessageWrapper
     */
    private ReceivedMessageWrapper createReceivedMessageWrapper(long id,
                                                                MessageHandle handle) {
        return new ReceivedMessageWrapper(id, handle);
    }

    /**
     * This functor is used by various collections to order the transaction log
     * files created by this resource manager. The resource manager will create
     * log files with sequentially increasing numbers (i.e xxx01.log, xxx2.log
     */
    private class TranLogFileComparator
            implements Comparator {

        // implementation of Comparator.comapre
        public int compare(Object o1, Object o2) {
            int result = -1;

            try {
                if ((o1 instanceof TransactionLog) &&
                        (o2 instanceof TransactionLog)) {
                    long seq1 = getSequenceNumber(
                            ((TransactionLog) o1).getName());
                    long seq2 = getSequenceNumber(
                            ((TransactionLog) o2).getName());

                    if (seq1 > seq2) {
                        result = 1;
                    } else if (seq1 < seq2) {
                        result = -1;
                    } else {
                        result = 0;
                    }
                } else {
                    throw new ClassCastException("o1 = " +
                                                 o1.getClass().getName() + " and o2 = " +
                                                 o2.getClass().getName());
                }
            } catch (Exception exception) {
                throw new RuntimeException("Error in ResourceManager.compare " +
                                           exception.toString());
            }

            return result;
        }

        // implementation of Comparator.equals
        public boolean equals(Object obj) {
            if (obj instanceof TranLogFileComparator) {
                return true;
            }

            return false;
        }
    }


    /**
     * This private member class is used to wrap the transactional object, which
     * for this particular resource manager is a published message or a received
     * message handle.
     */
    abstract private class TransactionalObjectWrapper {

        /**
         * The transactional object instance
         */
        private Object _object;

        /**
         * Create an instance of the wrapper using the type and the object
         *
         * @param object - the associated object
         */
        public TransactionalObjectWrapper(Object object) {
            _object = object;
        }

        /**
         * Check whether the wrapper contains a published message. Note that a
         * published message has a {@link MessageImpl} a the transactional
         * object.
         *
         * @return boolean - true if it is
         */
        public boolean isPublishedMessage() {
            return this instanceof PublishedMessageWrapper;
        }

        /**
         * Check whether the wrapper contains a received message handle. Note
         * that a received message contains a {@link MessageHandle} as the
         * transactional object.
         *
         * @return boolean - true if it does
         */
        public boolean isReceivedMessage() {
            return this instanceof ReceivedMessageWrapper;
        }

        /**
         * Return the transaction object
         *
         * @return Object
         */
        public Object getObject() {
            return _object;
        }

    }


    /**
     * This private member class is used to wrap a published message
     */
    private class PublishedMessageWrapper extends TransactionalObjectWrapper {

        /**
         * Create an instance of the wrapper using the specified message
         *
         * @param message - the message to wrap
         */
        public PublishedMessageWrapper(MessageImpl message) {
            super(message);
        }

        /**
         * Return an instance of the message object
         *
         * @return MessageImpl
         */
        public MessageImpl getMessage() {
            return (MessageImpl) super.getObject();
        }
    }


    /**
     * This private member class is used to wrap a received message
     */
    private class ReceivedMessageWrapper extends TransactionalObjectWrapper {

        /**
         * Caches the id of the {@link ConsumerEndpoint} that is processed this
         * handle
         */
        private long _consumerId;

        /**
         * Create an instance of the wrapper using the specified message
         *
         * @param id     - the identity of the consumer endpoint
         * @param handle - the handle to the message
         */
        public ReceivedMessageWrapper(long id, MessageHandle handle) {
            super(handle);
            _consumerId = id;
        }

        /**
         * Return a reference to the  consumer identity
         *
         * @return String
         */
        public long getConsumerId() {
            return _consumerId;
        }

        /**
         * Return an instance of the message handle
         *
         * @return MessageHandle
         */
        public MessageHandle getMessageHandle() {
            return (MessageHandle) super.getObject();
        }
    }

}
