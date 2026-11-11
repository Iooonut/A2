package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    private void check(RelOp op, int a, int b, boolean excepted) throws MyException {
        IExp exp = new RelationalExp(op, num(a), num(b));
        assertEquals(new BoolValue(excepted), exp.eval(new MyDictionary<>()));
    }

    @Test
    void leesThan() throws MyException {
        check(RelOp.LT, 3, 5, true);
        check(RelOp.LT, 5, 3, false);
    }

    @Test
    void leesOrThan() throws MyException {
        check(RelOp.LE, 3, 3, true);
        check(RelOp.LE, 5, 3, false);
    }

    @Test
    void equalsOp() throws MyException {
        check(RelOp.EQ, 3, 3, true);
        check(RelOp.EQ, 3, 5, false);
    }

    @Test
    void notEqualsOp() throws MyException {
        check(RelOp.NE, 3, 5, true);
        check(RelOp.NE, 3, 3, false);
    }

    @Test
    void greaterThan() throws MyException {
        check(RelOp.GT, 5, 3, true);
        check(RelOp.GT, 3, 5, false);
    }

    @Test
    void greaterOrEquals() throws MyException {
        check(RelOp.GE, 5, 5, true);
        check(RelOp.GE, 3, 5, false);
    }

    @Test
    void nonIntegerLeftOperatorThrows() {
        IExp exp = new RelationalExp(RelOp.LT, new ValueExp(new BoolValue(true)), num(5));
        assertThrows(ExpressionException.class, () -> exp.eval(new MyDictionary<>()));
    }


    @Test
    void nonIntegerRightOperatorThrows() {
        IExp exp = new RelationalExp(RelOp.LT, num(5), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> exp.eval(new MyDictionary<>()));
    }
}