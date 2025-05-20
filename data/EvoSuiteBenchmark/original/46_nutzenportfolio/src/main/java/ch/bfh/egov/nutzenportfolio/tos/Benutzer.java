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
 * Transfer Object für Benutzer.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Benutzer implements Serializable {
  static final long serialVersionUID = -2261596167210195125L;
  private Integer benutzerId;
  private Integer mandantId;
  private String benutzername;
  private String passwort;

	public Benutzer() {}

	public Integer getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(Integer benutzerId) {
		this.benutzerId = benutzerId;
	}

	public Integer getMandantId() {
		return mandantId;
	}

	public void setMandantId(Integer mandantId) {
		this.mandantId = mandantId;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

}

