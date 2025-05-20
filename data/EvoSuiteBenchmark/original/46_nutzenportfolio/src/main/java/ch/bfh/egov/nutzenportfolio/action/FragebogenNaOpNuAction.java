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
import ch.bfh.egov.nutzenportfolio.form.NaOpNuForm;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.NaOpNuService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class FragebogenNaOpNuAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private NaOpNuService service;

  public FragebogenNaOpNuAction(NaOpNuService service) {
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
    NaOpNuForm nForm = (NaOpNuForm) form;
    if (service.prepare(request, nForm)) {
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
    logger.debug("save");
    
    // Resultate abspeichern
    NaOpNuForm pForm = (NaOpNuForm) form;
    
    service.save(request, pForm);
    return mapping.findForward(Constants.SUCCESS);
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
