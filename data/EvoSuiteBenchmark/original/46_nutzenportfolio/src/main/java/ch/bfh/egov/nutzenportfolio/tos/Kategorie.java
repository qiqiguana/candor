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
 * Transfer Object für Kategorien.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Kategorie implements Serializable {
  private static final long serialVersionUID = 4646040690816730291L;
	private Integer kategorieId;
	private Integer nutzenattraktivitaetOperativerNutzenId;
	private Integer mandantId;
  private Integer customizingId;
	private String name;
	private String beschreibung;
  private Double gewichtung;
  private Boolean system;
	
	public Kategorie() {}
  
	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Integer getMandantId() {
		return mandantId;
	}

	public void setMandantId(Integer mandantId) {
		this.mandantId = mandantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNutzenattraktivitaetOperativerNutzenId() {
		return nutzenattraktivitaetOperativerNutzenId;
	}

	public void setNutzenattraktivitaetOperativerNutzenId(Integer nutzenattraktivitaetOperativerNutzenId) {
		this.nutzenattraktivitaetOperativerNutzenId = nutzenattraktivitaetOperativerNutzenId;
	}

	public Integer getKategorieId() {
		return kategorieId;
	}

	public void setKategorieId(Integer kategorieId) {
		this.kategorieId = kategorieId;
	}
  
  public Integer getCustomizingId() {
    return customizingId;
  }

  public void setCustomizingId(Integer customizingId) {
    this.customizingId = customizingId;
  }

  public Double getGewichtung() {
    return gewichtung;
  }

  public void setGewichtung(Double gewichtung) {
    this.gewichtung = gewichtung;
  }

  public Boolean getSystem() {
    return system;
  }

  public void setSystem(Boolean system) {
    this.system = system;
  }

}
