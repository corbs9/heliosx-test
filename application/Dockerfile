FROM eclipse-temurin:21.0.5_11-jre-alpine
RUN addgroup -S heliosx && adduser -S heliosx -G heliosx
USER heliosx:heliosx
COPY application.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
