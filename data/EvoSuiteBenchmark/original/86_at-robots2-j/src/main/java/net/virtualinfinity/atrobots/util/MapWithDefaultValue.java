package net.virtualinfinity.atrobots.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Pitts
 */
public class MapWithDefaultValue<K, E> implements Map<K, E> {
    private final Map<K, E> backingMap;
    private E defaultValue;

    public MapWithDefaultValue(Map<K, E> backingMap) {
        this.backingMap = backingMap;
    }

    public MapWithDefaultValue(Map<K, E> backingMap, E defaultValue) {
        this.backingMap = backingMap;
        this.defaultValue = defaultValue;
    }

    public E getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(E defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int size() {
        return backingMap.size();
    }

    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return backingMap.containsValue(value);
    }

    public E get(Object key) {
        if (!containsKey(key)) {
            return getDefaultValue();
        }
        return backingMap.get(key);
    }

    public E put(K key, E value) {
        return backingMap.put(key, value);
    }

    public E remove(Object key) {
        return backingMap.remove(key);
    }

    public void putAll(Map<? extends K, ? extends E> m) {
        backingMap.putAll(m);
    }

    public void clear() {
        backingMap.clear();
    }

    public Set<K> keySet() {
        return backingMap.keySet();
    }

    public Collection<E> values() {
        return backingMap.values();
    }

    public Set<Entry<K, E>> entrySet() {
        return backingMap.entrySet();
    }

    public boolean equals(Object o) {
        return backingMap.equals(o);
    }

    public int hashCode() {
        return backingMap.hashCode();
    }
}
