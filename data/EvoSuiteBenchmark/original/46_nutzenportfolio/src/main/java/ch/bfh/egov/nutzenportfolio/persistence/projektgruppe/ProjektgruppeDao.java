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

import ch.bfh.egov.nutzenportfolio.tos.Projektgruppe;

/**
 * Data Access Object-Interface für Projektgruppen.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface ProjektgruppeDao {
  public List getAll(Integer mandantId);
  public Projektgruppe getById(Projektgruppe p);
  public Projektgruppe getByName(Projektgruppe p);
  public Integer insert(Projektgruppe p);
  public void update(Projektgruppe p);
  public void delete(Projektgruppe p);
  public void unsetCustomizingId(Integer customizingId);
}

