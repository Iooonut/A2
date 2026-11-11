package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    private PrgState state;
    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    public void appendToOut() throws MyException {
        IExp num = new ValueExp(new IntValue(5));
        IStmt print = new PrintStmt(num);

        print.execute(state);
        assertEquals(1, state.getOut().size());
        assertEquals(num.eval(state.getSymTable()), state.getOut().get(0));
    }
}