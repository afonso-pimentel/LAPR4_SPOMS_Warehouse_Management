# Programming Language for SPOMS Application Protocol

The SPOMS Application Protocol is based upon TCP sockets and as such it has a fair ammount of complexity to it. According to what has already being taught in RCOMP classes, two different programming languagues can be used: C or Java.

Using C allows for a more detailed low level control of the socket and also the overall raw network packages due to the low level nature of C. This low level nature of C allows the students to have a more powerfull codebase because they can have a more fine grained control of the TCP socket but this comes with it's set of disavantages. The main disavantages is that C is not a very productive language, meaning, it does not provide abstractions that facilitate the job of the developer and it also exposes the students to really high levels of detail in terms of network knowledge, which slows down the overall productive rate of the students. On the other hand, Java provides out of the box abstractions to handle with TCP sockets, which helps the students to speed up their development process and facilitates the usage of TCP sockets.

Java was the chosen language to use on the AGV Digital Twin due to the previously mentioned advantages.



# Strategy Pattern vs Manual Implementation of Request Handlers

In the context of RCOMP each SPOMS Application Protocol client/server should be able to handle different requests that originate from different sources and that have totally diferent objectives with each request. In order to respond to each request appropriately a decision had to made in relation to what the standard of handling such requests would be.

The standard had two options: Strategy Pattern vs Manual Implementation of Request Handlers

Manual Implementation of Request Handlers would mean that each client/server would have it's own way of implementing how it would respond to a request. Manual implementation within each client/request would mean more flexibility for each client/server but less cohesion within the overall software architecture for the SPOMS project. Manual Implementation of Request Handlers would also mean less code quality on the overall software architecture for the SPOMS project due to the fact no standard guidelines would be followed.

Strategy Pattern would allow to set a standard for the whole SPOMS project and also ensure that an approach would be followed with it's foundations based on the a Software industry standard. 

Strategy Pattern was the chosen strategy to use due to the previously mentioned advantages.