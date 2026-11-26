package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;

public record WhileStmt(IExp condition, IStmt body) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(condition.eval(state.getSymTable(), state.getHeap()) instanceof BoolValue(boolean result))) {
            throw new StatementException("while: condition " + condition + " is not a boolean");
        }

        if(result) {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(while (" + condition + ") " + body + ")";
    }
}
