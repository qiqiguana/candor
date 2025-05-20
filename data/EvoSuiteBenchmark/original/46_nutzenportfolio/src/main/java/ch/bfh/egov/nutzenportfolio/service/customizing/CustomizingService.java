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
package ch.bfh.egov.nutzenportfolio.service.customizing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.DynaActionForm;

import ch.bfh.egov.nutzenportfolio.service.auswahlfeld.AuswahlfeldService;
import ch.bfh.egov.nutzenportfolio.service.common.CommonService;
import ch.bfh.egov.nutzenportfolio.service.detailziele.DetailzieleService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.NaOpNuService;
import ch.bfh.egov.nutzenportfolio.service.fragebogen.ProjektattraktivitaetService;
import ch.bfh.egov.nutzenportfolio.service.kategorie.KategorieService;
import ch.bfh.egov.nutzenportfolio.service.nutzenkriterium.NutzenkriteriumService;
import ch.bfh.egov.nutzenportfolio.service.projektgruppe.ProjektgruppeService;
import ch.bfh.egov.nutzenportfolio.service.strategischeziele.StrategischeZieleService;
import ch.bfh.egov.nutzenportfolio.tos.Customizing;

/**
 * Service-Interface für Customizings.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public interface CustomizingService {
  
  // Daten Zugriff
  public Customizing getById(Customizing c);
  public Customizing getByName(Customizing c);
  public Customizing getByPaUID(Long paUID);
  public Customizing getByNaOpNuUID(Long naOpNuUID);
  public List getAll(Integer mandantId);
  public Integer insert(Customizing c);
  public void update(Customizing c);
  
  // Business Logik
  public void init(
      CommonService cService,
      StrategischeZieleService szService,
      DetailzieleService dzService,
      AuswahlfeldService afService,
      KategorieService kService,
      NutzenkriteriumService nService,
      NaOpNuService naOpNuService,
      ProjektattraktivitaetService paService,
      ProjektgruppeService pgService);
  public void cascadeDelete(HttpServletRequest request, Customizing c);
  public void copy(HttpServletRequest request, DynaActionForm form);
  public void changeStatus(HttpServletRequest request, DynaActionForm form);
  public boolean complete(HttpServletRequest request);
  public void setStatus(HttpServletRequest request);
  public void unsetStatus(HttpServletRequest request);
}
