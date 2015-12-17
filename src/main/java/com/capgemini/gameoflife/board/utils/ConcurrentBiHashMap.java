package com.capgemini.gameoflife.board.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentBiHashMap<K, V> extends ConcurrentHashMap<K, V> implements BiMap<K, V> {
	private static final long serialVersionUID = -172939375740542585L;
	private Map<V, K> reversedMap;

	public ConcurrentBiHashMap() {
		super();
		this.reversedMap = new ConcurrentHashMap<>();
	}
	
	public ConcurrentBiHashMap(int initialCapacity) {
		super(initialCapacity);
		this.reversedMap = new ConcurrentHashMap<>(initialCapacity);
	}
	
	public ConcurrentBiHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		this.reversedMap = new ConcurrentHashMap<>(initialCapacity, loadFactor);
	}
	
	@Override
	public V put(K key, V value) {
		reversedMap.put(value, key);
		return super.put(key, value);
	};
	
	@Override
	public void putAll(Map<? extends K,? extends V> m) {
		super.putAll(m);
		m.forEach((k, v) -> reversedMap.put(v, k));
	};
	
	@Override
	public V remove(Object key) {
		V removed = super.remove(key);
		reversedMap.remove(removed);
		return removed;
	};
	
	@Override
	public boolean containsValue(Object value) {
		return reversedMap.containsKey(value);
	}

	@Override
	public K removeByValue(V value) {
		K removedKey = reversedMap.remove(value);
		super.remove(removedKey);
		return removedKey;
	}

	@Override
	public Collection<K> removeByValues(Collection<V> values) {
		Collection<K> removedKeys = new HashSet<K>(values.size(), 1.0f);
		values.forEach(v -> removedKeys.add(removeByValue(v)));
		return removedKeys;
	}

	@Override
	public K getByValue(V value) {
		return reversedMap.get(value);
	}

	@Override
	public Collection<V> removeByKeys(Collection<K> keys) {
		Collection<V> removedValues = new HashSet<V>(keys.size(), 1.0f);
		keys.forEach(k -> removedValues.add(remove(k)));
		return removedValues;
	}
	
	@Override
	public Set<V> valueSet() {
		return reversedMap.keySet();
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
