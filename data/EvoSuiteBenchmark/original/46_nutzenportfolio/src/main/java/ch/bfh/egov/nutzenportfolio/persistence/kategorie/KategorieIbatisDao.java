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
package ch.bfh.egov.nutzenportfolio.persistence.kategorie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

import java.util.List;

/**
 * Implementierende Data Access Object-Klasse für Kategorien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class KategorieIbatisDao extends SqlMapClientTemplate implements KategorieDao {
  Log logger = LogFactory.getLog(this.getClass());

  /**
   * Holt alle Kategorien eines Mandanten.
   * 
   * @param mandantId   die Id des Mandanten
   * @return            Liste aller Kategorien die den Kriterien entsprechen
   */
  public List<Kategorie> getAll(Integer mandantId) {
    return queryForList("Kategorie.getAll", mandantId);
  }
  
  /**
   * Holt eine Kategorie anhand ihrer Id.
   * 
   * @param kategorie       Kategoriem-Objekt mit benötigten Daten
   * @return                die Kategorie
   */
  public Kategorie getById(Kategorie kategorie) {
    return (Kategorie) queryForObject("Kategorie.getById", kategorie);
  }
  
  /**
   * Holt eine Kategorie anhand ihres Namens.
   * 
   * @param kategorie       Kategorie-Objekt mit benötigten Daten
   * @return                die Kategorie
   */
  public Kategorie getByName(Kategorie kategorie) {
    return (Kategorie) queryForObject("Kategorie.getByName", kategorie);
  }
  
  /**
   * Holt alle Kategorien, die dem angegeben Projekt zugewiesen sind.
   * 
   * @param naOpNu  Nutzenattraktivität / Operativer Nutzen-Objekt
   *                mit benötigten Daten
   * @return        Liste aller Kategorien die den Kriterien entsprechen
   */
  public List<Kategorie> getByProjektId(NaOpNu naOpNu) {
    return queryForList("Kategorie.getByProjektId", naOpNu);
  }
  
  /**
   * Holt alle Kategorien, die einer angegebenen Nutzenattraktivität
   * bzw. eines Operativen Nutzens  zugewiesen sind.
   * 
   * @param kategorie   Kategorie-Objekt mit benötigten Daten
   * @return            Liste aller Kategorien die den Kriterien entsprechen
   */
  public List<Kategorie> getAssignments(Kategorie kategorie) {
    return queryForList("Kategorie.getAssignments", kategorie);
  }
  
  /**
   * Holt alle Customizings, die mit dieser Kategorie verknüpft sind.
   * 
   * @param kategorie   Kategorie-Objekt mit benötigten Daten
   * @return            Liste aller verknüpften Customizings
   */
  public List<Customizing> getLinkedCustomizings(Kategorie kategorie) {
    return queryForList("Kategorie.getLinkedCustomizings", kategorie);
  }
  
  /**
   * Holt alle Nutzenkriterien, die mit dieser Kategorie verknüpft sind.
   * 
   * @param kategorie   Kategorie-Objekt mit benötigten Daten
   * @return            Liste aller verknüpften Nutzenkriterien
   */
  public List<Nutzenkriterium> getLinkedNutzenkriterien(Kategorie kategorie) {
    return queryForList("Kategorie.getLinkedNutzenkriterien", kategorie);
  }
  
  /**
   * Speichert eine neue Kategorie.
   * 
   * @param kategorie        Kategorie-Objekt mit benötigten Daten
   */
  public void insert(Kategorie kategorie) {
    insert("Kategorie.insert", kategorie);
  }
  
  /**
   * Ändert eine bestehende Kategorie.
   * 
   * @param kategorie        Kategorie-Objekt mit benötigten Daten
   */
  public void update(Kategorie kategorie) {
    update("Kategorie.update", kategorie);
  }
  
  /**
   * Löscht eine bestehende Kategorie.
   * 
   * @param kategorie        Kategorie-Objekt mit benötigten Daten
   */
  public void delete(Kategorie kategorie) {
    delete("Kategorie.delete", kategorie);
  }
  
  /**
   * Speichert eine neue Zuweisung zu dieser Kategorie.
   * 
   * @param kategorie        Kategorie-Objekt mit benötigten Daten
   */
  public void insertAssignment(Kategorie kategorie) {
    insert("Kategorie.insertAssignment", kategorie);
  }
  
  /**
   * Ändert eine bestehende Zuweisung zu dieser Kategorie.
   * 
   * @param kategorie        Kategorie-Objekt mit benötigten Daten
   */
  public void updateAssignment(Kategorie kategorie) {
    update("Kategorie.updateAssignment", kategorie);
  }
  
  /**
   * Löscht alle bestehenden Zuweisungen von Kategorien 
   * zu der angegebenen Nutzenattraktivität bzw. des Operativen Nutzens.
   * 
   * @param naOpNuId        die Id der Nutzenattraktivität bzw. des
   *                        Operativen Nutzens
   */
  public int deleteAssignments(Integer naOpNuId) {
    return delete("Kategorie.deleteAssignments", naOpNuId);
  }
}
