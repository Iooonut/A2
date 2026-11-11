package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class fileStmtTest {
    @TempDir
    Path dir;

    private PrgState state;
    private Path filePath;
    private IExp file;

    @BeforeEach
    void setUp() throws Exception {
        filePath = dir.resolve("numbers.in");
        Files.writeString(filePath, "7\n15\n");
        file = new ValueExp(new StringValue(filePath.toString()));
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @AfterEach
    void closeRemainingReaders() throws IOException {
        if (state != null) {
            for ( var reader : state.getFileTable().toMap().values()) {
                reader.close();
            }
        }
    }

    @Test
    void openingFileAddsItToFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertTrue(state.getFileTable().isDefined(new StringValue(filePath.toString())));
    }

    @Test
    void openingNonStringThrows() {
        IExp invalid = new ValueExp(new IntValue(5));

        assertThrows(StatementException.class,() -> new OpenRFileStmt(invalid).execute(state));
    }

    @Test
    void openingSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
    }

    @Test
    void openingMissingFileThrows() {
        IExp missing = new ValueExp(
                new StringValue(dir.resolve("missing.in").toString())
        );

        assertThrows(StatementException.class, () -> new OpenRFileStmt(missing).execute(state));
    }

    @Test
    void readingNumbersInOrderAndThenZero() throws MyException {
        new OpenRFileStmt(file).execute(state);

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(15),  state.getSymTable().lookup("x"));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));

        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void readingIntoUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "unknown").execute(state));
    }

    @Test
    void readingIntoNonIntegerVariableThrows() throws MyException {
        new VarDeclStmt("b", new BoolType()).execute(state);
        new OpenRFileStmt(file).execute(state);

        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "b").execute(state));
    }

    @Test
    void readingUnopenedFileThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void readingNonIntegerLineThrows() throws Exception {
        Path badFile = dir.resolve("bad.in");
        Files.writeString(badFile, "hello\n");

        IExp bad = new ValueExp(new StringValue(badFile.toString()));

        new OpenRFileStmt(bad).execute(state);

        assertThrows(StatementException.class, () -> new ReadFileStmt(bad, "x").execute(state));
    }

    @Test
    void closingFileRemovesItFromFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);

        assertFalse(state.getFileTable().isDefined(new StringValue(filePath.toString())));
    }

    @Test
    void closingUnopenedFileThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
    }

    @Test
    void readingNonStringFilenameThrows() {
        IExp invalid = new ValueExp(new IntValue(5));

        assertThrows(StatementException.class, () -> new ReadFileStmt(invalid, "x").execute(state));
    }

    @Test
    void closingNonStringFilenameThrows() {
        IExp invalid = new ValueExp(new IntValue(5));

        assertThrows(StatementException.class, () -> new CloseRFileStmt(invalid).execute(state));
    }
}
