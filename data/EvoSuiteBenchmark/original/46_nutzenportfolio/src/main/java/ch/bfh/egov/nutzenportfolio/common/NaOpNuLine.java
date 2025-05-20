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
package ch.bfh.egov.nutzenportfolio.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NaOpNuLine {
  private Integer nutzenkriteriumId;
  private Integer abstufung;
  private Integer gewichtung;
  private Log logger = LogFactory.getLog(this.getClass());
  
  public NaOpNuLine() {}

  public Integer getNutzenkriteriumId() {
    return nutzenkriteriumId;
  }

  public void setNutzenkriteriumId(Integer nutzenkriteriumId) {
    this.nutzenkriteriumId = nutzenkriteriumId;
  }

  public Integer getAbstufung() {
    logger.debug(abstufung);
    return abstufung;
  }

  public void setAbstufung(Integer abstufung) {
    logger.debug(abstufung);
    this.abstufung = abstufung;
  }

  public Integer getGewichtung() {
    logger.debug(gewichtung);
    return gewichtung;
  }

  public void setGewichtung(Integer gewichtung) {
    logger.debug(gewichtung);
    this.gewichtung = gewichtung;
  }

}
