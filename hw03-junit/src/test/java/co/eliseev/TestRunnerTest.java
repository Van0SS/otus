package co.eliseev;

import co.eliseev.etest.TestRunner;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

public class TestRunnerTest {


    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        // TODO static methods
        Example testClassMock = Mockito.mock(Example.class);

        TestRunner.run(testClassMock.getClass());

    }
}
