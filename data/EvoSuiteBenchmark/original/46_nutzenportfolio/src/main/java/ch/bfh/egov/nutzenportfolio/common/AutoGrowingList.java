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

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArrayList, die anwachsen kann.
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class AutoGrowingList extends ArrayList {
  private static final long serialVersionUID = -2296309274828478272L;
  private Class clazz;
  private Log logger = LogFactory.getLog(this.getClass());

  public AutoGrowingList(Class clazz) {
    super();
    this.clazz = clazz;
  }

  public Object get(int index) {
    Object obj = null;

    if (index >= super.size() || (obj = super.get(index)) == null) {
      try {
        while (index + 1 > super.size()) {
          logger.debug("list size: " + super.size());
          obj = clazz.newInstance();
          super.add(obj);
        }
      } catch (Exception iex) {
        logger.warn(iex.getMessage());
        logger.warn(iex.getStackTrace());
      }
    }
    logger.debug("list size: " + super.size());
    return obj;
  }
}
