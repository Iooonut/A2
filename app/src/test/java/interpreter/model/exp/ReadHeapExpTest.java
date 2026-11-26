package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadHeapExpTest {

    private IDictionary<String, IValue> symTable;
    private IHeap heap;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
    }

    @Test
    void readsValueFromHeap() throws MyException {
        int address = heap.allocate(new IntValue(20));
        symTable.put("v", new RefValue(address, new IntType()));

        IExp exp = new ReadHeapExp(new VarExp("v"));

        assertEquals(new IntValue(20), exp.eval(symTable, heap));
    }

    @Test
    void readingNonReferenceThrows() {
        IExp exp = new ReadHeapExp(new ValueExp(new IntValue(20)));

        assertThrows(ExpressionException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void readingMissingAddressThrows() {
        symTable.put("v", new RefValue(0, new IntType()));
        IExp exp = new ReadHeapExp(new VarExp("v"));

        assertThrows(ExpressionException.class, () -> exp.eval(symTable, heap));
    }
}
