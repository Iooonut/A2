package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    @Test
    void addAndGet() throws ADTException {
        IList<Integer> list = new MyList<>();

        list.add(1);
        list.add(2);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void sizeIsCorrect() {
        IList<Integer> list = new MyList<>();

        list.add(1);
        list.add(2);

        assertEquals(2,list.size());
    }

    @Test
    void invalidIndexGet() {
        IList<Integer> list = new MyList<>();

        assertThrows(ADTException.class, () -> list.get(1));
    }

    @Test
    void toListPreservesInsertionOrder() {
        IList<Integer> list = new MyList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(
                List.of(10, 20, 30),
                list.toList()
        );
    }
}