# OTUS Homework Java Developer

Student:

Alexey Eliseev (Алексей Елисеев)

eliseev.co@gmail.com

___
## module hw01-maven
Simple console application using Google Guava to calculate  integers input mean

**Example**:

_First preparing application_ `mvn clean package` then:

-
    _Execute_:
    
    `java -jar hw01-maven/target/guava-mean.jar 1 2 5`
    
    _Output_:
    `Mean result is 2.666666666666667`

-
    _Execute:_
    
    `java -jar hw01-maven/target/guava-mean.jar 1 2 5a`
    
    _Output_:
    `Exception in thread "main" java.lang.IllegalArgumentException: Argument must be Integer, but was: 5a`

- OR USER TERMINAL INTERFACE

    _Execute without args:_
    
    `java -jar hw01-maven/target/guava-mean.jar`
    
    _Output_:
    
    `______________________________
     To calculate mean of integers please enter numbers with space delimiters and press ENTER.
     To quit enter 'q' and press ENTER
     ______________________________`
     
     _Then type:_
     `1 2 7`

    _Output_:
    `Mean result is 3.333333333333333`
    
    _Type 'q' and press ENTER to exit_


---