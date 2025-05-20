/*
 * ResourcesBundle.java
 *
 *  Copyright 2010 Yann D'Isanto.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.mytdev.resources4j.interfaces;

import java.util.Locale;

/**
 * The ResourcesBundle associates Resources set to Locales.
 * @author Yann D'Isanto
 */
public interface ResourcesBundle {

    /**
     * Returns the Resources for the default Locale.
     * @return the Resources for the default Locale.
     */
    public Resources getResources();

    /**
     * Returns the Resources for the specified Locale.
     * @param locale the Locale.
     * @return the Resources for the specified Locale.
     */
    public Resources getResources(Locale locale);
    
}
