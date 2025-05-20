// Decompiled by DJ v3.8.8.85 Copyright 2005 Atanas Neshkov  Date: 16.05.2005 11:59:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DirectLinkMenuItemRenderer.java

package ch.bluepenguin.tapestry.components.menu.renderer.impl;

import ch.bluepenguin.tapestry.components.menu.model.IMenuItem;
import org.apache.tapestry.*;
import org.apache.tapestry.engine.DirectService;
import org.apache.tapestry.engine.ILink;

// Referenced classes of package ch.bluepenguin.tapestry.components.menu.renderer.impl:
//            DefaultMenuItemRenderer

public class DirectLinkMenuItemRenderer extends DefaultMenuItemRenderer
{

    public DirectLinkMenuItemRenderer()
    {
    }

    public void startRender(IMarkupWriter writer, IMenuItem item)
    {
        writer.begin("li");
        if(styleClass != null)
            writer.attribute("class", styleClass);
        IMarkupWriter awriter = writer.getNestedWriter();
        DirectService service = (DirectService)cycle.getEngine().getService("direct");
        ILink link = service.getLink(cycle, component, parameters);
        awriter.begin("a");
        awriter.attribute("href", link.getURL());
        awriter.print(item.getName());
        awriter.end();
        awriter.flush();
        awriter.close();
    }

    public void setComponent(IComponent component)
    {
        this.component = component;
    }

    public void setCycle(IRequestCycle cycle)
    {
        this.cycle = cycle;
    }

    public void setParameters(Object parameters[])
    {
        this.parameters = parameters;
    }

    private IRequestCycle cycle;
    private IComponent component;
    private Object parameters[];
}