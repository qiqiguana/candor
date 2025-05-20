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
package ch.bfh.egov.nutzenportfolio.service.projekt;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.tos.Projekt;

/**
 * Service-Interface für Projekte.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface ProjektService {
  
  // Daten Zugriff
  public Projekt getById(Projekt p);
  public Projekt getByUID(Long projektUID);
  
  // Business Logik
  public void populate(HttpServletRequest request);
  public void prepare(HttpServletRequest request, DynaActionForm form);
  public Projekt get(HttpServletRequest request, DynaActionForm form);
  public Projekt get(HttpServletRequest request);
  public void save(HttpServletRequest request, Projekt p);
  public boolean delete(HttpServletRequest request);
  public boolean exists(Projekt p);
  public Projekt createProjekt(HttpServletRequest request);
  public boolean auswertung(HttpServletRequest request, DynaActionForm form, ActionMessages errors);
  public void changeStatus(HttpServletRequest request, DynaActionForm f);
}
