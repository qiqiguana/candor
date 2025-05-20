// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HyperlinkMenuItemRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer.impl;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import org.apache.tapestry.IMarkupWriter;

// Referenced classes of package ch.bluepenguin.tapestry.components.menu.renderer.impl:
//            DefaultMenuItemRenderer

public class HyperlinkMenuItemRenderer extends DefaultMenuItemRenderer
{

    public HyperlinkMenuItemRenderer()
    {
    }

    public void startRender(IMarkupWriter writer, IMenuItem item)
    {
        writer.begin("li");
        if(styleClass != null)
            writer.attribute("class", styleClass);
        IMarkupWriter nested = writer.getNestedWriter();
        nested.begin("a");
        nested.attribute("href", getHyperlink());
        nested.print(item.getName());
        nested.end();
        nested.flush();
        nested.close();
    }

    public String getHyperlink()
    {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink)
    {
        this.hyperlink = hyperlink;
    }

    private String hyperlink;
}