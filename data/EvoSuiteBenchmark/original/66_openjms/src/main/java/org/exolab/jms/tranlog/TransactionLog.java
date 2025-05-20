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
 * Copyright 2001-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 *
 * $Id: TransactionLog.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
 *
 * Date			Author  Changes
 * 20/11/2001   jima    Created
 */
package org.exolab.jms.tranlog;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * The resource manager uses transaction logs to record the persistent records
 * for the resource manager in case of recovery. All records are logged
 * sequentially and each records has an associated XID. Log files have a finite
 * size, after which they are closed and a new log file is opened. There is
 * only one current transaction log file per resource manager.
 */
public class TransactionLog {

    /**
     * The name of this log file
     */
    private String _name = null;

    /**
     * Maintains the running total of the file size
     */
    private long _size = 0;

    /**
     * Cache the DataOutputStream handle
     */
    private transient DataOutputStream _dos = null;

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(TransactionLog.class);


    /**
     * Create a transaction log with the specified name, which includes the
     * directory it will reside in. If the create flag is true then it will
     * create the log file. If the create flag is false then it will assume
     * that the log file already exists and will attempt to open it.
     * <p>
     * Attempting to create a file that already exists or open a non-exisitent
     * log file with throw the TransactionLogException exception.
     *
     * @param name - the name of the transaction log absolute or final
     * @param vreate - flag inidicating whether to open or create the log
     * @throws TransactionLogException
     */
    public TransactionLog(String name, boolean create)
        throws TransactionLogException {
        if ((name == null) ||
            (name.length() == 0)) {
            throw new IllegalArgumentException("Can't specify a null or empty name");
        }

        _name = name;
        File file = new File(name);

        // check if the file needs to be created and whether it already
        // exists.
        if (create) {
            if (file.exists()) {
                throw new TransactionLogException(name +
                    " already exists");
            } else {
                try {
                    (new FileOutputStream(file)).close();
                } catch (Exception exception) {
                    // rethrow the exception
                    throw new TransactionLogException(
                        "Failed to create the log file " + name + " b/c" +
                        exception);
                }
            }
        } else {
            // check to see if a file needs to be open and that it actually
            // exists.
            if (!file.exists()) {
                throw new TransactionLogException(name + " does not exists");
            }
        }

        // set the size of the file
        _size = (new File(name)).length();
    }

    /**
     * Return the name of the transaction log file
     *
     * @return String
     */
    public String getName() {
        return _name;
    }

    /**
     * Add an {@link StateTransactionLogEntry} using the specified txid,
     * rid and state
     *
     * @param txid - the transaction identifier
     * @param expiry - expiry time for the transaction
     * @param rid - the resource identifier
     * @param state - the transaction log state
     * @throws TransactionLogException - if the entry cannot be created
     */
    public synchronized void logTransactionState(ExternalXid txid, long expiry,
                                                 String rid,
                                                 TransactionState state)
        throws TransactionLogException {
        try {
            StateTransactionLogEntry entry = new StateTransactionLogEntry(txid, rid);
            entry.setState(state);
            entry.setExpiryTime(expiry);

            DataOutputStream dos = getOutputStream();
            byte[] blob = SerializationHelper.serialize(entry);
            dos.writeLong(blob.length);
            dos.write(blob, 0, blob.length);
            dos.flush();

            // update the size
            _size += blob.length;
        } catch (Exception exception) {
            throw new TransactionLogException("Error in logTransactionState " +
                exception.toString());
        }
    }

    /**
     * Add an {@link DataTransactionLogEntry} using the specified txid,
     * rid and data
     *
     * @param txid - the transaction identifier
     * @param expiry - transaction expiry time
     * @param rid - the resource identifier
     * @param data - the opaque data to write
     * @throws TransactionLogException - if the entry cannot be created
     */
    public synchronized void logTransactionData(ExternalXid txid, long expiry, String rid,
                                                Object data)
        throws TransactionLogException {
        try {
            DataTransactionLogEntry entry = new DataTransactionLogEntry(txid, rid);
            entry.setData(data);
            entry.setExpiryTime(expiry);

            DataOutputStream dos = getOutputStream();
            byte[] blob = SerializationHelper.serialize(entry);
            dos.writeLong(blob.length);
            dos.write(blob, 0, blob.length);
            dos.flush();

            // update the size
            _size += blob.length;
        } catch (Exception exception) {
            throw new TransactionLogException("Error in logTransactionData " +
                exception.toString());
        }
    }

    /**
     * Close the transaction log
     *
     * @throws TransactionLogException - if it fails to close the log
     */
    public void close()
        throws TransactionLogException {
        try {
            if (_dos != null) {
                _dos.close();
            }
        } catch (IOException exception) {
            throw new TransactionLogException("Error in close " +
                exception.toString());
        }
    }

    /**
     * Return the size of the transaction log file.
     *
     * @return long - the length of the file
     */
    public long size() {
        return _size;
    }

    /**
     * Force a recovery of this log file. This will close the output file stream
     * if one is opened and then read each entry from the log file and send it to
     * the specified listener, if one is allocated.
     * <p>
     * The returned data structure is a HashMap, where the key is a
     * {@link ExternalXid} and the entries are LinkedList of {@link
     * BaseTransactionLogEntry} objects
     *
     * @return HashMap - a list of open transactions
     * @throws TransactionLogException - if there is a prob recovering
     */
    public synchronized HashMap recover()
        throws TransactionLogException {
        return getOpenTransactionList();
    }

    /**
     * Check if we can garbage collect this transaction log. It will go through
     * the log file and check to see whether there are any open transaction. If
     * there are no open transactions the it is a candidate for garage collection
     *
     * @return boolean - true if we can garbage collect; false otherwise
     */
    public synchronized boolean canGarbageCollect() {
        boolean result = false;

        try {
            HashMap records = getOpenTransactionList();
            if (records.size() == 0) {
                result = true;
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        return result;
    }

    /**
     * Destroy this transaction log, which basically removes it from the
     * file system
     *
     * @throws TransactionLogException
     */
    public synchronized void destroy()
        throws TransactionLogException {
        try {
            close();
            if (!(new File(_name)).delete()) {
                _log.error("Failed to destroy " + _name);
            }
        } catch (Exception exception) {
            throw new TransactionLogException("Error in destroy " +
                exception.toString());

        }
    }

    // override Object.equals
    public boolean equals(Object obj) {
        boolean result = false;

        if ((obj instanceof TransactionLog) &&
            (((TransactionLog) obj)._name.equals(_name))) {
            result = true;
        }

        return result;
    }

    /**
     * Return an instance of the output stream. If one does not exist then
     * create it.
     *
     * @return DataOutputStream - the output stream
     */
    private DataOutputStream getOutputStream()
        throws IOException, FileNotFoundException {
        if (_dos == null) {
            _dos = new DataOutputStream(
                new BufferedOutputStream(
                    new FileOutputStream(_name, true)));
        }

        return _dos;
    }

    /**
     * Return a list of open transactions in a HashMap. The key is the transaction
     * id and the data is a vector of associated data records in a LinkedList
     *
     * @return HashMap
     * @throws TransactionLogException - if there is a prob recovering
     */
    private HashMap getOpenTransactionList()
        throws TransactionLogException {

        HashMap records = new HashMap();

        // if the output stream is opened then close it
        try {
            if (_dos != null) {
                _dos.close();
                _dos = null;
            }
        } catch (Exception exception) {
            throw new TransactionLogException("Error in recover " +
                exception.toString());
        }


        FileInputStream fis = null;
        try {
            fis = new FileInputStream(_name);
            DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));

            while (dis.available() > 0) {
                byte[] blob = new byte[(int) dis.readLong()];
                dis.readFully(blob);
                Object object = SerializationHelper.deserialize(blob);
                if (object instanceof StateTransactionLogEntry) {
                    StateTransactionLogEntry state = (StateTransactionLogEntry) object;
                    LinkedList list = null;
                    switch (state.getState().getOrd()) {
                        case TransactionState.OPENED_ORD:
                            if (records.containsKey(state.getExternalXid())) {
                                _log.error("OPENED_ORD : Transaction log is inconsistent");
                                continue;
                            }

                            list = new LinkedList();
                            records.put(state.getExternalXid(), list);
                            list.add(state);
                            break;

                        case TransactionState.PREPARED_ORD:
                            list = (LinkedList) records.get(state.getExternalXid());
                            if (list == null) {
                                _log.error("PREPARED_ORD : Transaction log is inconsistent");
                                continue;
                            }

                            list.add(state);
                            break;

                        case TransactionState.CLOSED_ORD:
                            if (records.get(state.getExternalXid()) == null) {
                                _log.error("CLOSED_ORD : Transaction log is inconsistent");
                                continue;
                            }

                            records.remove(state.getExternalXid());
                            break;

                        default:
                            break;
                    }
                } else if (object instanceof DataTransactionLogEntry) {
                    DataTransactionLogEntry data = (DataTransactionLogEntry) object;
                    LinkedList list = (LinkedList) records.get(data.getExternalXid());
                    if (list == null) {
                        _log.error("DATA : Transaction log is inconsistent");
                        continue;
                    }

                    list.add(data);
                } else {
                    System.err.println("There is no support for log entry " +
                        "records of type " + object.getClass().getName());
                }

            }
        } catch (Exception exception) {
            throw new TransactionLogException("Error in recover " +
                exception.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception exception) {
                    throw new TransactionLogException("Error in recover " +
                        exception.toString());
                }
            }
        }

        return records;

    }

} //-- TransactionLog
