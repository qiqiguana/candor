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
package ch.bfh.egov.nutzenportfolio.service.projekt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.persistence.fragebogen.NaOpNuDao;
import ch.bfh.egov.nutzenportfolio.persistence.projekt.ProjektDao;
import ch.bfh.egov.nutzenportfolio.service.projektgruppe.ProjektgruppeService;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Projekt;
import ch.bfh.egov.nutzenportfolio.tos.Projektgruppe;
import ch.bfh.egov.nutzenportfolio.tos.Resultat;

/**
 * Implementierende Service-Klasse für Projekte.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektDaoService implements ProjektService {
  private ProjektDao dao;
  private ProjektgruppeService projektgruppeService;
  private NaOpNuDao naOpNuDao;
  private Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für Projekte sowie
   * anderer, benötigter Service-Objekte. 
   * 
   * @param dao                               Projekt DataAcessObject-Interface
   * @param projektgruppeService              Projektgruppe Service-Interface
   * @param naOpNuDao                         Nutzenattraktivität und Operativer
   *                                          NutzenProjekt Service-Interface
   */
  public ProjektDaoService(
      ProjektDao dao,
      ProjektgruppeService projektgruppeService,
      NaOpNuDao naOpNuDao) {
    this.dao = dao;
    this.projektgruppeService = projektgruppeService;
    this.naOpNuDao = naOpNuDao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.projekt.ProjektDao#getById(Projekt)
   */
  public Projekt getById(Projekt p) {
    return dao.getById(p);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.projekt.ProjektDao#getByUID(Long)
   */
  public Projekt getByUID(Long projektUID) {
    return dao.getByUID(projektUID);
  }

  /**
   * Setzt alle vorhandenen Projekte in den request.
   * 
   * @param request             der HttpServletRequest
   */
  public void populate(HttpServletRequest request) {

    // Filter
    Integer filter = null;
    try {
      filter = new Integer(request.getParameter("filtergruppe"));
    }
    catch(NumberFormatException e) {}

    // Projekte holen
    Projekt p = createProjekt(request);
    List projekte;
    if(filter != null) {
      Projektgruppe pg = new Projektgruppe();
      pg.setMandantId(p.getMandantId());
      pg.setProjektgruppeId(filter);
      projekte = dao.getGroup(pg);
    }
    else
      projekte = dao.getAll(p.getMandantId());

    // Die eindeutigen IDs setzen
    ListIterator it = projekte.listIterator();
    LinkedHashMap<Integer, Projekt> map = new LinkedHashMap<Integer, Projekt>();
    NaOpNu naOpNu = new NaOpNu();
    while (it.hasNext()) {
      Projekt projekt = (Projekt) it.next();
      Integer projektId = projekt.getProjektId();
      naOpNu.setProjektId(projektId);
      Long naOpNuUID = projekt.getNaOpNuUID();
      Boolean opNu = projekt.getOperativerNutzen();
      logger.debug("projektId=" + projektId);
      logger.debug("naOpNuUID=" + naOpNuUID);
      
      // Damit Projekte nicht doppelt angezeigt werden,
      // werden sie durch eine LinkedHashMap gefiltert. Der Grund dafür
      // liegt darin, dass das SQL Query getAll pro Projekt mehrere
      // Zeilen für die UID des Operativen Nutzens und der Nutzenattraktivität
      // ausgibt.
      if (map.containsKey(projektId)) {
        logger.debug("Projekt in der LinkedHashMap gefunden");
        projekt = map.get(projektId);
      }
      
      // UID überprüfen
      if (opNu == null) {
        logger.debug("Keine UID gesetzt");
      }
      
      // UID Operativer Nutzen
      else if (opNu) {
        logger.debug("Gesetzte UID: Operativer Nutzen");
        projekt.setOpNuUID(naOpNuUID);
        
        // Anzahl Resultate holen
        naOpNu.setNutzenattraktivitaet(false);
        naOpNu.setOperativerNutzen(true);
        NaOpNu opNuObj = naOpNuDao.getNaOpNuResultat(naOpNu);
        if (opNuObj == null) {
          projekt.setAnzahlOpNuResultate(0);
        }
        else {
          projekt.setAnzahlOpNuResultate(opNuObj.getAnzahl());
        }
      }
      
      // UID Nutzenattraktivitaet
      else {
        logger.debug("Gesetzte UID: Nutzenattraktivitaet");
        projekt.setNaUID(naOpNuUID);
        
        // Anzahl Resultate holen
        naOpNu.setNutzenattraktivitaet(true);
        naOpNu.setOperativerNutzen(false);
        NaOpNu na = naOpNuDao.getNaOpNuResultat(naOpNu);
        if (na == null) {
          projekt.setAnzahlNaResultate(0);
        }
        else {
          projekt.setAnzahlNaResultate(na.getAnzahl());
        }        
      }

      // Resultate Projektattraktivität holen
      NaOpNu pa = naOpNuDao.getPaResultat(naOpNu);
      if (pa == null) {
        projekt.setAnzahlPaResultate(0);
      }
      else {
        projekt.setAnzahlPaResultate(pa.getAnzahl());
      }
      
      map.put(projektId, projekt);
    }
    
    // Projekte in den Request stellen
    Collection proj = map.values();
    request.setAttribute(Constants.PROJEKTE, proj);
    request.setAttribute("filtergruppe", filter);
  }

  /**
   * Füllt das Formular mit allen benötigten Daten ab.
   * 
   * @param request             der HttpServletRequest
   * @param form                das DynaActionForm
   */
  public void prepare(HttpServletRequest request, DynaActionForm form) {
    Projekt p = get(request, form);
    
    // update?
    if (isUpdate(p)) {
      Integer id = (Integer) form.get(Constants.PROJEKT_ID);
      p.setProjektId(id);
      
      // Projekt holen und Daten setzen
      p = dao.getById(p);
      form.set("name", p.getName());
      form.set("beschreibung", p.getBeschreibung());
      form.set("projektgruppeId", p.getProjektgruppeId());
    }
    
    // Projektgruppen in den Reqeust setzen
    List pg = projektgruppeService.getAll(p.getMandantId());
    logger.debug(pg.size() + " Projektgruppen in der Liste");
    request.getSession().setAttribute("projektgruppeMap", pg);
  }
  
  /**
   * Erstellt ein Projekt-Objekt aus den Formulardaten.
   * 
   * @param request             der HttpServletRequest
   * @param form                das DynaActionForm
   * @return                    ein neues Projekt
   */
  public Projekt get(HttpServletRequest request, DynaActionForm form) {
    
    //Formulardaten holen
    Integer id = (Integer) form.get(Constants.PROJEKT_ID);
    Integer projektgruppeId = (Integer) form.get(Constants.PROJEKTGRUPPE_ID);
    String name = (String) form.get("name");
    String beschreibung = (String) form.get("beschreibung");
    Long projektUID = (Long) form.get("projektUID");
    
    // Objekt erzeugen
    Projekt p = createProjekt(request);
    p.setProjektId(id);
    p.setProjektgruppeId(projektgruppeId);
    p.setName(name);
    p.setBeschreibung(beschreibung);
    p.setProjektUID(projektUID);
    return p;
  }
  
  /**
   * Erstellt ein Projekt-Objekt anhand der Id aus dem request.
   * 
   * @param request             der HttpServletRequest
   * @return                    ein neues Projekt
   */
  public Projekt get(HttpServletRequest request) {
    
    // Id des Projekts aus dem Request holen
    String pId = request.getParameter(Constants.PROJEKT_ID);
    Integer id = null;
    try {
      id = new Integer(pId);
    } catch (Exception ex) {
      logger.warn("Fehlende projektId!");
      return null;
    }
    
    // Projekt überprüfen
    Projekt p = createProjekt(request);   
    p.setProjektId(id);
    p = dao.getById(p);
    if (p == null) {
      logger.warn("Projekt mit der id " + id + " wurde nicht gefunden.");
      populate(request);
    }
    return p;
  }
  
  /**
   * Speichert das angegebene Projekt ab.
   * 
   * @param request             der HttpServletRequest
   * @param p                   das zu speichernde Projekt
   */
  public void save(HttpServletRequest request, Projekt p) {
    
    // update?
    if (isUpdate(p)) {
      dao.update(p);
    }
    // insert
    else {
      
      // Einzigartige ID vergeben
      Random rnd = new Random();
      long projektUID = ((System.currentTimeMillis() >>> 16) << 16) + rnd.nextLong();
      logger.debug("projektUID=" + projektUID);
      p.setProjektUID(projektUID);
      dao.insert(p);
    }
    populate(request);
  }
  
  /**
   * Löscht ein Projekt.
   * 
   * @param request             der HttpServletRequest
   * @return                    true bei Erfolg, sonst false
   */
  public boolean delete(HttpServletRequest request) {
    
    // Projekt aus dem request holen
    Projekt p = get(request);
    if (p == null) {
      return false;
    }
    
    // Projekt löschen
    dao.delete(p);
    populate(request);
    return true;
  }
  
  /**
   * Prüft, ob der Name des angegebenen Projekts bereits vergeben ist
   * 
   * @param p                   das zu überprüfende Projekt
   * @return                    true, wenn der Name der Projekt
   *                            bereits vergeben ist, sonst false
   */
  public boolean exists(Projekt p) {
    Integer id = p.getProjektId();

    // update?
    if (id != null && id != 0) {
    	Projekt pCurrent = dao.getById(p);

      // Name geändert, überprüfen
      if (!pCurrent.getName().equals(p.getName())) {
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
   * Generiert ein Projet Objekt mit dem aktuellen Mandanten.
   * 
   * @param request               der HttpServletRequest
   * @return                      ein Projekt mit gesetzem Mandant
   */
  public Projekt createProjekt(HttpServletRequest request) {
    Projekt p = new Projekt();
    p.setMandantId(
        (Integer) request.getSession().getAttribute(Constants.MANDANT_ID));
    return p;
  }
  
  /**
   * Nimmt die Auswertung von Resultaten aus den Fragebögen vor.
   * 
   * @param request               der HttpServletRequest
   * @param form                  das DynaActionForm
   * @param errors                ActionMessages Objekt für Fehlerausgaben
   * @return                      true bei Erfolg, sonst false
   */
  public boolean auswertung(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors) {
    
    // Formulardaten holen
    Integer[] ids = (Integer[]) form.get("projektIds");
    Integer pgId = (Integer) form.get(Constants.PROJEKTGRUPPE_ID);
    request.setAttribute(Constants.PROJEKTGRUPPE_ID, pgId);
    if (ids == null || ids.length == 0) {
      logger.info("Keine Projekte gewählt");
      
      // Fehler bei keiner Auswahl
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.select.one", "Projekt"));
      populate(request);
      return false;
    }
    
    logger.debug(ids.length + " Projekte ausgewählt");
    
    // Resultate aller Projekte holen
    List<Resultat> resultate = new ArrayList<Resultat>();
    for (int i = 0; i < ids.length; i++) {
      
      NaOpNu naOpNu = new NaOpNu();
      naOpNu.setProjektId(ids[i]);
      
      // Nutzenattraktivität holen
      naOpNu.setNutzenattraktivitaet(true);
      naOpNu.setOperativerNutzen(false);
      NaOpNu na = naOpNuDao.getNaOpNuResultat(naOpNu);
      
      // Operativer Nutzen holen
      naOpNu.setNutzenattraktivitaet(false);
      naOpNu.setOperativerNutzen(true);
      NaOpNu opNu = naOpNuDao.getNaOpNuResultat(naOpNu);
      
      // Projektattraktivität holen
      NaOpNu pa = naOpNuDao.getPaResultat(naOpNu);
      if (pa != null && na != null) {
        
        logger.debug("paResultat vor Runden: " + pa.getResultat());
        Double paResultat = Math.rint(pa.getResultat() * 1000) / 1000;
        logger.debug("paResultat nach Runden: " + paResultat);
        
        logger.debug("naResultat vor Runden: " + na.getResultat());
        Double naResultat = Math.rint(na.getResultat() * 1000) / 1000;
        logger.debug("naResultat nach Runden: " + naResultat);
        
        Double opNuResultat = null;
        if(opNu != null) {
          logger.debug("opNuResultat vor Runden: " + opNu.getResultat());
          opNuResultat = Math.rint(opNu.getResultat() * 1000) / 1000;
          logger.debug("opNuResultat nach Runden: " + opNuResultat);
        }
        
        Resultat r = new Resultat();
        r.setPaResultat(paResultat);
        r.setNaResultat(naResultat);
        r.setOpNuResultat(opNuResultat);
        r.setName(na.getName());
        
        // Textlabel anhand der Resultate setzen
        setAuswertungLabels(r);
        logger.debug("Resultat hinzugefügt");
        resultate.add(r);
      }
      else {
        logger.warn("Ein Resultat oder Mehrere fehlen");
      }

    }

    // Resultat für grafische Auswertung in Session speichern
    logger.debug("Resultate in der Session: " + resultate.size());

    request.getSession().setAttribute(Constants.RESULTATE, resultate);

    return true;
  }

  /**
   * Ändert einen Status eines Projekts auf inaktiv respektive aktiv.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit der Id des Projeks
   */
  public void changeStatus(
      HttpServletRequest request,
      DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Integer projektId = (Integer) form.get(Constants.PROJEKT_ID);
    String type = (String) form.get("type");
    logger.debug("projektId=" + projektId);
    logger.debug("mandantId=" + mandantId);
    if (projektId == null) {
      return;
    }
    
    // Projekt holen
    Projekt p = new Projekt();
    p.setProjektId(projektId);
    p.setMandantId(mandantId);
    p = dao.getById(p);
    if (p == null) {
      return;
    }
    
    // Status ändern und update
    if (type.equals("pa")) {
      p.setPaStatus(!p.getPaStatus());
    }
    else if (type.equals("na")) {
      p.setNaStatus(!p.getNaStatus());
    }
    else if (type.equals("opNu")) {
      p.setOpNuStatus(!p.getOpNuStatus());
    }
    dao.updateStatus(p);
  }
  
  /**
   * Prüft, ob das angegebene Projekt neu ist, oder ob es sich um
   * ein update handelt.
   *  
   * @param p                   das zu überprüfende Projekt
   * @return                    true bei update, sonst false
   */
  private boolean isUpdate(Projekt p) {
    Integer id = p.getProjektId();
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }
  
  /**
   * Setzt die entsprechenden Labels anhand der Resultate der Auswertung.
   * 
   * @param r                   das zu setzende Resultat
   */
  private void setAuswertungLabels(Resultat r) {
    Double pa = r.getPaResultat();
    Double na = r.getNaResultat();
    Double opNu = r.getOpNuResultat();
    String label = null;
    Double diff = null;
    if(opNu != null)
      diff = Math.abs(opNu - na);

    if (pa >= 0 && pa < 1.5 && na >= 0 && na < 1.5) {
    	if(opNu != null) {
        if (opNu >= 0 && opNu < 1.5)
          label = "label.objektiv.und.management.und.projektbetroffene.unattraktiv";
        else if (opNu >= 1.5 && opNu <= 3.0 && diff >= 0.3)
          label = "label.objektiv.und.management.unattraktiv.projektbetroffene.attraktiv.diskrepanz.gross";
        else if (opNu >= 1.5 && opNu <= 3.0 && diff < 0.3)
          label = "label.objektiv.und.management.unattraktiv.projektbetroffene.attraktiv.diskrepanz.klein";
    	}
    	else
    		label = "label.objektiv.und.management.unattraktiv";
    }
    
    if (pa >= 0 && pa < 1.5 && na >= 1.5 && na <= 3.0) {
    	if(opNu != null) {
        if (opNu >= 0 && opNu < 1.5)
          label = "label.objektiv.und.projektbetroffene.unattraktiv.management.attraktiv";
        else if (opNu >= 1.5 && opNu <= 3.0)
          label = "label.objektiv.unattraktiv.management.und.projektbetroffene.attraktiv";
    	}
      else
      	label = "label.objektiv.unattraktiv.management.attraktiv";
    }
    
    if (pa >= 1.5 && pa <= 3.0 && na >= 0 && na < 1.5) {
      if(opNu != null) {
    	  if (opNu >= 0 && opNu < 1.5 && diff >= 0.3)
          label = "label.objektiv.attraktiv.management.und.projektbetroffene.unattraktiv.diskrepanz.gross";
        else if (opNu >= 0 && opNu < 1.5 && diff < 0.3)
          label = "label.objektiv.attraktiv.management.und.projektbetroffene.unattraktiv.diskrepanz.klein";
        else if (opNu >= 1.5 && opNu <= 3.0)
          label = "label.objektiv.und.projektbetroffene.attraktiv.management.unattraktiv";
      }
      else
      	label = "label.objektiv.attraktiv.management.unattraktiv";
    }
    
    if (pa >= 1.5 && pa <= 3.0 && na >= 1.5 && na <= 3.0) {
      if(opNu != null) {
    	  if (opNu >= 0 && opNu < 1.5 && diff >= 0.3)
          label = "label.objektiv.und.management.attraktiv.projektbetroffene.unattraktiv.diskrepanz.gross";
        else if (opNu >= 0 && opNu < 1.5 && diff < 0.3)
          label = "label.objektiv.und.management.attraktiv.projektbetroffene.unattraktiv.diskrepanz.klein";
        else if (opNu >= 1.5 && opNu <= 3.0)
          label = "label.objektiv.und.management.und.projektbetroffene.attraktiv";
      }
      else
      	label = "label.objektiv.und.management.attraktiv";
    }
    logger.debug("label=" + label);
    r.setLabel(label);
    
  }

}
