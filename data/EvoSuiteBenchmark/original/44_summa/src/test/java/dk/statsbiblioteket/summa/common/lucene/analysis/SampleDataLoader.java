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
package dk.statsbiblioteket.summa.common.lucene.analysis;

import dk.statsbiblioteket.util.Strings;
import junit.framework.TestCase;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;

/**
 * Load test data into memory so we have it around. This is done to
 * prevent any IO during performance test of the analyzers
 */
public class SampleDataLoader extends TestCase {


    static final List<String> data;
    static {
        String SOURCE = "common/lgpl-2.1-german.txt";
        InputStream in = getSystemResourceAsStream(SOURCE);
        if (in == null) {
            throw new NullPointerException("Unable to locate '" + SOURCE + "'");
        }
        data = Arrays.asList(Strings.flushLocal(in));
    }

    public SampleDataLoader() {
        // for unit test purpose
    }

    public void testDummy() {
        assertTrue(true);
    }

    public static String getDataString(int i) {
        return data.get(i);
    }

    public static Reader getDataReader(int i) {
        return new StringReader(data.get(i));
    }
}