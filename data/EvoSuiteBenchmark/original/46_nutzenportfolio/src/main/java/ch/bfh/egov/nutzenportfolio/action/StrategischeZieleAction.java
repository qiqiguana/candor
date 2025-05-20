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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class StrategischeZieleAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private StrategischeZieleService service;
  private CommonService commonService;

  public StrategischeZieleAction(
      StrategischeZieleService service,
      CommonService commonService,
      CustomizingService cService,
      DetailzieleService dzService) {
    super();
    this.service = service;
    this.commonService = commonService;
    this.service.init(cService, dzService);
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
    service.populate(request);
    DynaActionForm szForm = (DynaActionForm) form;
    Integer mandantId =
      (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Integer customizingId =
      (Integer) request.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    StrategischesZiel sz = new StrategischesZiel();
    sz.setMandantId(mandantId);
    sz.setCustomizingId(customizingId);
    Integer[] idArr = service.getSelectedIdArr(sz);
    szForm.set("strategischeZieleIds", idArr);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward edit(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm szForm = (DynaActionForm) form;
    service.edit(request, szForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward update(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm szForm = (DynaActionForm) form;
    service.update(request, szForm);
    service.populate(request);
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm szForm = (DynaActionForm) form;
    StrategischesZiel sz = service.getStrategischesZiel(request, szForm);
    if (sz == null || sz.getSystem()) {
      service.populate(request);
      return mapping.findForward(Constants.LIST);
    }
    ActionMessages messages = new ActionMessages();
    service.deleteQuestion(request, sz, messages);
    saveMessages(request, messages);
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm szForm = (DynaActionForm) form;
    StrategischesZiel sz = service.getStrategischesZiel(request, szForm);
    if (sz == null || sz.getSystem()) {
      service.populate(request);
      return mapping.findForward(Constants.LIST);
    }
    
    // Löschen
    service.delete(request, sz);
    service.populate(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward set(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Formulardaten holen
    DynaActionForm strategischeZieleForm = (DynaActionForm) form;
    Integer[] strategischeZieleIds =
      (Integer[]) strategischeZieleForm.get("strategischeZieleIds");
    
    if (strategischeZieleIds.length == 0) {
      ActionMessages errors = new ActionMessages();
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.select.one", "Strategisches Ziel"));
      saveMessages(request, errors);
      service.populate(request);
      return mapping.findForward(Constants.FAILURE);
    }
    
    // Projektattraktivität holen
    HttpSession session = request.getSession();
    Integer customizingId = (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    
    // Strategische Ziele zuweisen
    logger.debug("Strategische Ziele angewählt: " + strategischeZieleIds.length);
    List<StrategischesZiel> strategischeZiele = new ArrayList<StrategischesZiel>();
    for (int i = 0; i < strategischeZieleIds.length; i++) {
      Integer strategischesZielId = strategischeZieleIds[i];
      logger.debug("Füge Strategisches Ziele " + strategischesZielId + " hinzu.");
      StrategischesZiel ziel = new StrategischesZiel();
      ziel.setStrategischesZielId(strategischesZielId);
      ziel.setProjektattraktivitaetId(getProjektattraktivitaetId(request));
      strategischeZiele.add(ziel);
    }
    service.setProjektattraktivitaet(strategischeZiele);
    request.setAttribute("customizingId", customizingId);
    service.setStatus(request, mandantId, customizingId);
    return mapping.findForward(Constants.SUCCESS);
  }
  
  public ActionForward add(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm szForm = (DynaActionForm) form;
    ActionMessages errors = new ActionMessages();
    ActionMessages messages = new ActionMessages();
    if (!service.add(request, szForm, errors, messages)) {
      saveErrors(request, errors);
      saveMessages(request, messages);
    }
    service.populate(request);
    return mapping.findForward(Constants.FORM);
  }
  
  private Integer getProjektattraktivitaetId(HttpServletRequest request) {
    // Projektattraktivität holen
    Integer customizingId =
      (Integer) request.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    return commonService.getProjektattraktivitaetIdByCustomizingId(customizingId);
  }

}
