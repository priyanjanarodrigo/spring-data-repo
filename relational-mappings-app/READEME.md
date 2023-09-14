# Relationship Mappings Application

### Running the application with a specified active profile

- Executing the application with a specific profile using the maven command.

```
mvn clean install & mvn spring-boot:run -Dspring-boot.run.profiles=local
```

- Or else natigate to the `./target` directory and execute following command with a required profile at the end as an argument (make sure the .jar file name is accurate)

```agsl
java -jar relational-mappings-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
