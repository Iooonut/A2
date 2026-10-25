package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        state.getExeStack().pop();
    }

    @Test
    void declaresVariableWithDefaultValue() throws MyException {
        IStmt stmt = new VarDeclStmt("x", new IntType());

        stmt.execute(state);

        assertTrue(state.getSymTable().isDefined("x"));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void redeclaringVariableThrows() throws MyException {
        IStmt stmt = new VarDeclStmt("x", new IntType());

        stmt.execute(state);

        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}