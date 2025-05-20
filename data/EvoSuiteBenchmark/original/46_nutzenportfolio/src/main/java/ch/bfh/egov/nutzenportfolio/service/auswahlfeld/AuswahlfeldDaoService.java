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
package ch.bfh.egov.nutzenportfolio.service.auswahlfeld;

import java.util.ArrayList;
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
import ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldDao;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Implementierende Service-Klasse für Auswahlfelder. Auswahlfelder
 * sind Eintrittswahrscheinlichkeiten, Eintrittszeitpunkte, 
 * Abstufungen und Gewichtungen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class AuswahlfeldDaoService implements AuswahlfeldService {
  private AuswahlfeldDao dao;
  private NutzenkriteriumService nService;
  private CommonService cService;
  private CustomizingService customizingService;
  private StrategischeZieleService szService;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für Auswahlfelder. 
   * 
   * @param dao                       Auswahlfeld DataAcessObject-Interface
   */
  public AuswahlfeldDaoService(AuswahlfeldDao dao) {
    this.dao = dao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getById(Auswahlfeld)
   */
  public Auswahlfeld getById(Auswahlfeld a) {
    return dao.getById(a);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getByProjektId(Auswahlfeld)
   */
  public List<Auswahlfeld> getByProjektId(Auswahlfeld a) {
    return dao.getByProjektId(a);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getByNaOpNu(Auswahlfeld)
   */
  public List<Auswahlfeld> getByNaOpNu(Auswahlfeld a) {
    return dao.getByNaOpNu(a);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getGewichtungStatus(Integer)
   */
  public List<Auswahlfeld> getGewichtungStatus(Integer naOpNuId) {
    return dao.getGewichtungStatus(naOpNuId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getAbstufungStatus(Integer)
   */
  public List<Auswahlfeld> getAbstufungStatus(Integer naOpNuId) {
    return dao.getAbstufungStatus(naOpNuId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getAnzahlGewichtungen(Integer)
   */
  public List<Auswahlfeld> getAnzahlGewichtungen(Integer naOpNuId) {
    return dao.getAnzahlGewichtungen(naOpNuId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getAnzahlAbstufungen(Integer)
   */
  public List<Auswahlfeld> getAnzahlAbstufungen(Integer naOpNuId) {
    return dao.getAnzahlAbstufungen(naOpNuId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getSelectedByPaId(Auswahlfeld)
   */
  public List<Auswahlfeld> getSelectedByPaId(Auswahlfeld a) {
    return dao.getSelectedByPaId(a);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#getSelectedByNaOpNuId(Auswahlfeld)
   */
  public List<Auswahlfeld> getSelectedByNaOpNuId(Auswahlfeld a) {
    return dao.getSelectedByNaOpNuId(a);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld.AuswahlfeldIbatisDao#deleteGewichtungen(Auswahlfeld)
   */
  public void deleteGewichtungen(Auswahlfeld a) {
    dao.deleteGewichtungen(a);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch Übergabe anderer,
   * benötigter Service-Objekte.
   * 
   * @param nService                   Nutzenkriterium Service Objekt
   * @param cService                   Common Service Objekt
   * @param customizingService         Customizing Service Objekt
   */
  public void init(
      NutzenkriteriumService nService,
      CommonService cService,
      CustomizingService customizingService,
      StrategischeZieleService szService) {
    this.nService = nService;
    this.cService = cService;
    this.customizingService = customizingService;
    this.szService = szService;
  }
  
  /**
   * Generiert Auswahlfelder und füllt diese nach Typ und
   * Level ab.
   * 
   * @param request           der HttpServletRequest
   * @param path              der Pfad der momentanen Action
   */
  public void list(HttpServletRequest request, String path) {
    logger.debug("Pfad: " + path);
    
    // Neues Auswahlfeld generieren
    Auswahlfeld a = createAuswahlfeld(request, null);
    
    // Eintrittswahrscheinlichkeiten
    if (path.contains(Constants.VERWALTUNG_EINTRITTSWAHRSCHEINLICHKEITEN_ACTION)) {
      logger.debug("Eintrittswahrscheinlichkeiten holen");
      a = setType(a, Constants.EINTRITTSWAHRSCHEINLICHKEIT);
    }
    
    // Eintrittszeitpunkte
    else if (path.contains(Constants.VERWALTUNG_EINTRITTSZEITPUNKTE_ACTION)) {
      logger.debug("Eintrittszeitpunkte holen");
      a = setType(a, Constants.EINTRITTSZEITPUNKT);
    }
    
    // Abstufungen
    else if (path.contains(Constants.VERWALTUNG_ABSTUFUNGEN_ACTION)) {
      logger.debug("Abstufungen holen");
      a = setType(a, Constants.ABSTUFUNG);
    }
    
    // Gewichtungen
    else if (path.contains(Constants.VERWALTUNG_GEWICHTUNG_ACTION)) {
      logger.debug("Gewichtungen holen");
      a = setType(a, Constants.GEWICHTUNG);
    }
    
    // Alle Levels holen
    List<Auswahlfeld> afs = new ArrayList<Auswahlfeld>();
    for (int i = 1; i < 6; i++) {
      a.setLevel(i);
      afs.addAll(dao.getAuswahlFelder(a));  
    }
    
    logger.debug(afs.size() + " Auswahlfelder in der Liste");
    request.setAttribute(Constants.AUSWAHLFELDER, afs);
  }
  
  /**
   * Füllt ein Formular mit bestehenden Daten eines Auswahlfelds ab,
   * falls eine entsprechende id angegeben wurde.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void edit(HttpServletRequest request, DynaActionForm form) {
    if (isUpdate(request, form)) {
      Auswahlfeld af = createAuswahlfeld(request, form);
      af = dao.getById(af);
      form.set(Constants.NAME, af.getName());
      form.set("level", af.getLevel());
    }
  }
  
  /**
   * Führt ein update eines bestenenden Auswahlfelds anhand von
   * Formulardaten durch.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void update(HttpServletRequest request, DynaActionForm form) {
    logger.debug("Update Auswahlfeld");
    if (isUpdate(request, form)) {
      logger.debug("Update durchführen");
      Auswahlfeld af = createAuswahlfeld(request, form);
      dao.update(af);
    }
  }
  
  /**
   * Prüft, ob ein Auswahlfeld existiert und gibt es zurück.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          das Auswahlfeld
   */
  public Auswahlfeld getAuswahlfeld(HttpServletRequest request, DynaActionForm form) {
    Auswahlfeld af = createAuswahlfeld(request, form);
    return dao.getById(af);
  }
  
  /**
   * Abfrage für das Löschen von einem Auswahlfeld.
   * 
   * @param request                   der HttpServletRequest
   * @param af                        das zu löschende Auswahlfeld
   * @param messages                  das ActionMessages Objekt
   */
  public void deleteQuestion(
      HttpServletRequest request,
      Auswahlfeld af,
      ActionMessages messages) {
    
    // Verknüpfungen prüfen
    linkedTo(request, af);
    
    // Bestätigungsnachricht ausgeben
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", af.getName()));
    
    // Actions zum Löschen und Abbrechen definieren
    String action = "";
    if (af.getEintrittswahrscheinlichkeit()) {
      action = Constants.VERWALTUNG_EINTRITTSWAHRSCHEINLICHKEITEN_ACTION + ".do";
    }
    else if (af.getEintrittszeitpunkt()) {
      action = Constants.VERWALTUNG_EINTRITTSZEITPUNKTE_ACTION + ".do";
    }
    else if (af.getAbstufung()) {
      action = Constants.VERWALTUNG_ABSTUFUNGEN_ACTION + ".do";
    }
    else if (af.getGewichtung()) {
      action = Constants.VERWALTUNG_GEWICHTUNG_ACTION + ".do";
    }
    request.setAttribute(Constants.ACTION, action);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.AUSWAHLFELD_ID);
    request.setAttribute(Constants.ID_VALUE, af.getAuswahlfeldId());
  }
  
  /**
   * Setzt die Verknüpfungen zu diesem Auswahlfeld in den Request.
   * Ein Auswahlfeld kann mit Customizings verknüpft sein. Diese
   * Methode wird bei der Löschabfrage verwendet.
   * 
   * @param request                   der HttpServletRequest
   * @param af                        das zu löschende Auswahlfeld
   */
  public void linkedTo(HttpServletRequest request, Auswahlfeld af) {
    HashMap<String, List> map = new HashMap<String, List>();
    
    // Customizings suchen, die dieses Detailziel benötigen
    List<Customizing> customizings = dao.getLinkedPaCustomizings(af);
    customizings.addAll(dao.getLinkedNaOpNuCustomizings(af));
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
   * Löscht ein Auswahlfeld. Falls es mit inaktiven Customizings
   * verknüft ist, werden diese ebenfalls gelöscht. Wenn aktive Customizings
   * verknüft sind, wird das Auswahlfeld nicht gelöscht.
   * 
   * @param request                   der HttpServletRequest
   * @param af                        das zu löschende Auswahlfeld
   */
  public void delete(HttpServletRequest request, Auswahlfeld af) {
    
    // Verknüpfungen prüfen
    linkedTo(request, af);
    Boolean active = (Boolean) request.getAttribute("active");
    
    // Aktives Customizing verknüft. Dies sollte nicht passieren ausser bei
    // einem direkten URL-Aufruf ...
    if (active != null && active) {
      logger.debug("Aktives Customizing verknüpft, Auswahlfeld wird nicht gelöscht");
      return;
    }
    
    // Customizings löschen
    List<Customizing> customizings = dao.getLinkedPaCustomizings(af);
    customizings.addAll(dao.getLinkedNaOpNuCustomizings(af));
    for (Customizing c : customizings) {
      logger.debug("Lösche Customizing " + c.getCustomizingId());
      customizingService.cascadeDelete(request, c);
    }
    
    // Löschen des Auswahlfelds
    dao.delete(af);
  }
  
  /**
   * Löscht alle Verknüpfungen von Auswahlfeldern zu einer Projektattraktivität.
   * 
   * @param paId                      die Id der Projektattraktivität
   */
  public void deletePaAssignmentsById(Integer paId) {
    Auswahlfeld af = new Auswahlfeld();
    af.setProjektattraktivitaetId(paId);
    af = setType(af, Constants.EINTRITTSWAHRSCHEINLICHKEIT);
    dao.deleteAssignments(af);
    af = setType(af, Constants.EINTRITTSZEITPUNKT);
    dao.deleteAssignments(af);
  }
  
  /**
   * Löscht alle Verknüpfungen von Auswahlfeldern zu einer
   * Nutzenattraktivität oder einem Operativen Nutzen.
   * 
   * @param naOpNuId                  die Id der Nutzenattraktivität
   *                                  oder des Operativen Nutzens
   */
  public void deleteNaOpNuAssignmentsById(Integer naOpNuId) {
    Auswahlfeld af = new Auswahlfeld();
    af.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    af = setType(af, Constants.ABSTUFUNG);
    dao.deleteNaOpNuAssignments(af);
    af = setType(af, Constants.GEWICHTUNG);
    dao.deleteNaOpNuAssignments(af);
  }

  /**
   * Generiert Auswahlfelder und füllt diese nach Typ und
   * Level ab.
   * 
   * @param request           der HttpServletRequest
   * @param path              der Pfad der momentanen Action
   * @param form              das DynaActionForm
   */
  public void populate(HttpServletRequest request, String path, DynaActionForm form) {
    
    // Wir wollen kein [Ljava.lang.String;@xxxxxxx im Definition Feld ...
    try {
      form.set("name", null);
    } catch (IllegalArgumentException ex) {}
    
    // Benötigte Id's
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    
    // Neues Auswahlfeld generieren
    Auswahlfeld a = createAuswahlfeld(request, null);
    
    // Realisierbarkeit
    logger.debug("Pfad: " + path);
    if (path.contains(Constants.REALISIERBARKEIT_ACTION)) {
      Integer paId = cService.getProjektattraktivitaetIdByCustomizingId(customizingId);
      a.setProjektattraktivitaetId(paId);
      
      // Eintrittswahrscheinlichkeit abfüllen
      a = setType(a, Constants.EINTRITTSWAHRSCHEINLICHKEIT);
      put(request, a, Constants.EINTRITTSWAHRSCHEINLICHKEIT, 3);
      
      // Selektiere Auswahlfelder
      preselect(request, a, Constants.EINTRITTSWAHRSCHEINLICHKEIT, false);
      
      // Eintrittszeitpunkt abfüllen
      a = setType(a, Constants.EINTRITTSZEITPUNKT);
      put(request, a, Constants.EINTRITTSZEITPUNKT, 3);
      
      // Selektiere Auswahlfelder
      preselect(request, a, Constants.EINTRITTSZEITPUNKT, false);
      
      // Typen in den Request stellen für das Hinzufügen von neuen Begriffen
      request.setAttribute(Constants.EINTRITTSWAHRSCHEINLICHKEITEN,
          Constants.EINTRITTSWAHRSCHEINLICHKEIT);
      request.setAttribute(Constants.EINTRITTSZEITPUNKTE,
          Constants.EINTRITTSZEITPUNKT);

      // Anzahl Strategische Ziele holen
      Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
      StrategischesZiel sz = new StrategischesZiel();
      sz.setCustomizingId(customizingId);
      sz.setMandantId(mandantId);
      List<StrategischesZiel> szs = szService.getAssignments(sz);
      request.setAttribute("totalStrategischeZiele", szs.size());
    }

    // Stufenbeschriebe abfüllen
    else if (path.contains(Constants.STUFENBESCHRIEBE_ACTION) ||
        path.contains(Constants.GEWICHTUNG_ACTION)) {
      
      // Abstufungen holen
      Integer levels =
          (Integer) request.getSession().getAttribute(Constants.ABSTUFUNGEN);
      List<Integer> list = new ArrayList<Integer> ();
      
      // Niedrigste ist immer gewählt (1)
      list.add(new Integer(1));
      
      // Wenn 3 Abstufungen: Niedrigste, mittlere  und
      // höchste auswählen (1 + 3 + 5)
      if (levels == 3) {
        list.add(new Integer(3));
      }
      
      // Wenn 4 Abstufungen: Niedrigste, mittlere, zweithöchste und
      // Höchste auswählen (1 + 3 + 4 + 5)
      if (levels == 4) {
        list.add(new Integer(3));
        list.add(new Integer(4));
      }
      
      // Alle 5 Abstufungen (1 + 2 +3 + 4 + 5)
      if (levels == 5) {
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
      }
      
      // Höchste ist immer gewählt (5)
      list.add(new Integer(5));
      request.setAttribute(Constants.STUFEN, list);
      
      // Auswahlfelder abfüllen
      if (path.contains(Constants.STUFENBESCHRIEBE_ACTION)) {
        a = setType(a, Constants.ABSTUFUNG);
        put(request, a, Constants.ABSTUFUNG, 5);
        
        // Typ in den Request stellen für das Hinzufügen von neuen Begriffen
        request.setAttribute(Constants.ABSTUFUNGEN,
            Constants.ABSTUFUNG);
      }
      else {
        a = setType(a, Constants.GEWICHTUNG);
        put(request, a, Constants.GEWICHTUNG, 5);
        
        // Typ in den Request stellen für das Hinzufügen von neuen Begriffen
        request.setAttribute(Constants.GEWICHTUNGEN,
            Constants.GEWICHTUNG);
      }
      
      // Selektiere Auswahlfelder
      Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
      a.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      preselect(request, a, null, true);
      
      // Operativer Nutzen -> Projektbetroffene
      if (cService.isOperativerNutzen(request)) {
        request.setAttribute(Constants.PROJEKTBETROFFENE, true);
      }
      
      // Nutzenkriterien abfüllen
      nService.populate(request);
    }
    
  }
  
  /**
   * Weist die angewählten Einträge der Auswahlfelder der aktuellen
   * Projektattraktivitaet zu.
   * 
   * @param request           der HttpServletRequest
   * @param form              das Formular der Action
   * @param path              der Pfad der momentanen Action
   */
  public void setAuswahlfelder(
      HttpServletRequest request,
      DynaActionForm form,
      String path) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer mandantId =
      (Integer) session.getAttribute(Constants.MANDANT_ID);
    
    // Neues Auswahlfeld generieren
    Auswahlfeld a = createAuswahlfeld(request, null);
    
    // Realisierbarkeit
    logger.debug("Pfad: " + path);
    if (path.contains(Constants.REALISIERBARKEIT_ACTION)) {
      Integer pId = cService.getProjektattraktivitaetIdByCustomizingId(customizingId);
      a.setProjektattraktivitaetId(pId);
      
      // Eintrittswahrscheinlichkeiten updaten
      a = setType(a, Constants.EINTRITTSWAHRSCHEINLICHKEIT);
      dao.deleteAssignments(a);
      insertAssignments(a, Constants.EINTRITTSWAHRSCHEINLICHKEIT, form, 3);
      
      // Eintrittszeitpunkt abfüllen
      a = setType(a, Constants.EINTRITTSZEITPUNKT);
      dao.deleteAssignments(a);
      insertAssignments(a, Constants.EINTRITTSZEITPUNKT, form, 3);
      setRealisierbarkeitStatus(request, pId);
    }
    
    // Stufenbeschriebe abfüllen
    else if (path.contains(Constants.STUFENBESCHRIEBE_ACTION)) {
      Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
      logger.debug("naOpNuId=" + naOpNuId);
      a.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      a = setType(a, Constants.ABSTUFUNG);
      insertNaOpNuAssignment(a, Constants.ABSTUFUNG, request, form, 5);
      logger.debug("stufenbeschriebe");
      boolean isNa = !cService.isOperativerNutzen(request);
      setStufenStatus(request, naOpNuId, isNa);
    }
    
    // Gewichtung abfüllen
    else if (path.contains(Constants.GEWICHTUNG_ACTION)) {
      
      // allfällige direkte Gewichtung löschen
      logger.debug("lösche allfällige direkte Gewichtungen.");
      Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
      nService.unsetAssignmentGewichtung(naOpNuId);
      
      a.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      a = setType(a, Constants.GEWICHTUNG);
      insertNaOpNuAssignment(a, Constants.GEWICHTUNG, request, form, 5);
      logger.debug("gewichtung");
      boolean isNa = !cService.isOperativerNutzen(request);
      Nutzenkriterium n = new Nutzenkriterium();
      n.setMandantId(mandantId);
      n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      List<Nutzenkriterium> kriterien = nService.getAssignments(n);
      setGewichtungStatus(request, kriterien, naOpNuId, isNa);
    }
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumDaoService#next(HttpServletRequest)
   */
  public boolean next(HttpServletRequest request) {
    return nService.next(request);
  }
  
  /**
   * Generiert ein Auswahlfeld Objekt mit dem aktuellen Mandanten.
   * 
   * @param request               der HttpServletRequest
   * @param form                  das DynaActionForm
   * @return                      ein Auswahlfeld mit gesetzem Mandant
   */
  public Auswahlfeld createAuswahlfeld(HttpServletRequest request, DynaActionForm form) {
    Auswahlfeld a = new Auswahlfeld();
    a.setMandantId(
        (Integer) request.getSession().getAttribute(Constants.MANDANT_ID));
    if (form != null) {
      Integer id = (Integer) form.get(Constants.AUSWAHLFELD_ID);
      String name = (String) form.get(Constants.NAME);
      Integer level = (Integer) form.get("level");
      a.setAuswahlfeldId(id);
      a.setName(name);
      a.setLevel(level);
    }
    return a;
  }

  /**
   * Setzt den Typ eines Auswahlfelds anhand von Konstanten.
   * 
   * @param a                   das Auswahlfeld
   * @param type                der zu setzende Typ des Auswahlfelds
   * @return                    das Auswahl-Feld mit dem gesetzten Typ
   */
  public Auswahlfeld setType(Auswahlfeld a, int type) {
    
    switch (type) {
      case Constants.EINTRITTSWAHRSCHEINLICHKEIT:
        a.setEintrittswahrscheinlichkeit(true);
        a.setEintrittszeitpunkt(false);
        a.setAbstufung(false);
        a.setGewichtung(false);
        break;
      case Constants.EINTRITTSZEITPUNKT:
        a.setEintrittswahrscheinlichkeit(false);
        a.setEintrittszeitpunkt(true);
        a.setAbstufung(false);
        a.setGewichtung(false);
        break;
      case Constants.ABSTUFUNG:
        a.setEintrittswahrscheinlichkeit(false);
        a.setEintrittszeitpunkt(false);
        a.setAbstufung(true);
        a.setGewichtung(false);
        break;
      case Constants.GEWICHTUNG:
        a.setEintrittswahrscheinlichkeit(false);
        a.setEintrittszeitpunkt(false);
        a.setAbstufung(false);
        a.setGewichtung(true);
        break;
    }
    return a;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.service.common.CommonDaoService#isOperativerNutzen(HttpServletRequest)
   */
  public boolean isOperativerNutzen(HttpServletRequest request) {
    return cService.isOperativerNutzen(request);
  }
  
  /**
   * Speichert ein neues Auswahlfeld und fügt es dem aktuellen
   * Customizing zu.
   * 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm mit Daten des Auswahlfelds
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
    String[] names = (String[]) form.get("name");
    Integer[] types = (Integer[]) form.get("type");
    Integer[] lvls = (Integer[]) form.get("lvl");
    
    String name = null;
    Integer pos = null;
    for (int i = 0; i < names.length; i++) {
      logger.debug("names[" + i + "]=" + names[i]);
      if (names[i] != null && !names[i].equals("")) {
        name = names[i];
        pos = i;
      }
    }
    for (int i = 0; i < types.length; i++) {
      logger.debug("types[" + i + "]=" + types[i]);
    }
    for (int i = 0; i < lvls.length; i++) {
      logger.debug("lvls[" + i + "]=" + lvls[i]);
    }
    
    // Formulardaten überprüfen
    if (name == null) {
      logger.debug("Name nicht vorhanden.");
      errors.add("name",
          new ActionMessage("errors.required", "Name"));
      return false;
    }
    
    Integer type = types[pos];
    Integer lvl = lvls[pos];

    Auswahlfeld af = new Auswahlfeld();
    af.setMandantId(mandantId);
    af.setName(name);
    af.setLevel(lvl);
    setType(af, type);
    
    // Auf duplikate prüfen
    Auswahlfeld a = dao.getByName(af);
    Boolean match = false;
    if (a != null) {
      if (a.getAbstufung() && type == Constants.ABSTUFUNG) { match = true; }
      if (a.getGewichtung() && type == Constants.GEWICHTUNG) { match = true; }
      if (a.getEintrittswahrscheinlichkeit() &&
          type == Constants.EINTRITTSWAHRSCHEINLICHKEIT) { match = true; }
      if (a.getEintrittszeitpunkt() &&
          type == Constants.EINTRITTSZEITPUNKT) { match = true; }
    }
    
    if (a != null && match) {
      logger.debug(name + " ist ein Duplikat");
      messages.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.duplicate", "Eintrag"));
      return false;
    }
    
    // Auswahlfeld einfügen
    dao.insert(af);
    
    return true;
  }
  
  /**
   * Kopiert alle zugewiesenen Auswahlfelder eines Customizings
   * in ein neues.
   * 
   * @param paId                          die Id der aktuellen Projektattraktivität
   * @param newPaId                       die Id der neuen Projektattraktivität
   * @param naOpNuId                      die Id der aktuellen Nutzenattraktivität
   *                                      bzw. des aktuellen Operativen Nutzens
   * @param newNaOpNuId                   die Id der neuen Nutzenattraktivität
   *                                      bzw. des neuen Operativen Nutzens
   * @param type                          der Typ des Auswahlfelds
   */
  public void copy(
      Integer paId,
      Integer newPaId,
      Integer naOpNuId,
      Integer newNaOpNuId,
      Integer type) {
    Auswahlfeld a = new Auswahlfeld();
    
    // Typ setzen
    this.setType(a, type);

    // Projektattraktivität
    if (paId != null) {
      a.setProjektattraktivitaetId(paId);
      List<Auswahlfeld> afs = dao.getSelectedByPaId(a);
      for (Auswahlfeld af : afs) {
        af.setProjektattraktivitaetId(newPaId);
        dao.insertAssignment(af);
      }
    }
    
    // Nutzenattraktivität oder Operativer Nutzen
    else {
      a.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      List<Auswahlfeld> afs = dao.getSelectedByNaOpNuId(a);
      for (Auswahlfeld af : afs) {
        af.setNutzenattraktivitaetOperativerNutzenId(newNaOpNuId);
        dao.insertNaOpNuAssignment(af);
      }
    }
  }
  
  /**
   * Setzt den Navigations-Status von den Auswahlfeldern der
   * Realisierbarkeit eines Customizings. 
   * 
   * @param request                       HttpServletRequest
   * @param paId                          die Id der Projektattraktivität
   * @return                              true für Status grün, sonst false
   */
  public boolean setRealisierbarkeitStatus(
      HttpServletRequest request,
      Integer paId) {
    
    // Zugewiesene Auswahlfelder holen
    List<Auswahlfeld> afs = dao.getRealisierbarkeitStatus(paId);
    HttpSession session = request.getSession();
    
    // Mindestens ein Auswahlfeld muss angewählt sein
    if (afs != null && afs.size() > 0) {
      
      // Status grün, Status-Flag in die Session setzen
      session.setAttribute(Constants.REALISIERBARKEIT, true);
      return true;
    }
    else {
      // Status rot, eventuell vorhandenes Flag aus der Session löschen
      session.removeAttribute(Constants.REALISIERBARKEIT);
    }
    return false;
  }
  
  /**
   * Setzt den Navigations-Status von den Auswahlfeldern
   * der Abstufungen eines Customizings. 
   * 
   * @param request                       HttpServletRequest
   * @param naOpNuId                      die Id der Nutzenattraktivität
   *                                      bzw. des Operativen Nutzens
   * @param isNa                          true für Nutzenattraktivität, sonst false
   * @return                              true für Status grün, sonst false
   */
  public boolean setStufenStatus(
      HttpServletRequest request,
      Integer naOpNuId,
      boolean isNa) {
    
    // Zugewiesene Nutzenkriterien holen
    HttpSession session = request.getSession();
    Integer mandantId =
      (Integer) session.getAttribute(Constants.MANDANT_ID);
    Nutzenkriterium n = new Nutzenkriterium();
    n.setMandantId(mandantId);
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    List<Nutzenkriterium> kriterien = nService.getAssignments(n);
    
    // Zugewiesene Auswahlfelder holen
    List<Auswahlfeld> abstufungen = dao.getAbstufungStatus(naOpNuId);
    
    if (kriterien != null && abstufungen != null) {
      logger.debug(kriterien.size() + " Nutzenkriterien in der Liste");
      logger.debug(abstufungen.size() + " Abstufungen in der Liste");      
    }
    
    // Mindestens ein Auswahlfeld pro Nutzenkriterium muss angewählt sein
    if (abstufungen != null && kriterien != null && abstufungen.size() > 0 &&
        kriterien.size() > 0 && kriterien.size() <= abstufungen.size()) {
      
      // Nutzenattraktivität 
      if (isNa) {
        session.setAttribute(Constants.ABSTUFUNGEN_NA, true);
      }
      
      // Operativer Nutzen
      else {
        session.setAttribute(Constants.ABSTUFUNGEN_OP_NU, true);
      }
      return true;
    }
    
    // Status rot, eventuell vorhandenes Flag aus der Session löschen
    else {
      
      // Nutzenattraktivität
      if (isNa) {
        session.removeAttribute(Constants.ABSTUFUNGEN_NA);
      }
      
      // Operativer Nutzen
      else {
        session.removeAttribute(Constants.ABSTUFUNGEN_OP_NU);
      }
    }
    return false;
  }
  
  /**
   * Setzt den Navigations-Status von den Auswahlfeldern
   * der Gewichtung eines Customizings. 
   * 
   * @param request                       HttpServletRequest
   * @param kriterien                     Liste aller zugewiesenen Kriterien
   * @param naOpNuId                      die Id der Nutzenattraktivität
   *                                      bzw. des Operativen Nutzens
   * @param isNa                           true für Nutzenattraktivität, sonst false
   * @return                              true für Status grün, sonst false
   */
  public boolean setGewichtungStatus(
      HttpServletRequest request,
      List<Nutzenkriterium> kriterien,
      Integer naOpNuId,
      boolean isNa) {
    HttpSession session = request.getSession();
    
    // Gewichtung durch Fragebogen?
    List<Auswahlfeld> gewichtungen = dao.getGewichtungStatus(naOpNuId);
    if (gewichtungen != null && gewichtungen.size() > 0) {
      logger.debug("gewichtungen.size()=" + gewichtungen.size()
          + " bei naOpNuId=" + naOpNuId);      
      if (isNa) {
        logger.debug("GEWICHTUNGEN_NA=true");
        session.setAttribute(Constants.GEWICHTUNGEN_NA, true);
      }
      else {
        logger.debug("GEWICHTUNGEN_OP_NU=true");
        session.setAttribute(Constants.GEWICHTUNGEN_OP_NU, true);
      }
      return true;
    }
    
    // Direkte Gewichtung?
    else if (kriterien != null) {
      logger.debug("direkte Gewichtung bei naOpNuId=" + naOpNuId);
      for (Nutzenkriterium k : kriterien) {
        logger.debug("nutzenkriteriumId=" + k.getNutzenkriteriumId());
        logger.debug("gewichtung=" + k.getGewichtung());
        if (k.getGewichtung() == null) {
          logger.debug("direkte Gewichtung null");
          return false;
        }
      }
      if (isNa) {
        logger.debug("GEWICHTUNGEN_NA=true");
        session.setAttribute(Constants.GEWICHTUNGEN_NA, true);
      }
      else {
        logger.debug("GEWICHTUNGEN_OP_NU=true");
        session.setAttribute(Constants.GEWICHTUNGEN_OP_NU, true);
      }
      return true;
    }
    else {
      logger.debug("Gewichtungsstatus=false");
      return false;
    }
  }
  
  /**
   * Überprüft, ob ein Customizing komplett ist.
   * 
   * @param request                   der HttpServletRequest
   * @return                          true bei komplettem Customizing, sonst false
   */
  public boolean customizingComplete(HttpServletRequest request) {
    return customizingService.complete(request);
  }
  
  /**
   * Setzt für jeden gegebenen Level ein Auswahlfeld in den Request.
   * 
   * @param request           der HttpServletRequest
   * @param a                 das Auswahlfeld
   * @param type              der Typ des Auswahlfelds
   * @param levels            die zu setzenden levels
   */
  private void put(
      HttpServletRequest request,
      Auswahlfeld a,
      int type,
      int levels) {
    
    // setze für jeden Level ein Auswahlfeld 
    for (int i = 1; i <= levels; i++) {
      a.setLevel(i);
      
      // Inhalte der Auswahlfelder holen
      List<Auswahlfeld> list = dao.getAuswahlFelder(a);
      
      // Auswahl-Felder in den request setzen.
      String name = Constants.SELECTBOX + "_" + type + "_" + i;
      logger.debug("Setze Auswahlfeld " + name + " in den request");
      logger.debug("Inhalt des Auswahlfelds: " + list.size() + " Elemente");
      request.setAttribute(name, list);
    }
  }
  
  /**
   * Speichert pro Auswahlfeld und Level einen Eintrag für die
   * aktuelle Projektattraktivitaet
   * 
   * @param a                   das zu speicherende Auswahlfeld
   * @param levels              die zu speichernden levels
   */
  private void insertAssignments(
      Auswahlfeld a,
      int type,
      DynaActionForm form,
      int levels) {
    
    // für jeden Level einen Eintrag des jeweiligen Auswahlfelds setzen
    for (int i = 1; i <= levels; i++) {
      String name = Constants.SELECTBOX + "_" + type + "_" + i;
      logger.debug("Hole Auswahlfeld " + name + " vom Formular");
      Integer aId = (Integer) form.get(name);
      if (aId != null) {
        logger.debug("Gewählter Wert: " + aId);
        a.setAuswahlfeldId(aId);
        dao.insertAssignment(a);
      }
    }
  }
  
  /**
   * Speichert pro Auswahlfeld und Level einen Eintrag für die
   * aktuelle Nutzenattraktivität bzw. den Operativen Nutzen
   * 
   * @param a                   das zu speicherende Auswahlfeld
   * @param levels              die zu speichernden levels
   */
  private void insertNaOpNuAssignment(
      Auswahlfeld a,
      int type,
      HttpServletRequest request,
      DynaActionForm form,
      int levels) {
    
    // Nutzenkriterien holen
    Nutzenkriterium kriterium = new Nutzenkriterium();
    kriterium.setNutzenattraktivitaetOperativerNutzenId(
        a.getNutzenattraktivitaetOperativerNutzenId());
    kriterium.setMandantId(a.getMandantId());
    List<Nutzenkriterium> nutzenkriterien = nService.getAssignments(kriterium);
    
    // alte Zuweisungen löschen
    Integer kategorieId = (Integer) form.get(Constants.KATEGORIE_ID);
    for (Nutzenkriterium n : nutzenkriterien) {
      logger.debug("kategorie: " + n.getKategorieId() + ", " + kategorieId);
      if (kategorieId.equals(n.getKategorieId()) ||
          type == Constants.GEWICHTUNG) {
        a.setNutzenkriteriumId(n.getNutzenkriteriumId());
        logger.debug("Lösche Zuweisungen: "
            + " eintrittswahrscheinlichkeit=" + a.getEintrittswahrscheinlichkeit()
            + ", eintrittszeitpunkt=" + a.getEintrittszeitpunkt()
            + ", abstufung=" + a.getAbstufung()
            + ", gewichtung=" + a.getGewichtung()
            + ", nutzenkriteriumId=" + a.getNutzenkriteriumId()
            + ", nutzenattraktivitaetOperativerNutzenId="
            + a.getNutzenattraktivitaetOperativerNutzenId());
        dao.deleteNaOpNuAssignmentsByNutzenkriterium(a);
      }
    }
    
    // für jeden Level einen Eintrag des jeweiligen Auswahlfelds setzen
    for (int i = 1; i <= levels; i++) {
      a.setLevel(i);
      for (Nutzenkriterium n : nutzenkriterien) {
        a.setNutzenkriteriumId(n.getNutzenkriteriumId());
        String name = Constants.SELECTBOX
            + "_" + type
            + "_" + i;
        
        // Gewichtungen werden nicht mehr pro Nutzenkriterium bestimmt ...
        // Design change
        if (type != Constants.GEWICHTUNG) {
          name += "_" + n.getNutzenkriteriumId();
        }
        
        logger.debug("Hole Auswahlfeld " + name + " vom Formular");
        String aIdStr = request.getParameter(name);
        logger.debug("aIdStr=" + aIdStr);
        Integer aId;
        
        // neue Zuweisung erstellen
        if (aIdStr != null && (aId = new Integer(aIdStr)) != null) {
          
          a.setAuswahlfeldId(aId);
          logger.debug("Erstelle Zuweisung: auswahlfeldId=" + aId
              + ", nutzenkriteriumId=" + a.getNutzenkriteriumId()
              + ", nutzenattraktivitaetOperativerNutzenId="
              + a.getNutzenattraktivitaetOperativerNutzenId());
          dao.insertNaOpNuAssignment(a);
        }
      }
    }
  }
  
  /**
   * Selektiert die angewählten Auswahlfelder.
   * 
   * @param request                 der HttpServletRequest
   * @param a                       die Daten der zu selektierenden Auswahlfelder
   * @param type                    der Typ des Auswahlfelds
   * @param twoDimensional          true wenn die Levels auch berücksichtigt 
   *                                werden sollen, sonst false
   */
  private void preselect(
      HttpServletRequest request,
      Auswahlfeld a,
      Integer type,
      boolean twoDimensional) {
    
    // Selektierte Auswahlfelder der Projektattraktivität holen
    List<Auswahlfeld> afs = null;
    if (type != null && (type.equals(Constants.EINTRITTSWAHRSCHEINLICHKEIT) ||
        type.equals(Constants.EINTRITTSZEITPUNKT))) {
      afs = dao.getSelectedByPaId(a);
    }
    
    // Selektierte Auswahlfelder der Nutzenattraktivität bzw.
    // des Operativen Nutzens holen
    else {
      afs = dao.getSelectedByNaOpNuId(a);
    }
    
    // Pro Level (1-5) eine HashMap erstellen
    HashMap<String, HashMap> map = new HashMap<String, HashMap>();
    HashMap<Integer, Integer> lvl1 = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> lvl2 = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> lvl3 = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> lvl4 = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> lvl5 = new HashMap<Integer, Integer>();
    
    // Durch alle selektierten Auswahlfelder gehen
    Integer[] single = new Integer[6];
    for (Auswahlfeld af : afs) {
      Integer lvl = af.getLevel();
      logger.debug("lvl=" + lvl);
      
      // Level berücksichtigen
      if (twoDimensional) {
        Integer nutzenkriteriumId = af.getNutzenkriteriumId();
        logger.debug("nutzenkriteriumId=" + nutzenkriteriumId);
        Integer auswahlfeldId = af.getAuswahlfeldId();
        if (lvl.equals(1)) {
          lvl1.put(nutzenkriteriumId, auswahlfeldId);
        }
        else if (lvl.equals(2)) {
          lvl2.put(nutzenkriteriumId, auswahlfeldId);
        }
        else if (lvl.equals(3)) {
          lvl3.put(nutzenkriteriumId, auswahlfeldId);
        }
        else if (lvl.equals(4)) {
          lvl4.put(nutzenkriteriumId, auswahlfeldId);
        }
        else if (lvl.equals(5)) {
          lvl5.put(nutzenkriteriumId, auswahlfeldId);
        }
      }
      else {
        single[lvl] = af.getAuswahlfeldId();
      }
    }
    
    // Selektierte Auswahlfelder in den Request stellen
    if (twoDimensional) {
      map.put("1", lvl1);
      map.put("2", lvl2);
      map.put("3", lvl3);
      map.put("4", lvl4);
      map.put("5", lvl5);
      logger.debug("Selektierte Auswahlfelder in den Request stellen");
      request.setAttribute("selected", map);
    }
    else if (type.equals(Constants.EINTRITTSWAHRSCHEINLICHKEIT)) {
      request.setAttribute("ew", single);
    }
    else if (type.equals(Constants.EINTRITTSZEITPUNKT)) {
      request.setAttribute("ez", single);
    }
  }
  
  /**
   * Überprüft, ob die Id eines Auswahlfelds im Request ist
   * (update) oder nicht (neu). 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          true, wenn das angegebene Auswahlfeld
   *                                  besteht, sonst false.
   */
  private boolean isUpdate(HttpServletRequest request, DynaActionForm form) {
    Integer id = (Integer) form.get(Constants.AUSWAHLFELD_ID);
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }  

}
