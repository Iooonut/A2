package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RefTypeTest {

    @Test
    void referenceVariableGetsDefaultValue() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);

        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    void equalNestedReferenceTypes() {
        IType first = new RefType(new RefType(new IntType()));
        IType second = new RefType(new RefType(new IntType()));

        assertEquals(first, second);
    }

    @Test
    void differentReferenceTypesAreNotEqual() {
        IType first = new RefType(new RefType(new IntType()));
        IType second = new RefType(new IntType());

        assertNotEquals(first, second);
    }

}
