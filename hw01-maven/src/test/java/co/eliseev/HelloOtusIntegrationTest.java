package co.eliseev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloOtusIntegrationTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;

    /*
        The tests uses overriding standard streams.
     */
    @BeforeEach
    void setUpStreams() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;
    }

    @AfterEach
    void cleanUpStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private void input(String input) {
        //  Type input and then exit
        System.setIn(new ByteArrayInputStream((input + "\nq\n").getBytes()));
    }

    private ByteArrayOutputStream newOut() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
    }

    private ByteArrayOutputStream newErr() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
        return outContent;
    }

    @Test
    void testAppWithArgs() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        HelloOtus.main(new String[]{"1", "2", "5"});

        assertEquals("Mean result is 2.666666666666667\n", out.toString());
        assertEquals("", err.toString());

    }

    @Test
    void testAppWithArgs_Error() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        HelloOtus.main(new String[]{"1", "2a", "5"});

        assertEquals("", out.toString());
        assertEquals("Arguments must be Integer type, but was: 2a\n", err.toString());

    }

    @Test
    void testAppWithScanner() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        input("1 2 5");
        HelloOtus.main(null);

        assertEquals("______________________________\n" +
                "To calculate mean of integers please enter numbers with space delimiters and press ENTER.\n" +
                "To quit enter 'q' and press ENTER\n" +
                "______________________________\n" +
                "Mean result is 2.666666666666667\n", out.toString());
        assertEquals("", err.toString());

    }

    @Test
    void testAppWithScanner_Error() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();


        input("1 2 5a");
        HelloOtus.main(null);

        assertEquals("______________________________\n" +
                "To calculate mean of integers please enter numbers with space delimiters and press ENTER.\n" +
                "To quit enter 'q' and press ENTER\n" +
                "______________________________\n", out.toString());

        assertEquals("Arguments must be Integer type, but was: 5a\n", err.toString());
    }

    @Test
    void test_showHelp() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        HelloOtus.showHelp();

        assertEquals("______________________________\n" +
                "To calculate mean of integers please enter numbers with space delimiters and press ENTER.\n" +
                "To quit enter 'q' and press ENTER\n" +
                "______________________________\n", out.toString());
        assertEquals("", err.toString());
    }
}
