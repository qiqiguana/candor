// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GenericMarkupMenuItemRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer.impl;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import ch.bluepenguin.tapestry.components.menu.renderer.IMenuItemRenderer;
import java.util.*;
import org.apache.tapestry.IMarkupWriter;

// Referenced classes of package ch.bluepenguin.tapestry.components.menu.renderer.impl:
//            AbstractMenuItemRenderer

public class GenericMarkupMenuItemRenderer extends AbstractMenuItemRenderer
{

    public GenericMarkupMenuItemRenderer()
    {
    }

    public void setNestedRenderer(IMenuItemRenderer nested)
    {
        this.nested = nested;
    }

    public void setElement(String element)
    {
        this.element = element;
    }

    public void setAttributes(HashMap attributes)
    {
        this.attributes = attributes;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void startRender(IMarkupWriter writer, IMenuItem item)
    {
        writer.begin(element);
        if(attributes != null && attributes.keySet() != null)
        {
            String attributeName;
            String attributeValue;
            for(Iterator attributeNames = attributes.keySet().iterator(); attributeNames.hasNext(); writer.attribute(attributeName, attributeValue))
            {
                attributeName = (String)attributeNames.next();
                attributeValue = (String)attributes.get(attributeName);
            }

        }
        writer.print(text);
        nested.render(writer.getNestedWriter(), item);
    }

    IMenuItemRenderer nested;
    private String element;
    private HashMap attributes;
    private String text;
}