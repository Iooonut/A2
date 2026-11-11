package interpreter.model.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    @Test
    void equalStringsHaveEqualValues() {

        StringValue first = new StringValue("test.in");
        StringValue second = new StringValue("test.in");

        assertEquals(first, second);
    }

    @Test
    void equalStringsHaveSameHashCode() {

        StringValue first = new StringValue("test.in");
        StringValue second = new StringValue("test.in");

        assertEquals(first.hashCode(), second.hashCode());
    }
}