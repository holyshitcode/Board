FROM openjdk:11-jdk
ARG JAR_FILE="build/libs/JJBoard-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} app.jar
COPY ~/Pinpoint /pinpoint
EXPOSE 8080
ENTRYPOINT ["java", "-jar","-javaagent:./pinpoint/pinpoint-bootstrap-2.2.2.jar", "-Dpinpoint.agentId=adventcalendarDev","-Dpinpoint.applicationName=adventcalendar","-Dpinpoint.config=./pinpoint/pinpoint-root.config","-Dspring.profiles.active=${SERVER_MODE}","-Duser.timezone=Asia/Seoul", "/app.jar"]