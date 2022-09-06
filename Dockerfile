FROM eclipse-temurin:18 as builder

ARG JAR_FILE=target/school-registration-system-*.jar
COPY ${JAR_FILE} application.jar

RUN java -Djarmode=layertools -jar application.jar extract
RUN ls -l

FROM eclipse-temurin:18

EXPOSE 8080

ENV TZ=America/New_York
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY --from=builder dependencies/ ./
COPY --from=builder spring-boot-loader/ ./

# This is a workaround to https://github.com/moby/moby/issues/37965
RUN true

COPY --from=builder snapshot-dependencies/ ./

# This is a workaround to https://github.com/moby/moby/issues/37965
RUN true

COPY --from=builder application/ ./

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=droplet", "org.springframework.boot.loader.JarLauncher"]
