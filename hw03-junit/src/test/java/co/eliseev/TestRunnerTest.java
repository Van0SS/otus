package co.eliseev;

import co.eliseev.etest.TestRunner;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRunnerTest {


    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        List<String> expected = new ArrayList<>();
        expected.add("RUN STATIC order 0 beforeAll2");
        expected.add("RUN STATIC order 1 beforeAll");
        expected.add("RUN order 0 beforeEach2");
        //TODO finish

        TestRunner.run(Example.class);
        assertEquals(expected, Example.actions);
    }
}
