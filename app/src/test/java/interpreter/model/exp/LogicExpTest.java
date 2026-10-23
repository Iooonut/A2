package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp bool(boolean b) { return new ValueExp(new BoolValue(b));}

    @Test
    void andOperand() throws MyException, ExpressionException {
        IExp e = new LogicExp(LogicOp.AND, bool(true), bool(true));
        assertEquals(new BoolValue(true), e.eval(symTable));
    }
    @Test
    void orOperand() throws MyException, ExpressionException {
        IExp e = new LogicExp(LogicOp.OR, bool(true), bool(false));
        assertEquals(new BoolValue(true), e.eval(symTable));
    }
    @Test
    void nonBoolOperand() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), bool(true));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("first operand is not an boolean", ex.getMessage());
    }
}