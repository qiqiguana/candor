/**
 * Nutzenportfolio
 * Copyright (C) 2006 Kompetenzzentrum E-Business, Simon Bergamin

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ch.bfh.egov.nutzenportfolio.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.service.projekt.ProjektService;
import ch.bfh.egov.nutzenportfolio.service.projektgruppe.ProjektgruppeService;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class ProjektAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private ProjektService service;
  private ProjektgruppeService gService;

  public ProjektAction(ProjektService service, ProjektgruppeService gService) {
    super();
    this.service = service;
    this.gService = gService;
  }
  
  public ActionForward list(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    service.populate(request);
    gService.populate(request);
    return mapping.findForward(Constants.LIST);
  } 
  
  public ActionForward prepare(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    logger.debug("prepare");
    DynaActionForm pForm = (DynaActionForm) form;
    service.prepare(request, pForm);

    // Forward für save und abbrechen merken, da dieses Formular von zwei Orten aus aufgerufen werden kann
    if(request.getParameter(Constants.PROJEKT_ID) == null) {
    	request.getSession().setAttribute(Constants.PROJEKTE_FORWARD, Constants.SUCCESS);
    	request.setAttribute(Constants.ABORT_URL, "/adm/projektadmin.do");
    }
    else {
        request.getSession().setAttribute(Constants.PROJEKTE_FORWARD, Constants.SUCCESS_EDIT);
        request.setAttribute(Constants.ABORT_URL, "/adm/projekte/view.do?dispatch=list");    	
    }

    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward save(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {

    // Projekt erzeugen
    DynaActionForm pForm = (DynaActionForm) form;
    Projekt p = service.get(request, pForm);

    // Auf Duplikate überprüfen (Name)
    if (service.exists(p)) {
      ActionMessages errors = new ActionMessages();
      errors.add("name",
          new ActionMessage("errors.duplicate", "Name"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.FAILURE);
    }
    // Projekt speichern
    service.save(request, p);

    // Forward für save holen, da dieses Formular von zwei Orten aus aufgerufen werden kann
    String forward = (String) request.getSession().getAttribute(Constants.PROJEKTE_FORWARD);
    if(forward != null) {
        request.getSession().removeAttribute(Constants.PROJEKTE_FORWARD);
        return mapping.findForward(forward);
    }
    return mapping.findForward(Constants.SUCCESS);
  }

  public ActionForward deleteQuestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Projekt anhand der Id holen
    Projekt p = service.get(request);
    
    // Projekt nicht gefunden, Abbruch
    if (p == null) {
      return mapping.findForward(Constants.LIST);
    }
    
    // Abfrage zum löschen erzeugen
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", p.getName()));
    saveMessages(request, messages);
    request.setAttribute(Constants.ACTION, Constants.PROJEKT_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.PROJEKT_ID);
    request.setAttribute(Constants.ID_VALUE, p.getProjektId());
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Projekt anhand der Id holen
    service.delete(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward auswertung(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    DynaActionForm pForm = (DynaActionForm) form;
    ActionMessages errors = new ActionMessages();
    if (!service.auswertung(request, pForm, errors)) {
      saveMessages(request, errors);
      return mapping.findForward(Constants.FAILURE);
    }
    return mapping.findForward(Constants.SUCCESS);
  }
  
  public ActionForward changeStatus(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm f = (DynaActionForm) form;
    service.changeStatus(request, f);
    service.populate(request);
    return mapping.findForward(Constants.LIST);
  }

}
