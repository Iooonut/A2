package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    @Test
    void putAndLookup() throws ADTException {
        IDictionary<Integer, Integer> dict = new MyDictionary<>();

        dict.put(1,2);
        assertTrue(dict.isDefined(1));
        assertEquals(2,dict.lookup(1));
    }

    @Test
    void updateChangesValue() throws ADTException {
        IDictionary<Integer, Integer> dict = new MyDictionary<>();

        dict.put(1,2);
        dict.update(1,3);
        assertEquals(3,dict.lookup(1));
    }

    @Test
    void lookupThrowsException() {
        IDictionary<Integer, Integer> dict = new MyDictionary<>();

        assertThrows(ADTException.class,() -> dict.lookup(1));
    }


    @Test
    void updateThrowsException() {
        IDictionary<Integer, Integer> dict = new MyDictionary<>();

        assertThrows(ADTException.class,() -> dict.update(1,2));
    }
}