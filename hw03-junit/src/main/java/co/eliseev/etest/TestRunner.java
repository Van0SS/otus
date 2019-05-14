package co.eliseev.etest;

import co.eliseev.etest.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static co.eliseev.etest.CommonUtils.wrap;

public class TestRunner {

    private static Map<Integer, Method> beforeAllMethods = new TreeMap<>();
    private static Map<Integer, Method> afterAllMethods = new TreeMap<>();
    private static Map<Integer, Method> beforeEachMethods = new TreeMap<>();
    private static Map<Integer, Method> afterEachMethods = new TreeMap<>();
    private static List<Method> testList = new ArrayList<>();

    private static Constructor constructor;

    public static void run(Class testClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {


        constructor = testClass.getConstructor();

        Method[] methods = testClass.getDeclaredMethods();

        /*
            Get all annotations
         */
        readAnnotations(methods);


        /*
            BEFORE ALL
         */
        for (Map.Entry<Integer, Method> entry : beforeAllMethods.entrySet()) {
            entry.getValue().invoke(null);
        }

        for (Method method : testList) {


            // TODO read annotation
            Test annotation = method.getAnnotation(Test.class);
            // TODO check parameters

            byte[] bytes = annotation.bytes();
            short[] shorts = annotation.shorts();
            int[] ints = annotation.ints();
            long[] longs = annotation.longs();
            float[] floats = annotation.floats();
            double[] doubles = annotation.doubles();
            char[] chars = annotation.chars();
            boolean[] booleans = annotation.booleans();

            String[] strings = annotation.strings();
            Class<?>[] classes = annotation.classes();

            // TODO simplify?
            if (bytes.length > 0) {
                validate(method, "byte");
                runArray(method, wrap(bytes));
            } else if (shorts.length > 0) {
                validate(method, "short");
                runArray(method, wrap(shorts));
            } else if (ints.length > 0) {
                validate(method, "int");
                runArray(method, wrap(ints));
            } else if (longs.length > 0) {
                validate(method, "long");
                runArray(method, wrap(longs));
            } else if (floats.length > 0) {
                validate(method, "float");
                runArray(method, wrap(floats));
            } else if (doubles.length > 0) {
                validate(method, "double");
                runArray(method, wrap(doubles));
            } else if (chars.length > 0) {
                validate(method, "char");
                runArray(method, wrap(chars));
            } else if (booleans.length > 0) {
                validate(method, "boolean");
                runArray(method, wrap(booleans));
            } else if (strings.length > 0) {
                validate(method, "String");
                runArray(method, strings);
            } else if (classes.length > 0) {
                throw new UnsupportedOperationException("classes");
            } else {
                // Run simple @Test without input arguments and without args in method signature
                validate(method);
                run(method);
            }
        }

        // TODO
        invoke(afterAllMethods);
    }

    private static void validate(Method method) {
        if(method.getParameterCount() != 0){
            throw new RuntimeException("Methods with simple @Test annotation must not have any arguments, but '" + method.getName() + "' method has " + method.getParameterCount());
        }
    }

    private static void validate(Method method, String type) {
        if (method.getParameterCount() == 0) {
            throw new RuntimeException(type + " arguments in method '" + method.getName() + "' not found.\n" +
                    "When use @Test with " + type + " parameters\n" +
                    "you must add String argument in method signature");
        } else if (method.getParameterCount() > 1) {
            throw new RuntimeException("To many arguments in method '" + method.getName() + "'\n" + type + " argument must be single in method signature");
        } else {
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                if (!parameterType.getSimpleName().equals(type)) {
                    throw new RuntimeException("If you set in @Test " + type + " params you must create one " + type + " argument in method");
                }
            }
        }

    }


    private static void run(Method method) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        run(method, null);
    }

    private static <T> void run(Method method, T arg) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        invoke(beforeEachMethods);
        if (arg != null) {
            method.invoke(constructor.newInstance(), arg);
        } else {
            method.invoke(constructor.newInstance());
        }
        invoke(afterEachMethods);
    }

    private static <T> void runArray(Method method, T[] array) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        for (T element : array) {
            run(method, element);
        }
    }

    private static void readAnnotations(Method[] methods) {
        for (Method method : methods) {
            /*
                Skip ignored method
             */
            if (method.getAnnotation(Skip.class) != null) {
                System.out.println("IGNORE");
                continue;
            }

            if (method.getAnnotation(BeforeAll.class) != null) {
                requiredStatic(method);

                int order = method.getAnnotation(BeforeAll.class).order();
                checkOrder(order, beforeAllMethods, method);

                beforeAllMethods.put(order, method);
            }


            if (method.getAnnotation(AfterAll.class) != null) {
                requiredStatic(method);

                int order = method.getAnnotation(AfterAll.class).order();
                checkOrder(order, afterAllMethods, method);
                afterAllMethods.put(order, method);
            }

            if (method.getAnnotation(BeforeEach.class) != null) {

                int order = method.getAnnotation(BeforeEach.class).order();
                checkOrder(order, beforeEachMethods, method);

                beforeEachMethods.put(order, method);
            }

            if (method.getAnnotation(AfterEach.class) != null) {

                int order = method.getAnnotation(AfterEach.class).order();
                checkOrder(order, afterEachMethods, method);

                afterEachMethods.put(order, method);
            }

            if (method.getAnnotation(Test.class) != null) {

                // TODO read parameters
                testList.add(method);
            }

        }

        System.out.println("beforeAllMap size " + beforeAllMethods.size());
        System.out.println("afterAllMethods size " + afterAllMethods.size());
        System.out.println("beforeEachMethods size " + beforeEachMethods.size());
        System.out.println("afterEachMethods size " + afterEachMethods.size());
        System.out.println("testList size " + testList.size());
        System.out.println();
    }

    private static void invoke(Map<Integer, Method> methods) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        for (Method method : methods.values()) {
            method.invoke(constructor.newInstance());
        }
    }

    private static void requiredStatic(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            // TODO specify annotation
            throw new RuntimeException("Method " + method.getName() + " with @BeforeAll/@AfterAll annotation must be static"); // TODO parameters
        }
    }


    private static void checkOrder(int order, Map<Integer, Method> methods, Method method) {

        if (methods.containsKey(order)) {

            Method method1 = methods.get(order);

            String annotation = method.getAnnotations()[0].annotationType().getSimpleName(); // TODO
            throw new RuntimeException("Duplicate order!\n" +
                    "Order " + order + " were in two methods: " + method.getName() + ", " + method1.getName() + "." +
                    "\nSpecify one of this method as example: @" + annotation + "(order = " + (order + 1) + ")");
        }
    }
}