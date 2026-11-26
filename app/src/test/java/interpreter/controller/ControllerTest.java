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
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.List;
import java.util.Set;

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

    @Test
    void example6AllocatesReferences() throws MyException {
        PrgState state = run(Examples.ex6);

        assertEquals(List.of(new RefValue(1, new IntType()), new RefValue(2, new RefType(new IntType()))), state.getOut().toList());
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())), state.getHeap().getContent());
        assertEquals(Map.of("v", new RefValue(1, new IntType()), "a", new RefValue(2, new RefType(new IntType()))), state.getSymTable().toMap());
    }

    @Test
    void example7ReadsNestedReferences() throws MyException {
        PrgState state = run(Examples.ex7);

        assertEquals(List.of(new IntValue(20), new IntValue(25)), state.getOut().toList());
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())), state.getHeap().getContent());
        assertEquals(Map.of("v", new RefValue(1, new IntType()), "a", new RefValue(2, new RefType(new IntType()))), state.getSymTable().toMap());
    }

    @Test
    void example8WritesHeapValue() throws MyException {
        PrgState state = run(Examples.ex8);

        assertEquals(List.of(new IntValue(20), new IntValue(35)), state.getOut().toList());
        assertEquals(Map.of(1, new IntValue(30)), state.getHeap().getContent());
        assertEquals(Map.of("v", new RefValue(1, new IntType())), state.getSymTable().toMap());
    }

    @Test
    void example9PreservesIndirectReferences() throws MyException {
        PrgState state = run(Examples.ex9);

        assertEquals(List.of(new IntValue(20)), state.getOut().toList());
        assertEquals(Set.of(1, 2, 3), state.getHeap().getContent().keySet());
    }

    @Test
    void example10ExecutesWhileLoop() throws MyException {
        PrgState state = run(Examples.ex10);

        assertEquals(List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1), new IntValue(0)), state.getOut().toList());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
        assertTrue(state.getExeStack().isEmpty());
    }
}