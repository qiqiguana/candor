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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.NaOpNuService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.ProjektattraktivitaetService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.projektgruppe.ProjektgruppeService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class CustomizingAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private CustomizingService service;
  private CommonService commonService;

  public CustomizingAction(
      CustomizingService service,
      CommonService commonService,
      StrategischeZieleService szService,
      DetailzieleService dzService,
      AuswahlfeldService afService,
      KategorieService kService,
      NutzenkriteriumService nService,
      NaOpNuService naOpNuService,
      ProjektattraktivitaetService paService,
      ProjektgruppeService pgService) {
    super();
    this.service = service;
    this.commonService = commonService;
    this.service.init(
        commonService,
        szService,
        dzService,
        afService,
        kService,
        nService,
        naOpNuService,
        paService,
        pgService);
  }
  
  public ActionForward list(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    populate(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward prepare(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    HttpSession session = request.getSession();
    service.unsetStatus(request);
    DynaActionForm customizingForm = (DynaActionForm) form;
    Integer customizingId = (Integer) customizingForm.get("customizingId");
    logger.debug("form customizingId=" + customizingId);
    if (customizingId != null && customizingId != 0) {
      logger.debug("customizingId wird in Session gesetzt");
      session.setAttribute(Constants.CUSTOMIZING_ID, customizingId);
    }
    logger.debug("---" + (Integer) session.getAttribute(Constants.CUSTOMIZING_ID));
    if (isUpdate(request, customizingForm)) {
      Customizing c = createCustomizing(request);
      customizingId = (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
      c.setCustomizingId(customizingId);
      Customizing customizing = service.getById(c);
      customizingForm.set(Constants.CUSTOMIZING_ID, customizingId);
      customizingForm.set("name", customizing.getName());
      customizingForm.set("update", true);
      service.setStatus(request);
    }
    return mapping.findForward(Constants.FORM);
  }

  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    Customizing customizing = getCustomizing(request);
    if (customizing == null) {
      populate(request);
      logger.debug("customizing = null");
      return mapping.findForward(Constants.LIST);
    }
    if (customizing.getStatus()) {
      request.setAttribute("active", true);
      request.setAttribute("customizingDelete", true);
    }
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", customizing.getName()));
    saveMessages(request, messages);
    request.setAttribute(Constants.ACTION, Constants.CUSTOMIZING_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.CUSTOMIZING_ID);
    request.setAttribute(Constants.ID_VALUE, customizing.getCustomizingId());
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    Customizing customizing = getCustomizing(request);
    if (customizing == null || customizing.getStatus()) {
      populate(request);
      logger.debug("customizing = null oder noch aktiv");
      return mapping.findForward(Constants.LIST);
    }
    service.cascadeDelete(request, customizing);
    populate(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward insertOrUpdate(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Formulardaten holen
    DynaActionForm customizingForm = (DynaActionForm) form;
    Integer customizingId = (Integer) customizingForm.get("customizingId");
    Integer mandantId = (Integer) request.getSession().getAttribute("mandantId");
    String name = (String) customizingForm.get("name");
    Customizing customizing = new Customizing();
    customizing.setCustomizingId(customizingId);
    customizing.setMandantId(mandantId);
    customizing.setName(name);
    customizing.setStatus(true);
    
    // customizingname check auf unique
    if (exists(request, customizingForm)) {
      ActionMessages errors = new ActionMessages();
      errors.add("name",
          new ActionMessage("errors.duplicate", "Customizing"));
      logger.debug("Name besteht bereits.");
      saveErrors(request, errors);
      return mapping.findForward(Constants.FAILURE);
    }
    
    // update?
    if (isUpdate(request, customizingForm)) {
      service.update(customizing);
    }
    // insert
    else {
      customizingId = service.insert(customizing);
      
      // Projektattraktivitaet, Nutzenattraktivitaet und
      // OperativerNutzen einfügen
      commonService.insertCustomizingParts(customizingId);
    }
    populate(request);
    request.getSession().setAttribute(Constants.CUSTOMIZING_ID, customizingId);
    return mapping.findForward(Constants.SUCCESS);
  }
  
  public ActionForward copy(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm cForm = (DynaActionForm) form;
    service.copy(request, cForm);
    populate(request);
    return mapping.findForward(Constants.LIST);
  }
  
  public ActionForward changeStatus(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm cForm = (DynaActionForm) form;
    service.changeStatus(request, cForm);
    populate(request);
    return mapping.findForward(Constants.LIST);
  }
  
  private Customizing getCustomizing(HttpServletRequest request) {
    String param = request.getParameter(Constants.CUSTOMIZING_ID);
    Integer customizingId = null;
    try {
      customizingId = new Integer(param);
    } catch (Exception ex) {
      logger.warn("Fehlende customizingId!");
      return null;
    }
    Customizing c = createCustomizing(request);
    c.setCustomizingId(customizingId);
    c = service.getById(c);
    if (c == null) {
      logger.warn("Customizing mit der id " + customizingId + " wurde nicht gefunden.");
      return null;
    }
    return c;
  }
  
  private void populate(HttpServletRequest request) {
    request.getSession().removeAttribute(Constants.CUSTOMIZING_ID);
    Customizing c = createCustomizing(request);
    List customizings = service.getAll(c.getMandantId());
    request.setAttribute(Constants.CUSTOMIZINGS, customizings);
  }
  
  private boolean isUpdate(HttpServletRequest request, DynaActionForm customizingForm) {
    HttpSession session = request.getSession();
    logger.debug("---" + (Integer) session.getAttribute(Constants.CUSTOMIZING_ID));
    Object obj = request.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    logger.debug("session customizingId obj=" + obj);
    if (obj != null && obj instanceof Integer) {
      logger.debug("Update");
      return true;
    }
    Integer id = (Integer) customizingForm.get("customizingId");
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }
  
  private boolean exists(HttpServletRequest request, DynaActionForm customizingForm) {
    Integer id = (Integer) customizingForm.get("customizingId");
    String name = (String) customizingForm.get("name");
    Customizing c = createCustomizing(request);
    c.setCustomizingId(id);
    c.setName(name);
    
    // update?
    if (id != null && id != 0) {
      Customizing customizing = service.getById(c);
      
      // customizingname geändert, überprüfen
      if (!name.equals(customizing.getName())) {
        return service.getByName(c) != null;
      }
    }
    
    // insert
    else {
      return service.getByName(c) != null;
    }
    return false;
  }
  
  /**
   * Generiert ein Customizing Objekt mit dem aktuellen Mandanten.
   * 
   * @param request               der HttpServletRequest
   * @return                      ein Customizing mit gesetzem Mandant
   */
  private Customizing createCustomizing(HttpServletRequest request) {
    Customizing c = new Customizing();
    c.setMandantId(
        (Integer) request.getSession().getAttribute(Constants.MANDANT_ID));
    return c;
  }
}
