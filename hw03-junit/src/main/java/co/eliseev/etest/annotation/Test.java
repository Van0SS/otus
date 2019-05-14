package co.eliseev.etest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    byte[] bytes() default {};

    short[] shorts() default {};

    int[] ints() default {};

    long[] longs() default {};

    float[] floats() default {};

    double[] doubles() default {};

    char[] chars() default {};

    boolean[] booleans() default {};

    String[] strings() default {};

    Class<?>[] classes() default {};
}
