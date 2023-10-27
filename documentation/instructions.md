you need to develop a new microservice responsible for the life cycle of a race and its runners
(horses).
After a tough interrogation of the business, you learn that:
- A race takes place on a given day and has a unique name and number for that day;
- A race has at least 3 runners;
- Each runner has a name and a number;
- The runner of a race are numbered from 1, without duplicates or gaps.

  For the MVP, you'll be asked to develop an API to create races and their starters
  store the information in a database and expose it to the rest of the IS via a message
  message published on a bus.


We impose the following constraints on you:
- Spring Boot application in Java
- SQL database
- Kafka bus
  This exercise should take 2 hours to complete.