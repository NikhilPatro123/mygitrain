FROM openjdk:8-jdk-alpine
MAINTAINER scr-sdd@stockxbid.com
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME

ADD Shadowfaxpincode.csv /tmp/
ADD target/stockxbid-email-0.0.1-SNAPSHOT.jar /app/stockxbid-email-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar /app/stockxbid-email-0.0.1-SNAPSHOT.jar
EXPOSE 8080
#main
