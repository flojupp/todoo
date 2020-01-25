# todoo

How to start the todoo application
---

1. Run `mvn clean install` to build your application
1. Start mongod `sudo service mongod start`
1. Start application with `java -jar target/todoo-1.0.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Todo
---
* Swagger
* DI


Requirements:
---
Backend:

We would like you to create a simple REST-Service with the help of Dropwizard (https://www.dropwizard.io/en/release-1.3.x/manual/index.html). 
The service shall allow to manage Todos, e.g., like you might know it from Wunderlist, Todoist or similar applications. Of course much simpler.

 

A Todo contains an arbitrary list of subtasks and is structured as follows:
```javascript
{

    id [mandatory]

    name [mandatory]

    description

    tasks: [

        {

            id [mandatory]

            name [mandatory]

            description

        }

    ]

}
```
 

The service shall support the following endpoints:
* GET /todos → Returns a list of all Todos
* POST /todos → Expects a Todo (without id) and returns a Todo with id
* GET /todos/{id} → Returns a Todo
* PUT /todos/{id} → Overwrites an existing Todo
* DELETE /todos/{id} → Deletes a Todo

 

The way the Todos a stored in the backend is up to you.