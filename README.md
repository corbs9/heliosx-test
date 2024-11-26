## About the Application
- Developed with Java 21
- Latest version of Spring boot
- Followed hexagonal architecture as much as possible
- Developed utilising Java modules to ensure that a modular design is enforced with no cyclic dependencies
- Using Gradle as the build tool
- Open API 3.0 with Swagger
- Analysing of a consultation is done via a [QuestionRule](consultation/src/main/java/org/heliosx/consultation/domain/model/rules/QuestionRule.java)
and is extendable for additional rules
- Location of consultation questions and question rules are present [here](consultation/src/main/resources/consultation-questions/acne.json)
and [here](consultation/src/main/resources/consultation-rules/acne.json). I've had to guess as to the format as I don't
have a good idea about the requirements, but they work for this case

## Tradeoffs
- The application does not utilise any data storage. Representations of some of the data has been stored as a JSON file
due to time constraints
- Functional tests have been written from below the controller so to avoid starting up the application. 
Ideally, there should be a few tests against the APIs as a blackbox. However, it has been ensured that most of the domain
logic has been tested functionally
- There is very little error handling or validation: this wouldn't require much more work to introduce, but I don't
think this would have added much value for this purpose
- Not all consultations have been accounted for, nor has the various different question types. However, 
it should be _relatively_ easy to incorporate this
- Only `YES|NO` questions have been developed
- I chose Spring Boot as it's quick to get something up and running with a lot of features out of the box, but it's very
heavy. I would have preferred to use a library with a light wrapper around Jetty, such as Javalin, however there are
issues with the integration of OpenAPI & Swagger when utilising Java modules... and I don't have enough time to resolve 
these in the timescale
- Spring Boot also creates very large images and would require some optimisations to reduce this
- Most things are hardcoded when it comes to library dependencies, port numbers, etc

## Testing the Application
The application has been tested utilising unit, integration, and functional tests; All testing is done via JUnit5 and completes in a couple of seconds.

You can run the tests for the application by performing the following:

### Unit Tests
`./gradlew test`

### Integration Tests
`./gradlew integrationTest`

### Functional Tests
`./gradlew functionalTest`

If manual testing is preferred, there is swagger-ui which is navigable [here](http://localhost:8080/api/swagger-ui/index.html)

## Running the Application
### Via IntelliJ
Navigate to the application module in the root of the project, find the Application class in 
`org.heliosx.application` and click play

### Via Gradle
There are a couple of options to run the application from gradle. 
- `./gradlew bootRun` utilises Spring boots mechanism
- `./gradlew docker dockerComposeUp` ensure you have docker running and it will bring the application up in a docker 
container

