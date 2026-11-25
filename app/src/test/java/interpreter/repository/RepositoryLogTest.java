package interpreter.repository;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepositoryLogTest {
    @TempDir
    Path dir;

    @Test
    void logContainsFourSectionsInOrder() throws Exception {

        PrgState state = PrgState.of(Examples.ex1);

        Path logPath = dir.resolve("log.txt");

        Repository repo = new Repository(
                state,
                logPath.toString()
        );

        Controller controller = new Controller(repo, false);
        controller.allStep();

        String log = Files.readString(logPath);

        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");

        assertTrue(
                0 <= a && a < b && b < c && c < d
        );
    }
}