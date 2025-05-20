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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ProxyGeneratorTask.java,v 1.3 2005/05/07 14:01:44 tanderson Exp $
 */
package org.exolab.jms.plugins.proxygen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.util.FileNameMapper;
import org.apache.tools.ant.util.GlobPatternMapper;
import org.apache.tools.ant.util.SourceFileScanner;


/**
 * Ant task to generate proxies.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/05/07 14:01:44 $
 */
public class ProxyGeneratorTask extends MatchingTask {

    /**
     * The base directory to store compiled classes.
     */
    private File _base;

    /**
     * The base directory to store generated sources. If not set, defaults to
     * _base.
     */
    private File _sourceBase;

    /**
     * The class name to generate source for.
     */
    private String _classname;

    /**
     * A set of class names for adapters of Throwable.
     */
    private List _adapters = new ArrayList();

    /**
     * Indicates whether source should be compiled with debug information;
     * defaults to off.
     */
    private boolean _debug = false;

    /**
     * Compile class path.
     */
    private Path _compileClasspath;


    /**
     * Construct a new <code>ProxyGeneratorTask</code>.
     */
    public ProxyGeneratorTask() {
    }

    /**
     * Sets the location to store compiled classes; required.
     *
     * @param base the base directory
     */
    public void setBase(File base) {
        _base = base;
    }

    /**
     * Returns the base directory to compiled classes.
     *
     * @return the base directory to compiled classes
     */
    public File getBase() {
        return _base;
    }

    /**
     * Sets the the class to run this task against; optional.
     *
     * @param classname the class name
     */
    public void setClassname(String classname) {
        _classname = classname;
    }

    /**
     * Returns the class name to compile.
     *
     * @return the class name to compile
     */
    public String getClassname() {
        return _classname;
    }

    /**
     * Sets list of ThrowableAdapter clases to use.
     *
     * @param adapters a comma-separated list of ThrowableAdapter class names
     */
    public void setAdapters(String adapters) {
        StringTokenizer tokens = new StringTokenizer(adapters,  ",");
        while (tokens.hasMoreTokens()) {
            String classname = tokens.nextToken();
            Adapter adapter = new Adapter();
            adapter.setClassname(classname);
            addConfiguredAdapter(adapter);
        }
    }

    /**
     * Add a nested adapter class name.
     *
     * @param adapter the adapter
     */
    public void addConfiguredAdapter(Adapter adapter) {
        _adapters.add(adapter.getClassname());
    }

    /**
     * Optional directory to save generated source files to.
     *
     * @param sourceBase the directory to save generated source files to
     */
    public void setSourceBase(File sourceBase) {
        _sourceBase = sourceBase;
    }

    /**
     * Returns the directory to save generated source files to.
     *
     * @return the directory to save generated source files to
     */
    public File getSourceBase() {
        return _sourceBase;
    }


    /**
     * Indicates whether source should be compiled with debug information;
     * defaults to off.
     *
     * @param debug if <code>true</code> enable debug information
     */
    public void setDebug(boolean debug) {
        _debug = debug;
    }

    /**
     * Returns the debug flag.
     *
     * @return the debug flag
     */
    public boolean getDebug() {
        return _debug;
    }

    /**
     * Set the classpath to be used for this compilation.
     *
     * @param classpath the classpath
     */
    public void setClasspath(Path classpath) {
        if (_compileClasspath == null) {
            _compileClasspath = classpath;
        } else {
            _compileClasspath.append(classpath);
        }
    }

    /**
     * Creates a nested classpath element.
     *
     * @return a nested classpath element
     */
    public Path createClasspath() {
        if (_compileClasspath == null) {
            _compileClasspath = new Path(project);
        }
        return _compileClasspath.createPath();
    }

    /**
     * Adds to the classpath a reference to a &lt;path&gt; defined elsewhere.
     *
     * @param reference the reference
     */
    public void setClasspathRef(Reference reference) {
        createClasspath().setRefid(reference);
    }

    /**
     * Returns the classpath.
     *
     * @return the classpath
     */
    public Path getClasspath() {
        return _compileClasspath;
    }

    /**
     * Execute the task.
     *
     * @throws BuildException if the build fails
     */
    public void execute() throws BuildException {
        if (_base == null) {
            throw new BuildException("base attribute must be set!", location);
        }
        if (!_base.exists()) {
            throw new BuildException("base does not exist", location);
        }
        if (!_base.isDirectory()) {
            throw new BuildException("base is not a directory", location);
        }

        if (_sourceBase != null) {
            if (!_sourceBase.exists()) {
                throw new BuildException("sourceBase does not exist",
                                         location);
            }
            if (!_sourceBase.isDirectory()) {
                throw new BuildException("sourceBase is not a directory",
                                         location);
            }
        } else {
            _sourceBase = _base;
        }

        String[] files;
        String[] generateList;

        if (_classname == null) {
            // scan base dir to build up generation lists only if a
            // specific classname is not given
            DirectoryScanner scanner = getDirectoryScanner(_base);
            files = scanner.getIncludedFiles();
        } else {
            // otherwise perform a timestamp comparison - at least
            files = new String[]{
                _classname.replace('.', File.separatorChar) + ".class"};
        }
        generateList = scanDir(files);

        int count = generateList.length;
        if (count > 0) {
            log("Generating " + count + " prox" + (count > 1 ? "ies" : "y")
                + " to " + _base, Project.MSG_INFO);

            Path classpath = getCompileClasspath();
            AntClassLoader loader = new AntClassLoader(project, classpath);

            for (int i = 0; i < count; ++i) {
                generate(generateList[i], loader);
            }

            Javac javac = new Javac();
            javac.setProject(project);
            javac.createSrc().setLocation(_sourceBase);
            javac.setDestdir(_base);
            javac.setDebug(_debug);
            javac.setClasspath(classpath);
            javac.execute();
        }
    }

    /**
     * Helper class for specifying nested ThrowableAdapter implementations.
     */
    public static final class Adapter {

        /**
         * The class name.
         */
        private String _classname;

        /**
         * Sets the adapter class name.
         *
         * @param classname the adapter class name
         */
        public void setClassname(String classname) {
            _classname = classname;
        }

        /**
         * Returns the adapter class name.
         *
         * @return the adapter class name
         */
        public String getClassname() {
            return _classname;
        }
    }

    /**
     * Generate the proxy source.
     *
     * @param classname the name of the class
     * @param loader    the classloader to locate the class and its
     *                  dependencies
     * @return the path of the generated source
     * @throws BuildException if the source generation fails
     */
    protected String generate(String classname, ClassLoader loader)
            throws BuildException {

        String path = classname.replace('.', File.separatorChar)
                + "__Proxy.java";
        File file = new File(_sourceBase, path);
        File parent = file.getParentFile();
        if (parent.exists()) {
            if (!parent.isDirectory()) {
                throw new BuildException("Cannot generate sources to "
                                         + parent
                                         + ": path is not a directory", location);
            }
        } else if (!parent.mkdirs()) {
            throw new BuildException("Failed to create directory " + parent,
                                     location);
        }

        log("Generating proxy " + file, Project.MSG_DEBUG);

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            Class clazz = loader.loadClass(classname);
            Class[] adapters = getAdapters(loader);
            ProxyGenerator generator = new ProxyGenerator(clazz, adapters);
            generator.generate(stream);
            stream.close();
        } catch (ClassNotFoundException exception) {
            throw new BuildException("proxygen failed - class not found: "
                                     + exception.getMessage(), exception,
                                     location);
        } catch (IOException exception) {
            throw new BuildException(
                    "proxygen failed - I/O error: " + exception.getMessage(),
                    exception, location);
        } catch (Exception exception) {
            throw new BuildException(
                    "proxygen failed: " + exception.getMessage(),
                    exception, location);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException exception) {
                    throw new BuildException("proxygen failed - I/O error: "
                                             + exception.getMessage(),
                                             exception, location);
                }
            }
        }
        return path;
    }

    /**
     * Returns the class path for compilation.
     *
     * @return the compilation classpath
     */
    protected Path getCompileClasspath() {
        Path classpath = new Path(project);

        // add dest dir to classpath so that previously compiled and
        // untouched classes are on classpath
        classpath.setLocation(_base);

        if (getClasspath() == null) {
            classpath.addExisting(Path.systemClasspath);
        } else {
            classpath.addExisting(getClasspath().concatSystemClasspath("last"));
        }

        return classpath;
    }

    /**
     * Scans the base directory looking for class files to generate proxies
     * for.
     *
     * @param files the file paths
     * @return a list of class names to generate proxies for
     */
    protected String[] scanDir(String[] files) {
        ArrayList result = new ArrayList();
        String[] newFiles = files;

        SourceFileScanner scanner = new SourceFileScanner(this);
        FileNameMapper mapper = new GlobPatternMapper();
        mapper.setFrom("*.class");
        mapper.setTo("*__Proxy.java");
        newFiles = scanner.restrict(files, _base, _sourceBase, mapper);

        for (int i = 0; i < newFiles.length; i++) {
            String classname = newFiles[i].replace(File.separatorChar, '.');
            classname = classname.substring(0, classname.lastIndexOf(".class"));
            result.add(classname);
        }
        return (String[]) result.toArray(new String[0]);
    }

    /**
     * Returns the adapter classes.
     *
     * @param loader the class loader to use
     * @return the adapter classes
     * @throws ClassNotFoundException if an adapter class can't be found
     */
    private Class[] getAdapters(ClassLoader loader)
            throws ClassNotFoundException {
        Class[] result = new Class[_adapters.size()];
        Iterator iterator = _adapters.iterator();
        for (int i = 0; iterator.hasNext(); ++i) {
            String classname = (String) iterator.next();
            Class adapter = loader.loadClass(classname);
            result[i] = adapter;
        }
        return result;
    }
}

