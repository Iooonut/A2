package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;
    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    public void contIsNotBoolean() {
        IExp exp1 = new ValueExp(new IntValue(1));
        IExp exp2 = new ValueExp(new IntValue(2));
        IStmt first = new PrintStmt(exp1);
        IStmt second = new PrintStmt(exp2);

        IStmt ifStmt = new IfStmt(exp1, first, second);
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }

    @Test
    public void condIsTrue() throws MyException {
        IExp bool = new ValueExp(new BoolValue(true));
        IExp exp1 = new ValueExp(new IntValue(1));
        IExp exp2 = new ValueExp(new IntValue(2));
        IStmt first = new PrintStmt(exp1);
        IStmt second = new PrintStmt(exp2);

        IStmt ifStmt = new IfStmt(bool, first, second);
        ifStmt.execute(state);

        assertSame(first, state.getExeStack().pop());
    }


    @Test
    public void condIsTFalse() throws MyException {
        IExp bool = new ValueExp(new BoolValue(false));
        IExp exp1 = new ValueExp(new IntValue(1));
        IExp exp2 = new ValueExp(new IntValue(2));
        IStmt first = new PrintStmt(exp1);
        IStmt second = new PrintStmt(exp2);

        IStmt ifStmt = new IfStmt(bool, first, second);
        ifStmt.execute(state);

        assertSame(second, state.getExeStack().pop());
    }
}