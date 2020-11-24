# spring-webflux
Spring WebFlux demo with SSE

This demo implements a `@RestController` with an interface to "subscribe" to SSE and one to send event to a specific destination.
The first API is exposed to http://localhost:8080/subscribe-sse/{destId}
The one to send events to http://localhost:8080/send/{destId}

You do multiple subscription using different values of `destId`

Output of subscription is something like the following

    data:{"id":0,"destId":1,"value":"Hello 1"}
    data:{"id":1,"destId":1,"value":"Hello 1"}
    data:{"id":2,"destId":1,"value":"Hello 1"}
    data:{"id":3,"destId":1,"value":"Hello 1"}
