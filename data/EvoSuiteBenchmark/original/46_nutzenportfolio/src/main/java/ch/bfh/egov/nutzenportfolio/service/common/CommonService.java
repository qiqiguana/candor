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
package ch.bfh.egov.nutzenportfolio.service.common;

import javax.servlet.http.HttpServletRequest;

import ch.bfh.egov.nutzenportfolio.form.NaOpNuForm;

/**
 * Service-Interface für gemeinsam genutzte Methoden.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface CommonService {
  
  // Daten Zugriff
  public Integer getProjektattraktivitaetIdByCustomizingId(Integer customizingId);
  public Integer getNutzenattraktivitaetIdByCustomizingId(Integer customizingId);
  public Integer getOperativerNutzenIdByCustomizingId(Integer customizingId);
  public void deleteProjektattraktivitaet(Integer projektattraktivitaetId);
  public void deleteNutzenattraktivitaetOperativerNutzen(Integer naOpNuId);
  
  // Business Logik
  public void insertCustomizingParts(Integer customizingId);
  public Integer getNaOpNuId(NaOpNuForm form, Integer customizingId);
  public Integer getNaOpNuId(HttpServletRequest request, Integer customizingId);
  public boolean isOperativerNutzen(HttpServletRequest request);
}
