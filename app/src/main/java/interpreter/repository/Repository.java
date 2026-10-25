package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private final List<PrgState> programs;

    public Repository(PrgState state) {
        programs = new ArrayList<>();
        programs.add(state);
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(0);
    }
}
