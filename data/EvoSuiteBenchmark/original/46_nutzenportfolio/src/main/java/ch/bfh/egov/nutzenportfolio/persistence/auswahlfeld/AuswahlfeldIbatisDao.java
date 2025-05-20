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
package ch.bfh.egov.nutzenportfolio.persistence.auswahlfeld;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.Auswahlfeld;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;

import java.util.List;

/**
 * Implementierende Data Access Object-Klasse für Auswahlfelder.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class AuswahlfeldIbatisDao extends SqlMapClientTemplate implements AuswahlfeldDao {
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt ein Auswahlfeld anhand seiner Id.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            das Auswahlfeld
   */
  public Auswahlfeld getById(Auswahlfeld a) {
    return (Auswahlfeld) queryForObject("Auswahlfeld.getById", a);
  }
  
  /**
   * Holt ein Auswahlfeld anhand seines Namens.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            das Auswahlfeld
   */
  public Auswahlfeld getByName(Auswahlfeld a) {
    return (Auswahlfeld) queryForObject("Auswahlfeld.getByName", a);
  }
  
  /**
   * Holt alle Auswahlfelder einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getByNaOpNu(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getByNaOpNu", a);
  }
  
  /**
   * Holt alle Auswahlfelder eines bestimmten Typs.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getAuswahlFelder(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getAuswahlfelder", a);
  }
  
  /**
   * Holt alle Auswahlfelder, die dem angegeben Projekt zugewiesen sind.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getByProjektId(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getByProjektId", a);
  }
  
  /**
   * Holt alle Realisierbarkeit-Auswahlfelder einer Projektattraktivität.
   * 
   * @param paId        die Id der Projektattraktivität
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getRealisierbarkeitStatus(Integer paId) {
    return queryForList("Auswahlfeld.getRealisierbarkeitStatus", paId);
  }
  
  /**
   * Holt alle Abstufung-Auswahlfelder einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens.
   * 
   * @param naOpNuId    die Id der Nutzenattraktivität bzw. des Operativen Nutzens
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getAbstufungStatus(Integer naOpNuId) {
    return queryForList("Auswahlfeld.getAbstufungStatus", naOpNuId);
  }
  
  /**
   * Holt alle Gewichtung-Auswahlfelder einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens.
   * 
   * @param naOpNuId    die Id der Nutzenattraktivität bzw. des Operativen Nutzens
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getGewichtungStatus(Integer naOpNuId) {
    return queryForList("Auswahlfeld.getGewichtungStatus", naOpNuId);
  }
  
  /**
   * Holt alle Abstufung-Auswahlfelder einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens.
   * 
   * @param naOpNuId    die Id der Nutzenattraktivität bzw. des Operativen Nutzens
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getAnzahlAbstufungen(Integer naOpNuId) {
    return queryForList("Auswahlfeld.getAnzahlAbstufungen", naOpNuId);
  }
  
  /**
   * Holt alle Gewichtung-Auswahlfelder einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens.
   * 
   * @param naOpNuId    die Id der Nutzenattraktivität bzw. des Operativen Nutzens
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getAnzahlGewichtungen(Integer naOpNuId) {
    return queryForList("Auswahlfeld.getAnzahlGewichtungen", naOpNuId);
  }
  
  /**
   * Holt alle Auswahlfelder, die einer Projektattraktivität zugewiesen sind.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getSelectedByPaId(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getSelectedByPaId", a);
  }
  
  /**
   * Holt alle Auswahlfelder, die einer Nutzenattraktivität
   * bzw. eines Operativen Nutzens zugewiesen sind.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller Auswahlfelder die den Kriterien entsprechen
   */
  public List<Auswahlfeld> getSelectedByNaOpNuId(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getSelectedByNaOpNuId", a);
  }
  
  /**
   * Speichert ein neues Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void insert(Auswahlfeld a) {
    insert("Auswahlfeld.insert", a);
  }
  
  /**
   * Ändert ein bestehendes Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void update(Auswahlfeld a) {
    update("Auswahlfeld.update", a);
  }
  
  /**
   * Löscht ein bestehendes Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void delete(Auswahlfeld a) {
    delete("Auswahlfeld.delete", a);
  }
  
  /**
   * Holt alle Customizings, die mit diesem Auswahlfeld verknüpft sind.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller verknüpften Customizings
   */
  public List<Customizing> getLinkedPaCustomizings(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getLinkedPaCustomizings", a);
  }
  
  /**
   * Holt alle Customizings, die mit diesem Auswahlfeld verknüpft sind.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   * @return            Liste aller verknüpften Customizings
   */
  public List<Customizing> getLinkedNaOpNuCustomizings(Auswahlfeld a) {
    return queryForList("Auswahlfeld.getLinkedNaOpNuCustomizings", a);
  }
  
  /**
   * Speichert eine neue Zuweisung zu diesem Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void insertAssignment(Auswahlfeld a) {
    insert("Auswahlfeld.insertAssignment", a);
  }
  
  
  /**
   * Speichert eine neue Zuweisung zu diesem Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void insertNaOpNuAssignment(Auswahlfeld a) {
    insert("Auswahlfeld.insertNaOpNuAssignment", a);
  }
  
  /**
   * Löscht eine bestehende Zuweisung zu diesem Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void deleteAssignments(Auswahlfeld a) {
    delete("Auswahlfeld.deleteAssignments", a);
  }
  
  /**
   * Löscht eine bestehende Zuweisung zu diesem Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void deleteNaOpNuAssignments(Auswahlfeld a) {
    delete("Auswahlfeld.deleteNaOpNuAssignments", a);
  }
  
  /**
   * Löscht eine bestehende Zuweisung zu diesem Auswahlfeld.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void deleteNaOpNuAssignmentsByNutzenkriterium(Auswahlfeld a) {
    delete("Auswahlfeld.deleteNaOpNuAssignmentsByNutzenkriterium", a);
  }
  
  /**
   * Löscht alle Gewichtungen einer Nutzenattraktivität bezw. eines
   * Operativen Nutzens.
   * 
   * @param a           Auswahlfeld-Objekt mit benötigten Daten
   */
  public void deleteGewichtungen(Auswahlfeld a) {
    delete("Auswahlfeld.deleteGewichtungen", a);
  }
}
