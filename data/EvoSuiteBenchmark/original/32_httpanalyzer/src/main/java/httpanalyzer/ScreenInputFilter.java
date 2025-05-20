/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 * 
 * Copyright (C) 2010, vlad
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package httpanalyzer;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vlad
 */
public class ScreenInputFilter extends FilterInputStream {

    public ScreenInputFilter (InputStream in) {
        super(in);
    }
    @Override
    public int read() {
        int character=0;
        try {
            character = super.read();
            if (character == 0x0A)
            return character;
            else if (character < 0x20) {
                return 0x58;
            }
        } catch (IOException ex) {
            Logger.getLogger(ScreenInputFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return character;
    }


}
