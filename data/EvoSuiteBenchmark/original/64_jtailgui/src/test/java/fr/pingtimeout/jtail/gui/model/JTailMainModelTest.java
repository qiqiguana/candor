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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Observable;
import java.util.Observer;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 9 mai 2010
 * Time: 19:37:17
 * To change this template use File | Settings | File Templates.
 */
public class JTailMainModelTest {
    JTailMainModel model;
    JTailModel mockedModel;
    Observer observer;

    @Before
    public void before() {
        model = new JTailMainModel();
        observer = mock(Observer.class);
        mockedModel = mock(JTailModel.class);

        model.addObserver(observer);
    }

    @Test
    public void testObserver() {
        // The observer hasn't been updated yet
        verify(observer, never()).update(Matchers.<Observable>anyObject(), Matchers.<Object>anyObject());

        // Simulate an "add model" action
        model.add(mockedModel);

        // The observer should have received a "created !" notification saying that there is now 1 active model
        verify(observer, times(1)).update(
                model, model.createMainModelEvent(mockedModel, 0, JTailMainModelEvent.Type.CREATED));
        verify(observer, times(1)).update(Matchers.<Observable>anyObject(), Matchers.<Object>anyObject());

        // Simulate the same "add model" action
        model.add(mockedModel);

        // The observer should not receive any other notification
        verify(observer, times(1)).update(Matchers.<Observable>anyObject(), Matchers.<Object>anyObject());

        model.setCurrentFileIndex(0);

        // The observer should have received a "modified !" notification saying that the active index is now 0
        verify(observer, times(1)).update(
                model, model.createMainModelEvent(mockedModel, 0, JTailMainModelEvent.Type.CHANGED));
        verify(observer, times(2)).update(Matchers.<Observable>anyObject(), Matchers.<Object>anyObject());

        // Simulate a "remove model" action for an unknown model
        model.remove(mock(JTailModel.class));

        // The observer should not receive any other notification
        verify(observer, times(2)).update(Matchers.<Observable>anyObject(), Matchers.<Object>anyObject());

        // Simulate a "remove model" action for the active model
        model.remove(mockedModel);

        // The observer should have received a "removed !" notification saying that there is now 0 active model
        verify(observer, times(1)).update(
                model, model.createMainModelEvent(mockedModel, 0, JTailMainModelEvent.Type.REMOVED));
        verify(observer, times(3)).update(Matchers.<Observable>anyObject(), Matchers.<Object>anyObject());
    }

    @Test
    public void testAddRemove() {
        Assert.assertFalse("The main model already contains our mockedModel", model.contains(mockedModel));
        Assert.assertEquals("The main model already contains 1 element", 0, model.size());
        Assert.assertEquals("The main model contains something", 0, model.getFiles().size());
        Assert.assertEquals("The main model does not have the right index", -1, model.getCurrentFileIndex());

        // Remove an unknown model
        model.remove(mockedModel);
        Assert.assertFalse("The main model already contains our mockedModel", model.contains(mockedModel));
        Assert.assertEquals("The main model already contains 1 element", 0, model.size());
        Assert.assertEquals("The main model contains something", 0, model.getFiles().size());
        Assert.assertEquals("The main model does not have the right index", -1, model.getCurrentFileIndex());

        // Add a model
        model.add(mockedModel);
        Assert.assertTrue("The main model does not contain our mockedModel", model.contains(mockedModel));
        Assert.assertEquals("The main model does not contain our mockedModel", mockedModel, model.get(0));
        Assert.assertEquals("The main model does not have the right index", 0, model.getCurrentFileIndex());
        Assert.assertEquals("The main model does not contain 1 element", 1, model.size());
        Assert.assertEquals("The main model contains something", 1, model.getFiles().size());

        // Remove it
        model.remove(mockedModel);
        Assert.assertFalse("The main model still contains our mockedModel", model.contains(mockedModel));
        Assert.assertEquals("The main model still contains 1 element", 0, model.size());
        Assert.assertEquals("The main model contains something", 0, model.getFiles().size());
        Assert.assertEquals("The main model does not have the right index", -1, model.getCurrentFileIndex());
    }

    @Test
    public void testChangeFile() {
        Assert.assertEquals("The current index is incorrect", -1, model.getCurrentFileIndex());

        // Add a model
        model.add(mockedModel);
        Assert.assertEquals("The current index is incorrect", 0, model.getCurrentFileIndex());

        // Remove it
        model.remove(mockedModel);
        Assert.assertFalse("The main model still contains our mockedModel", model.contains(mockedModel));
        Assert.assertEquals("The main model still contains 1 element", 0, model.size());
        Assert.assertEquals("The main model contains something", 0, model.getFiles().size());
        Assert.assertEquals("The main model does not have the right index", -1, model.getCurrentFileIndex());
    }
}
