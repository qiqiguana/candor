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
package ch.bfh.egov.nutzenportfolio.service.kategorie;

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
import ch.bfh.egov.nutzenportfolio.persistence.kategorie.KategorieDao;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.customizing.CustomizingService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

/**
 * Implementierende Service-Klasse f�r Kategorien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class KategorieDaoService implements KategorieService {
  private KategorieDao dao;
  private CommonService cService;
  private CustomizingService customizingService;
  private NutzenkriteriumService nService;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. �bergabe des DataAcessObject-Interfaces f�r Kategorien. 
   * 
   * @param dao                   Kategorien DataAcessObject-Interface
   */
  public KategorieDaoService(KategorieDao dao) {
      this.dao = dao;
  }

  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.kategorie.KategorieIbatisDao#getAll(Integer)
   */
  public List<Kategorie> getAll(Integer mandantId) {
      return dao.getAll(mandantId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.kategorie.KategorieIbatisDao#getByProjektId(NaOpNu)
   */
  public List<Kategorie> getByProjektId(NaOpNu naOpNu) {
    return dao.getByProjektId(naOpNu);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.kategorie.KategorieIbatisDao#getAssignments(Kategorie)
   */
  public List<Kategorie> getAssignments(Kategorie kategorie) {
    return dao.getAssignments(kategorie);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.kategorie.KategorieIbatisDao#updateAssignment(Kategorie)
   */
  public void updateAssignment(Kategorie k) {
    dao.updateAssignment(k);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.kategorie.KategorieIbatisDao#deleteAssignments(Integer)
   */
  public void deleteAssignments(Integer naOpNuId) {
    dao.deleteAssignments(naOpNuId);
  }
  
  /**
   * Initialisierung dieses Service-Objekts durch �bergabe anderer,
   * ben�tigter Service-Objekte.
   * 
   * @param cService                   CommonService Objekt
   * @param customizingService         CustomizingService Objekt
   * @param nService                   NutzenkriteriumService Objekt
   */
  public void init(
      CommonService cService,
      CustomizingService customizingService,
      NutzenkriteriumService nService) {
    this.customizingService = customizingService;
    this.cService = cService;
    this.nService = nService;
  }
  
  /**
   * Stellt eine Liste aller Kategorien in den request.
   * 
   * @param request                   der HttpServletRequest
   */
  public void list(HttpServletRequest request) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    List kategorien = dao.getAll(mandantId);
    request.setAttribute(Constants.KATEGORIEN, kategorien);
  }
  
  /**
   * Stellt eine Liste aller Kategorien in den request
   * und selektiert die angew�hlten Kategorien.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void populate(HttpServletRequest request, DynaActionForm form) {
    
    // Ben�tigte Ids aus der Session holen
    HttpSession session = request.getSession();
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    Integer customizingId = (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    
    // Alle Kategorien holen
    List kategorien = dao.getAll(mandantId);
    request.setAttribute(Constants.KATEGORIEN, kategorien);
    
    // Selektierte Kategorien holen
    Kategorie k = new Kategorie();
    k.setMandantId(mandantId);
    k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    Integer[] idArr = getSelectedIdArr(k);
    form.set("kategorienIds", idArr);
  }
  
  /**
   * F�llt ein Formular mit bestehenden Daten einer Kategorie ab,
   * falls eine entsprechende id angegeben wurde.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void edit(HttpServletRequest request, DynaActionForm form) {
    if (isUpdate(request, form)) {
      Kategorie k = createKategorie(request, form);
      k = dao.getById(k);
      form.set("name", k.getName());
      form.set("beschreibung", k.getBeschreibung());
    }
  }
  
  /**
   * F�hrt ein update einer bestenenden Kategorie anhand von
   * Formulardaten durch.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   */
  public void update(HttpServletRequest request, DynaActionForm form) {
    logger.debug("Update Kategorie");
    if (isUpdate(request, form)) {
      logger.debug("Update durchf�hren");
      Kategorie k = createKategorie(request, form);
      dao.update(k);
    }
  }
  
  /**
   * Pr�ft, ob eine Kategorie existiert und gibt sie zur�ck.
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          die Kategorie
   */
  public Kategorie getKategorie(
      HttpServletRequest request,
      DynaActionForm form) {
    Kategorie k = createKategorie(request, form);
    return dao.getById(k);
  }
  
  /**
   * Abfrage f�r das L�schen von einer Kategorie.
   * 
   * @param request                   der HttpServletRequest
   * @param kategorie                 die zu l�schende Kategorie
   * @param messages                  das ActionMessages Objekt
   */
  public void deleteQuestion(
      HttpServletRequest request,
      Kategorie kategorie,
      ActionMessages messages) {
    
    // Verkn�pfungen pr�fen
    linkedTo(request, kategorie);
    
    // Best�tigungsnachricht ausgeben
    messages.add(ActionMessages.GLOBAL_MESSAGE,
        new ActionMessage("messages.question.delete", kategorie.getName()));
    
    // Actions zum L�schen und Abbrechen definieren
    request.setAttribute(Constants.ACTION, Constants.VERWALTUNG_KATEGORIEN_ACTION);
    request.setAttribute(Constants.DISPATCH_YES, Constants.DELETE);
    request.setAttribute(Constants.DISPATCH_NO, Constants.LIST);
    request.setAttribute(Constants.ID_NAME, Constants.KATEGORIE_ID);
    request.setAttribute(Constants.ID_VALUE, kategorie.getKategorieId());
  }
  
  /**
   * Setzt die Verkn�pfungen zu dieser Kategorie in den Request.
   * Eine Kategorie kann mit Nutzenkriterien und / oder Customizings
   * verkn�pft sein. Diese Methode wird bei der L�schabfrage verwendet.
   * 
   * @param request                   der HttpServletRequest
   * @param kategorie                 die zu l�schende Kategorie
   */
  public void linkedTo(HttpServletRequest request, Kategorie kategorie) {
    HashMap<String, List> map = new HashMap<String, List>();
    
    // Customizings suchen, die dieses Strategische Ziel ben�tigen
    List<Customizing> customizings = dao.getLinkedCustomizings(kategorie);
    if (customizings.size() > 0) {
      for (Customizing c : customizings) {
        if (c.getStatus()) {
          request.setAttribute("active", true);
          break;
        }
      }
      map.put("Customizings", customizings);
    }
    
    // Nutzenkriterien suchen, die diese Kategorie ben�tigen
    List<Nutzenkriterium> kriterien = dao.getLinkedNutzenkriterien(kategorie);
    if (kriterien.size() > 0) {
      map.put(Constants.NUTZENKRITERIEN, kriterien);
    }
    
    request.setAttribute("linked", map);
    
  }
  
  /**
   * L�scht eine Kategorie. Falls sie mit inaktiven Customizings
   * verkn�ft ist, werden diese ebenfalls gel�scht. Wenn aktive Customizings
   * verkn�ft sind, wird die Kategorie nicht gel�scht. Verkn�pfte
   * Nutzenkriterien werden nicht gel�scht.
   * 
   * @param request                   der HttpServletRequest
   * @param kategorie                 die zu l�schende Kategorie
   */
  public void delete(HttpServletRequest request, Kategorie kategorie) {
    
    // Verkn�pfungen pr�fen
    linkedTo(request, kategorie);
    Boolean active = (Boolean) request.getAttribute("active");
    
    // Aktives Customizing verkn�ft. Dies sollte nicht passieren ausser bei
    // einem direkten URL-Aufruf ...
    if (active != null && active) {
      logger.debug("Aktives Customizing verkn�pft, Kategorie wird nicht gel�scht");
      return;
    }
    
    // Customizings l�schen
    List<Customizing> customizings = dao.getLinkedCustomizings(kategorie);
    for (Customizing c : customizings) {
      logger.debug("L�sche Customizing " + c.getCustomizingId());
      customizingService.cascadeDelete(request, c);
    }
    
    // Id der Kategorie aus der Nutzenkriterium Tabelle l�schen
    List<Nutzenkriterium> kriterien = dao.getLinkedNutzenkriterien(kategorie);
    for (Nutzenkriterium n : kriterien) {
      logger.debug("L�sche Zuweisung kategorieId des Nutzenkriteriums " + n.getNutzenkriteriumId());
      n.setKategorieId(null);
      nService.update(n);
    }
    
    // L�schen der Kategorie
    dao.delete(kategorie);
  }
  
  /**
   * Speichert die gew�hlten Zuweisungen von Kategorien zum aktuellen Customizing.
   * 
   * @param request                       HttpServletRequest
   * @param form                          Formular mit Daten der neuen Kategorie
   * @return                              true bei Erfolg, sonst false
   */
  public boolean save(HttpServletRequest request, DynaActionForm form) {
    
    // Ben�tigte Ids aus der Session holen
    HttpSession session = request.getSession();
    Integer customizingId = (Integer) session.getAttribute(Constants.CUSTOMIZING_ID);
    Integer mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
    
    // Formulardaten holen
    Integer[] ids = (Integer[]) form.get("kategorienIds");
    if (ids == null || ids.length == 0) {
      logger.info("Keine Kategorien gew�hlt");
      populate(request, form);
      return false;
    }
    
    // Zuweisungen l�schen
    Integer naOpNuId = cService.getNaOpNuId(request, customizingId);
    dao.deleteAssignments(naOpNuId);
    
    // Kategorien zuweisen
    logger.debug("Kategorien angew�hlt: " + ids.length);
    for (int i = 0; i < ids.length; i++) {
      Integer kategorieId = ids[i];
      logger.debug("F�ge Kategorie " + kategorieId + " hinzu.");
      Kategorie k = new Kategorie();
      k.setKategorieId(kategorieId);
      k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
      dao.insertAssignment(k);
    }
    boolean isNa = !cService.isOperativerNutzen(request);
    setStatus(request, mandantId, naOpNuId, isNa);
    return true;
  }
  
  /**
   * F�gt eine neue Kategorie ein.
   * 
   * @param request                       HttpServletRequest
   * @param form                          Formular mit Daten der neuen Kategorie
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
    
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Kategorie k = new Kategorie();
    k.setMandantId(mandantId);
    k.setName(name);
    k.setBeschreibung(beschreibung);
    
    // Auf duplikate pr�fen
    if (dao.getByName(k) != null) {
      messages.add(ActionMessages.GLOBAL_MESSAGE,
          new ActionMessage("errors.duplicate", "Kategorie"));
      return false;
    }
    
    // Kategorie einf�gen
    dao.insert(k);
    return true;
  }
  
  /**
   * Kopiert alle zugewiesenen Kategorien eines Customizings
   * in ein neues.
   * 
   * @param mandantId                     die Id des Mandanten
   * @param naOpNuId                      die Id der aktuellen Nutzenattraktivit�t
   *                                      bzw. des aktuellen Operativen Nutzens
   * @param newNaOpNuId                   die Id der neuen Nutzenattraktivit�t
   *                                      bzw. des neuen Operativen Nutzens
   */
  public void copy(Integer mandantId, Integer naOpNuId, Integer newNaOpNuId) {
    Kategorie k = new Kategorie();
    k.setMandantId(mandantId);
    k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    
    // Zugewiesene Kategorien holen
    List<Kategorie> kategorien = dao.getAssignments(k);
    for (Kategorie kategorie : kategorien) {
      kategorie.setNutzenattraktivitaetOperativerNutzenId(newNaOpNuId);
      
      // Kategorien in die neue Nutzenattraktivit�t bzw. den
      // neuen Operativen Nutzen kopieren
      dao.insertAssignment(kategorie);
    }
  }
  
  /**
   * Setzt den Navigations-Status von den Kategorien
   * eines Customizings. 
   * 
   * @param request                       HttpServletRequest
   * @param mandantId                     die Id des Mandanten
   * @param naOpNuId                      die Id der Nutzenattraktivit�t
   *                                      bzw. des Operativen Nutzens
   * @return                              Liste aller zugewiesenen Kategorien
   */
  public List<Kategorie> setStatus(
      HttpServletRequest request,
      Integer mandantId,
      Integer naOpNuId,
      boolean isNa) {
    Kategorie k = new Kategorie();
    k.setMandantId(mandantId);
    k.setNutzenattraktivitaetOperativerNutzenId(naOpNuId);
    
    // Zugewiesene Kategorien holen
    List<Kategorie> kategorien = dao.getAssignments(k);
    HttpSession session = request.getSession();
    
    // Mindestens eine Kategorie muss angew�hlt sein
    if (kategorien != null && kategorien.size() > 0) {
      // Status gr�n, Status-Flag in die Session setzen
      
      // Status f�r das Customizing Nutzenattraktivit�t
      if (isNa) {
        session.setAttribute(Constants.KATEGORIEN_NA, true);
      }
      
      // Status f�r das Customizing Operativer Nutzen
      else {
        session.setAttribute(Constants.KATEGORIEN_OP_NU, true);
      }
      return kategorien;
    }
    
    // Status rot, eventuell vorhandenes Flag aus der Session l�schen 
    else {
      
      // Status f�r das Customizing Nutzenattraktivit�t
      if (isNa) {
        session.removeAttribute(Constants.KATEGORIEN_NA);
      }
      
      // Status f�r das Customizing Operativer Nutzen
      else {
        session.removeAttribute(Constants.KATEGORIEN_OP_NU);
      }
    }
    return null;
  }

  /**
   * Holt die die zugewiesenen Kategorien zu dem angegeben
   * Customizing und gibt sie als Array von Ids zur�ck.<br/>
   * TODO Customizing Objekt �bergeben macht mehr Sinn
   * 
   * @param k                             Daten des aktuellen Customizings
   * @return                              Integer Array mit Ids aller
   *                                      zugewiesenen Kategorien
   */
  private Integer[] getSelectedIdArr(Kategorie k) {
    logger.debug("Hole kategorieIds: mandantId="
        + k.getMandantId() + ", customizingId=" + k.getCustomizingId());
    
    // TODO Performance-Gewinn m�glich durch eigene SQL Abfrage
    List<Kategorie> kategorien = dao.getAssignments(k);
    logger.debug(kategorien.size() + " ids gefunden.");
    Integer[] ids = new Integer[kategorien.size()];
    int i = 0;
    for (Kategorie kategorie : kategorien) {
      ids[i++] = kategorie.getKategorieId();
    }

    return ids;
  }
  
  /**
   * Erstellt ein Objekt vom Typ Kategorie mit der id des Mandanten
   * und, falls angegeben, mit abgef�llten Formulardaten
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          Kategorie mit mandantId
   */
  private Kategorie createKategorie(HttpServletRequest request, DynaActionForm form) {
    Integer mandantId = (Integer) request.getSession().getAttribute(Constants.MANDANT_ID);
    Kategorie kategorie = new Kategorie();
    kategorie.setMandantId(mandantId);
    if (form != null) {
      Integer id = (Integer) form.get(Constants.KATEGORIE_ID);
      String name = (String) form.get("name");
      String beschreibung = (String) form.get("beschreibung");
      kategorie.setKategorieId(id);
      kategorie.setName(name);
      kategorie.setBeschreibung(beschreibung);
    }
    return kategorie;
  }
  
  /**
   * �berpr�ft, ob die Id einer Kategorie im Request ist
   * (update) oder nicht (neu). 
   * 
   * @param request                   der HttpServletRequest
   * @param form                      das DynaActionForm
   * @return                          true, wenn die angegebene Kategorie
   *                                  besteht, sonst false.
   */
  private boolean isUpdate(HttpServletRequest request, DynaActionForm form) {
    Integer id = (Integer) form.get(Constants.KATEGORIE_ID);
    if (id == null || id == 0) {
        return false;
    }
    return true;
  }

}
