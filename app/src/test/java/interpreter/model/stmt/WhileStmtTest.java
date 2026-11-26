package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    @TempDir
    Path dir;

    private PrgState runLoop(int initialValue) throws MyException {

        IStmt program = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(initialValue))),
                        new WhileStmt(new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))))));

        PrgState state = PrgState.of(program);
        Repository repo = new Repository(state, dir.resolve("log.txt").toString());

        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void loopExecutesZeroTimes() throws MyException {
        PrgState state = runLoop(0);

        assertEquals(0, state.getOut().size());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void loopExecutesOnce() throws MyException {
        PrgState state = runLoop(1);

        assertEquals(List.of(new IntValue(1)), state.getOut().toList());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void loopExecutesSeveralTimes() throws MyException {
        PrgState state = runLoop(3);

        assertEquals(List.of(new IntValue(3), new IntValue(2), new IntValue(1)), state.getOut().toList());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void nonBooleanConditionThrows() {
        PrgState state = PrgState.of(new NopStmt());
        IStmt loop = new WhileStmt(new ValueExp(new IntValue(5)), new NopStmt());

        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}