## Webflux Metrics Issue

from spring boot 2.1.4.RELEASE micrometer metrics are not being recorded correctly.

|Spring Boot Version | Server  | Controller Response | Metrics Reported |
|--------------------|---------|---------------------|------------------|
|2.1.8.BUILD-SNAPSHOT| webflux | Mono                | yes              | 
|2.1.8.BUILD-SNAPSHOT| webflux | String              | yes              | 
|2.1.6.RELEASE       | webflux | Mono                | no               | 
|2.1.6.RELEASE       | webflux | String              | no               | 
|2.1.6.RELEASE       | webflux | Exception           | yes              | 
|2.1.6.RELEASE       | web     | String              | yes              | 
|2.1.5.RELEASE       | webflux | String              | no               | 
|2.1.5.RELEASE       | webflux | Mono                | no               | 
|2.1.5.RELEASE       | webflux | Exception           | yes              | 
|2.1.5.RELEASE       | web     | String              | yes              | 
|2.1.4.RELEASE       | webflux | String              | yes              | 
|2.1.4.RELEASE       | webflux | Mono                | yes              | 
|2.1.4.RELEASE       | webflux | Exception           | yes              | 
|2.1.4.RELEASE       | web     | String              | yes              | 

#### Fix

forcing reactor-netty to be downgraded to 0.8.6.RELEASE can be used as a workaround.

```gradle
dependencies {
    ...
    implementation ("io.projectreactor.netty:reactor-netty:0.8.6.RELEASE") {
        force = true
    }
}
```

#### Testing
After building this project with the correct version of spring boot and selecting either `spring-boot-starter-webflux` or `spring-boot-starter-web` the test endpoint was requested a number of times.
The metrics endpoint was then check to see if the `/test` endpoint had been registered.

For testing the reactive controller the `/test` method was made to return `Mono.just("test")` otherwise it would just return a string.
For the exception test a new runtime exception was thrown. 

#### Simulating traffic

Send a get request to  <http://localhost:8080/test> a couple of times.

#### Checking metrics

Go to <http://localhost:8080/actuator/metrics/http.server.requests> and check there is a value of '/test' in the values array.
