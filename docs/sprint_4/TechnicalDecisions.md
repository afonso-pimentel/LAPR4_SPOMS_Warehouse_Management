# Sprint 3

## Programming Language for SPOMS Application Protocol

The SPOMS Application Protocol is based upon TCP sockets and as such it has a fair ammount of complexity to it. According to what has already being taught in RCOMP classes, two different programming languagues can be used: C or Java.

Using C allows for a more detailed low level control of the socket and also the overall raw network packages due to the low level nature of C. This low level nature of C allows the students to have a more powerfull codebase because they can have a more fine grained control of the TCP socket but this comes with it's set of disavantages. The main disavantages is that C is not a very productive language, meaning, it does not provide abstractions that facilitate the job of the developer and it also exposes the students to really high levels of detail in terms of network knowledge, which slows down the overall productive rate of the students. On the other hand, Java provides out of the box abstractions to handle with TCP sockets, which helps the students to speed up their development process and facilitates the usage of TCP sockets.

Java was the chosen language to use on the AGV Digital Twin due to the previously mentioned advantages.

## Strategy Pattern vs Manual Implementation of Request Handlers

In the context of RCOMP each SPOMS Application Protocol client/server should be able to handle different requests that originate from different sources and that have totally diferent objectives with each request. In order to respond to each request appropriately a decision had to made in relation to what the standard of handling such requests would be.

The standard had two options: Strategy Pattern vs Manual Implementation of Request Handlers

Manual Implementation of Request Handlers would mean that each client/server would have it's own way of implementing how it would respond to a request. Manual implementation within each client/request would mean more flexibility for each client/server but less cohesion within the overall software architecture for the SPOMS project. Manual Implementation of Request Handlers would also mean less code quality on the overall software architecture for the SPOMS project due to the fact no standard guidelines would be followed.

Strategy Pattern would allow to set a standard for the whole SPOMS project and also ensure that an approach would be followed with it's foundations based on the a Software industry standard. 

Strategy Pattern was the chosen strategy to use due to the previously mentioned advantages.

# Sprint 4

## Big SQL Query to handle multiple target audience survey rules vs Strategy Pattern for each rule
In the context of LPROG and the requirements for the identification of the survey target audience, there was a need to apply different rules to identify the target audience of a given survey. The allowed/needed rules to identify customers as target audience of a given survey where the following:

* Specific product bought a given date period;
* Product from a specific category bought on a given date period;
* Product from a specific brand bought on a given date period;
* Customer with a specific gender;
* Customer with an age within a specified interval;

In order to apply all of these rules the team had to decide between two paths: 

**BIG SQL Query**: In the BIG SQL Query path the team had to build a method on a given repository and this method would have to query the database based on all the possible combinations of the survey rules for the target audience. This path had the advantage of being really simple in terms of needed software layers that had to be changed, meaning, only one layer had to be changed/extended which would be the repository layer but this path had a lot of disadvantages:
* Hard to extend/modify due to the complexity attached to the SQL Query.
* Hard to unit test

**Strategy Pattern**: In the Strategy Pattern patht the team had to build a Strategy for each supported rule to identify customers as target audience for a survey. This path had the disavantage that the team would have to touch multiple layers but it had the following advantages:
* Easy to extend/modify.
* Easy to unit test.
* Easier to understand.
* Easier to maintain.

**Strategy Pattern was the chosen path to use due to the previously mentioned advantages.**

## Union of rule results or intersection of rule results

In the context of LPROG and the application of rules to identify the customers that would comprise the target audience of a given survey, the team had to decide if the final target audience for a given survey would be the union of the results for each rule or the intersection of the results for each rule.

The union of rule results would be by far the easiest path to take due to the fact that JAVA supports out of the box the necessary methods to create a list of non-repeatable elements, meaning, behind the scenes the code only had to append the results of each rule to the final target audience list.

The intersection of rule results would be the hardest to implement due to the fact that the team would need to create extra code to identify which clients adhered to a rule but not adhered to all the other rules and then perform an intersection operation between the results of each role.

**Due to the union rule being the easiest and the fact that no requirements where attached to which rule to follow, the team decided to go with the union rule.**

## JAVA Keystore vs Operating System Store
In the context of RCOMP and the need to implement security on each application developed during the SPOMS project, the team had to determine if we were going to use JAVA's keystore to handle all the certificates for each application or if the team would use the Operating System Store.

Altough the Operating System store seemed to be the most secure one and the most reliable one due to the fact that even browsers nowadays use the Operating System's store(e.g Google Chrome), the examples and teachings done by the teachers of RCOMP were based upon JAVA's keystore. JAVA's keystore was also easier to develop with due to the fact that the programming languague used on the project was JAVA itself.

**Due to the previously mentioned reasons, the team decided to use JAVA Keystore instead of the Operating System store.**