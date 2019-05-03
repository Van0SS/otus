package co.eliseev;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloOtusTest {


    @Test
    public void test_convertLineToInts() {

        String line = "1 2 5";
        int[] result = HelloOtus.convertLineToInts(line);
        assertThat(result.length, equalTo(3));
        assertThat(result[0], equalTo(1));
        assertThat(result[1], equalTo(2));
        assertThat(result[2], equalTo(5));

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_convertLineToInts_EmptyLine() {

        try {
            HelloOtus.convertLineToInts("");
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Your input line is empty. Please enter numbers with space delimiters and press ENTER"));
            throw expectedException;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_convertLineToInts_FailParse() {

        try {
            String input = "1 2 5a";
            HelloOtus.convertLineToInts(input);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Arguments must be Integer type, but was: 5a"));
            throw expectedException;
        }
    }

    @Test
    public void test_ConvertStringsToInts() {

        String[] strings = new String[]{"1", "2", "5"};
        int[] result = HelloOtus.convertStringsToInts(strings);
        assertThat(result.length, equalTo(3));
        assertThat(result[0], equalTo(1));
        assertThat(result[1], equalTo(2));
        assertThat(result[2], equalTo(5));

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_convertStringsToInts_FailParse() {

        String[] strings = new String[]{"1", "2", "5b"};
        try {
            HelloOtus.convertStringsToInts(strings);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Arguments must be Integer type, but was: 5b"));
            throw expectedException;
        }
    }

    @Test
    public void test_hasArguments() {
        assertThat(HelloOtus.hasArguments(new String[]{"a", ""}), equalTo(true));
        assertThat(HelloOtus.hasArguments(new String[]{}), equalTo(false));
        assertThat(HelloOtus.hasArguments(null), equalTo(false));
    }
}

