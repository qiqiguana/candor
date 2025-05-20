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
package ch.bfh.egov.nutzenportfolio.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import ch.bfh.egov.nutzenportfolio.common.AutoGrowingList;
import ch.bfh.egov.nutzenportfolio.common.NutzenkriteriumLine;

/**
 * Formular für die Fragen von Nutzenkriterien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class FragenForm extends ActionForm {
  
  static final long serialVersionUID = 1554933891233372257L;
  private AutoGrowingList nutzenkriteriumLine;
  private Integer[] nutzenkriteriumIds;
  private Integer[] kategorieIds;
  private String[] fragen;
  private String[] gewichtung;
  private String[] kategorieGewichtung;
  private Log logger = LogFactory.getLog(this.getClass());
  
  public FragenForm() {}

  /**
   * Holt ein einzelnes Nutzenkriterium anhand des angegebenen Indices aus
   * dem Formular.
   * 
   * @param index                der Index ist sozusagen die Zeilennummer des Formulars
   * @return                     ein einzelnes Nutzenkriterium
   */
  public NutzenkriteriumLine getNutzenkriterium(int index) {
    if (nutzenkriteriumLine == null) {
      nutzenkriteriumLine = new AutoGrowingList(NutzenkriteriumLine.class);
    }
    logger.debug(index);
    return (NutzenkriteriumLine) nutzenkriteriumLine.get(index);
  }

  public String[] getFragen() {
    return fragen;
  }

  public void setFragen(String[] fragen) {
    this.fragen = fragen;
  }

  public String[] getGewichtung() {
    return gewichtung;
  }

  public void setGewichtung(String[] gewichtung) {
    this.gewichtung = gewichtung;
  }

  public String[] getKategorieGewichtung() {
    return kategorieGewichtung;
  }

  public void setKategorieGewichtung(String[] kategorieGewichtung) {
    this.kategorieGewichtung = kategorieGewichtung;
  }

  public Integer[] getNutzenkriteriumIds() {
    return nutzenkriteriumIds;
  }

  public void setNutzenkriteriumIds(Integer[] nutzenkriteriumIds) {
    this.nutzenkriteriumIds = nutzenkriteriumIds;
  }

  public Integer[] getKategorieIds() {
    return kategorieIds;
  }

  public void setKategorieIds(Integer[] kategorieIds) {
    this.kategorieIds = kategorieIds;
  }

  public AutoGrowingList getNutzenkriteriumLine() {
    return nutzenkriteriumLine;
  }

  public void setNutzenkriteriumLine(AutoGrowingList nutzenkriteriumLine) {
    this.nutzenkriteriumLine = nutzenkriteriumLine;
  }
  
}
