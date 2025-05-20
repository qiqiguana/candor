/*
 * Copyright (C) 2009 VTT Technical Research Centre of Finland.
 *
 * This file is part of NOEN framework.
 *
 * NOEN framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2.
 *
 * NOEN framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package sf.noen.webui.wicket;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import java.io.Serializable;

/**
 * The form for entering the endpoint URL to test and to start testing.
 *
 * @author Teemu Kanstr�n
 */
public class URLForm extends Form {
  /** It's the URL to test. */
  private URL url = new URL();

  public URLForm(final String componentName) {
    super(componentName);
    add(new TextField("url", new PropertyModel(url, "url")));
  }

  public final void onSubmit() {
    System.out.println("url:"+url);
    NoenSession session = (NoenSession)getSession();
    session.setUrl(url.url);
	setResponsePage(ReportPage.class);
//    throw new RuntimeException("was here:"+url.url);
  }

  /**
   * This class is only needed because Wicket needs a model object and this model object needs to have
   * matching attributes to the names on the form. So no passing a string object as a model but you need
   * to wrap the string like this. Making the variable public saves the trouble of getters/setters.
   */
  private class URL implements Serializable {
    public String url = "http://lalaaa";
  }
}
