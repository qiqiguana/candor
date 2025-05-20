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
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class KategorieAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private KategorieService service;

  public KategorieAction(
      KategorieService service,
      CommonService cService,
      CustomizingService customizingService,
      NutzenkriteriumService nService) {
    super();
    this.service = service;
    service.init(cService, customizingService,nService);
  }
  
  public ActionForward list(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    service.list(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward prepare(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    logger.debug("prepare");
    DynaActionForm kForm = (DynaActionForm) form;
    service.populate(request, kForm);
    return mapping.findForward(Constants.FORM);
  }

  public ActionForward edit(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm kForm = (DynaActionForm) form;
    service.edit(request, kForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward update(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm kForm = (DynaActionForm) form;
    service.update(request, kForm);
    service.list(request);
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm kForm = (DynaActionForm) form;
    Kategorie k = service.getKategorie(request, kForm);
    if (k == null || k.getSystem()) {
      service.list(request);
      return mapping.findForward(Constants.LIST);
    }
    ActionMessages messages = new ActionMessages();
    service.deleteQuestion(request, k, messages);
    saveMessages(request, messages);
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm kForm = (DynaActionForm) form;
    Kategorie k = service.getKategorie(request, kForm);
    if (k == null || k.getSystem()) {
      service.list(request);
      return mapping.findForward(Constants.LIST);
    }
    
    // Löschen
    service.delete(request, k);
    service.list(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward save(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Formulardaten holen
    DynaActionForm kForm = (DynaActionForm) form;
    if (service.save(request, kForm)) {
      return mapping.findForward(Constants.SUCCESS);
    }
    
    // Fehler bei keiner Auswahl
    ActionMessages errors = new ActionMessages();
    errors.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("errors.select.one", "Kategorie"));
    saveMessages(request, errors);
    return mapping.findForward(Constants.FAILURE);
  }
  
  public ActionForward add(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm kForm = (DynaActionForm) form;
    ActionMessages errors = new ActionMessages();
    ActionMessages messages = new ActionMessages();
    if (!service.add(request, kForm, errors, messages)) {
      saveErrors(request, errors);
      saveMessages(request, messages);
    }
    service.populate(request, kForm);
    return mapping.findForward(Constants.FORM);
  }

}
