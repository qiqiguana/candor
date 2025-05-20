/*
 * Copyright (c) 2010 Pierre Laporte.
 *
 * This file is part of JTailPlus.
 *
 * JTailPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTailPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTailPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.pingtimeout.jtail.io.index;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: plaporte
 * Date: 23 août 2010
 * Time: 17:25:02
 * To change this template use File | Settings | File Templates.
 */
public class RomFileIndexTest extends AbstractFileIndexTest {
    @Override
    public FileIndex createFileIndex(File file) {
        return new RomFileIndex(file);
    }
}
