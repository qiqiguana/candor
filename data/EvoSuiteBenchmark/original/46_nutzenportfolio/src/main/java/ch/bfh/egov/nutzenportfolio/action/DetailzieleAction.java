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

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
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

public class DetailzieleAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private DetailzieleService service;
  private StrategischeZieleService szService;
  private CommonService commonService;

  public DetailzieleAction(
      DetailzieleService service,
      StrategischeZieleService szService,
      CommonService commonService,
      CustomizingService customizingService) {
    super();
    this.service = service;
    this.szService = szService;
    this.commonService = commonService;
    service.init(commonService, customizingService, szService);
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

    if(!populate(request)) {
      ActionMessages errors = new ActionMessages();
      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.strategische.ziele.not.set"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.FAILURE);
    }
    return mapping.findForward(Constants.FORM);
  }

  public ActionForward edit(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm dzForm = (DynaActionForm) form;
    service.edit(request, dzForm);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward update(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm dzForm = (DynaActionForm) form;
    service.update(request, dzForm);
    service.populate(request);
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm dzForm = (DynaActionForm) form;
    Detailziel dz = service.getDetailziel(request, dzForm);
    if (dz == null) {
      service.populate(request);
      return mapping.findForward(Constants.LIST);
    }
    ActionMessages messages = new ActionMessages();
    service.deleteQuestion(request, dz, messages);
    saveMessages(request, messages);
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm dzForm = (DynaActionForm) form;
    Detailziel dz = service.getDetailziel(request, dzForm);
    if (dz == null) {
      service.populate(request);
      return mapping.findForward(Constants.LIST);
    }
    
    // Löschen
    service.delete(request, dz);
    service.populate(request);
    return mapping.findForward(Constants.LIST);
  }

  public ActionForward remove(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    //Formulardaten holen
    DynaActionForm dzForm = (DynaActionForm) form;
    Integer detailzielId = (Integer) dzForm.get(Constants.DETAILZIEL_ID);
    Integer paId = (Integer) dzForm.get(Constants.PROJEKTATTRAKTIVITAET_ID);
    Boolean quantifizierbar = (Boolean) dzForm.get(Constants.QUANTIFIZIERBAR);
    logger.debug("detailzielId=" + detailzielId);
    logger.debug("paId=" + paId);
    logger.debug("quantifizierbar=" + quantifizierbar);
    Integer mandantId =
      (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    
    Detailziel dz = new Detailziel();
    dz.setProjektattraktivitaetId(paId);
    dz.setQuantifizierbar(quantifizierbar);
    dz.setMandantId(mandantId);
    List<Detailziel> dzs = service.getAssignments(dz);
    logger.debug(dzs.size() + " Detailziele in der Liste");
    
    // Zuweisung löschen
    for (Detailziel d : dzs) {
      if (d.getDetailzielId().equals(detailzielId)) {
        logger.debug("Zuweisung detailzielId=" + detailzielId
            + ", projektattraktivitaetId=" + d.getProjektattraktivitaetId()
            + " wird gelöscht.");
        service.deleteAssignment(d);
        break;
      }
    }

    populate(request);
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward set(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    // Formulardaten holen
    DynaActionForm dzForm = (DynaActionForm) form;
    Integer detailzielId = (Integer) dzForm.get(Constants.DETAILZIEL_ID);
    if (detailzielId == null) {
      populate(request);
      return mapping.findForward(Constants.FORM);
    }
    
    // Detailziel zuweisen
    logger.debug("Detailziel zuweisen: " + detailzielId);
    Detailziel dz = new Detailziel();
    dz.setDetailzielId(detailzielId);
    dz.setProjektattraktivitaetId(getProjektattraktivitaetId(request));
    service.insertAssignment(dz);
    
    // Formular abfüllen und anzeigen
    populate(request);
    return mapping.findForward(Constants.FORM);
  }

  public ActionForward next(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {

    // Nächste Action?
    String last = request.getParameter(Constants.LAST_STEP);
    if(last != null && last.equals("true"))
      return mapping.findForward(Constants.NEXT);
    populate(request);
    return mapping.findForward(Constants.FORM);
  }

  public ActionForward add(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    DynaActionForm dzForm = (DynaActionForm) form;
    ActionMessages errors = new ActionMessages();
    ActionMessages messages = new ActionMessages();
    if (!service.add(request, dzForm, errors, messages)) {
      saveErrors(request, errors);
      saveMessages(request, messages);
    }
    populate(request);
    return mapping.findForward(Constants.FORM);
  }
  
  private boolean populate(HttpServletRequest request) {
    Integer customizingId = (Integer) request.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    Integer projektattraktivitaetId = getProjektattraktivitaetId(request);
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);

    // Strategische Ziele holen
    StrategischesZiel sz = new StrategischesZiel();
    sz.setCustomizingId(customizingId);
    sz.setMandantId(mandantId);
    List<StrategischesZiel> szs = szService.getAssignments(sz);
    Integer szId = null;
    Integer step = null;

    if(szs.size() == 0) {
      return false;
    }

    // 1 Schritt pro Strategisches Ziel. Parameter überprüfen.
    String stepStr = request.getParameter(Constants.STEP);
    String next = request.getParameter(Constants.NEXT);
    logger.debug("Angegebener step: " + stepStr);
    try {
      step = new Integer(stepStr);
    } catch (NumberFormatException nfex) {
      step = 1;
    }

    // Denselben Schritt wiederholen, wenn nicht "weiter" geklickt wurde
    if(next == null && step > 1)
      --step;

    // Strategische Ziele in Array stellen
    Integer[] szIdArr = new Integer[szs.size()];
    String[] szNameArr = new String[szs.size()];
    int i = 0;
    for(StrategischesZiel sZiel : szs) {
      szIdArr[i] = sZiel.getStrategischesZielId();
      szNameArr[i++] = sZiel.getName();
      logger.debug("Strategisches Ziel: id=" + szIdArr[i - 1] + ", name=" + szNameArr[i - 1]);
    }

    // Auf inkorrekten step Parameter prüfen
    if ((step - 1) >= szIdArr.length) {
      logger.warn("Nicht verfügbarer Schritt ausgewählt!");
      step = 1;
    }

    // Auf letzten Schritt prüfen
    if (step == szIdArr.length) {
      request.setAttribute(Constants.LAST_STEP, true);
    }

    // Id des Strategischen Ziels anhand des step Parameters holen
    szId = szIdArr[step - 1];
    logger.debug("Gewähltes Strategisches Ziel: " + szId);
    request.setAttribute(Constants.STRATEGISCHES_ZIEL_ID, szId);
    request.setAttribute(Constants.STRATEGISCHES_ZIEL_NAME, szNameArr[step - 1]);
    request.setAttribute(Constants.STEP, step + 1);

    
    // Detailziele für quantifizierbaren Nutzen holen
    // Alle Detailziele zum gewählten Strategischen Ziel holen
    Detailziel dz = new Detailziel();
    dz.setProjektattraktivitaetId(projektattraktivitaetId);
    dz.setStrategischesZielId(szId);
    dz.setQuantifizierbar(true);
    dz.setMandantId(mandantId);

    // Detailziele aussortieren, die einem anderen Customizing zugeordnet sind
    List<Detailziel> dzs = service.getByStrategischesZiel(dz);
    HashMap<Integer, Detailziel> map = new HashMap<Integer, Detailziel>();
    for (Detailziel d : dzs) {
      Integer id = d.getDetailzielId();
      boolean contains = map.containsKey(id);
      Integer dPaId = d.getProjektattraktivitaetId();
      
      // HashMap abfüllen um allfällige doppelte Detailziele auszusortieren
      // (ist der Fall bei Zuweisungen zu anderen Customizings)
      if (!contains || dPaId != null && contains && dPaId.equals(projektattraktivitaetId))
        map.put(id, d);
    }
    request.setAttribute(Constants.DETAILZIELE_Q, map.values());
    request.setAttribute(Constants.PROJEKTATTRAKTIVITAET_ID, projektattraktivitaetId);

    List<Detailziel> dzsA = service.getAssignments(dz);
    request.setAttribute(Constants.DETAILZIELE_ASSIGNED_Q, dzsA);


    // Detailziele für nicht quantifizierbaren Nutzen holen
    // Alle Detailziele zum gewählten Strategischen Ziel holen
    dz.setQuantifizierbar(false);

    // Detailziele aussortieren, die einem anderen Customizing zugeordnet sind
    dzs = service.getByStrategischesZiel(dz);
    map = new HashMap<Integer, Detailziel>();
    for (Detailziel d : dzs) {
      Integer id = d.getDetailzielId();
      boolean contains = map.containsKey(id);
      Integer dPaId = d.getProjektattraktivitaetId();
      
      // HashMap abfüllen um allfällige doppelte Detailziele auszusortieren
      // (ist der Fall bei Zuweisungen zu anderen Customizings)
      if (!contains || dPaId != null && contains && dPaId.equals(projektattraktivitaetId))
        map.put(id, d);
    }
    request.setAttribute(Constants.DETAILZIELE_NQ, map.values());

    dzsA = service.getAssignments(dz);
    request.setAttribute(Constants.DETAILZIELE_ASSIGNED_NQ, dzsA);

    service.setStatus(request, szs, mandantId, projektattraktivitaetId);
    return true;
  }
  
  private Integer getProjektattraktivitaetId(HttpServletRequest request) {
    // Projektattraktivität holen
    Integer customizingId =
      (Integer) request.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    return commonService.getProjektattraktivitaetIdByCustomizingId(customizingId);
  }

}
