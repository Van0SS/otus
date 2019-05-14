package co.eliseev;


import co.eliseev.etest.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static co.eliseev.etest.Checks.checkThat;

public class Example {

    public static List<String> actions = new ArrayList<>();

    @BeforeAll(order = 1)
    public static void beforeAll() {
        logAction("RUN STATIC order 1 beforeAll");
    }

    @BeforeAll
    public static void beforeAll2() {
        logAction("RUN STATIC order 0 beforeAll2");
    }

    @Test
    public void test2() {
        logAction("RUN test2");
    }

    @BeforeEach
    public void beforeEach2() {
        logAction("RUN order 0 beforeEach2");
    }

    @AfterEach(order = 1)
    public void afterEach() {
        logAction("RUN order 1 afterEach");
    }

    @AfterEach
    public void afterEach2() {
        logAction("RUN order 0 afterEach2");
    }

    @Test(strings = {"a", "b"})
    public void testString(String s) {
        logAction("RUN testString " + s);
        checkThat("a").equalTo("a");
    }

    @Test(ints = {1, 2})
    public void testInt(int i) {
        logAction("RUN testInt " + i);
        checkThat(new ArrayList<>()).isEmpty();
    }


    @AfterAll(order = 1)
    public static void afterAll() {
        logAction("RUN STATIC order 1 afterAll");
    }

    @AfterAll
    public static void afterAll2() {
        logAction("RUN STATIC order 0 afterAll2");
    }

    @BeforeEach(order = 1)
    public void beforeEach() {
        logAction("RUN order 1 beforeEach");
    }

    private static void logAction(String action) {
        System.out.println(action);
        actions.add(action);
    }
}
