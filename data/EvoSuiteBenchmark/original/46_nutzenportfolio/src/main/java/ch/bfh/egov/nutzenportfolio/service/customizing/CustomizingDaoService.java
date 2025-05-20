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
package ch.bfh.egov.nutzenportfolio.service.customizing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingDao;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.NaOpNuService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.ProjektattraktivitaetService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.projektgruppe.ProjektgruppeService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tag.CustomizingNavigation;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;
import ch.bfh.egov.nutzenportfolio.tos.StrategischesZiel;

/**
 * Implementierende Service-Klasse für Customizings.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class CustomizingDaoService implements CustomizingService {
  private CustomizingDao dao;
  private CommonService cService;
  private StrategischeZieleService szService;
  private DetailzieleService dzService;
  private AuswahlfeldService afService;
  private KategorieService kService;
  private NutzenkriteriumService nService;
  private NaOpNuService naOpNuService;
  private ProjektattraktivitaetService paService;
  private ProjektgruppeService pgService;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für Customizings. 
   * 
   * @param dao                               Customizing DataAcessObject-Interface
   */
  public CustomizingDaoService(CustomizingDao dao) {
    this.dao = dao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#getById(Customizing)
   */
  public Customizing getById(Customizing c) {
    return dao.getById(c);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#getByName(Customizing)
   */
  public Customizing getByName(Customizing c) {
    return dao.getByName(c);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#getByPaUID(Long)
   */
  public Customizing getByPaUID(Long paUID) {
    return dao.getByPaUID(paUID);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#getByNaOpNuUID(Long)
   */
  public Customizing getByNaOpNuUID(Long naOpNuUID) {
    return dao.getByNaOpNuUID(naOpNuUID);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#getAll(Integer)
   */
  public List getAll(Integer mandantId) {
    return dao.getAll(mandantId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#insert(Customizing)
   */
  public Integer insert(Customizing c) {
    Integer id = dao.insert(c);
    logger.debug("id: " + id);
    return id;
  }

  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.customizing.CustomizingIbatisDao#update(Customizing)
   */
  public void update(Customizing c) {
    dao.update(c);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch Übergabe anderer,
   * benötigter Service-Objekte.
   * 
   * @param cService                   Common Service Objekt
   * @param szService                  Strategische Ziele Service Objekt
   * @param dzService                  Customizing Service Objekt
   * @param afService                  Auswahlfeld Service Objekt
   * @param kService                   Kategorie Service Objekt
   * @param nService                   Nutzenkriterium Service Objekt
   * @param naOpNuService              Nutzenattraktivität / Operativer Nutzen
   *                                   Service Objekt
   * @param paService                  Projektattraktivitaet Service Objekt
   * @param pgService                  Projektgruppe Service Objekt
   */
  public void init(
      CommonService cService,
      StrategischeZieleService szService,
      DetailzieleService dzService,
      AuswahlfeldService afService,
      KategorieService kService,
      NutzenkriteriumService nService,
      NaOpNuService naOpNuService,
      ProjektattraktivitaetService paService,
      ProjektgruppeService pgService) {
    this.cService = cService;
    this.szService = szService;
    this.dzService = dzService;
    this.afService = afService;
    this.kService = kService;
    this.nService = nService;
    this.naOpNuService = naOpNuService;
    this.paService = paService;
    this.pgService = pgService;
  }
  
  /**
   * Löscht ein Customizing inklusive aller Verknüpfungen.
   * 
   * @param request                   der HttpServletRequest
   * @param c                         das zu löschende Customizing
   */
  public void cascadeDelete(HttpServletRequest request, Customizing c) {
    Integer customizingId = c.getCustomizingId();
    Integer naId = cService.getNutzenattraktivitaetIdByCustomizingId(customizingId);
    Integer opNuId = cService.getOperativerNutzenIdByCustomizingId(customizingId);
    Integer paId = cService.getProjektattraktivitaetIdByCustomizingId(customizingId);
    
    // Projektattraktivität Verknüpfungen löschen
    dzService.deleteAssignments(paId);
    afService.deletePaAssignmentsById(paId);

    // Resultate Projektattraktivität löschen
    paService.deleteAssignments(customizingId);
    
    // Nutzenattraktivität Verknüpfungen löschen
    kService.deleteAssignments(naId);
    nService.deleteAssignments(naId);
    afService.deleteNaOpNuAssignmentsById(naId);
    
    // Operativer Nutzen Verknüpfungen löschen
    kService.deleteAssignments(opNuId);
    nService.deleteAssignments(opNuId);
    afService.deleteNaOpNuAssignmentsById(opNuId);
    
    // Resultate Nutzenattraktivität & Operativer Nutzen löschen
    naOpNuService.deleteAssignments(customizingId);
    
    // Projektgruppe Verknüpfung löschen
    pgService.unsetCustomizingId(customizingId);
    
    // Projektattraktivität löschen
    cService.deleteProjektattraktivitaet(paId);
    
    // Nutzenattraktivität & Operativer Nutzen löschen
    cService.deleteNutzenattraktivitaetOperativerNutzen(naId);
    cService.deleteNutzenattraktivitaetOperativerNutzen(opNuId);
    
    // Customizing löschen
    dao.delete(c);
    
  }
  
  /**
   * Kopiert ein Customizing inklusive aller Verknüpfungen.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit der zu kopierenden Customizing Id
   */
  public void copy(HttpServletRequest request, DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Integer customizingId = (Integer) form.get(Constants.CUSTOMIZING_ID);
    logger.debug("customizingId" + customizingId);
    if (customizingId == null) {
      return;
    }
    
    // Customizing holen
    Customizing c = new Customizing();
    c.setCustomizingId(customizingId);
    c.setMandantId(mandantId);
    c = dao.getById(c);

    // Customizing kopieren
    String name = c.getName();
    c.setName(name + " (1)");
    Integer newCustomizingId = dao.insert(c);
    
    // Projektattraktivität, Nutzenattraktivität und Operativer Nutzen kopieren
    cService.insertCustomizingParts(newCustomizingId);
    
    // Ids holen
    Integer paId = cService.getProjektattraktivitaetIdByCustomizingId(customizingId);
    Integer[] naOpNuId = new Integer[2];
    naOpNuId[0] = cService.getNutzenattraktivitaetIdByCustomizingId(customizingId);
    naOpNuId[1] = cService.getOperativerNutzenIdByCustomizingId(customizingId);
    
    // Neue Ids holen
    Integer newPaId = cService.getProjektattraktivitaetIdByCustomizingId(newCustomizingId);
    Integer[] newNaOpNuId = new Integer[2];
    newNaOpNuId[0] = cService.getNutzenattraktivitaetIdByCustomizingId(newCustomizingId);
    newNaOpNuId[1] = cService.getOperativerNutzenIdByCustomizingId(newCustomizingId);
    
    // Strategische Ziele
    szService.copy(mandantId, customizingId, newPaId);
    
    // Detailziele
    dzService.copy(mandantId, paId, newPaId, true);
    dzService.copy(mandantId, paId, newPaId, false);
    
    // Auswahlfelder
    afService.copy(paId, newPaId, null, null, Constants.EINTRITTSWAHRSCHEINLICHKEIT);
    afService.copy(paId, newPaId, null, null, Constants.EINTRITTSZEITPUNKT);
    afService.copy(null, null, naOpNuId[0], newNaOpNuId[0], Constants.ABSTUFUNG);
    afService.copy(null, null, naOpNuId[0], newNaOpNuId[0], Constants.GEWICHTUNG);
    afService.copy(null, null, naOpNuId[1], newNaOpNuId[1], Constants.ABSTUFUNG);
    afService.copy(null, null, naOpNuId[1], newNaOpNuId[1], Constants.GEWICHTUNG);

    // Kategorien
    kService.copy(mandantId, naOpNuId[0], newNaOpNuId[0]);
    kService.copy(mandantId, naOpNuId[1], newNaOpNuId[1]);
    
    // Nutzenkriterien
    nService.copy(mandantId, naOpNuId[0], newNaOpNuId[0]);
    nService.copy(mandantId, naOpNuId[1], newNaOpNuId[1]);
  }
  
  /**
   * Ändert den Status eines Customizing auf inaktiv respektive aktiv.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit der zu kopierenden Customizing Id
   */
  public void changeStatus(HttpServletRequest request, DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Integer customizingId = (Integer) form.get(Constants.CUSTOMIZING_ID);
    logger.debug("customizingId" + customizingId);
    if (customizingId == null) {
      return;
    }
    
    // Customizing holen
    Customizing c = new Customizing();
    c.setCustomizingId(customizingId);
    c.setMandantId(mandantId);
    c = dao.getById(c);
    
    // Status ändern und update
    c.setStatus(!c.getStatus());
    dao.update(c);
  }
  
  /**
   * Überprüft, ob ein Customizing komplett ist.
   * 
   * @return                          true bei komplettem Customizing, sonst false
   */
  public boolean complete(HttpServletRequest request) {
    CustomizingNavigation nav = new CustomizingNavigation();
    nav.setName(Constants.CUSTOMIZING);
    nav.setRequest(request);
    if (nav.checkState()) {
      request.setAttribute(Constants.INCOMPLETE, false);
      return true;
    }
    request.setAttribute(Constants.INCOMPLETE, true);
    return false;
  }
  
  /**
   * Setzt den Status jedes Customizing Schritts in die Session.
   * Beispiel: Sobald mindestens 1 Strategisches Ziel für das momentane
   * Customizing angewählt ist, wird in die Session unter dem Schlüssel
   * "strategischeZiele" der Wert "true" gesetzt. Dies wird später zur
   * Status-Prüfung bei der Navigation verwendet (Schritt; erledigt=grün,
   * wird bearbeitet=gelb, nicht erledigt=rot). Die Methode unsetStatus
   * dient zum aufheben der gespeicherten Session Variabeln.
   * 
   * @param request         der HttpServletRequest zum erhalten der Session
   */
  public void setStatus(HttpServletRequest request) {
    
    HttpSession session = request.getSession();
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer customizingId = (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    
    // Projektattraktivität
    Integer paId = cService.getProjektattraktivitaetIdByCustomizingId(customizingId);
    logger.debug("paId=" + paId);
    
    // Strategische Ziele
    List<StrategischesZiel> szs = szService.setStatus(
        request, mandantId, customizingId);
    
    // Detailziele
    boolean dzOk = dzService.setStatus(request, szs, mandantId, paId);
    
    // Realisierbarkeit
    boolean realisierbarkeitOk = afService.setRealisierbarkeitStatus(request, paId);

    if (szs != null && dzOk && realisierbarkeitOk) {
      session.setAttribute(Constants.PROJEKTATTRAKTIVITAET, true);
    }
    
    // Nutzenattraktivität
    Integer naOpNuIds[] = new Integer[2];
    naOpNuIds[0] = cService.getNutzenattraktivitaetIdByCustomizingId(customizingId);
    logger.debug("naId=" + naOpNuIds[0]);
    
    // Operativer Nutzen
    naOpNuIds[1] = cService.getOperativerNutzenIdByCustomizingId(customizingId);
    logger.debug("opNuId=" + naOpNuIds[1]);
    
    boolean isNa = true;
    for (int i = 0; i < naOpNuIds.length; i++) {
      Integer naOpNuId = naOpNuIds[i];
      if (i == 1) {
        isNa = false;
      }
      
      // Kategorien
      List<Kategorie> kategorien = kService.setStatus(request, mandantId, naOpNuId, isNa);
      if (kategorien != null) {
        
        // Nutzenkriterien
        List<Nutzenkriterium> kriterien = nService.setStatus(
            request, kategorien, mandantId, naOpNuId, isNa);
        logger.debug("kriterienOk=" + (kriterien != null));
        
        // Stufenbeschriebe
        boolean stufenOk = afService.setStufenStatus(request, naOpNuId, isNa);
        logger.debug("stufenOk=" + stufenOk);
        
        // Gewichtung
        boolean gewichtungOk = afService.setGewichtungStatus(
            request, kriterien, naOpNuId, isNa);
        logger.debug("gewichtungOk=" + gewichtungOk);
      }
    }
    
  }
  
  /**
   * Dient zum aufheben der gespeicherten Session Variabeln mit den
   * Status-Flags.
   * 
   * @param request         der HttpServletRequest zum erhalten der Session
   */
  public void unsetStatus(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute(Constants.STRATEGISCHE_ZIELE);
    session.removeAttribute(Constants.DETAILZIELE_Q);
    session.removeAttribute(Constants.DETAILZIELE_NQ);
    session.removeAttribute(Constants.REALISIERBARKEIT);
    session.removeAttribute(Constants.KATEGORIEN_NA);
    session.removeAttribute(Constants.KATEGORIEN_OP_NU);
    session.removeAttribute(Constants.NUTZENKRITERIEN_NA);
    session.removeAttribute(Constants.FRAGEN_NA);
    session.removeAttribute(Constants.NUTZENKRITERIEN_OP_NU);
    session.removeAttribute(Constants.FRAGEN_OP_NU);
    session.removeAttribute(Constants.ABSTUFUNGEN_NA);
    session.removeAttribute(Constants.ABSTUFUNGEN_OP_NU);
    session.removeAttribute(Constants.GEWICHTUNGEN_NA);
    session.removeAttribute(Constants.GEWICHTUNGEN_OP_NU);
  }

}
