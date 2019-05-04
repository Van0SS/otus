package co.eliseev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloOtusTest {


    @Test
    void test_convertLineToInts() {

        String line = "1 2 5";
        int[] result = HelloOtus.convertLineToInts(line);
        assertEquals(3, result.length);
        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
        assertEquals(5, result[2]);

    }

    @Test
    void test_convertLineToInts_EmptyLine() {
        assertThrows(IllegalArgumentException.class, () ->
                        HelloOtus.convertLineToInts(""),
                "Your input line is empty. Please enter numbers with space delimiters and press ENTER");
    }

    @Test
    void test_convertLineToInts_FailParse() {
        assertThrows(IllegalArgumentException.class, () ->
                        HelloOtus.convertLineToInts("1 2 5a"),
                "Arguments must be Integer type, but was: 5a");
    }

    @Test
    void test_ConvertStringsToInts() {
        String[] strings = new String[]{"1", "2", "5"};
        int[] result = HelloOtus.convertStringsToInts(strings);
        assertEquals(3, result.length);
        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
        assertEquals(5, result[2]);

    }

    @Test
    void test_convertStringsToInts_FailParse() {
        assertThrows(IllegalArgumentException.class, () ->
                        HelloOtus.convertStringsToInts(new String[]{"1", "2", "5b"}),
                "Arguments must be Integer type, but was: 5b");
    }

    @Test
    void test_hasArguments() {
        assertTrue(HelloOtus.hasArguments(new String[]{"a", ""}));
        assertFalse(HelloOtus.hasArguments(new String[]{}));
        assertFalse(HelloOtus.hasArguments(null));
    }
}

