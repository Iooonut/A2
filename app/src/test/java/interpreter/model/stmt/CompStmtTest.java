package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class CompStmtTest {
    private PrgState state;
    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    public void correctPushOrder() throws MyException {
        IStmt first = new PrintStmt(new ValueExp(new IntValue(5)));
        IStmt second = new NopStmt();

        IStmt comp = new CompStmt(first,second);
        comp.execute(state);

        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
    }
}