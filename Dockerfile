FROM tomcat:9.0.70-jdk17-corretto-al2

COPY target/RandomNumberClientServerApp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh","run"]