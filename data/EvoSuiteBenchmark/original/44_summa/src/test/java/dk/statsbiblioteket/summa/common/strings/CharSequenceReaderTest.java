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
package dk.statsbiblioteket.summa.common.strings;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;

/**
 * Unit tests for the {@link CharSequenceReader}
 */
public class CharSequenceReaderTest extends TestCase {

    StringBuffer buf;
    CharSequenceReader seq;

    @Override
    public void setUp() {
        buf = new StringBuffer();
    }

    public void testReadManySingleChar() throws Exception {
        seq = new CharSequenceReader(buf.append("foobar"));
        assertEquals("foobar", readFullySingleChar(seq));
    }

    public void testReadManySmallArray() throws Exception {
        seq = new CharSequenceReader(buf.append("foobar"));
        assertEquals("foobar", readFullySmallArray(seq));
    }

    public void testReadManyBigArray() throws Exception {
        seq = new CharSequenceReader(buf.append("foobar"));
        assertEquals("foobar", readFullyBigArray(seq));
    }

    public void testReadMoreSingleChar() throws Exception {
        seq = new CharSequenceReader(buf.append("autobiografia"));
        assertEquals("autobiografia", readFullySingleChar(seq));
    }

    public void testReadMoreSmallArray() throws Exception {
        seq = new CharSequenceReader(buf.append("autobiografia"));
        assertEquals("autobiografia", readFullySmallArray(seq));
    }

    public void testReadMoreBigArray() throws Exception {
        seq = new CharSequenceReader(buf.append("autobiografia"));
        assertEquals("autobiografia", readFullyBigArray(seq));
    }

    public void testReadSingleSingleChar() throws Exception {
        seq = new CharSequenceReader(buf.append("f"));
        assertEquals("f", readFullySingleChar(seq));
    }

    public void testReadSingleSmallArray() throws Exception {
        seq = new CharSequenceReader(buf.append("f"));
        assertEquals("f", readFullyBigArray(seq));
    }

    public void testReadSingleBigArray() throws Exception {
        seq = new CharSequenceReader(buf.append("f"));
        assertEquals("f", readFullyBigArray(seq));
    }

    public void testReadEmptySingleChar() throws Exception {
        seq = new CharSequenceReader(buf.append(""));
        assertEquals("", readFullySingleChar(seq));
    }

    public void testReadEmptySmallArray() throws Exception {
        seq = new CharSequenceReader(buf.append(""));
        assertEquals("", readFullyBigArray(seq));
    }

    public void testReadEmptyBigArray() throws Exception {
        seq = new CharSequenceReader(buf.append(""));
        assertEquals("", readFullyBigArray(seq));
    }

    public static String readFullySmallArray(Reader r) throws IOException {
        int len;
        char[] a = new char[5];
        StringBuffer tmp = new StringBuffer();

        while ((len = r.read(a)) != -1) {
            tmp.append(a, 0, len);
        }

        return tmp.toString();
    }

    public static String readFullySingleChar(Reader r) throws IOException {
        int val;
        StringBuffer tmp = new StringBuffer();

        while ((val = r.read()) != -1) {
            tmp.append((char)val);
        }

        return tmp.toString();
    }

    public static String readFullyBigArray(Reader r) throws IOException {
        int len;
        char[] a = new char[1024];
        StringBuffer tmp = new StringBuffer();

        while ((len = r.read(a)) != -1) {
            tmp.append(a, 0, len);
        }

        return tmp.toString();
    }

}

