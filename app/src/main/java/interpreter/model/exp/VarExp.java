package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String name) implements IExp{
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if(!(symTable.isDefined(name)))
            throw new ExpressionException("Variable: " + name + " is not declared.");

        try{
            return symTable.lookup(name);
        } catch (ADTException e) {
            throw new ExpressionException("Variable: " + name + " is not declared.");
        }
    }
}
