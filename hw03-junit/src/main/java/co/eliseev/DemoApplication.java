package co.eliseev;

public class DemoApplication {
    public static void main(String[] args) throws IllegalAccessException {
        Person person = new Person();
        person.setName("Vadim");

        LengthValidator lengthValidator = new LengthValidator();
        lengthValidator.validate(person);
    }

}
