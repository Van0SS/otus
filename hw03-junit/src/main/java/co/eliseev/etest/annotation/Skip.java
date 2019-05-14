package co.eliseev.etest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Skip {

    String reason() default "Life is too short to spend time explaining";
}
