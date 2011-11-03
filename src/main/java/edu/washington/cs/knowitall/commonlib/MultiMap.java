package edu.washington.cs.knowitall.commonlib;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

import edu.washington.cs.knowitall.commonlib.delegate.Factory;

public class MultiMap<E, F> implements Map<E, Collection<F>> {
    private final Factory<Collection<F>> factory;
    private final Map<E, Collection<F>> map;
    
    public MultiMap(Map<E, Collection<F>> map, Factory<Collection<F>> factory) {
        this.factory = factory;
        this.map = map;
    }

    public MultiMap(Map<E, Collection<F>> map) {
        this(map, new Factory<Collection<F>>() {
            @Override
            public Collection<F> create() {
                return new TreeSet<F>();
            }
        });
    }

    public MultiMap() {
        this(new TreeMap<E, Collection<F>>());
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public Collection<F> get(Object key) {
        return this.map.get(key);
    }

    @Override
    public Collection<F> put(E key, Collection<F> value) {
        return this.map.put(key, value);
    }
    
    public Collection<F> put(E key, F value) {
        if (!this.map.containsKey(key)) {
            Collection<F> list = this.factory.create();
            list.add(value);
            return this.map.put(key, list);
        }
        else {
            Collection<F> list = this.map.get(key);
            list.add(value);
            return list;
        }
    }

    @Override
    public Collection<F> remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends E, ? extends Collection<F>> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<E> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<Collection<F>> values() {
        return this.map.values();
    }

    @Override
    public Set<java.util.Map.Entry<E, Collection<F>>> entrySet() {
        return this.map.entrySet();
    }
}
