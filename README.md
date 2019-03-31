# OTUS Homework Java Developer

## module hw01-maven
Simple console application using Google Guava to calculate  integers input mean
___

**Example**:

_Preparing application_ `mvn clean package` then:

_Execute_:
`java -jar hw01-maven/target/guava-mean.jar 1 2 5`

_Output_:
`Mean result is 2.666666666666667`

-
_Execute:_
`java -jar hw01-maven/target/guava-mean.jar`

_Output_:
`Exception in thread "main" java.lang.IllegalArgumentException: Please enter args to calculate guavaMean`

-
_Execute:_
`java -jar hw01-maven/target/guava-mean.jar 1 2 5a`

_Output_:
`Exception in thread "main" java.lang.IllegalArgumentException: Argument must be Integer, but input was: 5a`

---