/*
 * $Id: VelocityWriter.java,v 1.1 2004/05/03 21:41:31 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/VelocityWriter.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.tools;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Uses Velocity templates in the conf/ subdirectory to build Java source
 * files from populated beans. Used by the CodeGenerator tool and the
 * LegacyAdapterGenerator tools.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class VelocityWriter {

    /** Offset from the project basedir to the template directory */
    private final static String DEFAULT_TEMPLATEDIR = "conf";
    /** Offset from the project basedir to the src-gen directory */
    private final static String DEFAULT_BASEDIR = "src-gen";
    /** Default suffix for generated file */
    private final static String DEFAULT_SUFFIX = "java";

    private String templateDir = DEFAULT_TEMPLATEDIR;
    private String baseDir = DEFAULT_BASEDIR;
    private String suffix = DEFAULT_SUFFIX;

    /**
     * Builds a default VelocityWriter object. Override the defaults with
     * appropriate setXXX() calls.
     */
    public VelocityWriter() {;}

    /**
     * Returns the template directory for this object.
     * @return the template directory.
     */
    public String getTemplateDir() {
        return templateDir;
    }

    /**
     * Set the template directory for this object.
     * @param templateDir the template directory to set.
     */
    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    /**
     * Return the base directory for this object.
     * @return the base directory.
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * Set the base directory for this object.
     * @param basedir the base directory to set.
     */
    public void setBaseDir(String basedir) {
        this.baseDir = baseDir;
    }

    /**
     * Return the suffix for the generated file.
     * @return the generated file suffix.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Set the suffix for the generated file.
     * @param suffix the generated file suffix to set.
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Instantiates a Velocity engine to write the bean to a file.
     * @param generatedBeanFqcn the fully qualified class name of the bean
     * to be generated.
     * @param templateBean the bean containing values to be fed to the template.
     * @exception Exception if any was thrown.
     */
    public void write(String generatedBeanFqcn, Object templateBean) 
            throws Exception {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put(getUnqualifiedClassName(templateBean), templateBean);
        Template codeTemplate = 
            Velocity.getTemplate(getTemplateFileName(templateBean));
        PrintWriter writer = getWriter(generatedBeanFqcn);
        codeTemplate.merge(context, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Returns the template file name. By convention, we name the template
     * file as an all underscore version of the object. For example, the
     * template file for JavaSource.java will be javasource.vm. Templates
     * are stored in the conf/ subdirectory.
     * @param bean the bean object that drives the template.
     * @return the template file name corresponding to the bean.
     * @exception Exception if any are thrown from the method.
     */
    private String getTemplateFileName(Object bean) throws Exception {
        String uqcn = getUnqualifiedClassName(bean);
        String templateFile = getTemplateDir() + "/" + 
            uqcn.toLowerCase() + ".vm";
        return templateFile;
    }

    /**
     * Computes the file name for the generated bean. It also makes parent
     * directories if needed and returns a reference to a PrintWriter.
     * @param fqcn the fully qualified class name for the generated bean.
     * @return a PrintWriter pointing to the new file created.
     * @exception Exception if any are thrown from the method.
     */
    private PrintWriter getWriter(String fqcn) throws Exception {
        File f = new File(getBaseDir() + File.separator + 
            fqcn.replace('.', File.separatorChar) + "." + getSuffix());
        f.getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(new FileOutputStream(f), true);
        return writer;
    }

    /**
     * Returns the unqualified class name for the bean. The unqualified name
     * has the package name stripped out.
     * @param bean the bean object that drives the template.
     * @return the unqualified (no package portion) name of the bean class.
     * @exception Exception if any are thrown from the method.
     */
    private String getUnqualifiedClassName(Object bean) throws Exception {
        String uqcn = bean.getClass().getName().substring(
            bean.getClass().getName().lastIndexOf(".") + 1);
        return uqcn;
    }
}
