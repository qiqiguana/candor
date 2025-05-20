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

package fr.pingtimeout.jtail.gui.model;

import fr.pingtimeout.jtail.io.LineReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import static org.mockito.Mockito.*;

public class JTailModelTest {
    private final static String MOCKED_FILE_NAME = "Mocked file";
    private static final int MOCKED_LINEREADER_SIZE = 4;

    File mockedFile;

    LineReader mockedLineReader;
    JTailModel model;
    Observer observer;

    @Before
    public void before() throws IOException {
        // Create mock objects
        mockedFile = mock(File.class);
        mockedLineReader = mock(LineReader.class);

        // Override method calls
        when(mockedFile.getName()).thenReturn(MOCKED_FILE_NAME);
        when(mockedLineReader.getIndexSize()).thenReturn(MOCKED_LINEREADER_SIZE);
        when(mockedLineReader.readLine(0)).thenReturn("Line 0");
        when(mockedLineReader.readLine(1)).thenReturn("Line 1");
        when(mockedLineReader.readLine(2)).thenReturn("Line 2");
        when(mockedLineReader.readLine(3)).thenReturn("Line 3");
        when(mockedLineReader.readBlock(0, 1)).thenReturn(
                Arrays.asList("Line 0"));
        when(mockedLineReader.readBlock(0, 2)).thenReturn(
                Arrays.asList("Line 0", "Line 1"));
        when(mockedLineReader.readBlock(1, 3)).thenReturn(
                Arrays.asList("Line 1", "Line 2"));
        when(mockedLineReader.readBlock(2, 4)).thenReturn(
                Arrays.asList("Line 2", "Line 3"));

        // Build Model
        model = new JTailModel(mockedFile, mockedLineReader);
        observer = mock(Observer.class);
        model.addObserver(observer);
    }

    @Test
    public void testFileName() {
        Assert.assertEquals("getFileName does not work properly",
                MOCKED_FILE_NAME,
                model.getFileName());
    }

    @Test
    public void testObserver() {
        // The observer hasn't been updated yet
        verify(observer, never())
                .update(Matchers.<Observable>anyObject(),
                        Matchers.<Object>anyObject());

        // Simulate a scroll
        model.setFirstDisplayedLine(1);

        // The observer should have received a "scrolled !" notification
        // But not a "resized !" notification
        verify(observer, times(1)).update(
                model, model.createModelEvent(JTailModelEvent.Type.SCROLLED));

        // Simulate a resize
        model.setNbDisplayedLines(2);

        // The observer should have received a "resized !" notification
        // But not a "scrolled !" notification
        verify(observer, times(1)).update(
                model, model.createModelEvent(JTailModelEvent.Type.RESIZED));
    }

    @Test
    public void testInitialValues() {
        Assert.assertEquals("At the beginning, the model's first line number should be 0",
                0, model.getFirstLine());
        Assert.assertEquals("At the beginning, the model's first displayed line number should be 0",
                0, model.getFirstDisplayedLine());
        Assert.assertEquals("At the beginning, the model should have 4 lines",
                4, model.getTotalNbLines());
        Assert.assertEquals("At the beginning, the model should display 1 line",
                1, model.getNbDisplayedLines());
    }

    @Test
    public void testSetters() {
        // Simulate a scroll
        model.setFirstDisplayedLine(1);

        Assert.assertEquals("The model's first line number should still be 0",
                0, model.getFirstLine());
        Assert.assertEquals("The model's first displayed line number should be 1",
                1, model.getFirstDisplayedLine());
        Assert.assertEquals("The model should still have 4 lines",
                4, model.getTotalNbLines());
        Assert.assertEquals("The model should display 1 line",
                1, model.getNbDisplayedLines());

        // Simulate a resize
        model.setNbDisplayedLines(2);

        Assert.assertEquals("The model's first line number should still be 0",
                0, model.getFirstLine());
        Assert.assertEquals("The model's first displayed line number should still be 1",
                1, model.getFirstDisplayedLine());
        Assert.assertEquals("The model should still have 4 lines",
                4, model.getTotalNbLines());
        Assert.assertEquals("The model should now display 2 line",
                2, model.getNbDisplayedLines());
    }

    @Test
    public void testContent() {
        Assert.assertEquals("At the beginning, the model should only display 'Line 0'",
                "Line 0", model.getContent());
        Assert.assertEquals("At the beginning, the model should only display '0' as line number",
                "0", model.getLineNumbers());

        // Simulate a resize
        model.setNbDisplayedLines(2);

        Assert.assertEquals("The model should display 'Line 0\\nLine 1'",
                "Line 0\nLine 1", model.getContent());
        Assert.assertEquals("At the beginning, the model should only display '0\\n1' as line number",
                "0\n1", model.getLineNumbers());

    }
}
