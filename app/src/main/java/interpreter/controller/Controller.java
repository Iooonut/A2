package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        this.displayFlag = displayFlag;
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty()) {
            throw new StatementException("program stack is empty");
        }
        IStmt crsStmt = stack.pop();
        return crsStmt.execute(state);
    }


    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();

        display(prg);

        while(!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            display(prg);
        }
    }

    private void display(PrgState state) {
        if (displayFlag) {
            System.out.println(state);
        }
    }
}
