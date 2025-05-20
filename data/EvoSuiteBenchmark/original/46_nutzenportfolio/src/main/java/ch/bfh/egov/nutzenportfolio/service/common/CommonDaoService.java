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
package ch.bfh.egov.nutzenportfolio.service.common;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.bfh.egov.nutzenportfolio.Constants;
import ch.bfh.egov.nutzenportfolio.form.NaOpNuForm;
import ch.bfh.egov.nutzenportfolio.persistence.common.CommonDao;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Projektattraktivitaet;

/**
 * Implementierende Service-Klasse für gemeinsam genutzte Methoden.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class CommonDaoService implements CommonService {
  private CommonDao dao;
  private Log logger = LogFactory.getLog(this.getClass());

  /**
   * Konstruktor. Übergabe des DataAcessObject-Interfaces für
   * gemeinsam genutzte Methoden. 
   * 
   * @param dao                          Common DataAcessObject-Interface                      
   */
  public CommonDaoService(CommonDao dao) {
    this.dao = dao;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#getProjektattraktivitaetIdByCustomizingId(Integer)
   */
  public Integer getProjektattraktivitaetIdByCustomizingId(Integer customizingId) {
    return dao.getProjektattraktivitaetIdByCustomizingId(customizingId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#getNutzenattraktivitaetIdByCustomizingId(Integer)
   */
  public Integer getNutzenattraktivitaetIdByCustomizingId(Integer customizingId) {
    return dao.getNutzenattraktivitaetIdByCustomizingId(customizingId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#getOperativerNutzenIdByCustomizingId(Integer)
   */
  public Integer getOperativerNutzenIdByCustomizingId(Integer customizingId) {
    return dao.getOperativerNutzenIdByCustomizingId(customizingId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#insertProjektattraktivitaet(Projektattraktivitaet)
   */
  private Integer insertProjektattraktivitaet(Projektattraktivitaet p) {
    Integer id = dao.insertProjektattraktivitaet(p);
    logger.debug("id: " + id);
    return id;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#insertNutzenattraktivitaetOperativerNutzen(NaOpNu)
   */
  private Integer insertNutzenattraktivitaetOperativerNutzen(NaOpNu n) {
    Integer id = dao.insertNutzenattraktivitaetOperativerNutzen(n);
    logger.debug("id: " + id);
    return id;
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#deleteProjektattraktivitaet(Integer)
   */
  public void deleteProjektattraktivitaet(Integer projektattraktivitaetId) {
    dao.deleteProjektattraktivitaet(projektattraktivitaetId);
  }
  
  /**
   * @see ch.bfh.egov.nutzenportfolio.persistence.common.CommonIbatisDao#deleteNutzenattraktivitaetOperativerNutzen(Integer)
   */
  public void deleteNutzenattraktivitaetOperativerNutzen(Integer naOpNuId) {
    dao.deleteNutzenattraktivitaetOperativerNutzen(naOpNuId);
  }
  
  /**
   * Speichert die Teile eines Customizings (Projektattraktivität,
   * Nutzenattraktivität und Operativer Nutzen).
   * 
   * @param customizingId         die Id des Customizings
   */
  public void insertCustomizingParts (Integer customizingId) {
    Random rnd = new Random();
    
    Integer paId = dao.getProjektattraktivitaetIdByCustomizingId(customizingId);
    Integer naId = dao.getNutzenattraktivitaetIdByCustomizingId(customizingId);
    Integer opNuId = dao.getOperativerNutzenIdByCustomizingId(customizingId);
    if (paId != null || naId != null || opNuId != null) {
      logger.warn("Die Bestandteile des Customizings " + customizingId
          + " wurden bereits gespeichert!");
      return;
    }
    
    // Projektattraktivität einfügen
    Projektattraktivitaet p = new Projektattraktivitaet();
    long paUID = ((System.currentTimeMillis() >>> 16) << 16) + rnd.nextLong();
    p.setPaUID(paUID);
    p.setCustomizingId(customizingId);
    insertProjektattraktivitaet(p);
    
    // Nutzenattraktivität einfügen
    NaOpNu n = new NaOpNu();
    long naOpNuUID = ((System.currentTimeMillis() >>> 16) << 16) + rnd.nextLong();
    n.setNaOpNuUID(naOpNuUID);
    n.setCustomizingId(customizingId);
    n.setNutzenattraktivitaet(true);
    n.setOperativerNutzen(false);
    insertNutzenattraktivitaetOperativerNutzen(n);
    
    // Operativer Nutzen einfügen
    naOpNuUID = ((System.currentTimeMillis() >>> 16) << 16) + rnd.nextLong();
    n.setNaOpNuUID(naOpNuUID);
    n.setNutzenattraktivitaet(false);
    n.setOperativerNutzen(true);
    insertNutzenattraktivitaetOperativerNutzen(n);
  }
  
  /**
   * Holt die Id der Nutzenattraktivität bzw. des Operativer Nutzens
   * mithilfe der Id des Customizings aus dem angegebenen Formular.
   * 
   * @param form                    das Formular 
   * @param customizingId           die Id des Customizings
   * @return                        die Id utzenattraktivität bzw.
   *                                des Operativer Nutzens
   */
  public Integer getNaOpNuId(NaOpNuForm form, Integer customizingId) {
    if (form.getOpNu()) {
      return getOperativerNutzenIdByCustomizingId(customizingId);
    }
    return this.getNutzenattraktivitaetIdByCustomizingId(customizingId);
  }
  
  /**
   * Holt die Id der Nutzenattraktivität bzw. des Operativer Nutzens
   * mithilfe der Id des Customizings aus dem Request.
   * 
   * @param request                 der HttpServletRequest
   * @param customizingId           die Id des Customizings
   * @return                        die Id utzenattraktivität bzw.
   *                                des Operativer Nutzens
   */
  public Integer getNaOpNuId(HttpServletRequest request, Integer customizingId) {
    if (isOperativerNutzen(request)) {
      return getOperativerNutzenIdByCustomizingId(customizingId);
    }
    return this.getNutzenattraktivitaetIdByCustomizingId(customizingId);
  }
  
  /**
   * Gibt zurück ob wir uns im Customizing Operativer Nutzens befinden
   * oder nicht.
   * 
   * @param request                 der HttpServletRequest
   * @return                        true bei Operativer Nutzen, sonst false
   */
  public boolean isOperativerNutzen(HttpServletRequest request) {
    
    // Parameter Operativer Nutzen holen
    String param = request.getParameter(Constants.OPERATIVER_NUTZEN);
    logger.debug("Parameter=" + param);
    boolean paramNull = param == null ? true : false;
    
    // Attribut Operativer Nutzen holen
    String attr = (String) request.getAttribute(Constants.OPERATIVER_NUTZEN);
    logger.debug("Attribut=" + attr);
    boolean attrNull = attr == null ? true : false;
    HttpSession session = request.getSession();
    
    // Parameter und Attribut überprüfen
    if (!paramNull || !attrNull) {
      
      // Operativer Nutzen durch Request festgestellt
      if (!paramNull && param.equals("true") ||
          !attrNull && attr.equals("true")) {
        logger.debug("Operativer Nutzen (Request)");
        session.setAttribute(Constants.OPERATIVER_NUTZEN, "true");
        return true;
      }
      
      // Operativer Nutzen durch Session festgestellt
      else if (!paramNull && param.equals("false") || 
          !attrNull && attr.equals("false")) {
        logger.debug("Nutzenattraktivität (Session)");
        session.setAttribute(Constants.OPERATIVER_NUTZEN, "false");
        return false;
      }
    }
    
    // Session überprüfen
    else {
      String opNu = (String) session.getAttribute(Constants.OPERATIVER_NUTZEN);
      if (opNu.equals("true")) {
        logger.debug("Operativer Nutzen (Session)");
        return true;
      }
      else {
        logger.debug("Nutzenattraktivität (Session)");
        return false;
      }
    }
    return false;
  }

}
