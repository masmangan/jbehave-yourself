# jbehave-yourself [![Build Status](https://travis-ci.org/masmangan/jbehave-yourself.svg?branch=master)](https://travis-ci.org/masmangan/jbehave-yourself)

This code is a revision of a [JBehave] BDD example available from [Tim Dalton] at:

https://objectcomputing.com/resources/publications/sett/june-2010-jbehave-yourself

The original code is from 2010 and was no building anymore at this time (2019).
Code and Maven dependencies were updated to allow building with a more recent dependency configuration.

In order to better understand the example, some [Score Bowling] knowledge is necessary.

## Running the example
You need a JDK, git and maven on your local box in order to run the example. 

```
git clone https://github.com/masmangan/jbehave-yourself.git
cd jbehave-yourself
cd functionaljava-bowling
mvn test
```
Maven should take care of all dependencies and a scenario execution will be presented, ending with a BUILD SUCCESS message.

[Tim Dalton]: https://objectcomputing.com/resources/publications/sett/june-2010-jbehave-yourself
[JBehave]: https://jbehave.org/
[Score Bowling]: https://www.wikihow.com/Score-Bowling
