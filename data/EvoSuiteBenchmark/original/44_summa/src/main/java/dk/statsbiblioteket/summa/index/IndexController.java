/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.index;

import dk.statsbiblioteket.util.qa.QAInfo;
import dk.statsbiblioteket.summa.common.filter.object.ObjectFilter;

/**
 * A controller for manipulating indexes. An index is a representation of
 * data in searcheable form and will normally include an Luxene index. In
 * addition it will normally include an IndexDescriptor and Facet information.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
public interface IndexController extends ObjectFilter {
    /**
     * Add a manipulator to the list of manipulators. The order of addition is
     * significant: Manipulators will be called in order of addition.
     * @param manipulator the manipulator to add.
     */
    public void addManipulator(IndexManipulator manipulator);

    /**
     * Removea a manipulator from the list of manipulators.
     * @param manipulator the manipulator to remove.
     * @return if the manipulator was removed.
     */
    public boolean removeManipulator(IndexManipulator manipulator);

}




