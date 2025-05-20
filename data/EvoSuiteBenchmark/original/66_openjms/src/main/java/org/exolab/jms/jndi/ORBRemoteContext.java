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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ORBRemoteContext.java,v 1.1 2005/11/18 03:29:41 tanderson Exp $
 */
package org.exolab.jms.jndi;

import java.util.Hashtable;
import java.util.NoSuchElementException;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.codehaus.spice.jndikit.RemoteContext;
import org.exolab.jms.net.proxy.Proxy;


/**
 * <code>Context</code> implementation that reference counts the
 * underlying provider.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/11/18 03:29:41 $
 */
class ORBRemoteContext implements Context {

    /**
     * Environment key for the naming provider reference counter.
     */
    private static String REFERENCE_KEY = "NamingProviderReferenceCounter";

    /**
     * The context.
     */
    private RemoteContext _context;


    /**
     * Construct a new <code>ORBRemoteContext</code>.
     *
     * @param context the context to delegate requests to
     * @throws NamingException for any error
     */
    public ORBRemoteContext(RemoteContext context) throws NamingException {
        _context = context;
        reference();
    }

    /**
     * Retrieves the named object.
     *
     * @param name the name of the object to look up
     * @return	the object bound to <tt>name</tt>
     * @throws	NamingException if a naming exception is encountered
     */
    public Object lookup(Name name) throws NamingException {
        return wrap(_context.lookup(name));
    }

    /**
     * Retrieves the named object.
     *
     * @param name the name of the object to look up
     * @return	the object bound to <tt>name</tt>
     * @throws	NamingException if a naming exception is encountered
     */
    public Object lookup(String name) throws NamingException {
        return wrap(_context.lookup(name));
    }

    /**
     * Binds a name to an object.
     *
     * @param name the name to bind; may not be empty
     * @param obj  the object to bind; possibly null
     * @throws	NamingException if a naming exception is encountered
     */
    public void bind(Name name, Object obj) throws NamingException {
        _context.bind(name, obj);
    }

    /**
     * Binds a name to an object.
     *
     * @param name the name to bind; may not be empty
     * @param obj  the object to bind; possibly null
     * @throws	NamingException if a naming exception is encountered
     */
    public void bind(String name, Object obj) throws NamingException {
        _context.bind(name, obj);
    }

    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name the name to bind; may not be empty
     * @param obj  the object to bind; possibly null
     * @throws	NamingException if a naming exception is encountered
     */
    public void rebind(Name name, Object obj) throws NamingException {
        _context.rebind(name, obj);
    }

    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name the name to bind; may not be empty
     * @param obj  the object to bind; possibly null
     * @throws	NamingException if a naming exception is encountered
     */
    public void rebind(String name, Object obj) throws NamingException {
        _context.rebind(name, obj);
    }

    /**
     * Unbinds the named object.
     *
     * @param name the name to unbind; may not be empty
     * @throws	NamingException if a naming exception is encountered
     */
    public void unbind(Name name) throws NamingException {
        _context.unbind(name);
    }

    /**
     * Unbinds the named object.
     *
     * @param name the name to unbind; may not be empty
     * @throws	NamingException if a naming exception is encountered
     */
    public void unbind(String name) throws NamingException {
        _context.unbind(name);
    }

    /**
     * Binds a new name to the object bound to an old name, and unbinds
     * the old name.
     *
     * @param oldName the name of the existing binding; may not be empty
     * @param newName the name of the new binding; may not be empty
     * @throws	NamingException if a naming exception is encountered
     */
    public void rename(Name oldName, Name newName) throws NamingException {
        _context.rename(oldName, newName);
    }

    /**
     * Binds a new name to the object bound to an old name, and unbinds
     * the old name.
     *
     * @param oldName the name of the existing binding; may not be empty
     * @param newName the name of the new binding; may not be empty
     * @throws	NamingException if a naming exception is encountered
     */
    public void rename(String oldName, String newName) throws NamingException {
        _context.rename(oldName, newName);
    }

    /**
     * Enumerates the names bound in the named context, along with the
     * class names of objects bound to them.
     *
     * @param name the name of the context to list
     * @return	an enumeration of the names and class names of the
     * bindings in this context.  Each element of the
     * enumeration is of type <tt>NameClassPair</tt>.
     * @throws	NamingException if a naming exception is encountered
     */
    public NamingEnumeration list(Name name) throws NamingException {
        return _context.list(name);
    }

    /**
     * Enumerates the names bound in the named context, along with the
     * class names of objects bound to them.
     *
     * @param name the name of the context to list
     * @return	an enumeration of the names and class names of the
     * bindings in this context.  Each element of the
     * enumeration is of type <tt>NameClassPair</tt>.
     * @throws	NamingException if a naming exception is encountered
     */
    public NamingEnumeration list(String name) throws NamingException {
        return _context.list(name);
    }

    /**
     * Enumerates the names bound in the named context, along with the
     * objects bound to them.
     *
     * @param name the name of the context to list
     * @return	an enumeration of the bindings in this context.
     * Each element of the enumeration is of type
     * <tt>Binding</tt>.
     * @throws	NamingException if a naming exception is encountered
     */
    public NamingEnumeration listBindings(Name name) throws NamingException {
        return new ORBNamingEnumeration(_context.listBindings(name));
    }

    /**
     * Enumerates the names bound in the named context, along with the
     * objects bound to them.
     *
     * @param name the name of the context to list
     * @return	an enumeration of the bindings in this context.
     * Each element of the enumeration is of type
     * <tt>Binding</tt>.
     * @throws	NamingException if a naming exception is encountered
     */
    public NamingEnumeration listBindings(String name) throws NamingException {
        return _context.listBindings(name);
    }

    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name the name of the context to be destroyed; may not be empty
     * @throws	NamingException if a naming exception is encountered
     */
    public void destroySubcontext(Name name) throws NamingException {
        _context.destroySubcontext(name);
    }

    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name the name of the context to be destroyed; may not be empty
     * @throws	NamingException if a naming exception is encountered
     */
    public void destroySubcontext(String name) throws NamingException {
        _context.destroySubcontext(name);
    }

    /**
     * Creates and binds a new context.
     *
     * @param name the name of the context to create; may not be empty
     * @return	the newly created context
     * @throws	NamingException if a naming exception is encountered
     */
    public Context createSubcontext(Name name) throws NamingException {
        return (Context) wrap(_context.createSubcontext(name));
    }

    /**
     * Creates and binds a new context.
     *
     * @param name the name of the context to create; may not be empty
     * @return	the newly created context
     * @throws	NamingException if a naming exception is encountered
     */
    public Context createSubcontext(String name) throws NamingException {
        return (Context) wrap(_context.createSubcontext(name));
    }

    /**
     * Retrieves the named object, following links except
     * for the terminal atomic component of the name.
     *
     * @param name the name of the object to look up
     * @return	the object bound to <tt>name</tt>, not following the
     * terminal link (if any).
     * @throws	NamingException if a naming exception is encountered
     */
    public Object lookupLink(Name name) throws NamingException {
        return wrap(_context.lookupLink(name));
    }

    /**
     * Retrieves the named object, following links except
     * for the terminal atomic component of the name.
     *
     * @param name the name of the object to look up
     * @return	the object bound to <tt>name</tt>, not following the
     * terminal link (if any)
     * @throws	NamingException if a naming exception is encountered
     */
    public Object lookupLink(String name) throws NamingException {
        return wrap(_context.lookupLink(name));
    }

    /**
     * Retrieves the parser associated with the named context.
     *
     * @param name the name of the context from which to get the parser
     * @return	a name parser that can parse compound names into their atomic
     * components
     * @throws	NamingException if a naming exception is encountered
     */
    public NameParser getNameParser(Name name) throws NamingException {
        return _context.getNameParser(name);
    }

    /**
     * Retrieves the parser associated with the named context.
     *
     * @param name the name of the context from which to get the parser
     * @return	a name parser that can parse compound names into their atomic
     * components
     * @throws	NamingException if a naming exception is encountered
     */
    public NameParser getNameParser(String name) throws NamingException {
        return _context.getNameParser(name);
    }

    /**
     * Composes the name of this context with a name relative to
     * this context.
     *
     * @param name   a name relative to this context
     * @param prefix the name of this context relative to one of its ancestors
     * @return	the composition of <code>prefix</code> and <code>name</code>
     * @throws	NamingException if a naming exception is encountered
     */
    public Name composeName(Name name, Name prefix) throws NamingException {
        return _context.composeName(name, prefix);
    }

    /**
     * Composes the name of this context with a name relative to
     * this context.
     *
     * @param name   a name relative to this context
     * @param prefix the name of this context relative to one of its ancestors
     * @return	the composition of <code>prefix</code> and <code>name</code>
     * @throws	NamingException if a naming exception is encountered
     */
    public String composeName(String name, String prefix)
            throws NamingException {
        return _context.composeName(name, prefix);
    }

    /**
     * Adds a new environment property to the environment of this
     * context.  If the property already exists, its value is overwritten.
     *
     * @param propName the name of the environment property to add; may not be null
     * @param propVal  the value of the property to add; may not be null
     * @return	the previous value of the property, or null if the property was
     * not in the environment before
     * @throws	NamingException if a naming exception is encountered
     */
    public Object addToEnvironment(String propName, Object propVal)
            throws NamingException {
        return _context.addToEnvironment(propName, propVal);
    }

    /**
     * Removes an environment property from the environment of this
     * context.  See class description for more details on environment
     * properties.
     *
     * @param propName the name of the environment property to remove; may not be null
     * @return	the previous value of the property, or null if the property was
     * not in the environment
     * @throws	NamingException if a naming exception is encountered
     */
    public Object removeFromEnvironment(String propName)
            throws NamingException {
        return _context.removeFromEnvironment(propName);
    }

    /**
     * Retrieves the environment in effect for this context.
     *
     * @return	the environment of this context; never null
     * @throws	NamingException if a naming exception is encountered
     */
    public Hashtable getEnvironment() throws NamingException {
        return _context.getEnvironment();
    }

    /**
     * Closes this context.
     *
     * @throws	NamingException if a naming exception is encountered
     */
    public void close() throws NamingException {
        if (_context != null) {
            if (dereference() <= 0) {
                Object provider = getEnvironment().get(
                        RemoteContext.NAMING_PROVIDER);
                if (provider instanceof Proxy) {
                    ((Proxy) provider).disposeProxy();
                }
            }
            _context.close();
            _context = null;
        }
    }

    /**
     * Retrieves the full name of this context within its own namespace.
     *
     * @return	this context's name in its own namespace; never null
     * @throws	NamingException if a naming exception is encountered
     */
    public String getNameInNamespace() throws NamingException {
        return _context.getNameInNamespace();
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     *
     * @throws Throwable the <code>Exception</code> raised by this method
     */
    protected void finalize() throws Throwable {
        close();
    }

    /**
     * Wrap the supplied object in an <code>ORBRemoteContext</code> iff it
     * is an instance of <code>RemoteContext</code>, otherwise returns the
     * object unchanged.
     *
     * @param object the object to wrap
     * @return the supplied object in an <code>ORBRemoteContext</code> iff it
     * is an instance of <code>RemoteContext</code>, otherwise returns the
     * object unchanged.
     * @throws NamingException if a naming exception is encountered
     */
    private Object wrap(Object object) throws NamingException {
        if (object instanceof RemoteContext) {
            return new ORBRemoteContext((RemoteContext) object);
        }
        return object;
    }

    /**
     * Increment the reference count of the provider. This is the number
     * of <code>Context</code> instances that refer to it.
     *
     * @throws NamingException for any naming error
     */
    private void reference() throws NamingException {
        Ref ref = (Ref) _context.getEnvironment().get(REFERENCE_KEY);
        if (ref == null) {
            ref = new Ref();
            _context.addToEnvironment(REFERENCE_KEY, ref);
        }
        ref.inc();
    }

    /**
     * Dereference the reference count of the provider.
     *
     * @return the number of references
     * @throws NamingException for any naming error
     */
    private int dereference() throws NamingException {
        Ref ref = (Ref) _context.getEnvironment().get(REFERENCE_KEY);
        return (ref != null) ? ref.dec() : 0;
    }


    /**
     * Helper to wrap any <code>RemoteContext</code> instances returned
     * by a <code>NamingEnumeration</code> in <code>ORBRemoteContext</code>
     * instances.
     */
    private static class ORBNamingEnumeration implements NamingEnumeration {

        /**
         * The enumeration.
         */
        private final NamingEnumeration _iterator;

        /**
         * Construct a new <code>ORBNamingEnumeration</code>.
         *
         * @param iterator the enumeration to delegate to
         */
        private ORBNamingEnumeration(NamingEnumeration iterator) {
            _iterator = iterator;
        }

        /**
         * Retrieves the next element in the enumeration.
         *
         * @return the next element in the enumeration.
         * @throws NamingException for any naming error
         * @throws java.util.NoSuchElementException If attempting to get the next element when none is available.
         */
        public Object next() throws NamingException {
            return wrap(_iterator.next());
        }

        /**
         * Determines whether there are any more elements in the enumeration.
         *
         * @throws NamingException for any naming error.
         * @return		true if there is more in the enumeration; false otherwise.
         */
        public boolean hasMore() throws NamingException {
            return _iterator.hasMore();
        }

        /**
         * Closes this enumeration.
         *
         * @throws NamingException for any naming error
         */
        public void close() throws NamingException {
            _iterator.close();
        }

        /**
         * Tests if this enumeration contains more elements.
         *
         * @return <code>true</code> if and only if this enumeration object
         *         contains at least one more element to provide;
         *         <code>false</code> otherwise.
         */
        public boolean hasMoreElements() {
            return _iterator.hasMoreElements();
        }

        /**
         * Returns the next element of this enumeration if this enumeration
         * object has at least one more element to provide.
         *
         * @return the next element of this enumeration.
         * @throws java.util.NoSuchElementException if no more elements exist.
         */
        public Object nextElement() {
            try {
                return wrap(_iterator.nextElement());
            } catch (NamingException exception) {
                throw new NoSuchElementException(exception.getMessage());
            }
        }

        /**
         * Wraps any <code>Context</code> instances in <code>ORBRemoteContext</code>.
         *
         * @param obj the object
         * @return obj
         * @throws NamingException for any naming error
         */
        private Object wrap(Object obj) throws NamingException {
            if (obj instanceof Binding) {
                Binding binding = (Binding) obj;
                Object bound = binding.getObject();
                if (bound instanceof RemoteContext) {
                    binding.setObject(
                            new ORBRemoteContext((RemoteContext) bound));
                }
            }
            return obj;
        }
    }

    /**
     * Helper to maintain a reference count.
     */
    private static class Ref {

        /**
         * The reference count.
         */
        private int _count;

        /**
         * Increment the reference count.
         *
         * @return the reference count
         */
        public synchronized int inc() {
            return ++_count;
        }

        /**
         * Decrement the reference count.
         *
         * @return the reference count
         */
        public synchronized int dec() {
            return --_count;
        }

    }

}
