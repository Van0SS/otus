package co.eliseev;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloOtusIntegrationTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;

    /*
        The tests uses overriding standard streams.
     */
    @Before
    public void setUpStreams() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;
    }

    @After
    public void cleanUpStreams() {
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
    public void testAppWithArgs_Ok() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        HelloOtus.main(new String[]{"1", "2", "5"});

        assertThat(out.toString(), equalTo("Mean result is 2.666666666666667\n"));
        assertThat(err.toString(), equalTo(""));

    }

    @Test
    public void testAppWithArgs_Error() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        HelloOtus.main(new String[]{"1", "2a", "5"});

        assertThat(out.toString(), equalTo(""));
        assertThat(err.toString(), equalTo("Arguments must be Integer type, but was: 2a\n"));

    }

    @Test
    public void testAppWithScanner_Ok() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();

        input("1 2 5");
        HelloOtus.main(null);

        assertThat(out.toString(), equalTo(
                "______________________________\n" +
                        "To calculate mean of integers please enter numbers with space delimiters and press ENTER.\n" +
                        "To quit enter 'q' and press ENTER\n" +
                        "______________________________\n" +
                        "Mean result is 2.666666666666667\n"));
        assertThat(err.toString(), equalTo(""));

    }

    @Test
    public void testAppWithScanner_Error() {

        ByteArrayOutputStream out = newOut();
        ByteArrayOutputStream err = newErr();


        input("1 2 5a");
        HelloOtus.main(null);

        assertThat(out.toString(), equalTo(
                "______________________________\n" +
                        "To calculate mean of integers please enter numbers with space delimiters and press ENTER.\n" +
                        "To quit enter 'q' and press ENTER\n" +
                        "______________________________\n"));

        assertThat(err.toString(), equalTo("Arguments must be Integer type, but was: 5a\n"));
    }

}
