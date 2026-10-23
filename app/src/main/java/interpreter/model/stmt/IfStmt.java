package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var e = cond.eval(symTable);
        if (!(e instanceof BoolValue(boolean val))) {
            throw new StatementException("cond" + cond + "is not a boolean");
        }
        if (val) {
            state.getExeStack().push(thenS);
        }else {
            state.getExeStack().push(elseS);
        }
        return state;
    }

    @Override
    public String toString() {
        return "if (" + cond + ") then (" + thenS + ") else (" + elseS + ")";
    }
}
