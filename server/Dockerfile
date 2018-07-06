FROM openjdk:8-jre

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/film-vote/film-vote-exec.jar"]

ARG JAR_FILE

ADD ${JAR_FILE} /usr/share/film-vote/film-vote-exec.jar
