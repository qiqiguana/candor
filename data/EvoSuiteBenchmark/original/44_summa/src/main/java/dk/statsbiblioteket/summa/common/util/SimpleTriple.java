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
package dk.statsbiblioteket.summa.common.util;

import dk.statsbiblioteket.util.qa.QAInfo;

/**
 * Trivial implementation of Triple.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.QA_NEEDED,
        author = "te")
public class SimpleTriple<T, S, R> {
    protected T key;
    protected S value1;
    protected R value2;

    public SimpleTriple(T key, S value1, R value2) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
    }
    public T getKey() {
        return key;
    }
    public S getValue1() {
        return value1;
    }
    public R getValue2() {
        return value2;
    }
}
