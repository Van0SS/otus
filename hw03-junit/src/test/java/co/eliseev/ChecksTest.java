package co.eliseev;

import co.eliseev.etest.CheckError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static co.eliseev.etest.Checks.checkThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Check by JUnit 5 that custom asserts works fine
 */
class ChecksTest {


    @Test
    void test_isNull() {

        assertDoesNotThrow(() ->
                checkThat((String) null).isNull());


        // FIXME try supplier
        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat("").isNull());
        assertEquals("Target is not null", exception.getMessage()); // FIXME
    }


    @Test
    void test_isNotNull() {
        assertDoesNotThrow(() ->
                checkThat(new Object()).isNotNull());

        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat((String) null).isNotNull());
        assertEquals("Target is null", exception.getMessage());
    }

    @Test
    void test_equalTo() {
        assertDoesNotThrow(() ->
                checkThat("").equalTo(""));

        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat((String) null).equalTo(""));
        assertEquals("Objects are not equals\n" +
                "Expected :null\n" +
                "Actual   :", exception.getMessage());
    }

    @Test
    void test_notEqualTo() {
        assertDoesNotThrow(() ->
                checkThat("a").notEqualTo("b"));

        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat("").notEqualTo(""));
        assertEquals("Objects are equals", exception.getMessage());
    }

    @Test
    void test_isTrue() {

        assertDoesNotThrow(() ->
                checkThat(true).isTrue());

        assertDoesNotThrow(() ->
                checkThat(Boolean.TRUE).isTrue());

        CheckError exception1 = assertThrows(CheckError.class, () ->
                checkThat(new Object()).isTrue());
        assertEquals("Target is not Boolean\n" +
                "Expected :Boolean\n" +
                "Actual   :Object", exception1.getMessage());

        CheckError exception2 = assertThrows(CheckError.class, () ->
                checkThat(false).isTrue());
        assertEquals("Is not true\n" +
                "Expected :true\n" +
                "Actual   :false", exception2.getMessage());

        CheckError exception3 = assertThrows(CheckError.class, () ->
                checkThat(Boolean.FALSE).isTrue());
        assertEquals("Is not true\n" +
                "Expected :true\n" +
                "Actual   :false", exception3.getMessage());

        CheckError exception4 = assertThrows(CheckError.class, () ->
                checkThat((Boolean) null).isTrue());
        assertEquals("Target is null", exception4.getMessage());

    }


    @Test
    void test_isFalse() {

        assertDoesNotThrow(() ->
                checkThat(false).isFalse());

        assertDoesNotThrow(() ->
                checkThat(Boolean.FALSE).isFalse());

        CheckError exception1 = assertThrows(CheckError.class, () ->
                checkThat(new Object()).isFalse());
        assertEquals("Target is not Boolean\n" +
                "Expected :Boolean\n" +
                "Actual   :Object", exception1.getMessage());

        CheckError exception2 = assertThrows(CheckError.class, () ->
                checkThat(true).isFalse());
        assertEquals("Is not false\n" +
                "Expected :false\n" +
                "Actual   :true", exception2.getMessage());

        CheckError exception3 = assertThrows(CheckError.class, () ->
                checkThat(Boolean.TRUE).isFalse());
        assertEquals("Is not false\n" +
                "Expected :false\n" +
                "Actual   :true", exception3.getMessage());

        CheckError exception4 = assertThrows(CheckError.class, () ->
                checkThat((Boolean) null).isFalse());
        assertEquals("Target is null", exception4.getMessage());

    }

    private void methodWithException() {
        throw new CheckError("UPS");
    }

    private void methodWithoutException() {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
    }

    @Test
    void test_willThrow() {

        // OK
        assertDoesNotThrow(() ->
                checkThat(() ->
                        methodWithException())
                        .willThrow(CheckError.class)
                        .withMessage("UPS"));


        // Different message
        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat(() ->
                        methodWithException())
                        .willThrow(CheckError.class)
                        .withMessage("UPS a"));
        assertEquals("Another exception message was thrown\n" +
                "Expected :UPS a\n" +
                "Actual   :UPS", exception.getMessage());


        // Different Exception class
        CheckError exception2 = assertThrows(CheckError.class, () ->
                checkThat(() ->
                        methodWithException())
                        .willThrow(NullPointerException.class)
                        .withMessage("UPS a"));
        assertEquals("Target operation throw another type of exception\n" +
                "Expected :NullPointerException\n" +
                "Actual   :CheckError", exception2.getMessage());


        // Custom checker expects that method throw Exception but it does not
        CheckError exception1 = assertThrows(CheckError.class, () ->
                checkThat(() ->
                        methodWithoutException()).willThrow(CheckError.class).withMessage("UPS"));
        assertEquals("Target operation does not throw any exception\n" +
                "Expected :CheckError\n" +
                "Actual   :null", exception1.getMessage());
    }

    @Test
    void test_hasSize() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");

        assertDoesNotThrow(() ->
                checkThat(strings).hasSize(3)
        );

        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat(strings).hasSize(4));
        assertEquals("Collection has different size\n" +
                "Expected :4\n" +
                "Actual   :3", exception.getMessage());

    }


    @Test
    void test_isEmpty() {
        ArrayList<String> strings = new ArrayList<>();
        Executable checkThatIsEmpty = () ->
                checkThat(strings).isEmpty();

        assertDoesNotThrow(checkThatIsEmpty);

        strings.add("a");
        CheckError exception = assertThrows(CheckError.class, checkThatIsEmpty);
        assertEquals("Collection is not empty", exception.getMessage());

    }

    @Test
    void test_contains() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");

        assertDoesNotThrow(() ->
                checkThat(strings).contains("a"));

        CheckError exception = assertThrows(CheckError.class, () ->
                checkThat(strings).contains("z"));
        assertEquals("Collection does not contain 'z'", exception.getMessage());
    }
}