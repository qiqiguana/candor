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

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;
import ch.bfh.egov.nutzenportfolio.tos.NaOpNu;
import ch.bfh.egov.nutzenportfolio.tos.Kategorie;
import ch.bfh.egov.nutzenportfolio.tos.Nutzenkriterium;

/**
 * Data Access Object-Interface für Kategorien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface KategorieDao {
  public List<Kategorie> getAll(Integer mandantId);
  public Kategorie getById(Kategorie kategorie);
  public Kategorie getByName(Kategorie kategorie);
  public List<Kategorie> getByProjektId(NaOpNu naOpNu);
  public List<Kategorie> getAssignments(Kategorie kategorie);
  public List<Customizing> getLinkedCustomizings(Kategorie kategorie);
  public List<Nutzenkriterium> getLinkedNutzenkriterien(Kategorie kategorie);
  public void insert(Kategorie kategorie);
  public void update(Kategorie kategorie);
  public void delete(Kategorie kategorie);
  public void insertAssignment(Kategorie kategorie);
  public void updateAssignment(Kategorie kategorie);
  public int deleteAssignments(Integer naOpNuId);
}

