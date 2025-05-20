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
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.projektgruppe.ProjektgruppeService;
import ch.bfh.egov.nutzenportfolio.tos.Projektgruppe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class ProjektgruppeAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private ProjektgruppeService service;

  public ProjektgruppeAction(
      ProjektgruppeService service,
      CustomizingService cService) {
    super();
    this.service = service;
    this.service.init(cService);
  }
  
  public ActionForward list(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    service.populate(request);
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
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward save(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Projektgruppe erzeugen
    DynaActionForm pForm = (DynaActionForm) form;
    Projektgruppe p = service.get(request, pForm);
    
    // Auf Duplikate überprüfen (Name)
    if (service.exists(p)) {
      ActionMessages errors = new ActionMessages();
      errors.add("name",
          new ActionMessage("errors.duplicate", "Name"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.FAILURE);
    }
    
    // Projektgruppe speichern
    service.save(request, p);
    return mapping.findForward(Constants.SUCCESS);
  }
  
  public ActionForward deleteQuestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Projektgruppe anhand der Id holen
    Projektgruppe p = service.get(request);
    
    // Projektgruppe nicht gefunden, Abbruch
    if (p == null) {
      return mapping.findForward(Constants.LIST);
    }
    
    // Abfrage zum löschen erzeugen
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", p.getName()));
    saveMessages(request, messages);
    request.setAttribute(Constants.ACTION, Constants.PROJEKTGRUPPE_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.PROJEKTGRUPPE_ID);
    request.setAttribute(Constants.ID_VALUE, p.getProjektgruppeId());
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Projektgruppe anhand der Id holen
    service.delete(request);
    return mapping.findForward(Constants.LIST);
  }

}
