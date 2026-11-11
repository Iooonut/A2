package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void put(K key, V Value);
    void update(K key, V Value) throws ADTException;
    void remove(K key) throws ADTException;
    Map<K, V> toMap();
}

