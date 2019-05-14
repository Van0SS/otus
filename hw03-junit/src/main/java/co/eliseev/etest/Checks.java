package co.eliseev.etest;


import java.util.Collection;

public class Checks {

    /*
        Start checking with 'checkThat'
     */

    public static CheckWrapper checkThat(Object object) {
        return new CheckWrapper(object);
    }

    public static CheckWrapper checkThat(Boolean aBoolean) {
        return new CheckWrapper(aBoolean);
    }

    public static CheckWrapper checkThat(Executable executable) {
        return new CheckWrapper(executable);
    }

    public static CollectionWrapper checkThat(Collection collection) {
        return new CollectionWrapper(collection);
    }

    public static class CheckWrapper {

        private Object target;
        private Boolean aBoolean;
        private Executable executable;


        CheckWrapper(Boolean aBoolean) {
            this.target = aBoolean;
            this.aBoolean = aBoolean;
        }

        CheckWrapper(Object object) {
            if (object instanceof Executable) {
                this.executable = (Executable) object;
            }
            target = object;
        }

        public void isNull() {
            if (target != null) {
                fail("Target is not null");
            }
        }


        public void isNotNull() {
            if (target == null) {
                fail("Target is null");
            }
        }

        public void equalTo(String actual) {
            if (!actual.equals(target)) {
                fail("Objects are not equals", target, actual);
            }
        }

        public void notEqualTo(String actual) {
            if (actual.equals(target)) {
                fail("Objects are equals");
            }
        }

        public void isTrue() {
            checkBoolean();

            if (!aBoolean) {
                fail("Is not true", true, false);
            }
        }

        public void isFalse() {
            checkBoolean();

            if (aBoolean) {
                fail("Is not false", false, true);
            }
        }

        private void checkBoolean() {
            if (target == null) {
                fail("Target is null");
            } else if (!(target instanceof Boolean)) { // FIXME
                fail("Target is not Boolean", "Boolean", target.getClass().getSimpleName());
            }

            if (aBoolean == null) {
                fail("Boolean is null");
            }
        }

        //TODO will not throw

        public <T extends Throwable> ExceptionWrapper willThrow(Class<T> expectedType) {

            try {
                executable.execute();
            } catch (Throwable throwable) {
                if (!expectedType.isInstance(throwable)) {
                    fail("Target operation throw another type of exception",
                            expectedType.getSimpleName(),
                            throwable.getClass().getSimpleName());
                }
                return new ExceptionWrapper(throwable);
            }
            fail("Target operation does not throw any exception",
                    expectedType.getSimpleName(),
                    null);
            return null; // FIXME fail will throw an Exception but compiler does not know about that
        }


        // TODO lessThan
        // TODO lessOrEqualsThan
        // TODO greaterThan
        // TODO greaterOrEqualsThan
        // TODO inRange
        // TODO notInRange
        // TODO closeTo
        // TODO notCloseTo
    }

    public static class ExceptionWrapper {
        private Throwable throwable;

        ExceptionWrapper(Throwable throwable) {
            this.throwable = throwable;
        }

        public void withMessage(String expectedMessage) {
            String actualMessage = throwable.getMessage();
            if (!expectedMessage.equals(actualMessage)) {
                fail("Another exception message was thrown", expectedMessage, actualMessage);
            }
        }
    }

    // TODO Support Arrays

    public static class CollectionWrapper {


        private Collection collection;

        public CollectionWrapper(Collection collection) {
            this.collection = collection;
        }

        public CollectionWrapper isEmpty() {
            if (!collection.isEmpty()) {
                fail("Collection is not empty");
            }
            return this;
        }

        public CollectionWrapper hasSize(int expectedSize) {
            int actualSize = collection.size();
            if (actualSize != expectedSize) {
                fail("Collection has different size", expectedSize, actualSize);
            }
            return this;
        }

        public void contains(Object object) {
            if (!collection.contains(object)) {
                fail("Collection does not contain '" + object + "'");
            }
        }
    }


    private static void fail(String message, Object expected, Object actual) {
        throw new CheckError(renderMessage(message, expected, actual));
    }

    private static void fail(String message) {
        throw new CheckError(message);

    }

    private static String renderMessage(String reason, Object expected, Object actual) {
        return reason + "\n" +
                "Expected :" + expected + "\n" +
                "Actual   :" + actual;
    }
}
