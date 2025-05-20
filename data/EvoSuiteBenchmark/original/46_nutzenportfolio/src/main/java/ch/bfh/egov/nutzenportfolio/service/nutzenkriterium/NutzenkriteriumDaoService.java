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
package ch.bfh.egov.nutzenportfolio.service.nutzenkriterium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.common.AutoGrowingList;
import ch.bfh.egov.nutzenportfolio.common.NutzenkriteriumLine;
import ch.bfh.egov.nutzenportfolio.form.FragenForm;
import ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumDao;
import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

/**
 * Implementierende Service-Klasse für Nutzenkriterien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NutzenkriteriumDaoService implements NutzenkriteriumService {
  private NutzenkriteriumDao dao;
  private CommonService cService;
  private KategorieService kService;
  private AuswahlfeldService aService;
  private CustomizingService customizingService;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für Nutzenkriterien. 
   * 
   * @param dao                               Nutzenkriterien DataAcessObject-Interface
   */
  public NutzenkriteriumDaoService(NutzenkriteriumDao dao) {
    this.dao = dao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumIbatisDao#getAssignmentById(Nutzenkriterium)
   */
  public Nutzenkriterium getAssignmentById(Nutzenkriterium kriterium) {
    return dao.getAssignmentById(kriterium);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumIbatisDao#getByProjektId(NaOpNu)
   */
  public List<Nutzenkriterium> getByProjektId(NaOpNu naOpNu) {
    return dao.getByProjektId(naOpNu);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumIbatisDao#getAssignments(Nutzenkriterium)
   */
  public List<Nutzenkriterium> getAssignments(Nutzenkriterium kriterium) {
    return dao.getAssignments(kriterium);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumIbatisDao#update(Nutzenkriterium)
   */
  public void update(Nutzenkriterium kriterium) {
    dao.updateAssignment(kriterium);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumIbatisDao#unsetAssignmentGewichtung(Integer)
   */
  public void unsetAssignmentGewichtung(Integer naOpNuId) {
    dao.unsetAssignmentGewichtung(naOpNuId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.nutzenkriterium.NutzenkriteriumIbatisDao#deleteAssignments(Integer)
   */
  public void deleteAssignments(Integer naOpNuId) {
    dao.deleteAssignments(naOpNuId);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch Übergabe anderer,
   * benötigter Service-Objekte.
   * 
   * @param aService                   AuswahlFeldService Objekt
   * @param cService                   CommonService Objekt
   * @param kService                   KategorieService Objekt
   * @param customizingService         CustomizingService Objekt
   */
  public void init(
      AuswahlfeldService aService,
      CommonService cService,
      KategorieService kService,
      CustomizingService customizingService) {
    this.aService = aService;
    this.cService = cService;
    this.kService = kService;
    this.customizingService = customizingService;
  }
  
  /**
   * Stellt eine Liste aller Nutzenkriterien in den request.
   * 
   * @param request                   der HttpServletRequest
   */
  public void list(HttpServletRequest request) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    List<Nutzenkriterium> kriterien = dao.getAll(mandantId);
    
    // Sortieren der Nutzenkriterien nach Namen
    Collections.<Nutzenkriterium>sort(kriterien, new Comparator<Nutzenkriterium>() {
      public int compare(Nutzenkriterium a, Nutzenkriterium b) {
        String name1 = a.getName().toLowerCase();
        String name2 = b.getName().toLowerCase();
        return name1.compareTo(name2);
      }
    });
    request.setAttribute(Constants.NUTZENKRITERIEN, kriterien);
  }
  
  /**
   * Stellt eine Liste von Nutzenkriterien sowie deren zugehörigen
   * Kategorien in den Request. Pro Request bzw. pro Seitenaufruf
   * wird eine Kategorie mit den jeweiligen Nutzenkriterien angezeigt.
   * Mit einem Klick auf "weiter" gelangt der Benutzer zur nächsten Seite
   * bzw. der nächsten gewählten Kategorie.
   * 
   * @param request                   der HttpServletRequest
   * @return                          true bei Erfolg, sonst false
   */
  public boolean populate(HttpServletRequest request) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    logger.debug("naOpNuId=" + naOpNuId);
    
    // Zugewiesene Kategorien holen
    Kategorie k = new Kategorie();
    k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    k.setMandantId(mandantId);
    List<Kategorie> kategorien = kService.getAssignments(k);
    Integer kategorieId = null;
    Integer step = null;
    
    if (kategorien.size() == 0) {
      return false;
    }
    
    // 1 Schritt pro Kategorie. Parameter überprüfen.
    String stepStr = request.getParameter(Constants.STEP);
    String next = request.getParameter(Constants.NEXT);
    logger.debug("Angegebener step: " + stepStr);
    try {
      step = new Integer(stepStr);
    } catch (NumberFormatException nfex) {
      step = 1;
    }
    
    // Denselben Schritt wiederholen, wenn nicht "weiter" geklickt wurde
    if (next == null && step > 1) {
      --step;
    }
    
    // Kategorien in Array stellen
    Integer[] kategorienIdArr = new Integer[kategorien.size()];
    String[] kategorienNameArr = new String[kategorien.size()];
    int i = 0;
    for (Kategorie kategorie : kategorien) {
      kategorienIdArr[i] = kategorie.getKategorieId();
      kategorienNameArr[i++] = kategorie.getName();
      logger.debug("Kategorie: id=" + kategorienIdArr[i - 1]
          + ", name=" + kategorienNameArr[i - 1]);
    }

    // Auf inkorrekten step Parameter prüfen
    if ((step - 1) >= kategorienIdArr.length) {
      logger.warn("Nicht verfügbarer Schritt ausgewählt!");
      step = 1;
    }
    
    // Auf letzten Schritt prüfen
    if (step == kategorienIdArr.length) {
      request.setAttribute(Constants.LAST_STEP, true);
    }
    
    // Id der Kategorie anhand des step Parameters holen
    kategorieId = kategorienIdArr[step - 1];
    logger.debug("Gewaehlte Kategorie: " + kategorieId);
    request.setAttribute(Constants.KATEGORIE_ID, kategorieId);
    request.setAttribute("kategorie", kategorienNameArr[step - 1]);
    request.setAttribute(Constants.STEP, step + 1);
    
    // Alle Nutzenkriterien zur gewählten Kategorie holen
    Nutzenkriterium n = new Nutzenkriterium();
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    n.setKategorieId(kategorieId);
    n.setMandantId(mandantId);
    
    // Nutzenkriterien aussortieren, die einem anderen Customizing zugeordnet sind
    List<Nutzenkriterium> nutzenkriterien = dao.getByKategorie(n);
    Iterator it = nutzenkriterien.iterator();
    HashMap<String, Nutzenkriterium> map = new HashMap<String, Nutzenkriterium>();
    while (it.hasNext()) {
      Nutzenkriterium nk = (Nutzenkriterium) it.next();
      String name = nk.getName();
      boolean contains = map.containsKey(name);
      Integer nuNaOpNuId = nk.getNutzenattraktivitaetOperativerNutzenId();
      
      // HashMap abfüllen um allfällige doppelte Nutzenkriterien auszusortieren
      // (ist der Fall bei Zuweisungen zu anderen Customizings)
      if (!contains || nuNaOpNuId != null && contains && nuNaOpNuId.equals(naOpNuId)) {
        map.put(name, nk);
      }
    }
    nutzenkriterien = new ArrayList<Nutzenkriterium>(map.values());
    request.setAttribute(Constants.NUTZENKRITERIEN, nutzenkriterien);
    
    // Operativer Nutzen -> Projektbetroffene
    if (cService.isOperativerNutzen(request)) {
      request.setAttribute(Constants.PROJEKTBETROFFENE, true);
    }

    Integer levels = (Integer) request.getSession().getAttribute(Constants.ABSTUFUNGEN);
    request.setAttribute("totalKategorien", ((levels != null) ? kategorien.size() : 0));

    // Alle zugewiesenen Nutzenkriterien zur gewählten Kategorie holen
    List nAssigned = dao.getAssignments(n);
    request.setAttribute(Constants.NA_OP_NU_ID, naOpNuId);
    request.setAttribute(Constants.NUTZENKRITERIEN_ASSIGNED, nAssigned);
    boolean isNa = !cService.isOperativerNutzen(request);
    setStatus(request, kategorien, mandantId, naOpNuId, isNa);
    return true;
  }
  
  /**
   * Füllt ein Formular mit bestehenden Daten eines Nutzenkriteriums ab,
   * falls eine entsprechende id angegeben wurde.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void edit(HttpServletRequest request, DynaActionForm form) {
    if (isUpdate(request, form)) {
      Nutzenkriterium n = createNutzenkriterium(request, form);
      Integer mandantId = n.getMandantId();
      n = dao.getById(n);
      form.set("name", n.getName());
      form.set("beschreibung", n.getBeschreibung());
      form.set("frageManagementDefault", n.getFrageManagementDefault());
      form.set("frageProjektbetroffeneDefault", n.getFrageProjektbetroffeneDefault());
      form.set(Constants.KATEGORIE_ID, n.getKategorieId());
      
      // Kategorien in die Session stellen
      logger.debug("Kategorien holen für mandantId=" + mandantId);
      List<Kategorie> kategorien = kService.getAll(mandantId);
      request.getSession().setAttribute(Constants.KATEGORIEN, kategorien);
    }
  }
  
  /**
   * Führt ein update eines bestenenden Nutzenkriteriums anhand von
   * Formulardaten durch.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void update(HttpServletRequest request, DynaActionForm form) {
    logger.debug("Update Nutzenkriterium");
    if (isUpdate(request, form)) {
      logger.debug("Update durchführen");
      Nutzenkriterium n = createNutzenkriterium(request, form);
      dao.update(n);
    }
  }
  
  /**
   * Prüft, ob ein Nutzenkriterium existiert und gibt es zurück.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          das Nutzenkriterium
   */
  public Nutzenkriterium getNutzenkriterium(HttpServletRequest request, DynaActionForm form) {
    Nutzenkriterium n = createNutzenkriterium(request, form);
    return dao.getById(n);
  }
  
  /**
   * Abfrage für das Löschen von einem Nutzenkriterium.
   * 
   * @param request                   der HttpServletRequest
   * @param kriterium                 das zu löschende Nutzenkriterium
   * @param messages                  das ActionMessages Objekt
   */
  public void deleteQuestion(
      HttpServletRequest request,
      Nutzenkriterium kriterium,
      ActionMessages messages) {
    
    // Verknüpfungen prüfen
    linkedTo(request, kriterium);
    
    // Bestätigungsnachricht ausgeben
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", kriterium.getName()));
    
    // Actions zum Löschen und Abbrechen definieren
    request.setAttribute(Constants.ACTION, Constants.VERWALTUNG_NUTZENKRITERIEN_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.NUTZENKRITERIUM_ID);
    request.setAttribute(Constants.ID_VALUE, kriterium.getNutzenkriteriumId());
  }
  
  /**
   * Setzt die Verknüpfungen zu diesem Nutzenkriterium in den Request.
   * Ein Nutzenkriterium kann mit Customizings verknüpft sein. Diese
   * Methode wird bei der Löschabfrage verwendet.
   * 
   * @param request                   der HttpServletRequest
   * @param kritierium                das zu löschende Nutzenkriterium
   */
  public void linkedTo(HttpServletRequest request, Nutzenkriterium kritierium) {
    HashMap<String, List> map = new HashMap<String, List>();
    
    // Customizings suchen, die dieses Nutzenkriterium benötigen
    List<Customizing> customizings = dao.getLinkedCustomizings(kritierium);
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
   * Löscht ein Nutzenkriterium. Falls es mit inaktiven Customizings
   * verknüft ist, werden diese ebenfalls gelöscht. Wenn aktive Customizings
   * verknüft sind, wird das Nutzenkriterium nicht gelöscht.
   * 
   * @param request                   der HttpServletRequest
   * @param kriterium                 das zu löschende Nutzenkriterium
   */
  public void delete(HttpServletRequest request, Nutzenkriterium kriterium) {
    
    // Verknüpfungen prüfen
    linkedTo(request, kriterium);
    Boolean active = (Boolean) request.getAttribute("active");
    
    // Aktives Customizing verknüft. Dies sollte nicht passieren ausser bei
    // einem direkten URL-Aufruf ...
    if (active != null && active) {
      logger.debug("Aktives Customizing verknüpft, Nutzenkriterium wird nicht gelöscht");
      return;
    }
    
    // Customizings löschen
    List<Customizing> customizings = dao.getLinkedCustomizings(kriterium);
    for (Customizing c : customizings) {
      logger.debug("Lösche Customizing " + c.getCustomizingId());
      customizingService.cascadeDelete(request, c);
    }
    
    // Löschen des Nutzenkriteriums
    dao.delete(kriterium);
  }
  
  /**
   * Weist dem aktuellen Customizing das gewählte Nutzenkriterium zu.
   * Wenn das Nutzenkriterium eine Default-Frage besitzt, wird diese
   * zur späteren Verwendung in der Verknüpfungstabelle gespeichert.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit dem gewählten Nutzenkriterium
   */
  public void set(HttpServletRequest request, DynaActionForm form) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    Integer nutzenkriteriumId = (Integer) form.get(Constants.NUTZENKRITERIUM_ID);
    if (nutzenkriteriumId == null) {
      return;
    }
    
    // Nutzenkriterium zuweisen
    logger.debug("Nutzenkriterium zuweisen: " + nutzenkriteriumId);
    Nutzenkriterium n = new Nutzenkriterium();
    n.setNutzenkriteriumId(nutzenkriteriumId);
    n.setMandantId(mandantId);
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    
    // Default fragen holen und setzen
    Nutzenkriterium fragen = dao.getDefaultFragen(n);
    if (cService.isOperativerNutzen(request)) {
      n.setFrageProjektbetroffene(fragen.getFrageProjektbetroffeneDefault());
    }
    else {
      n.setFrageManagement(fragen.getFrageManagementDefault());
    }
    dao.insertAssignment(n);
  }
  
  /**
   * Überprüft, ob das aktuelle Nutzenkriterium zum letzten
   * Schritt bzw. zur letzten gewählten Kategorie gehört. Wenn
   * ja, kann zur nächsten Action gesprungen werden.
   * 
   * @param request                   der HttpServletRequest
   * @return                          true beim letzten Schritt, sonst false
   */
  public boolean next(HttpServletRequest request) {
    
    // Nächste Action?
    String last = request.getParameter(Constants.LAST_STEP);
    if (last != null && last.equals("true")) {
      return true;
    }
    return false;
  }
  
  /**
   * Entfernt die Zuweisung des gewählten Nutzenkriteriums zum
   * aktuellen Customizing.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit dem Nutzenkriterium
   */
  public void remove(HttpServletRequest request, DynaActionForm form) {
    
    // Nutzenkriterium anhand der id holen
    HttpSession session = request.getSession();
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer nutzenkriteriumId = (Integer) form.get(Constants.NUTZENKRITERIUM_ID);
    Integer naOpNuId = (Integer) form.get(Constants.NA_OP_NU_ID);
    Nutzenkriterium n = new Nutzenkriterium();
    n.setNutzenkriteriumId(nutzenkriteriumId);
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    n.setMandantId(mandantId);
    
    n = dao.getAssignmentById(n);
    
    // Zuweisung löschen
    if (n != null) {
      logger.debug("Zuweisung nutzenkriteriumId=" + nutzenkriteriumId
          + ", nutzenattraktivitaetOperativerNutzenId=" + n.getNutzenattraktivitaetOperativerNutzenId()
          + " wird gelöscht.");
      dao.deleteAssignment(n);
    }
  }
  
  /**
   * Setzt die zugehörigen Fragen von Nutzenkriterien zur Anzeige
   * in den Request.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit den Nutzenkriterien
   * @return                          true bei Erfolg, sonst false
   */
  public boolean populateFragen(HttpServletRequest request, FragenForm form) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    boolean isOperativerNutzen = cService.isOperativerNutzen(request);
    
    // Alle zugewiesenen Nutzenkriterien zur gewählten Kategorie holen
    Nutzenkriterium n = new Nutzenkriterium();
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    n.setMandantId(mandantId);
    List<Nutzenkriterium> kriterien = dao.getAssignments(n);
    
    if (kriterien.size() == 0) {
      return false;
    }
    
    // Formulardaten wieder abfüllen (bei gescheiterter Validierung)
    AutoGrowingList list = form.getNutzenkriteriumLine();
    if (list != null) {
      
      // Formulardaten zum Abgleich in eine HashMap stellen
      Iterator it = list.iterator();
      HashMap<Integer, String> formData = new HashMap<Integer, String>();
      while (it.hasNext()) {
        NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next();
        formData.put(nl.getNutzenkriteriumId(), nl.getFrage());
      }
      
      // Nutzennkriterium suchen
      for (int i = 0; i < kriterien.size(); i++) {
        Nutzenkriterium kriterium = (Nutzenkriterium) kriterien.get(i);
        Integer nutzenkriteriumId =
            (Integer) kriterium.getNutzenkriteriumId();
        if (formData.containsKey(nutzenkriteriumId)) {
          
          // Frage mit Formulardaten ersetzen
          String frage = formData.get(nutzenkriteriumId);
          
          // Fragen für Projektbetroffene
          if (isOperativerNutzen) {
            logger.debug("Ersetze Frage in Datenbank: "
                + kriterium.getFrageProjektbetroffene()
                + " mit Frage aus Formular: " + frage);
            kriterium.setFrageProjektbetroffene(frage);
          }
          
          // Fragen für Management
          else {
            logger.debug("Ersetze Frage in Datenbank: "
                + kriterium.getFrageManagement()
                + " mit Frage aus Formular: " + frage);
            kriterium.setFrageManagement(frage);
          }
          kriterien.set(i, kriterium);
        }
      }
    }
    
    // Operativer Nutzen -> Projektbetroffene
    if (isOperativerNutzen) {
      request.setAttribute(Constants.PROJEKTBETROFFENE, true);
    }
    request.setAttribute(Constants.NUTZENKRITERIEN, kriterien);

    // Anzahl Kategorien holen
    Kategorie k = new Kategorie();
    k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    k.setMandantId(mandantId);
    List<Kategorie> kategorien = kService.getAssignments(k);
    request.setAttribute("totalKategorien", kategorien.size());
    return true;
  }
  
  /**
   * Setzt die eingegebenen Daten zur Direkten Gewichtung aller
   * gewählten Nutzenkriterien in den Request, um diese anzeigen
   * zu können. Bei gescheiterter Validierung werden die Daten
   * erneut angezeigt.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit den Nutzenkriterien
   */
  public void populateDirekteGewichtung(HttpServletRequest request, FragenForm form) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    
    // Alle zugewiesenen Nutzenkriterien zur gewählten Kategorie holen
    Nutzenkriterium n = new Nutzenkriterium();
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    n.setMandantId(mandantId);
    List<Nutzenkriterium> kriterien = dao.getAssignments(n);
    
    // Formulardaten wieder abfüllen (bei gescheiterter Validierung)
    AutoGrowingList list = form.getNutzenkriteriumLine();
    if (list != null) {
      
      // Formulardaten zum Abgleich in eine HashMap stellen
      Iterator it = list.iterator();
      HashMap<Integer, Double[]> formData = new HashMap<Integer, Double[]>();
      while (it.hasNext()) {
        NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next();
        Double kategorieGewichtung = 0.0;
        Double gewichtung = 0.0;
        try {
          kategorieGewichtung = new Double(nl.getKategorieGewichtung());
        } catch (Exception e) {}
        try {
          gewichtung = new Double(nl.getGewichtung());
        } catch (Exception e) {}
        Double[] gewichtungen = {
            gewichtung,
            kategorieGewichtung};
        formData.put(nl.getNutzenkriteriumId(), gewichtungen);
      }
      
      // Nutzennkriterium suchen
      for (int i = 0; i < kriterien.size(); i++) {
        Nutzenkriterium kriterium = (Nutzenkriterium) kriterien.get(i);
        Integer nutzenkriteriumId =
            (Integer) kriterium.getNutzenkriteriumId();
        if (formData.containsKey(nutzenkriteriumId)) {
          
          // Gewichtung mit Formulardaten ersetzen
          Double[] gewichtungen = formData.get(nutzenkriteriumId);
          kriterium.setGewichtung(gewichtungen[0]);
          kriterium.setKategorieGewichtung(gewichtungen[1]);
          kriterien.set(i, kriterium);
        }
      }
    }
    
    request.setAttribute(Constants.NUTZENKRITERIEN, kriterien);
  }
  
  /**
   * Speichert die Fragen zu den gewählten Nutzenkriterien ab.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit den Nutzenkriterien
   * @param errors                    das ActionMessages Objekt zum Speichern
   *                                  von Fehlermeldungen
   * @return                          true bei Erfolg, sonst false
   */
  public boolean saveFragen(
      HttpServletRequest request,
      FragenForm form,
      ActionMessages errors) {
    AutoGrowingList list = form.getNutzenkriteriumLine();
    if (list == null) {
      return false;
    }
    
    // Fragen überprüfen
    Iterator it = list.iterator();
    while (it.hasNext()) {
      NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next();
      String frage = nl.getFrage();
      if (frage == null || frage.length() == 0) {
        errors.add("nutzenkriterium_" + nl.getNutzenkriteriumId(),
            new ActionMessage("errors.required", "Frage"));
        return false;
      }
    }
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    
    // Fragen speichern
    it = list.iterator();
    while (it.hasNext()) {
      NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next();
      Nutzenkriterium n = new Nutzenkriterium();
      n.setNutzenkriteriumId(nl.getNutzenkriteriumId());
      n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      if (cService.isOperativerNutzen(request)) {
        n.setFrageProjektbetroffene(nl.getFrage());
      }
      else {
        n.setFrageManagement(nl.getFrage());
      }
      
      logger.debug("nutzenkriteriumId: " + n.getNutzenkriteriumId());
      logger.debug("nutzenattraktivitaetId: " + naOpNuId);
      logger.debug("frage: " + nl.getFrage());
      dao.updateAssignment(n);
    }
    return true;
  }
  
  /**
   * Fügt ein neues Nutzenkriterium ein und wählt dieses anschliessend
   * automatisch aus.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit dem neuen Nutzenkriterium
   * @param errors                    das ActionMessages Objekt zum Speichern
   *                                  von Fehlermeldungen
   * @return                          true bei Erfolg, sonst false
   */
  public boolean add(
      HttpServletRequest request,
      DynaActionForm form,
      ActionMessages errors,
      ActionMessages messages) {
    
    // Ids holen
    HttpSession session = request.getSession();
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer customizingId = (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    
    // Formulardaten holen
    String name = (String) form.get("name");
    Integer kategorieId = (Integer) form.get(Constants.KATEGORIE_ID);
    logger.debug("name=" + name);
    logger.debug("kategorieId=" + kategorieId);
    
    // Formulardaten überprüfen
    if (name == null || name.length() == 0) {
      errors.add("name",
          new ActionMessage("errors.required", "Name"));
      return false;
    }
    
    Nutzenkriterium n = new Nutzenkriterium();
    n.setName(name);
    n.setKategorieId(kategorieId);
    n.setMandantId(mandantId);
    
    // Auf duplikate prüfen
    if (dao.getByName(n) != null) {
      messages.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.duplicate", "Nutzenkriterium"));
      return false;
    }
    
    // Nutzenkriterium einfügen
    dao.insert(n);
    
    // Nutzenkriterium setzen
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    dao.insertAssignment(n);
    return true;
  }
  
  /**
   * Speichert die Direkte Gewichtung von Nutzenkriterien
   * der Nutzenattraktivität bzw. des Operativen Nutzens.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das Formular mit den Nutzenkriterien
   * @param errors                    das ActionMessages Objekt zum Speichern
   *                                  von Fehlermeldungen
   * @return                          true bei Erfolg, sonst false
   */
  public boolean saveDirekteGewichtung(
      HttpServletRequest request,
      FragenForm form,
      ActionMessages errors) {
    
    // Formulardaten holen
    AutoGrowingList list = form.getNutzenkriteriumLine();
    if (list == null) {
      return false;
    }
    
    // Gewichtungen überprüfen
    Double gewichtung = 0.0;
    Iterator it = list.iterator();  
    while (it.hasNext()) {
      NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next();
      Integer nutzenkriteriumId = nl.getNutzenkriteriumId();
      logger.debug("nutzenkrtieriumId=" + nutzenkriteriumId);
      String g = nl.getGewichtung();
      logger.debug("Gewichtung: " + g);
      try {
        gewichtung += new Double(g);
      } catch (Exception e) {}
    }
    
    logger.debug("Total gewichtungen: " + gewichtung);
    if (gewichtung < 99.999 || gewichtung > 100.0001) {
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.gewichtung.failure"));
      return false;
    }
    
    /*
    // Gewichtungen überprüfen
    Iterator it = list.iterator();
    ArrayList<Double> gewichtungsListe = new ArrayList<Double>(); 
    HashMap<Integer, ArrayList<Double>> gewichtungen = new HashMap<Integer, ArrayList<Double>>(); 
    Double kategorieGewichtung = 0.0;
    boolean isFirst = true;
    Integer tmpKategorieId = null;
    while (it.hasNext()) {
      NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next();
      Integer kategorieId = nl.getKategorieId();
      Integer nutzenkriteriumId = nl.getNutzenkriteriumId();
      logger.debug("kategorieId=" + kategorieId);
      logger.debug("nutzenkrtieriumId=" + nutzenkriteriumId);
      
      // Gewichtung (muss pro Kategorie 100% ergeben)
      String g = nl.getGewichtung();
      logger.debug("Gewichtung: " + g);
      
      
      // Gewichtung für die selbe Kategorie in eine Liste stellen
      if (isFirst || kategorieId == null) {
        isFirst = false;
        tmpKategorieId = kategorieId;
        try {
          gewichtungsListe.add(new Double(g));
        } catch (Exception e) {}
      }
      
      // Nächste Kategorie 
      else {
        gewichtungen.put(tmpKategorieId, gewichtungsListe);
        gewichtungsListe = new ArrayList<Double>();
        tmpKategorieId = kategorieId;
        try {
          gewichtungsListe.add(new Double(g));
        } catch (Exception e) {}
      }
      
      // kategorieGewichtung
      String kg = nl.getKategorieGewichtung();
      logger.debug("Gewichtung Kategorie: " + kg);
      try {
        kategorieGewichtung += new Double(kg);
      } catch (Exception e) {}
    }
    
    it = gewichtungen.keySet().iterator();
    while (it.hasNext()) {
      Integer key = (Integer) it.next();
      gewichtungsListe = gewichtungen.get(key);
      Double gewichtung = 0.0;
      for (Double d : gewichtungsListe) {
        gewichtung += d;
      }
      
      logger.debug("Total gewichtungen: " + gewichtung);
      if (gewichtung < 99.999 || gewichtung > 100) {
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.gewichtung.failure"));
        return false;
      }
    }
    logger.debug("Total gewichtungen Kategorie: " + kategorieGewichtung);
    if (kategorieGewichtung < 99.999 || kategorieGewichtung > 100) {
      errors.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.gewichtung.failure"));
      return false;
    }*/
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    
    // Allfällige indirekte Gewichtungen löschen
    Auswahlfeld a = new Auswahlfeld();
    a.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    aService.deleteGewichtungen(a); 
    
    // Gewichtung speichern
    it = list.iterator();
    while (it.hasNext()) {
      NutzenkriteriumLine nl = (NutzenkriteriumLine) it.next(); 
      Nutzenkriterium n = new Nutzenkriterium();
      n.setNutzenkriteriumId(nl.getNutzenkriteriumId());
      n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      n.setGewichtung(new Double(nl.getGewichtung()));
      dao.updateAssignmentGewichtung(n);
      
      // Gewichtung der Kategorien speichern
      Integer kategorieId = nl.getKategorieId();
      if (kategorieId != null) {
        Double g = null;
        try {
          g = new Double(nl.getKategorieGewichtung());
        } catch (Exception e) {}
        Kategorie k = new Kategorie();
        k.setGewichtung(g);
        k.setKategorieId(nl.getKategorieId());
        k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
        kService.updateAssignment(k);
      }
    }

    // Status setzen
    boolean isNa = !cService.isOperativerNutzen(request);
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

  /**
   * Prüft, ob eine Direkte Gewichtung vorliegt oder nicht.
   * 
   * @param request                   der HttpServletRequest
   * @return                          true bei Direkter Gewichtung, sonst false
   */
  public boolean direkteGewichtung(HttpServletRequest request) {
    Integer typ =
      (Integer) request.getSession().getAttribute(Constants.GEWICHTUNGSTYP);
    logger.debug("Direkte Gewichtung ausgewählt");
    return typ == Constants.GEWICHTUNG_DIREKT;
  }
  
  /**
   * Setzt den Status des Schritts "Nutzenkriterien", sowohl für das 
   * Customizing Nutzenattraktiviät wie für das Customizing Operativer Nutzen.
   * Der Status zeigt in der Navigation an, ob dieser Schritt des Customizings
   * bereits erfolgreich abgeschlossen wurde oder nicht.
   * 
   * @param request                   der HttpServletRequest
   * @param kategorien                eine Liste aller gewählten Kategorien
   * @param mandantId                 die Id des Mandanten
   * @param naOpNuId                  die Id der Nutzenattraktivität bzw.
   *                                  des Operativen Nutzens
   * @param isNa                      true für Nutzenattraktivität, sonst false
   * @return                          Liste aller gewählten Nutzenkriterien
   */
  public List<Nutzenkriterium> setStatus(
      HttpServletRequest request,
      List<Kategorie> kategorien,
      Integer mandantId,
      Integer naOpNuId,
      boolean isNa) {
    
    boolean ok = true;
    for (Kategorie k : kategorien) {
      Nutzenkriterium nk = new Nutzenkriterium();
      nk.setKategorieId(k.getKategorieId());
      nk.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      List<Nutzenkriterium> nks = dao.getByNaOpNuAndKategorie(nk);
      logger.debug(nks.size() + " Nutzenkriterien in der Liste");
      if (nks == null || nks.size() == 0) {
        ok = false;
        break;
      }
    }
  
    HttpSession session = request.getSession();
    if (ok && isNa) {
      session.setAttribute(Constants.NUTZENKRITERIEN_NA, true);
      session.setAttribute(Constants.FRAGEN_NA, true);
    }
    else if (ok) {
      session.setAttribute(Constants.NUTZENKRITERIEN_OP_NU, true);
      session.setAttribute(Constants.FRAGEN_OP_NU, true);
    }
    
    if (ok) {
      Nutzenkriterium nk = new Nutzenkriterium();
      nk.setMandantId(mandantId);
      nk.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      return dao.getAssignments(nk);
    }
    else if (!ok && isNa) {
      session.removeAttribute(Constants.NUTZENKRITERIEN_NA);
      session.removeAttribute(Constants.FRAGEN_NA);
    }
    else {
      session.removeAttribute(Constants.NUTZENKRITERIEN_OP_NU);
      session.removeAttribute(Constants.FRAGEN_OP_NU);
    }
    return null;
  }
  
  /**
   * Löscht die Fragebogen-Gewichtung des aktuellen Customizings.
   * 
   * 
   * @param request                   der HttpServletRequest
   */
  public void deleteFragebogenGewichtung(HttpServletRequest request) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    
    // allfällige Gewichtung über Fragebogen löschen
    Auswahlfeld a = new Auswahlfeld();
    a = aService.setType(a, Constants.GEWICHTUNG);
    a.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    logger.debug("Fragebogen-Gewichtung löschen:");
    logger.debug("nutzenattraktivitaetOperativerNutzenId=" + a.getNutzenattraktivitaetOperativerNutzenId());
    aService.deleteGewichtungen(a);
  }
  
  /**
   * Selektiert die zuvor gewählte Anzahl von Abstufungen für die
   * Stufenbeschriebe und die Gewichtung.
   * Stellt den gewählten Gewichtungstyp in den Request. Dies dient
   * zur Vorauswahl der richtigen Checkbox im Formular "Gewichtung".
   * 
   * @param request                   der HttpServletRequest
   * @param path                      der Pfad der Action
   */
  public void preselect(HttpServletRequest request, String path) {
    
    // Benötigte ids holen
    HttpSession session = request.getSession();
    Integer customizingId =
      (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    logger.debug("naOpNuId=" + naOpNuId);
    logger.debug("Pfad: " + path);
    
    // Abstufungstatus holen
    List<Auswahlfeld> afs = new ArrayList<Auswahlfeld>();
    if (path.contains(Constants.ABSTUFUNGEN_ACTION)) {
      afs = aService.getAnzahlAbstufungen(naOpNuId);
    }
    
    // Gewichtungsstatus holen
    else {
      afs = aService.getAnzahlGewichtungen(naOpNuId);
    }
    logger.debug(afs.size() + " Abstufungen in der Liste");
    
    request.setAttribute(Constants.ABSTUFUNGEN, afs.size());
    
    // Gewichtung bestimmen
    List<Auswahlfeld> gewichtungen = aService.getGewichtungStatus(naOpNuId);
    if (gewichtungen.size() > 0) {
      request.setAttribute(Constants.GEWICHTUNG_NOT_DIRECT, true);
    }
    else {
      request.removeAttribute(Constants.GEWICHTUNG_NOT_DIRECT);
    }
  }
  
  
  /**
   * Kopiert alle Zuweisungen von Nutzenkriterien von einer Nutzenattraktivität
   * bzw. von einem Operativen Nutzen zu einer neuen Id. Wird zum Kopieren
   * von Customizings verwendet.
   * 
   * @param mandantId                 die Id des Mandanten
   * @param naOpNuId                  die Id der Nutzenattraktivität bzw. des
   *                                  Operativen Nutzens, welche die zu kopierenden
   *                                  Zuweisungen enthält
   * @param newNaOpNuId               die neue Id für die kopierten Zuweisungen
   */
  public void copy(Integer mandantId, Integer naOpNuId, Integer newNaOpNuId) {
    Nutzenkriterium n = new Nutzenkriterium();
    n.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    n.setMandantId(mandantId);
    List<Nutzenkriterium> kriterien = dao.getAssignments(n);
    for (Nutzenkriterium kriterium : kriterien) {
      kriterium.setNutzenattraktivitaetOperativerNutzenId(newNaOpNuId);
      dao.insertAssignment(kriterium);
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
   * Erstellt ein Objekt vom Typ Nutzenkriterium mit der id des Mandanten
   * und, falls angegeben, mit abgefüllten Formulardaten
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          Nutzenkriterium mit mandantId
   */
  private Nutzenkriterium createNutzenkriterium(HttpServletRequest request, DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Nutzenkriterium n = new Nutzenkriterium();
    n.setMandantId(mandantId);
    if (form != null) {
      Integer id = (Integer) form.get(Constants.NUTZENKRITERIUM_ID);
      String name = (String) form.get(Constants.NAME);
      String beschreibung = (String) form.get("beschreibung");
      String frageManagementDefault = (String) form.get("frageManagementDefault");
      String frageProjektbetroffeneDefault = (String) form.get("frageProjektbetroffeneDefault");
      Integer kategorieId = (Integer) form.get(Constants.KATEGORIE_ID);
      n.setNutzenkriteriumId(id);
      n.setName(name);
      n.setBeschreibung(beschreibung);
      n.setFrageManagementDefault(frageManagementDefault);
      n.setFrageProjektbetroffeneDefault(frageProjektbetroffeneDefault);
      n.setKategorieId(kategorieId);
    }
    return n;
  }
  
  /**
   * Überprüft, ob die Id eines Nutzenkriteriums Ziels im Request ist
   * (update) oder nicht (neu). 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          true, wenn das angegebene Nutzenkriterium
   *                                  besteht, sonst false.
   */
  private boolean isUpdate(HttpServletRequest request, DynaActionForm form) {
    Integer id = (Integer) form.get(Constants.NUTZENKRITERIUM_ID);
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }
}
