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
    public Element encode(IFXObject obj) throws IFXException {
        try {
            Element element = new Element(getElementName(obj), namespace);
            List accessorList = getAccessors(obj);
            Iterator accessorIter = accessorList.iterator();
            while (accessorIter.hasNext()) {
                Method accessorMethod = (Method) accessorIter.next();
                if (accessorMethod.getName().equals("getClass")) {
                    continue;
                }
                Object retValObj = accessorMethod.invoke(obj, null);
                if (retValObj == null) {
                    continue;
                }
                IFXObject[] retVals = new IFXObject[1];
                if (retValObj.getClass().isArray()) {
                    retVals = (IFXObject[]) retValObj;
                } else {
                    retVals[0] = (IFXObject) retValObj;
                }
                for (int i = 0; i < retVals.length; i++) {
                    IFXObject retVal = retVals[i];
                    if (retVal instanceof IBaseType) {
                        String value = ((IBaseType) retVal).getString();
                        if (accessorMethod.getName().equals("getId")) {
                            element.setAttribute("Id", value);
                        } else {
                            Element baseElement = new Element(getElementName(retVal), namespace);
                            baseElement.setText(value);
                            element.addContent(baseElement);
                        }
                    } else {
                        element.addContent(encode(retVal));
                    }
                }
            }
            return element;
        } catch (Exception e) {
            throw new IFXException("Error encoding " + obj.getClass().getName(), e);
        }
    }
}
