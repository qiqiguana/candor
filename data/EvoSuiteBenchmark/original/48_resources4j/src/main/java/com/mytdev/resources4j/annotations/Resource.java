/*
 * Resource.java
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to inject a resource on a field.
 * @author Yann D'Isanto
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Resource {

    /** @return the resource name */
    String name() default "";

    /** @return the property to set. If blank (the default value), the field itself is set.  */
    String property() default "";

    /** @return the radix to use for retreiving byte, short, integer or long. Default is 10. */
    int radix() default 10;

    /** @return the boolean default value. Default value is false. */
    boolean booleanDefaultValue() default false;

    /** @return the byte default value. Default value is 0. */
    byte byteDefaultValue() default 0;

    /** @return the char default value. Default value is '\0'. */
    char charDefaultValue() default '\0';

    /** @return the double default value. Default value is 0.0. */
    double doubleDefaultValue() default 0.0;

    /** @return the float default value. Default value is 0.0. */
    float floatDefaultValue() default 0.0F;

    /** @return the int default value. Default value is O. */
    int intDefaultValue() default 0;

    /** @return the long default value. Default value is O. */
    long longDefaultValue() default 0L;

    /** @return the short default value. Default value is O. */
    short shortDefaultValue() default 0;

    /** @return the String default value. Default value is "". */
    String stringDefaultValue() default "";

}
