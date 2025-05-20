/*
 * ChildResources.java
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

package com.mytdev.resources4j.impl;

import com.mytdev.resources4j.interfaces.Resources;
import java.text.DateFormat;
import java.util.Map;

/**
 * An AbstractResources which has a parent Resources.
 * @author Yann D'Isanto
 */
public abstract class ChildResources extends AbstractResources {

    private Resources parentResources;

    public ChildResources(Map<String, String> resources, Resources parentResources, DateFormat dateFormat) {
        super(resources, dateFormat);
        this.parentResources = parentResources;
    }

    @Override
    protected String loadString(String key) {
        String value = super.loadString(key);
        if(value == null && parentResources != null) {
            value = parentResources.getString(key);
        }
        return value;
    }



}
