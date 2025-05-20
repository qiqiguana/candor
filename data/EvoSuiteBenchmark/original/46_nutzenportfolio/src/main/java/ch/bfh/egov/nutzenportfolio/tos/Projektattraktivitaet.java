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
 * Transfer Object für Projektattraktivitäten.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Projektattraktivitaet implements Serializable {
  static final long serialVersionUID = 6774278594491886714L;
  private Integer projektattraktivitaetId;
  private Integer projektattraktivitaetResultatId;
  private Integer customizingId;
  private Integer projektId;
  private String email;
  private Double resultat;
  private Long paUID;
  
  public Projektattraktivitaet() {}

	public Integer getProjektattraktivitaetId() {
    return projektattraktivitaetId;
  }

  public void setProjektattraktivitaetId(Integer projektattraktivitaetId) {
    this.projektattraktivitaetId = projektattraktivitaetId;
  }

  public Integer getCustomizingId() {
    return customizingId;
  }

  public void setCustomizingId(Integer customizingId) {
    this.customizingId = customizingId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getProjektattraktivitaetResultatId() {
    return projektattraktivitaetResultatId;
  }

  public void setProjektattraktivitaetResultatId(
      Integer projektattraktivitaetResultatId) {
    this.projektattraktivitaetResultatId = projektattraktivitaetResultatId;
  }

  public Integer getProjektId() {
    return projektId;
  }

  public void setProjektId(Integer projektId) {
    this.projektId = projektId;
  }

  public Double getResultat() {
    return resultat;
  }

  public void setResultat(Double resultat) {
    this.resultat = resultat;
  }

  public Long getPaUID() {
    return paUID;
  }

  public void setPaUID(Long paUID) {
    this.paUID = paUID;
  }

}

