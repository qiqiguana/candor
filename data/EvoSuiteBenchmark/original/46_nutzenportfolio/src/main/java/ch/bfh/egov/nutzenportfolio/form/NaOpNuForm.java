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
import ch.bfh.egov.nutzenportfolio.common.NaOpNuLine;

/**
 * Formular für die Fragebögen der Nutzenattraktivität
 * und des Operativen Nutzens.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class NaOpNuForm extends ActionForm {

  private static final long serialVersionUID = 1455354150947183286L;
  private Integer projektId;
  private Integer naOpNuId;
  private Integer mandantId;
  private Integer stufen;
  private Integer gewichtungsstufen;
  private AutoGrowingList naOpNuLines;
  private Integer[] abstufung;
  private Integer[] gewichtung;
  private Integer[] nutzenkriteriumId;
  private Boolean opNu;
  private Log logger = LogFactory.getLog(this.getClass());
  
  public NaOpNuForm() {}

  /**
   * Holt eine einzelne Zeile des Fragebogens anhand des angegebenen Indices aus
   * dem Formular.
   * 
   * @param index                der Index ist sozusagen die Zeilennummer des Formulars
   * @return                     eine einzelne Zeile des Fragebogens
   */
  public NaOpNuLine getNk(int index) {
    if (naOpNuLines == null) {
      naOpNuLines = new AutoGrowingList(NaOpNuLine.class);
    }
    logger.debug(index);
    return (NaOpNuLine) naOpNuLines.get(index);
  }

  public Integer[] getAbstufung() {
    return abstufung;
  }

  public void setAbstufung(Integer[] abstufung) {
    this.abstufung = abstufung;
  }

  public Integer[] getGewichtung() {
    return gewichtung;
  }

  public void setGewichtung(Integer[] gewichtung) {
    this.gewichtung = gewichtung;
  }

  public Integer getMandantId() {
    return mandantId;
  }

  public void setMandantId(Integer mandantId) {
    this.mandantId = mandantId;
  }

  public Integer getGewichtungsstufen() {
    return gewichtungsstufen;
  }

  public void setGewichtungsstufen(Integer gewichtungsstufen) {
    this.gewichtungsstufen = gewichtungsstufen;
  }

  public Integer getStufen() {
    return stufen;
  }

  public void setStufen(Integer stufen) {
    this.stufen = stufen;
  }

  public Integer getNaOpNuId() {
    return naOpNuId;
  }

  public void setNaOpNuId(Integer naOpNuId) {
    this.naOpNuId = naOpNuId;
  }

  public AutoGrowingList getNaOpNuLines() {
    return naOpNuLines;
  }

  public void setNaOpNuLines(AutoGrowingList naOpNuLines) {
    this.naOpNuLines = naOpNuLines;
  }

  public Integer getProjektId() {
    return projektId;
  }

  public void setProjektId(Integer projektId) {
    this.projektId = projektId;
  }
  

  public Integer[] getNutzenkriteriumId() {
    return nutzenkriteriumId;
  }

  public void setNutzenkriteriumId(Integer[] nutzenkriteriumId) {
    this.nutzenkriteriumId = nutzenkriteriumId;
  }

  public Boolean getOpNu() {
    return opNu;
  }

  public void setOpNu(Boolean opNu) {
    this.opNu = opNu;
  }

}
