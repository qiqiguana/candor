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
package ch.bfh.egov.nutzenportfolio.service.projektgruppe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.persistence.projektgruppe.ProjektgruppeDao;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.tos.Projektgruppe;

/**
 * Implementierende Service-Klasse für Projektgruppen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektgruppeDaoService implements ProjektgruppeService {
  private ProjektgruppeDao dao;
  private CustomizingService customizingService;
  private Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für Projektgruppen. 
   * 
   * @param dao                               Projektgruppen DataAcessObject-Interface
   */
  public ProjektgruppeDaoService(ProjektgruppeDao dao) {
    this.dao = dao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.projektgruppe.ProjektgruppeDao#getAll(Integer)
   */
  public List getAll(Integer mandantId) {
    return dao.getAll(mandantId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.projektgruppe.ProjektgruppeDao#unsetCustomizingId(Integer)
   */
  public void unsetCustomizingId(Integer customizingId) {
    dao.unsetCustomizingId(customizingId);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch Übergabe anderer,
   * benötigter Service-Objekte.
   * 
   * @param cService                   CustomizingService Objekt
   */
  public void init(CustomizingService cService) {
    this.customizingService = cService;
  }

  /**
   * Setzt alle vorhandenen Projektgruppen in den request.
   * 
   * @param request             der HttpServletRequest
   */
  public void populate(HttpServletRequest request) {
    Projektgruppe p = createProjektgruppe(request);
    List projektgruppen = dao.getAll(p.getMandantId());
    request.setAttribute(Constants.PROJEKTGRUPPEN, projektgruppen);
  }
  
  /**
   * Füllt das Formular mit allen benötigten Daten ab.
   * 
   * @param request             der HttpServletRequest
   * @param form                das DynaActionForm
   */
  public void prepare(HttpServletRequest request, DynaActionForm form) {
    Projektgruppe p = get(request);
    
    // update?
    if (isUpdate(p)) {
      Integer id = (Integer) form.get(Constants.PROJEKTGRUPPE_ID);
      p.setProjektgruppeId(id);
      
      // Projektgrupe holen und Daten setzen
      p = dao.getById(p);
      form.set("name", p.getName());
      form.set("beschreibung", p.getBeschreibung());
      form.set("customizingId", p.getCustomizingId());
    }
    List customizings = customizingService.getAll(p.getMandantId());
    logger.debug(customizings.size() + " Customizings in der Liste");
    request.getSession().setAttribute(Constants.CUSTOMIZINGS, customizings);
  }
  
  /**
   * Erstellt ein Projektgruppe-Objekt aus den Formulardaten.
   * 
   * @param request             der HttpServletRequest
   * @param form                das DynaActionForm
   * @return                    eine neue Projektgruppe
   */
  public Projektgruppe get(HttpServletRequest request, DynaActionForm form) {
    
    // Formulardaten holen
    Integer id = (Integer) form.get(Constants.PROJEKTGRUPPE_ID);
    Integer customizingId = (Integer) form.get(Constants.CUSTOMIZING_ID);
    String name = (String) form.get("name");
    String beschreibung = (String) form.get("beschreibung");
    
    // Objekt erzeugen
    Projektgruppe p = createProjektgruppe(request);
    p.setProjektgruppeId(id);
    p.setCustomizingId(customizingId);
    p.setName(name);
    p.setBeschreibung(beschreibung);
    return p;
  }
  
  /**
   * Erstellt ein Projektgruppe-Objekt anhand der Id aus dem Request.
   * 
   * @param request             der HttpServletRequest
   * @return                    eine neue Projektgruppe
   */
  public Projektgruppe get(HttpServletRequest request) {
    
    // Id der Projektgruppe aus dem Request holen
    String pId = request.getParameter(Constants.PROJEKTGRUPPE_ID);
    Integer id = null;
    try {
      id = new Integer(pId);
    } catch (Exception ex) {
      logger.debug("Fehlende projektgruppeId!");
      return createProjektgruppe(request);
    }
    
    // Projektgruppe überprüfen
    Projektgruppe p = createProjektgruppe(request);   
    p.setProjektgruppeId(id);
    p = dao.getById(p);
    if (p == null) {
      logger.warn("Projektgruppe mit der id " + id + " wurde nicht gefunden.");
      populate(request);
    }
    return p;
  }
  
  /**
   * Speichert die gegebene Projektgruppe ab.
   * 
   * @param request             der HttpServletRequest
   * @param p                   die zu speichernde Projektgruppe
   */
  public void save(HttpServletRequest request, Projektgruppe p) {
    
    // update?
    if (isUpdate(p)) {
      dao.update(p);
    }
    // insert
    else {
      dao.insert(p);
    }
    populate(request);
  }
  
  /**
   * Löscht eine Projektgruppe.
   * 
   * @param request             der HttpServletRequest
   * @return                    true, wenn der Löschvorgang erfolgreich war,
   *                            sonst false
   */
  public boolean delete(HttpServletRequest request) {
    
    // Projektgruppe aus dem request holen
    Projektgruppe p = get(request);
    if (p == null) {
      return false;
    }
    
    // Projektgruppe löschen
    dao.delete(p);
    populate(request);
    return true;
  }
  
  /**
   * Prüft, ob der Name der angegebenen Projektgruppe bereits vergeben ist
   * 
   * @param p                   die zu überprüfende Projektgruppe
   * @return                    true, wenn der Name der Projektgruppe
   *                            bereits vergeben ist, sonst false
   */
  public boolean exists(Projektgruppe p) {
    Integer id = p.getProjektgruppeId();
    String name = p.getName();
    
    // update?
    if (id != null && id != 0) {
      Projektgruppe pg = dao.getById(p);
      
      // Name geändert, überprüfen
      if (!name.equals(pg.getName())) {
        return dao.getByName(p) != null;
      }
    }
    
    // insert
    else {
      return dao.getByName(p) != null;
    }
    return false;
  }
  
  /**
   * Prüft, ob die angegebene Projektgruppe neu ist, oder ob es sich um
   * ein update handelt.
   *  
   * @param p                   die zu überprüfende Projektgruppe
   * @return                    true, wenn es sich um ein update handelt,
   *                            sonst false
   */
  private boolean isUpdate(Projektgruppe p) {
    Integer id = p.getProjektgruppeId();
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }
  
  /**
   * Generiert ein Projekgruppe Objekt mit dem aktuellen Mandanten.
   * 
   * @param request               der HttpServletRequest
   * @return                      eine Projektgruppe mit gesetzem Mandant
   */
  private Projektgruppe createProjektgruppe(HttpServletRequest request) {
    Projektgruppe p = new Projektgruppe();
    p.setMandantId(
        (Integer) request.getSession().getAttribute(Constants.MANDANT_ID));
    return p;
  }
}
