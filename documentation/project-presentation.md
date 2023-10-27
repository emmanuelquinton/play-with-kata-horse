## Foreword
In this document I will describe the solution that I've implemented.

## API description

To describe the API I used an openAPI contract .
The openapi specifications are available [here](https://swagger.io/specification/)

To generate the API and the DTOs I use the openapi Gradle generator which is described [here](https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/README.adoc) allows us to generate:



## Description Project 

### Technologies

* JDK 17
* Springboot 3
* H2 as database
* Openapi as API specification
* Gradle as builder tools

### sourceSets
There are 3 sourceSets:
* main: contains all micro=service code
* test: contains all unitary tests
* integration-test: contains all integration tests


### packages

All classes are grouped into 3 distinct packages
- **api**:  which contains Controllers and Mapper (Dto <-> Model)
- **domain**: which contains use cases(services), models and port
- **persistence**: which should contain the implementation of the secondary port and entities


### With more time
With more time, I could have:
* implement a helical architecture to clearly separate endpoints/domain and persistence
* use a Postgres database, using docker-compose to run it on the development workstation and the test container to run it in integration tests
* develop the sending of events to kafka using the ["transactional outbox pattern"](https://microservices.io/patterns/data/transactional-outbox.html).
* set up system tests




