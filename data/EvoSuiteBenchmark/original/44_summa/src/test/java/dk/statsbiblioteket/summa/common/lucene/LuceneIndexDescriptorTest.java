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
package dk.statsbiblioteket.summa.common.lucene;

import dk.statsbiblioteket.summa.common.configuration.Resolver;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class LuceneIndexDescriptorTest extends TestCase {
    public LuceneIndexDescriptorTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(LuceneIndexDescriptorTest.class);
    }

    public void testMoreLikeThisFields() throws IOException {
        LuceneIndexDescriptor descriptor = new LuceneIndexDescriptor(
                Resolver.getURL("common/LucenIndexDescriptor/MoreLikeThisDescriptor.xml"));
        assertEquals("There should be the right number of MoreLikeThis fields",
                   2, descriptor.getMoreLikethisFields().size());
    }

    public void testParseSpeed() throws IOException {
  //      long startTime = System.currentTimeMillis();
        LuceneIndexDescriptor descriptor = new LuceneIndexDescriptor(
                Resolver.getURL("common/LucenIndexDescriptor/ManyFieldsDescriptor.xml"));
//        System.out.println("Spend " + (System.currentTimeMillis()-startTime) + "ms loading descriptor");
        assertEquals("There should be the right number of fields",
                   7621, descriptor.getFields().size());
    }
}

