package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record PrintStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        var out = state.getOut();
        var e = exp.eval(symTable, heap);
        out.add(e);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }
}
