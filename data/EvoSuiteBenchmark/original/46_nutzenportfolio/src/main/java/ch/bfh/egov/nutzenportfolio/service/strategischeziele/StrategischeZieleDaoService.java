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
package ch.bfh.egov.nutzenportfolio.service.strategischeziele;

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
import ch.bfh.egov.nutzenportfolio.persistence.strategischeziele.StrategischeZieleDao;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Implementierende Service-Klasse f�r Strategische Ziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class StrategischeZieleDaoService implements StrategischeZieleService {
  private StrategischeZieleDao dao;
  private CustomizingService cService;
  private DetailzieleService dzService;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. �bergabe des DataAcessObject-Interfaces f�r Strategische Ziele. 
   * 
   * @param dao                               Strategische Ziele DataAcessObject-Interface
   */
  public StrategischeZieleDaoService(StrategischeZieleDao dao) {
      this.dao = dao;
  }

  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.strategischeziele.StrategischeZieleDao#getAll(Integer)
   */
  public List<StrategischesZiel> getAll(Integer mandantId) {
      return dao.getAll(mandantId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.strategischeziele.StrategischeZieleDao#getByProjektId(Projekt)
   */
  public List<StrategischesZiel> getByProjektId(Projekt p) {
    return dao.getByProjektId(p);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.strategischeziele.StrategischeZieleDao#getAssignments(StrategischesZiel)
   */
  public List<StrategischesZiel> getAssignments(StrategischesZiel ziel) {
    return dao.getAssignments(ziel);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch �bergabe anderer,
   * ben�tigter Service-Objekte.
   * 
   * @param cService                   CustomizingService Objekt
   * @param dzService                  DetailzieleService Objekt
   */
  public void init(CustomizingService cService, DetailzieleService dzService) {
    this.cService = cService;
    this.dzService = dzService;
  }

  /**
   * Stellt eine Liste aller Strategischen Ziele in den request.
   * 
   * @param request                   der HttpServletRequest
   */
  public void populate(HttpServletRequest request) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    List strategischeZiele = getAll(mandantId);
    request.setAttribute(Constants.STRATEGISCHE_ZIELE, strategischeZiele);
  }
  
  /**
   * F�llt ein Formular mit bestehenden Daten eines Strategischen Ziels ab,
   * falls eine entsprechende id angegeben wurde.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void edit(HttpServletRequest request, DynaActionForm form) {
    if (isUpdate(request, form)) {
      StrategischesZiel sz = createStrategischesZiel(request, form);
      sz = dao.getById(sz);
      form.set("name", sz.getName());
      form.set("beschreibung", sz.getBeschreibung());
    }
  }
  
  /**
   * F�hrt ein update eines bestenenden Strategischen Ziels anhand von
   * Formulardaten durch.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void update(HttpServletRequest request, DynaActionForm form) {
    logger.debug("Update Strategisches Ziel");
    if (isUpdate(request, form)) {
      logger.debug("Update durchf�hren");
      StrategischesZiel sz = createStrategischesZiel(request, form);
      dao.update(sz);
    }
  }
  
  /**
   * Pr�ft, ob ein Strategisches Ziel existiert und gibt es zur�ck.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          das StrategischeZiel
   */
  public StrategischesZiel getStrategischesZiel(
      HttpServletRequest request,
      DynaActionForm form) {
    StrategischesZiel sz = createStrategischesZiel(request, form);
    return dao.getById(sz);
  }
  
  /**
   * Abfrage f�r das L�schen von einem Strategischen Ziel
   * 
   * @param request                   der HttpServletRequest
   * @param sz                        das zu l�schende Strategische Ziel
   * @param messages                  das ActionMessages Objekt
   */
  public void deleteQuestion(
      HttpServletRequest request,
      StrategischesZiel sz,
      ActionMessages messages) {
    
    // Verkn�pfungen pr�fen
    linkedTo(request, sz);
    
    // Best�tigungsnachricht ausgeben
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", sz.getName()));
    
    // Actions zum L�schen und Abbrechen definieren
    request.setAttribute(Constants.ACTION, Constants.VERWALTUNG_STRATEGISCHE_ZIELE_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.STRATEGISCHES_ZIEL_ID);
    request.setAttribute(Constants.ID_VALUE, sz.getStrategischesZielId());
  }
  
  /**
   * Setzt die Verkn�pfungen zu diesem Strategischen Ziel in den Request.
   * Ein Strategisches Ziel kann mit Detailzielen und / oder Customizings
   * verkn�pft sein. Diese Methode wird bei der L�schabfrage verwendet.
   * 
   * @param request                   der HttpServletRequest
   * @param sz                        das zu l�schende Strategische Ziel
   */
  public void linkedTo(HttpServletRequest request, StrategischesZiel sz) {
    HashMap<String, List> map = new HashMap<String, List>();
    
    // Customizings suchen, die dieses Strategische Ziel ben�tigen
    List<Customizing> customizings = dao.getLinkedCustomizings(sz);
    if (customizings.size() > 0) {
      for (Customizing c : customizings) {
        if (c.getStatus()) {
          request.setAttribute("active", true);
          break;
        }
      }
      map.put("Customizings", customizings);
    }
    
    // Detailziele suchen, die dieses Strategische Ziel ben�tigen
    List<Detailziel> detailziele = dao.getLinkedDetailziele(sz);
    if (detailziele.size() > 0) {
      map.put("Detailziele", detailziele);
    }
    
    request.setAttribute("linked", map);
    
  }
  
  /**
   * L�scht ein Strategisches Ziel. Falls es mit inaktiven Customizings
   * verkn�ft ist, werden diese ebenfalls gel�scht. Wenn aktive Customizings
   * verkn�ft sind, wird das Strategische Ziel nicht gel�scht. Verkn�pfte
   * Detailziele werden nicht gel�scht.
   * 
   * @param request                   der HttpServletRequest
   * @param sz                        das zu l�schende Strategische Ziel
   */
  public void delete(HttpServletRequest request, StrategischesZiel sz) {
    
    // Verkn�pfungen pr�fen
    linkedTo(request, sz);
    Boolean active = (Boolean) request.getAttribute("active");
    
    // Aktives Customizing verkn�ft. Dies sollte nicht passieren ausser bei
    // einem direkten URL-Aufruf ...
    if (active != null && active) {
      logger.debug("Aktives Customizing verkn�pft, Strategisches Ziel wird nicht gel�scht");
      return;
    }
    
    // Customizings l�schen
    List<Customizing> customizings = dao.getLinkedCustomizings(sz);
    for (Customizing c : customizings) {
      logger.debug("L�sche Customizing " + c.getCustomizingId());
      cService.cascadeDelete(request, c);
    }
    
    // Id des Strategischen Ziels aus der Detailziel Tabelle l�schen
    List<Detailziel> detailziele = dao.getLinkedDetailziele(sz);
    for (Detailziel dz : detailziele) {
      logger.debug("L�sche Zuweisung strategischesZielId des Detailziels " + dz.getDetailzielId());
      dz.setStrategischesZielId(null);
      dzService.update(dz);
    }
    
    // L�schen des Strategischen Ziels
    dao.delete(sz);
  }
  
  /**
   * Weist die angegebenen Strategischen Ziele einer Projektattraktivit�t zu.
   * 
   * @param ziele               Liste aller zuzuweisenden Strategischen Ziele
   */
  public void setProjektattraktivitaet(List<StrategischesZiel> ziele) {
    boolean delete = true;
    for (StrategischesZiel ziel : ziele) {
      
      // L�schen aller Beziehungen im ersten Durchgang
      if (delete) {
        logger.debug("L�sche Beziehungen Strategische Ziele - Projektattraktivit�t");
        dao.deleteAssignments(ziel.getProjektattraktivitaetId());
        delete = false;
      }
      
      // Einf�gen der neuen Beziehung
      dao.insertAssignment(ziel);
    }
  }
  
  /**
   * F�gt ein neues Strategisches Ziel ein.
   * 
   * @param request                       HttpServletRequest
   * @param form                          Formular mit Daten des neuen
   *                                      Strategischen Ziels
   * @param errors                        ActionMessages Objekt zur �bergabe
   *                                      von spezifischen Fehlermeldungen
   * @param messages                      ActionMessages Objekt zur �bergabe
   *                                      von globalen Fehlermeldungen
   * @return                              true bei Erfolg, sonst false
   */
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages) {
    
    // Formulardaten holen
    String name = (String) form.get("name");
    String beschreibung = (String) form.get("beschreibung");
    logger.debug("name=" + name);
    logger.debug("beschreibung=" + beschreibung);
    
    // Formulardaten �berpr�fen
    if (name == null || name.length() == 0) {
      errors.add("name",
          new ActionMessage("errors.required", "Name"));
      return false;
    }
    
    else if (beschreibung == null || beschreibung.length() == 0) {
      errors.add("beschreibung",
          new ActionMessage("errors.required", "Beschreibung"));
      return false;
    }
    
    StrategischesZiel sz = createStrategischesZiel(request, form);
    
    // Auf duplikate pr�fen
    if (dao.getByName(sz) != null) {
      messages.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.duplicate", "Strategisches Ziel"));
      return false;
    }
    
    // Strategisches Ziel einf�gen
    dao.insert(sz);
    return true;
  }

  /**
   * Holt die die zugewiesenen Strategischen Ziele zu dem angegeben
   * Customizing und gibt sie als Array von Ids zur�ck.<br/>
   * TODO Customizing Objekt �bergeben macht mehr Sinn
   * 
   * @param ziel                          Daten des aktuellen Customizings
   * @return                              Integer Array mit Ids aller
   *                                      zugewiesenen Strategischen Ziele
   */
  public Integer[] getSelectedIdArr(StrategischesZiel ziel) {
    logger.debug("Hole strategischeZieleIds: mandantId="
        + ziel.getMandantId() + ", customizingId=" + ziel.getCustomizingId());
    
    // TODO Performance-Gewinn m�glich durch eigene SQL Abfrage
    List<StrategischesZiel> szs = dao.getAssignments(ziel);
    logger.debug(szs.size() + " ids gefunden.");
    Integer[] ids = new Integer[szs.size()];
    int i = 0;
    for (StrategischesZiel sz : szs) {
      ids[i++] = sz.getStrategischesZielId();
    }

    return ids;
  }
  
  /**
   * Kopiert alle zugewiesenen Strategischen Ziele eines Customizings
   * in ein neues.
   * 
   * @param mandantId                     die Id des Mandanten
   * @param customizingId                 die Id des aktuellen Customizings
   * @param newPaId                       die Id der neuen Projektattraktivit�t
   */
  public void copy(Integer mandantId, Integer customizingId, Integer newPaId) {
    StrategischesZiel sz = new StrategischesZiel();
    sz.setMandantId(mandantId);
    sz.setCustomizingId(customizingId);
    
    // Zugewiesene Strategische Ziele holen
    List<StrategischesZiel> szs = dao.getAssignments(sz);
    for (StrategischesZiel s : szs) {
      
      // Strategische Ziele in die neue Projektattraktivit�t kopieren
      s.setProjektattraktivitaetId(newPaId);
      dao.insertAssignment(s);
    }
  }
  
  /**
   * Setzt den Navigations-Status von den Strategischen Zielen
   * eines Customizings. 
   * 
   * @param request                       HttpServletRequest
   * @param mandantId                     die Id des Mandanten
   * @param customizingId                 die Id des Customizings
   * @return                              Liste aller zugewiesenen Strategischen Ziele
   */
  public List<StrategischesZiel> setStatus(
      HttpServletRequest request,
      Integer mandantId,
      Integer customizingId) {
    StrategischesZiel sz = new StrategischesZiel();
    sz.setMandantId(mandantId);
    sz.setCustomizingId(customizingId);
    
    // Zugewiesene Strategische Ziele holen
    List<StrategischesZiel> szs = dao.getAssignments(sz);
    HttpSession session = request.getSession();
    
    // Mindestens ein Strategisches Ziele muss angew�hlt sein
    if (szs != null && szs.size() > 0) {
      
      // Status gr�n, Status-Flag in die Session setzen
      session.setAttribute(Constants.STRATEGISCHE_ZIELE, true);
      return szs;
    }
    
    // Status rot, eventuell vorhandenes Flag aus der Session l�schen 
    else {
      session.removeAttribute(Constants.STRATEGISCHE_ZIELE);
    }
    return null;
  }
  
  /**
   * Erstellt ein Objekt vom Typ StrategischesZiel mit der id des Mandanten
   * und, falls angegeben, mit abgef�llten Formulardaten
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          StrategischesZiel mit mandantId
   */
  private StrategischesZiel createStrategischesZiel(HttpServletRequest request, DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    StrategischesZiel sz = new StrategischesZiel();
    sz.setMandantId(mandantId);
    
    // Falls vorhanden, Formulardaten �bernehmen
    if (form != null) {
      Integer szId = (Integer) form.get(Constants.STRATEGISCHES_ZIEL_ID);
      String name = (String) form.get("name");
      String beschreibung = (String) form.get("beschreibung");
      sz.setStrategischesZielId(szId);
      sz.setName(name);
      sz.setBeschreibung(beschreibung);
    }
    return sz;
  }
  
  /**
   * �berpr�ft, ob die Id eines Strategischen Ziels im Request ist
   * (update) oder nicht (neu). 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          true, wenn das angegebene Strategische
   *                                  Ziel besteht, sonst false.
   */
  private boolean isUpdate(HttpServletRequest request, DynaActionForm form) {
    Integer id = (Integer) form.get(Constants.STRATEGISCHES_ZIEL_ID);
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }

}
