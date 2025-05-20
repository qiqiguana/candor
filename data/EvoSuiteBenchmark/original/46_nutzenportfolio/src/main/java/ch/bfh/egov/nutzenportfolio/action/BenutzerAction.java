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
import ch.bfh.egov.nutzenportfolio.service.benutzer.BenutzerService;
import ch.bfh.egov.nutzenportfolio.tos.Benutzer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

public class BenutzerAction extends DispatchAction {
  private Log logger = LogFactory.getLog(this.getClass());
  private BenutzerService service;

  public BenutzerAction(BenutzerService service) {
      super();
      this.service = service;
  }

  public ActionForward login(
  		ActionMapping mapping,
  		ActionForm form,
  		HttpServletRequest request,
  		HttpServletResponse response)
  		throws Exception {
  	DynaActionForm loginForm = (DynaActionForm) form;
    if (loginValidationSuccessful(request, loginForm)) {
      Benutzer benutzer = new Benutzer();
      BeanUtils.copyProperties(benutzer, loginForm);
	
      benutzer = service.loginSuccessful(benutzer);
      if (benutzer != null) {
      	// mandantId in session stellen
      	HttpSession session = request.getSession();
      	logger.debug("mandantId: " + benutzer.getMandantId());
      	session.setAttribute("mandantId", benutzer.getMandantId());
      	return mapping.findForward(Constants.SUCCESS);
      }
    }
    ActionMessages errors = new ActionMessages();
    errors.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("errors.login.failure"));
    saveMessages(request, errors);
    return mapping.findForward(Constants.FAILURE);
  }
  
  public ActionForward logout(
  		ActionMapping mapping,
  		ActionForm form,
  		HttpServletRequest request,
  		HttpServletResponse response)
  		throws Exception {
  	// invalidate session
  	HttpSession session = request.getSession();
  	session.invalidate();
  	return mapping.findForward(Constants.LOGOUT);
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
    DynaActionForm benutzerForm = (DynaActionForm) form;
    if (isUpdate(request, benutzerForm)) {
        Integer benutzerId = (Integer) benutzerForm.get("benutzerId");
        Benutzer benutzer = service.getBenutzer(benutzerId);
        String passwort = benutzer.getPasswort();
        benutzerForm.set("benutzername", benutzer.getBenutzername());
        benutzerForm.set("passwort", passwort);
        benutzerForm.set("passwort2", passwort);
        benutzerForm.set("update", true);
    }
    return mapping.findForward(Constants.FORM);
  }
  
  public ActionForward deletequestion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    
    Benutzer benutzer = getBenutzer(request);
    if (benutzer == null) {
      populate(request);
      return mapping.findForward(Constants.LIST);
    }
    ActionMessages messages = new ActionMessages();
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", benutzer.getBenutzername()));
    saveMessages(request, messages);
    request.setAttribute(Constants.ACTION, Constants.BENUTZER_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.BENUTZER_ID);
    request.setAttribute(Constants.ID_VALUE, benutzer.getBenutzerId());
    return mapping.findForward(Constants.QUESTION);
  }
  
  public ActionForward delete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    Benutzer benutzer = getBenutzer(request);
    if (benutzer == null) {
      populate(request);
      return mapping.findForward(Constants.LIST);
    }
    service.deleteBenutzer(benutzer.getBenutzerId());
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
    DynaActionForm benutzerForm = (DynaActionForm) form;
    Integer benutzerId = (Integer) benutzerForm.get("benutzerId");
    Integer mandantId = (Integer) request.getSession().getAttribute("mandantId");
    String benutzername = (String) benutzerForm.get("benutzername");
    String passwort = (String) benutzerForm.get("passwort");
    Benutzer benutzer = new Benutzer();
    benutzer.setBenutzerId(benutzerId);
    benutzer.setMandantId(mandantId);
    benutzer.setBenutzername(benutzername);
    benutzer.setPasswort(passwort);
    
    // benutzername check auf unique
    if (exists(request, benutzerForm)) {
      ActionMessages errors = new ActionMessages();
      errors.add("benutzername",
          new ActionMessage("errors.duplicate", "Benutzername"));
      saveMessages(request, errors);
      return mapping.findForward(Constants.FAILURE);
    }
    
    // update?
    if (isUpdate(request, benutzerForm)) {
      service.updateBenutzer(benutzer);
    }
    // insert
    else {
      service.insertBenutzer(benutzer);
    }
    populate(request);
    return mapping.findForward(Constants.SUCCESS);
  }
  
  private Benutzer getBenutzer(HttpServletRequest request) {
    String benutzerIdParam = request.getParameter("benutzerId");
    Integer benutzerId = null;
    try {
      benutzerId = new Integer(benutzerIdParam);
    } catch (Exception ex) {
      logger.warn("Fehlende benutzerId!");
      return null;
    }
    Benutzer benutzer = service.getBenutzer(benutzerId);
    if (benutzer == null) {
      logger.warn("Benutzer mit der id " + benutzerId + " wurde nicht gefunden.");
      return null;
    }
    return benutzer;
  }
  
  private void populate(HttpServletRequest request) {
    Integer mandantId = 
      (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    List benutzer = service.getAllBenutzer(mandantId);
    request.setAttribute(Constants.BENUTZER, benutzer);
  }
  
  private boolean isUpdate(HttpServletRequest request, DynaActionForm benutzerForm) {
    Integer id = (Integer) benutzerForm.get("benutzerId");
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }
  
  private boolean exists(HttpServletRequest request, DynaActionForm benutzerForm) {
    Integer id = (Integer) benutzerForm.get("benutzerId");
    String benutzername = (String) benutzerForm.get("benutzername");
    
    // update?
    if (id != null && id != 0) {
      Benutzer benutzer = service.getBenutzer(id);
      
      // benutzername geändert, überprüfen
      if (!benutzername.equals(benutzer.getBenutzername())) {
        return service.getBenutzerByBenutzername(benutzername) != null;
      }
    }
    
    // insert
    else {
      return service.getBenutzerByBenutzername(benutzername) != null;
    }
    return false;
  }

  private boolean loginValidationSuccessful(
  		HttpServletRequest request,
  		DynaActionForm form) {
    boolean isOk = true;
    ActionMessages errors = new ActionMessages();
    String benutzername = form.getString("benutzername");
    String passwort = form.getString("passwort");
    if (benutzername == null || benutzername.trim().length() == 0) {
        errors.add("benutzername",
            new ActionMessage("errors.required", "Benutzername"));
    }
    if (passwort == null || passwort.trim().length() == 0) {
        errors.add("passwort",
            new ActionMessage("errors.required", "Passwort"));
    }
    if (!errors.isEmpty()) {
        saveErrors(request, errors);
        isOk = false;
    }
    return isOk;
  }
}
