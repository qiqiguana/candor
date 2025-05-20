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
 * Implementierende Data Access Object-Klasse für gemeinsam genutzte Methoden.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class CommonIbatisDao extends SqlMapClientTemplate implements CommonDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt die Id der Projektattraktivität anhand der Id des Customizings.
   * 
   * @param customizingId   die Id des Customizings
   * @return                die Id der Projektattraktivität
   */
  public Integer getProjektattraktivitaetIdByCustomizingId(Integer customizingId) {
    return 
        (Integer)
        queryForObject("Common.getProjektattraktivitaetIdByCustomizingId", customizingId);
  }
  
  /**
   * Holt die Id der Nutzenattraktivität anhand der Id des Customizings.
   * 
   * @param customizingId   die Id des Customizings
   * @return                die Id der Nutzenattraktivität
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
   * Speichert eine neue Projektattraktivität.
   * 
   * @param p               Projektattraktivität-Objekt mit benötigten Daten
   * @return                die Id der Projektattraktivität
   */
  public Integer insertProjektattraktivitaet(Projektattraktivitaet p) {
    return (Integer) insert("Common.insertProjektattraktivitaet", p);
  }

  /**
   * Speichert eine neue Nutzenattraktivität bzw. einen neuen Operativen Nutzen.
   * 
   * @param n               Nutzenattraktivität / Operativen Nutzen-Objekt
   *                        mit benötigten Daten
   * @return                die Id der Nutzenattraktivität bzw. des Operativen Nutzens
   */
  public Integer insertNutzenattraktivitaetOperativerNutzen(NaOpNu n) {
    return (Integer) insert("Common.insertNutzenattraktivitaetOperativerNutzen", n);
  }
  
  /**
   * Löscht eine Projektattraktivität.
   * 
   * @param projektattraktivitaetId   die Id der Projektattraktivität
   */
  public void deleteProjektattraktivitaet(Integer projektattraktivitaetId) {
    delete("Common.deleteProjektattraktivitaet", projektattraktivitaetId);
  }
  
  /**
   * Löscht eine Nutzenattraktivität bzw. einen Operativen Nutzen.
   * 
   * @param naOpNuId        die Id der Nutzenattraktivität bzw. des Operativen Nutzens
   */
  public void deleteNutzenattraktivitaetOperativerNutzen(Integer naOpNuId) {
    delete("Common.deleteNutzenattraktivitaetOperativerNutzen", naOpNuId);
  }
}
