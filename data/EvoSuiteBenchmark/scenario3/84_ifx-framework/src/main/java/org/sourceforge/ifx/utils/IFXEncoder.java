package org.sourceforge.ifx.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.Writer;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import org.sourceforge.ifx.basetypes.IFXObject;
import org.sourceforge.ifx.basetypes.IBaseType;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.jdom.input.SAXBuilder;

/**
 * Encodes an IFXObject to its equivalent IFX XML Element.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class IFXEncoder {

    /**
     * Builds a JDOM Element for the IFXObject supplied. No validation is
     * performed, regardless of the setting. To validate, use the encode()
     * methods instead.
     *
     * @param obj an IFXObject.
     * @return a JDOM Element.
     * @exception IFXException if an exception was encountered in encoding.
     */
    public Element encode(IFXObject obj) throws IFXException;
}
