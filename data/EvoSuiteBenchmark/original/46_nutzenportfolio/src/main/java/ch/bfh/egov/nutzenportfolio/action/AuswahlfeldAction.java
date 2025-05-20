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
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class AuswahlfeldAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private AuswahlfeldService service;

  public AuswahlfeldAction(
      AuswahlfeldService service,
      NutzenkriteriumService nService,
      CommonService cService,
      CustomizingService customizingService,
      StrategischeZieleService szService) {
    super();
    this.service = service;
    service.init(nService, cService, customizingService, szService);
  }
  
  public ActionForward list(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    service.list(request, mapping.getPath());
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward prepare(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm afForm = (DynaActionForm) form;
    
    // Auswahlfelder holen
    service.populate(request, mapping.getPath(), afForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward edit(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm aForm = (DynaActionForm) form;
    service.edit(request, aForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward update(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm aForm = (DynaActionForm) form;
    service.update(request, aForm);
    service.list(request, mapping.getPath());
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm aForm = (DynaActionForm) form;
    Auswahlfeld a = service.getAuswahlfeld(request, aForm);
    if (a == null) {
      service.list(request, mapping.getPath());
      return mapping.findForward(Constants.LIST);
    }
    ActionMessages messages = new ActionMessages();
    service.deleteQuestion(request, a, messages);
    saveMessages(request, messages);
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm aForm = (DynaActionForm) form;
    Auswahlfeld a = service.getAuswahlfeld(request, aForm);
    if (a == null) {
      service.list(request, mapping.getPath());
      return mapping.findForward(Constants.LIST);
    }
    
    // Löschen
    service.delete(request, a);
    service.list(request, mapping.getPath());
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward next(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Formulardaten holen, Auswahlfelder setzen und nächste Action anwählen
    DynaActionForm aForm = (DynaActionForm) form;
    String path = mapping.getPath();
    service.setAuswahlfelder(request, aForm, path);
    
    if (path.contains(Constants.REALISIERBARKEIT_ACTION)) {
      return mapping.findForward(Constants.NEXT);
    }
    
    // Nächste Action?
    logger.debug("Abstufungen: " + request.getSession().getAttribute(Constants.ABSTUFUNGEN));
    if (service.next(request)) {
      logger.debug("Nächster Schritt");
      if (service.isOperativerNutzen(request) && 
          path.contains(Constants.GEWICHTUNG_ACTION)) {
        if (!service.customizingComplete(request)) {
          service.populate(request, mapping.getPath(), aForm);
          return mapping.findForward(Constants.FORM);
        }
        return mapping.findForward(Constants.END);
      }
      return mapping.findForward(Constants.NEXT);
    }
    
    // Nächste Kategorie
    logger.debug("Nächste Kategorie");
    service.populate(request, mapping.getPath(), aForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward add(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm afForm = (DynaActionForm) form;
    ActionMessages errors = new ActionMessages();
    ActionMessages messages = new ActionMessages();
    if (!service.add(request, afForm, errors, messages)) {
      saveErrors(request, errors);
      saveMessages(request, messages);
    }
    service.populate(request, mapping.getPath(), afForm);
    return mapping.findForward(Constants.FORM);
  }

}
