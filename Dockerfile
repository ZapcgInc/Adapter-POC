FROM openjdk:8
RUN \
  curl -L -o sbt-0.13.9.deb http://dl.bintray.com/sbt/debian/sbt-1.1.2.deb && \
  dpkg -i sbt-1.1.2.deb && \
  rm sbt-1.1.2.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion
WORKDIR /Adapter-POC-master
ADD . /Adapter-POC-master
