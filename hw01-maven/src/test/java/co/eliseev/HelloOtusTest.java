package co.eliseev;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class HelloOtusTest {


    @Test
    public void testConvertLineToInts_Ok() {

        String line = "1 2 5";
        int[] result = HelloOtus.convertLineToInts(line);
        assertThat(result.length, equalTo(3));
        assertThat(result[0], equalTo(1));
        assertThat(result[1], equalTo(2));
        assertThat(result[2], equalTo(5));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertLineToInts_Fail() {

        try {
            HelloOtus.convertLineToInts("");
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Your input line is empty. Please enter numbers with space delimiters and press ENTER"));
            throw expectedException;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertLineToInts_FailParse() {

        try {
            String input = "1 2 5a";
            HelloOtus.convertLineToInts(input);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Arguments must be Integer type, but was: 5a"));
            throw expectedException;
        }
    }

    @Test
    public void testGuavaIntMean() {

        int[] testData = new int[]{1, 2, 5};
        double result = HelloOtus.calculate(testData);
        assertThat(result, closeTo(2.6, 0.1));

    }

    @Test
    public void testConvertStringsToInts_Ok() {

        String[] strings = new String[]{"1", "2", "5"};
        int[] result = HelloOtus.convertStringsToInts(strings);
        assertThat(result.length, equalTo(3));
        assertThat(result[0], equalTo(1));
        assertThat(result[1], equalTo(2));
        assertThat(result[2], equalTo(5));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertStringsToInts_Fail() {

        String[] strings = new String[]{"1", "2", "5b"};
        try {
            HelloOtus.convertStringsToInts(strings);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Arguments must be Integer type, but was: 5b"));
            throw expectedException;
        }
    }
}

