FROM openjdk

RUN mkdir /tmp/test

ADD Test.java /

RUN javac /Test.java
CMD java Test
