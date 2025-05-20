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
 * Transfer Object für Strategische Ziele.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class StrategischesZiel implements Serializable {
  static final long serialVersionUID = -606241255232304330L;
	private Integer strategischesZielId;
	private Integer projektattraktivitaetId;
	private Integer mandantId;
  private Integer customizingId;
	private String name;
	private String beschreibung;
  private Boolean system;
	
	public StrategischesZiel() {}

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

	public Integer getProjektattraktivitaetId() {
		return projektattraktivitaetId;
	}

	public void setProjektattraktivitaetId(Integer projektattraktivitaetId) {
		this.projektattraktivitaetId = projektattraktivitaetId;
	}

	public Integer getStrategischesZielId() {
		return strategischesZielId;
	}

	public void setStrategischesZielId(Integer strategischesZielId) {
		this.strategischesZielId = strategischesZielId;
	}

  public Integer getCustomizingId() {
    return customizingId;
  }

  public void setCustomizingId(Integer customizingId) {
    this.customizingId = customizingId;
  }

  public Boolean getSystem() {
    return system;
  }

  public void setSystem(Boolean system) {
    this.system = system;
  }

}
