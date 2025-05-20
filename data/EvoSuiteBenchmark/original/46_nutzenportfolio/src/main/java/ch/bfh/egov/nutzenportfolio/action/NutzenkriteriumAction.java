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
import ch.bfh.egov.nutzenportfolio.form.FragenForm;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class NutzenkriteriumAction extends DispatchAction {
  private NutzenkriteriumService service;
  private CommonService cService;
  private Log logger = LogFactory.getLog(this.getClass());

  public NutzenkriteriumAction(
      NutzenkriteriumService service,
      AuswahlfeldService aService,
      CommonService cService,
      KategorieService kService,
      CustomizingService customizingService) {
    super();
    this.service = service;
    this.cService = cService;
    this.service.init(aService, cService, kService, customizingService);
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
    if (!service.populate(request)) {
      ActionMessages errors = new ActionMessages();
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.kategorien.not.set"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.KATEGORIEN);
    }
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward edit(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    service.edit(request, nForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward update(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    service.update(request, nForm);
    service.list(request);
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    Nutzenkriterium n = service.getNutzenkriterium(request, nForm);
    if (n == null) {
      service.list(request);
      return mapping.findForward(Constants.LIST);
    }
    ActionMessages messages = new ActionMessages();
    service.deleteQuestion(request, n, messages);
    saveMessages(request, messages);
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    Nutzenkriterium n = service.getNutzenkriterium(request, nForm);
    if (n == null) {
      service.list(request);
      return mapping.findForward(Constants.LIST);
    }
    
    // Löschen
    service.delete(request, n);
    service.list(request);
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward remove(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    service.remove(request, nForm);
    service.populate(request);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward set(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    service.set(request, nForm);
    service.populate(request);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward next(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    service.populate(request);
    if (service.next(request)) {
      return mapping.findForward(Constants.NEXT);
    }
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward populate(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    FragenForm nForm = (FragenForm) form;
    if (!service.populateFragen(request, nForm)) {
      ActionMessages errors = new ActionMessages();
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.nutzenkriterien.not.set"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.NUTZENKRITERIEN);
    }
    
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward save(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    FragenForm nForm = (FragenForm) form;
    
    // validieren und speichern 
    ActionMessages errors = new ActionMessages();
    if (service.saveFragen(request, nForm, errors)) {
      return mapping.findForward(Constants.SUCCESS);
    }
    
    // Fehler bei der Validierung
    saveErrors(request, errors);
    service.populateFragen(request, nForm);
    return mapping.findForward(Constants.FAILURE);
  }
  
  public ActionForward select(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Vorselektieren anhand anzahl auswahlfelder
    if (!service.populate(request)) {
      ActionMessages errors = new ActionMessages();
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.kategorien.not.set"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.KATEGORIEN);
    }
    service.preselect(request, mapping.getPath());
    cService.isOperativerNutzen(request);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward saveAbstufungen(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Anzahl Abstufungen in die Session stellen
    DynaActionForm aForm = (DynaActionForm) form;
    Integer abstufung = (Integer) aForm.get(Constants.ABSTUFUNGEN);
    logger.debug("Gewählte Abstufung: " + abstufung);
    request.getSession().setAttribute(Constants.ABSTUFUNGEN, abstufung);
    return mapping.findForward(Constants.NEXT);
  }
  
  public ActionForward saveGewichtungstyp(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Gewichtugnstyp in die Session stellen
    DynaActionForm aForm = (DynaActionForm) form;
    Integer gewichtungstyp = (Integer) aForm.get(Constants.GEWICHTUNGSTYP);
    logger.debug("Gewählter Gewichtugnstyp: " + gewichtungstyp);
    request.getSession().setAttribute(Constants.GEWICHTUNGSTYP, gewichtungstyp);
    
    // Operativer Nutzen -> Projektbetroffene
    if (cService.isOperativerNutzen(request)) {
      request.setAttribute(Constants.PROJEKTBETROFFENE, true);
    }
    if (service.direkteGewichtung(request)) {
      return mapping.findForward(Constants.DIREKT);
    }
    return mapping.findForward(Constants.NEXT);
  }
  
  public ActionForward prepareDirekteGewichtung(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    FragenForm fForm = (FragenForm) form;
    service.populateDirekteGewichtung(request, fForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward saveDirekteGewichtung(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    FragenForm fForm = (FragenForm) form;
    ActionMessages errors = new ActionMessages();
    if (!service.saveDirekteGewichtung(request, fForm, errors)) {
      saveMessages(request, errors);
      service.populateDirekteGewichtung(request, fForm);
      return mapping.findForward(Constants.FORM);
    }
    
    // Allfällige gewichtung über Fragebogen löschen
    service.deleteFragebogenGewichtung(request);
    
    if (cService.isOperativerNutzen(request)) {
      if (!service.customizingComplete(request)) {
        service.populateDirekteGewichtung(request, fForm);
        return mapping.findForward(Constants.FORM);
      }
      return mapping.findForward(Constants.END);
    }
    return mapping.findForward(Constants.NEXT);
    
  }
  
  public ActionForward add(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm nForm = (DynaActionForm) form;
    ActionMessages errors = new ActionMessages();
    ActionMessages messages = new ActionMessages();
    if (!service.add(request, nForm, errors, messages)) {
      saveErrors(request, errors);
      saveMessages(request, messages);
    }
    service.populate(request);
    return mapping.findForward(Constants.FORM);
  }
  
}
