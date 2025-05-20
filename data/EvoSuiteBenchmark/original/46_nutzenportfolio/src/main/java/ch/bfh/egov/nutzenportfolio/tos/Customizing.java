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
 * Transfer Object für Customizings.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class Customizing implements Serializable {
  static final long serialVersionUID = -309972355751606573L;
  private Integer customizingId;
  private Integer mandantId;
  private String name;
  private Boolean status;

	public Customizing() {}

	public Integer getCustomizingId() {
		return customizingId;
	}

	public void setCustomizingId(Integer customizingId) {
		this.customizingId = customizingId;
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

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

}

