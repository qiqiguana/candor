/*
 * ResourcesInjector.java
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
package com.mytdev.resources4j;

import com.mytdev.resources4j.interfaces.ResourcesBundle;
import com.mytdev.resources4j.interfaces.Resources;
import com.mytdev.resources4j.annotations.Resource;
import com.mytdev.resources4j.annotations.TimeResource;
import com.mytdev.resources4j.annotations.DateTimeResource;
import com.mytdev.resources4j.annotations.DateResource;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * The ResourcesInjector class allows to inject resources on an object using a
 * specific ResourceBundle and a specific Locale.
 * The reources are injected in the processed object's fields marked with
 * the resources annotations.
 * @author Yann D'Isanto
 */
public final class ResourcesInjector {

    private Locale locale = Locale.getDefault();
    private ResourcesBundle resourcesBundle;

    /**
     * Creates a new instance of <code>ResourcesInjector</code> with the
     * specified ResourcesBundle and the default Locale.
     * @param resourcesBundle the ResourcesBundle.
     */
    public ResourcesInjector(ResourcesBundle resourcesBundle) {
        this(resourcesBundle, Locale.getDefault());
    }

    /**
     * Creates a new instance of <code>ResourcesInjector</code> with the
     * specified ResourcesBundle and Locale.
     * @param resourcesBundle the ResourcesBundle.
     * @param locale the locale.
     */
    public ResourcesInjector(ResourcesBundle resourcesBundle, Locale locale) {
        setResourcesBundle(resourcesBundle);
        setLocale(locale);
    }

    /**
     * Injects resources in the specified object's fields marked with the
     * resources annotations.
     * @param object the object to process.
     * @throws ResourcesException if an error occurs during resources injection.
     */
    public void process(Object object) throws ResourcesException {
        try {
            Class<?> c = object.getClass();
            process(object, c.getDeclaredFields());
            process(object, c.getFields());
        } catch (IllegalArgumentException ex) {
            throw new ResourcesException(ex);
        } catch (IllegalAccessException ex) {
            throw new ResourcesException(ex);
        } catch (InvocationTargetException ex) {
            throw new ResourcesException(ex);
        } catch (NoSuchMethodException ex) {
            throw new ResourcesException(ex);
        } catch (IOException ex) {
            throw new ResourcesException(ex);
        }
    }

    private void process(Object object, Field[] fields)
            throws IllegalArgumentException, IllegalAccessException, IOException,
            NoSuchMethodException, InvocationTargetException {
        Resources resources = resourcesBundle.getResources(locale);
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();

            Resource resource = field.getAnnotation(Resource.class);
            if (resource != null) {
                String property = resource.property();
                String resourceName = "".equals(resource.name()) ? field.getName() : resource.name();
                if ("".equals(property)) {
                    Object value = getResourceValue(field.getName(), resource, fieldClass);
                    field.set(object, value);
                } else {
                    injectResourceToProperty(field.get(object), property, resourceName, resource, null);
                }
            }

            DateResource dateResource = field.getAnnotation(DateResource.class);
            if (dateResource != null) {
                String resourceName = "".equals(dateResource.name())
                        ? field.getName() : dateResource.name();
                String pattern = dateResource.pattern();
                DateFormat dateFormat = null;
                if ("".equals(pattern)) {
                    dateFormat = DateFormat.getDateInstance(dateResource.style(), locale);
                } else {
                    if (dateResource.loadPatternAsResource()) {
                        pattern = resources.getString(pattern);
                    }
                    dateFormat = new SimpleDateFormat(pattern);
                }
                String value = dateFormat.format(resources.getDate(resourceName));
                String property = dateResource.property();
                if ("".equals(dateResource.property())) {
                    field.set(object, value);
                } else {
                    injectResourceToProperty(field.get(object), property, resourceName, resource, value);
                }
            }

            TimeResource timeResource = field.getAnnotation(TimeResource.class);
            if (timeResource != null) {
                String resourceName = "".equals(timeResource.name())
                        ? field.getName() : timeResource.name();
                String pattern = timeResource.pattern();
                DateFormat dateFormat = null;
                if ("".equals(pattern)) {
                    dateFormat = DateFormat.getTimeInstance(timeResource.style(), locale);
                } else {
                    if (timeResource.loadPatternAsResource()) {
                        pattern = resources.getString(pattern);
                    }
                    dateFormat = new SimpleDateFormat(pattern);
                }
                String value = dateFormat.format(resources.getDate(resourceName));
                String property = timeResource.property();
                if ("".equals(timeResource.property())) {
                    field.set(object, value);
                } else {
                    injectResourceToProperty(field.get(object), property, resourceName, resource, value);
                }
            }

            DateTimeResource dateTimeResource = field.getAnnotation(DateTimeResource.class);
            if (dateTimeResource != null) {
                String resourceName = "".equals(dateTimeResource.name())
                        ? field.getName() : dateTimeResource.name();
                String pattern = dateTimeResource.pattern();
                DateFormat dateFormat = null;
                if ("".equals(pattern)) {
                    dateFormat = DateFormat.getDateTimeInstance(
                            dateTimeResource.dateStyle(),
                            dateTimeResource.timeStyle(),
                            locale);
                } else {
                    if (dateTimeResource.loadPatternAsResource()) {
                        pattern = resources.getString(pattern);
                    }
                    dateFormat = new SimpleDateFormat(pattern);
                }
                String value = dateFormat.format(resources.getDate(resourceName));
                String property = dateTimeResource.property();
                if ("".equals(dateTimeResource.property())) {
                    field.set(object, value);
                } else {
                    injectResourceToProperty(field.get(object), property, resourceName, resource, value);
                }
            }
            field.setAccessible(false);
        }
    }

    private Object getResourceValue(String resourceName, Resource resource, Class<?> type)
            throws IllegalArgumentException, IOException {
        Object value = null;
        Resources resources = resourcesBundle.getResources(locale);
        String resourceKey = "".equals(resource.name()) ? resourceName : resource.name();
        if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            value = resources.getInt(
                    resourceKey, resource.intDefaultValue(), resource.radix());
        } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            value = resources.getDouble(
                    resourceKey, resource.doubleDefaultValue());
        } else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            value = resources.getBoolean(
                    resourceKey, resource.booleanDefaultValue());
        } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            value = resources.getLong(
                    resourceKey, resource.longDefaultValue(), resource.radix());
        } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            value = resources.getFloat(
                    resourceKey, resource.floatDefaultValue());
        } else if (type.equals(Character.TYPE) || type.equals(Character.class)) {
            value = resources.getChar(
                    resourceKey, resource.charDefaultValue());
        } else if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            value = resources.getByte(
                    resourceKey, resource.byteDefaultValue(), resource.radix());
        } else if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            value = resources.getShort(
                    resourceKey, resource.shortDefaultValue(), resource.radix());
        } else if (type.isArray()) {
            Class<?> arrayClass = type.getComponentType();
            if (arrayClass.equals(Integer.TYPE)) {
                value = resources.getIntArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Double.TYPE)) {
                value = resources.getDoubleArray(resourceKey);
            } else if (arrayClass.equals(Boolean.TYPE)) {
                value = resources.getBooleanArray(resourceKey);
            } else if (arrayClass.equals(Long.TYPE)) {
                value = resources.getLongArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Float.TYPE)) {
                value = resources.getFloatArray(resourceKey);
            } else if (arrayClass.equals(Character.TYPE)) {
                value = resources.getCharArray(resourceKey);
            } else if (arrayClass.equals(Byte.TYPE)) {
                value = resources.getByteArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Short.TYPE)) {
                value = resources.getShortArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Integer.class)) {
                value = resources.getIntegerArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Double.class)) {
                value = resources.getDoubleObjectArray(resourceKey);
            } else if (arrayClass.equals(Boolean.class)) {
                value = resources.getBooleanObjectArray(resourceKey);
            } else if (arrayClass.equals(Long.class)) {
                value = resources.getLongObjectArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Float.class)) {
                value = resources.getFloatObjectArray(resourceKey);
            } else if (arrayClass.equals(Character.class)) {
                value = resources.getCharacterArray(resourceKey);
            } else if (arrayClass.equals(Byte.class)) {
                value = resources.getByteObjectArray(resourceKey, resource.radix());
            } else if (arrayClass.equals(Short.class)) {
                value = resources.getShortObjectArray(resourceKey, resource.radix());
            }
        } else if (type.isAssignableFrom(String.class)) {
            value = resources.getString(resourceKey, resource.stringDefaultValue());
        } else if (type.isAssignableFrom(Color.class)) {
            value = resources.getColor(resourceKey);
        } else if (type.isAssignableFrom(Date.class)) {
            value = resources.getDate(resourceKey);
        } else if (type.isAssignableFrom(Image.class)) {
            value = resources.getImage(resourceKey);
        } else if (type.isAssignableFrom(Icon.class)) {
            value = new ImageIcon(resources.getImage(resourceKey));
        } else {
            throw new IllegalArgumentException("unupported resource type:" + type);
        }
        return value;
    }

    private void injectResourceToProperty(Object object, String propertyPath, String resourceName, Resource resource, Object value)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        int index = propertyPath.lastIndexOf(".");
        if (index > -1) {
            object = getProperty(object, propertyPath.substring(0, index));
        }
        String property = index == -1 ? propertyPath : propertyPath.substring(index + 1, propertyPath.length());
        String methodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
        Class<?> objectClass = object.getClass();
        for (Method method : objectClass.getMethods()) {
            if (method.getName().equals(methodName)) {
                if (value == null) {
                    value = getResourceValue(resourceName, resource, method.getParameterTypes()[0]);
                }
                method.invoke(object, value);
                return;
            }
        }
        try {
            Field field = objectClass.getDeclaredField(property);
            if (value == null) {
                value = getResourceValue(resourceName, resource, field.getType());
            }
            field.set(object, value);
        } catch (NoSuchFieldException ex3) {
            for (Method method : objectClass.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    if (value == null) {
                        value = getResourceValue(resourceName, resource, method.getParameterTypes()[0]);
                    }
                    method.invoke(object, value);
                }
            }
            try {
                Field field = objectClass.getField(propertyPath);
                if (value == null) {
                    value = getResourceValue(resourceName, resource, field.getType());
                }
                field.set(object, value);
            } catch (NoSuchFieldException ex6) {
                throw new IllegalArgumentException("property not found: " + propertyPath);
            }
        }
    }

    private Object getProperty(Object object, String path) throws IllegalAccessException, InvocationTargetException {
        Object value = null;
        int index = path.indexOf(".");
        String property = index == -1 ? path : path.substring(0, index);
        String methodBasename = property.substring(0, 1).toUpperCase() + property.substring(1);
        Method method = null;
        Field field = null;
        Class<?> objectClass = object.getClass();
        try {
            method = objectClass.getMethod("get" + methodBasename);
        } catch (NoSuchMethodException ex1) {
            try {
                method = objectClass.getMethod("is" + methodBasename);
            } catch (NoSuchMethodException ex2) {
                try {
                    field = objectClass.getDeclaredField(path);
                } catch (NoSuchFieldException ex3) {
                    try {
                        method = objectClass.getDeclaredMethod("get" + methodBasename);
                    } catch (NoSuchMethodException ex4) {
                        try {
                            method = objectClass.getDeclaredMethod("is" + methodBasename);
                        } catch (NoSuchMethodException ex5) {
                            try {
                                field = objectClass.getField(path);
                            } catch (NoSuchFieldException ex6) {
                                throw new IllegalArgumentException("property not found: " + path);
                            }
                        }
                    }
                }
            }
        }
        if (method != null) {
            value = method.invoke(object);
        } else if (field != null) {
            value = field.get(object);
        }
        if (index > -1) {
            value = getProperty(value, path.substring(index + 1, path.length()));
        }
        return value;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public ResourcesInjector setLocale(Locale locale) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        return this;
    }

    /**
     * @return the resourcesBundle
     */
    public ResourcesBundle getResourcesBundle() {
        return resourcesBundle;
    }

    /**
     * @param resourcesBundle the resourcesBundle to set
     */
    public ResourcesInjector setResourcesBundle(ResourcesBundle resourcesBundle) {
        if (resourcesBundle == null) {
            throw new NullPointerException("resources bundle is null");
        }
        this.resourcesBundle = resourcesBundle;
        return this;
    }
}
