package org.sourceforge.ifx.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import org.sourceforge.ifx.basetypes.IFXObject;
import org.sourceforge.ifx.basetypes.IFXString;
import org.sourceforge.ifx.basetypes.IBaseType;
import org.jdom.Element;

/**
 * Decodes an IFX XML String to its equivalent Framework bean.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.3 $
 */
public class IFXDecoder {

    /**
     * Decodes the element into its equivalent IFX XML representation.
     *
     * @param element the Element to decode.
     * @return an IFXObject.
     * @exception IFXException wrapper for underlying exception.
     */
    public IFXObject decode(Element element) throws IFXException {
        try {
            String id = element.getAttributeValue("Id");
            String beanClassName = getBeanClassName(element);
            IFXObject obj = (IFXObject) Class.forName(beanClassName).newInstance();
            List children = element.getChildren();
            if (children.size() == 0) {
                String text = element.getText();
                if (text != null && text.trim().length() > 0) {
                    if (!(obj instanceof IBaseType)) {
                        throw new IFXException("Leaf node not an instance of IBaseType: " + obj.getClass().getName());
                    } else {
                        ((IBaseType) obj).setString(text);
                        return obj;
                    }
                } else {
                    // return the empty object
                    return obj;
                }
            } else {
                Iterator childIter = children.iterator();
                while (childIter.hasNext()) {
                    Element childElement = (Element) childIter.next();
                    IFXObject childObj = decode(childElement);
                    // run corresponding accessor method to see if the
                    // result is an array
                    Method accessorMethod = getAccessor(obj, childElement);
                    Object retValObj = accessorMethod.invoke(obj, null);
                    boolean isArray = accessorMethod.getReturnType().isArray();
                    // then get the mutator method and invoke it
                    Method mutatorMethod = getMutator(obj, childElement);
                    if (isArray) {
                        // if result is an array, build a new array with
                        // size = returned array size + 1, and stuff this
                        // object at the end of the array
                        IFXObject[] childObjs = null;
                        if (retValObj != null) {
                            int oldLen = Array.getLength(retValObj);
                            childObjs = (IFXObject[]) Array.newInstance(childObj.getClass(), new int[] { oldLen + 1 });
                            IFXObject[] retValObjs = (IFXObject[]) retValObj;
                            System.arraycopy(retValObj, 0, childObjs, 0, oldLen);
                            childObjs[oldLen] = childObj;
                        } else {
                            childObjs = (IFXObject[]) Array.newInstance(childObj.getClass(), new int[] { 1 });
                            childObjs[0] = childObj;
                        }
                        mutatorMethod.invoke(obj, new Object[] { childObjs });
                    } else {
                        mutatorMethod.invoke(obj, new Object[] { childObj });
                    }
                }
                // if Id attribute is specified, populate it
                if (id != null) {
                    Method mutatorMethod = getMutator(obj, "Id");
                    IFXString idObj = new IFXString();
                    idObj.setString(id);
                    mutatorMethod.invoke(obj, new Object[] { idObj });
                }
                return obj;
            }
        } catch (Exception e) {
            throw new IFXException("Error decoding " + element.getName(), e);
        }
    }
}
