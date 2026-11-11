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

        // Create the program state for Example 1
        PrgState state = PrgState.of(Examples.ex1);

        // Create a log file inside the temporary directory
        Path logPath = dir.resolve("log.txt");

        Repository repo = new Repository(
                state,
                logPath.toString()
        );

        // Run the program without console state displays
        Controller controller = new Controller(repo, false);
        controller.allStep();

        // Read the generated log file
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