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
package dk.statsbiblioteket.summa.common.configuration;

import dk.statsbiblioteket.util.qa.QAInfo;

/**
 * A runtime exception thrown if there is an error communicating
 * with the {@link ConfigurationStorage} backend.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "mke")
public class ConfigurationStorageException extends RuntimeException {
    public static final long serialVersionUID = 65735478946L;
    public ConfigurationStorageException (Throwable cause) {
        super (cause);
    }

    public ConfigurationStorageException (String message) {
        super (message);
    }

    public ConfigurationStorageException (String message, Throwable cause) {
        super (message, cause);
    }
}




