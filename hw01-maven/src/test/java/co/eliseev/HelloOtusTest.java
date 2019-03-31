package co.eliseev;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class HelloOtusTest {


    @Test
    public void testConvertStringsToInts() {

        String[] testData = new String[]{"1", "2", "5"};
        int[] result = HelloOtus.convertStringsToInts(testData);
        assertThat(result.length, equalTo(3));
        assertThat(result[0], equalTo(1));
        assertThat(result[1], equalTo(2));
        assertThat(result[2], equalTo(5));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailConvertStringsToInts() {

        try {
            HelloOtus.convertStringsToInts(null);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Please enter args to calculate guavaMean"));
            throw expectedException;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgsConvertingStringsToInts() {

        try {
            String[] testData = new String[]{"1", "2", "5a"};
            HelloOtus.convertStringsToInts(testData);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Argument must be Integer, but input was: 5a"));
            throw expectedException;
        }
    }

    @Test
    public void testGuavaIntMean() {

        int[] testData = new int[]{1, 2, 5};
        double result = HelloOtus.guavaMean(testData);
        assertThat(result, closeTo(2.6, 0.1));

    }
}

