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
package ch.bfh.egov.nutzenportfolio.tos;

import java.io.Serializable;

/**
 * Transfer Object für Resultate.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Resultat implements Serializable {
  private static final long serialVersionUID = 7065575288307145111L;
  private Double naResultat;
  private Double opNuResultat;
  private Double paResultat;
  private String name;
  private String label;

  public Resultat() {}
  
  public Double getNaResultat() {
    return naResultat;
  }
  public void setNaResultat(Double naResultat) {
    this.naResultat = naResultat;
  }
  public Double getOpNuResultat() {
    return opNuResultat;
  }
  public void setOpNuResultat(Double opNuResultat) {
    this.opNuResultat = opNuResultat;
  }
  public Double getPaResultat() {
    return paResultat;
  }
  public void setPaResultat(Double paResultat) {
    this.paResultat = paResultat;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
