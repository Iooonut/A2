package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(state, dir.resolve("log1.txt").toString()), false);
        controller.allStep();
        return state;
    }

    @Test
    void example1() throws MyException {
        PrgState state = run(Examples.ex1);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }
    @Test
    void example2() throws MyException {
        PrgState state = run(Examples.ex2);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }
    @Test
    void example3() throws MyException {
        PrgState state = run(Examples.ex3);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = PrgState.of(new NopStmt());

        Controller controller = new Controller(new Repository(state, dir.resolve("log1.txt").toString()), false);
        state.getExeStack().pop();

        var ex = assertThrows(StatementException.class, () -> controller.oneStep(state));
        assertEquals("program stack is empty",ex.getMessage());
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesFile() throws Exception {
        Path input = dir.resolve("numbers.in");
        Files.writeString(input, "15\n50\n");
        PrgState state = run(Examples.fileExample(input.toString()));

        assertEquals(2, state.getOut().size());
        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(50), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }


    @Test
    void fileExampleWithEmptyFilePrintsTwoZeros() throws Exception {
        Path input = dir.resolve("empty.in");
        Files.writeString(input, "");
        PrgState state = run(Examples.fileExample(input.toString()));

        assertEquals(2, state.getOut().size());
        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0),state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }


    @Test
    void relationalExamplePrintsFive() throws MyException {
        PrgState state = run(Examples.ex5);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }


}