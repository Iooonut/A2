package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K,V> {
    private final Map<K, V> dictionary = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Key doesn't exist: " + key);
        }
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V Value) {
        dictionary.put(key, Value);
    }

    @Override
    public void update(K key, V Value) throws ADTException {
        if (!isDefined(key)) {
            throw new ADTException("Key doesn't exist: " + key);
        }
        dictionary.put(key, Value);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}
