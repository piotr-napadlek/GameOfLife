package com.capgemini.gameoflife.board.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface BiMap<K, V> extends Map<K, V> {
	public K removeByValue(V value);
	public Collection<K> removeByValues(Collection<V> values);
	public K getByValue(V value);
	public Collection<V> removeByKeys(Collection<K> keys);
	public Set<V> valueSet();
}
