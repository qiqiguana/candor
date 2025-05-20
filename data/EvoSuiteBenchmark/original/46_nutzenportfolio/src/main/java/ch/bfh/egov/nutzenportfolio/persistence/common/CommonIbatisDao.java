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
package ch.bfh.egov.nutzenportfolio.persistence.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Projektattraktivitaet;

/**
 * Implementierende Data Access Object-Klasse f�r gemeinsam genutzte Methoden.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class CommonIbatisDao extends SqlMapClientTemplate implements CommonDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt die Id der Projektattraktivit�t anhand der Id des Customizings.
   * 
   * @param customizingId   die Id des Customizings
   * @return                die Id der Projektattraktivit�t
   */
  public Integer getProjektattraktivitaetIdByCustomizingId(Integer customizingId) {
    return 
        (Integer)
        queryForObject("Common.getProjektattraktivitaetIdByCustomizingId", customizingId);
  }
  
  /**
   * Holt die Id der Nutzenattraktivit�t anhand der Id des Customizings.
   * 
   * @param customizingId   die Id des Customizings
   * @return                die Id der Nutzenattraktivit�t
   */
  public Integer getNutzenattraktivitaetIdByCustomizingId(Integer customizingId) {
    return
        (Integer)
        queryForObject("Common.getNutzenattraktivitaetIdByCustomizingId", customizingId);
  }
  
  /**
   * Holt die Id des Operativen Nutzens anhand der Id des Customizings.
   * 
   * @param customizingId   die Id des Customizings
   * @return                die Id des Operativen Nutzens
   */
  public Integer getOperativerNutzenIdByCustomizingId(Integer customizingId) {
    return
        (Integer)
        queryForObject("Common.getOperativerNutzenIdByCustomizingId", customizingId);
  }

  /**
   * Speichert eine neue Projektattraktivit�t.
   * 
   * @param p               Projektattraktivit�t-Objekt mit ben�tigten Daten
   * @return                die Id der Projektattraktivit�t
   */
  public Integer insertProjektattraktivitaet(Projektattraktivitaet p) {
    return (Integer) insert("Common.insertProjektattraktivitaet", p);
  }

  /**
   * Speichert eine neue Nutzenattraktivit�t bzw. einen neuen Operativen Nutzen.
   * 
   * @param n               Nutzenattraktivit�t / Operativen Nutzen-Objekt
   *                        mit ben�tigten Daten
   * @return                die Id der Nutzenattraktivit�t bzw. des Operativen Nutzens
   */
  public Integer insertNutzenattraktivitaetOperativerNutzen(NaOpNu n) {
    return (Integer) insert("Common.insertNutzenattraktivitaetOperativerNutzen", n);
  }
  
  /**
   * L�scht eine Projektattraktivit�t.
   * 
   * @param projektattraktivitaetId   die Id der Projektattraktivit�t
   */
  public void deleteProjektattraktivitaet(Integer projektattraktivitaetId) {
    delete("Common.deleteProjektattraktivitaet", projektattraktivitaetId);
  }
  
  /**
   * L�scht eine Nutzenattraktivit�t bzw. einen Operativen Nutzen.
   * 
   * @param naOpNuId        die Id der Nutzenattraktivit�t bzw. des Operativen Nutzens
   */
  public void deleteNutzenattraktivitaetOperativerNutzen(Integer naOpNuId) {
    delete("Common.deleteNutzenattraktivitaetOperativerNutzen", naOpNuId);
  }
}
