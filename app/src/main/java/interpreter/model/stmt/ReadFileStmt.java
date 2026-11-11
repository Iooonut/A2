package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        if (!symTable.isDefined(varName)) {
            throw new StatementException("readFile: variable " + varName + "is not declared");
        }

        if (!symTable.lookup(varName).getType().equals(new IntType())) {
            throw new StatementException("readFile: variable " + varName + "is not an integer");
        }

        if(!(exp.eval(symTable) instanceof StringValue fileName)) {
            throw new StatementException("readFile: " + exp + "is not a string");
        }

        if(!fileTable.isDefined(fileName)) {
            throw new StatementException("readFile: " + fileName + "is not opened");
        }


        try {
            String line = fileTable.lookup(fileName).readLine();
            int number;

            if (line == null) {
                number = 0;
            } else {
                number = Integer.parseInt(line.strip());
            }

            symTable.update(varName, new IntValue(number));
        } catch (IOException | NumberFormatException e) {
            throw new StatementException("readFile: cannot read an integer from " + fileName + ": " + e.getMessage());
        }
        return state;
    }
    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
