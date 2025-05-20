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

import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;

/**
 * Data Access Object-Interface f�r die Frageb�gen
 * Nutzenattraktivit�t und Operativer Nutzen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface NaOpNuDao {
  public NaOpNu getByEmail(NaOpNu naOpNu);
  public NaOpNu getByUID(Long naOpNuUID);
  public NaOpNu getNaOpNuResultat(NaOpNu naOpNu);
  public NaOpNu getPaResultat(NaOpNu naOpNu);
  public Integer insertNaOpNuResultat(NaOpNu naOpNu);
  public void deleteAssignments(Integer customizingId);
}

