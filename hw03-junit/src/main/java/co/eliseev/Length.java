package co.eliseev;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

    int min() default 1;

    int max() default 10;

}
