package co.eliseev;

import java.lang.reflect.Field;

public class LengthValidator {

    public boolean validate(Object object) throws IllegalAccessException {
        System.out.println("Validating");
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();


        for (Field field : fields) {
            if(!field.isAnnotationPresent(Length.class)){
                continue;
            }
            field.setAccessible(true);

            System.out.println("Annotation @Length present");
            Length length = field.getAnnotation(Length.class);
            int min = length.min();
            int max = length.max();

            String name = (String) field.get(object);

            if (name.length() < min && name.length() > max) {
                System.err.println("Length is invalid " + name);
            }

            field.setAccessible(false );

        }


        return true;
    }
}
