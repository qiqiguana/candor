// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DefaultMenuItemRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer.impl;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import org.apache.tapestry.IMarkupWriter;

// Referenced classes of package ch.bluepenguin.tapestry.components.menu.renderer.impl:
//            AbstractMenuItemRenderer

public class DefaultMenuItemRenderer extends AbstractMenuItemRenderer
{

    public DefaultMenuItemRenderer()
    {
    }

    public void startRender(IMarkupWriter writer, IMenuItem item)
    {
        writer.begin("li");
        if(styleClass != null)
            writer.attribute("class", styleClass);
        writer.print(item.getName());
    }
}