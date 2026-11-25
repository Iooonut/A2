package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyHeapTest {

    @Test
    void allocateReturnsSequentialAddresses()
            throws ADTException {

        IHeap heap = new MyHeap();

        assertEquals(1, heap.allocate(new IntValue(20)));
        assertEquals(2, heap.allocate(new IntValue(50)));
        assertEquals(3, heap.allocate(new IntValue(70)));

        assertEquals(new IntValue(20), heap.get(1));
        assertEquals(new IntValue(50), heap.get(2));
        assertEquals(new IntValue(70), heap.get(3));
    }

    @Test
    void getMissingAddressThrows() {
        IHeap heap = new MyHeap();

        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void updateMissingAddressThrows() {
        IHeap heap = new MyHeap();

        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(20)));
    }
}
