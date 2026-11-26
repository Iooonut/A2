package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}

    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream().filter(v -> v instanceof RefValue).map(v -> ((RefValue) v).address()).collect(Collectors.toSet());
    }

    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>();
        Deque<Integer> pending = new ArrayDeque<>(roots);

        while(!pending.isEmpty()) {
            int address = pending.removeFirst();

            if(!heap.containsKey(address) || !reachable.add(address)) {
                continue;
            }

            IValue value = heap.get(address);

            if(value instanceof RefValue ref) {
                pending.addLast(ref.address());

            }
        }

        return reachable;
    }

    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = reachableAddresses(roots, heap);

        return heap.entrySet().stream().filter(e -> reachable.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
