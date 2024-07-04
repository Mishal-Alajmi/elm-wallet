FROM bellsoft/liberica-runtime-container:jdk-17-glibc
VOLUME /app
ARG JAR_FILE=target/elm-wallet-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]