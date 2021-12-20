FROM azul/zulu-openjdk:17.0.1 as builder
ENV TZ=Europe/Berlin
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract


# Run ture https://github.com/moby/moby/issues/37965
FROM azul/zulu-openjdk:17.0.1
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT exec java $JAVA_OPTS -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom org.springframework.boot.loader.JarLauncher
