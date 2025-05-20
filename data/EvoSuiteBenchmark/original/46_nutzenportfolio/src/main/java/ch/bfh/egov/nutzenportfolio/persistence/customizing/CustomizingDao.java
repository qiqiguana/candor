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

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Customizing;

/**
 * Data Access Object-Interface für Customizings.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface CustomizingDao {
  public Customizing getById(Customizing c);
  public Customizing getByName(Customizing c);
  public Customizing getByPaUID(Long paUID);
  public Customizing getByNaOpNuUID(Long naOpNuUID);
  public List<Customizing> getAll(Integer mandantId);
  public Integer insert(Customizing c);
  public void update(Customizing c);
  public void delete(Customizing c);
}

