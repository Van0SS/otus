package co.eliseev;


import co.eliseev.etest.annotation.*;

import java.util.ArrayList;

import static co.eliseev.etest.Checks.checkThat;

public class Example {

    @BeforeAll(order = 1)
    public static void beforeAll() {
        System.out.println("RUN STATIC order 1 beforeAll");
    }

    @BeforeAll
    public static void beforeAll2() {
        System.out.println("RUN STATIC order 0 beforeAll2");
    }

    @Test
    public void test2() {
        System.out.println("RUN test2");
    }

    @BeforeEach
    public void beforeEach2() {
        System.out.println("RUN order 0 beforeEach2");
    }

    @AfterEach(order = 1)
    public void afterEach() {
        System.out.println("RUN order 1 afterEach");
    }

    @AfterEach
    public void afterEach2() {
        System.out.println("RUN order 0 afterEach2");
    }

    @Test(strings = {"a", "b"})
    public void testString(String s) {
        System.out.println("RUN testString " + s);
        checkThat("a").equalTo("a");
    }

    @Test(ints = {1, 2})
    public void testInt(int i) {
        System.out.println("RUN testInt " + i);
        checkThat(new ArrayList<>()).isEmpty();
    }


    @AfterAll(order = 1)
    public static void afterAll() {
        System.out.println("RUN STATIC order 1 afterAll");
    }

    @AfterAll
    public static void afterAll2() {
        System.out.println("RUN STATIC order 0 afterAll2");
    }

    @BeforeEach(order = 1)
    public void beforeEach() {
        System.out.println("RUN order 1 beforeEach");
    }

}
