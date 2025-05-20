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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.time.Duration;
import sf.noen.webui.model.Configuration;
import sf.noen.webui.model.Update;
import sf.noen.webui.model.UpdateStatus;
import sf.noen.webui.model.UpdateType;
import sf.noen.webui.server.Tester;

import java.util.HashMap;
import java.util.Map;

/**
 * The webpage reporting test results, one to many at a time through ajax updates.
 *
 * @author Teemu Kanstr�n
 */
public class ReportPage extends NoenWebPage {
  private WebMarkupContainer resultsTable = new WebMarkupContainer("resultstable");
  private RepeatingView results = new RepeatingView("results");
  private UpdateStatus status = null;
  private Map<String, WebMarkupContainer> components = new HashMap<String, WebMarkupContainer>();
  private WebMarkupContainer totalRow = new WebMarkupContainer("totalrow");
  private Label totalItem = null;
  private int total = 0;
  private AbstractAjaxTimerBehavior timer = null;

  public ReportPage() {
    resultsTable.setOutputMarkupId(true);
//	WebMarkupContainer css = new WebMarkupContainer( "mycss" );
//    add( css );
    add(resultsTable);
    resultsTable.add(results);

    Label target = new Label("target", getUrl());
    add(target);
    totalRow.setOutputMarkupId(true);
    add(totalRow);
    totalItem = new Label("total", ""+total);
    totalItem.setOutputMarkupId(true);
    totalRow.add(totalItem);
	startTimer();
  }

  private void startTimer() {
    this.status = new UpdateStatus();
    String className = Configuration.getTestClassName();
    Class testClass = null;
    try {
      testClass = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Can not instantiate test class (" + className + ")", e);
    }
    Tester tester = new Tester(testClass, status);
    System.out.println("execute tests");
    tester.executeTests();
    System.out.println("adding timer");
	addTimer();
  }

  private void addTimer() {
	timer = new AbstractAjaxTimerBehavior(Duration.ONE_SECOND) {
	  protected void onTimer(AjaxRequestTarget target) {
		processUpdates(target);
	  }
	};
	resultsTable.add(timer);
  }

  private void processUpdates(AjaxRequestTarget target) {
    System.out.println("processing updates");
    for (Update update : status.getUpdates()) {
      Component item = null;
      if (update.isNew()) {
        item = buildItem(update, target);
      } else {
        item = updateItem(update);
      }

      // notice how we set the newly created item tag's id to that of the newly created
      // Wicket component, this is what will link this markup tag to Wicket component
      // during Ajax repaint

      // all thats left is to repaint the new item via Ajax
      target.addComponent(item);
      totalRow.remove(totalItem);
//      totalItem.remove("total");
      totalItem = new Label("total", "" + total);
      totalItem.setOutputMarkupId(true);
      totalRow.add(totalItem);
      target.addComponent(totalRow);

      status.processed(update);
    }
  }

  //TODO move logging to log4j
  private Component buildItem(Update update, AjaxRequestTarget target) {
    WebMarkupContainer item = new WebMarkupContainer(results.newChildId());
    item.setOutputMarkupId(true);
    results.add(item);
    String id = update.getName();
//    System.out.println("new item:"+id);
    components.put(id, item);
    item.add(new Label("name", id));
    Label status = new Label("status", update.getStatus());
    status.add(new SimpleAttributeModifier("class", update.style));
    item.add(status);
    Label description = new Label("description", update.getDescription());
    item.add(description);

    // first execute javascript which creates a placeholder tag in markup for this item
    target.prependJavascript(
            String.format(
                    "var item=document.createElement('%s');item.id='%s';Wicket.$('%s').appendChild(item);",
                    "tr", item.getMarkupId(), resultsTable.getMarkupId()));

    return item;
  }

  private Component updateItem(Update update) {
    String id = update.getName();
    System.out.println("updateing item:" + id);
    WebMarkupContainer item = components.get(id);

    item.remove("status");
    Label status = new Label("status", update.getStatus());
    item.add(status);
    status.add(new SimpleAttributeModifier("class", update.style));

    item.remove("description");
    Label description = new Label("description", update.getDescription());
    item.add(description);

    if (update.type == UpdateType.TEST_FINISHED || update.type == UpdateType.TEST_FAILED) {
      total++;
    }
	if (update.type == UpdateType.SUITE_FINISHED) {
	  timer.stop();
	}

    return item;
  }

}
