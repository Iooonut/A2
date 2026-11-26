package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GarbageCollectorTest {

    @Test
    void removesUnreachableLocations() {

        List<IValue> symTable = List.of(new RefValue(1, new IntType()));
        Map<Integer, IValue> heap = Map.of(1, new IntValue(20), 2, new IntValue(30));
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);

        assertEquals(Map.of(1, new IntValue(20)), kept);
    }

    @Test
    void keepsLocationsReachableThroughHeap() {

        List<IValue> symTable = List.of(new RefValue(3, new IntType()), new RefValue(2, new RefType(new IntType())));
        Map<Integer, IValue> heap = Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType()), 3, new IntValue(30), 4, new IntValue(7));
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);

        assertEquals(Set.of(2, 3), roots);
        assertEquals(Set.of(1, 2, 3), GarbageCollector.reachableAddresses(roots, heap));

        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);

        assertEquals(Set.of(1, 2, 3), kept.keySet());
        assertEquals(new IntValue(20), kept.get(1));
    }
}