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
package ch.bfh.egov.nutzenportfolio.service.benutzer;

import java.util.List;

import ch.bfh.egov.nutzenportfolio.tos.Benutzer;

/**
 * Service-Interface für Benutzer.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface BenutzerService {
  
  // Daten Zugriff
  public Benutzer getBenutzer(Integer id);
  public Benutzer getBenutzerByBenutzername(String benutzername);
  public List<Benutzer> getAllBenutzer(Integer mandantId);
  public void insertBenutzer(Benutzer benutzer);
  public void updateBenutzer(Benutzer benutzer);
  public void deleteBenutzer(Integer id);
  
  // Business Logik
  public Benutzer loginSuccessful(Benutzer benutzer);
}
