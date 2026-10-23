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

class ArithExpTest {
    private final IDictionary<String, IValue>  symTable = new MyDictionary<>();

    private static IExp num(int n) { return new ValueExp(new IntValue(n));}

    @Test
    void addOperator() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, num(1), num(2));
        assertEquals(new IntValue(3), e.eval(symTable));
    }
    @Test
    void subOperator() throws MyException {
        IExp e = new ArithExp(ArithOp.SUB, num(1), num(2));
        assertEquals(new IntValue(-1), e.eval(symTable));
    }
    @Test
    void mulOperator() throws MyException {
        IExp e = new ArithExp(ArithOp.MUL, num(2), num(3));
        assertEquals(new IntValue(6), e.eval(symTable));
    }
    @Test
    void divOperator() throws MyException {
        IExp e = new ArithExp(ArithOp.DIV, num(6), num(2));
        assertEquals(new IntValue(3), e.eval(symTable));
    }

    @Test
    void divByZero() {
        IExp e = new ArithExp(ArithOp.DIV, num(2), num(0));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }

    @Test
    void nonIntOperand() {
        IExp e = new ArithExp(ArithOp.ADD, num(2), new ValueExp(new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }

}