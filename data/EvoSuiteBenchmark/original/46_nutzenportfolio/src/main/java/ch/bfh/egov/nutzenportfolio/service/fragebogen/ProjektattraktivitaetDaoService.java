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
package ch.bfh.egov.nutzenportfolio.service.fragebogen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.common.AutoGrowingList;
import ch.bfh.egov.nutzenportfolio.common.ProjektattraktivitaetLine;
import ch.bfh.egov.nutzenportfolio.form.ProjektattraktivitaetForm;
import ch.bfh.egov.nutzenportfolio.persistence.fragebogen.ProjektattraktivitaetDao;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.ProjektattraktivitaetService;
import ch.bfh.egov.nutzenportfolio.service.projekt.ProjektService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Detailziel;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.Projektattraktivitaet;

/**
 * Implementierende Service-Klasse für den Fragebogen
 * Projektattraktivität.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektattraktivitaetDaoService implements ProjektattraktivitaetService {
  private ProjektattraktivitaetDao dao;
  private ProjektService pService;
  private StrategischeZieleService szService;
  private DetailzieleService dzService;
  private AuswahlfeldService afService;
  private CustomizingService cService;
  private Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für den Fragebogen
   * Projektattraktivität sowie anderer, benötigter Service-Objekte. 
   * 
   * @param dao                               Projektattraktivität DataAcessObject-Interface
   * @param pService                          Projekt Service-Interface
   * @param szService                         Strategisches Ziel Service-Interface
   * @param dzService                         Detailziel Service-Interface
   * @param afService                         Auswahlfeld Service-Interface
   * @param cService                          Customizing Service-Interface                      
   */
  public ProjektattraktivitaetDaoService(
      ProjektattraktivitaetDao dao,
      ProjektService pService,
      StrategischeZieleService szService,
      DetailzieleService dzService,
      AuswahlfeldService afService,
      CustomizingService cService) {
    this.dao = dao;
    this.pService = pService;
    this.szService = szService;
    this.dzService = dzService;
    this.afService = afService;
    this.cService = cService;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.fragebogen.ProjektattraktivitaetIbatisDao#deleteAssignments(Integer)
   */
  public void deleteAssignments(Integer customizingId) {
    dao.deleteAssignments(customizingId);
  }
  
  /**
   * Füllt das Formular mit allen benötigten Daten ab.
   * 
   * @param request             der HttpServletRequest
   * @param form                das DynaActionForm
   * @return                    true bei Erfolg, sonst false
   */
  public boolean prepare(HttpServletRequest request, ProjektattraktivitaetForm form) {
    
    // Session und Projekt ID holen
    HttpSession session = request.getSession();
    Integer projektId = getProjektId(session, null);
    
    // Session-Überprüfung über E-Mail Adresse
    if (session.getAttribute("email") == null) {
      return false;
    }
    
    Projekt p = new Projekt();
    p.setProjektId(projektId);
    form.setProjektId(projektId);
    
    // Strategische Ziele holen
    List sz = szService.getByProjektId(p);
    logger.debug(sz.size() + " Strategische Ziele in der Liste");
    session.setAttribute(Constants.STRATEGISCHE_ZIELE, sz);
    
    // Detailziele holen
    List<Detailziel> dz = dzService.getByProjektId(p);
    List dzForms = createObjectList(dz, form);
    logger.debug(dz.size() + " Detailziele in der Liste");
    session.setAttribute(Constants.DETAILZIELE, dzForms);
    
    // Eintrittswahrscheinlichkeiten holen
    Auswahlfeld a = new Auswahlfeld();
    a.setProjektId(p.getProjektId());
    a.setEintrittswahrscheinlichkeit(true);
    a.setEintrittszeitpunkt(false);
    a.setGewichtung(false);
    a.setAbstufung(false);
    List ew = afService.getByProjektId(a);
    logger.debug(dz.size() + " Eintrittswahrscheinlichkeiten in der Liste");
    session.setAttribute(Constants.EINTRITTSWAHRSCHEINLICHKEITEN, ew);
    
    // Eintrittszeitpunkte holen
    a.setEintrittswahrscheinlichkeit(false);
    a.setEintrittszeitpunkt(true);
    List ez = afService.getByProjektId(a);
    logger.debug(dz.size() + " Eintrittszeitpunkte in der Liste");
    session.setAttribute(Constants.EINTRITTSZEITPUNKTE, ez);

    return true;
  }
  
  /**
   * Speichert die Resultate des Fragebogens zur Projetktattraktivitaet ab.
   * 
   * @param request             der HttpServletRequest
   * @param form                das zu speichernde Formular
   */
  public void save(HttpServletRequest request, ProjektattraktivitaetForm form) {
    
    // E-Mail und UID's aus der Session holen
    HttpSession session = request.getSession();
    String email = (String) session.getAttribute("email");
    Long paUID = (Long) session.getAttribute("paUID");
    Integer projektId = getProjektId(session, null);
    logger.debug("email: " + email);
    logger.debug("paUID: " + paUID);
    logger.debug("ProjektId: " + projektId);
    
    if (email == null) {
      return;
    }
    
    // Werte aus der Session löschen
    session.removeAttribute("email");
    session.removeAttribute("projektUID");
    session.removeAttribute("paUID");
    
    // Projekt und Projektattraktivität anhand der UID's holen
    Projektattraktivitaet pa = dao.getByUID(paUID);
    if (projektId == null || pa == null) {
      return;
    }

    pa.setProjektId(projektId);
    pa.setEmail(email);
    
    AutoGrowingList l = form.getProjektattraktivitaetLines();
    Iterator it = l.iterator();
    Integer values = 0;
    int messbarkeitCounter = 0;
    int messbarkeitenTotal = 0;
    int nachweisbarkeitCounter = 0;
    int nachweisbarkeitenTotal = 0;
    while (it.hasNext()) {
      ProjektattraktivitaetLine p = (ProjektattraktivitaetLine) it.next();
      if (p != null) {
        Boolean messbar = p.getMessbarkeit();
        Boolean nachweisbar = p.getNachweisbarkeit();
        Integer ewId = p.getEintrittswahrscheinlichkeitId();
        Integer ezId = p.getEintrittszeitpunktId();
        
        // Level der Eintrittswahrscheinlichkeit holen
        Auswahlfeld a = new Auswahlfeld();
        a.setAuswahlfeldId(ewId);
        a = afService.getById(a);
        Integer ewLevel = a.getLevel() - 1;
        logger.debug("Level der Eintrittswahrscheinlichkeit: " + ewLevel);
        
        // Level des Eintrittszeitpunkts holen 
        a.setAuswahlfeldId(ezId);
        a = afService.getById(a);
        Integer ezLevel  = a.getLevel() - 1;
        logger.debug("Level des Eintrittszeitpunkts: " + ezLevel);
        
        // Realisierbarkeit aus Matrix holen
        Integer value = Constants.REALISIERBARKEIT_MATRIX[ewLevel][ezLevel];
        logger.debug("Wert aus Matrix: " + value);
        values += value;
        
        // Messbarkeiten zählen
        if (nachweisbar == null) {
          logger.debug("Quantifizierbarer Nutzen");
          messbarkeitenTotal++;
          if (messbar) {
            logger.debug("messbar");
            messbarkeitCounter++;
          }
        }
        
        // Nachweisbarkeiten zählen
        else if (messbar == null) {
          logger.debug("Nicht quantifizierbarer Nutzen");
          nachweisbarkeitenTotal++;
          if (nachweisbar) {
            logger.debug("nachweisbar");
            nachweisbarkeitCounter++;
          }
        }
      }
    }
    
    // Arithmetisches Mittel der Realisierbarkeit berechnen
    Double result = values / new Double(l.size()); 
    logger.debug("Arithmetisches Mittel der Realisierbarkeit: " + result);
    
    // Nachweisbarkeit berechnen
    Double nachweisbar = nachweisbarkeitCounter / new Double(nachweisbarkeitenTotal);
    logger.debug("Nachweisbarkeiten: " + nachweisbar);
    int round = (int) Math.floor(nachweisbar*9);
    logger.debug("Nachweisbarkeiten index: " + round);
    result += Constants.FORMULA[round];
    
    // Messbarkeiten berechnen
    Double messbar = messbarkeitCounter / new Double(messbarkeitenTotal);
    logger.debug("Messbarkeiten: " + messbar);
    round = (int) Math.floor(nachweisbar*9);
    logger.debug("Messbarkeiten index: " + round);
    result += Constants.FORMULA[round];

    // Arithmetisches Mittel aller Berechnungen
    result = result / 3;
    logger.debug("Resultat: " + result);
    
    // Resultat speichern
    pa.setResultat(result);
    dao.insertProjektattraktivitaetResultat(pa);
    
  }
  
  /**
   * Gibt den Status eines Customizings zurück.
   * 
   * @param request             der HttpServletRequest
   * @param form                das Formular mit den benötigten Daten
   *                            zum holen des Customizings
   * @return                    true bei Status aktiv, sonst false
   */
  public boolean isActive(HttpServletRequest request, DynaActionForm form) {
    
    // Projekt anhand UID holen
    Long pUID = (Long) form.get("p");
    Projekt p = new Projekt();
    p.setPaUID(pUID);
    p = pService.getByUID(pUID);
    if (p == null) {
      return false;
    }
    boolean status = false;
    
    // UID der Projektattraktivität holen
    Long paUID = (Long) form.get("pa");
    logger.debug("paUID=" + paUID);
    Customizing c = null;
    Long naOpNuUID= null;
    
    // Auf andere UIDs ausweichen, falls die UID der Projektattraktivität
    // nicht angegeben wurde
    if (paUID == null) {
      
      // UID der Nutzenattraktivität bzw. des Operativen Nutzens holen
      naOpNuUID = (Long) form.get("o");
      logger.debug("opNuUID=" + naOpNuUID);
      if (naOpNuUID == null) {
        naOpNuUID = (Long) form.get("na");
        logger.debug("naUID=" + naOpNuUID);
        status = p.getNaStatus();
      } 
      else {
        status = p.getOpNuStatus();
      }
      
      // Customizing holen
      c = cService.getByNaOpNuUID(naOpNuUID);
    }
    else {
      
      // Customizing holen
      c = cService.getByPaUID(paUID);
      status = p.getPaStatus();
    }

    // Status zurückgeben
    if (c != null) {
      return c.getStatus() && status;
    }
    return false;
  }
  
  /**
   * Führt das Login für den Fragebogen Projektattraktvität aus.
   * 
   * @param request             der HttpServletRequest
   * @param form                das Formular mit den Login-Daten
   * @return                    true bei Erfolg, sonst false
   */
  public boolean login(HttpServletRequest request, DynaActionForm form) {
    
    // E-Mail Adresse, Projekt UID und  Projektattraktvität wird benötigt
    String email = form.getString("email");
    Long projektUID = (Long) form.get("p");
    Long paUID = (Long) form.get("pa");
    logger.debug("email=" + email);
    HttpSession session = request.getSession(); 
    Projektattraktivitaet p = new Projektattraktivitaet();
    p.setProjektId(getProjektId(session, projektUID));
    p.setEmail(email);
    
    // Wenn für die angegeben Daten schon ein ausgefüllter Fragebogen
    // existiert, darf der Bentuzer den Fragebogen nicht noch einmal ausfüllen
    p = dao.getByEmail(p);
    if (p == null) {
      logger.debug("E-Mail nicht gefunden. Ok.");
      
      // Benötigte Daten in der Session speichern
      session.setAttribute("email", email);
      session.setAttribute("projektUID", projektUID);
      session.setAttribute("paUID", paUID);
      return true;
    }
    
    // Fragebogen wurde schon ausgefült, login verweigern
    logger.debug("E-Mail gefunden.");
    return false;
  }
  
  /**
   * Kopieren einer Liste von Detailzielen in eine Liste von
   * Projektattraktivität-Formularen.<br/>
   * TODO wird diese Methode wirklich benötigt?
   * 
   * @param l                   zu kopierende Liste mit Detailzielen
   * @param form                das Projektattraktivität-Formular
   * @return                    eine Liste von
   *                            Projektattraktivität-Formularen
   */
  private List createObjectList(List<Detailziel> l, ProjektattraktivitaetForm form) {
    List<ProjektattraktivitaetForm> paForms = new ArrayList<ProjektattraktivitaetForm>();
    for (Detailziel dz : l) {
      ProjektattraktivitaetForm paForm = new ProjektattraktivitaetForm();
      try {
        
        // Werte kopieren und das Formular der Liste hinzufügen
        BeanUtils.copyProperties(paForm, dz);
        paForms.add(paForm);
      } catch (Exception ex) {
        logger.error(ex.getMessage());
      }
    }

    return paForms;
  }
  
  /**
   * Holt eine ProjektId anhand der angegebenen Projekt UID oder
   * anhand der Projekt UID aus der Session.
   * 
   * @param session             die HttpSession
   * @param uid                 die ProjektUID
   * @return                    die projektId
   */
  private Integer getProjektId(HttpSession session, Long uid) {
    
    Long projektUID = uid;
    // Projekt UID aus der Session holen
    if (uid == null) {
      projektUID = (Long) session.getAttribute("projektUID"); 
    }
    
    // Projekt anhand der UID holen
    Projekt projekt = pService.getByUID(projektUID);
    if (projekt == null) {
      return null;
    }
    return projekt.getProjektId();
  }

}
