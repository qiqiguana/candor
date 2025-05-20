/*
 * DateTimeResource.java
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

package com.mytdev.resources4j.annotations;

import java.text.DateFormat;

/**
 * Annotation used to inject a Date resource on a String field as date time.
 * @author Yann D'Isanto
 */
public @interface DateTimeResource {

    /** @return the resource name */
    String name();

    /** @return the property to set. If blank (the default value), the field itself is set.  */
    String property() default "";

    /** @return the DateFormat date style to use if no pattern is specified */
    int dateStyle() default DateFormat.DEFAULT;

    /** @return the DateFormat time style to use if no pattern is specified */
    int timeStyle() default DateFormat.DEFAULT;

    /** @return the DateFormat pattern to use or an empty String to use DateFormat style. */
    String pattern() default "";

    /** @return true if the specified pattern is a key to load the pattern in resources. */
    boolean loadPatternAsResource() default false;

}
