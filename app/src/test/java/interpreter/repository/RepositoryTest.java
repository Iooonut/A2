package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    @Test
    public void returnCorrectPrgState() {

        PrgState state = PrgState.of(new NopStmt());
        IRepository repo = new Repository(state);

        assertSame(state, repo.getCrtPrg());

    }

}