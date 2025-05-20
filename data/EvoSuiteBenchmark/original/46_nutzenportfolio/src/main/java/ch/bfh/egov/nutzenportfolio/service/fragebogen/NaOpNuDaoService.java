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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.common.AutoGrowingList;
import ch.bfh.egov.nutzenportfolio.common.NaOpNuLine;
import ch.bfh.egov.nutzenportfolio.form.NaOpNuForm;
import ch.bfh.egov.nutzenportfolio.persistence.fragebogen.NaOpNuDao;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.projekt.ProjektService;
import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;

/**
 * Implementierende Service-Klasse für die Fragebögen
 * Nutzenattraktivität und Operativer Nutzen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NaOpNuDaoService implements NaOpNuService {
  private NaOpNuDao dao;
  private ProjektService pService;
  private KategorieService kService;
  private NutzenkriteriumService nService;
  private AuswahlfeldService afService;
  private Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für den Fragebogen
   * Nutzenattraktivitaet bzw. Operativer Nutzen sowie anderer,
   * benötigter Service-Objekte. 
   * 
   * @param dao                               Nutzenattraktivitaet / Operativer Nutzen
   *                                          DataAcessObject-Interface
   * @param pService                          Projekt Service-Interface
   * @param kService                          Kategorie Service-Interface
   * @param nService                          Nutzenkriterium Service-Interface
   * @param afService                         Auswahlfeld Service-Interface                      
   */
  public NaOpNuDaoService(
      NaOpNuDao dao,
      ProjektService pService,
      KategorieService kService,
      NutzenkriteriumService nService,
      AuswahlfeldService afService) {
    this.dao = dao;
    this.pService = pService;
    this.kService = kService;
    this.nService = nService;
    this.afService = afService;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.fragebogen.NaOpNuIbatisDao#getNaOpNuResultat(NaOpNu)
   */
  public NaOpNu getNaOpNuResultat(NaOpNu naOpNu) {
    return dao.getNaOpNuResultat(naOpNu);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.fragebogen.NaOpNuIbatisDao#getPaResultat(NaOpNu)
   */
  public NaOpNu getPaResultat(NaOpNu naOpNu) {
    return dao.getPaResultat(naOpNu);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.fragebogen.NaOpNuIbatisDao#deleteAssignments(Integer)
   */
  public void deleteAssignments(Integer customizingId) {
    dao.deleteAssignments(customizingId);
  }
  
  /**
   * Füllt das Formular mit allen benötigten Daten ab.
   * 
   * @param request             der HttpServletRequest
   * @param form                das Fragebogen Formular
   */
  public boolean prepare(HttpServletRequest request, NaOpNuForm form) {
    
    // Session und Projekt ID holen
    HttpSession session = request.getSession();
    Integer projektId = getProjektId(session, null);
    Long opNuUID = (Long) session.getAttribute("opNuUID");
    form.setProjektId(projektId);
    
    // Session-Überprüfung über E-Mail Adresse
    if (session.getAttribute("email") == null) {
      return false;
    }
    
    // Customizing typ bestimmen
    NaOpNu nOpNu = new NaOpNu();
    
    // Operativer Nutzen
    if (opNuUID != null && opNuUID != 0) {
      nOpNu.setOperativerNutzen(true);
      nOpNu.setNutzenattraktivitaet(false);
      request.setAttribute(Constants.PROJEKTBETROFFENE, true);
      form.setOpNu(true);
    }
    
    // Nutzenattraktivitaet
    else {
      nOpNu.setNutzenattraktivitaet(true);
      nOpNu.setOperativerNutzen(false);
      form.setOpNu(false);
    }
    
    nOpNu.setProjektId(projektId);
    
    // Kategorien holen
    List k = kService.getByProjektId(nOpNu);
    logger.debug(k.size() + " Kategorien in der Liste");
    request.setAttribute(Constants.KATEGORIEN, k);
    
    // Nutzenkriterien holen
    List<Nutzenkriterium> n = nService.getByProjektId(nOpNu);
    //List nForms = createObjectList(n, form);
    logger.debug(n.size() + " Nutzenkriterien in der Liste");
    
    // Auswahlfelder holen
    Auswahlfeld a = new Auswahlfeld();
    a.setProjektId(nOpNu.getProjektId());
    a.setOperativerNutzen(nOpNu.getOperativerNutzen());
    a.setNutzenattraktivitaet(nOpNu.getNutzenattraktivitaet());
    
    // Anzahl Stufen
    Integer stufen = null;
    Integer gewichtungsstufen = null;
    
    // Auswahlfelder pro Nutzenkriterium setzen
    ListIterator<Nutzenkriterium> it = n.listIterator();
    while (it.hasNext()) {
      Nutzenkriterium nk = (Nutzenkriterium) it.next();
      a.setNutzenkriteriumId(nk.getNutzenkriteriumId());
      
      // Abstufung holen
      a.setAbstufung(true);
      a.setGewichtung(false);
      List abstufungen = afService.getByNaOpNu(a);
      logger.debug(abstufungen.size()
          + " Abstufungen für das Nutzenkriterium "
          + nk.getNutzenkriteriumId());
      nk.setAbstufungen(abstufungen);
      if (stufen == null) {
        stufen = abstufungen.size();
      }
      
      // Gewichtung holen
      a.setAbstufung(false);
      a.setGewichtung(true);
      List gewichtungen = afService.getByNaOpNu(a);
      logger.debug(gewichtungen.size()
          + " Gewichtungen für das Nutzenkriterium "
          + nk.getNutzenkriteriumId());
      if (gewichtungen.size() > 0) {
        nk.setGewichtungen(gewichtungen);
      }
      if (gewichtungsstufen == null) {
        gewichtungsstufen = gewichtungen.size();
      }
      
      // Nutzenkriterium in der Liste ändern
      it.set(nk);
    }
    form.setStufen(stufen);
    form.setGewichtungsstufen(gewichtungsstufen);
    request.setAttribute(Constants.NUTZENKRITERIEN, n);
    return true;
  }
  
  /**
   * Speichert die Resultate des Fragebogens ab.
   * 
   * @param request             der HttpServletRequest
   * @param form                das zu speichernde Formular
   */
  public void save(HttpServletRequest request, NaOpNuForm form) {

    // E-Mail und UID's aus der Session holen
    HttpSession session = request.getSession();
    String email = (String) session.getAttribute("email");
    Long naUID = (Long) session.getAttribute("naUID");
    Long opNuUID = (Long) session.getAttribute("opNuUID");
    Integer projektId = getProjektId(session, null);
    logger.debug("email: " + email);
    logger.debug("naUID: " + naUID);
    logger.debug("opNuUID: " + opNuUID);
    
    // Werte aus der Session löschen
    session.removeAttribute("email");
    session.removeAttribute("projektUID");
    session.removeAttribute("naUID");
    session.removeAttribute("opNuUID");
    
    // Projekt und Projektattraktivität anhand der UID's holen
    Long naOpNuUID = naUID;
    if (naUID == null || naUID == 0) {
      naOpNuUID = opNuUID;
    }
    NaOpNu naOpNu = dao.getByUID(naOpNuUID);
    if (projektId == null || naOpNu == null) {
      return;
    }
    
    Integer naOpNuId = naOpNu.getNutzenattraktivitaetOperativerNutzenId();
    Integer mandantId = naOpNu.getMandantId();
    logger.debug("projektId=" + projektId);
    logger.debug("customizingId=" + naOpNu.getCustomizingId());
    logger.debug("naOpNuId=" + naOpNuId);
    logger.debug("mandantId=" + mandantId);
    naOpNu.setProjektId(projektId);
    naOpNu.setEmail(email);
    
    // Stufen holen
    Integer stufen = form.getStufen();
    logger.debug("Anzahl stufen: " + stufen);
    Integer gewichtungsstufen = form.getGewichtungsstufen();
    logger.debug("Anzahl gewichtungsstufen: " + gewichtungsstufen);
    
    AutoGrowingList l = form.getNaOpNuLines();
    Iterator it = l.iterator();
    Double values = 0.0;
    boolean fragebogenGewichtung = false;
    while (it.hasNext()) {
      NaOpNuLine line = (NaOpNuLine) it.next();
      if (line != null) {
        Integer nId = line.getNutzenkriteriumId();
        logger.debug("nutzenkriteriumId=" + nId);
        
        // Level der Abstufung holen
        Integer levelAbstufung = line.getAbstufung();
        logger.debug("Level der Abstufung: " + levelAbstufung);
        Double valueAbstufung = Constants.NA_OP_NU_MATRIX[stufen - 2][levelAbstufung - 1];
        logger.debug("Constants.NA_OP_NU_MATRIX["
            + (stufen - 2) + "][" + (levelAbstufung - 1) + "] = "
            + valueAbstufung);
        
        // Level der Gewichtung holen
        Integer levelGewichtung = line.getGewichtung();
        logger.debug("Level der Gewichtung: " + levelGewichtung);

        
        // Bei Gewichtung über Fragebogen 
        if (levelGewichtung != null) {
          fragebogenGewichtung = true;
          Double valueGewichtung = Constants.NA_OP_NU_MATRIX[stufen - 2][levelAbstufung - 1];
          logger.debug("Constants.NA_OP_NU_MATRIX["
              + (stufen - 2) + "][" + (levelAbstufung - 1) + "] = "
              + valueGewichtung);
          values += valueAbstufung * valueGewichtung / 3;
        }
        
        // Bei direkter Gewichtung
        else {
          logger.debug("Direkte Gewichtung");
          
          // Nutzenkriterium holen
          Nutzenkriterium n = new Nutzenkriterium();
          n.setNutzenkriteriumId(nId);
          n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
          n.setMandantId(mandantId);
          n = nService.getAssignmentById(n);
          Double gewichtung = null;
          if (n == null) {
            logger.error("Nutzenkriterium null!");
            return;
          }
          else if ((gewichtung = n.getGewichtung()) == null) {
            logger.error("Direkte Gewichtung null!");
            return;
          }
          else {
            // direkte Gewichtung berechnen
            Double kategorieGewichtung = n.getKategorieGewichtung() / 100;
            logger.debug("kategorieGewichtung=" + kategorieGewichtung);
            logger.debug("gewichtung=" + gewichtung);
            Double value = valueAbstufung * (gewichtung * kategorieGewichtung / 100);
            values += value;
            logger.debug("Berechnung: " + valueAbstufung
                + " * " + "(" + gewichtung + " * " + kategorieGewichtung +
                " / " + 100 + ") = " + value);
          }
        }

      }
    }
    
    // Arithmetisches Mittel bei Gewichtung über Fragebogen
    if (fragebogenGewichtung) {
      values = values / l.size();
    }
    
    // Resultat speichern
    naOpNu.setResultat(values);
    dao.insertNaOpNuResultat(naOpNu);
    
  }
  
  /**
   * Führt das Login für den Fragebogen Nutzenattraktivitaet
   * bzw. Operativer Nutzen aus.
   * 
   * @param request             der HttpServletRequest
   * @param form                das Formular mit den Login-Daten
   * @return                    true bei Erfolg, sonst false
   */
  public boolean login(HttpServletRequest request, DynaActionForm form) {
    
    // Formulardaten holen
    String email = form.getString("email");
    Long projektUID = (Long) form.get("p");
    Long naUID = (Long) form.get("na");
    Long opNuUID = (Long) form.get("o");
    logger.debug("email=" + email);
    logger.debug("projektUID=" + projektUID);
    logger.debug("naUID=" + naUID);
    logger.debug("opNuUID=" + opNuUID);
    
    // E-Mail überprüfen
    HttpSession session = request.getSession(); 
    NaOpNu naOpNu = new NaOpNu();
    naOpNu.setProjektId(getProjektId(session, projektUID));
    naOpNu.setEmail(email);
    
    // Typ des Fragebogens bestimmen
    if (naUID == null || naUID == 0) {
      naOpNu.setNutzenattraktivitaet(false);
      naOpNu.setOperativerNutzen(true);
    }
    else {
      naOpNu.setNutzenattraktivitaet(true);
      naOpNu.setOperativerNutzen(false);
    }
    
    // Wenn für die angegeben Daten schon ein ausgefüllter Fragebogen
    // existiert, darf der Bentuzer den Fragebogen nicht noch einmal ausfüllen
    naOpNu = dao.getByEmail(naOpNu);
    if (naOpNu == null) {
      logger.debug("E-Mail nicht gefunden. Ok.");
      
      // Benötigte Daten in der Session speichern
      session.setAttribute("email", email);
      session.setAttribute("projektUID", projektUID);
      session.setAttribute("naUID", naUID);
      session.setAttribute("opNuUID", opNuUID);
      return true;
    }
    
    // Fragebogen wurde schon ausgefült, login verweigern
    logger.debug("E-Mail gefunden.");
    return false;
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
