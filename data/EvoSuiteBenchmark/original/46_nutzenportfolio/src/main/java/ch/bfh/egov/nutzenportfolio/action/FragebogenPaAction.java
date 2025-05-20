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
import ch.bfh.egov.nutzenportfolio.form.ProjektattraktivitaetForm;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.ProjektattraktivitaetService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class FragebogenPaAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private ProjektattraktivitaetService service;

  public FragebogenPaAction(ProjektattraktivitaetService service) {
    super();
    this.service = service;
  }

  public ActionForward prepare(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    logger.debug("prepare");
    ProjektattraktivitaetForm pForm = (ProjektattraktivitaetForm) form;
    if (service.prepare(request, pForm)) {
      return mapping.findForward(Constants.FORM);
    }
    return mapping.findForward(Constants.FAILURE);
  }
  
  public ActionForward save(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Resultate abspeichern
    ProjektattraktivitaetForm pForm = (ProjektattraktivitaetForm) form;
    
    service.save(request, pForm);
    return mapping.findForward(Constants.SUCCESS);
  }
  
  public ActionForward show(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm paForm = (DynaActionForm) form;
    boolean status = service.isActive(request, paForm);
    logger.debug("status=" + status);
    request.setAttribute("status", status);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward login(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm loginForm = (DynaActionForm) form;
    if (service.login(request, loginForm)) {
      return mapping.findForward(Constants.SUCCESS);
    }
    ActionMessages errors = new ActionMessages();
    errors.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("errors.fragebogen.exists"));
    saveMessages(request, errors);
    return mapping.findForward(Constants.FAILURE);
  }

}
