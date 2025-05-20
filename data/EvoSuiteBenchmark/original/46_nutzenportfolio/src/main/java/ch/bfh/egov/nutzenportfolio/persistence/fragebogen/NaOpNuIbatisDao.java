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
package ch.bfh.egov.nutzenportfolio.persistence.fragebogen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;

/**
 * Implementierende Data Access Object-Klasse f�r die Frageb�gen
 * Nutzenattraktivit�t und Operativer Nutzen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NaOpNuIbatisDao
    extends SqlMapClientTemplate
    implements NaOpNuDao {
  
  Log logger = LogFactory.getLog(this.getClass());
  
  /**
   * Holt eine Nutzenattraktivit�t bzw. einen Operativer Nutzen
   * anhand der E-Mail-Adresse.
   * 
   * @param naOpNu      Nutzenattraktivit�t / Operativer Nutzen-Objekt
   * @return            Nutzenattraktivit�t / Operativer Nutzen-Objekt
   */
  public NaOpNu getByEmail(NaOpNu naOpNu) {
    return
        (NaOpNu)
        queryForObject("NutzenattraktivitaetOperativerNutzen.getByEmail",
            naOpNu);
  }
  
  /**
   * Holt eine Nutzenattraktivit�t bzw. einen Operativer Nutzen
   * anhand der UID.
   * 
   * @param naOpNuUID   die UID der Nutzenattraktivit�t bzw.
   *                    des Operativen Nutzens
   * @return            Nutzenattraktivit�t / Operativer Nutzen-Objekt
   */
  public NaOpNu getByUID(Long naOpNuUID) {
    return
        (NaOpNu)
        queryForObject("NutzenattraktivitaetOperativerNutzen.getByUID",
            naOpNuUID);
  }
  
  /**
   * Holt das Resultat eines Fragebogens der
   * Nutzenattraktivit�t oder des Operativer Nutzens
   * 
   * @param naOpNu     Nutzenattraktivit�t / Operativer Nutzen-Objekt
   *                   mit den ben�tigten Daten
   * @return            Nutzenattraktivit�t / Operativer Nutzen-Objekt
   */
  public NaOpNu getNaOpNuResultat(NaOpNu naOpNu) {
    return
        (NaOpNu)
        queryForObject("NutzenattraktivitaetOperativerNutzen.getNaOpNuResultat",
            naOpNu);
  }
  
  /**
   * Holt das Resultat eines Fragebogens der Projektattraktivit�t.
   * 
   * @param naOpNu     Nutzenattraktivit�t / Operativer Nutzen-Objekt
   *                   mit den ben�tigten Daten
   * @return            Nutzenattraktivit�t / Operativer Nutzen-Objekt
   */
  public NaOpNu getPaResultat(NaOpNu naOpNu) {
    return
        (NaOpNu)
        queryForObject("NutzenattraktivitaetOperativerNutzen.getPaResultat",
            naOpNu);
  }
  
  /**
   * Speichert ein neues Resultat eines Fragebogens der
   * Nutzenattraktivit�t oder des Operativer Nutzens.
   * 
   * @param naOpNu      Nutzenattraktivit�t / Operativer Nutzen-Objekt
   *                    mit den ben�tigten Daten
   * @return            Id der neuen Nutzenattraktivit�t bzw. des
   *                    neuen Operativen Nutzens
   */
  public Integer insertNaOpNuResultat(NaOpNu naOpNu) {
    return
        (Integer)
        insert("NutzenattraktivitaetOperativerNutzen.insertNaOpNuResultat",
            naOpNu);
  }
  
  /**
   * L�scht alle bestehenden Resultate eines Customizings von den Frageb�gen
   * Nutzenattraktivit�t und Operativer Nutzen.
   * 
   * @param customizingId     die Id des Customizings
   */
  public void deleteAssignments(Integer customizingId) {
    delete("NutzenattraktivitaetOperativerNutzen.deleteAssignments", customizingId);
  }
}
