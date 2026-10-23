package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {

    @Test
    void testUndefinedVariable() throws MyException, ExpressionException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp e = new VarExp("x");
        var ex = assertThrows(ExpressionException.class,() -> e.eval(symTable));
        assertEquals("Variable: x is not declared.", ex.getMessage());
    }

}