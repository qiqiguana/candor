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
package ch.bfh.egov.nutzenportfolio.persistence.projektgruppe;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Projektgruppe;

/**
 * Implementierende Data Access Object-Klasse für Projektgruppen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class ProjektgruppeIbatisDao extends SqlMapClientTemplate implements ProjektgruppeDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt alle Projektgruppen eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Projektgruppen
   */
  public List getAll(Integer mandantId) {
    return queryForList("Projektgruppe.getAll", mandantId);
  }
  
  /**
   * Holt eine Projektgruppe anhand seiner Id.
   * 
   * @param p           Projektgruppe-Objekt mit benötigten Daten
   * @return            die Projektgruppe
   */
  public Projektgruppe getById(Projektgruppe p) {
    return (Projektgruppe) queryForObject("Projektgruppe.getById", p);
  }
  
  /**
   * Holt eine Projektgruppe anhand seines Namens.
   * 
   * @param p             Projektgruppe-Objekt mit benötigten Daten
   * @return              die Projektgruppe
   */
  public Projektgruppe getByName(Projektgruppe p) {
    return (Projektgruppe) queryForObject("Projektgruppe.getByName", p);
  }
  
  /**
   * Speichert eine neue Projektgruppe.
   * 
   * @param p           Projektgruppe-Objekt mit benötigten Daten
   */
  public Integer insert(Projektgruppe p) {
    return (Integer) insert("Projektgruppe.insert", p);
  }
  
  /**
   * Ändert eine bestehende Projektgruppe.
   * 
   * @param p           Projektgruppe-Objekt mit benötigten Daten
   */
  public void update(Projektgruppe p) {
    update("Projektgruppe.update", p);
  }
  
  /**
   * Löscht eine bestehende Projektgruppe.
   * 
   * @param p     die zu löschende Projektgruppe
   */
  public void delete(Projektgruppe p) {
    update("Projektgruppe.delete", p);
  }
  
  
  /**
   * Löscht alle Verknüpfungen von Projektgruppen zu
   * einem Customizing.
   * 
   * @param customizingId     die Id des Customizings
   */
  public void unsetCustomizingId(Integer customizingId) {
    update("Projektgruppe.unsetCustomizingId", customizingId);
  }
}
