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
package ch.bfh.egov.nutzenportfolio.persistence.customizing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;

import java.util.List;

/**
 * Implementierende Data Access Object-Klasse für Customizings.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class CustomizingIbatisDao extends SqlMapClientTemplate implements CustomizingDao {
  Log logger = LogFactory.getLog(this.getClass());

  /**
   * Holt ein Customizing anhand seiner Id.
   * 
   * @param c           Customizing-Objekt mit benötigten Daten
   * @return            das Customizing
   */
  public Customizing getById(Customizing c) {
    return ((Customizing) queryForObject("Customizing.getById", c));
  }
  
  /**
   * Holt ein Customizing anhand seines Namens.
   * 
   * @param c           Customizing-Objekt mit benötigten Daten
   * @return            das Customizing
   */
  public Customizing getByName(Customizing c) {
    return ((Customizing) queryForObject("Customizing.getByName", c));
  }
  
  /**
   * Holt ein Customizing anhand der Projektattraktivität UID.
   * 
   * @param paUID       die UID der Projektattraktivität
   * @return            das Customizing
   */
  public Customizing getByPaUID(Long paUID) {
    return ((Customizing) queryForObject("Customizing.getByPaUID", paUID));
  }
  
  /**
   * Holt ein Customizing anhand der Nutzenattraktivität UID bzw.
   * der Operativer Nutzen UID.
   * 
   * @param naOpNuUID   die UID der Nutzenattraktivität bzw. des Operativen Nutzens
   * @return            das Customizing
   */
  public Customizing getByNaOpNuUID(Long naOpNuUID) {
    return ((Customizing) queryForObject("Customizing.getByNaOpNuUID", naOpNuUID));
  }
  
  /**
   * Holt alle Customizings eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Customizings
   */
  public List<Customizing> getAll(Integer mandantId) {
    return queryForList("Customizing.getAll", mandantId);
  }
  
  /**
   * Speichert ein neues Customizing.
   * 
   * @param c     Customizing-Objekt mit benötigten Daten
   * @return      die Id des Customizings
   */
  public Integer insert(Customizing c) {
    return (Integer)insert("Customizing.insert", c);
  }

  /**
   * Ändert ein bestehendes Customizing.
   * 
   * @param c    Customizing-Objekt mit benötigten Daten
   */
  public void update(Customizing c) {
    update("Customizing.update", c);
  }

  /**
   * Löscht ein bestehendes Customizing.
   * 
   * @param c    Customizing-Objekt mit benötigten Daten
   */
  public void delete(Customizing c) {
    delete("Customizing.delete", c);
  }
}
