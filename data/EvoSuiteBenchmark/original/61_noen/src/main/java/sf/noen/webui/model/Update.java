/*
 * Copyright (C) 2009 VTT Technical Research Centre of Finland.
 *
 * This file is part of NOEN framework.
 *
 * NOEN framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2.
 *
 * NOEN framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package sf.noen.webui.model;

import java.io.Serializable;

/**
 * Describes an update for the table of test results in the web UI.
 *
 * @author Teemu Kanstr�n
 */
public class Update implements Serializable {
  private final String name;
  private final String status;
  private final String description;
  public final UpdateType type;
  public final String style;

  public Update(String name, String status, String description, UpdateType type, String style) {
    this.name = name;
    this.status = status;
    this.description = description;
    this.type = type;
    this.style = style;
  }

  public String getName() {
    return name;
  }

  public String getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public boolean isNew() {
    if (type == UpdateType.TEST_STARTED || type == UpdateType.SUITE_STARTED || type == UpdateType.TEST_IGNORED) {
      return true;
    }
    return false;
  }
}
