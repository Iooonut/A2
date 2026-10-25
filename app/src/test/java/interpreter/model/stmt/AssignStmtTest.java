package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IType;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
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
    public void varNotDeclared() {
        IExp number = new ValueExp(new IntValue(5));
        String name= "v";

        IStmt assign = new AssignStmt(name, number);
        var ex = assertThrows(StatementException.class, () -> assign.execute(state));
        assertEquals("Variable v is not declared yet", ex.getMessage());

    }

    @Test
    public void varTypeDiffers() throws MyException {
        IExp number = new ValueExp(new IntValue(5));
        IType type = new BoolType();
        String name= "v";

        IStmt create = new VarDeclStmt(name,type);
        IStmt assign = new AssignStmt(name, number);

        create.execute(state);

        var ex = assertThrows(StatementException.class, () -> assign.execute(state));
        assertEquals("Type of: v is different from declared type: bool" , ex.getMessage());
    }

    @Test
    public void assignValue() throws MyException {
        IExp number = new ValueExp(new IntValue(5));
        IType type = new IntType();
        String name= "v";

        IStmt create = new VarDeclStmt(name, type);
        IStmt assign = new AssignStmt(name, number);

        create.execute(state);
        assign.execute(state);
        assertEquals(number.eval(state.getSymTable()),state.getSymTable().lookup(name));

    }

}
