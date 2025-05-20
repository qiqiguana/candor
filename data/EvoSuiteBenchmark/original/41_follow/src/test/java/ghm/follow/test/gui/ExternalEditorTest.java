/* 
 * Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)
 *
 * This file is part of Follow (http://follow.sf.net).
 *
 * Follow is free software; you can redistribute it and/or modify
 * it under the terms of version 2 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * Follow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Follow; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ghm.follow.test.gui;

import org.junit.Test;

import ghm.follow.gui.ExternalEditor;
import junit.framework.JUnit4TestAdapter;

import static org.junit.Assert.*;

public class ExternalEditorTest
{

	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter(ExternalEditorTest.class);
	}

	@Test
	public void testSimple()
	{
		String config = "C:\\WINNT\\notepad.exe";
		String filename = "C:\\tmp\\test.txt";

		ExternalEditor editor = new ExternalEditor(config);
		String[] cmd = editor.toCmdArray(filename);

		assertEquals(2, cmd.length);
		assertEquals(config, cmd[0]);
		assertEquals(filename, cmd[1]);
	}

	@Test
	public void testExePlusArgs()
	{
		String exe = "C:\\app\\TextPad4\\TextPad.exe";
		String arg1 = "-somearg";
		String arg2 = "/OtherArg";
		String arg3 = "Another";
		String config = exe + "  " + arg1 + " " + arg2 + "\t" + arg3;
		String filename = "C:\\tmp\\test.txt";

		ExternalEditor editor = new ExternalEditor(config);
		String[] cmd = editor.toCmdArray(filename);

		assertEquals(5, cmd.length);
		assertEquals(exe, cmd[0]);
		assertEquals(arg1, cmd[1]);
		assertEquals(arg2, cmd[2]);
		assertEquals(arg3, cmd[3]);
		assertEquals(filename, cmd[4]);
	}

	@Test
	public void testExeWithSpaces()
	{
		String exe = "C:\\Program Files\\Internet Explorer\\IEXPLORE.EXE";
		String config = "'" + exe + "'";
		String filename = "C:\\tmp\\test.txt";

		ExternalEditor editor = new ExternalEditor(config);
		String[] cmd = editor.toCmdArray(filename);

		assertEquals(2, cmd.length);
		assertEquals(exe, cmd[0]);
		assertEquals(filename, cmd[1]);
	}

	@Test
	public void testArgsWithSpaces()
	{
		String exe = "/usr/openwin/bin/textedit";
		String arg1 = "Some Arg";
		String arg2 = "another";
		String config = exe + " '" + arg1 + "' " + arg2;
		String filename = "C:\\tmp\\test.txt";

		ExternalEditor editor = new ExternalEditor(config);
		String[] cmd = editor.toCmdArray(filename);

		assertEquals(4, cmd.length);
		assertEquals(exe, cmd[0]);
		assertEquals(arg1, cmd[1]);
		assertEquals(arg2, cmd[2]);
		assertEquals(filename, cmd[3]);
	}

	@Test
	public void testExeAndArgsWithSpaces()
	{
		String exe = "C:\\Program Files\\something.exe";
		String arg = "Some Arg";
		String config = "'" + exe + "' '" + arg + "' ";
		String filename = "C:\\tmp\\test.txt";

		ExternalEditor editor = new ExternalEditor(config);
		String[] cmd = editor.toCmdArray(filename);

		assertEquals(3, cmd.length);
		assertEquals(exe, cmd[0]);
		assertEquals(arg, cmd[1]);
		assertEquals(filename, cmd[2]);
	}

	public static void main(String[] args)
	{
		String[] testCaseName = { ExternalEditorTest.class.getName() };
		junit.textui.TestRunner.main(testCaseName);
	}
}