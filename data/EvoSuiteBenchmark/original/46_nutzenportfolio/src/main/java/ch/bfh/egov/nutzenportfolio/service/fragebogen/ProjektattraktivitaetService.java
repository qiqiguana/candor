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
package ch.bfh.egov.nutzenportfolio.service.fragebogen;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.form.ProjektattraktivitaetForm;

/**
 * Service-Interface f�r den Fragebogen Projektattraktivit�t.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface ProjektattraktivitaetService {
  
  // Datenzugriff
  public void deleteAssignments(Integer customizingId);
  
  // Business Logik
  public boolean prepare(HttpServletRequest request, ProjektattraktivitaetForm form);
  public void save(HttpServletRequest request, ProjektattraktivitaetForm form);
  public boolean isActive(HttpServletRequest request, DynaActionForm form);
  public boolean login(HttpServletRequest request, DynaActionForm loginForm);
}
