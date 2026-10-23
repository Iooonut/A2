package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if(!(symTable.isDefined(id))) {
            throw new StatementException("Variable " + id + " is not declared yet");
        }
        IValue val = exp.eval(symTable);
        if(!(val.getType().equals(symTable.lookup(id).getType()))) {
            throw new StatementException("Type of: " + id + "is different from declared type:" + symTable.lookup(id).getType());
        }
        symTable.update(id,val);
        return state;
    }

    @Override
    public String toString() { return id + " = " + exp; }
}
