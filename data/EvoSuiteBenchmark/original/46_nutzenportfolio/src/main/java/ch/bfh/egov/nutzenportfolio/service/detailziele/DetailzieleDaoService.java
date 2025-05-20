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
package ch.bfh.egov.nutzenportfolio.service.detailziele;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleDao;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Implementierende Service-Klasse f�r Detailziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class DetailzieleDaoService implements DetailzieleService {
  private DetailzieleDao dao;
  private CommonService cService;
  private CustomizingService customizingService;
  private StrategischeZieleService szService;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. �bergabe des DataAcessObject-Interfaces f�r Detailziele. 
   * 
   * @param dao                   Detailziel DataAcessObject-Interface
   */
  public DetailzieleDaoService(DetailzieleDao dao) {
      this.dao = dao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#getById(Detailziel)
   */
  public Detailziel getById(Detailziel ziel) {
    return dao.getById(ziel);
  }

  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#getAll(Integer)
   */
  public List<Detailziel> getAll(Integer mandantId) {
      return dao.getAll(mandantId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#getByProjektId(Projekt)
   */
  public List<Detailziel> getByProjektId(Projekt p) {
    return dao.getByProjektId(p);
  }

  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#getByStrategischesZiel(Detailziel)
   */
  public List<Detailziel> getByStrategischesZiel(Detailziel ziel) {
    return dao.getByStrategischesZiel(ziel);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#getByPaAndStrategischesZiel(Detailziel)
   */
  public List<Detailziel> getByPaAndStrategischesZiel(Detailziel ziel) {
    return dao.getByPaAndStrategischesZiel(ziel);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#getAssignments(Detailziel)
   */
  public List<Detailziel> getAssignments(Detailziel ziel) {
    return dao.getAssignments(ziel);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#update(Detailziel)
   */
  public void update(Detailziel ziel) {
    dao.update(ziel);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#insertAssignment(Detailziel)
   */
  public void insertAssignment(Detailziel ziel) {
    dao.insertAssignment(ziel);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#deleteAssignment(Detailziel)
   */
  public void deleteAssignment(Detailziel ziel) {
    dao.deleteAssignment(ziel);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.detailziele.DetailzieleIbatisDao#deleteAssignments(Integer)
   */
  public void deleteAssignments(Integer projektattraktivitaetId) {
    dao.deleteAssignments(projektattraktivitaetId);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch �bergabe anderer,
   * ben�tigter Service-Objekte.
   * 
   * @param cService                   Common Service Objekt
   * @param customizingService         Customizing Service Objekt
   * @param szService                  Strategische Ziele Service Objekt
   */
  public void init(
      CommonService cService,
      CustomizingService customizingService,
      StrategischeZieleService szService) {
    this.cService = cService;
    this.customizingService = customizingService;
    this.szService = szService;
  }
  
  /**
   * Stellt eine Liste aller Detailziele in den request.
   * 
   * @param request                   der HttpServletRequest
   */
  public void populate(HttpServletRequest request) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    List<Detailziel> detailziele = getAll(mandantId);
    
    // Sortieren der Detailziele nach Namen
    Collections.<Detailziel>sort(detailziele, new Comparator<Detailziel>() {
      public int compare(Detailziel a, Detailziel b) {
        String name1 = a.getName().toLowerCase();
        String name2 = b.getName().toLowerCase();
        return name1.compareTo(name2);
      }
    });
    request.setAttribute(Constants.DETAILZIELE, detailziele);
  }
  
  /**
   * F�llt ein Formular mit bestehenden Daten eines Detailziels ab,
   * falls eine entsprechende id angegeben wurde.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void edit(HttpServletRequest request, DynaActionForm form) {
    if (isUpdate(request, form)) {
      Detailziel dz = createDetailziel(request, form);
      Integer mandantId = dz.getMandantId();
      dz = dao.getById(dz);
      form.set("name", dz.getName());
      form.set("strategischesZielId", dz.getStrategischesZielId());
      
      // Strategische Ziele in die Session stellen
      logger.debug("Strategische Ziele holen f�r mandantId=" + mandantId);
      List<StrategischesZiel> szs = szService.getAll(mandantId);
      request.getSession().setAttribute(Constants.STRATEGISCHE_ZIELE, szs);
    }
  }
  
  /**
   * F�hrt ein update eines bestenenden Detailziels anhand von
   * Formulardaten durch.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void update(HttpServletRequest request, DynaActionForm form) {
    logger.debug("Update Detailziel");
    if (isUpdate(request, form)) {
      logger.debug("Update durchf�hren");
      Detailziel dz = createDetailziel(request, form);
      dao.update(dz);
    }
  }
  
  /**
   * Pr�ft, ob ein Detailziel existiert und gibt es zur�ck.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          das Detailziel
   */
  public Detailziel getDetailziel(HttpServletRequest request, DynaActionForm form) {
    Detailziel dz = createDetailziel(request, form);
    return dao.getById(dz);
  }
  
  /**
   * Abfrage f�r das L�schen von einem Detailziel
   * 
   * @param request                   der HttpServletRequest
   * @param dz                        das zu l�schende Detailziel
   * @param messages                  das ActionMessages Objekt
   */
  public void deleteQuestion(
      HttpServletRequest request,
      Detailziel dz,
      ActionMessages messages) {
    
    // Verkn�pfungen pr�fen
    linkedTo(request, dz);
    
    // Best�tigungsnachricht ausgeben
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", dz.getName()));
    
    // Actions zum L�schen und Abbrechen definieren
    request.setAttribute(Constants.ACTION, Constants.VERWALTUNG_DETAILZIELE_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.DETAILZIEL_ID);
    request.setAttribute(Constants.ID_VALUE, dz.getDetailzielId());
  }
  
  /**
   * Setzt die Verkn�pfungen zu diesem Detailziel in den Request.
   * Ein Detailziel kann mit Customizings verkn�pft sein. Diese
   * Methode wird bei der L�schabfrage verwendet.
   * 
   * @param request                   der HttpServletRequest
   * @param dz                        das zu l�schende Detailziel
   */
  public void linkedTo(HttpServletRequest request, Detailziel dz) {
    HashMap<String, List> map = new HashMap<String, List>();
    
    // Customizings suchen, die dieses Detailziel ben�tigen
    List<Customizing> customizings = dao.getLinkedCustomizings(dz);
    if (customizings.size() > 0) {
      for (Customizing c : customizings) {
        if (c.getStatus()) {
          request.setAttribute("active", true);
          break;
        }
      }
      map.put("Customizings", customizings);
    }
    
    request.setAttribute("linked", map);
    
  }
  
  /**
   * L�scht ein Detailziel. Falls es mit inaktiven Customizings
   * verkn�ft ist, werden diese ebenfalls gel�scht. Wenn aktive Customizings
   * verkn�ft sind, wird das Detailziel nicht gel�scht.
   * 
   * @param request                   der HttpServletRequest
   * @param dz                        das zu l�schende Detailziel
   */
  public void delete(HttpServletRequest request, Detailziel dz) {
    
    // Verkn�pfungen pr�fen
    linkedTo(request, dz);
    Boolean active = (Boolean) request.getAttribute("active");
    
    // Aktives Customizing verkn�ft. Dies sollte nicht passieren ausser bei
    // einem direkten URL-Aufruf ...
    if (active != null && active) {
      logger.debug("Aktives Customizing verkn�pft, Detailziel wird nicht gel�scht");
      return;
    }
    
    // Customizings l�schen
    List<Customizing> customizings = dao.getLinkedCustomizings(dz);
    for (Customizing c : customizings) {
      logger.debug("L�sche Customizing " + c.getCustomizingId());
      customizingService.cascadeDelete(request, c);
    }
    
    // L�schen des Detailziels
    dao.delete(dz.getDetailzielId());
  }
  
  /**
   * Speichert ein neues Detailziel und f�gt es dem aktuellen
   * Customizing Projektattraktivit�t zu.
   * 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm mit Daten des Detailziels
   * @param errors                    ActionMessages Objekt zur Ausgabe von
   *                                  Feld-bezogenen Fehlermeldungen
   * @param messages                  ActionMessages Objekt zur Ausgabe von
   *                                  globalen Fehlermeldungen
   * @return                          true bei Erfolg, sonst false
   */
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    
    // Formulardaten holen
    String name = (String) form.get("name");
    Boolean quantifizierbar = (Boolean) form.get("quantifizierbar");
    Integer szId = (Integer) form.get(Constants.STRATEGISCHES_ZIEL_ID);
    logger.debug("name=" + name);
    logger.debug("quantifizierbar=" + quantifizierbar);
    logger.debug("szId=" + szId);
    
    // Formulardaten �berpr�fen
    if (name == null || name.length() == 0) {
      errors.add("name",
          new ActionMessage("errors.required", "Name"));
      return false;
    }
    
    Detailziel dz = new Detailziel();
    dz.setName(name);
    dz.setQuantifizierbar(quantifizierbar);
    dz.setStrategischesZielId(szId);
    dz.setMandantId(mandantId);
    
    // Auf duplikate pr�fen
    if (dao.getByName(dz) != null) {
      messages.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.duplicate", "Detailziel"));
      return false;
    }
    
    // Detailziel einf�gen
    dao.insert(dz);
    
    // Projektattraktivit�t holen
    Integer customizingId =
      (Integer) request.getSession().getAttribute(Constants.CUSTOMIZING_ID);
    Integer paId = cService.getProjektattraktivitaetIdByCustomizingId(customizingId);
    dz.setProjektattraktivitaetId(paId);
    
    // Detailziel setzen
    dao.insertAssignment(dz);
    return true;
  }
  
  /**
   * Kopiert alle zugewiesenen Detailziele eines Customizings
   * in ein neues.
   * 
   * @param mandantId                     die Id des Mandanten
   * @param paId                          die Id der aktuellen Projektattraktivit�t
   * @param newPaId                       die Id der neuen Projektattraktivit�t
   * @param quantifizierbar               quantifizierbares (true) oder nicht
   *                                      quantifizierbares (false) Detailziel
   */
  public void copy(
      Integer mandantId,
      Integer paId,
      Integer newPaId,
      boolean quantifizierbar) {
    Detailziel dz = new Detailziel();
    dz.setMandantId(mandantId);
    dz.setQuantifizierbar(quantifizierbar);
    dz.setProjektattraktivitaetId(paId);
    
    // Zugewiesene Detailziele holen
    List<Detailziel> dzs = dao.getAssignments(dz);
    for (Detailziel d : dzs) {
      d.setProjektattraktivitaetId(newPaId);
      
      // Detailziele in die neue Projektattraktivit�t kopieren
      dao.insertAssignment(d);
    }
  }
  
  /**
   * Setzt den Navigations-Status von den Detailzielen
   * eines Customizings. 
   * 
   * @param request                       HttpServletRequest
   * @param szs                           Liste aller zugewiesenen Strategischen
   *                                      Ziele
   * @param mandantId                     die Id des Mandanten
   * @param paId                          die Id der Projektattraktivit�t
   * @return                              true f�r Status gr�n, sonst false
   */
  public boolean setStatus(
      HttpServletRequest request,
      List<StrategischesZiel> szs,
      Integer mandantId,
      Integer paId) {
    
    // Quantifizierbare und nicht quantifizierbare Detailziele
    // unterscheiden, Strategische Ziele �berpr�fen
    boolean alleDzQ = true;
    boolean alleDzNQ = true;
    if (szs == null) {
      return false;
    }
    
    // Mindestens ein Detailziel pro Strategisches Ziel muss angew�hlt sein
    for (StrategischesZiel sz : szs) {
      Detailziel dZiel = new Detailziel();
      dZiel.setStrategischesZielId(sz.getStrategischesZielId());
      dZiel.setProjektattraktivitaetId(paId);
      dZiel.setMandantId(mandantId);
      logger.debug("szId=" + sz.getStrategischesZielId());
      
      // Quantifizierbar
      dZiel.setQuantifizierbar(true);
      List<Detailziel> dzs = dao.getByPaAndStrategischesZiel(dZiel);
      logger.debug(dzs.size() + " quantifizierbare Detailziele in der Liste");
      if (dzs == null || dzs.size() == 0) {
        alleDzQ = false;
      }
      
      // Nicht quantifizierbar
      dZiel.setQuantifizierbar(false);
      dzs = dao.getByPaAndStrategischesZiel(dZiel);
      logger.debug(dzs.size() + " nicht quantifizierbare Detailziele in der Liste");
      if (dzs == null || dzs.size() == 0) {
        alleDzNQ = false;
      }
    }

    HttpSession session = request.getSession();
    if(alleDzQ && alleDzNQ) {
      // Status gr�n, Status-Flag in die Session setzen
      logger.debug("Alle Strategischen Ziele haben mindestens 1 quantifizierbares Detailziel und 1 nicht quantifizierbares Detailziel");
      session.setAttribute(Constants.DETAILZIELE, true);
    }
    else {
      // Status rot, eventuell vorhandenes Flag aus der Session l�schen
      session.removeAttribute(Constants.DETAILZIELE);
    }
    return alleDzQ && alleDzNQ;
  }
  
  /**
   * Erstellt ein Objekt vom Typ Detailziel mit der id des Mandanten
   * und, falls angegeben, mit abgef�llten Formulardaten
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          Detailziel mit mandantId
   */
  private Detailziel createDetailziel(HttpServletRequest request, DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Detailziel dz = new Detailziel();
    dz.setMandantId(mandantId);
    if (form != null) {
      Integer dzId = (Integer) form.get(Constants.DETAILZIEL_ID);
      String name = (String) form.get(Constants.NAME);
      Integer szId = (Integer) form.get(Constants.STRATEGISCHES_ZIEL_ID);
      dz.setDetailzielId(dzId);
      dz.setName(name);
      dz.setStrategischesZielId(szId);
    }
    return dz;
  }
  
  /**
   * �berpr�ft, ob die Id eines Detailziels im Request ist
   * (update) oder nicht (neu). 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          true, wenn das angegebene Detailziel
   *                                  besteht, sonst false.
   */
  private boolean isUpdate(HttpServletRequest request, DynaActionForm form) {
    Integer id = (Integer) form.get(Constants.DETAILZIEL_ID);
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }

}
